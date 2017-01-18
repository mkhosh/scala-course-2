package quickcheck

/**
  * Created by mkhoshneshin on 1/13/17.
  */
abstract class Simulation {
  type Action = () => Unit

  case class Event(time: Int, action: Action)

  private var curtime = 0

  def currentTime: Int = curtime

  def lengthOfAgenda = agenda.length

  private type Agenda = List[Event]
  private var agenda: Agenda = List()

  private def insert(ag: Agenda, item: Event): List[Event] = ag match {
    case first :: rest if first.time <= item.time =>
      first :: insert(rest, item)
    case _ =>
      item :: ag
  }

  def afterDelay(delay: Int)(block: => Unit): Unit = {
    val item = Event(currentTime + delay, () => block)
    agenda = insert(agenda, item)
  }

  def run(): Unit = {
    afterDelay(0) {
      println(s"*** simulation started, time = $currentTime ***")
    }
    loop()
  }

  private def loop(): Unit = agenda match {
    case first :: rest =>
      agenda = rest
      curtime = first.time
      print(s"<<doing at $curtime>>")
      first.action()
      loop()
    case Nil =>
  }
}

