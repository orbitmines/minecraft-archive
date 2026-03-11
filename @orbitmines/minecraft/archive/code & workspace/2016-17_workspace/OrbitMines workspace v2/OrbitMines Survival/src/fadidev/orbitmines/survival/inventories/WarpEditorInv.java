package fadidev.orbitmines.survival.inventories;

import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.OMInventory;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.survival.OrbitMinesSurvival;
import fadidev.orbitmines.survival.handlers.SurvivalMessages;
import fadidev.orbitmines.survival.handlers.SurvivalPlayer;
import fadidev.orbitmines.survival.handlers.Warp;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Fadi on 9-9-2016.
 */
public class WarpEditorInv extends OMInventory {

    private OrbitMinesSurvival survival;
    private Warp warp;

    public WarpEditorInv(Warp warp){
        survival = OrbitMinesSurvival.getSurvival();
        this.warp = warp;

        setInventory(Bukkit.createInventory(null, 9, "§0§lEdit Warp: " + (warp == null ? "" : warp.getName())));
    }

    @Override
    public void open(Player player) {
        player.openInventory(getInventory());
        getInventory().setContents(getContents(player));

        registerLast(player);
    }

    @Override
    public void onClick(OMPlayer omPlayer, InventoryClickEvent e) {
        e.setCancelled(true);

        ItemStack item = e.getCurrentItem();

        if(ItemUtils.isNull(item))
            return;

        SurvivalPlayer omp = (SurvivalPlayer) omPlayer;
        Player p = omp.getPlayer();

        if(item.getType() == Material.NAME_TAG && item.getItemMeta().getDisplayName().equals(SurvivalMessages.INV_RENAME_WARP.get(omp))){
            new WarpRenameGUI(omp, warp).open();
        }
        else if(item.getItemMeta().getDisplayName().equals(SurvivalMessages.INV_CHANGE_ITEM.get(omp))){
            new WarpItemEditorInv(warp).open(p);
        }
        else if(item.getType() == Material.MAP && item.getItemMeta().getDisplayName().equals(SurvivalMessages.INV_SET_LOCATION.get(omp))){
            warp.setLocation(p.getLocation());
            Warp.saveToConfig();

            p.closeInventory();
            p.sendMessage(SurvivalMessages.INV_LOCATION_CHANGED.get(omp, warp.getName()));
        }
        else if((item.getType() == Material.ENDER_PEARL || item.getType() == Material.EYE_OF_ENDER) && item.getItemMeta().getDisplayName().startsWith("§7§lWarp ")){
            warp.setEnabled(!warp.isEnabled());
            Warp.saveToConfig();
            new WarpEditorInv(warp).open(p);
        }
    }

    private ItemStack[] getContents(Player player){
        ItemStack[] contents = new ItemStack[getInventory().getSize()];
        SurvivalPlayer omp = SurvivalPlayer.getSurvivalPlayer(player);

        contents[1] = ItemUtils.itemstack(Material.NAME_TAG, 1, SurvivalMessages.INV_RENAME_WARP.get(omp));
        contents[3] = ItemUtils.itemstack(warp.getItemStack().getType(), 1, SurvivalMessages.INV_CHANGE_ITEM.get(omp), warp.getItemStack().getDurability());
        contents[5] = ItemUtils.itemstack(Material.MAP, 1, SurvivalMessages.INV_SET_LOCATION.get(omp));

        if(warp.isEnabled())
            contents[7] = ItemUtils.itemstack(Material.EYE_OF_ENDER, 1, "§7§lWarp " + Utils.statusString(omp.getLanguage(), true));
        else
            contents[7] = ItemUtils.itemstack(Material.ENDER_PEARL, 1, "§7§lWarp " + Utils.statusString(omp.getLanguage(), false));

        return contents;
    }
}
