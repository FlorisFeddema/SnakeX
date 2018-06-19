package SnakeX.Client.UI;


import SnakeX.Model.Shared.Snake;

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
}
