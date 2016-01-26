name := "RxPath"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= {
  Seq(
    "org.scala-lang.modules" %%  "scala-swing"   % "1.0.2",
    "io.reactivex" %% "rxscala" % "0.25.0"
  )
}
