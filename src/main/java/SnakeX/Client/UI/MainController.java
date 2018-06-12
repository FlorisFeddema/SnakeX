package SnakeX.Client.UI;

import SnakeX.Client.Logic.IsClient;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


import java.io.IOException;


import static javafx.application.Platform.runLater;

public class MainController extends Controller implements IsMainController {

    public Label lblTime;
    public Label lblPlayers;
    public BarChart WinChart;
    public VBox pnChat;
    public TextField tbChat;

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

    private void addMessage(Label label){
        pnChat.getChildren().add(label);
        if (pnChat.getChildren().size() > 20){
            pnChat.getChildren().remove(0);
        }
    }
}
