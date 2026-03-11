package fadidev.orbitmines.api.events;

import org.bukkit.entity.Creeper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

/**
 * Created by Fadi on 7-9-2016.
 */
public class DeathEvent implements Listener {

    @EventHandler
    public void onDeath(EntityDeathEvent e){
        if(e.getEntity() instanceof Creeper)
            e.getDrops().clear();
    }
}
