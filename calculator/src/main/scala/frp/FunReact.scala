package frp

/**
  * Created by mkhoshneshin on 1/27/17.
  */
object FunReact {
}
class BankAccount {
  val balance = Var(0)

  def deposit(amount: Int): Unit =
    if (amount > 0) {
      val b = balance()
      balance() = b + amount
    }

  def withdraw(amount: Int): Unit =
    if (0 < amount && amount <= balance()) {
      val b = balance()
      balance() = b - amount
    } else throw new Error("insufficient funds")

  override def toString: String = s"Account with $$${balance()}"
}

