package fadidev.orbitmines.creative.handlers;

import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.scoreboard.ScoreboardSet;
import fadidev.orbitmines.creative.OrbitMinesCreative;

/**
 * Created by Fadi on 10-9-2016.
 */
public class CreativeScoreboard extends ScoreboardSet {

    private OrbitMinesCreative creative;
    private String[] titles = {
            "&6&lOrbitMines&d&lCreative",
            "&e&lO&6&lrbitMines&d&lCreative",
            "&e&lOr&6&lbitMines&d&lCreative",
            "&e&lOrb&6&litMines&d&lCreative",
            "&e&lOrbi&6&ltMines&d&lCreative",
            "&e&lOrbit&6&lMines&d&lCreative",
            "&e&lOrbitM&6&lines&d&lCreative",
            "&e&lOrbitMi&6&lnes&d&lCreative",
            "&e&lOrbitMin&6&les&d&lCreative",
            "&e&lOrbitMine&6&ls&d&lCreative",
            "&e&lOrbitMines&d&lCreative",
            "&e&lOrbitMines&5&lC&d&lreative",
            "&e&lOrbitMines&5&lCr&d&leative",
            "&e&lOrbitMines&5&lCre&d&lative",
            "&e&lOrbitMines&5&lCrea&d&ltive",
            "&e&lOrbitMines&5&lCreat&d&live",
            "&e&lOrbitMines&5&lCreati&d&lve",
            "&e&lOrbitMines&5&lCreativ&d&le",
            "&e&lOrbitMines&5&lCreative",
            "&6&lO&e&lrbitMines&5&lCreative",
            "&6&lOr&e&lbitMines&5&lCreative",
            "&6&lOrb&e&litMines&5&lCreative",
            "&6&lOrbi&e&ltMines&5&lCreative",
            "&6&lOrbit&e&lMines&5&lCreative",
            "&6&lOrbitM&e&lines&5&lCreative",
            "&6&lOrbitMi&e&lnes&5&lCreative",
            "&6&lOrbitMin&e&les&5&lCreative",
            "&6&lOrbitMine&e&ls&5&lCreative",
            "&6&lOrbitMines&5&lCreative",
            "&6&lOrbitMines&d&lC&5&lreative",
            "&6&lOrbitMines&d&lCr&5&leative",
            "&6&lOrbitMines&d&lCre&5&lative",
            "&6&lOrbitMines&d&lCrea&5&ltive",
            "&6&lOrbitMines&d&lCreat&5&live",
            "&6&lOrbitMines&d&lCreati&5&lve",
            "&6&lOrbitMines&d&lCreativ&5&le",
            "&6&lOrbitMines&d&lCreative"
    };
    private int index;

    public CreativeScoreboard(OMPlayer omp) {
        super(omp, true);

        creative = OrbitMinesCreative.getCreative();
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
        CreativePlayer omp = (CreativePlayer) getOMPlayer();

        addScore(9, "");
        addScore(8, "&e&lOrbitMines Tokens");
        if(omp.isLoaded())
            addScore(7, " " + omp.getOrbitMinesTokens() + "  ");
        else
            addScore(7, " " + CreativeMessages.WORD_LOADING.get(omp) + "...  ");
        addScore(6, " ");
        addScore(5, "&b&lVIP Points");
        if(omp.isLoaded())
            addScore(4, " " + omp.getVipPoints());
        else
            addScore(4, " " + CreativeMessages.WORD_LOADING.get(omp) + "...");
        addScore(3, "  ");
        addScore(2, "&d&lPlot " + CreativeMessages.WORD_NUMBER.get(omp));
        if(omp.isLoaded())
            addScore(1, " " + (omp.hasPlot() ? omp.getPlot().getPlotId() : "/plot h") + " ");
        else
            addScore(1, " " + CreativeMessages.WORD_LOADING.get(omp) + "... ");
        addScore(0, "   ");
    }

    @Override
    public void updateTeams() {

    }
}
