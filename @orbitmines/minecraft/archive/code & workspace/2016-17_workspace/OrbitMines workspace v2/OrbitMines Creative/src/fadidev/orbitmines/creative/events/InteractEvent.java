package fadidev.orbitmines.creative.events;

import fadidev.orbitmines.creative.handlers.CreativePlayer;
import fadidev.orbitmines.creative.managers.CreativeInteractManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Fadi on 10-9-2016.
 */
public class InteractEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        CreativePlayer omp = CreativePlayer.getCreativePlayer(p);

        if(omp.isLoaded()){
            CreativeInteractManager manager = new CreativeInteractManager(e);

            manager.handlePlotInteract();
            manager.handleMonsterEggs();
        }
    }
}
