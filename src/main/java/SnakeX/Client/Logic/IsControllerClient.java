package SnakeX.Client.Logic;

public interface IsControllerClient {
    void setWins(int wins);

    void setGames(int games);

    void showMessageOther(String message, String name);

    void joinGame(String url, String enemyName, int enemyRating, int playerRating);
}