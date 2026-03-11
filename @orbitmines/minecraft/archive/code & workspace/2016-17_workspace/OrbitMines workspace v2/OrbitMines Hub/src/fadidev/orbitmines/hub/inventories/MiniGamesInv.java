package fadidev.orbitmines.hub.inventories;

import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.OMInventory;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.hub.handlers.players.HubPlayer;
import fadidev.orbitmines.hub.utils.enums.TicketType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * Created by Fadi on 9-9-2016.
 */
public class MiniGamesInv extends OMInventory {

    public MiniGamesInv(){
        setInventory(Bukkit.createInventory(null, 9, "§0§lMiniGames"));
    }

    @Override
    public void open(Player player) {
        getInventory().setContents(getContents(player));
        player.openInventory(getInventory());

        registerLast(player);
    }

    @Override
    public void onClick(OMPlayer omp, InventoryClickEvent e) {
        e.setCancelled(true);

        ItemStack item = e.getCurrentItem();

        if(ItemUtils.isNull(item))
            return;

        if(item.getType() == Material.SNOW_BALL && item.getItemMeta().getDisplayName().equals("§f§lTicket Gamble")){
            new TicketInv(false).open(omp.getPlayer());
        }
        else if(item.getType() == Material.SKULL_ITEM && item.getItemMeta().getDisplayName().equals("§2§lStats")){
            new StatsInv().open(omp.getPlayer());
        }
    }

    private ItemStack[] getContents(Player player){
        ItemStack[] contents = new ItemStack[getInventory().getSize()];

        {
            ItemStack item = new ItemStack(Material.SNOW_BALL, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§f§lTicket Gamble");
            item.setItemMeta(meta);
            contents[2] = item;
        }
        {
            ItemStack item = new ItemStack(Material.SKULL_ITEM, 1);
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            meta.setDisplayName("§2§lStats");
            meta.setOwner(player.getName());
            item.setItemMeta(meta);
            item.setDurability((short) 3);
            contents[6] = item;
        }

        return contents;
    }
}
