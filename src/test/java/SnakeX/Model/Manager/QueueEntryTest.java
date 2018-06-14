package SnakeX.Model.Manager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;

public class QueueEntryTest {

    QueueEntry entry;
    @Mock
    Player player;

    long time;

    @Before
    public void setUp() throws Exception {
        time = 1000000;
        entry = new QueueEntry(player, time);
    }

    @Test
    public void constructor() {
        assertSame(player, entry.getPlayer());
        assertEquals(time, entry.getTime());
    }

}