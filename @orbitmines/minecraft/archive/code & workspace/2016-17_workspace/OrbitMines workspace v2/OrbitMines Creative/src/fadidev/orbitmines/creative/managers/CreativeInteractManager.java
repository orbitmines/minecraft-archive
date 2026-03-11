package fadidev.orbitmines.creative.managers;

import fadidev.orbitmines.api.managers.InteractManager;
import fadidev.orbitmines.creative.OrbitMinesCreative;
import fadidev.orbitmines.creative.handlers.CreativePlayer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Fadi on 10-9-2016.
 */
public class CreativeInteractManager extends InteractManager {

    /*
     * TODO
     * Not the best code: (Old Code)
     * Should edit this shortly.
     */

    private OrbitMinesCreative creative;
    private CreativePlayer omp;

    public CreativeInteractManager(PlayerInteractEvent e) {
        super(e);

        creative = OrbitMinesCreative.getCreative();
        omp = CreativePlayer.getCreativePlayer(e.getPlayer());
    }

    public void handlePlotInteract(){
        if(omp.isOpMode() || b == null || item == null || item.getType() == Material.TNT || item.getType() == Material.EXPLOSIVE_MINECART)
            return;

        if(p.getWorld().getName().equals(creative.getPlotWorld().getName()) && !omp.isInPvPPlot()){
            Location l = p.getLocation();

            if(a == Action.RIGHT_CLICK_BLOCK && (item.getType() == Material.WATER_BUCKET || item.getType() == Material.LAVA_BUCKET))
                l = e.getClickedBlock().getLocation();

            if(l != null && !omp.isOnPlot(l)) {
                e.setCancelled(true);
                omp.updateInventory();
            }
        }
        else if(p.getWorld().getName().equals(creative.getLobby().getName())){
            e.setCancelled(true);
            omp.updateInventory();
        }
    }

    public void handleMonsterEggs(){
        if(item != null && item.getType() == Material.MONSTER_EGG){
            e.setCancelled(true);
            omp.updateInventory();
        }
    }
}
