package fadidev.orbitmines.creative.events;

import fadidev.orbitmines.api.handlers.npc.NPCArmorStand;
import fadidev.orbitmines.creative.OrbitMinesCreative;
import fadidev.orbitmines.creative.handlers.CreativePlayer;
import org.bukkit.GameMode;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Created by Fadi on 15-9-2016.
 */
public class DamageByEntityEvent implements Listener {
    
    private OrbitMinesCreative creative;
    
    public DamageByEntityEvent(){
        creative = OrbitMinesCreative.getCreative();
    }
    
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Player){
            Player pD = (Player) e.getDamager();
            CreativePlayer ompD = CreativePlayer.getCreativePlayer(pD);

            if(pD.getWorld().getName().equals(creative.getPlotWorld().getName())){
                if(e.getEntity() instanceof Player){
                    Player pE = (Player) e.getEntity();
                    CreativePlayer ompE = CreativePlayer.getCreativePlayer(pE);

                    if(pD.getGameMode() != GameMode.SURVIVAL || pE.getGameMode() != GameMode.SURVIVAL || !ompD.isInPvPPlot() || !ompE.isInPvPPlot() || ompD.getPvPPlot().getPlotId() != ompE.getPvPPlot().getPlotId() || ompD.getKitSelected() == null || ompE.getKitSelected() == null)
                        e.setCancelled(true);
                }
                else{
                    if(!ompD.isOpMode() && !ompD.isOnPlot(e.getEntity().getLocation()) || ((e.getEntity() instanceof ArmorStand) && NPCArmorStand.getNPCArmorStand((ArmorStand) e.getEntity()) != null))
                        e.setCancelled(true);
                }
            }
            else if(pD.getWorld().getName().equals(creative.getLobby().getName())){
                if(!ompD.isOpMode())
                    e.setCancelled(true);
            }
        }

        if(e.getEntity() instanceof Player){
            Player pE = (Player) e.getEntity();
            CreativePlayer ompE = CreativePlayer.getCreativePlayer(pE);

            if(ompE.isInPvPPlot() && ompE.getKitSelected() == null)
                e.setCancelled(true);
        }
    }
}
