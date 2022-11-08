package input

import models.TimeSeries

trait SeriesReader[S <: TimeSeries[_, _]] {
  def read: S
}
