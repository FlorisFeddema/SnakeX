package snakex.model.manager;

import javax.websocket.Session;

public interface IsLobby {

    /***
     * Gets a player that is connected with the session id
     * @param session connected session
     * @return player if connected, else null
     */
    Player getPlayer(Session session);

    /***
     * adds a new player to the list
     * @param player player object to add
     */
    void addPlayer(Player player);

    /***
     * removes the player that is connected with the session id
     * @param session connected session
     */
    void removePlayer(Session session);

    /***
     * updates the player that is connected with the session id
     * @param session connected session
     * @return return the updated player object
     */
    Player updatePlayer(Session session);

    /***
     * registers a new user to the database
     * @param username username of the user
     * @param password hashed password of the user
     * @return return succes
     */
    boolean registerPlayer(String username, String password);

    /***
     * logs a player in
     * @param username username of the user
     * @param password hashed password of the user
     * @param session connected session
     * @return id if succes, interger minvalue when no succes
     */
    int loginPlayer(String username, String password, Session session);

    /***
     * adds the message to the chat and sends it to the other users
     * @param text text of the message
     * @param session connected session
     */
    void receiveMessage(String text, Session session);

    /***
     * adds the player to the queue until game is found
     * @param session connected session
     */
    void joinQueue(Session session);

    /***
     * adds a new server to the serverlist
     * @param session connected session
     * @param url url of the server, for the player to connect
     */
    void connectServer(Session session, String url);

    /***
     * ends the game with players on the server
     * @param id1 id of player 1
     * @param id2 id of player 2
     * @param session session of the server
     */
    void endGame(int id1, int id2, Session session);
}
