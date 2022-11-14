package input.csv

import com.github.tototoshi.csv.*
import input.SeriesReader
import models.MultipleSeries
import models.implementation.ListMultipleSeries

import java.io.File


trait CSVFileReader[X : Ordering, Y] extends SeriesReader[MultipleSeries[X, List[Y]]] {

  val file: File
  val stringToX: String => X
  val stringToY: String => Y
  val separator: Char
  val commentSymbol: String


  override def read: MultipleSeries[X, List[Y]] = {
    given customFormat: DefaultCSVFormat with
      override val delimiter: Char = separator
      override val treatEmptyLineAsNil: Boolean = true

    new ListMultipleSeries[X, Y](CSVReader.open(file).all()
      .filter(l => l.nonEmpty)
      .filter(l => !l.head.startsWith(commentSymbol))
      .map(l => l.filter(s => s.nonEmpty))
      .map(l => (stringToX(l.head), l.tail.map(stringToY))))
  }
}
