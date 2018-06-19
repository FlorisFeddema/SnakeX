package snakex.model.game;

import snakex.model.shared.Snake;
import snakex.model.enums.MoveDirection;

import javax.websocket.Session;

public interface IsGameModel {

    /***
     * sets the player snake
     * @param snake player snake
     */
    void setPlayer(Snake snake);

    /***
     * connects a player to a snake object
     * @param id id of the player
     * @param session connected session of the player
     */
    void connectPlayer(int id, Session session);

    /***
     * updates the direction of the player
     * @param direction direction of the player
     * @param session session of the player
     */
    void updateDirection(MoveDirection direction, Session session);
}
