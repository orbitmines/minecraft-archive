package fadidev.orbitmines.survival.inventories;

import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.OMInventory;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.survival.OrbitMinesSurvival;
import fadidev.orbitmines.survival.handlers.SurvivalMessages;
import fadidev.orbitmines.survival.handlers.SurvivalPlayer;
import fadidev.orbitmines.survival.handlers.Warp;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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
public class WarpInv extends OMInventory {

    private OrbitMinesSurvival survival;
    private int page;
    private boolean favorite;

    public WarpInv(int page, boolean favorite){
        survival = OrbitMinesSurvival.getSurvival();
        this.page = page;
        this.favorite = favorite;

        setInventory(Bukkit.createInventory(null, 36, "§0§lWarps"));
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

        boolean isFavorite = e.getInventory().getItem(9).getItemMeta().getDisplayName().equals("§a§l" + SurvivalMessages.WORD_FAVORITE.get(omp) + " Warps");

        if(item.getType() == Material.COMPASS && item.getItemMeta().getDisplayName().equals("§7§l" + SurvivalMessages.WORD_SEARCH.get(omp) + " Warps")){
            new WarpInv(0, false).open(p);
        }
        else if(item.getType() == Material.DIAMOND && item.getItemMeta().getDisplayName().equals("§7§l" + SurvivalMessages.WORD_FAVORITE.get(omp) + " Warps")){
            new WarpInv(0, true).open(p);
        }
        else if(item.getType() == Material.ENDER_PEARL && item.getItemMeta().getDisplayName().equals("§3" + SurvivalMessages.WORD_SCROLL_UP.get(omp))){
            new WarpInv(e.getInventory().getItem(17).getAmount() -1, isFavorite).open(p);
        }
        else if(item.getType() == Material.ENDER_PEARL && item.getItemMeta().getDisplayName().equals("§3" + SurvivalMessages.WORD_SCROLL_DOWN.get(omp))){
            new WarpInv(e.getInventory().getItem(26).getAmount() -1, isFavorite).open(p);
        }
        else if(item.getItemMeta().getDisplayName().startsWith("§7§lWarp: ")){
            Warp warp = Warp.getWarp(item.getItemMeta().getDisplayName().substring(14));

            if(e.getAction() == InventoryAction.PICKUP_HALF){
                if(warp.isEnabled()){
                    p.closeInventory();
                    warp.teleport(omp);
                }
                else{
                    p.sendMessage(SurvivalMessages.INV_WARP_DISABLED.get(omp));
                }
            }
            else if(e.getAction() == InventoryAction.PICKUP_ALL){
                if(omp.getFavoriteWarps().contains(warp)){
                    omp.removeFavoriteWarp(warp);
                    new WarpInv(e.getInventory().getItem(26).getAmount() -2, isFavorite).open(p);
                }
                else{
                    omp.addFavoriteWarp(warp);
                    new WarpInv(e.getInventory().getItem(26).getAmount() -2, isFavorite).open(p);
                }
            }
        }
    }

    private ItemStack[] getContents(Player player){
        ItemStack[] contents = new ItemStack[getInventory().getSize()];
        SurvivalPlayer omp = SurvivalPlayer.getSurvivalPlayer(player);

        if(favorite){
            contents[0] = ItemUtils.itemstack(Material.COMPASS, 1, "§7§l" + SurvivalMessages.WORD_SEARCH.get(omp) + " Warps");
            contents[9] = survival.getApi().getNms().customItem().hideFlags(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.DIAMOND, 1, "§a§l" + SurvivalMessages.WORD_FAVORITE.get(omp) + " Warps"), Enchantment.DURABILITY, 1), 1);
        }
        else{
            contents[0] = survival.getApi().getNms().customItem().hideFlags(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.COMPASS, 1, "§a§l" + SurvivalMessages.WORD_SEARCH.get(omp) + " Warps"), Enchantment.DURABILITY, 1), 1);
            contents[9] = ItemUtils.itemstack(Material.DIAMOND, 1, "§7§l" + SurvivalMessages.WORD_FAVORITE.get(omp) + " Warps");
        }

        int index = 0;
        List<Warp> warps;
        if(favorite)
            warps = omp.getFavoriteWarps();
        else
            warps = survival.getWarps();

        for(int i = 2; i < 34; i++){
            if(i == 7)
                i = 11;
            else if(i == 16)
                i = 20;
            else if(i == 25)
                i = 29;

            contents[i] = getWarpItem(omp, warps, index + 5 * page);
            index++;
        }

        if(page == 0)
            contents[17] = ItemUtils.itemstack(Material.STAINED_GLASS_PANE, page, "§8" + SurvivalMessages.WORD_UNAVAILABLE.get(omp), 7);
        else
            contents[17] = ItemUtils.itemstack(Material.ENDER_PEARL, page, "§3" + SurvivalMessages.WORD_SCROLL_UP.get(omp));

        if(contents[33].getType() == Material.STAINED_GLASS_PANE)
            contents[26] = ItemUtils.itemstack(Material.STAINED_GLASS_PANE, page +2, "§8" + SurvivalMessages.WORD_UNAVAILABLE.get(omp), 7);
        else
            contents[26] = ItemUtils.itemstack(Material.ENDER_PEARL, page +2, "§3" + SurvivalMessages.WORD_SCROLL_DOWN.get(omp));

        return contents;
    }

    private ItemStack getWarpItem(SurvivalPlayer omp, List<Warp> warps, int index){
        if(warps.size() >= index +1 && warps.get(index) != null){
            Warp warp = warps.get(index);

            ItemStack item = new ItemStack(warp.getItemStack());
            ItemMeta meta = item.getItemMeta();
            List<String> lore = meta.getLore();
            lore.add("");
            lore.add(SurvivalMessages.INV_TELEPORT.get(omp));
            if(omp.getFavoriteWarps().contains(warp))
                lore.add(SurvivalMessages.INV_REMOVE_FAVORITE.get(omp));
            else
                lore.add(SurvivalMessages.INV_ADD_FAVORITE.get(omp));

            meta.setLore(lore);
            item.setItemMeta(meta);

            if(omp.getFavoriteWarps().contains(warp))
                return survival.getApi().getNms().customItem().hideFlags(ItemUtils.addEnchantment(item, Enchantment.DURABILITY, 1), 1);
            return item;
        }
        return ItemUtils.itemstack(Material.STAINED_GLASS_PANE, 1, "§8" + SurvivalMessages.WORD_EMPTY.get(omp) + " Warp Slot", 7);
    }
}
