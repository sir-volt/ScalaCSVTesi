package input.csv.implementation

import input.csv.CSVFileReader

import java.io.File

class DoubleCSVFileReader(
                           override val file: File,
                           override val separator: Char,
                           override val commentSymbol: String) extends CSVFileReader[Double, Double] {
  override val stringToX: String => Double = s => s.toDouble
  override val stringToY: String => Double = stringToX
}
