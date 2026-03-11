package fadidev.orbitmines.hub.events;

import fadidev.orbitmines.hub.OrbitMinesHub;
import fadidev.orbitmines.hub.handlers.players.HubPlayer;
import fadidev.orbitmines.hub.managers.HubInteractManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Fadi on 10-9-2016.
 */
public class InteractEvent implements Listener {

    private OrbitMinesHub hub;

    public InteractEvent(){
        hub = OrbitMinesHub.getHub();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        HubPlayer omp = HubPlayer.getHubPlayer(p);
        ItemStack item = e.getItem();

        if(omp.isLoaded()){
            HubInteractManager manager = new HubInteractManager(e);

            if(!omp.inBuilderWorld()){
                if(p.getWorld().getName().equals(hub.getLobby().getName())){
                    manager.handleKickSign();
                    manager.handleMindCraft();
                    manager.handleSpawnInteract();
                    manager.handlePhysicalAction();
                    manager.handleMiniGameSigns();
                }

                if(item != null){
                    if(!omp.isOpMode() && manager.handleWrittenBook() && manager.handleGrapplingHook())
                        e.setCancelled(true);

                    if(item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null){
                        if(manager.handleCosmeticPerks()){}
                        else if(manager.handleSettings()){}
                        else if(manager.handleFly()){}
                        else if(manager.handleServerSelector()){}
                        else if(manager.handleAchievements()){}
                    }
                }
            }
            else{
                if(item != null && item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null)
                    manager.handleCageBuilder();
            }

            if(item != null && item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null){
                if(manager.handlePetAbilities()){}
                else if(manager.handleGadgets()){}
            }
        }
    }
}
