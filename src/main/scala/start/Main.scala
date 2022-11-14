package start

import input.csv.implementation.DoubleCSVFileReader
import plots.implementation.DoubleSpecificColumnPlot
import readFiles.implementation.ListFiles

import java.io.File
import start.Config
import scopt.*

import java.nio.file.Path

object Main {
  def main(args: Array[String]): Unit = {
    val parser = new OptionParser[Config]("plotCSV") {
      opt[Char]("separator")
        .action((s, c) => c.copy(separator = s))
        .text("this is the separator used in CSV files")
        .required().withFallback(() => ' ')

      opt[String]("commentSymbol")
        .action((s, c) => c.copy(commentSymbol = s))
        .text("this is the symbol beginning comment line in CSV file")
        .required().withFallback(() => "#")

      opt[Int]("width")
        .validate(w => if (w < 0) failure("Width must be positive") else success)
        .action((w, c) => c.copy(width = w))
        .text("this is the width of the plot image")

      opt[Int]("height")
        .validate(h => if (h < 0) failure("Width must be positive") else success)
        .action((h, c) => c.copy(height = h))
        .text("this the height of the plot image")

      opt[Path]("input")
        .validate(f => if (f.toFile.exists) success else failure("Input file does not exist"))
        .action((p, c) => c.copy(input = p))
        .text("this is the path of the input folder or file")
        .required()

      opt[Path]("output")
        .validate(f => if (f.toFile.exists) success else failure("Input file does not exist"))
        .action((o, c) => c.copy(output = o))
        .text("this is the path of the output folder or file")
        .required()

      opt[Int]("column")
        .action((n, c) => c.copy(column = n))
        .text("this is the index of the column inside CSV")
        .required()

      checkConfig(c => {
        if (c.input == null || c.output == null) failure("Invalid input or output") else {
          val fInput = c.input.toFile
          val fOutput = c.output.toFile
          if (fInput.isFile.equals(fOutput.isFile)) success
          else failure("input and output must be both folders or files")
        }
      })
    }
    parser.parse(args, Config()) match {
      case Some(config) => start(config)
      case None => println("this configuration is invalid. Try again")
    }
  }

  def start(config: Config): Unit = {
    def strategy(input: File, output: String) = {
      println(input.toString + " -> " + output)
      val reader = new DoubleCSVFileReader(input, config.separator, config.commentSymbol)
      val series = reader.read
      val w = config.width
      val h = config.height
      val plotter = new DoubleSpecificColumnPlot(series, config.column, (if (w == 0) 480 else w, if (h == 0) 480 else h))
      plotter.toPNG(output)
    }
    if (config.input.toFile.isFile) {
      strategy(config.input.toFile, config.output.toString)
    } else {
      val filesList = new ListFiles(config.input.toString).getListOfFiles
      filesList.foreach(f => strategy(f, config.output.resolve(f.getName + ".png").toString))
    }
  }
}
