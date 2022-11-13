package plotTest

import input.csv.implementation.DoubleCSVFileReader
import plots.implementation.DoubleSpecificColumPlot

import org.junit.Test
import org.junit.Assert.*
import java.io.File
import java.nio.file.Paths

class PlotTest {
  val file = new File(Paths.get("./src/test/resources/casestudy_random-0.0.txt").toAbsolutePath.toString)
  val separatorFile = ' '
  val commentSymbol = "#"
  val reader = new DoubleCSVFileReader(file, separatorFile, commentSymbol)
  val series = reader.read
  val caseStudy0 = new File("./src/test/plotresults/casestudy-0-column-7.png")


  @Test
  def testPlot() = {
    val plotter = new DoubleSpecificColumPlot(series,7)
    assertNotNull(plotter)
    assertNotNull(plotter.show)
    assertNotNull(plotter.toPNG("./src/test/plotresults/casestudy-0-column-7.png"))
  }


}
