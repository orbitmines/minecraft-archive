package fadidev.orbitmines.kitpvp.inventories;

import fadidev.orbitmines.api.handlers.Currency;
import fadidev.orbitmines.api.handlers.Kit;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.OMInventory;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.kitpvp.OrbitMinesKitPvP;
import fadidev.orbitmines.kitpvp.handlers.KitPvPMessages;
import fadidev.orbitmines.kitpvp.handlers.KitPvPPlayer;
import fadidev.orbitmines.kitpvp.utils.enums.KitPvPKit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fadi on 9-9-2016.
 */
public class KitInv extends OMInventory {

    private OrbitMinesKitPvP kitPvP;
    private KitPvPKit kitPvPKit;
    private int level;

    public KitInv(KitPvPKit kit, int level){
        kitPvP = OrbitMinesKitPvP.getKitPvP();
        this.kitPvPKit = kit;
        this.level = level;

        setInventory(Bukkit.createInventory(null, 54, "§0§l" + kit.getName() + " (Level " + level + ")"));
    }

    @Override
    public void open(Player player) {
        getInventory().setContents(getContents(player));
        player.openInventory(getInventory());

        registerLast(player);
    }

    @Override
    public void onClick(final OMPlayer omPlayer, InventoryClickEvent e) {
        e.setCancelled(true);

        ItemStack item = e.getCurrentItem();

        if(ItemUtils.isNull(item))
            return;

        KitPvPPlayer omp = (KitPvPPlayer) omPlayer;
        Player p = omp.getPlayer();

        if(item.getType() == Material.REDSTONE_BLOCK && item.getItemMeta().getDisplayName().equals("§4§l§o<< " + KitPvPMessages.WORD_BACK.get(omp))){
            new KitSelectorInv().open(p);
        }
        else if(item.getType() == Material.NETHER_STAR && item.getItemMeta().getDisplayName().equals("§b§l" + kitPvPKit.getName() + " §7§o(§a§oLvL 1§7§o)")){
            new KitInv(kitPvPKit, 1).open(p);
        }
        else if(item.getType() == Material.NETHER_STAR && item.getItemMeta().getDisplayName().equals("§b§l" + kitPvPKit.getName() + " §7§o(§a§oLvL 2§7§o)")){
            new KitInv(kitPvPKit, 2).open(p);
        }
        else if(item.getType() == Material.NETHER_STAR && item.getItemMeta().getDisplayName().equals("§b§l" + kitPvPKit.getName() + " §7§o(§a§oLvL 3§7§o)")){
            new KitInv(kitPvPKit, 3).open(p);
        }
        else if(item.getType() == Material.EMERALD_BLOCK && item.getItemMeta().getLore() != null && item.getItemMeta().getLore().contains("§7" + Messages.WORD_PRICE.get(omp) + ": §6" + kitPvPKit.getPrice(level) + " Coins")){
            if(omp.hasMoney(kitPvPKit.getPrice(level))){
                omp.removeMoney(kitPvPKit.getPrice(level));
                omp.setUnlockedKitLevel(kitPvPKit, level);

                p.sendMessage(KitPvPMessages.INV_BOUGHT_KIT.get(omp, kitPvPKit.getName(), level + ""));
                new KitInv(kitPvPKit, level).open(p);
            }
            else{
                omp.requiredMoney(kitPvPKit.getPrice(level));
            }
        }
        else if(item.getType() == Material.EMERALD_BLOCK && item.getItemMeta().getLore() != null && item.getItemMeta().getLore().contains("§7" + Messages.WORD_PRICE.get(omp) + ": §e" + kitPvPKit.getPrice(level) + " OMT")){
            if(omp.hasOrbitMinesTokens(kitPvPKit.getPrice(level))){
                omp.removeOrbitMinesTokens(kitPvPKit.getPrice(level));
                omp.setUnlockedKitLevel(kitPvPKit, level);

                p.sendMessage(KitPvPMessages.INV_BOUGHT_KIT.get(omp, kitPvPKit.getName(), level + ""));
                new KitInv(kitPvPKit, level).open(p);
            }
            else{
                omp.requiredOrbitMinesTokens(kitPvPKit.getPrice(level));
            }
        }
    }

    private ItemStack[] getContents(Player player){
        KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer(player);
        ItemStack[] contents = new ItemStack[getInventory().getSize()];

        Kit kit = kitPvPKit.getKit(this.level);
        int items = kit.contentItems();

        contents[45] = ItemUtils.itemstack(Material.REDSTONE_BLOCK, 1, "§4§l§o<< " + KitPvPMessages.WORD_BACK.get(omp));
        contents[48] = getItem(omp, 1);
        contents[49] = getItem(omp, 2);
        contents[50] = getItem(omp, 3);

        contents[4] = kit.getHelmet();
        contents[13] = kit.getChestplate();
        contents[22] = kit.getLeggings();
        contents[31] = kit.getBoots();

        if(level == omp.getUnlockedLevel(kitPvPKit) +1){
            ItemStack item = new ItemStack(Material.EMERALD_BLOCK, 1);
            ItemMeta itemmeta = item.getItemMeta();
            itemmeta.setDisplayName("§2§l§o" + KitPvPMessages.WORD_BUY.get(omp) + " " + kitPvPKit.getName() + " §7§o(§a§oLvL " + level + "§7§o)");
            List<String> itemLore = new ArrayList<>();
            if(kitPvPKit.getVIPRank() == null){
                itemLore.add("");
                if(kitPvPKit.getCurrency() == Currency.getCurrency(Material.GOLD_NUGGET)){
                    itemLore.add("§7" + Messages.WORD_PRICE.get(omp) + ": §e" + kitPvPKit.getPrice(level) + " OMT");
                }
                else{
                    itemLore.add("§7" + Messages.WORD_PRICE.get(omp) + ": §6" + kitPvPKit.getPrice(level) + " Coins");
                }
                itemLore.add("");
            }
            else{
                if(!omp.hasPerms(kitPvPKit.getVIPRank())){
                    itemmeta.setDisplayName("§7" + Messages.WORD_REQUIRED.get(omp) + ": " + kitPvPKit.getVIPRank().getRankString() + " VIP");
                }
                else{
                    item = null;
                }
            }

            if(item != null){
                itemmeta.setLore(itemLore);
                item.setItemMeta(itemmeta);
                contents[53] = item;
            }
        }

        if(items == 7 || items == 8){
            contents[2] = getItem(omp, kit.getItem(0));
            contents[6] = getItem(omp, kit.getItem(1));
            contents[11] = getItem(omp, kit.getItem(2));
            contents[15] = getItem(omp, kit.getItem(3));
            contents[20] = getItem(omp, kit.getItem(4));
            contents[24] = getItem(omp, kit.getItem(5));
            contents[29] = getItem(omp, kit.getItem(6));

            if(items == 8)
                contents[33] = getItem(omp, kit.getItem(7));
        }
        else{
            if(items >= 1)
                contents[11] = getItem(omp, kit.getItem(0));
            if(items >= 2)
                contents[15] = getItem(omp, kit.getItem(1));
            if(items >= 3)
                contents[20] = getItem(omp, kit.getItem(2));
            if(items >= 4)
                contents[24] = getItem(omp, kit.getItem(3));
            if(items >= 5)
                contents[29] = getItem(omp, kit.getItem(4));
            if(items == 6)
                contents[33] = getItem(omp, kit.getItem(5));
        }

        PotionEffect effect = kit.getPotionEffect();
        if(effect != null){
            ItemStack item = getItem(omp, effect.getType(), effect.getAmplifier() +1);
            contents[18] = item;
            contents[26] = item;
        }

        return contents;
    }

    private ItemStack getItem(OMPlayer omp, PotionEffectType type, int level){
        String levelName = "";
        for(int i = 0; i < level; i++){
            levelName += "I";
        }
        levelName = levelName.replace("IIIIIII", "VII");
        levelName = levelName.replace("IIIIII", "VI");
        levelName = levelName.replace("IIIII", "V");
        levelName = levelName.replace("IIII", "IV");

        ItemStack item = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        meta.setDisplayName("§b§l§o" + Utils.getName(type) + " " + levelName);
        meta.addCustomEffect(new PotionEffect(type, 1, level -1), true);
        item.setItemMeta(meta);

        return kitPvP.getApi().getNms().customItem().hideFlags(item, 2, 32);
    }

    private ItemStack getItem(OMPlayer omp, ItemStack item){
        if(item != null && item.getType() == Material.ARROW){
            ItemMeta meta = item.getItemMeta();
            List<String> itemLore = new ArrayList<>();
            itemLore.add(KitPvPMessages.INV_ARROW_REGEN.get(omp, kitPvPKit.getNextArrow() + ""));
            itemLore.add(" §cMaximum: §6" + item.getAmount() + " Arrows");
            meta.setLore(itemLore);
            item.setItemMeta(meta);
        }

        return item;
    }

    private ItemStack getItem(OMPlayer omp, int level){
        if(kitPvPKit.getMaxLevel() >= level){
            return ItemUtils.itemstack(Material.NETHER_STAR, level, "§b§l" + kitPvPKit.getName() + " §7§o(§a§oLvL " + level + "§7§o)");
        }
        return ItemUtils.itemstack(Material.INK_SACK, level, "§4§l§o" + KitPvPMessages.WORD_UNAVAILABLE.get(omp), 1);
    }
}
