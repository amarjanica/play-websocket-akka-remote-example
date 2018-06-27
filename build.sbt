name := "play-websocket-akka-example"

scalaVersion := "2.12.6"

lazy val akkaRemoteApp = project in file("akka-remote")

lazy val playApp = project in file("play")
