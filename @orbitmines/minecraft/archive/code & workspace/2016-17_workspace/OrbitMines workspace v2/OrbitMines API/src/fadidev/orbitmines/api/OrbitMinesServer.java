package fadidev.orbitmines.api;

import fadidev.orbitmines.api.managers.ClickManager;
import fadidev.orbitmines.api.runnables.orbitmines.NPCRunnable;
import fadidev.orbitmines.api.runnables.orbitmines.PlayerRunnable;
import fadidev.orbitmines.api.runnables.orbitmines.PlayerSecondRunnable;
import fadidev.orbitmines.api.utils.enums.Server;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by Fadi on 4-9-2016.
 */
public abstract class OrbitMinesServer {

    private OrbitMinesAPI api;

    public OrbitMinesServer(){
        api = OrbitMinesAPI.getApi();

        api.setServerPlugin(this);
    }

    public abstract Server getServer();

    public abstract int getGadgetSlot();

    public abstract String getSpawnBuilders();

    public abstract boolean petsEnabled();

    public abstract boolean chatcolorsEnabled();

    public abstract boolean disguisesEnabled();

    public abstract boolean gadgetsEnabled();

    public abstract boolean wardrobeEnabled();

    public abstract boolean trailsEnabled();

    public abstract boolean hatsEnabled();

    public abstract boolean fireworksEnabled();

    public abstract NPCRunnable startNpcRunnable();

    public abstract PlayerRunnable startPlayerRunnable();

    public abstract PlayerSecondRunnable startPlayerSecondRunnable();

    public abstract void updateFormat(AsyncPlayerChatEvent e);

    public OrbitMinesAPI getApi(){
        return api;
    }

    public ClickManager getClickManager(){
        return api.getClickManager();
    }
}
