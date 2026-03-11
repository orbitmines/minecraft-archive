package fadidev.orbitmines.prison.events;

import fadidev.orbitmines.prison.OrbitMinesPrison;
import fadidev.orbitmines.prison.handlers.PrisonPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Created by Fadi on 19-9-2016.
 */
public class DamageByEntityEvent implements Listener {

    private OrbitMinesPrison prison;

    public DamageByEntityEvent(){
        prison = OrbitMinesPrison.getPrison();
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Player && e.getEntity() instanceof Player){
            Player pD = (Player) e.getDamager();
            Player pE = (Player) e.getEntity();

            if(pD.getWorld().getName().equals(prison.getLobby().getName())){
                PrisonPlayer ompD = PrisonPlayer.getPrisonPlayer(pD);
                PrisonPlayer ompE = PrisonPlayer.getPrisonPlayer(pE);

                if(!ompD.isInPvP() || !ompE.isInPvP())
                    e.setCancelled(true);
            }
            else{
                e.setCancelled(true);
            }
        }
        else if(e.getDamager() instanceof Player){
            Player pD = (Player) e.getDamager();
            PrisonPlayer ompD = PrisonPlayer.getPrisonPlayer(pD);

            if(!ompD.isOpMode() && pD.getWorld().getName().equals(prison.getCellWorld().getName()) && !ompD.isOnCell(e.getEntity().getLocation()))
                e.setCancelled(true);
        }
    }
}
