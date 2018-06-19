package snakex.client.logic;

import java.io.IOException;

public interface IsClientManagerEndPoint {

    /***
     * logs the player in using the server connection
     * @param name name of the player
     * @param hash hash of the player password
     * @return id when correct, integer minvalue when incorrect
     * @throws IOException when server is not reached
     * @throws InterruptedException when server is not reacting to request, or delay is to long
     */
    int loginPlayer(String name, String hash) throws IOException, InterruptedException;

    /***
     * registers a new player in the system using the server connection
     * @param name name of the player
     * @param hash hash of the player password
     * @return true if registerd, false if name already taken
     * @throws IOException when server is not reached
     * @throws InterruptedException when server is not reacting to request, or delay is to long
     */
    boolean registerPlayer(String name, String hash) throws IOException, InterruptedException;

    /***
     * updates the stats of the player
     * @throws IOException when server is not reached
     * @throws InterruptedException when server is not reacting to request, or delay is to long
     */
    void updatePlayer() throws IOException, InterruptedException;

    /***
     * send message to other players using the server connection
     * @param message text of the message
     * @throws IOException when server is not reached
     */
    void sendMessagePlayer(String message) throws IOException;

    /***
     * players joins the queue on the server
     * @throws IOException when server is not reached
     */
    void joinQueue() throws IOException;
}
