package fadidev.orbitmines.creative.events;

import fadidev.orbitmines.creative.OrbitMinesCreative;
import fadidev.orbitmines.creative.handlers.CreativePlayer;
import fadidev.orbitmines.creative.handlers.Plot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

/**
 * Created by Fadi on 15-9-2016.
 */
public class DeathEvent implements Listener {

    private OrbitMinesCreative creative;

    public DeathEvent(){
        creative = OrbitMinesCreative.getCreative();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        final Player p = e.getEntity();
        final CreativePlayer omp = CreativePlayer.getCreativePlayer(p);

        if(omp.isInPvPPlot()){
            final Plot plot = omp.getPvPPlot();

            if(!plot.hasPvPDrops())
                e.getDrops().clear();

            omp.setKitSelected(null);
            p.setHealth(20D);

            new BukkitRunnable(){
                public void run(){
                    p.teleport(plot.getPvPLobbyLocation());
                    p.setVelocity(new Vector(0, 0, 0));
                    p.setFireTicks(0);
                    omp.clearInventory();
                    omp.clearPotionEffects();
                }
            }.runTaskLater(creative, 1);
        }
    }
}
