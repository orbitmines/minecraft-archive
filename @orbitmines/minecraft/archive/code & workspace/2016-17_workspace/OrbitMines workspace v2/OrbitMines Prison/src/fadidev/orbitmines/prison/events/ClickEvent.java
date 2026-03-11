package fadidev.orbitmines.prison.events;

import fadidev.orbitmines.prison.OrbitMinesPrison;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Created by Fadi on 10-9-2016.
 */
public class ClickEvent implements Listener {

    private OrbitMinesPrison prison;

    public ClickEvent(){
        prison = OrbitMinesPrison.getPrison();
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getWhoClicked() instanceof Player)
            prison.getOmServer().getClickManager().handleOMInventories(e);
    }
}
