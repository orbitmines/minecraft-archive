package fadidev.orbitmines.hub.events;

import fadidev.orbitmines.hub.handlers.players.HubPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

/**
 * Created by Fadi on 12-9-2016.
 */
public class PickupEvent implements Listener {

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e){
        Player p = e.getPlayer();
        HubPlayer omp = HubPlayer.getHubPlayer(p);

        if(!omp.isOpMode() && !omp.inBuilderWorld()) {
            e.setCancelled(true);
            omp.updateInventory();
        }
    }
}
