package fadidev.orbitmines.hub;

import fadidev.orbitmines.api.OrbitMinesServer;
import fadidev.orbitmines.api.runnables.orbitmines.NPCRunnable;
import fadidev.orbitmines.api.runnables.orbitmines.PlayerRunnable;
import fadidev.orbitmines.api.runnables.orbitmines.PlayerSecondRunnable;
import fadidev.orbitmines.api.utils.enums.Server;
import fadidev.orbitmines.hub.handlers.players.HubPlayer;
import fadidev.orbitmines.hub.runnables.HubNPCRunnable;
import fadidev.orbitmines.hub.runnables.HubPlayerRunnable;
import fadidev.orbitmines.hub.runnables.HubPlayerSecondRunnable;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by Fadi on 7-9-2016.
 */
public class Hub extends OrbitMinesServer {

    @Override
    public Server getServer() {
        return Server.HUB;
    }

    @Override
    public int getGadgetSlot() {
        return 5;
    }

    @Override
    public String getSpawnBuilders() {
        return "§b§lMod §bAlderius\n§b§lMod §bsharewoods\n§b§lMod §beekhoorn2000\n§d§lBuilder §dcasidas\n§4§lOwner §4O_o_Fadi_o_O";
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
        return new HubNPCRunnable();
    }

    @Override
    public PlayerRunnable startPlayerRunnable() {
        return new HubPlayerRunnable();
    }

    @Override
    public PlayerSecondRunnable startPlayerSecondRunnable() {
        return new HubPlayerSecondRunnable();
    }

    @Override
    public void updateFormat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        HubPlayer omp = HubPlayer.getHubPlayer(p);

        e.setFormat(omp.getChatFormat());
    }
}
