package fadidev.orbitmines.survival.events;

import fadidev.orbitmines.survival.handlers.SurvivalPlayer;
import fadidev.orbitmines.survival.managers.SurvivalInteractManager;
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
        SurvivalPlayer omp = SurvivalPlayer.getSurvivalPlayer(p);

        if(omp.isLoaded()){
            SurvivalInteractManager manager = new SurvivalInteractManager(e);

            manager.handleClaimHoe();
            manager.handleSpawnInteract();
            manager.handleChestShops();
        }
    }
}
