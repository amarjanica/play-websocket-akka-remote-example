package controllers

import actors.ProxyActor
import play.api.mvc._
import play.api.libs.streams.ActorFlow
import javax.inject.Inject
import akka.actor.ActorSystem
import akka.stream.Materializer

class Application @Inject()(cc:ControllerComponents) (implicit system: ActorSystem, mat: Materializer) extends AbstractController(cc) {
  val remotePath =
    "akka.tcp://ExamplePingerSystem@127.0.0.1:2552/user/pinger"
  val remoteActor = system.actorSelection(remotePath)

  def ws = WebSocket.accept[String, String] { _ =>
    ActorFlow.actorRef(out => ProxyActor.props(out, remoteActor))
  }

  def index() = Action {
    Ok(views.html.index())
  }
}