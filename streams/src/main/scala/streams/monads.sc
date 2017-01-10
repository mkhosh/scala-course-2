import scala.util.{Failure, Try}
// Monad should have a flatMap (>>= or bind in haskell) and unit (same as return of pure)

List(1, 2, 3) flatMap (x => List(x, x))
List(1, 2, 3) flatMap (((x: Int) => x * 2) andThen (x => List(x)))

/**
  * Monad Laws
  * - Associativity
  *     (m flatMap f) flatMap g == m flatMap (x => f(x) flatMap g)
  *
  *     it makes it possible to write:
  *     for {x <- m
  *          y <- g(x)
  *          z <- g(y)} yield z
  *
  * - Left Unit
  *     unit(x) flatMap f == f(x)
  *
  * - Right unit
  *     m flatMap unit == m
  *
  *     for (x <- m) yield x == m
  *
  */


Try(12)
// cannot do this Failure(12)
Try(12/0)

for {
  x <- Try(12.0/14.0)
  y <- Try(13.0*2)
} yield x + y

for {
  x <- Try(12/0)
  y <- Try(13*2)
} yield x + y

