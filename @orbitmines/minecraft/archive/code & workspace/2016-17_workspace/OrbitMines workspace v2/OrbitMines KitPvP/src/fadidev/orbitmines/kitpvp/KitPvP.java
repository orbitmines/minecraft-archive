package fadidev.orbitmines.kitpvp;

import fadidev.orbitmines.api.OrbitMinesServer;
import fadidev.orbitmines.api.runnables.orbitmines.NPCRunnable;
import fadidev.orbitmines.api.runnables.orbitmines.PlayerRunnable;
import fadidev.orbitmines.api.runnables.orbitmines.PlayerSecondRunnable;
import fadidev.orbitmines.api.utils.enums.Server;
import fadidev.orbitmines.kitpvp.handlers.KitPvPPlayer;
import fadidev.orbitmines.kitpvp.runnables.KitPvPNPCRunnable;
import fadidev.orbitmines.kitpvp.runnables.KitPvPPlayerRunnable;
import fadidev.orbitmines.kitpvp.runnables.KitPvPPlayerSecondRunnable;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by Fadi on 10-9-2016.
 */
public class KitPvP extends OrbitMinesServer {

    @Override
    public Server getServer() {
        return Server.KITPVP;
    }

    @Override
    public int getGadgetSlot() {
        return 0;
    }

    @Override
    public String getSpawnBuilders() {
        return "§b§lMod §bAlderius";
    }

    @Override
    public boolean petsEnabled() {
        return false;
    }

    @Override
    public boolean chatcolorsEnabled() {
        return true;
    }

    @Override
    public boolean disguisesEnabled() {
        return false;
    }

    @Override
    public boolean gadgetsEnabled() {
        return false;
    }

    @Override
    public boolean wardrobeEnabled() {
        return false;
    }

    @Override
    public boolean trailsEnabled() {
        return true;
    }

    @Override
    public boolean hatsEnabled() {
        return false;
    }

    @Override
    public boolean fireworksEnabled() {
        return false;
    }

    @Override
    public NPCRunnable startNpcRunnable() {
        return new KitPvPNPCRunnable();
    }

    @Override
    public PlayerRunnable startPlayerRunnable() {
        return new KitPvPPlayerRunnable();
    }

    @Override
    public PlayerSecondRunnable startPlayerSecondRunnable() {
        return new KitPvPPlayerSecondRunnable();
    }

    @Override
    public void updateFormat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer(p);

        e.setFormat(omp.getChatFormat());
    }
}
