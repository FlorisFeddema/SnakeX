package snakex.model.manager;


public interface IsChat {


    /***
     * gets the last messsage send in the chat
     * @return the last message
     */
    Message getLastMessage();

    /***
     * adds a new message to the chat
     * @param message message to add
     */
    void addMessage(Message message);
}
