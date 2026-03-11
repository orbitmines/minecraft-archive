package fadidev.orbitmines.minigames.runnables;

import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.runnables.orbitmines.PlayerRunnable;
import fadidev.orbitmines.minigames.OrbitMinesMiniGames;
import fadidev.orbitmines.minigames.handlers.Arena;
import fadidev.orbitmines.minigames.handlers.players.MiniGamesPlayer;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

/**
 * Created by Fadi on 30-9-2016.
 */
public class MiniGamesPlayerRunnable extends PlayerRunnable {

    private OrbitMinesMiniGames miniGames;

    public MiniGamesPlayerRunnable(){
        miniGames = OrbitMinesMiniGames.getMiniGames();
    }

    @Override
    protected void run(OMPlayer omPlayer) {
        MiniGamesPlayer omp = (MiniGamesPlayer) omPlayer;
        Player p = omp.getPlayer();
        Arena arena = omp.getArena();

        if(omp.getArena() == null)
            return;

        if(arena.isPlayer(omp)){
            p.setGameMode(GameMode.SURVIVAL);

            arena.getData().run(omp);
        }
        else{
            p.setGameMode(GameMode.SPECTATOR);
            miniGames.getSpectatorKit().get(omp.getLanguage()).replaceItems(p);
        }
    }
}
