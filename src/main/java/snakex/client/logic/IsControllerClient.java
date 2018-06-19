package snakex.client.logic;

import snakex.model.enums.GameResult;
import snakex.model.enums.MoveDirection;

public interface IsControllerClient {
    /***
     * sets the wins
     * @param wins amount of wins
     */
    void setWins(int wins);

    /***
     * sets the games
     * @param games amount of games
     */
    void setGames(int games);

    /***
     * shows message from another player
     * @param message text of the message
     * @param name name of the sender
     */
    void showMessageOther(String message, String name);

    /***
     * joins a game
     * @param url url of the gameserver
     * @param enemy name of the enemy
     * @param enemyRating rating of the enemy
     * @param rating your rating
     * @param xPlayer x spawn player
     * @param yPlayer y spawn player
     * @param xEnemy x spawn enemy
     * @param yEnemy y spawn enemy
     * @param length length of the snake
     */
    void joinGame(String url, String enemy, int enemyRating, int rating, int xPlayer, int yPlayer, int xEnemy, int yEnemy, int length);

    /***
     * moves the snakes in the direction on the server, gives also the result of the moves
     * @param playerDirection direction of the client player
     * @param enemyDirection direction of the client enemy
     * @param result result of the moves, for the player
     */
    void move(MoveDirection playerDirection, MoveDirection enemyDirection, GameResult result);
}