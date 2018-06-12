package SnakeX.ManagerServer;

import SnakeX.Model.Player;

public interface IsRestEndpoint {
    int loginPlayer(String username, String password);

    boolean registerPlayer(String username, String password);

    Player updatePlayer(Player player);
}
