package models

import scala.collection.immutable.LinearSeq

trait MultipleSeries[T, L <: LinearSeq[_]] extends TimeSeries[T, L]
