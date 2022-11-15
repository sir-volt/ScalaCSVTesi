package start

import input.csv.implementation.DoubleCSVFileReader
import de.sciss.chart.api.*
import plots.implementation.{DoubleAveragePlot, DoubleSpecificColumnPlot}
import readFiles.implementation.ListFiles
import scala.io.StdIn.*

import java.io.File
import java.nio.file.Paths

object TestMain extends App {
  val filesList = new ListFiles("src/test/resources").getListOfFiles
  println("choose which file you want to study from 0 to " + (filesList.size - 1) + ":")
  var fileChosen = readInt()
  while fileChosen < 0 || fileChosen > (filesList.size - 1) do
    println("You put a wrong value. Try again:")
    fileChosen = readInt()

  val file = new File(Paths.get("./src/test/resources/casestudy_random-"+ fileChosen + ".0.txt").toAbsolutePath.toString)
  val separatorFile = ' '
  val commentSymbol = "#"
  val reader = new DoubleCSVFileReader(file, separatorFile, commentSymbol)

  val series = reader.read
  val entriesSize = series.entries(fileChosen)._2.size

  println("which column would you like to plot? Digit a number from 0 to " + entriesSize + ":")
  //val plotter = new DoubleAveragePlot(series)
  var columnChosen = readInt()
  while columnChosen < 0 || columnChosen > entriesSize do
    println("You put a wrong value. Try again:")
    columnChosen = readInt()
  val plotter = new DoubleSpecificColumnPlot(series,columnChosen, (500, 500))

  plotter.show
  plotter.toPNG("./src/test/plotresults/casestudy-" + fileChosen + "-column-" + columnChosen +".png")
}
