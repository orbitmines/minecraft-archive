package fadidev.orbitmines.creative.events;

import fadidev.orbitmines.creative.OrbitMinesCreative;
import fadidev.orbitmines.creative.handlers.CreativePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlaceEvent implements Listener {

    private OrbitMinesCreative creative;

    public PlaceEvent(){
        creative = OrbitMinesCreative.getCreative();
    }

	@EventHandler
	public void onPlace(BlockPlaceEvent e){
		Player p = e.getPlayer();
		CreativePlayer omp = CreativePlayer.getCreativePlayer(p);
		
		if(omp.isLoaded()){
			if(!omp.isOpMode()){
                if(p.getWorld().getName().equals(creative.getPlotWorld().getName()))
                    e.setCancelled(omp.isInPvPPlot() && (omp.getKitSelected() != null || !omp.getPvPPlot().canPvPBuild()) || !omp.isOnPlot(e.getBlockPlaced().getLocation()));
                else if(p.getWorld().getName().equals(creative.getLobby().getName()))
                    e.setCancelled(true);
			}
		}
		else{
			e.setCancelled(true);
			omp.notLoaded();
		}
	}
}
