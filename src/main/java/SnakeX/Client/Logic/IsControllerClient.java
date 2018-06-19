package SnakeX.Client.Logic;

import SnakeX.Model.enums.MoveDirection;

public interface IsControllerClient {
    void setWins(int wins);

    void setGames(int games);

    void showMessageOther(String message, String name);

    void joinGame(String url, String enemy, int enemyRating, int rating, int xPlayer, int yPlayer, int xEnemy, int yEnemy, int length);

    void move(MoveDirection playerDirection, MoveDirection enemyDirection, boolean playerAlive, boolean enemyAlive);
}