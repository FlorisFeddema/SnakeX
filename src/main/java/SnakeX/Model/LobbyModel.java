package SnakeX.Model;

import SnakeX.ManagerServer.IsRestEndpoint;
import SnakeX.ManagerServer.RestEndPoint;
import SnakeX.Model.enums.PlayerStatus;
import SnakeX.Shared.ConsoleColors;
import com.google.gson.JsonObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class LobbyModel implements IsLobby {
    private IsChat chat;
    private Set<Player> players;
    private IsRestEndpoint rest;

    public LobbyModel(){
        players = new HashSet<>();
        rest = new RestEndPoint();
        chat = new Chat();
    }

    public Player getPlayer(Session session){
        for (Player i : players){
            if (i.getSession().getId() == session.getId()){
                return i;
            }
        }
        return null;
    }

    @Override
    public void addPlayer(Player player) {
        players.add(player);
    }

    @Override
    public void removePlayer(Session session) {
        players.removeIf(p -> p.getSession().getId() == session.getId());
    }

    @Override
    public Player updatePlayer(Session session) {
        //return rest.updatePlayer(getPlayer(session));
        Player player = getPlayer(session);
        player.setWins(5);
        player.setGames(10);
        return player;
    }

    @Override
    public boolean registerPlayer(String username, String password) {
        //TODO FIX
        //return rest.registerPlayer(username,  password);
        return true;
    }

    @Override
    public int loginPlayer(String username, String password, Session session) {
        //TODO FIX
        //int id = rest.loginPlayer(username,  password);
        int id = 555;
        Player player = getPlayer(session);
        player.setId(id);
        player.setName(username);
        return id;
    }

    @Override
    public void receiveMessage(String text, Session session) {
        Message message = new Message(text, getPlayer(session));
        chat.addMessage(message);

        JsonObject json = new JsonObject();
        json.addProperty("message", true);
        json.addProperty("text", message.getText());
        json.addProperty("name", message.getPlayer().getName());
        try {
            System.out.println(ConsoleColors.BLUE + "Manager: Sending it to people");

            sendChatOther(json, message.getPlayer());
        } catch (IOException e) {
            //ignore
        }
    }

    private void sendChatOther(JsonObject message, Player player) throws IOException {
        for (Player i : players){
            if (i.getSession().getId() != player.getSession().getId() && i.getStatus() != PlayerStatus.Playing){
                i.getSession().getBasicRemote().sendText(message.toString());
            }
        }
    }

}
