package snakex.client.ui;

public interface IsMainController {

    /***
     * adds a message from another player
     * @param name name of the sender
     * @param message text of the message
     */
    void addMessageOther(String name, String message);

    /***
     * ui joins a game and loads a gamecontroller
     */
    void joinGame();
}
