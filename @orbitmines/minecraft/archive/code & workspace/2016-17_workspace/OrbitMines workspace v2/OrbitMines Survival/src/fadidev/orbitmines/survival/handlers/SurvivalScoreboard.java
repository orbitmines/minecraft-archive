package fadidev.orbitmines.survival.handlers;

import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.scoreboard.ScoreboardSet;
import fadidev.orbitmines.survival.OrbitMinesSurvival;

/**
 * Created by Fadi on 10-9-2016.
 */
public class SurvivalScoreboard extends ScoreboardSet {

    private OrbitMinesSurvival survival;
    private String[] titles = {
            "§6§lOrbitMines§a§lSurvival",
			"§e§lO§6§lrbitMines§a§lSurvival",
			"§e§lOr§6§lbitMines§a§lSurvival",
			"§e§lOrb§6§litMines§a§lSurvival",
			"§e§lOrbi§6§ltMines§a§lSurvival",
			"§e§lOrbit§6§lMines§a§lSurvival",
			"§e§lOrbitM§6§lines§a§lSurvival",
			"§e§lOrbitMi§6§lnes§a§lSurvival",
			"§e§lOrbitMin§6§les§a§lSurvival",
			"§e§lOrbitMine§6§ls§a§lSurvival",
			"§e§lOrbitMines§a§lSurvival",
			"§e§lOrbitMines§2§lS§a§lurvival",
			"§e§lOrbitMines§2§lSu§a§lrvival",
			"§e§lOrbitMines§2§lSur§a§lvival",
			"§e§lOrbitMines§2§lSurv§a§lival",
			"§e§lOrbitMines§2§lSurvi§a§lval",
			"§e§lOrbitMines§2§lSurviv§a§lal",
			"§e§lOrbitMines§2§lSurviva§a§ll",
			"§e§lOrbitMines§2§lSurvival",
			"§6§lO§e§lrbitMines§2§lSurvival",
			"§6§lOr§e§lbitMines§2§lSurvival",
			"§6§lOrb§e§litMines§2§lSurvival",
			"§6§lOrbi§e§ltMines§2§lSurvival",
			"§6§lOrbit§e§lMines§2§lSurvival",
			"§6§lOrbitM§e§lines§2§lSurvival",
			"§6§lOrbitMi§e§lnes§2§lSurvival",
			"§6§lOrbitMin§e§les§2§lSurvival",
			"§6§lOrbitMine§e§ls§2§lSurvival",
			"§6§lOrbitMines§2§lSurvival",
			"§6§lOrbitMines§a§lS§2§lurvival",
			"§6§lOrbitMines§a§lSu§2§lrvival",
			"§6§lOrbitMines§a§lSur§2§lvival",
			"§6§lOrbitMines§a§lSurv§2§lival",
			"§6§lOrbitMines§a§lSurvi§2§lval",
			"§6§lOrbitMines§a§lSurviv§2§lal",
			"§6§lOrbitMines§a§lSurviva§2§ll",
			"§6§lOrbitMines§a§lSurvival"
    };
    private int index;

    public SurvivalScoreboard(OMPlayer omp) {
        super(omp, true);

        survival = OrbitMinesSurvival.getSurvival();
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
        SurvivalPlayer omp = (SurvivalPlayer) getOMPlayer();

        addScore(9, "");
        addScore(8, "§e§lOrbitMines Tokens");
        if(omp.isLoaded())
            addScore(7, " " + omp.getOrbitMinesTokens() + "  ");
        else
            addScore(7, " " + SurvivalMessages.WORD_LOADING.get(omp) + "...  ");
        addScore(6, " ");
        addScore(5, "§b§lVIP Points");
        if(omp.isLoaded())
            addScore(4, " " + omp.getVipPoints());
        else
            addScore(4, " " + SurvivalMessages.WORD_LOADING.get(omp) + "...");
        addScore(3, "  ");
        addScore(2, "§2§l" + SurvivalMessages.WORD_MONEY.get(omp));
        if(omp.isLoaded())
            addScore(1, " " + omp.getMoney() + " ");
        else
            addScore(1, " " + SurvivalMessages.WORD_LOADING.get(omp) + "... ");
        addScore(0, "   ");
    }

    @Override
    public void updateTeams() {

    }
}
