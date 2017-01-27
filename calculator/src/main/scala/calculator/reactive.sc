import calculator.BankAccount

trait Coin {}

case class Gold() extends Coin {}

case class Silver() extends Coin {}

object Bronze extends Coin {}

val pf: PartialFunction[Coin, String] = {
  case Gold() => "a golden coin"
  case Bronze => "a bronze coin"
  // no case for Silver(), because we're only interested in Gold()
}

pf.isDefinedAt(Gold())
pf.isDefinedAt(Silver()) // false
pf(Gold()) // a golden coin
pf(Bronze)
//println(pf(Silver())) // throws a scala.MatchError

val xs = List(1, 2, 3)
val ys = List(4, 5, 6)
var i = 0
for {
  x <- xs
  z = x * 2
  y <- {i+=2; ys}
} yield (x, y, z)

xs flatMap { x =>
  ys map {
    y => (x, y)
  }
}

