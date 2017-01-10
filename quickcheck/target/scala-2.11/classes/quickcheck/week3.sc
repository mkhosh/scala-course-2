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

def REPEAT(command: => Unit) (condition: => Boolean): Unit = {
  command
  if (condition) () else REPEAT(command)(condition)
}

var i = 0
REPEAT {
  print(i+" ")
  i = i + 1
} (i >= 10)