package fadidev.orbitmines.kitpvp.events;

import fadidev.orbitmines.kitpvp.managers.KitPvPInteractManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Fadi on 10-9-2016.
 */
public class InteractEvent implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        KitPvPInteractManager manager = new KitPvPInteractManager(e);

        /*if(e.getItem() != null && e.getItem().getItemMeta() != null && e.getItem().getItemMeta().getDisplayName() != null) {
            manager.handlePetAbilities();
            manager.handleGadgets();
        }*/
    }
}
