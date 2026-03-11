package fadidev.orbitmines.hub.handlers;

import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.scoreboard.ScoreboardSet;
import fadidev.orbitmines.hub.OrbitMinesHub;
import fadidev.orbitmines.hub.handlers.players.HubPlayer;

/**
 * Created by Fadi on 10-9-2016.
 */
public class HubScoreboard extends ScoreboardSet {

    private OrbitMinesHub hub;
    private String[] titles = {
            "§6§lOrbitMines§4§lNetwork",
            "§e§lO§6§lrbitMines§4§lNetwork",
			"§e§lOr§6§lbitMines§4§lNetwork",
			"§e§lOrb§6§litMines§4§lNetwork",
            "§e§lOrbi§6§ltMines§4§lNetwork",
            "§e§lOrbit§6§lMines§4§lNetwork",
            "§e§lOrbitM§6§lines§4§lNetwork",
            "§e§lOrbitMi§6§lnes§4§lNetwork",
            "§e§lOrbitMin§6§les§4§lNetwork",
            "§e§lOrbitMine§6§ls§4§lNetwork",
            "§e§lOrbitMines§4§lNetwork",
            "§e§lOrbitMines§c§lN§4§letwork",
            "§e§lOrbitMines§c§lNe§4§ltwork",
            "§e§lOrbitMines§c§lNet§4§lwork",
            "§e§lOrbitMines§c§lNetw§4§lork",
            "§e§lOrbitMines§c§lNetwo§4§lrk",
            "§e§lOrbitMines§c§lNetwor§4§lk",
            "§e§lOrbitMines§c§lNetwork",
            "§6§lO§e§lrbitMines§c§lNetwork",
            "§6§lOr§e§lbitMines§c§lNetwork",
            "§6§lOrb§e§litMines§c§lNetwork",
            "§6§lOrbi§e§ltMines§c§lNetwork",
            "§6§lOrbit§e§lMines§c§lNetwork",
            "§6§lOrbitM§e§lines§c§lNetwork",
            "§6§lOrbitMi§e§lnes§c§lNetwork",
            "§6§lOrbitMin§e§les§c§lNetwork",
            "§6§lOrbitMine§e§ls§c§lNetwork",
            "§6§lOrbitMines§c§lNetwork",
            "§6§lOrbitMines§4§lN§c§letwork",
            "§6§lOrbitMines§4§lNe§c§ltwork",
            "§6§lOrbitMines§4§lNet§c§lwork",
            "§6§lOrbitMines§4§lNetw§c§lork",
            "§6§lOrbitMines§4§lNetwo§c§lrk",
            "§6§lOrbitMines§4§lNetwor§c§lk",
            "§6§lOrbitMines§4§lNetwork"
    };
    private int index;

    public HubScoreboard(OMPlayer omp) {
        super(omp, true);

        hub = OrbitMinesHub.getHub();
        this.index = 0;
    }

    @Override
    public void updateTitle() {
        HubPlayer omp = (HubPlayer) getOMPlayer();
        if(!omp.hasScoreboardEnabled())
            return;

        if(index >= titles.length)
            index = 0;

        setTitle(titles[index]);

        index++;
    }

    @Override
    public void updateScores() {
        HubPlayer omp = (HubPlayer) getOMPlayer();
        if(!omp.hasScoreboardEnabled())
            return;

        addScore(14, "");
        addScore(13, "§e§lOrbitMines Tokens");
        if(omp.isLoaded())
            addScore(12, " " + omp.getOrbitMinesTokens() + "  ");
        else
            addScore(12, " " + HubMessages.WORD_LOADING.get(omp) + "...  ");
        addScore(11, " ");
        addScore(10, "§b§lVIP Points");
        if(omp.isLoaded())
            addScore(9, " " + omp.getVipPoints());
        else
            addScore(9, " " + HubMessages.WORD_LOADING.get(omp) + "...");
        addScore(8, "  ");
        addScore(7, "§f§lMiniGame Coins");
        if(omp.isLoaded())
            addScore(6, " " + omp.getMiniGameCoins() + " ");
        else
            addScore(6, " " + HubMessages.WORD_LOADING.get(omp) + "... ");
        addScore(5, "   ");
        if(!omp.getPlayer().getWorld().getName().equals(hub.getLobby().getName()) || omp.getPlayer().getLocation().distance(hub.getMiniGameLocation()) > 16) {
            addScore(4, "§c§lRank");
            if(omp.isLoaded())
                addScore(3, " " + omp.getRankString());
            else
                addScore(3, " " + HubMessages.WORD_LOADING.get(omp) + "...   ");
        }
        else{
            addScore(4, "§f§lTickets");
            if(omp.isLoaded())
                addScore(3, " " + omp.getMiniGameTickets() + "   ");
            else
                addScore(3, " " + HubMessages.WORD_LOADING.get(omp) + "...   ");
        }
        addScore(2, "    ");
        addScore(1, "§d§l" + HubMessages.WORD_ALL_PLAYERS.get(omp) + ": §f#" + hub.getPlayerCounter());
        addScore(0, "     ");
    }

    @Override
    public void updateTeams() {

    }
}
