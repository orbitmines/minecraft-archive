package fadidev.orbitmines.skyblock.events;

import fadidev.orbitmines.skyblock.OrbitMinesSkyBlock;
import fadidev.orbitmines.skyblock.handlers.SkyBlockPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

/**
 * Created by Fadi on 20-9-2016.
 */
public class InteractAtEntityEvent implements Listener {

    private OrbitMinesSkyBlock skyBlock;

    public InteractAtEntityEvent(){
        skyBlock = OrbitMinesSkyBlock.getSkyBlock();
    }

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent e){
        if(!(e.getRightClicked() instanceof ArmorStand))
            return;


        Player p = e.getPlayer();
        SkyBlockPlayer omp = SkyBlockPlayer.getSkyBlockPlayer(p);

        if(p.getWorld().getName().equals(skyBlock.getSkyBlockWorld().getName()) || p.getWorld().getName().equals(skyBlock.getSkyBlockNetherWorld().getName())){
            if(omp.hasIsland()){
                if(!omp.onIsland(e.getRightClicked().getLocation(), true))
                    e.setCancelled(true);
            }
            else{
                e.setCancelled(true);
            }
        }
    }
}
