package fadidev.orbitmines.prison.handlers;

import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.scoreboard.ScoreboardSet;
import fadidev.orbitmines.api.utils.enums.ranks.StaffRank;
import fadidev.orbitmines.api.utils.enums.ranks.VIPRank;
import fadidev.orbitmines.prison.OrbitMinesPrison;
import org.bukkit.entity.Player;

import java.util.Collections;

/**
 * Created by Fadi on 10-9-2016.
 */
public class PrisonScoreboard extends ScoreboardSet {

    private OrbitMinesPrison prison;
    private String[] titles = {
            "§6§lOrbitMines§4§lPrison",
			"§e§lO§6§lrbitMines§4§lPrison",
			"§e§lOr§6§lbitMines§4§lPrison",
			"§e§lOrb§6§litMines§4§lPrison",
			"§e§lOrbi§6§ltMines§4§lPrison",
			"§e§lOrbit§6§lMines§4§lPrison",
			"§e§lOrbitM§6§lines§4§lPrison",
			"§e§lOrbitMi§6§lnes§4§lPrison",
			"§e§lOrbitMin§6§les§4§lPrison",
			"§e§lOrbitMine§6§ls§4§lPrison",
			"§e§lOrbitMines§4§lPrison",
			"§e§lOrbitMines§c§lP§4§lrison",
			"§e§lOrbitMines§c§lPr§4§lison",
			"§e§lOrbitMines§c§lPri§4§lson",
			"§e§lOrbitMines§c§lPris§4§lon",
			"§e§lOrbitMines§c§lPriso§4§ln",
			"§e§lOrbitMines§c§lPrison",
			"§6§lO§e§lrbitMines§c§lPrison",
			"§6§lOr§e§lbitMines§c§lPrison",
			"§6§lOrb§e§litMines§c§lPrison",
			"§6§lOrbi§e§ltMines§c§lPrison",
			"§6§lOrbit§e§lMines§c§lPrison",
			"§6§lOrbitM§e§lines§c§lPrison",
			"§6§lOrbitMi§e§lnes§c§lPrison",
			"§6§lOrbitMin§e§les§c§lPrison",
			"§6§lOrbitMine§e§ls§c§lPrison",
			"§6§lOrbitMines§c§lPrison",
			"§6§lOrbitMines§4§lP§c§lrison",
			"§6§lOrbitMines§4§lPr§c§lison",
			"§6§lOrbitMines§4§lPri§c§lson",
			"§6§lOrbitMines§4§lPris§c§lon",
			"§6§lOrbitMines§4§lPriso§c§ln",
			"§6§lOrbitMines§4§lPrison"
    };
    private int index;

    public PrisonScoreboard(OMPlayer omp) {
        super(omp, false);

        this.prison = OrbitMinesPrison.getPrison();
        this.index = 0;
    }

    @Override
    public void updateTitle() {
        if(index >= titles.length)
            index = 0;

        setTitle(titles[index]);

        index++;
    }

    @Override
    public void updateScores() {
        PrisonPlayer omp = (PrisonPlayer) getOMPlayer();

        addScore(9, "");
        addScore(8, "§6§lGold");
        if(omp.isLoaded())
            addScore(7, " " + omp.getGold() + "  ");
        else
            addScore(7, " " + PrisonMessages.WORD_LOADING.get(omp) + "...  ");
        addScore(6, " ");
        addScore(5, "§9§lRank");
        if(omp.isLoaded())
            addScore(4, " " + omp.getRank().getName());
        else
            addScore(4, " " + PrisonMessages.WORD_LOADING.get(omp) + "...");
        addScore(3, "  ");
        addScore(2, "§c§lRankup " + Messages.WORD_PRICE.get(omp));
        if(omp.isLoaded())
            addScore(1, " " + (omp.getRank().getNextRank() == null ? "§f" + PrisonMessages.WORD_HIGHEST_RANK.get(omp) : omp.getRank().getRankupPrice()) + " ");
        else
            addScore(1, " " + PrisonMessages.WORD_LOADING.get(omp) + "... ");
        addScore(0, "   ");
    }

    @Override
    public void updateTeams() {
        for(PrisonPlayer omPlayer : prison.getPrisonPlayers()){
            StaffRank staff = omPlayer.getStaffRank();
            VIPRank vip = omPlayer.getVipRank();
            Player p = omPlayer.getPlayer();

            addTeam(p.getName(), Collections.singletonList(p));
            if(staff != StaffRank.USER)
                setTeamPrefix(p.getName(), staff.getScoreboardPrefix());
            else
                setTeamPrefix(p.getName(), vip.getScoreboardPrefix());

            setTeamSuffix(p.getName(), " §8| §a" + omPlayer.getRank().toString());
        }
    }
}
