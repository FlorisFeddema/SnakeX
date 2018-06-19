package snakex.model.manager;


public interface IsChat {

    Message getLastMessage();

    void addMessage(Message message);
}
