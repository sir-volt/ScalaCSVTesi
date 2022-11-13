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



  /*devo implementare nel main CSVFileReader senza Y ma con un valore di Y che scelgo mettendo un Product a mia scelta
  * ES: Tupla di 3-4-5 colonne. In base a quante colonne ci sono nel CSV*/
  /* la classe che implementa ha il metodo read che restituisce un MultipleSeries di Double,Tuple(...)
  * La strategy che passo alla classe DoubleMultipleSeries trasformerà i valori che sono nelle tail in delle tuple di Double*/
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
