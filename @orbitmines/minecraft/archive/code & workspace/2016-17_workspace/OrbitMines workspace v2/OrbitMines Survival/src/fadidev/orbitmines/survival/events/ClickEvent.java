package fadidev.orbitmines.survival.events;

import fadidev.orbitmines.survival.OrbitMinesSurvival;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Created by Fadi on 10-9-2016.
 */
public class ClickEvent implements Listener {

    private OrbitMinesSurvival survival;

    public ClickEvent(){
        survival = OrbitMinesSurvival.getSurvival();
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getWhoClicked() instanceof Player)
            survival.getOmServer().getClickManager().handleOMInventories(e);
    }
}
