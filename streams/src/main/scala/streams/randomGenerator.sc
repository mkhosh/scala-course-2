import java.util.Random

val rand = new Random
rand.nextInt()

trait Generator[+T] {
  selfOrWhatever =>
  //an alias for this
  def generate: T

  def map[S](f: T => S): Generator[S] = new Generator[S] {
    def generate = f(selfOrWhatever.generate)
  }

  def flatMap[S](f: T => Generator[S]): Generator[S] = new Generator[S] {
    def generate = f(selfOrWhatever.generate).generate
  }
}

def single[T](x: T): Generator[T] = new Generator[T] {
  //an alias for this
  override def generate = x
}

val integers = new Generator[Int] {
  val rand = new java.util.Random

  override def generate = rand.nextInt()
}

def choose(lo: Int, hi: Int): Generator[Int] =
  for (x <- integers) yield lo + Math.abs(x) % (hi - lo)

// choose as many as you want
def oneOf[T](xs: T*): Generator[T] =
  for (idx <- choose(0, xs.length)) yield xs(idx)

val chosen = choose(0, 2).generate
oneOf("abc", "de", "12").generate

integers.generate

//val booleans = new Generator[Boolean] {
//  override def generate = integers.generate > 0
//}

val booleans = integers.map(_ > 0)

booleans.generate

def pairs[T,S](gT:Generator[T],gS:Generator[S]): Generator[(T,S)] = for {
  x <- gT
  y <- gS
} yield (x, y)

def lists: Generator[List[Int]] = for {
  isEmpty <- booleans
  list <- if (isEmpty) emptyLists else nonEmptyLists
} yield list

def emptyLists = single(Nil)

def nonEmptyLists = for {
  x <- integers
  xs <- lists
} yield x :: xs

lists.generate

trait Tree

case class Inner(left: Tree, right: Tree) extends Tree

case class Leaf(x: Int) extends Tree

def trees: Generator[Tree] = for {
  isLeaf <- booleans
  tree <- if (isLeaf) leafs else inners
} yield tree

def leafs = for {
  x <- integers
} yield Leaf(x)

def inners = for {
  l <- trees
  r <- trees
} yield Inner(l, r)

trees.generate

def test[T](r: Generator[T], noTimes: Int = 100)(test: T => Boolean) {
  for (_ <- 0 until noTimes) {
    val value = r.generate
    assert(test(value), "Test failed for: " + value)
  }
  println("Tests passed " + noTimes + " times.")
}

pairs(lists,lists).generate
test(pairs(lists,lists))//(p => (p._1++p._2).length > p._1.length)
// you cannot pattern match directly using lambda notation (pattern => ...)
// you need to use x match {case pattern or the syntactic sugar {case pattern => ...
{
  case (xs,ys) => (xs++ys).length == xs.length + ys.length
}

List(1,2,3):::(List(4,5))