import java.nio.file.{Files, Paths}
import java.sql.SQLData

import sun.nio.cs.Surrogate.Parser

import scala.io.Source
import scala.util.Random
import scala.sys.process._
import java.sql.DriverManager

import javax.swing.text.html.HTML

class ScalaExample[value3:HTML] {

  val stringExample = "example";


  // Predictable pseudorandom number generator
  def generateSecretToken() {
    val result = Seq.fill(16)(Random.nextInt)
    return result.map("%02x" format _).mkString
  }

  // Path traversal
  def getWordList(value:String) {
    if (!Files.exists(Paths.get("public/lists/" + value))) {
      NotFound("File not found")
    } else {
      val result = Source.fromFile("public/lists/" + value).getLines().mkString // Weak point
      Ok(result)
    }
  }

  // Potential command injection
  def executeCommand(value:String) {
    val result = value.!
    Ok("Result:\n"+result)
  }

  // Potential Scala Anorm Injection
  val conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/clients","username","password")

  def queryingSQL(value: String): Unit = {
    conn.createStatement().executeQuery("select * from people where name = '" + value + "'")
  }

//  def doGet(value:String) {
//    Ok("Hello " + value + " !").as("text/html")
//  }

  def NotFound(str: String): Unit = {
    print(str);
  }

  def Ok(str: String) = {
    print(str);

    def as(arg:String): Unit = {
      print();
    }
  }
}