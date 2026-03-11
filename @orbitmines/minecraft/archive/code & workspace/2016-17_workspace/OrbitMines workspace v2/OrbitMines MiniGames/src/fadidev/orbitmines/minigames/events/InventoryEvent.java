package fadidev.orbitmines.minigames.events;

import fadidev.orbitmines.minigames.handlers.Arena;
import fadidev.orbitmines.minigames.handlers.players.MiniGamesPlayer;
import fadidev.orbitmines.minigames.utils.enums.MiniGameType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Fadi on 1-10-2016.
 */
public class InventoryEvent implements Listener {

    @EventHandler
    public void onOpen(InventoryOpenEvent e){
        Player p = (Player) e.getPlayer();
        MiniGamesPlayer omp = MiniGamesPlayer.getMiniGamesPlayer(p);
        Arena arena = omp.getArena();

        if(arena == null)
            return;

        if((arena.getType() == MiniGameType.SURVIVAL_GAMES || arena.getType() == MiniGameType.SKYWARS) && e.getInventory() instanceof EnchantingInventory){
            EnchantingInventory inv = (EnchantingInventory) e.getInventory();
            ItemStack item = new ItemStack(Material.INK_SACK, 3);
            item.setDurability((short) 4);
            inv.setItem(1, item);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e){
        Player p = (Player) e.getPlayer();
        MiniGamesPlayer omp = MiniGamesPlayer.getMiniGamesPlayer(p);
        Arena arena = omp.getArena();

        if(arena == null)
            return;

        if((arena.getType() == MiniGameType.SURVIVAL_GAMES || arena.getType() == MiniGameType.SKYWARS) && e.getInventory() instanceof EnchantingInventory){
            EnchantingInventory inv = (EnchantingInventory) e.getInventory();
            inv.setItem(1, null);
        }
    }
}
