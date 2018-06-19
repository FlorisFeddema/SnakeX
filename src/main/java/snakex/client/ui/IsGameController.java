package snakex.client.ui;


import snakex.model.shared.Snake;

public interface IsGameController {

    /***
     * Draws the grid on the GUI
     */
    void setupGrid();

    /***
     * updates the position of the snakes on the GUI
     * @param snakes snakes to show
     */
    void move(Snake[] snakes);

    /***
     * shows a alert when a player has won, also takes the player back to main menu
     */
    void showWin();

    /***
     * shows a alert when a player has won, also takes the player back to main menu
     */
    void showLoss();

    /***
     * shows a alert when the game was a draw, also takes the player back to main menu
     */
    void showDraw();

}
