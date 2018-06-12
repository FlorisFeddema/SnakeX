package SnakeX.Client.Logic;

import SnakeX.Shared.ConsoleColors;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.URI;

public class ClientGameEndPoint {
    private IsClient client;
    private Session server;

    public ClientGameEndPoint(IsClient client){
        this.client = client;

        URI uri = URI.create("ws://localhost:9991/snake/manager/");
        try {

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            //connects to server
            server = container.connectToServer(this, uri);
        } catch (Throwable t) {
        }
    }
}
