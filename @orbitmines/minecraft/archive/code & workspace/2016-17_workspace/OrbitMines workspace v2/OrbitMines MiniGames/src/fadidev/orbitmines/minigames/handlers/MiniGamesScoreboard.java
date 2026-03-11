package fadidev.orbitmines.minigames.handlers;

import fadidev.orbitmines.api.handlers.Kit;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.scoreboard.ScoreboardSet;
import fadidev.orbitmines.minigames.OrbitMinesMiniGames;
import fadidev.orbitmines.minigames.handlers.data.GhostAttackData;
import fadidev.orbitmines.minigames.handlers.players.MiniGamesPlayer;
import fadidev.orbitmines.minigames.utils.enums.MiniGameType;
import fadidev.orbitmines.minigames.utils.enums.State;
import fadidev.orbitmines.minigames.utils.enums.TicketType;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.NameTagVisibility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fadi on 10-9-2016.
 */
public class MiniGamesScoreboard extends ScoreboardSet {

    private OrbitMinesMiniGames miniGames;
    private String[] titles = {
            "§6§lOrbitMines§f§lMiniGames",
		    "§e§lO§6§lrbitMines§f§lMiniGames",
            "§e§lOr§6§lbitMines§f§lMiniGames",
            "§e§lOrb§6§litMines§f§lMiniGames",
            "§e§lOrbi§6§ltMines§f§lMiniGames",
            "§e§lOrbit§6§lMines§f§lMiniGames",
            "§e§lOrbitM§6§lines§f§lMiniGames",
            "§e§lOrbitMi§6§lnes§f§lMiniGames",
            "§e§lOrbitMin§6§les§f§lMiniGames",
            "§e§lOrbitMine§6§ls§f§lMiniGames",
            "§e§lOrbitMines§f§lMiniGames",
            "§e§lOrbitMines§7§lM§f§liniGames",
            "§e§lOrbitMines§7§lMi§f§lniGames",
            "§e§lOrbitMines§7§lMin§f§liGames",
            "§e§lOrbitMines§7§lMini§f§lGames",
            "§e§lOrbitMines§7§lMiniG§f§lames",
            "§e§lOrbitMines§7§lMiniGa§f§lmes",
            "§e§lOrbitMines§7§lMiniGam§f§les",
            "§e§lOrbitMines§7§lMiniGame§f§ls",
            "§e§lOrbitMines§7§lMiniGames",
            "§6§lO§e§lrbitMines§7§lMiniGames",
            "§6§lOr§e§lbitMines§7§lMiniGames",
            "§6§lOrb§e§litMines§7§lMiniGames",
            "§6§lOrbi§e§ltMines§7§lMiniGames",
            "§6§lOrbit§e§lMines§7§lMiniGames",
            "§6§lOrbitM§e§lines§7§lMiniGames",
            "§6§lOrbitMi§e§lnes§7§lMiniGames",
            "§6§lOrbitMin§e§les§7§lMiniGames",
            "§6§lOrbitMine§e§ls§7§lMiniGames",
            "§6§lOrbitMines§7§lMiniGames",
            "§6§lOrbitMines§f§lM§7§liniGames",
            "§6§lOrbitMines§f§lMi§7§lniGames",
            "§6§lOrbitMines§f§lMin§7§liGames",
            "§6§lOrbitMines§f§lMini§7§lGames",
            "§6§lOrbitMines§f§lMiniG§7§lames",
            "§6§lOrbitMines§f§lMiniGa§7§lmes",
            "§6§lOrbitMines§f§lMiniGam§7§les",
            "§6§lOrbitMines§f§lMiniGame§7§ls",
            "§6§lOrbitMines§f§lMiniGames",
    };
    private int index;

    public MiniGamesScoreboard(OMPlayer omp) {
        super(omp, true);

        miniGames = OrbitMinesMiniGames.getMiniGames();
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
        MiniGamesPlayer omp = (MiniGamesPlayer) getOMPlayer();
        Arena arena = omp.getArena();

        if(arena == null){
            addScore(0, "");
            return;
        }

        if(arena.getState() == State.ENDING || arena.getState() == State.IN_GAME || arena.getState() == State.WARMUP){
            arena.getData().updateScoreboard(this);
            return;
        }

        if(arena.getState() != State.WAITING && arena.getState() != State.STARTING){
            addScore(0, "");
            return;
        }

        if(arena.getPlayers().size() >= 3){
            addScore(12, "");
            addScore(11, "§6§l" + MiniGamesMessages.WORD_TIME_LEFT.get(omp));
            addScore(10, " " + arena.getMinutes() + "m " + arena.getSeconds() + "s");
        }
        else{
            addScore(11, "");
            addScore(10, "§6§l§o" + MiniGamesMessages.WORD_WAITING.get(omp) + "...");
        }

        addScore(9, " ");
        addScore(8, "§a§l" + MiniGamesMessages.WORD_PLAYERS.get(omp));
        addScore(7, " " + arena.getPlayers().size());
        addScore(6, "  ");
        addScore(5, "§e§lKit");

        Kit kit = arena.getData().getArenaPlayer(omp).getKitSelected();
        if(kit != null){
            addScore(4, " " + TicketType.valueOf(kit.getName()).getName().replace(" Kit", ""));
        }
        else{
            addScore(4, " " + MiniGamesMessages.WORD_NONE.get(omp));
        }
        addScore(3, "   ");

        addScore(2, "§f§lMiniGame Coins");
        if(omp.isLoaded())
            addScore(1, " " + omp.getMiniGameCoins() + " ");
        else
            addScore(1, " " + MiniGamesMessages.WORD_LOADING.get(omp) + "... ");
        addScore(0, "    ");
    }

    @Override
    public void updateTeams() {
        MiniGamesPlayer omp = (MiniGamesPlayer) getOMPlayer();
        Arena arena = omp.getArena();

        if(arena != null)
            setUseRankTeams(arena.getState() == State.WAITING || arena.getState() == State.STARTING);
    }

    public void updateAliveTeams(){
        MiniGamesPlayer omp = (MiniGamesPlayer) getOMPlayer();
        Arena arena = omp.getArena();

        List<Player> players = new ArrayList<>();
        List<Player> spectators = new ArrayList<>();

        for(MiniGamesPlayer omplayer : arena.getPlayers()){
            players.add(omplayer.getPlayer());

            if(!arena.isSpectator(omp))
                continue;

            if(arena.getType() != MiniGameType.GHOST_ATTACK) {
                omp.getPlayer().showPlayer(omplayer.getPlayer());
                continue;
            }

            GhostAttackData gaData = (GhostAttackData) arena.getData();
            if(!gaData.isGhost(omplayer) || gaData.isRevealed())
                omp.getPlayer().showPlayer(omplayer.getPlayer());
            else
                omp.getPlayer().hidePlayer(omplayer.getPlayer());
        }
        for(MiniGamesPlayer omplayer : arena.getSpectators()){
            spectators.add(omplayer.getPlayer());
            miniGames.getApi().getNms().entity().setInvisible(omplayer.getPlayer(), true);

            if(arena.isSpectator(omp))
                omp.getPlayer().showPlayer(omplayer.getPlayer());
            else
                omp.getPlayer().hidePlayer(omplayer.getPlayer());
        }

        addTeam("PlayersMG", players);
        addTeam("SpectateMG", spectators);

        setTeamPrefix("PlayersMG", "§a");
        setTeamPrefix("SpectateMG", "§c");

        setTeamCanSeeFriendlyInvisibles("PlayersMG", true);
        if(arena.getType() == MiniGameType.GHOST_ATTACK) {
            setTeamNameTagVisibility("PlayersMG", NameTagVisibility.NEVER);
            setTeamCanSeeFriendlyInvisibles("PlayersMG", ((GhostAttackData) arena.getData()).isRevealed());
        }

        setTeamCanSeeFriendlyInvisibles("SpectateMG", true);
    }
}
