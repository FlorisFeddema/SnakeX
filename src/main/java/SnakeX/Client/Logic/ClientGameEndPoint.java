package SnakeX.Client.Logic;


import SnakeX.Model.enums.GameResult;
import SnakeX.Model.enums.MoveDirection;
import SnakeX.Shared.ConsoleColors;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;

import static SnakeX.Shared.Static.keyInJson;

@ClientEndpoint
public class ClientGameEndPoint implements IsClientGameEndPoint {
    private IsControllerClient client;
    private Session server;

    public ClientGameEndPoint(IsControllerClient client, String url, int id){
        this.client = client;

        URI uri = URI.create(url);
        try {

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            server = container.connectToServer(this, uri);
            JsonObject json = new JsonObject();
            json.addProperty("connect", true);
            json.addProperty("id", id);
            server.getBasicRemote().sendText(json.toString());
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
        JsonObject json = new JsonParser().parse(message).getAsJsonObject();
        if (keyInJson(json, "move")){
            move(json);
        }
    }

    @Override
     public void sendDirection(MoveDirection direction){
        JsonObject json = new JsonObject();
        json.addProperty("direction", direction.toString());
         try {
             server.getBasicRemote().sendText(json.toString());
         } catch (IOException e) {
             //ignore
         }
     }

     private void move(JsonObject json){
        MoveDirection playerDirection = MoveDirection.valueOf(json.get("player").getAsString());
        MoveDirection enemyDirection = MoveDirection.valueOf(json.get("enemy").getAsString());
        GameResult result = GameResult.valueOf(json.get("result").getAsString());
        client.move(playerDirection, enemyDirection, result);
     }

}
