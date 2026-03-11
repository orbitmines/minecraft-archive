package fadidev.orbitmines.hub.events;

import fadidev.orbitmines.hub.handlers.players.HubPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Created by Fadi on 8-9-2016.
 */
public class BreakEvent implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        HubPlayer omp = HubPlayer.getHubPlayer(p);

        if(!omp.isOpMode() && !omp.inBuilderWorld())
            e.setCancelled(true);
    }
}
