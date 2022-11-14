package plots.implementation

import models.MultipleSeries
import plots.PlotMultipleSeries

class DoubleSpecificColumnPlot(override val data: MultipleSeries[Double, List[Double]], val column: Int) extends PlotMultipleSeries[Double, Double, List[Double]] {
  override val extractionYStrategy: List[Double] => Double = l => l(column)
  override val title: String = "Values of column No." + column + " in Time series"
}
