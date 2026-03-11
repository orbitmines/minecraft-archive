package fadidev.orbitmines.api.handlers.scoreboard;

import fadidev.orbitmines.api.handlers.OMPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Fadi on 11-5-2016.
 */
public abstract class ScoreboardSet {

    private OMPlayer omp;
    private boolean useRankTeams;
    private String title;
    private Map<Integer, String> scores;
    private Map<String, List<Player>> teams;
    private Map<String, String> teamPrefix;
    private Map<String, String> teamSuffix;
    private Map<String, Boolean> teamAllowFriendlyFire;
    private Map<String, Boolean> teamCanSeeFriendlyInvisibles;

    public ScoreboardSet(OMPlayer omp, boolean useRankTeams){
        this.omp = omp;
        this.useRankTeams = useRankTeams;
        this.scores = new HashMap<>();
        this.teams = new HashMap<>();
        this.teamPrefix = new HashMap<>();
        this.teamSuffix = new HashMap<>();
        this.teamAllowFriendlyFire = new HashMap<>();
        this.teamCanSeeFriendlyInvisibles = new HashMap<>();
    }

    public abstract void updateTitle();
    public abstract void updateScores();
    public abstract void updateTeams();

    public void clear(){
        this.title = null;
        this.scores.clear();
    }

    public OMPlayer getOMPlayer() {
        return omp;
    }

    public boolean useRankTeams() {
        return useRankTeams;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addScore(int score, String text){
        this.scores.put(score, text);
    }

    public Map<Integer, String> getScores() {
        return scores;
    }

    public String getScore(int score){
        return scores.get(score);
    }

    public Map<String, List<Player>> getTeams() {
        return teams;
    }

    public Map<String, String> getTeamPrefix() {
        return teamPrefix;
    }

    public Map<String, String> getTeamSuffix() {
        return teamSuffix;
    }

    public Map<String, Boolean> getTeamAllowFriendlyFire() {
        return teamAllowFriendlyFire;
    }

    public Map<String, Boolean> getTeamCanSeeFriendlyInvisibles() {
        return teamCanSeeFriendlyInvisibles;
    }

    public String addTeam(String name){
        return addTeam(name, new ArrayList<Player>());
    }

    public String addTeam(String name, List<Player> players){
        this.teams.put(name, players);
        this.teamPrefix.put(name, null);
        this.teamSuffix.put(name, null);
        this.teamAllowFriendlyFire.put(name, false);
        this.teamCanSeeFriendlyInvisibles.put(name, false);

        return name;
    }

    public void setTeamPrefix(String name, String prefix){
        this.teamPrefix.put(name, prefix);
    }

    public void setTeamSuffix(String name, String suffix){
        this.teamSuffix.put(name, suffix);
    }

    public void setTeamAllowFriendlyFire(String name, boolean allowFriendlyFire){
        this.teamAllowFriendlyFire.put(name, allowFriendlyFire);
    }

    public void setTeamCanSeeFriendlyInvisibles(String name, boolean canSeeFriendlyInvisibles){
        this.teamCanSeeFriendlyInvisibles.put(name, canSeeFriendlyInvisibles);
    }
}
