package actors

import akka.actor.{Actor, ActorRef, ActorSelection, Props}
import akka.event.Logging

object ProxyActor {
  def props(out: ActorRef, remote: ActorSelection) = Props(new ProxyActor(out, remote))
}

class ProxyActor(out: ActorRef, remote: ActorSelection) extends Actor {
  val log = Logging(context.system, this)

  override def preStart(): Unit = {
    // TODO: test if joined, repeat in case of failure!
    (remote ! "join")(out)
  }

  override def postStop(): Unit = {
    (remote ! "leave")(out)
  }

  override def receive: Receive = {
    case _ =>
  }
}