package fadidev.orbitmines.survival.events;

import fadidev.orbitmines.survival.OrbitMinesSurvival;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Created by Fadi-Laptop on 16-9-2016.
 */
public class DamageEvent implements Listener {

    private OrbitMinesSurvival survival;

    public DamageEvent(){
        survival = OrbitMinesSurvival.getSurvival();
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if(e.getEntity() instanceof Player && e.getCause() == EntityDamageEvent.DamageCause.FALL){
            Player pE = (Player) e.getEntity();

            if(survival.getNoFall().contains(pE)){
                e.setCancelled(true);
                survival.getNoFall().remove(pE);
            }
        }
    }
}
