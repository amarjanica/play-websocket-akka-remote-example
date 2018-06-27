package com.example

import akka.actor.{Actor, ActorRef, Terminated}
import akka.event.Logging

class PingerActor() extends Actor {

  val log = Logging(context.system, this)

  override def preStart() = {
    log.debug("Starting")
  }

  override def preRestart(reason: Throwable, message: Option[Any]) {
    log.error(
      reason, "Restarting due to [{}] when processing [{}]",
      reason.getMessage, message.getOrElse("")
    )
  }

  def receive = active(Set.empty, 1)

  def active(isInSet: Set[ActorRef], start: Long): Receive = {
    case "join" =>
      log.info(s"Joining to updates...${isInSet.size}")
      context become active(isInSet + sender(), start)
      context watch sender()
      sender() ! "joined"

    case "leave" =>
      val newIsInSet = isInSet - sender()
      log.info(s"Unsubscribe request received. Old: {}, new: {}", isInSet.size, newIsInSet.size)
      context become active(newIsInSet, start)

    case "ping" =>
      log.info(s"ping number $start!")
      self ! s"ping number $start!"
      context become active(isInSet, start + 1)

    case out: String =>
      log.info("Notifying {} actors of {}", isInSet.size, out)
      isInSet.foreach(_ ! out)

    case Terminated(subscriber) =>
      (self ! "leave")(subscriber)
  }

}
