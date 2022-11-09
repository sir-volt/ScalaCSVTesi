package start

import input.csv.implementation.DoubleCSVFileReader
import de.sciss.chart.api.*
import plots.implementation.DoubleAveragePlot

import java.io.File
import java.nio.file.Paths

object Main extends App {
  val file = new File(Paths.get("./src/test/resources/casestudy_random-0.0.txt").toAbsolutePath.toString)
  val separatorFile = ' '
  val commentSymbol = "#"
  val reader = new DoubleCSVFileReader(file, separatorFile, commentSymbol)

  val series = reader.read

  val plotter = new DoubleAveragePlot(series)

  /*crea un object con il @main che legge ciclo di file da riga di comando usando la pagina trovata
  * https://docs.scala-lang.org/scala3/book/methods-main-methods.html parte Command Lines Arguments.
  * chiamo il metodo come voglio, deve prendere la directory, i file, gli indexer,ecc... legge da cartella,
  * cicla sui file, leggi i file, plotta e crea i PNG,ecc*/
  plotter.show
}
