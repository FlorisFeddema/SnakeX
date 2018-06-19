package snakex.model.manager;

import snakex.model.enums.PlayerStatus;

import java.util.ArrayList;
import java.util.List;

public class Queue {
    private static final int THRESHOLD = 400;
    private List<QueueEntry> entries;

    public Queue() {
        this.entries = new ArrayList<>();
    }

    public void addEntry(Player player){
        long time = System.currentTimeMillis();
        QueueEntry entry = new QueueEntry(player, time);
        player.setStatus(PlayerStatus.QUEUE);
        entries.add(entry);
    }

    public void removeEntry(Player player){
        entries.removeIf(i -> i.getPlayer() == player);
    }

    public Player[] searchMatch(){
        Player[] players = new Player[2];

        if (entries.isEmpty()){
            return players;
        }

        for (QueueEntry i : entries){
            for (QueueEntry j : entries){
                if (i != j){
                    int diff = i.getPlayer().getRating() - j.getPlayer().getRating();
                    diff = (diff < 0) ? -diff : diff;
                    if (diff < THRESHOLD){
                        players[0] = i.getPlayer();
                        players[1] = j.getPlayer();
                        return players;
                    }
                }
            }
        }

        return players;
    }
}

