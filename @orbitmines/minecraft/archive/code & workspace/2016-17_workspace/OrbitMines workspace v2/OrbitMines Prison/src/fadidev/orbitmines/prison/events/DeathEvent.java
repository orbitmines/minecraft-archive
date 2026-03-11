package fadidev.orbitmines.prison.events;

import fadidev.orbitmines.prison.OrbitMinesPrison;
import fadidev.orbitmines.prison.handlers.PrisonPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

/**
 * Created by Fadi on 19-9-2016.
 */
public class DeathEvent implements Listener {

    private OrbitMinesPrison prison;

    public DeathEvent(){
        prison = OrbitMinesPrison.getPrison();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        final Player p = e.getEntity();
        final PrisonPlayer omp = PrisonPlayer.getPrisonPlayer(p);

        p.setHealth(20D);
        p.setFoodLevel(20);

        new BukkitRunnable(){
            public void run(){
                p.teleport(prison.getSpawn());
                p.setVelocity(new Vector(0, 0, 0));
                p.setFireTicks(0);
                omp.clearLevels();
                omp.clearPotionEffects();
            }
        }.runTaskLater(prison, 1);
    }
}
