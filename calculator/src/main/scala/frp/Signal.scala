package frp

import scala.util.DynamicVariable

/**
  * Created by mkhoshneshin on 1/30/17.
  */
class Signal[T](expr: => T) {

  import Signal._
  //import stuff for Signal object like caller

  private var myExpr: () => T = _
  private var myValue: T = _
  private var observers: Set[Signal[_]] = Set()
  update(expr)

  protected def computeValue(): Unit = {
    val newValue = caller.withValue(this)(myExpr())
    if (myValue != newValue) {
      myValue = newValue
      val obs = observers
      observers = Set()
      obs.foreach(_.computeValue())
    }
  }

  protected def update(expr: => T): Unit = {
    myExpr = () => expr
    computeValue()
  }

  def apply(): T = {
    observers += caller.value
    assert(!caller.value.observers.contains(this), "cyclic signal definition")
    myValue
  }

  override def toString: String = if (myValue == null) "_" else myValue.toString

  def curObs: String = observers.mkString(";")
}

object NoSignal extends Signal[Nothing](???) {
  override def computeValue(): Unit = ()

  override def toString: String = "NoSignal"
}

object Signal {
//  private val caller = new StackableVariable[Signal[_]](NoSignal)
  private val caller = new DynamicVariable[Signal[_]](NoSignal)

  //takes signals of any value type
  def apply[T](expr: => T) = new Signal(expr)
}

class Var[T](expr: => T) extends Signal[T](expr) {
  //we need to add here because the update of parent is protected and cannot be accessed from client
  override def update(expr: => T): Unit = super.update(expr)
}

object Var {
  def apply[T](expr: => T) = new Var(expr)
}

class StackableVariable[T](init: T) {
  private var values = List(init)

  def value: T = values.head

  def withValue[R](newValue: T)(op: => R): R = {
    values = newValue :: values
    try op finally {
      values = values.tail
    }
  }

  override def toString: String = values.mkString(",")
}