import frp.{BankAccount, _}

val num = Var(1)
num.curObs
val twice = Signal(num()*2)
num.curObs
num() = 13
num.curObs
twice()



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
a
c.curObs

val xchange = Signal(246.00)
val inDollar = Signal(c() * xchange())

inDollar()

b.withdraw(10)

inDollar()


