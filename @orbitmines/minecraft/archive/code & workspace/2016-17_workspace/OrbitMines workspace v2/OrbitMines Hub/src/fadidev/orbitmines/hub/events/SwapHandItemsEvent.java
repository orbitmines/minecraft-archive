package fadidev.orbitmines.hub.events;

import fadidev.orbitmines.hub.handlers.players.HubPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

/**
 * Created by Fadi on 27-9-2016.
 */
public class SwapHandItemsEvent implements Listener {

    @EventHandler
    public void onSwap(PlayerSwapHandItemsEvent e){
        Player p = e.getPlayer();
        HubPlayer omp = HubPlayer.getHubPlayer(p);

        if(!omp.isOpMode() && !omp.inBuilderWorld())
            e.setCancelled(true);
    }
}
