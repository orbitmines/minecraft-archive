package fadidev.orbitmines.prison.events;

import fadidev.orbitmines.prison.handlers.PrisonPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Created by Fadi on 19-9-2016.
 */
public class DamageEvent implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if(e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();
            PrisonPlayer omp = PrisonPlayer.getPrisonPlayer(p);

            if(!omp.isInPvP())
                e.setCancelled(true);
        }
        else{
            e.setCancelled(true);
        }
    }
}
