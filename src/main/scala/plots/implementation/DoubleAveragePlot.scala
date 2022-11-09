package plots.implementation

import models.MultipleSeries
import plots.PlotMultipleSeries

class DoubleAveragePlot(override val data: MultipleSeries[Double, List[Double]]) extends PlotMultipleSeries[Double, Double, List[Double]]{
  override val aggregationStrategy: List[Double] => Double = l => l.sum / l.size
  override val title: String = "Average of points"
}
