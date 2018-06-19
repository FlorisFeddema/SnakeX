package snakex.client.ui;

import snakex.client.logic.IsClient;
import snakex.model.shared.Point;
import snakex.model.shared.Snake;
import snakex.model.enums.MoveDirection;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class GameController extends Controller implements IsGameController{

    public Label lblEnemyName;
    public Label lblEnemyElo;
    public Label lblYourElo;
    private int horizontal = 20;
    private int vertical = 20;
    private int size = 600;
    private int squareSize = size / horizontal;

    public Pane pnGame;

    private Rectangle[][] grid;

    public void setupGrid(){
        grid = new Rectangle[horizontal][vertical];
        for (int i = 0; i < horizontal; i++) {
            for (int j = 0; j < vertical; j++) {
                double x = 100 + i * ((double)size / horizontal) + 1;
                double y = 50 + j * ((double)size/ vertical) +  1;
                Rectangle rectangle = new Rectangle(x,y, squareSize, squareSize);
                rectangle.setArcWidth(5);
                rectangle.setArcHeight(5);
                rectangle.setStroke(Color.BLACK);
                rectangle.setFill(Color.WHITE);
                rectangle.setVisible(true);
                grid[i][j] = rectangle;
                pnGame.getChildren().add(rectangle);
            }
        }
    }

    @Override
    public void setClient(IsClient client){
        super.setClient(client);
        client.setGameController(this);
        setupGrid();
        setupMatchDetails();
        Client.stage.getScene().addEventHandler(KeyEvent.KEY_PRESSED, key -> {
            if(key.getCode()==KeyCode.DOWN) {
                client.move(MoveDirection.DOWN);
            }
        });
        Client.stage.getScene().addEventHandler(KeyEvent.KEY_PRESSED, key -> {
            if(key.getCode()==KeyCode.UP) {
                client.move(MoveDirection.UP);
            }
        });
        Client.stage.getScene().addEventHandler(KeyEvent.KEY_PRESSED, key -> {
            if(key.getCode()==KeyCode.LEFT) {
                client.move(MoveDirection.LEFT);
            }
        });
        Client.stage.getScene().addEventHandler(KeyEvent.KEY_PRESSED, key -> {
            if(key.getCode()==KeyCode.RIGHT) {
                client.move(MoveDirection.RIGHT);
            }
        });

        client.connectGame();
    }

    private void setupMatchDetails(){
        lblEnemyElo.setText(Integer.toString(client.getEnemy().getRating()));
        lblYourElo.setText(Integer.toString(client.getPlayer().getRating()));
        lblEnemyName.setText(client.getEnemy().getName());
        move(new Snake[]{client.getPlayer(), client.getEnemy()});
    }


    public void move(Snake[] snakes){
        Platform.runLater(() -> {
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
        });
    }

    public void showWin(){
        Platform.runLater(() -> {
            showAlert("You won!", "Winner");
            loadMainMenu();
        });
    }

    public void showLoss(){
        Platform.runLater(() -> {
            showAlert("You lost!", "Loser");
            loadMainMenu();
        });
    }

    public void showDraw(){
        Platform.runLater(() -> {
            showAlert("It is a draw", "DRAW");
            loadMainMenu();
        });
    }

    private void loadMainMenu(){
        try {
            changeScene("Main", client);
        } catch (IOException e) {
            //ignore
        }
    }
}
