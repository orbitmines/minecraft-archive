package fadidev.orbitmines.creative.events;

import fadidev.orbitmines.api.handlers.Cooldowns;
import fadidev.orbitmines.api.handlers.chat.Title;
import fadidev.orbitmines.creative.OrbitMinesCreative;
import fadidev.orbitmines.creative.handlers.CreativeMessages;
import fadidev.orbitmines.creative.handlers.CreativePlayer;
import fadidev.orbitmines.creative.handlers.Plot;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by Fadi on 15-9-2016.
 */
public class MoveEvent implements Listener {

    private OrbitMinesCreative creative;

    public MoveEvent(){
        creative = OrbitMinesCreative.getCreative();
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        CreativePlayer omp = CreativePlayer.getCreativePlayer(p);

        if(omp.isLoaded()){
            if(omp.getCooldowns().containsKey(Cooldowns.TELEPORTING)){
                Location l = omp.getTpLocation();
                if(p.getLocation().getX() == l.getX() && p.getLocation().getY() == l.getY() && p.getLocation().getZ() == l.getZ())
                    return;

                omp.removeCooldown(Cooldowns.TELEPORTING);

                if(omp.isInPvPPlot()){
                    Title t = new Title("", CreativeMessages.CANCEL_PLOT_TP.get(omp), 0, 40, 20);
                    t.send(p);
                }
                else{
                    Title t = new Title("", CreativeMessages.CANCEL_SPAWN_TP.get(omp), 0, 40, 20);
                    t.send(p);
                }
            }

            if(p.getWorld().getName().equals(creative.getLobby().getName())){
                Block b = p.getWorld().getBlockAt(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() -2, p.getLocation().getZ()));

                if(b != null && b.getType() == Material.BEACON){
                    if(!omp.hasPlot()){
                        p.sendMessage(CreativeMessages.CMD_PLOT_PLOT_PREPARING.get(omp));
                        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 5, 1);
                        Plot.nextPlot(omp);
                    }

                    p.teleport(omp.getPlot().getHomeLocation());
                    p.playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 5, 1);
                    Title t = new Title("", CreativeMessages.TELEPORTED_TO_YOUR_PLOT.get(omp), 20, 40, 20);
                    t.send(p);
                }
            }
        }
    }
}
