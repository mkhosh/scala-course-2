type Action = () => Unit
/**
  * () in the Action type is different than the literal
  * value () of Unit. The first is 0-arity input type while
  * the latter is a value.
  */

def f: Action = () => print("something ")

def h: () => Unit = () => print(" bye ")

def f2() = print(" simple definition ")

f()
h()
f2()

val a: Unit = ()

()

def g(x: => Int) = x * x
var y = 1
g {
  y += 1; y
}
y
def aaa(f: => String) = {
  f
}

aaa("abc")

val bbb = aaa _

bbb("abc")

val actions = List(f, h)

for (a <- actions) a()
