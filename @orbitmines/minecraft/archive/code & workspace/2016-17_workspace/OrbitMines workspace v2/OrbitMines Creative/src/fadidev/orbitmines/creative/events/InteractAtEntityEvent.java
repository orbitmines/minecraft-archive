package fadidev.orbitmines.creative.events;

import fadidev.orbitmines.api.handlers.npc.NPCArmorStand;
import fadidev.orbitmines.creative.handlers.CreativePlayer;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Fadi on 15-9-2016.
 */
public class InteractAtEntityEvent implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent e){
        if(e.getRightClicked() instanceof ArmorStand){
            Player p = e.getPlayer();
            CreativePlayer omp = CreativePlayer.getCreativePlayer(p);
            ItemStack item = p.getItemInHand();

            if(item != null && item.getType() == Material.MONSTER_EGG){
                e.setCancelled(true);
                omp.updateInventory();
            }

            if(omp.getPlot() == null || omp.isInPvPPlot() || !omp.isOnPlot(e.getRightClicked().getLocation()) || ((e.getRightClicked() instanceof ArmorStand) && NPCArmorStand.getNPCArmorStand((ArmorStand) e.getRightClicked()) != null))
                e.setCancelled(true);
        }
    }
}
