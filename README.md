# play-websocket-akka-remote-example
> Minimal example that shows how Play websocket communication can be done with remote actors, and how to separate
Play and Akka services.

## Run it
- In 2 separate terminals start `akka` and `play` by running:
```bash
   sbt "project playApp" run
```
and  
```bash
   sbt "project akkaRemoteApp" run
```
Go to <http://localhost:9000>, you should see server notifications from akka.  
Server notifications in browser are received with the help of [Websockets](https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API).  
```javascript
    var exampleSocket = new WebSocket("ws://" + window.location.host + "/ws");

    exampleSocket.onmessage = function (event) {
        console.log("Event from server", event);
        document.getElementById("eventMessage").textContent = event.data;
    }

```