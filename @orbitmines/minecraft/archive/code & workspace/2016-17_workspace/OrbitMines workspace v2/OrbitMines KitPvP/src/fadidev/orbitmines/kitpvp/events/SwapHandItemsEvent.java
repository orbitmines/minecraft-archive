package fadidev.orbitmines.kitpvp.events;

import fadidev.orbitmines.kitpvp.handlers.KitPvPPlayer;
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
        KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer(p);

        if(!omp.isOpMode() && omp.getKitSelected() == null)
            e.setCancelled(true);
    }
}
