package snakex.client.logic;

import snakex.model.enums.MoveDirection;

public interface IsClientGameEndPoint {
    void sendDirection(MoveDirection direction);

    void disconnect();
}
