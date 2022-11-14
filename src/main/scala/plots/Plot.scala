package plots

import de.sciss.chart.api._
import de.sciss.chart.XYChart

trait Plot {
  val title: String
  val resolution: (Int, Int)

  def plot: XYChart
  def show: Unit = plot.show(title = title, resolution = resolution)

  def toPNG(file: String): Unit = plot.saveAsPNG(file = file, resolution = resolution)
}
