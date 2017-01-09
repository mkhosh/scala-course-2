abstract class JSON {
  override def toString = this match {
    case JSeq(elems) =>
      "[" + (elems mkString ", ") + "]"
    case JObj(bindings) =>
      val assocs = bindings map {
        case (key, value) => "\"" + key + "\": " + value
      }
      "{" + (assocs mkString ", ") + "}"
    case JNum(num) => num.toString
    case JStr(str) => '\"' + str + '\"'
    case JBool(b) => b.toString
    case JNull => "null"
  }
}

List(1, 2, 3) mkString "a"

case class JSeq(elems: List[JSON]) extends JSON

case class JObj(bindings: Map[String, JSON]) extends JSON

case class JNum(num: Double) extends JSON

case class JStr(str: String) extends JSON

case class JBool(b: Boolean) extends JSON

case object JNull extends JSON

val data = JObj(Map(
  "firstName" -> JStr("John"),
  "lastName" -> JStr("Smith"),
  "address" -> JObj(Map(
    "streetAddress" -> JStr("21 2nd Street"),
    "state" -> JStr("NY"),
    "postalCode" -> JNum(10021)
  )),
  "phoneNumbers" -> JSeq(List(
    JObj(Map(
      "type" -> JStr("home"), "number" -> JStr("212 555-1234")
    )),
    JObj(Map(
      "type" -> JStr("fax"), "number" -> JStr("646 555-4567")
    ))))))

def show(json: JSON): String = json match {
  case JSeq(elems) =>
    "[" + (elems map show mkString ", ") + "]"
  case JObj(bindings) =>
    val assocs = bindings map {
      case (key, value) => "\"" + key + "\": " + show(value)
    }
    "{" + (assocs mkString ", ") + "}"
  case JNum(num) => num.toString
  case JStr(str) => '\"' + str + '\"'
  case JBool(b) => b.toString
  case JNull => "null"
}

show(data)

val f: PartialFunction[String, String] = {
  case "ping" => "pong"
}
f("ping")
f.isDefinedAt("abc")

trait myTrait[T] {
  def apply(x: T): T
}

val a = new myTrait[Int] {
  def apply(x: Int) = x * x
}

a(12)

val f2: PartialFunction[List[Int], String] = {
  case Nil => "One"
  case x :: y :: rest => "," + f2(rest)
}

f2(List(1,2,3))
f2.isDefinedAt(List(1,2,3)) //it seems that it doesn't check recurssive call