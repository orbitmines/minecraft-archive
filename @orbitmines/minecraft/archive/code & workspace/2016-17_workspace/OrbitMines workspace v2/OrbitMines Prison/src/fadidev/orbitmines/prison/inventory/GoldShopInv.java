package fadidev.orbitmines.prison.inventory;

import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.OMInventory;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.prison.OrbitMinesPrison;
import fadidev.orbitmines.prison.handlers.Mine;
import fadidev.orbitmines.prison.handlers.PrisonMessages;
import fadidev.orbitmines.prison.handlers.PrisonPlayer;
import fadidev.orbitmines.prison.utils.PrisonUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fadi on 9-9-2016.
 */
public class GoldShopInv extends OMInventory {

    private OrbitMinesPrison prison;
    private Material material;
    private Mine mine;

    public GoldShopInv(Material material, Mine mine){
        prison = OrbitMinesPrison.getPrison();
        this.material = material;
        this.mine = mine;

        setInventory(Bukkit.createInventory(null, 45, "§0§lGold Shop"));
    }


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

        PrisonPlayer omp = (PrisonPlayer) omPlayer;
        Player p = omp.getPlayer();

        if((item.getType() == Material.LOG || item.getType() == Material.LOG_2) && item.getItemMeta().getDisplayName().equals("§e" + mine.getWoodType().getName() + " Wood") || item.getType() == mine.getShopMaterial() && item.getItemMeta().getDisplayName().equals(mine.getShopName()) || item.getType() == Material.COAL && item.getItemMeta().getDisplayName().equals("§8Coal") || item.getType() == Material.IRON_INGOT && item.getItemMeta().getDisplayName().equals("§7Iron") || item.getType() == Material.DIAMOND && item.getItemMeta().getDisplayName().equals("§bDiamond") || item.getType() == Material.EMERALD && item.getItemMeta().getDisplayName().equals("§aEmerald") || item.getType() == Material.REDSTONE && item.getItemMeta().getDisplayName().equals("§cRedstone") || item.getType() == Material.INK_SACK && item.getItemMeta().getDisplayName().equals("§1Lapis Lazuli") || item.getType() == Material.GOLD_INGOT && item.getItemMeta().getDisplayName().equals("§6Gold")){
            if(item.getEnchantments() == null || item.getEnchantments().size() == 0)
                new GoldShopInv(item.getType(), mine).open(omp.getPlayer());
        }
        else{
            if(item.getType() != Material.STAINED_GLASS_PANE && item.getItemMeta().getLore() != null && item.getItemMeta().getLore().get(0).startsWith(" §7" + Messages.WORD_REWARD.get(omp) + ":")){
                List<ItemStack> items = new ArrayList<>();
                for(ItemStack i : p.getInventory().getContents()){
                    if(i != null && i.getType() == item.getType() && i.getDurability() == item.getDurability()){
                        p.getInventory().remove(i);
                        items.add(i);
                    }
                }
                List<ItemStack> itemsToRemove = new ArrayList<>();
                List<ItemStack> itemsToAdd = new ArrayList<>();

                int amount = 0;
                for(ItemStack i : items){
                    if(amount != item.getAmount()){
                        if(amount + i.getAmount() <= item.getAmount()){
                            itemsToRemove.add(i);

                            amount += i.getAmount();
                        }
                        else{
                            itemsToRemove.add(i);

                            ItemStack item3 = new ItemStack(i);
                            item3.setAmount(i.getAmount() - (item.getAmount() - amount));
                            itemsToAdd.add(item3);

                            amount = item.getAmount();
                        }
                    }
                }

                for(ItemStack i : itemsToRemove){
                    items.remove(i);
                }
                for(ItemStack i : itemsToAdd){
                    items.add(i);
                }

                p.getInventory().addItem(items.toArray(new ItemStack[items.size()]));
                omp.updateInventory();

                Material m = item.getType();
                if(PrisonUtils.getSecondMaterial(item.getType()) == null)
                    m = PrisonUtils.getFirstMaterial(item.getType(), mine);

                double r = PrisonUtils.getReward(item.getType(), item.getDurability()) * item.getAmount();
                int reward = (int) (r * (omp.getRank().getMultiplier() + omp.getGoldMultiplier()));

                p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 5, 1);
                p.sendMessage(PrisonMessages.INV_SELL.get(omp, item.getItemMeta().getDisplayName(), reward + ""));
                new GoldShopInv(m, mine).open(omp.getPlayer());

                omp.addGold(reward);
            }
        }
    }

    private ItemStack[] getContents(Player p){
        ItemStack[] contents = new ItemStack[getInventory().getSize()];
        PrisonPlayer omp = PrisonPlayer.getPrisonPlayer(p);

        contents[0] = getCategoryItem(mine.getWoodType().getMaterial(), mine.getWoodType().getDurability(), "§e" + mine.getWoodType().getName() + " Wood", material);
        contents[1] = getCategoryItem(mine.getShopMaterial(), mine.getShopDurability(), mine.getShopName(), material);
        contents[2] = getCategoryItem(Material.COAL, 0, "§8Coal", material);
        contents[3] = getCategoryItem(Material.IRON_INGOT, 0, "§7Iron", material);
        contents[4] = getCategoryItem(Material.DIAMOND, 0, "§bDiamond", material);
        contents[5] = getCategoryItem(Material.EMERALD, 0, "§aEmerald", material);
        contents[6] = getCategoryItem(Material.REDSTONE, 0, "§cRedstone", material);
        contents[7] = getCategoryItem(Material.INK_SACK, 4, "§1Lapis Lazuli", material);
        contents[8] = getCategoryItem(Material.GOLD_INGOT, 0, "§6Gold", material);

        if(material != null){
            short durability1 = PrisonUtils.getDurability(material, mine);
            contents[19] = getSellItem(omp, material, durability1, PrisonUtils.getDisplayname(material, durability1, 1), 1, material);
            contents[20] = getSellItem(omp, material, durability1, PrisonUtils.getDisplayname(material, durability1, 4), 4, material);
            contents[21] = getSellItem(omp, material, durability1, PrisonUtils.getDisplayname(material, durability1, 8), 8, material);
            contents[22] = getSellItem(omp, material, durability1, PrisonUtils.getDisplayname(material, durability1, 16), 16, material);
            contents[23] = getSellItem(omp, material, durability1, PrisonUtils.getDisplayname(material, durability1, 32), 32, material);
            contents[24] = getSellItem(omp, material, durability1, PrisonUtils.getDisplayname(material, durability1, 48), 48, material);
            contents[25] = getSellItem(omp, material, durability1, PrisonUtils.getDisplayname(material, durability1, 64), 64, material);

            Material material2 = PrisonUtils.getSecondMaterial(material);
            short durability2 = PrisonUtils.getSecondDurability(material2, mine);
            contents[28] = getSellItem(omp, material2, durability2, PrisonUtils.getDisplayname(material2, durability2, 1), 1, material2);
            contents[29] = getSellItem(omp, material2, durability2, PrisonUtils.getDisplayname(material2, durability2, 4), 4, material2);
            contents[30] = getSellItem(omp, material2, durability2, PrisonUtils.getDisplayname(material2, durability2, 8), 8, material2);
            contents[31] = getSellItem(omp, material2, durability2, PrisonUtils.getDisplayname(material2, durability2, 16), 16, material2);
            contents[32] = getSellItem(omp, material2, durability2, PrisonUtils.getDisplayname(material2, durability2, 32), 32, material2);
            contents[33] = getSellItem(omp, material2, durability2, PrisonUtils.getDisplayname(material2, durability2, 48), 48, material2);
            contents[34] = getSellItem(omp, material2, durability2, PrisonUtils.getDisplayname(material2, durability2, 64), 64, material2);
        }
        else{
            for(int i = 19; i < 35; i++){
                if(i != 26 && i != 27){
                    contents[i] = getSellItem(omp, null, (short) 0, "", 0, null);
                }
            }
        }

        return contents;
    }

    private ItemStack getSellItem(PrisonPlayer omp, Material m, short durability, String displayName, int amount, Material material){
        if(material != null){
            int reward = (PrisonUtils.getReward(m, durability) * amount);
            double rankReward = reward * (omp.getRank().getMultiplier() - 1);
            double multiplier = omp.getGoldMultiplier();

            if(omp.getPlayer().getInventory().containsAtLeast(ItemUtils.itemstack(m, 1, durability), amount)){
                ItemStack item = new ItemStack(m, amount);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(displayName);
                List<String> lore = new ArrayList<>();
                lore.add(" §7" + Messages.WORD_REWARD.get(omp) + ": §6§l" + reward + " Gold ");
                lore.add(" §7Rank Bonus: §6§l" + (int) rankReward + " Gold ");
                if(multiplier != 1){
                    lore.add(" §7VIP Bonus: §6§l" + (int) ((reward * multiplier) - reward) + " Gold ");
                }
                meta.setLore(lore);
                item.setItemMeta(meta);
                item.setDurability(durability);

                return item;
            }
            else{
                ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, amount);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("§8" + displayName.substring(2));
                List<String> lore = new ArrayList<>();
                lore.add(" §7" + Messages.WORD_REWARD.get(omp) + ": §6§l" + reward + " Gold ");
                lore.add(" §7Rank Bonus: §6§l" + (int) rankReward + " Gold ");
                if(multiplier != 1){
                    lore.add(" §7VIP Bonus: §6§l" + (int) ((reward * multiplier) - reward) + " Gold ");
                }
                meta.setLore(lore);
                item.setItemMeta(meta);
                item.setDurability((short) 15);

                return item;
            }
        }
        else{
            ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§8§l???");
            item.setItemMeta(meta);
            item.setDurability((short) 15);

            return item;
        }
    }

    private ItemStack getCategoryItem(Material m, int durability, String displayName, Material material){
        ItemStack item = new ItemStack(m, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        item.setItemMeta(meta);
        item.setDurability((short) durability);

        if(material != null && m == material)
            item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);

        return prison.getApi().getNms().customItem().hideFlags(item, 1);
    }
}
