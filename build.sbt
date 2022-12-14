ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.1.3"

lazy val root = (project in file("."))
  .settings(
    name := "ScalaCSVTesi",
    libraryDependencies += "com.github.sbt" % "junit-interface" % "0.13.3" % Test,
    libraryDependencies += "com.github.tototoshi" %% "scala-csv" % "1.3.10",
    libraryDependencies += "de.sciss" %% "scala-chart" % "0.8.0",
    libraryDependencies += "com.github.scopt" %% "scopt" % "4.1.0"
  )

Compile / mainClass := Some("start.Main")

//addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.6")
