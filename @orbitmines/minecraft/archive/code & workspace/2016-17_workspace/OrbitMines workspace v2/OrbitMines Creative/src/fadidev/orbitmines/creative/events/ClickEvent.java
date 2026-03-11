package fadidev.orbitmines.creative.events;

import fadidev.orbitmines.creative.OrbitMinesCreative;
import fadidev.orbitmines.creative.handlers.CreativePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

/**
 * Created by Fadi on 10-9-2016.
 */
public class ClickEvent implements Listener {

    private OrbitMinesCreative creative;

    public ClickEvent(){
        creative = OrbitMinesCreative.getCreative();
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getWhoClicked() instanceof Player){
            final Player p = (Player) e.getWhoClicked();
            CreativePlayer omp = CreativePlayer.getCreativePlayer(p);

            if(omp.isLoaded()){
                if(e.getSlotType() == InventoryType.SlotType.ARMOR && omp.getPvPPlot() == null){
                    e.setResult(Event.Result.DENY);
                    omp.updateInventory();
                }

                creative.getOmServer().getClickManager().handleOMInventories(e);
            }
        }
    }
}
