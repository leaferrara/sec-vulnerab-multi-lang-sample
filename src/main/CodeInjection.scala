import javax.script._


object Example1 {
  def main(args: Array[String]): Unit = {
    try {
      val manager = new ScriptEngineManager
      val engine = manager.getEngineByName("JavaScript")
      System.out.println(args(0))
      engine.eval("print('" + args(0) + "')")
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }
}
