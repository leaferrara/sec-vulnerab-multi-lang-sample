import java.nio.file.{Files, Paths}

import scala.io.Source
import scala.util.Random

class ScalaExample {

  val stringExample = "example";

  // This is vulnerable code - for test purposes only!
  def generateSecretToken() {
    val result = Seq.fill(16)(Random.nextInt)
    return result.map("%02x" format _).mkString
  }

  def getWordList(value:String) {
    if (!Files.exists(Paths.get("public/lists/" + value))) {
      NotFound("File not found")
    } else {
      val result = Source.fromFile("public/lists/" + value).getLines().mkString // Weak point
      Ok(result)
    }
  }

  def NotFound(str: String): Unit = {
    print(str);
  }

  def Ok(str: String): Unit = {
    print(str);
  }
}