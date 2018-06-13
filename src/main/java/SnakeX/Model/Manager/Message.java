package SnakeX.Model.Manager;

public class Message {
    public String getText() {
        return text;
    }

    public Player getPlayer() {
        return player;
    }

    private String text;
    private Player player;

    public Message(String text, Player player){
        this.text = text;
        this.player = player;
    }


}
