package SnakeX.Client.Logic;

import SnakeX.Model.enums.MoveDirection;

public interface IsClientGameEndPoint {
    void sendDirection(MoveDirection direction);

    void disconnect();
}
