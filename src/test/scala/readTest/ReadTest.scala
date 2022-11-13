package readTest

import input.csv.implementation.DoubleCSVFileReader
import org.junit.Test
import org.junit.Assert.*

import java.io.File
import java.nio.file.Paths

class ReadTest {
  val expectedTimeSeries: List[Double] = (BigDecimal(0.0) to BigDecimal(300.0) by 1).toList.map(l => l.doubleValue)
  val expectedLineValues: List[Double] = List.fill(15)(0.0)
  val expectedColumnValues: List[Double] = List(0.0, 107.0, 266.0, 276.0, 276.0, 276.0, 276.0, 276.0,
    275.0, 275.0, 275.0, 275.0, 275.0, 276.0, 276.0, 276.0, 276.0, 276.0, 276.0, 276.0, 276.0, 275.0,
    275.0, 276.0, 275.0, 276.0, 276.0, 276.0, 276.0, 276.0, 276.0, 276.0, 277.0, 277.0, 278.0, 277.0,
    276.0, 276.0, 277.0, 277.0, 277.0, 277.0, 277.0, 275.0, 275.0, 277.0, 276.0, 276.0, 275.0, 275.0,
    276.0, 275.0, 274.0, 274.0, 274.0, 275.0, 275.0, 275.0, 276.0, 275.0, 274.0, 276.0, 278.0, 276.0,
    277.0, 276.0, 277.0, 277.0, 277.0, 276.0, 277.0, 278.0, 277.0, 277.0, 277.0, 277.0, 276.0, 276.0,
    275.0, 275.0, 276.0, 276.0, 275.0, 277.0, 276.0, 276.0, 277.0, 277.0, 277.0, 277.0, 276.0, 276.0,
    278.0, 276.0, 278.0, 276.0, 276.0, 276.0, 276.0, 276.0, 276.0, 277.0, 276.0, 277.0, 278.0, 278.0,
    278.0, 277.0, 276.0, 277.0, 276.0, 276.0, 275.0, 275.0, 275.0, 275.0, 275.0, 275.0, 275.0, 276.0,
    275.0, 275.0, 276.0, 277.0, 277.0, 275.0, 276.0, 277.0, 277.0, 277.0, 276.0, 278.0, 276.0, 276.0,
    276.0, 276.0, 276.0, 276.0, 276.0, 276.0, 274.0, 274.0, 275.0, 276.0, 275.0, 276.0, 278.0, 277.0,
    278.0, 278.0, 278.0, 278.0, 278.0, 279.0, 278.0, 278.0, 278.0, 279.0, 279.0, 279.0, 279.0, 279.0,
    279.0, 277.0, 278.0, 278.0, 278.0, 278.0, 278.0, 278.0, 278.0, 278.0, 278.0, 278.0, 278.0, 278.0,
    279.0, 279.0, 279.0, 279.0, 279.0, 279.0, 279.0, 279.0, 279.0, 279.0, 278.0, 278.0, 278.0, 278.0,
    278.0, 278.0, 278.0, 278.0, 279.0, 278.0, 278.0, 278.0, 278.0, 278.0, 279.0, 278.0, 278.0, 278.0,
    278.0, 278.0, 278.0, 278.0, 278.0, 278.0, 279.0, 279.0, 280.0, 280.0, 279.0, 279.0, 280.0, 280.0,
    280.0, 280.0, 280.0, 280.0, 280.0, 280.0, 280.0, 279.0, 279.0, 280.0, 280.0, 280.0, 280.0, 280.0,
    280.0, 280.0, 280.0, 279.0, 279.0, 279.0, 279.0, 279.0, 280.0, 280.0, 279.0, 279.0, 279.0, 279.0,
    280.0, 280.0, 280.0, 280.0, 280.0, 280.0, 280.0, 280.0, 280.0, 280.0, 280.0, 280.0, 280.0, 280.0,
    280.0, 280.0, 280.0, 280.0, 280.0, 280.0, 280.0, 280.0, 279.0, 280.0, 278.0, 278.0, 278.0, 279.0,
    279.0, 281.0, 280.0, 281.0, 280.0, 280.0, 280.0, 280.0, 280.0, 280.0, 280.0, 280.0, 280.0, 280.0,
    280.0, 280.0, 280.0, 280.0, 280.0, 280.0, 279.0, 280.0, 280.0, 280.0, 280.0, 280.0, 278.0)
  val file = new File(Paths.get("./src/test/resources/casestudy_random-0.0.txt").toAbsolutePath.toString)
  val separatorFile = ' '
  val commentSymbol = "#"
  val reader = new DoubleCSVFileReader(file, separatorFile, commentSymbol)
  val series = reader.read

  @Test
  def testTimeSeries() = {
    val timeSeries = series.time
    assertEquals(expectedTimeSeries.size, timeSeries.size)
    assertTrue(timeSeries.exists(time => expectedTimeSeries.contains(time)))
  }

  @Test
  def testLineValues() = {
    val lineValues = series.getY(0)
    assertEquals(expectedLineValues.size, lineValues.size)
    assertTrue(lineValues.exists(value => expectedLineValues.contains(value)))
  }

  @Test
  def testColumnValues() = {
    val columnValues = series.time.map(time => series.getY(time)(6))
    assertEquals(expectedColumnValues.size, columnValues.size)
    assertTrue(columnValues.exists(value => expectedLineValues.contains(value)))
  }
}
