package snakex.client.logic;

import snakex.client.ui.IsGameController;
import snakex.client.ui.IsMainController;
import snakex.model.shared.Point;
import snakex.model.shared.Snake;
import snakex.model.enums.MoveDirection;

import java.io.IOException;

public interface IsClient {

    /***
     * gets the snake of the player on the client
     * @return snake object
     */
    Snake getPlayer();

    /***
     * gets the snake of the enemy on the client
     * @return snake object
     */
    Snake getEnemy();

    /***
     * gets the wins of the player
     * @return amount of wins
     */
    int getWins();

    /***
     * gets the played games of the player
     * @return amount of games played
     */
    int getGames();

    /***
     * sets the main controller in the client
     * @param controller IsMainController object
     */
    void setMainController(IsMainController controller);

    /***
     * sets the game controller in the client
     * @param controller IsGameController object
     */
    void setGameController(IsGameController controller);

    /***
     * logs the player in using the server connection
     * @param name name of the player
     * @param hash hash of the player password
     * @return id when correct, integer minvalue when incorrect
     * @throws IOException when server is not reached
     * @throws InterruptedException when server is not reacting to request, or delay is to long
     */
    int loginPlayer(String name, String hash) throws IOException, InterruptedException;

    /***
     * registers a new player in the system using the server connection
     * @param name name of the player
     * @param hash hash of the player password
     * @return true if registerd, false if name already taken
     * @throws IOException when server is not reached
     * @throws InterruptedException when server is not reacting to request, or delay is to long
     */
    boolean registerPlayer(String name, String hash) throws IOException, InterruptedException;

    /***
     * updates the stats of the player
     * @throws IOException when server is not reached
     * @throws InterruptedException when server is not reacting to request, or delay is to long
     */
    void updatePlayerStats() throws IOException, InterruptedException;

    /***
     * send message to other players using the server connection
     * @param message text of the message
     * @throws IOException when server is not reached
     */
    void sendMessagePlayer(String message) throws IOException;

    /***
     * players joins the queue on the server
     * @throws IOException when server is not reached
     */
    void joinQueue() throws IOException;

    /***
     * updates the direction of the snake for the next move
     * @param direction direction to face
     */
    void move(MoveDirection direction);

    /***
     * connects to a game with connection
     */
    void connectGame();

    /***
     * gets the point of the powerup
     * @return powerup
     */
    Point getPowerUp();
}
