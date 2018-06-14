package SnakeX.Model.Manager;

import SnakeX.Model.Manager.ServerEntry;
import SnakeX.Model.enums.ServerStatus;
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
        assertEquals(ServerStatus.Free, entry.getStatus());
    }

    @Test
    public void setStatus() {
        ServerStatus status = ServerStatus.Busy;
        assertEquals(ServerStatus.Free, entry.getStatus());
        entry.setStatus(status);
        assertSame(status, entry.getStatus());

    }
}