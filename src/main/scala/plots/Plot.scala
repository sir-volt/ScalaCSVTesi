package plots

import de.sciss.chart.api._
import de.sciss.chart.XYChart

trait Plot {
  val title: String

  def plot: XYChart

  def show: Unit = plot.show(title)

  def toPNG(file: String, resolution: (Int, Int)): Unit = plot.saveAsPNG(file, resolution)
}