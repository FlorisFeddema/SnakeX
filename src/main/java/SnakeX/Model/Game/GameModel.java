package SnakeX.Model.Game;

import SnakeX.GameServer.GameManagerEndPoint;
import SnakeX.GameServer.IsGameManagerEndPoint;

public class GameModel implements IsGameModel {

    private IsGameManagerEndPoint manager;

    public GameModel(String url) {
        manager = new GameManagerEndPoint(url);
    }
}
