package snakex.shared;

import snakex.client.ui.Client;
import snakex.gameserver.GameServer;
import snakex.managerserver.ManagerServer;

public class GameSystem {
    public static void main(String[] args) {
        startServices();
    }

    private static void startServices(){

        new Thread(() -> {
            String[] args = {"9900"};
            ManagerServer.main(args);
        }).start();

        new Thread(() -> {
            String[] args = {"9905"};
            GameServer.main(args);
        }).start();

//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//
//        new Thread(() -> Client.main(null)).start();

    }
}
