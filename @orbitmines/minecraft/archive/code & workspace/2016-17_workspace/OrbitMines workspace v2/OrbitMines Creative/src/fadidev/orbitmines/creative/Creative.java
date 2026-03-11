package fadidev.orbitmines.creative;

import fadidev.orbitmines.api.OrbitMinesServer;
import fadidev.orbitmines.api.runnables.orbitmines.NPCRunnable;
import fadidev.orbitmines.api.runnables.orbitmines.PlayerRunnable;
import fadidev.orbitmines.api.runnables.orbitmines.PlayerSecondRunnable;
import fadidev.orbitmines.api.utils.enums.Server;
import fadidev.orbitmines.creative.handlers.CreativePlayer;
import fadidev.orbitmines.creative.runnables.CreativeNPCRunnable;
import fadidev.orbitmines.creative.runnables.CreativePlayerRunnable;
import fadidev.orbitmines.creative.runnables.CreativePlayerSecondRunnable;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by Fadi on 14-9-2016.
 */
public class Creative extends OrbitMinesServer {

    @Override
    public Server getServer() {
        return Server.CREATIVE;
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
        return true;
    }

    @Override
    public boolean gadgetsEnabled() {
        return false;
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
        return false;
    }

    @Override
    public NPCRunnable startNpcRunnable() {
        return new CreativeNPCRunnable();
    }

    @Override
    public PlayerRunnable startPlayerRunnable() {
        return new CreativePlayerRunnable();
    }

    @Override
    public PlayerSecondRunnable startPlayerSecondRunnable() {
        return new CreativePlayerSecondRunnable();
    }

    @Override
    public void updateFormat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        CreativePlayer omp = CreativePlayer.getCreativePlayer(p);

        e.setFormat(omp.getChatFormat());
    }
}
