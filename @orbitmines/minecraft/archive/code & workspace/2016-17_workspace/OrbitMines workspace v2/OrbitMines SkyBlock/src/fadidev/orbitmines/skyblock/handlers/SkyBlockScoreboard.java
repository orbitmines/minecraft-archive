package fadidev.orbitmines.skyblock.handlers;

import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.scoreboard.ScoreboardSet;
import fadidev.orbitmines.skyblock.OrbitMinesSkyBlock;

/**
 * Created by Fadi on 10-9-2016.
 */
public class SkyBlockScoreboard extends ScoreboardSet {

    private OrbitMinesSkyBlock skyBlock;
    private String[] titles = {
            "§6§lOrbitMines§5§lSkyBlock",
            "§e§lO§6§lrbitMines§5§lSkyBlock",
            "§e§lOr§6§lbitMines§5§lSkyBlock",
            "§e§lOrb§6§litMines§5§lSkyBlock",
            "§e§lOrbi§6§ltMines§5§lSkyBlock",
            "§e§lOrbit§6§lMines§5§lSkyBlock",
            "§e§lOrbitM§6§lines§5§lSkyBlock",
            "§e§lOrbitMi§6§lnes§5§lSkyBlock",
            "§e§lOrbitMin§6§les§5§lSkyBlock",
            "§e§lOrbitMine§6§ls§5§lSkyBlock",
            "§e§lOrbitMines§5§lSkyBlock",
            "§e§lOrbitMines§d§lS§5§lkyBlock",
            "§e§lOrbitMines§d§lSk§5§lyBlock",
            "§e§lOrbitMines§d§lSky§5§lBlock",
            "§e§lOrbitMines§d§lSkyB§5§llock",
            "§e§lOrbitMines§d§lSkyBl§5§lock",
            "§e§lOrbitMines§d§lSkyBlo§5§lck",
            "§e§lOrbitMines§d§lSkyBloc§5§lk",
            "§e§lOrbitMines§d§lSkyBlock",
            "§6§lO§e§lrbitMines§d§lSkyBlock",
            "§6§lOr§e§lbitMines§d§lSkyBlock",
            "§6§lOrb§e§litMines§d§lSkyBlock",
            "§6§lOrbi§e§ltMines§d§lSkyBlock",
            "§6§lOrbit§e§lMines§d§lSkyBlock",
            "§6§lOrbitM§e§lines§d§lSkyBlock",
            "§6§lOrbitMi§e§lnes§d§lSkyBlock",
            "§6§lOrbitMin§e§les§d§lSkyBlock",
            "§6§lOrbitMine§e§ls§d§lSkyBlock",
            "§6§lOrbitMines§d§lSkyBlock",
            "§6§lOrbitMines§5§lS§d§lkyBlock",
            "§6§lOrbitMines§5§lSk§d§lyBlock",
            "§6§lOrbitMines§5§lSky§d§lBlock",
            "§6§lOrbitMines§5§lSkyB§d§llock",
            "§6§lOrbitMines§5§lSkyBl§d§lock",
            "§6§lOrbitMines§5§lSkyBlo§d§lck",
            "§6§lOrbitMines§5§lSkyBloc§d§lk",
            "§6§lOrbitMines§5§lSkyBlock"
    };
    private int index;

    public SkyBlockScoreboard(OMPlayer omp) {
        super(omp, true);

        skyBlock = OrbitMinesSkyBlock.getSkyBlock();
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
        SkyBlockPlayer omp = (SkyBlockPlayer) getOMPlayer();

        addScore(9, "");
        addScore(8, "§e§lOrbitMines Tokens");
        if(omp.isLoaded())
            addScore(7, " " + omp.getOrbitMinesTokens() + "  ");
        else
            addScore(7, " " + SkyBlockMessages.WORD_LOADING.get(omp) + "...  ");
        addScore(6, " ");
        addScore(5, "§b§lVIP Points");
        if(omp.isLoaded())
            addScore(4, " " + omp.getVipPoints());
        else
            addScore(4, " " + SkyBlockMessages.WORD_LOADING.get(omp) + "...");
        addScore(3, "  ");
        addScore(2, "§d§lIsland " + SkyBlockMessages.WORD_NUMBER.get(omp));
        if(omp.isLoaded())
            addScore(1, " " + (omp.hasIsland() ? omp.getIsland().getIslandId() + "" : "/is h") + " ");
        else
            addScore(1, " " + SkyBlockMessages.WORD_LOADING.get(omp) + "... ");
        addScore(0, "   ");
    }

    @Override
    public void updateTeams() {

    }
}
