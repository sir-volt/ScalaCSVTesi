package start

import java.io.File
import start.Config
import scopt.OParser

class configMain {
  val parser = new scopt.OptionParser[Config]("scopt") {
    head("scopt", "4.x")

    /*
    opt[Int]('f', "foo")
      .foreach(x => c = c.copy(foo = x))
      .text("foo is an integer property")

    opt[File]('o', "out")
      .required()
      .valueName("<file>")
      .foreach(x => c = c.copy(out = x))
      .text("out is a required file property")*/
  }
}
