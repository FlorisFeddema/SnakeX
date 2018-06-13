package SnakeX.Model.Manager;


public interface IsChat {

    Message getLastMessage();

    void addMessage(Message message);
}
