package snakex.model.game;

import snakex.model.shared.Snake;
import snakex.model.enums.MoveDirection;

import javax.websocket.Session;

public interface IsGameModel {

    void setPlayer(Snake snake);

    void connectPlayer(int id, Session session);

    void updateDirection(MoveDirection direction, Session session);
}
