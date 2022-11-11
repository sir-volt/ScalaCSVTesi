package readFiles

import java.io.File

trait ReadFiles {
  val dir: String

  def getListOfFiles: List[File] = {
    val d = new File(dir)
    if (d.exists && d.isDirectory) {
      d.listFiles.filter(_.isFile).filter(file => file.getName.endsWith(".txt") || file.getName.endsWith(".csv")).toList
    } else {
      List[File]()
    }
  }
}
