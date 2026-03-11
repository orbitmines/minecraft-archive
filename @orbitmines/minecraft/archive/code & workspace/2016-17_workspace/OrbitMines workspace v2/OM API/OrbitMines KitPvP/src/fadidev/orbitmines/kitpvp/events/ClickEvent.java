package fadidev.orbitmines.kitpvp.events;

import fadidev.orbitmines.kitpvp.OrbitMinesKitPvP;
import fadidev.orbitmines.kitpvp.handlers.KitPvPPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Created by Fadi on 10-9-2016.
 */
public class ClickEvent implements Listener {

    private OrbitMinesKitPvP kitPvP;

    public ClickEvent(){
        kitPvP = OrbitMinesKitPvP.getKitPvP();
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getWhoClicked() instanceof Player){
            final Player p = (Player) e.getWhoClicked();
            KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer(p);

            kitPvP.getOmServer().getClickManager().handleOMInventories(e);
        }
    }
}
