package fadidev.orbitmines.prison.events;

import fadidev.orbitmines.prison.OrbitMinesPrison;
import fadidev.orbitmines.prison.handlers.PrisonPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

/**
 * Created by Fadi on 19-9-2016.
 */
public class InteractAtEntityEvent implements Listener {

    private OrbitMinesPrison prison;

    public InteractAtEntityEvent(){
        prison = OrbitMinesPrison.getPrison();
    }

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent e){
        Player p = e.getPlayer();
        PrisonPlayer omp = PrisonPlayer.getPrisonPlayer(p);

        if(omp.isLoaded()){
            if(omp.isOpMode())
                return;

            if(omp.getCell() == null || p.getWorld().getName().equals(prison.getCellWorld().getName()) && !omp.isOnCell(e.getRightClicked().getLocation()))
                e.setCancelled(true);
        }
    }
}
