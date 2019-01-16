import java.nio.file.{Files, Paths}

import sun.nio.cs.Surrogate.Parser

import scala.io.Source
import scala.util.Random
import scala.sys.process._
import scala.reflect.macros._

class ScalaExample {

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

//  // Potential Scala Anorm Injection
//  val peopleParser = Parser.parser[Person]("id", "name", "age")
////  val peopleParser = Macro.parser[Person]("id", "name", "age")
//
//  DB.withConnection { implicit c =>
//    val people: List[Person] = SQL("select * from people where name = '" + value + "'").as(peopleParser.*)
//  }
//
//  db.run {
//    sql"select * from people where name = '#$value'".as[Person]
//  }


  def NotFound(str: String): Unit = {
    print(str);
  }

  def Ok(str: String): Unit = {
    print(str);
  }
}

//object DB {
//  def withConnection()
//}

class Person(val id:String, val name:String, val age:String) {

}