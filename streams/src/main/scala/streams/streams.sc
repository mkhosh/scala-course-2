val xs = Stream.cons(1, Stream.cons(2, Stream.Empty))

(xs map (_ * 2)).toList

(1 to 1000).toStream.filter(_ % 3 == 0)(4)

val fr = Stream.from(1).take(3)

def streamRange(lo: Int, hi: Int): Stream[Int] = {
  print(lo + " ")
  if (lo >= hi) Stream.empty
  else Stream.cons(lo, streamRange(lo + 1, hi))
}

val isLazy = streamRange(1, 100)

isLazy.take(3).toList
isLazy.take(5).toList

isLazy.filter(_ % 5 == 0)(5)

def streamFrom(lo: Int): Stream[Int] =
  lo #:: streamFrom(lo + 1)

streamFrom(2)

def streamSum(xs: Stream[Int]): Int = xs match {
  case Stream.Empty => 0
  case Stream.cons(x, xs2) => x + streamSum(xs2)
}

streamSum({
  (1 to 1000).toStream
})

trait MyStream[+A] {
  def isEmpty: Boolean

  def head: A

  def tail: MyStream[A]

  override def toString: String = "MyStream(" + head.toString + ", ?)"
}

object MyStream {
  def cons[T](hd: T, tl: => MyStream[T]) = new MyStream[T] {
    def isEmpty = false

    def head = hd

    lazy val tail = tl
  }

  val empty = new MyStream[Nothing] {
    def isEmpty = true

    def head = throw new NoSuchElementException("empty.head")

    def tail = throw new NoSuchElementException("empty.tail")
  }
}

val ys = MyStream.cons(1, MyStream.cons(2, MyStream.cons(3, MyStream.empty)))

val nat = streamFrom(1)

nat map (_ * 4)

def sieve(s: Stream[Int]): Stream[Int] =
  s.head #:: sieve(s.tail filter (_ % s.head != 0))

sieve(streamFrom(2)).take(10).toList

def sqrtStream(x: Double): Stream[Double] = {
  def improve(guess: Double) = (guess + x / guess) / 2
  lazy val guesses: Stream[Double] = 1 #:: (guesses map improve)
  guesses
}

sqrtStream(2).filter(x => Math.abs(x*x-2)/2 < 0.00001)(0)