package snakex.model.game;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import snakex.model.enums.MoveDirection;
import snakex.model.manager.Player;
import snakex.model.shared.Snake;
import snakex.rest.GameRestEndPoint;
import snakex.rest.IsGameRestEndPoint;

import javax.websocket.Session;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameModelTest {

    GameModel game;
    String url;

    @Mock
    IsGameRestEndPoint rest;

    @Mock
    Snake player;

    @Before
    public void setUp() throws Exception {
        url = "ws://localhost:9999";
        rest = mock(GameRestEndPoint.class);
        game = new GameModel(url, rest);
        player = mock(Snake.class);
        when(player.getId()).thenReturn(5);
    }

    @Test
    public void setPlayer() {
        game.setPlayer(player);
    }

    @Test
    public void connectPlayer() {
        game.setPlayer(player);
        Session session = mock(Session.class);
        game.connectPlayer(5, session);
    }
}