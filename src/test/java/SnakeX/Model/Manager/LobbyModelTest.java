package SnakeX.Model.Manager;

import SnakeX.Model.enums.PlayerStatus;
import SnakeX.REST.RestEndPoint;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import sun.plugin.dom.core.CoreConstants;

import javax.websocket.Session;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LobbyModelTest {

    LobbyModel lobby;

    @Mock
    RestEndPoint rest;

    String correct = "test";
    String incorrect = "test1";

    String id = "yes";

    Session session;

    @Before
    public void setUp() throws Exception {
        session  = mock(Session.class);
        when(session.getId()).thenReturn(id);

        rest = mock(RestEndPoint.class);
        when(rest.loginPlayer(correct, correct)).thenReturn(5);
        when(rest.loginPlayer(incorrect, incorrect)).thenReturn(Integer.MIN_VALUE);
        when(rest.registerPlayer(correct, correct)).thenReturn(true);
        when(rest.registerPlayer(incorrect, incorrect)).thenReturn(false);

        lobby = new LobbyModel(rest);
    }

    @Test
    public void connectServer() {
        String url = "testurl.yes:7070";
        lobby.connectServer(session, url);

        assertEquals(1, lobby.getServers().size());
        lobby.removePlayer(session);
        assertEquals(0, lobby.getServers().size());
    }

    @Test
    public void getPlayer() {
        assertNull(lobby.getPlayer(session));
    }

    @Test
    public void addPlayer() {
        Player player = new Player(session);
        lobby.addPlayer(player);
        Player result = lobby.getPlayer(session);


        assertEquals(1, lobby.getPlayers().size());
        assertSame(result, player);
    }

    @Test
    public void removePlayer(){
        Player player = new Player(session);
        lobby.addPlayer(player);

        lobby.removePlayer(session);

        assertNull(lobby.getPlayer(session));
    }

    @Test
    public void registerPlayer() {
        boolean result = lobby.registerPlayer(correct, correct);
        assertTrue(result);

        result = lobby.registerPlayer(incorrect, incorrect);
        assertFalse(result);
    }

    @Test
    public void loginPlayer() {
        int id = lobby.loginPlayer(correct, correct, session);
        assertEquals(id, 5);

        id = lobby.loginPlayer(correct, correct, session);
        assertEquals(id, Integer.MIN_VALUE);

        id = lobby.loginPlayer(incorrect, incorrect, session);
        assertEquals(id, Integer.MIN_VALUE);
    }

    @Test
    public void receiveMessage() {
        String message = "yoyooy";
        Player player = new Player(session);
        player.setName("name");
        lobby.addPlayer(player);
        lobby.receiveMessage(message, session);
        assertEquals(message, lobby.getChat().getLastMessage().getText());

    }

    @Test
    public void joinQueue() {
        String id2 = "no";
        Session session2 = mock(Session.class);
        when(session2.getId()).thenReturn(id2);

        Player player1 = new Player(session);
        Player player2 = new Player(session2);

        player1.setRating(1000);
        player2.setRating(1300);

        lobby.addPlayer(player1);
        lobby.addPlayer(player2);

        lobby.joinQueue(session);
        lobby.joinQueue(session2);

        Player x = lobby.getPlayer(session);
        assertEquals(PlayerStatus.Queue ,x.getStatus());

        lobby.checkQueue();
        assertEquals(PlayerStatus.Queue ,x.getStatus());

        String id3 = "server";
        Session session3 = mock(Session.class);
        when(session2.getId()).thenReturn(id3);
        lobby.connectServer(session, "server");

        lobby.checkQueue();
        assertEquals(PlayerStatus.Playing ,x.getStatus());

    }
}