package fadidev.orbitmines.api.events;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.Cooldowns;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.npc.Hologram;
import fadidev.orbitmines.api.handlers.npc.NPCArmorStand;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Fadi on 3-9-2016.
 */
public class InteractAtEntityEvent implements Listener {

    private OrbitMinesAPI api;

    public InteractAtEntityEvent(){
        this.api = OrbitMinesAPI.getApi();
    }

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent e){
        if(!(e.getRightClicked() instanceof ArmorStand))
            return;

        Player p = e.getPlayer();
        OMPlayer omp = OMPlayer.getOMPlayer(p);

        if(api.getServerPlugin().gadgetsEnabled()){
            ItemStack item = p.getItemInHand();

            if(item != null && (item.getType() == Material.MONSTER_EGG || item.getType() == Material.EGG)){
                e.setCancelled(true);
                omp.updateInventory();

                return;
            }
        }

        Hologram hologram = Hologram.getHologram((ArmorStand) e.getRightClicked());

        if(hologram != null){
            e.setCancelled(true);
            omp.updateInventory();
            return;
        }

        NPCArmorStand npc = NPCArmorStand.getNPCArmorStand((ArmorStand) e.getRightClicked());

        if(npc == null)
            return;

        e.setCancelled(true);
        omp.updateInventory();

        if(!omp.isLoaded())
            return;

        if(!omp.onCooldown(Cooldowns.NPC_INTERACT)){
            npc.click(p);

            omp.resetCooldown(Cooldowns.NPC_INTERACT);
        }
    }
}
