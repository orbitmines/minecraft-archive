package fadidev.orbitmines.survival.events;

import fadidev.orbitmines.survival.OrbitMinesSurvival;
import fadidev.orbitmines.survival.handlers.SurvivalPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

/**
 * Created by Fadi-Laptop on 16-9-2016.
 */
public class DeathEvent implements Listener {

    private OrbitMinesSurvival survival;

    public DeathEvent(){
        survival = OrbitMinesSurvival.getSurvival();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        final Player p = e.getEntity();
        final SurvivalPlayer omp = SurvivalPlayer.getSurvivalPlayer(p);

        p.setHealth(20D);
        p.setFoodLevel(20);
        omp.setBackLocation(p.getLocation());

        new BukkitRunnable(){
            public void run(){
                p.teleport(survival.getSpawn());
                p.setVelocity(new Vector(0, 0, 0));
                p.setFireTicks(0);
                omp.clearLevels();
                omp.clearPotionEffects();
            }
        }.runTaskLater(survival, 1);
    }
}
