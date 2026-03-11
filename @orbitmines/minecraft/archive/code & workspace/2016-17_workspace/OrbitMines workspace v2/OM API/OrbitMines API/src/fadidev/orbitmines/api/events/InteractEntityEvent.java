package fadidev.orbitmines.api.events;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.Cooldowns;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.npc.NPC;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Fadi on 3-9-2016.
 */
public class InteractEntityEvent implements Listener {

    private OrbitMinesAPI api;

    public InteractEntityEvent(){
        this.api = OrbitMinesAPI.getApi();
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent e){
        Player p = e.getPlayer();
        OMPlayer omp = OMPlayer.getOMPlayer(p);

        if(api.getServerPlugin().gadgetsEnabled()){
            if(omp.getSoccerMagmaCube() == e.getRightClicked()){
                e.setCancelled(true);
                omp.disableSoccerMagmaCube();
                omp.updateInventory();

                return;
            }
            ItemStack item = p.getItemInHand();

            if(omp.getPet() == e.getRightClicked() && item != null && item.getType() == Material.SADDLE && item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().equals("§e§nPet Ride")){
                e.setCancelled(true);
                e.getRightClicked().setPassenger(p);
                omp.updateInventory();

                return;
            }

            if(item != null && (item.getType() == Material.MONSTER_EGG || item.getType() == Material.EGG)){
                e.setCancelled(true);
                omp.updateInventory();

                return;
            }
        }

        if(api.getPets().contains(e.getRightClicked())){
            e.setCancelled(true);
            return;
        }

        NPC npc = NPC.getNpc(e.getRightClicked());

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
