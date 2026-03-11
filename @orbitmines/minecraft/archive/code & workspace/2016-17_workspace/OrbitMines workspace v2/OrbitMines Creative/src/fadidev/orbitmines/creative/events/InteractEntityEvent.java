package fadidev.orbitmines.creative.events;

import fadidev.orbitmines.creative.OrbitMinesCreative;
import fadidev.orbitmines.creative.handlers.CreativePlayer;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Fadi on 15-9-2016.
 */
public class InteractEntityEvent implements Listener {

    private OrbitMinesCreative creative;

    public InteractEntityEvent(){
        creative = OrbitMinesCreative.getCreative();
    }

    @EventHandler
    public void onPlayerEntityInteract(PlayerInteractEntityEvent e){
        Player p = e.getPlayer();
        CreativePlayer omp = CreativePlayer.getCreativePlayer(p);
        Entity en = e.getRightClicked();
        ItemStack item = p.getItemInHand();

        if(omp.isLoaded()){
           if(!omp.isOpMode()){
                if(p.getWorld().getName().equals(creative.getPlotWorld().getName())){
                    if(!(en instanceof Player) && !omp.isOnPlot(en.getLocation())){
                        e.setCancelled(true);
                    }
                }
                else if(p.getWorld().getName().equals(creative.getLobby().getName())){
                    e.setCancelled(true);
                }

                if(item != null && item.getType() == Material.MONSTER_EGG){
                    e.setCancelled(true);
                    omp.updateInventory();
                }
            }
        }
        else{
            e.setCancelled(true);
            omp.updateInventory();
            omp.notLoaded();
        }
    }
}
