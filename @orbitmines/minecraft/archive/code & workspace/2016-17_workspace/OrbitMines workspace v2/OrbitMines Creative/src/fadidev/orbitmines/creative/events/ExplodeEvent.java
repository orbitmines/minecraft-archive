package fadidev.orbitmines.creative.events;

import fadidev.orbitmines.api.handlers.Particle;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;

/**
 * Created by Fadi on 15-9-2016.
 */
public class ExplodeEvent implements Listener {

    @EventHandler
    public void entityExplodeEvent(ExplosionPrimeEvent e){
        e.setFire(false);

        if(e.getEntity() instanceof TNTPrimed){
            e.setCancelled(true);

            for(Player player : Bukkit.getOnlinePlayers()){
                player.playSound(e.getEntity().getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 5, 1);

                Particle p = new Particle(org.bukkit.Particle.EXPLOSION_HUGE, e.getEntity().getLocation());
                p.setAmount(4);
                p.send(Bukkit.getOnlinePlayers());
            }
        }
    }
}
