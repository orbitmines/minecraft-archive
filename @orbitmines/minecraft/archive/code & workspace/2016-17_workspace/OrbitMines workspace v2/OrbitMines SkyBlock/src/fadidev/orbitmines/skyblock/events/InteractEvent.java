package fadidev.orbitmines.skyblock.events;

import fadidev.orbitmines.skyblock.handlers.SkyBlockPlayer;
import fadidev.orbitmines.skyblock.managers.SkyBlockInteractManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Fadi on 10-9-2016.
 */
public class InteractEvent implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        SkyBlockPlayer omp = SkyBlockPlayer.getSkyBlockPlayer(p);

        if(omp.isLoaded()){
            SkyBlockInteractManager manager = new SkyBlockInteractManager(e);

            manager.handleIsland();
        }
    }
}
