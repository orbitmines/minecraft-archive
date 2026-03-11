package fadidev.orbitmines.skyblock.events;

import fadidev.orbitmines.skyblock.OrbitMinesSkyBlock;
import fadidev.orbitmines.skyblock.handlers.SkyBlockPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Created by Fadi on 20-9-2016.
 */
public class DamageByEntityEvent implements Listener {

    private OrbitMinesSkyBlock skyBlock;

    public DamageByEntityEvent(){
        skyBlock = OrbitMinesSkyBlock.getSkyBlock();
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Player){
            Player pD = (Player) e.getDamager();
            SkyBlockPlayer ompD = SkyBlockPlayer.getSkyBlockPlayer(pD);

            if(pD.getWorld().getName().equals(skyBlock.getSkyBlockWorld().getName()) || pD.getWorld().getName().equals(skyBlock.getSkyBlockNetherWorld().getName())){
                if(ompD.hasIsland()){
                    if(!ompD.onIsland(e.getEntity().getLocation(), true))
                        e.setCancelled(true);
                }
                else{
                    e.setCancelled(true);
                }
            }
            else if(pD.getWorld().getName().equals(skyBlock.getLobby().getName())){
                e.setCancelled(true);
            }
        }
    }
}
