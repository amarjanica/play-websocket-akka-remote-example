name := "play-sample"

scalaVersion := "2.12.6"

lazy val playApp = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies += guice
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.11",
  "com.typesafe.akka" %% "akka-remote" % "2.5.11"
)
