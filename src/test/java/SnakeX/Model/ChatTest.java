package SnakeX.Model;

import SnakeX.Model.Manager.Chat;
import SnakeX.Model.Manager.Message;
import SnakeX.Model.Manager.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ChatTest {

    Chat chat;

    @Mock
    Player player;

    @Before
    public void setUp() throws Exception {
        chat = new Chat();
        player = mock(Player.class);
    }

    @Test
    public void getLastMessage() {
        Message message1 = new Message("yay", player);
        Message message2 = new Message("ah", player);
        chat.addMessage(message1);
        chat.addMessage(message2);

        Message result = chat.getLastMessage();

        assertSame(message2, result);
        assertNotSame(message1, result);
    }

    @Test
    public void addMessage() {
        Message message1 = new Message("yay", player);
        chat.addMessage(message1);

        Message result = chat.getLastMessage();

        assertSame(message1, result);
        Assert.assertEquals(message1.getText(), message1.getText());

        }
}