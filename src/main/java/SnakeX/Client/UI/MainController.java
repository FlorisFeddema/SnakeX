package SnakeX.Client.UI;

import SnakeX.Client.Logic.IsClient;
import SnakeX.Shared.ConsoleColors;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;


import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


import static javafx.application.Platform.runLater;

public class MainController extends Controller implements IsMainController {

    public Label lblTime;
    public BarChart WinChart;
    public VBox pnChat;
    public TextField tbChat;
    public Button btnPlay;
    public Label lblQueue;
    private Timer timer;
    private int queueTime;

    @Override
    public void setClient(IsClient client){
        super.setClient(client);
        client.setMainController(this);
        try {
            client.updatePlayerStats();
        } catch (IOException e) {
            //ignore
        } catch (InterruptedException e) {
            //ignore
        }
        queueTime = 0;
        setupBarChart();
    }

    private void setupBarChart(){
        int games = client.getGames();
        int wins = client.getWins();
        int losses = games - wins;

        XYChart.Series winbar = new XYChart.Series();
        winbar.setName("Win");

        winbar.getData().add(new XYChart.Data("Games", wins));

        WinChart.getData().add(winbar);

        XYChart.Series lossbar = new XYChart.Series();
        lossbar.setName("Loss");

        lossbar.getData().add(new XYChart.Data("Games", losses));

        WinChart.getData().add(lossbar);
    }

    @FXML
    public void sendMessage() {
        String message = tbChat.getText();
        if (message.length() > 0){
            addMessagePlayer(message);
            tbChat.setText("");
            try {
                client.sendMessagePlayer(message);
            } catch (IOException e) {
                //ignore
            }
        }
    }

    private void addMessagePlayer(String message){
        Label label = new Label(  "You: " + message );
        label.setTextFill(Color.web("#009933"));
        addMessage(label);
    }

    public void addMessageOther(String name, String message){
        runLater(() -> {
            Label label = new Label(name + ": " + message );
            label.setTextFill(Color.web("#cc0000"));
            addMessage(label);
        });

    }

    @Override
    public void joinGame() {
        Platform.runLater(() -> {
            try {
                changeScene("Game", client);
            } catch (IOException e) {
                //ignore
            }
        });
    }

    private void addMessage(Label label){
        pnChat.getChildren().add(label);
        if (pnChat.getChildren().size() > 20){
            pnChat.getChildren().remove(0);
        }
    }

    @FXML
    public void playGame() {
        btnPlay.setDisable(true);

        try {
            client.joinQueue();
            lblQueue.setVisible(true);
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    queueTime += 1;
                    int queueMinute = queueTime / 60;
                    int queueSecond = queueTime % 60;
                    String text = "Time: " + String.format("%1$02d", queueMinute) + ":" + String.format("%1$02d", queueSecond);
                    Platform.runLater(() -> lblTime.setText(text));
                }
            }, 1000, 1000);
        } catch (IOException e) {
            btnPlay.setDisable(false);

        }

    }
}
