package SnakeX.Model;

public interface IsChat {

    Message getLastMessage();

    void addMessage(Message message);
}
