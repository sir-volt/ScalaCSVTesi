# Un Framework per la graficazione di dati in Scala

This frameworks/support library has the objective to elaborate data from files
and generate a series of graphics respectively.
It uses [Scala-CSV](https://github.com/tototoshi/scala-csv)
in order to read files and extract data from each of them, while
using [Scala-Chart](https://github.com/Sciss/scala-chart) to generate,
animate and save graphics after studying said data.
Pieces of codes and functionalities will be shown here.
## Reading Files
ReadFiles is a trait that contains a val `dir` string which indicates the directory
that has the files that we want to read. This is done with
a method called `getListOfFiles`:
```
  def getListOfFiles: List[File] = {
    val d = new File(dir)
    if (d.exists && d.isDirectory) {
      d.listFiles.filter(_.isFile).filter(file => file.getName.endsWith(".txt") || file.getName.endsWith(".csv")).toList
    } else {
      List[File]()
    }
  }
```
It returns a `List[File]` that can then be chosen in the `Main`
class by specifying one number in terminal. The value of `dir` is overriden
with the implementation of `ReadFiles` trait called `ListFiles`:
```
  val filesList = new ListFiles("src/test/resources").getListOfFiles
  println("choose which file you want to study from 0 to " + (filesList.size - 1) + ":")
  var fileChosen = readInt()
  while fileChosen < 0 || fileChosen > (filesList.size - 1) do
    println("You put a wrong value. Try again:")
    fileChosen = readInt()
```
When a file is chosen, it's then called an implementation of `CSVFileReader` which is a trait
that extends the main trait `SeriesReader`. `SeriesReader` defines the contracts for method `read`
which takes as its input a new type called `TimeSeries`. `TimeSeries` contains an object
extension to define each entry contained in a .csv file. Each line in .csv file is considered
as an entry, with the first value corresponding to X generic coordinate value (time)
while the second one is defined
as Y generic coordinate value:
```
object TimeSeries {
  extension [T, Y](s: TimeSeries[T, Y]) {
    def entries: LinearSeq[(T, Y)] =
      for t <- s.time yield (t, s.getY(t))
  }
}
```
the main methods in the actual `TimeSeries` trait are `getY`, which returns
Y value given a specific time, and `time`, which returns a list of all time T
values saved.
```
trait TimeSeries[T, Y] {

  def getY(time: T): Y

  def time: LinearSeq[T]
}
```
This trait is then extended with another one called `MultipleSeries`, that specifies that the Y generic
values as a `LinearSeq` of values:
```
trait MultipleSeries[T, L <: LinearSeq[_]] extends TimeSeries[T, L]
```
`ListMultipleSeris` is then the class that implements and extends `MultipleSeries` trait
using X generics that must be part of `Ordering` type. It takes as input `lines: List[(X, List[Y])]`
which then are put inside an immutable `ListMap[X, List[Y]]`. It finally implements
`time` by taking the sorted set of keys in ListMap and `getY` by simply using get method
of ListMap:
```
  private val mapValues: ListMap[X, List[Y]] = ListMap(lines*)
  private val sortedTimes: List[X] = this.mapValues.keys.toList.sorted

  override def getY(time: X): List[Y] = this.mapValues(time)

  override def time: LinearSeq[X] = this.sortedTimes
```
`CSVFileReader` implements `read` method of `SeriesReader` using `scala-CSV` made by
tototoshi and the main method `CSVReader.open(file)`. It at first establishes
a custom format specifying a `separator` Char to distinguish how
each value in .csv file is separated. With method `open(file).all()` a
`List[List[String]]` is generated containing file values, which are filtered so that empty lines
or lines that starts with specific `commentSymbol` String are not considered. 
It then maps the values contained in each line of file by
converting the first value of each line into an X generic value
required with `stringToX` and mapping the rest of the values into a new `List[Y]` (converting
Strings into Y generic required with `stringToY`). By doing so, we return a new `MultipleSeries[X, List[Y]]`
```
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
```
the values of `file,stringToX,stringToY,separator,commentSymbol` are defined with the
scala class `DoubleCSVFileReader`, which extends `CSVFileReader`, that turns String values into
Doubles:
```
override val stringToX: String => Double = s => s.toDouble
override val stringToY: String => Double = stringToX
```
## Create a graphic with data values
`Plot` functions by using `Scala-Chart` library to plot input values.
it contains contracts for `plot` method (which returns `XYChart` as value) and defines `show` and `toPNG` methods:
the first one calls for `show` method contained in `Scala-Chart` library;
the second one utilizes `saveAsPNG` method:
```
trait Plot {
  val title: String

  def plot: XYChart
  
  def show: Unit = plot.show(title)

  def toPNG(file: String): Unit = plot.saveAsPNG(file)
}
```
`plot` method is finalized with `PlotMultipleSeries[X : Numeric, Y : Numeric, L <: LinearSeq[Y]]` trait that extends `Plot` trait.
It takes a `MultipleSeries[X, L]` dataset, choosing (x, y) tuple values based on an `aggregationStrategy` that takes L list values
and turning it into one single Y value:
```
  val data: MultipleSeries[X, L]
  val aggregationStrategy: L => Y

    override def plot: XYChart = {
        val d = for((x, y) <- data.entries) yield (x, aggregationStrategy(y))
        given datasetConverter: ToXYDataset[LinearSeq[(X, Y)]] = ToIntervalXYDataset.FromTuple2s
        XYLineChart(d)
    }
```
The final class that we use in `Main` is the actual implementation
of `PlotMultipleSeries` called `DoubleSpecificColumn`. It takes as parameters
the column of values that the user wants to plot, and it overrides `val data` as parameter
input. It also overrides `val aggregationStrategy` and `val title`:
```
class DoubleSpecificColumPlot(override val data: MultipleSeries[Double, List[Double]], val column: Int) extends PlotMultipleSeries[Double, Double, List[Double]] {
  override val aggregationStrategy: List[Double] => Double = l => l(column)
  override val title: String = "Values of column No." + column + " in Time series"
}
```