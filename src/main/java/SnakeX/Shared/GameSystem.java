package SnakeX.Shared;

import SnakeX.Client.UI.Client;
import SnakeX.GameServer.GameServer;
import SnakeX.ManagerServer.ManagerServer;

public class GameSystem {
    public static void main(String[] args) {
        StartServices();
    }

    private static void StartServices(){

        new Thread(() -> {
            String[] args = {"9900"};
            ManagerServer.main(args);
        }).start();

        new Thread(() -> {
            String[] args = {"9906"};
            GameServer.main(args);
        }).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

        new Thread(() -> Client.main(null)).start();

    }
}
