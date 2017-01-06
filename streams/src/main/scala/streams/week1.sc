
val myList = List(1, 2, 3)

myList.foreach(println)

for {
  i <- 1 to 5
  j = i * 2
  k <- i to j
} yield (i, j, k)

def even(n: Int) = n % 2 == 0

((1000 to 10000).toStream filter even) (2)
val s = Stream(1, 2, 3)

def streamRange(lo: Int, hi: => Int): Stream[Int] = {
  print(lo + " ")
  if (lo >= hi) Stream.empty
  else lo #:: streamRange(lo + 1, hi)
}

lazy val s2 = streamRange(1, 10).take(3)

s2.apply(1)

def from(n: Int): Stream[Int] = n #:: from(n + 1)

val nats = from(0)

(nats map (_ * 2 + 1)).take(10).toList

case class test(a: Int) {
  def delta(d: Int) = copy(a = a + d)
}

val t1 = test(12)

val t2 = t1 delta 3

t2.a

val v = Vector(Vector(1, 2, 4), Vector(6, 7))

val v2 = v(0)

v2 indexOf (4)

true && false

Vector("abc": _*)

val myset = Set(1, 3, 4)

myset + 1