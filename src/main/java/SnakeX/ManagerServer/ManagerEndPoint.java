package SnakeX.ManagerServer;

import SnakeX.Model.Manager.IsLobby;
import SnakeX.Model.Manager.LobbyModel;
import SnakeX.Model.Manager.Player;
import SnakeX.Shared.ConsoleColors;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

import static SnakeX.Shared.Static.keyInJson;

@ServerEndpoint(value = "/snake/manager/")
public class ManagerEndPoint  {

    private static IsLobby lobby = new LobbyModel();


    @OnOpen
    public void connect(Session session){

    }

    @OnClose
    public void close(Session session){
        lobby.removePlayer(session);
    }

    @OnError
    public void onError(Throwable cause, Session session) {
        System.out.println(ConsoleColors.BLUE + "Manager: A client got an error");
    }

    @OnMessage
    public void onMessageReceived(String message, Session session) {
        System.out.println(ConsoleColors.BLUE + "Manager: Got a message");
        JsonObject json = new JsonParser().parse(message).getAsJsonObject();
        if (keyInJson(json, "login")) {
            loginPlayer(json, session);
        } else if (keyInJson(json, "register")){
            registerPlayer(json, session);
        } else if (keyInJson(json, "stats")){
            statsPlayer(session);
        } else if (keyInJson(json, "message")){
            receiveMessage(json, session);
        } else if (keyInJson(json, "queue")){
            joinQueue(session);
        } else if (keyInJson(json, "gameserver")){
            registerServer();
        }
    }

    private void registerServer(){

    }

    private void joinQueue(Session session) {
        lobby.joinQueue(session);
    }

    private void receiveMessage(JsonObject json, Session session) {
        String message = json.get("text").getAsString();

        lobby.receiveMessage(message, session);
    }

    private void statsPlayer(Session session) {
        Player player = lobby.updatePlayer(session);
        JsonObject json = new JsonObject();
        json.addProperty("stats", true);
        json.addProperty("games", player.getGames());
        json.addProperty("wins", player.getWins());

        try {
            session.getBasicRemote().sendText(json.toString());
        } catch (IOException e) {
            //ignore
        }
    }

    private void registerPlayer(JsonObject json,  Session session){
        String username = json.get("username").getAsString();
        String password = json.get("password").getAsString();

        boolean success = lobby.registerPlayer(username, password);

        json = new JsonObject();
        json.addProperty("register", success);

        try {
            session.getBasicRemote().sendText(json.toString());
        } catch (IOException e) {
            //ignore
        }
    }

    private void loginPlayer(JsonObject json, Session session){
        String username = json.get("username").getAsString();
        String password = json.get("password").getAsString();

        int id = lobby.loginPlayer(username, password, session);

        json = new JsonObject();
        json.addProperty("login", true);
        json.addProperty("id", id);
        try {
            session.getBasicRemote().sendText(json.toString());
        } catch (IOException e) {
            //ignore
        }
    }

}
