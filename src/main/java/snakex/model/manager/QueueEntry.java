package snakex.model.manager;

public class QueueEntry {
    private Player player;

    public Player getPlayer() {
        return player;
    }

    public long getTime() {
        return time;
    }

    private long time;

    public QueueEntry(Player player, long time) {
        this.player = player;
        this.time = time;
    }
}
