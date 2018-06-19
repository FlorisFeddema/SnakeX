package snakex.client.logic;

import snakex.model.enums.MoveDirection;

public interface IsClientGameEndPoint {
    /***
     * sends direction to the server
     * @param direction direction to face
     */
    void sendDirection(MoveDirection direction);

    /***
     * disconnects from the server
     */
    void disconnect();
}
