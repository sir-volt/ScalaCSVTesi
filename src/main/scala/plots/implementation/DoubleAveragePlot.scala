package plots.implementation

import models.MultipleSeries
import plots.PlotMultipleSeries

class DoubleAveragePlot(override val data: MultipleSeries[Double, List[Double]], override val resolution: (Int, Int)) extends PlotMultipleSeries[Double, Double, List[Double]]{
  override val extractionYStrategy: List[Double] => Double = l => l.sum / l.size
  override val title: String = "Average of points"
}
