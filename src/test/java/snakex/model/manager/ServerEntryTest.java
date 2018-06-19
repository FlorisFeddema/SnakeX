package snakex.model.manager;

import snakex.model.enums.ServerStatus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.websocket.Session;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ServerEntryTest {

    ServerEntry entry;
    String url;
    @Mock
    Session session;

    @Before
    public void setUp() throws Exception {
        session = mock(Session.class);
        url = "test";
        entry = new ServerEntry(session, url);
    }

    @Test
    public void constructor() {
        assertSame(session, entry.getSession());
        assertSame(url, entry.getUrl());
        assertEquals(ServerStatus.FREE, entry.getStatus());
    }

    @Test
    public void setStatus() {
        ServerStatus status = ServerStatus.BUSY;
        assertEquals(ServerStatus.FREE, entry.getStatus());
        entry.setStatus(status);
        assertSame(status, entry.getStatus());

    }
}