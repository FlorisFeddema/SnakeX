package snakex.model.manager;

import snakex.model.shared.Snake;
import snakex.model.enums.PlayerStatus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.websocket.Session;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class PlayerTest {

    Player player;

    @Mock
    Session session;

    @Before
    public void setUp() throws Exception {
        session = mock(Session.class);
        player = new Player(session);
    }

    @Test
    public void getSnake() {
        Snake snake = mock(Snake.class);
        player.setSnake(snake);
        assertSame(snake, player.getSnake());
    }

    @Test
    public void getGames() {
        int games = 10;
        player.setGames(games);
        assertEquals(games, player.getGames());
    }

    @Test
    public void getWins() {
        int wins = 8;
        player.setWins(wins);
        assertEquals(wins, player.getWins());
    }

    @Test
    public void getSession() {
        assertSame(session, player.getSession());
    }

    @Test
    public void getName() {
        String name = "test";
        player.setName(name);
        assertEquals(name, player.getName());
    }

    @Test
    public void getId() {
        int id = 951;
        player.setId(id);
        assertEquals(id, player.getId());
    }

    @Test
    public void getRating() {
        int rating = 981;
        player.setRating(rating);
        assertEquals(rating, player.getRating());
    }

    @Test
    public void getStatus() {
        assertEquals(PlayerStatus.LOBBY, player.getStatus());
        PlayerStatus status = PlayerStatus.QUEUE;
        player.setStatus(status);
        assertEquals(status, player.getStatus());

    }
}