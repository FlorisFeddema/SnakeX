package SnakeX.Model.Game;

import SnakeX.Model.Shared.Snake;
import SnakeX.Model.enums.MoveDirection;

import javax.websocket.Session;

public interface IsGameModel {

    void setPlayer(Snake snake);

    void connectPlayer(int id, Session session);

    void updateDirection(MoveDirection direction, Session session);
}
