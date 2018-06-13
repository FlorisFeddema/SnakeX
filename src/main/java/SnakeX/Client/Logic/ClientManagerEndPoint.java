package SnakeX.Client.Logic;

import SnakeX.Shared.ConsoleColors;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

import static SnakeX.Shared.Static.keyInJson;

@ClientEndpoint
public class ClientManagerEndPoint implements IsClientManagerEndPoint {

    private IsControllerClient client;
    private Session server;

    private SynchronousQueue<Integer> playerId;
    private SynchronousQueue<Boolean> register;
    private SynchronousQueue<JsonObject> stats;

    public ClientManagerEndPoint(IsControllerClient client) {
        playerId = new SynchronousQueue();
        register = new SynchronousQueue();
        stats = new SynchronousQueue();

        this.client = client;

        URI uri = URI.create("ws://localhost:9900/snake/manager/");
        try {

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            //connects to server
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
        System.out.println(ConsoleColors.GREEN + "Client: Got a message");
        JsonObject json = new JsonParser().parse(message).getAsJsonObject();
        if (keyInJson(json, "login")) {
            playerId.offer(json.get("id").getAsInt());
        } else if (keyInJson(json, "register")){
            register.offer(json.get("register").getAsBoolean());
        } else if (keyInJson(json, "stats")){
            stats.offer(json);
        } else if (keyInJson(json, "message")){
            receiveMessageOther(json);
        } else if (keyInJson(json, "gameserver")){
            joinGame(json);
        }
    }


    private void joinGame(JsonObject json){
        String url = json.get("url").getAsString();
        System.out.println(ConsoleColors.GREEN + "Client: " + url);
    }

    @Override
    public int loginPlayer(String name, String hash) throws IOException, InterruptedException {
        JsonObject json = new JsonObject();
        json.addProperty("login", true);
        json.addProperty("username", name);
        json.addProperty("password", hash);
        server.getBasicRemote().sendText(json.toString());
        return playerId.poll(5, TimeUnit.SECONDS);
    }

    @Override
    public boolean registerPlayer(String name, String hash) throws IOException, InterruptedException {
        JsonObject json = new JsonObject();
        json.addProperty("register", true);
        json.addProperty("username", name);
        json.addProperty("password", hash);
        server.getBasicRemote().sendText(json.toString());
        return register.poll(5, TimeUnit.SECONDS);
    }

    @Override
    public void updatePlayer() throws IOException, InterruptedException {
        JsonObject json = new JsonObject();
        json.addProperty("stats", true);

        server.getBasicRemote().sendText(json.toString());
        JsonObject result = stats.poll(5, TimeUnit.SECONDS);
        client.setGames(result.get("games").getAsInt());
        client.setWins(result.get("wins").getAsInt());
    }

    @Override
    public void sendMessagePlayer(String message) throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("message", true);
        json.addProperty("text", message);
        server.getBasicRemote().sendText(json.toString());
    }

    @Override
    public void joinQueue() throws IOException{
        JsonObject json = new JsonObject();
        json.addProperty("queue", true);
        server.getBasicRemote().sendText(json.toString());
    }

    private void receiveMessageOther(JsonObject json){
        String name = json.get("name").getAsString();
        String message = json.get("text").getAsString();
        client.showMessageOther(name, message);
    }
}
