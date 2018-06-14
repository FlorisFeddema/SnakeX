package SnakeX.Model.Manager;

import SnakeX.Model.Manager.Message;
import SnakeX.Model.Manager.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageTest {


    Message message;
    Player player;
    String text;

    @Before
    public void setUp() throws Exception {
        text = "test";
        player = new Player(null);
        message = new Message(text, player);
    }

    @Test
    public void getText() {
        assertSame(text, message.getText());
    }

    @Test
    public void getPlayer() {
        assertSame(player, message.getPlayer());
    }
}