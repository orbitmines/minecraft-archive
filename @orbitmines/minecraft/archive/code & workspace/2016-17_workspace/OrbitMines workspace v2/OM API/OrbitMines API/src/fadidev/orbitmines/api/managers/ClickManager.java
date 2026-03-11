package fadidev.orbitmines.api.managers;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.OMInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Created by Fadi on 6-9-2016.
 */
public class ClickManager {

    private OrbitMinesAPI api;

    public ClickManager(){
        api = OrbitMinesAPI.getApi();
    }

    /* Basically the 'onClick' method from OMInventory instances */
    public void handleOMInventories(InventoryClickEvent e){
        if(!(e.getWhoClicked() instanceof Player))
            return;

        Player p = (Player) e.getWhoClicked();
        OMPlayer omp = OMPlayer.getOMPlayer(p);
        OMInventory lastInventory = omp.getLastInventory();

        if(lastInventory == null)
            return;

        if(e.getInventory() == null || e.getInventory().getName() == null || !e.getInventory().getName().equals(lastInventory.getInventory().getTitle()))
            return;

        lastInventory.onClick(omp, e);
    }
}
