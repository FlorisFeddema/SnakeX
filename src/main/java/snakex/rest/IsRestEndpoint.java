package snakex.rest;

import snakex.model.manager.Player;

public interface IsRestEndpoint {

    /***
     * makes an api call to login a player
     * @param username username of the player
     * @param password hashed password of the player
     * @return id if succes, integer minvalue if fail
     */
    int loginPlayer(String username, String password);

    /***
     * makes an api call to register a player
     * @param username username of the player
     * @param password hashed password of the player
     * @return succes
     */
    boolean registerPlayer(String username, String password);

    /***
     * updates the stats of a player
     * @param player player to update
     * @return updated player object
     */
    Player updatePlayer(Player player);
}
