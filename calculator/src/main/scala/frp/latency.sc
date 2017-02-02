//import Future
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

val v = 1


/** Error as an Effect - Try[T] handles exceptions */

/** Latency as Effect - Future[T] handles exception and latency */
trait Socket {
  def readFromMemory(): Array[Byte]

  def sendToEurope(packet: Array[Byte]): Array[Byte]
}

object Socket {
  def apply() = Socket

  def readFromMemory(): Array[Byte] = Array(2.toByte)

  def sendToEurope(packet: Array[Byte]): Array[Byte] = Array(4.toByte)

}

val socket = Socket() //> socket
val packet = socket.readFromMemory()
//block for 50,000 ns (3 days)
//only continue if there is no exception
val confirmation = socket.sendToEurope(packet)

//block for 150,000,000 ns (5 years)
//only continue if there is no exception

//If a computation takes a lot of time, we will use a callback
//callback needs to use pattern matching

trait SocketFuture {
  def readFromMemory(): Future[Array[Byte]]

  def sendToEurope(packet: Array[Byte]): Future[Array[Byte]]
}

object SocketFuture {
  def apply() = SocketFuture

  def readFromMemory(): Future[Array[Byte]] = Future(Array(2.toByte))

  def sendToEurope(packet: Array[Byte]): Future[Array[Byte]] = Future(Array(4.toByte))

}

val socketFuture = SocketFuture()
val packetFuture: Future[Array[Byte]] = socketFuture.readFromMemory()

val confirmationFuture: Future[Array[Byte]] =
  packetFuture.flatMap(socketFuture.sendToEurope)

for {
  i: Int <- List(1,2,3)
} yield i