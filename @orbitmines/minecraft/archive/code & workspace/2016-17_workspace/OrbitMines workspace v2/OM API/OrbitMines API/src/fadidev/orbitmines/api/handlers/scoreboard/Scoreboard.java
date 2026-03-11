package fadidev.orbitmines.api.handlers.scoreboard;

import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.Utils;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Team;

/**
 * Created by Fadi on 11-5-2016.
 */
public class Scoreboard {

    private Board board;
    private ScoreboardSet set;
    private OMPlayer omp;

    public Scoreboard(OMPlayer omp){
        omp.setScoreboard(this);
        this.board = new Board(omp.getPlayer().getName());
        this.omp = omp;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public ScoreboardSet getSet() {
        return set;
    }

    public OMPlayer getOMPlayer() {
        return omp;
    }

    public void set(ScoreboardSet set){
        if(set != null){
            if(getSet() != null){
                for(int score : getSet().getScores().keySet()){
                    if(!set.getScores().containsKey(score))
                        board.remove(score, getSet().getScore(score));
                }
            }

            getBoard().setTitle(set.getTitle());

            for(int score : set.getScores().keySet()){
                getBoard().add(set.getScore(score), score);
            }

            getBoard().update();
            getBoard().send(omp.getPlayer());

            if(getSet() != null)
                updateTeams();
        }
        else{
            this.board = new Board(omp.getPlayer().getName());
            omp.getPlayer().getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
            for(Team team : omp.getPlayer().getScoreboard().getTeams()){
                for(OfflinePlayer p : team.getPlayers()){
                    team.removePlayer(p);
                }
            }
        }

        this.set = set;
    }

    public void update(){
        if(getSet() == null)
            return;

        set.clear();
        set.updateTitle();
        set.updateScores();
        set.updateTeams();

        set(getSet());
    }

    private void updateTeams(){
        for(String team : getSet().getTeams().keySet()){
            Team t = getBoard().getScoreboard().getTeam(team);
            if(t == null)
                t = getBoard().getScoreboard().registerNewTeam(team);

            for(Player player : getSet().getTeams().get(team)){
                t.addPlayer(player);
            }

            String prefix = getSet().getTeamPrefix().get(team);
            String suffix = getSet().getTeamSuffix().get(team);

            if(prefix != null)
                t.setPrefix(prefix);
            if(suffix != null)
                t.setSuffix(suffix);

            t.setAllowFriendlyFire(getSet().getTeamAllowFriendlyFire().get(team));
            t.setCanSeeFriendlyInvisibles(getSet().getTeamCanSeeFriendlyInvisibles().get(team));
        }

        if(getSet().useRankTeams())
            Utils.updateRankTeams(getBoard().getScoreboard());
    }
}
