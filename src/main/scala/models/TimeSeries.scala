package models

import scala.collection.immutable.LinearSeq

trait TimeSeries[T, Y] {

  def getY(time: T): Y

  def time: LinearSeq[T]
}

object TimeSeries {
  extension [T, Y](s: TimeSeries[T, Y]) {
    def entries: LinearSeq[(T, Y)] =
      for t <- s.time yield (t, s.getY(t))
  }
}
