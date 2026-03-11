package fadidev.orbitmines.prison.inventory;

import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.OMInventory;
import org.bukkit.Bukkit;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Created by Fadi on 9-9-2016.
 */
public class ChestShopViewerInv extends OMInventory {

    private Chest chest;

    public ChestShopViewerInv(Chest chest){
        this.chest = chest;

        setInventory(Bukkit.createInventory(null, chest.getInventory().getSize(), "§0§lChest Shop Viewer"));
    }

    @Override
    public void open(Player player) {
        player.openInventory(getInventory());
        getInventory().setContents(chest.getInventory().getContents());

        registerLast(player);
    }

    @Override
    public void onClick(OMPlayer omPlayer, InventoryClickEvent e) {
        e.setCancelled(true);
    }
}
