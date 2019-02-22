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
    conn.createStatement().executeQuery("select id, username from people where name = '" + value + "'")
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

/*  // Potential Scala Slick Injection
  db.run {
    sql"select * from people where name = '#$value'".as[Person]
  }

  // Potential Scala Anorm Injection
  val peopleParser = Macro.parser[Person]("id", "name", "age")

  DB.withConnection { implicit c =>
    val people: List[Person] = SQL("select * from people where name = '" + value + "'").as(peopleParser.*)
  }

  // Potential information leakage in Scala Play
  def doGet(value:String) = Action {
    val configElement = configuration.underlying.getString(value)

    Ok("Hello "+ configElement +" !")
  }

  // Scala Play Server-Side Request Forgery (SSRF)
  def doGet(value:String) = Action {
    WS.url(value).get().map { response =>
      Ok(response.body)
    }
  }


  // Potential XSS in Scala Twirl template engine
  @(value: Html)
  <html><h1>bla bla bla</h1></html>
  @value


  // Potential XSS in Scala MVC API engine
  def doGet(value:String) = Action {
    Ok("Hello " + value + " !").as("text/html")
  }*/

  def main(args: Array[String]): Unit = {
    val runtime = Runtime.getRuntime
    //Executes potential dangerous command
    val proc = runtime.exec("find" + " " + args(0))
  }

  import java.net.URLClassLoader

  val classURLs: Array[Nothing] = Array[Nothing](new Nothing("file:subdir/"))
  val loader = new URLClassLoader(classURLs)
  val loadedClass: Class[_] = Class.forName("loadMe", true, loader)
}