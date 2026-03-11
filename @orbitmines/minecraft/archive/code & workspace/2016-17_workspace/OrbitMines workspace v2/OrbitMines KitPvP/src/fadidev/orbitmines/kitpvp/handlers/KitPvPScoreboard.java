package fadidev.orbitmines.kitpvp.handlers;

import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.scoreboard.ScoreboardSet;
import fadidev.orbitmines.kitpvp.OrbitMinesKitPvP;

/**
 * Created by Fadi on 10-9-2016.
 */
public class KitPvPScoreboard extends ScoreboardSet {

    private OrbitMinesKitPvP kitPvP;
    private String[] titles = {
            "§6§lOrbitMines§c§lKitPvP", 
			"§e§lO§6§lrbitMines§c§lKitPvP", 
			"§e§lOr§6§lbitMines§c§lKitPvP", 
			"§e§lOrb§6§litMines§c§lKitPvP", 
			"§e§lOrbi§6§ltMines§c§lKitPvP", 
			"§e§lOrbit§6§lMines§c§lKitPvP", 
			"§e§lOrbitM§6§lines§c§lKitPvP", 
			"§e§lOrbitMi§6§lnes§c§lKitPvP", 
			"§e§lOrbitMin§6§les§c§lKitPvP", 
			"§e§lOrbitMine§6§ls§c§lKitPvP", 
			"§e§lOrbitMines§c§lKitPvP", 
			"§e§lOrbitMines§4§lK§c§litPvP", 
			"§e§lOrbitMines§4§lKi§c§ltPvP", 
			"§e§lOrbitMines§4§lKit§c§lPvP", 
			"§e§lOrbitMines§4§lKitP§c§lvP", 
			"§e§lOrbitMines§4§lKitPv§c§lP", 
			"§e§lOrbitMines§4§lKitPvP",
			"§6§lO§e§lrbitMines§4§lKitPvP", 
			"§6§lOr§e§lbitMines§4§lKitPvP", 
			"§6§lOrb§e§litMines§4§lKitPvP", 
			"§6§lOrbi§e§ltMines§4§lKitPvP", 
			"§6§lOrbit§e§lMines§4§lKitPvP", 
			"§6§lOrbitM§e§lines§4§lKitPvP", 
			"§6§lOrbitMi§e§lnes§4§lKitPvP", 
			"§6§lOrbitMin§e§les§4§lKitPvP", 
			"§6§lOrbitMine§e§ls§4§lKitPvP", 
			"§6§lOrbitMines§4§lKitPvP",
			"§6§lOrbitMines§c§lK§4§litPvP", 
			"§6§lOrbitMines§c§lKi§4§ltPvP", 
			"§6§lOrbitMines§c§lKit§4§lPvP", 
			"§6§lOrbitMines§c§lKitP§4§lvP", 
			"§6§lOrbitMines§c§lKitPv§4§lP", 
			"§6§lOrbitMines§c§lKitPv§4§lP", 
			"§6§lOrbitMines§c§lKitPvP"
    };
    private int index;

    public KitPvPScoreboard(OMPlayer omp) {
        super(omp, true);

        kitPvP = OrbitMinesKitPvP.getKitPvP();
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
        KitPvPPlayer omp = (KitPvPPlayer) getOMPlayer();

        addScore(14, "");
        addScore(13, "§f§l" + KitPvPMessages.WORD_CURRENT.get(omp) + " Streak: §6§l" + omp.getCurrentStreak());
        addScore(12, " ");
        addScore(11, "§7§lKit");
        if(omp.isLoaded())
            addScore(10, " " + (omp.getKitSelected() != null ? "§b§l" + omp.getKitSelected().getName() + " §aLvL " + omp.getKitLevelSelected() : KitPvPMessages.SELECTING_KIT.get(omp)));
        else
            addScore(10, " " + KitPvPMessages.WORD_LOADING.get(omp) + "...");
        addScore(9, "  ");
        addScore(8, "§6§lCoins");
        if(omp.isLoaded())
            addScore(7, " " + omp.getMoney() + " ");
        else
            addScore(7, " " + KitPvPMessages.WORD_LOADING.get(omp) + "... ");
        addScore(6, "   ");
        addScore(5, "§c§lKills");
        if(omp.isLoaded())
            addScore(4, " " + omp.getKills() + " ");
        else
            addScore(4, " " + KitPvPMessages.WORD_LOADING.get(omp) + "... ");
        addScore(3, "    ");
        addScore(2, "§4§lDeaths");
        if(omp.isLoaded())
            addScore(1, " " + omp.getDeaths() + " ");
        else
            addScore(1, " " + KitPvPMessages.WORD_LOADING.get(omp) + "... ");
        addScore(0, "     ");
    }

    @Override
    public void updateTeams() {

    }
}
