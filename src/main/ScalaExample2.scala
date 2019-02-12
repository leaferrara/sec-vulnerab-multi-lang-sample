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


  // Path traversal
  def getWordList3(value:String) {
    if (!Files.exists(Paths.get("public/lists/" + value))) {
      NotFound("File not found")
    } else {
      val result = Source.fromFile("public/lists/" + value).getLines().mkString // Weak point
      Ok(result)
    }
  }

  // Path traversal
  def getWordList333(value:String) {
    if (!Files.exists(Paths.get("public/lists/" + value))) {
      NotFound("File not found")
    } else {
      val result = Source.fromFile("public/lists/" + value).getLines().mkString // Weak point
      Ok(result)
    }
  }

  def doGet(value:String) {
    Ok("Hello " + value + " !").as("text/html")
  }

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
