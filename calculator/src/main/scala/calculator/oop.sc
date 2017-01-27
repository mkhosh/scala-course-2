abstract class test {
  def sayIt(s: String): String
}

val a = new test {
  def sayIt(s: String) = s"you said $s"
}

a.sayIt("what")


trait GenericStream {
  val stream: Stream[Int]
}

trait IntegerStreams extends GenericStream {
  override val stream = Stream.from(1)
  val oddsStream: Stream[Int] = Stream.from(1, 2)
  val evensStream: Stream[Int] = Stream.from(2, 2)
}

trait FibonacciStream extends GenericStream {
  override val stream: Stream[Int] = 0 #:: stream.scanLeft(1)(_ + _)
}

object FunkyMath extends IntegerStreams with FibonacciStream {
  def generateIntegers(n: Int): List[AnyVal] = stream.take(n).toList
}

val fib = new FibonacciStream {}
fib.stream.take(10).toList

List(1,2,3,4).scanLeft(1)(_+_)

val fib2: Stream[Int] = 0 #:: fib2.scanLeft(1)(_+_)
fib2.take(10).toList