package fadidev.orbitmines.survival.events;

import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.survival.OrbitMinesSurvival;
import fadidev.orbitmines.survival.handlers.Region;
import fadidev.orbitmines.survival.handlers.ShopSign;
import fadidev.orbitmines.survival.handlers.SurvivalPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Created by Fadi-Laptop on 16-9-2016.
 */
public class DamageByEntityEvent implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if(e.getEntity() instanceof Player && e.getDamager() instanceof Player){
            Player pE = (Player) e.getEntity();
            SurvivalPlayer ompE = SurvivalPlayer.getSurvivalPlayer(pE);

            if(!ompE.isInPvP())
                e.setCancelled(true);
        }
    }
}
