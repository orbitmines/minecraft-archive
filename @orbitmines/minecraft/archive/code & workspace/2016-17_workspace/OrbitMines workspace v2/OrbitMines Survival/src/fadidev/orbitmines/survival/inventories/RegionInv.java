package fadidev.orbitmines.survival.inventories;

import fadidev.orbitmines.api.handlers.Currency;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.ConfirmInv;
import fadidev.orbitmines.api.inventory.OMInventory;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.survival.OrbitMinesSurvival;
import fadidev.orbitmines.survival.handlers.Region;
import fadidev.orbitmines.survival.handlers.SurvivalMessages;
import fadidev.orbitmines.survival.handlers.SurvivalPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fadi on 9-9-2016.
 */
public class RegionInv extends OMInventory {

    private OrbitMinesSurvival survival;

    public RegionInv(){
        survival = OrbitMinesSurvival.getSurvival();

        setInventory(Bukkit.createInventory(null, 45, "§0§lRegion Teleporter"));
        getInventory().setContents(getContents());
    }

    @Override
    public void open(Player player) {
        player.openInventory(getInventory());

        registerLast(player);
    }

    @Override
    public void onClick(OMPlayer omPlayer, InventoryClickEvent e) {
        e.setCancelled(true);

        ItemStack item = e.getCurrentItem();

        if(ItemUtils.isNull(item))
            return;

        SurvivalPlayer omp = (SurvivalPlayer) omPlayer;

        if(item.getItemMeta().getDisplayName().startsWith("§7§lRegion")){
            Region r = Region.getRegion(Integer.parseInt(item.getItemMeta().getDisplayName().substring(15)));
            r.teleport(omp);
        }
    }

    private ItemStack[] getContents(){
        ItemStack[] contents = new ItemStack[getInventory().getSize()];

        for(Region r : survival.getRegions()){
            contents[r.getSlot()] = r.getItemStack();
        }

        return contents;
    }
}
