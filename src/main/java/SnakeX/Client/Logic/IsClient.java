package SnakeX.Client.Logic;

import SnakeX.Client.UI.IsGameController;
import SnakeX.Client.UI.IsMainController;
import SnakeX.Model.enums.MoveDirection;

import java.io.IOException;

public interface IsClient {

    Snake getPlayer();

    Snake getEnemy();

    int getWins();

    int getGames();

    void setMainController(IsMainController controller);

    void setGameController(IsGameController controller);

    int loginPlayer(String name, String hash) throws IOException, InterruptedException;

    boolean registerPlayer(String name, String hash) throws IOException, InterruptedException;

    void updatePlayerStats() throws IOException, InterruptedException;

    void sendMessagePlayer(String message) throws IOException;

    void joinQueue() throws IOException;

    void move(MoveDirection direction);
}
