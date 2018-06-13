package SnakeX.Client.Logic;

import SnakeX.Shared.ConsoleColors;

import javax.websocket.*;
import java.net.URI;

@ClientEndpoint
public class ClientGameEndPoint implements IsClientGameEndPoint {
    private IsControllerClient client;
    private Session server;

    public ClientGameEndPoint(IsControllerClient client, String url){
        this.client = client;

        URI uri = URI.create(url);
        try {

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            server = container.connectToServer(this, uri);
        } catch (Throwable t) {
        }
    }

    @OnError
    public void onWebSocketError(Throwable cause) {
        System.out.println(ConsoleColors.GREEN + "Client: We got an error");
        cause.printStackTrace(System.err);
    }


    @OnMessage
    public void onWebSocketText(String message) {
    }

}
