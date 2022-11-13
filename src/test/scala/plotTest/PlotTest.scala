package plotTest

import input.csv.implementation.DoubleCSVFileReader
import models.implementation.ListMultipleSeries
import plots.implementation.{DoubleAveragePlot, DoubleSpecificColumPlot}
import org.junit.Test
import org.junit.Assert.*

import java.io.File
import java.nio.file.Paths

class PlotTest {
  val testValuesList: List[(Double, List[Double])] = List((0,List(1, 2, 3)), (1, List(3, 4, 5)), (3, List(6, 7, 8)), (5, List(9, 10, 11)))
  val testValues = new ListMultipleSeries[Double, Double](testValuesList)
  val testColumn = 2
  val testDirectory = new File("src/test/resources")

  @Test
  def testColumnPlot() = {
    val plotter = new DoubleSpecificColumPlot(testValues,testColumn)
    assertNotNull(plotter)
    assertNotNull(plotter.show)
    plotter.toPNG("./src/test/plotresults/testColumn.png")
    val plotGenerated = new File("./src/test/plotresults/testColumn.png")
    assertTrue(testDirectory.listFiles(_.isFile).contains(plotGenerated))
  }

}
