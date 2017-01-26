package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  lazy val genHeap: Gen[H] = oneOf(const(empty), genNonEmptyHeap)
  lazy val genNonEmptyHeap: Gen[H] = for {
    x <- arbitrary[A]
    h <- genHeap
  } yield insert(x, h)


  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genNonEmptyHeap)

  property("gen1") = forAll { (h: H) =>
    val m = if (isEmpty(h)) 0 else findMin(h)
    findMin(insert(m, h)) == m
  }
  // If you insert any two elements into an empty heap, finding the minimum of
  // the resulting heap should get the smallest of the two elements back.
  property("minVal") = forAll { (x1: A, x2: A) =>
    val h1 = insert(x1, empty)
    val h2 = insert(x2, h1)
    findMin(h2) == (x1 min x2)
  }

  // If you insert an element into an empty heap, then delete the minimum, the resulting heap should be empty.
  property("one elem is empty") = forAll { x: A =>
    val h = insert(x, empty)
    isEmpty(deleteMin(h))
  }

  // Given any heap, you should get a sorted sequence of elements when continually finding and deleting minima.
  // (Hint: recursion and helper functions are your friends.)
  def getHeapVals(h: H): List[A] =
  if (isEmpty(h)) Nil
  else findMin(h) :: getHeapVals(deleteMin(h))

  property("sortedOUtput") = forAll { (h: H) =>
    val xs = getHeapVals(h)
    xs == xs.sorted
  }

  // Finding a minimum of the melding of any two heaps should return a minimum of one or the other.
  property("minOf") = forAll { (h1: H, h2: H) =>
    findMin(meld(h1, h2)) == (findMin(h1) min findMin(h2))
  }

  property("meldAfterTransfer") = forAll { (h1: H, h2: H) =>
    val x = findMin(h1)
    getHeapVals(meld(h1, h2)) == getHeapVals(meld(deleteMin(h1), insert(x, h2)))
  }
}
