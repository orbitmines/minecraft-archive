package fadidev.orbitmines.minigames;

import fadidev.orbitmines.api.OrbitMinesServer;
import fadidev.orbitmines.api.runnables.orbitmines.NPCRunnable;
import fadidev.orbitmines.api.runnables.orbitmines.PlayerRunnable;
import fadidev.orbitmines.api.runnables.orbitmines.PlayerSecondRunnable;
import fadidev.orbitmines.api.utils.enums.Server;
import fadidev.orbitmines.minigames.handlers.Arena;
import fadidev.orbitmines.minigames.handlers.players.MiniGamesPlayer;
import fadidev.orbitmines.minigames.runnables.MiniGamesNPCRunnable;
import fadidev.orbitmines.minigames.runnables.MiniGamesPlayerRunnable;
import fadidev.orbitmines.minigames.runnables.MiniGamesPlayerSecondRunnable;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by Fadi on 30-9-2016.
 */
public class MiniGames extends OrbitMinesServer {

    @Override
    public Server getServer() {
        return Server.MINIGAMES;
    }

    @Override
    public int getGadgetSlot() {
        return 7;
    }

    @Override
    public String getSpawnBuilders() {
        return "§b§lMod §bAlderius";
    }

    @Override
    public boolean petsEnabled() {
        return true;
    }

    @Override
    public boolean chatcolorsEnabled() {
        return true;
    }

    @Override
    public boolean disguisesEnabled() {
        return true;
    }

    @Override
    public boolean gadgetsEnabled() {
        return true;
    }

    @Override
    public boolean wardrobeEnabled() {
        return true;
    }

    @Override
    public boolean trailsEnabled() {
        return true;
    }

    @Override
    public boolean hatsEnabled() {
        return true;
    }

    @Override
    public boolean fireworksEnabled() {
        return true;
    }

    @Override
    public NPCRunnable startNpcRunnable() {
        return new MiniGamesNPCRunnable();
    }

    @Override
    public PlayerRunnable startPlayerRunnable() {
        return new MiniGamesPlayerRunnable();
    }

    @Override
    public PlayerSecondRunnable startPlayerSecondRunnable() {
        return new MiniGamesPlayerSecondRunnable();
    }

    @Override
    public void updateFormat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        MiniGamesPlayer omp = MiniGamesPlayer.getMiniGamesPlayer(p);
        Arena arena = omp.getArena();

        if(arena != null){
            String format = omp.getChatFormat().replace("%2$s", e.getMessage());
            arena.sendMessage(format);
        }
        e.setCancelled(true);
    }
}
