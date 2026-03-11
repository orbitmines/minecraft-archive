package fadidev.orbitmines.survival.inventories;

import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.OMInventory;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.survival.OrbitMinesSurvival;
import fadidev.orbitmines.survival.handlers.SurvivalMessages;
import fadidev.orbitmines.survival.handlers.SurvivalPlayer;
import fadidev.orbitmines.survival.handlers.Warp;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by Fadi on 9-9-2016.
 */
public class WarpItemEditorInv extends OMInventory {

    private OrbitMinesSurvival survival;
    private Warp warp;

    public WarpItemEditorInv(Warp warp){
        survival = OrbitMinesSurvival.getSurvival();
        this.warp = warp;

        setInventory(Bukkit.createInventory(null, 27, "§0§lWarp Item: " + (warp == null ? "" : warp.getName())));
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

        if(item.getItemMeta().getDisplayName().startsWith("§7" + SurvivalMessages.WORD_CHANGE_TO.get(omp) + " ")){
            warp.updateItemStack(item.getType(), item.getDurability());
            Warp.saveToConfig();

            p.closeInventory();
            p.sendMessage(SurvivalMessages.INV_CHANGE_WARP_ITEM.get(omp, item.getItemMeta().getDisplayName().substring(12)));
        }
        else if(item.getType() == Material.BARRIER && item.getItemMeta().getDisplayName().equals("§c§l" + Messages.WORD_CANCEL.get(omp))){
            new WarpEditorInv(warp).open(p);
        }
    }

    private ItemStack[] getContents(Player player){
        ItemStack[] contents = new ItemStack[getInventory().getSize()];
        SurvivalPlayer omp = SurvivalPlayer.getSurvivalPlayer(player);

        contents[0] = getItemStack(omp, "§fBirch Sapling", Material.SAPLING, 2);
        contents[1] = getItemStack(omp, "§eSand", Material.SAND, 0);
        contents[2] = getItemStack(omp, "§cRed Rose", Material.RED_ROSE, 0);
        contents[3] = getItemStack(omp, "§aOak Sapling", Material.SAPLING, 0);
        contents[4] = getItemStack(omp, "§bPacked Ice", Material.ICE, 1);
        contents[5] = getItemStack(omp, "§bIce", Material.ICE, 0);
        contents[6] = getItemStack(omp, "§2Jungle Sapling", Material.SAPLING, 3);
        contents[7] = getItemStack(omp, "§2Spruce Sapling", Material.SAPLING, 1);
        contents[8] = getItemStack(omp, "§6Yellow Stained Clay", Material.STAINED_CLAY, 4);
        contents[9] = getItemStack(omp, "§cMushroom Block", Material.HUGE_MUSHROOM_2, 2);
        contents[10] = getItemStack(omp, "§9Water Bucket", Material.WATER_BUCKET, 0);
        contents[11] = getItemStack(omp, "§aGrass Block", Material.GRASS, 0);
        contents[12] = getItemStack(omp, "§2Dark Oak Sapling", Material.SAPLING, 5);
        contents[13] = getItemStack(omp, "§eAcacia Sapling", Material.SAPLING, 4);
        contents[14] = getItemStack(omp, "§fSnow Block", Material.SNOW_BLOCK, 0);
        contents[15] = getItemStack(omp, "§7Stone", Material.STONE, 0);
        contents[16] = getItemStack(omp, "§eSun Flower", Material.DOUBLE_PLANT, 0);
        contents[17] = getItemStack(omp, "§2Vine", Material.VINE, 0);

        contents[22] = ItemUtils.itemstack(Material.BARRIER, 1, "§c§l" + Messages.WORD_CANCEL.get(omp));

        return contents;
    }

    private ItemStack getItemStack(SurvivalPlayer omp, String name, Material material, int durability){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§7" + SurvivalMessages.WORD_CHANGE_TO.get(omp) + " " + name);
        item.setItemMeta(meta);
        item.setDurability((short) durability);

        return item;
    }
}
