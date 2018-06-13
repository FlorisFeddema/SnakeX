package SnakeX.REST;

import SnakeX.Model.Shared.Player;

public interface IsRestEndpoint {
    int loginPlayer(String username, String password);

    boolean registerPlayer(String username, String password);

    Player updatePlayer(Player player);
}
