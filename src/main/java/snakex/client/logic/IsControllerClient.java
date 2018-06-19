package snakex.client.logic;

import snakex.model.enums.GameResult;
import snakex.model.enums.MoveDirection;
import snakex.model.shared.Point;

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
     * @param enemyName name of the enemy
     * @param enemyRating rating of the enemy
     * @param rating your rating
     * @param player position of player
     * @param enemy position of enemy
     * @param length length of the snake
     */
    void joinGame(String url, String enemyName, int enemyRating, int rating, Point player, Point enemy, int length);

    /***
     * moves the snakes in the direction on the server, gives also the result of the moves
     * @param playerDirection direction of the client player
     * @param enemyDirection direction of the client enemy
     * @param result result of the moves, for the player
     */
    void move(MoveDirection playerDirection, MoveDirection enemyDirection, GameResult result);

    /***
     * sets the powerup at a point
     * @param x x coordinate
     * @param y y coordinate
     */
    void drawPowerUp(int x, int y);

    /***
     * grows a snake
     * @param isPlayer is player or enemy
     */
    void growSnake(boolean isPlayer);
}