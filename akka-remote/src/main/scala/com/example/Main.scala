package com.example

import akka.actor.{ActorSystem, Props}

import scala.concurrent.duration._

object Main extends App {
  val system = ActorSystem("ExamplePingerSystem")
  val pingerActor = system.actorOf(Props[PingerActor], "pinger")

  import system.dispatcher
  system.scheduler.schedule(0 seconds, 5 seconds){
    pingerActor ! "ping"
  }
}
