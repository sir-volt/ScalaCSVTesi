package start

import input.csv.implementation.DoubleCSVFileReader
import java.io.File

object Main extends App {
  val file = new File("")
  val reader = new DoubleCSVFileReader(file)

  val series = reader.read

  println(series.entries)
}
