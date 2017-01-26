1 +: List(2, 3) :+ 4

class BankAccount {
  private var balance = 0

  def deposit(amount: Int): Unit = {
    if (amount > 0) balance = balance + amount
  }

  def withdraw(amount: Int): Int =
    if (0 < amount && amount <= balance) {
      balance = balance - amount
      balance
    } else throw new Error("insufficient funds")

  override def toString = "(balace = " + balance + ")"
}

val myAccount = new BankAccount

myAccount.deposit(250)
myAccount.deposit(10)
myAccount.withdraw(35)
myAccount

if (1 == 1) print("hi ")

def REPEAT(command: => Unit)(condition: => Boolean): Unit = {
  command
  if (condition) () else REPEAT(command)(condition)
}

var i = 0
REPEAT {
  print(i + " ")
  i = i + 1
}(i >= 10)

val e: Seq[Int] = Nil
val e2: List[Int] = List()

val e3 = 1 :: e2

3 min 4

List(3,1,2,1).sorted

import org.scalacheck.Prop.{forAll, BooleanOperators}

val propMakeList = forAll { n: Int =>
  (n >= 0 && n < 10000) ==> (List.fill(n)("").length == n)
}

propMakeList.check