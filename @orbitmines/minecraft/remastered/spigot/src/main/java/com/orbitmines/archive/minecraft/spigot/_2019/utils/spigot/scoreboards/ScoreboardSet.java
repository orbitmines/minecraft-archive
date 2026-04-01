package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.scoreboards;

import com.orbitmines.archive.minecraft._2019.utils.mutable.MutableString;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotPlayer;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* OrbitMines - @author Fadi Shawki - 2017
*/
public abstract class ScoreboardSet<P extends SpigotPlayer> {

    @Getter protected P player;
    @Getter private final MutableString title;
    @Getter private final Map<Integer, MutableString> scores;

    public ScoreboardSet(P player, MutableString title, MutableString... scores) {
        this.player = player;
        this.title = title;
        this.scores = new HashMap<>();

        int score = scores.length -1;
        for (int i = 0; i < scores.length; i++) {
            this.scores.put(score, scores[i]);
            score--;
        }
    }

    public abstract List<ScoreboardTeam> getTeams();

    public abstract boolean canBypassSettings();

    public MutableString getScore(int score) {
        return scores.get(score);
    }
}
