package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.runnables;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.Interval;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.PlayerRunnable;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.TimeUnit;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.scoreboards.Board;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.scoreboards.ScoreboardSet;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.scoreboards.SpigotScoreboard;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Team;

import java.util.Collection;

public class ScoreboardRunnable<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends PlayerRunnable<S, P> {

    private S server;

    public ScoreboardRunnable(S plugin) {
        super(plugin, Interval.of(TimeUnit.TICK, 5));

        this.server = plugin;
    }

    @Override
    public void run() {
        server.getScoreboardAnimation().next();

        super.run();
    }

    @Override
    public void run(P player) {
        SpigotScoreboard<P> scoreboard = player.getSpigotScoreboard();
        ScoreboardSet<P> set = scoreboard.getSet();

        if (set == null)
            return;

        set(player, scoreboard, set);
    }

    @Override
    public Collection<P> getPlayers() {
        return server.getPlayers();
    }

    private void set(P player, SpigotScoreboard<P> scoreboard, ScoreboardSet<P> set) {
        Board board = scoreboard.getBoard();

        if (set != null) {
            if (player.hasScoreboard() || set.canBypassSettings()) {
                if (scoreboard.getSet() != null) {
                    for (int score : scoreboard.getSet().getScores().keySet()) {
                        if (!set.getScores().containsKey(score))
                            board.remove(score, scoreboard.getSet().getScore(score).getString());
                    }
                }

                board.setTitle(set.getTitle().getString());

                for (int score : set.getScores().keySet()) {
                    board.add(set.getScore(score).getString(), score);
                }
            } else {
                runSync(() -> {
                    /* Create scoreboard in sync */
                    scoreboard.setBoard(new Board(player.bukkit().getName()));
                    player.bukkit().getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
                });
            }

            board.update();
            board.send(player.bukkit());

            if (scoreboard.getSet() != null)
                scoreboard.updateTeams();
        } else {
            runSync(() -> {
                /* Create scoreboard in sync */
                scoreboard.setBoard(new Board(player.bukkit().getName()));
                player.bukkit().getScoreboard().clearSlot(DisplaySlot.SIDEBAR);

                for (Team team : player.bukkit().getScoreboard().getTeams()) {
                    for (String entry : team.getEntries()) {
                        team.removeEntry(entry);
                    }
                }
            });
        }

        scoreboard.setSet(set);
    }
}
