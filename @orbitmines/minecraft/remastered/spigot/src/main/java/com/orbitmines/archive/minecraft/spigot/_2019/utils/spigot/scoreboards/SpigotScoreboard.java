package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.scoreboards;

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotPlayer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Team;

/*
* OrbitMines - @author Fadi Shawki - 2017
*/
public class SpigotScoreboard<P extends SpigotPlayer> {

    @Getter @Setter private Board board;
    @Getter @Setter private ScoreboardSet<P> set;
    @Getter private P player;

    public SpigotScoreboard(P player) {
        this.board = new Board(player.bukkit().getName());
        this.player = player;
    }

    public void set(ScoreboardSet<P> set) {
        if (set != null) {
            if (player.hasScoreboard() || set.canBypassSettings()) {
                if (this.set != null) {
                    for (int score : this.set.getScores().keySet()) {
                        if (!set.getScores().containsKey(score))
                            board.remove(score, this.set.getScore(score).getString());
                    }
                }

                board.setTitle(set.getTitle().getString());

                for (int score : set.getScores().keySet()) {
                    board.add(set.getScore(score).getString(), score);
                }
            } else {
                this.board = new Board(player.bukkit().getName());
                player.bukkit().getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
            }

            board.update();
            board.send(player.bukkit());

            if (this.set != null)
                updateTeams();
        } else {
            this.board = new Board(player.bukkit().getName());
            player.bukkit().getScoreboard().clearSlot(DisplaySlot.SIDEBAR);

            for (Team team : player.bukkit().getScoreboard().getTeams()) {
                for (String entry : team.getEntries()) {
                    team.removeEntry(entry);
                }
            }
        }

        this.set = set;
    }

    public void clear() {
        this.board = new Board(player.bukkit().getName());
        set(set);
    }

    public void updateTeams() {
        for (ScoreboardTeam team : set.getTeams()) {
            team.update(board.getScoreboard());
        }
    }
}
