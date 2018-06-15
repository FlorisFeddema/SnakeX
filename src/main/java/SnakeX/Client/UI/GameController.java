package SnakeX.Client.UI;

import SnakeX.Client.Logic.IsClient;
import SnakeX.Client.Logic.Point;
import SnakeX.Client.Logic.Snake;
import SnakeX.Model.enums.MoveDirection;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameController extends Controller implements IsGameController{

    private int horizontal = 20;
    private int vertical = 20;
    private int size = 600;
    private int squareSize = size / horizontal;


    public Pane PnGame;

    private Rectangle[][] grid;


    public void setupGrid(){
        grid = new Rectangle[horizontal][vertical];
        for (int i = 0; i < horizontal; i++) {
            for (int j = 0; j < vertical; j++) {
                double x = 100 + i * (size / horizontal) + 1;
                double y = 50 + j * (size/ vertical) + 1;
                Rectangle rectangle = new Rectangle(x,y, squareSize, squareSize);
                rectangle.setArcWidth(5);
                rectangle.setArcHeight(5);
                rectangle.setStroke(Color.BLACK);
                rectangle.setFill(Color.WHITE);
                rectangle.setVisible(true);
                grid[i][j] = rectangle;
                PnGame.getChildren().add(rectangle);
            }
        }
    }

    @Override
    public void setClient(IsClient client){
        super.setClient(client);
        client.setGameController(this);
        Client.stage.getScene().addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()==KeyCode.DOWN) {
                client.move(MoveDirection.Down);
            }
        });
        Client.stage.getScene().addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()==KeyCode.UP) {
                client.move(MoveDirection.Up);
            }
        });
        Client.stage.getScene().addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()==KeyCode.LEFT) {
                client.move(MoveDirection.Left);
            }
        });
        Client.stage.getScene().addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()==KeyCode.RIGHT) {
                client.move(MoveDirection.Right);
            }
        });
    }

    public void spawn(Snake snake){
        int x = snake.getPositions().get(0).getX();
        int y = snake.getPositions().get(0).getY();
        grid[x][y].setFill(client.getPlayer().getColor());
    }

    public void move(Snake[] snakes){
        for (Rectangle[] i: grid) {
            for (Rectangle j: i) {
                j.setFill(Color.WHITE);
            }
        }

        for (Snake snake:snakes) {
            for (Point i:snake.getPositions()) {
                grid[i.getX()][i.getY()].setFill(snake.getColor());
            }
        }
    }
}
