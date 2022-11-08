package input.csv

import models.MultipleSeries

import scala.collection.immutable.{LinearSeq, ListMap}

class ListMultipleSeries[X : Ordering, Y] (lines: List[(X, List[Y])]) extends MultipleSeries[X, List[Y]]{

  private val mapValues: ListMap[X, List[Y]] = ListMap(lines*)
  private val sortedTimes: List[X] = this.mapValues.keys.toList.sorted

  override def getY(time: X): List[Y] = this.mapValues(time)

  override def time: LinearSeq[X] = this.sortedTimes
}
