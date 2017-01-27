import calculator.{BankAccount, Signal}

def consolidate(accts: List[BankAccount]): Signal[Int] = {
  Signal(accts.map(_.balance()).sum)
}


class test {
  def f():Unit = println("Hello")
}

val l1 = List(new test, new test).map(_.f)
val l2 = List(new test, new test).map(_.f())

l1
l2