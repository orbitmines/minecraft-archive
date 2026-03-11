package fadidev.orbitmines.creative.events;

import fadidev.orbitmines.creative.handlers.CreativePlayer;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

/**
 * Created by Fadi on 15-9-2016.
 */
public class PickupEvent implements Listener {

    @EventHandler
    public void onPickUp(PlayerPickupItemEvent e){
        Player p = e.getPlayer();
        CreativePlayer omp = CreativePlayer.getCreativePlayer(p);

        e.setCancelled(p.getGameMode() == GameMode.CREATIVE || (omp.isInPvPPlot() && (omp.getKitSelected() == null || !omp.getPvPPlot().hasPvPDrops())));
    }
}
