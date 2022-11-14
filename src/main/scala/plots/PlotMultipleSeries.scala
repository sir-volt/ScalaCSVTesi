package plots

import models.MultipleSeries
import de.sciss.chart.api._
import de.sciss.chart.XYChart
import scala.collection.immutable.LinearSeq

trait PlotMultipleSeries[X : Numeric, Y : Numeric, L <: LinearSeq[Y]] extends Plot {
  val data: MultipleSeries[X, L]
  val extractionYStrategy: L => Y

  override def plot: XYChart = {
    val d = for((x, y) <- data.entries) yield (x, extractionYStrategy(y))
    given datasetConverter: ToXYDataset[LinearSeq[(X, Y)]] = ToIntervalXYDataset.FromTuple2s
    XYLineChart(d)
  }
}
