package snakex.client.logic;


import snakex.model.enums.GameResult;
import snakex.model.enums.MoveDirection;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;

import static snakex.shared.Static.keyInJson;

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
        } catch (Exception t) {
            //ignore
        }
    }

    @OnError
    public void onWebSocketError(Throwable cause) {
        //ignore
    }


    @OnMessage
    public void onWebSocketText(String message) {
        JsonObject json = new JsonParser().parse(message).getAsJsonObject();
        if (keyInJson(json, "move")){
            move(json);
        } else if (keyInJson(json, "powerUp")){
            spawnPowerUp(json);
        } else if (keyInJson(json, "pickup")){
            pickup(json);
        }
    }

    private void pickup(JsonObject json){
        boolean isPlayer = json.get("pickup").getAsBoolean();
        client.growSnake(isPlayer);
    }

    private void spawnPowerUp(JsonObject json){
        int x = json.get("x").getAsInt();
        int y = json.get("y").getAsInt();
        client.drawPowerUp(x, y);
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

    @Override
    public void disconnect() {
        try {
            server.close();
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
