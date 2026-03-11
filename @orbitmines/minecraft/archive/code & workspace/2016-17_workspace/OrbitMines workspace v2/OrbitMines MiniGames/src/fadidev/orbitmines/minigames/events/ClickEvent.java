package fadidev.orbitmines.minigames.events;

import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.minigames.OrbitMinesMiniGames;
import fadidev.orbitmines.minigames.handlers.Arena;
import fadidev.orbitmines.minigames.handlers.players.MiniGamesPlayer;
import fadidev.orbitmines.minigames.utils.enums.MiniGameType;
import fadidev.orbitmines.minigames.utils.enums.State;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Fadi on 10-9-2016.
 */
public class ClickEvent implements Listener {

    private OrbitMinesMiniGames miniGames;

    public ClickEvent() {
        miniGames = OrbitMinesMiniGames.getMiniGames();
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getWhoClicked() instanceof Player){
            Player p = (Player) e.getWhoClicked();
            MiniGamesPlayer omp = MiniGamesPlayer.getMiniGamesPlayer(p);

            ItemStack item = e.getCurrentItem();
            Arena arena = omp.getArena();
            if(arena != null){
                if(arena.getState() == State.IN_GAME){
                    if((arena.getType() == MiniGameType.SURVIVAL_GAMES || arena.getType() == MiniGameType.SKYWARS) && e.getClickedInventory() instanceof EnchantingInventory){
                        if(item != null && item.getType() == Material.INK_SACK && item.getDurability() == 4){
                            e.setCancelled(true);
                            return;
                        }
                    }
                }
                else{
                    e.setCancelled(true);
                }
            }

            if(arena != null && arena.isSpectator(omp) && !ItemUtils.isNull(item)){
                if(item.getType() == Material.NAME_TAG && item.getItemMeta().getDisplayName().equals("§e§nTeleporter")){
                    e.setCancelled(true);
                    arena.getTeleporterInv().open(p);

                    return;
                }
                else if(item.getType() == Material.ENDER_PEARL && item.getItemMeta().getDisplayName().endsWith("Hub")){
                    e.setCancelled(true);

                    arena.leave(omp);

                    return;
                }
            }

            miniGames.getOmServer().getClickManager().handleOMInventories(e);
        }
    }
}
