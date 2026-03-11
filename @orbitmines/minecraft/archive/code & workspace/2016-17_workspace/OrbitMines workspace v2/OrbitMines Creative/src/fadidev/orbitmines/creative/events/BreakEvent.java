package fadidev.orbitmines.creative.events;

import fadidev.orbitmines.creative.OrbitMinesCreative;
import fadidev.orbitmines.creative.handlers.CreativePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Created by Fadi on 15-9-2016.
 */
public class BreakEvent implements Listener {

    private OrbitMinesCreative creative;

    public BreakEvent(){
        creative = OrbitMinesCreative.getCreative();
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        CreativePlayer omp = CreativePlayer.getCreativePlayer(p);

        if(omp.isLoaded()){
            if(!omp.isOpMode() && (p.getWorld().getName().equals(creative.getPlotWorld().getName())))
                e.setCancelled(omp.isInPvPPlot() && (omp.getKitSelected() != null || !omp.getPvPPlot().canPvPBuild()) || !omp.isOnPlot(e.getBlock().getLocation()));
        }
        else{
            omp.notLoaded();
            e.setCancelled(true);
        }
    }
}
