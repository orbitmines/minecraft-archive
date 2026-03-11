package fadidev.orbitmines.prison.events;

import fadidev.orbitmines.prison.handlers.PrisonPlayer;
import fadidev.orbitmines.prison.managers.PrisonInteractManager;
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
        PrisonPlayer omp = PrisonPlayer.getPrisonPlayer(p);

        if(omp.isLoaded()){
            PrisonInteractManager manager = new PrisonInteractManager(e);

            manager.handlePhysical();
            manager.handleChestShops();
            manager.handleMineSigns();
            manager.handleShopSigns();
            manager.handleShops();
        }
    }
}
