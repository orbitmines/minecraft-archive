package fadidev.orbitmines.minigames.events;

import fadidev.orbitmines.api.handlers.Kit;
import fadidev.orbitmines.minigames.OrbitMinesMiniGames;
import fadidev.orbitmines.minigames.handlers.Arena;
import fadidev.orbitmines.minigames.handlers.players.MiniGamesPlayer;
import fadidev.orbitmines.minigames.managers.MiniGamesInteractManager;
import fadidev.orbitmines.minigames.utils.enums.MiniGameType;
import fadidev.orbitmines.minigames.utils.enums.State;
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

    private OrbitMinesMiniGames miniGames;

    public InteractEvent(){
        miniGames = OrbitMinesMiniGames.getMiniGames();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        MiniGamesPlayer omp = MiniGamesPlayer.getMiniGamesPlayer(p);
        ItemStack item = e.getItem();

        if(omp.isLoaded()){
            MiniGamesInteractManager manager = new MiniGamesInteractManager(e);

            if(p.getWorld().getName().equals(miniGames.getLobby().getName())){

                if(item != null && item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null){
                    if(manager.handlePetAbilities()){}
                    else if(manager.handleGadgets()){}
                }
            }
            else{
                manager.handleChests();
                manager.handleSpectator();
            }

            if(item != null && item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null){

                if(manager.handleStats()){}
                else if(manager.handleGameEffects()){}
                else if(manager.handleBackToHub()){}
                else if(manager.handleCosmeticPerks()){}
                else if(manager.handleTeleporter()){}
                else{
                    Arena arena = omp.getArena();
                    if(arena != null && arena.getType() == MiniGameType.CHICKEN_FIGHT && arena.getState() == State.IN_GAME && !arena.isSpectator(omp)){
                        Kit kit = omp.getChickenFightPlayer().getKitSelected();

                        if(kit != null){
                            if(manager.handleFeatherAttack()){}
                            else if(manager.handleEggBomb()){}
                            else if(manager.handleFireShield()){}
                            else if(manager.handleIronFist()){}
                        }
                    }
                }
            }
        }
    }
}
