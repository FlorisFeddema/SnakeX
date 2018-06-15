package SnakeX.Client.UI;

import SnakeX.Client.Logic.Snake;

public interface IsGameController {

    /***
     * Draws the grid on the GUI
     */
    void setupGrid();

    /***
     * Spawns a snake at the position on the GUI
     * @param snake snake to spawn
     */
    void spawn(Snake snake);

    /***
     * updates the position of the snakes on the GUI
     * @param snakes snakes to show
     */
    void move(Snake[] snakes);
}
