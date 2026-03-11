package fadidev.orbitmines.skyblock.events;

import fadidev.orbitmines.api.utils.enums.ranks.VIPRank;
import fadidev.orbitmines.skyblock.OrbitMinesSkyBlock;
import fadidev.orbitmines.skyblock.handlers.SkyBlockPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

/**
 * Created by Fadi on 20-9-2016.
 */
public class DeathEvent implements Listener {

    private OrbitMinesSkyBlock skyBlock;

    public DeathEvent(){
        skyBlock = OrbitMinesSkyBlock.getSkyBlock();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        final Player p = e.getEntity();
        final SkyBlockPlayer omp = SkyBlockPlayer.getSkyBlockPlayer(p);

        p.setHealth(20D);
        p.setFoodLevel(20);
        p.setFallDistance(0);

        new BukkitRunnable(){
            public void run(){
                p.teleport(skyBlock.getSpawn());
                p.setVelocity(new Vector(0, 0, 0));
                p.setFireTicks(0);
                if(!omp.hasPerms(VIPRank.DIAMOND_VIP))
                    omp.clearLevels();
                omp.clearPotionEffects();
            }
        }.runTaskLater(skyBlock, 1);
    }
}
