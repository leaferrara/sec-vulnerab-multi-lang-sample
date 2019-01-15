import scala.util.Random

class ScalaExample {

  val stringExample = "example";

  // This is vulnerable code - for test purposes only!
  def generateSecretToken() {
    val result = Seq.fill(16)(Random.nextInt)
    return result.map("%02x" format _).mkString
  }
}