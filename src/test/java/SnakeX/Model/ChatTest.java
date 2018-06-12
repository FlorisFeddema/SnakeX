package SnakeX.Model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ChatTest {

    Chat chat;
    Player player;

    @Before
    public void setUp() throws Exception {
        chat = new Chat();
        player = new Player(null);
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