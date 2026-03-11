package fadidev.orbitmines.survival.inventories;

import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.OMInventory;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.survival.OrbitMinesSurvival;
import fadidev.orbitmines.survival.handlers.SurvivalPlayer;
import fadidev.orbitmines.survival.handlers.Warp;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Created by Fadi on 9-9-2016.
 */
public class ChestShopViewerInv extends OMInventory {

    private OrbitMinesSurvival survival;
    private Chest chest;

    public ChestShopViewerInv(Chest chest){
        survival = OrbitMinesSurvival.getSurvival();
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
