package SnakeX.Shared;

import SnakeX.Client.UI.Client;
import SnakeX.GameServer.GameServer;
import SnakeX.ManagerServer.ManagerServer;

public class GameSystem {
    public static void main(String[] args) {
        StartServices();
    }

    private static void StartServices(){

        new Thread() {
            @Override
            public void run() {
                String[] args = {"9900"};
                ManagerServer.main(args);
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                String[] args = {"9991"};
                GameServer.main(args);
            }
        }.start();

        System.out.println(ConsoleColors.CYAN + "Server is starting");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

        new Thread() {
            @Override
            public void run() {
                Client.main(null);
            }
        }.start();

    }
}
