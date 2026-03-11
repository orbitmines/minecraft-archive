package fadidev.orbitmines.skyblock;

import fadidev.orbitmines.api.OrbitMinesServer;
import fadidev.orbitmines.api.runnables.orbitmines.NPCRunnable;
import fadidev.orbitmines.api.runnables.orbitmines.PlayerRunnable;
import fadidev.orbitmines.api.runnables.orbitmines.PlayerSecondRunnable;
import fadidev.orbitmines.api.utils.enums.Server;
import fadidev.orbitmines.skyblock.handlers.SkyBlockPlayer;
import fadidev.orbitmines.skyblock.runnables.SkyBlockNPCRunnable;
import fadidev.orbitmines.skyblock.runnables.SkyBlockPlayerRunnable;
import fadidev.orbitmines.skyblock.runnables.SkyBlockPlayerSecondRunnable;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by Fadi on 20-9-2016.
 */
public class SkyBlock extends OrbitMinesServer {

    @Override
    public Server getServer() {
        return Server.SKYBLOCK;
    }

    @Override
    public int getGadgetSlot() {
        return 0;
    }

    @Override
    public String getSpawnBuilders() {
        return "§b§lMod §bsharewoods\n§b§lMod §beekhoorn2000";
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
        return new SkyBlockNPCRunnable();
    }

    @Override
    public PlayerRunnable startPlayerRunnable() {
        return new SkyBlockPlayerRunnable();
    }

    @Override
    public PlayerSecondRunnable startPlayerSecondRunnable() {
        return new SkyBlockPlayerSecondRunnable();
    }

    @Override
    public void updateFormat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        SkyBlockPlayer omp = SkyBlockPlayer.getSkyBlockPlayer(p);

        e.setFormat(omp.getChatFormat());
    }
}
