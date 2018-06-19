package snakex.model.manager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class QueueTest {

    Queue queue;

    @Mock
    Player player1;
    @Mock
    Player player2;
    @Mock
    Player player3;

    @Before
    public void setUp() throws Exception {
        player1 = mock(Player.class);
        player2 = mock(Player.class);
        player3 = mock(Player.class);
        queue = new Queue();
        when(player1.getRating()).thenReturn(1500);
        when(player2.getRating()).thenReturn(2100);
        when(player3.getRating()).thenReturn(1300);
    }

    @Test
    public void findMatchCorrect() {
        queue.addEntry(player1);
        queue.addEntry(player3);

        Player[] players = queue.searchMatch();

        assertNotNull(players);
    }

    @Test
    public void findMatchIncorrect() {
        queue.addEntry(player2);
        queue.addEntry(player3);

        Player[] players = queue.searchMatch();

        assertNull(players);
    }


    @Test
    public void findMatchComplex() {
        queue.addEntry(player1);
        queue.addEntry(player2);
        queue.addEntry(player3);

        Player[] players = queue.searchMatch();

        assertTrue(players[0] == player1 && players[1] == player3);
        assertFalse(players[0] == player3 && players[1] == player2);
    }

    @Test
    public void removePlayer() {
        queue.addEntry(player1);
        queue.addEntry(player3);

        queue.removeEntry(player1);

        Player[] players = queue.searchMatch();

        assertNull(players);

    }
}