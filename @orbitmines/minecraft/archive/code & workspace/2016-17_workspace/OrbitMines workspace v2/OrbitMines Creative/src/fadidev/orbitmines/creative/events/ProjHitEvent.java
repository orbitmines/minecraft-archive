package fadidev.orbitmines.creative.events;

import fadidev.orbitmines.creative.handlers.CreativePlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

/**
 * Created by Fadi on 15-9-2016.
 */
public class ProjHitEvent implements Listener {

    @EventHandler
    public void onHit(ProjectileHitEvent e){
        Projectile proj = e.getEntity();

        if(proj instanceof Arrow && proj.getShooter() instanceof Player){
            Player p = (Player) proj.getShooter();
            CreativePlayer omp = CreativePlayer.getCreativePlayer(p);

            if(omp.isInPvPPlot() && !omp.getPvPPlot().hasPvPArrows())
                proj.remove();
        }
    }
}
