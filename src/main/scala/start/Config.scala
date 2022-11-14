package start

import java.io.File
import java.nio.file.Path

case class Config(
  separator: Char = ' ',
  commentSymbol: String = "#",
  width: Int = 0,
  height: Int = 0,
  input: Path = null,
  output: Path = null,
  column: Int = 0)
