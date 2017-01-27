import calculator.{BankAccount, Signal, Var}

def consolidate(accts: List[BankAccount]): Signal[Int] = {
  Signal(accts.map(_.balance()).sum)
}

val a = new BankAccount
val b = new BankAccount
val c = consolidate(List(a, b))
c()

a.deposit(20)
b.deposit(30)
a.withdraw(5)
c()

val xchange = Signal(246.00)
val inDollar = Signal(c() * xchange())

inDollar()

b.withdraw(10)

inDollar()

var num = Var(1)
val twice = Signal(num()*2)
num = Var(2)
twice()
