package SnakeX.Client.Logic;

import SnakeX.Client.UI.IsMainController;

import java.io.IOException;

public interface IsClient {

    int getWins();

    int getGames();

    void setMainController(IsMainController controller);

    int loginPlayer(String name, String hash) throws IOException, InterruptedException;

    boolean registerPlayer(String name, String hash) throws IOException, InterruptedException;

    void updatePlayerStats() throws IOException, InterruptedException;

    void sendMessagePlayer(String message) throws IOException;

    void joinQueue() throws IOException;
}
