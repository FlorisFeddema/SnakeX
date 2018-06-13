package SnakeX.Model.Manager;

import SnakeX.Model.enums.PlayerStatus;
import java.util.ArrayList;
import java.util.List;

public class Queue {
    final int threshold = 400;
    private List<QueueEntry> entries;

    public Queue() {
        this.entries = new ArrayList<>();
    }

    public void addEntry(Player player){
        long time = System.currentTimeMillis();
        QueueEntry entry = new QueueEntry(player, time);
        player.setStatus(PlayerStatus.Queue);
        entries.add(entry);
    }

    public void removeEntry(Player player){
        entries.removeIf(i -> i.getPlayer() == player);
    }

    public Player[] searchMatch(){
        Player[] players = new Player[2];

        if (entries.size() < 1){
            return null;
        }

        for (QueueEntry i : entries){
            for (QueueEntry j : entries){
                int diff = i.getPlayer().getRating() - i.getPlayer().getRating();
                diff = (diff < 0) ? -diff : diff;
                if (diff < threshold){
                    players[0] = i.getPlayer();
                    players[1] = j.getPlayer();
                    removeEntry(players[0]);
                    removeEntry(players[1]);
                    return players;
                }
            }
        }

        return null;
    }
}

