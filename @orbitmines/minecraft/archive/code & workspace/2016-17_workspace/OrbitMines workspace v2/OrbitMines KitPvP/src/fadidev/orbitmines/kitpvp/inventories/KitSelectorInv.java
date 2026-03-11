package fadidev.orbitmines.kitpvp.inventories;

import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.OMInventory;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.kitpvp.OrbitMinesKitPvP;
import fadidev.orbitmines.kitpvp.handlers.KitPvPMessages;
import fadidev.orbitmines.kitpvp.handlers.KitPvPPlayer;
import fadidev.orbitmines.kitpvp.utils.enums.KitPvPKit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by Fadi on 9-9-2016.
 */
public class KitSelectorInv extends OMInventory {

    private OrbitMinesKitPvP kitPvP;

    public KitSelectorInv(){
        kitPvP = OrbitMinesKitPvP.getKitPvP();

        setInventory(Bukkit.createInventory(null, 54, "§0§lKit Selector"));
    }

    @Override
    public void open(Player player) {
        KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer(player);
        player.sendMessage(KitPvPMessages.OPENING_KIT_SELECTOR.get(omp));

        getInventory().setContents(getContents(player));
        player.openInventory(getInventory());

        registerLast(player);
    }

    @Override
    public void onClick(OMPlayer omPlayer, InventoryClickEvent e) {
        e.setCancelled(true);

        ItemStack item = e.getCurrentItem();

        if(ItemUtils.isNull(item))
            return;

        KitPvPPlayer omp = (KitPvPPlayer) omPlayer;
        Player p = omp.getPlayer();
        InventoryAction a = e.getAction();

        for(KitPvPKit kitpvpkit : KitPvPKit.values()){
            if(item.getType() == kitpvpkit.getMaterial() && item.getItemMeta().getLore().contains("§4§l" + Messages.WORD_LOCKED.get(omp))){
                if(a == InventoryAction.PICKUP_HALF){
                    p.sendMessage(KitPvPMessages.INV_KIT_LOCKED.get(omp));
                }
                else if(a == InventoryAction.PICKUP_ALL){
                    new KitInv(kitpvpkit, 1).open(p);
                }
                break;
            }

            for(int level = 1; level <= kitpvpkit.getMaxLevel(); level++){
                if(item.getType() == kitpvpkit.getMaterial() && item.getItemMeta().getLore().contains("§a§l" + Messages.WORD_UNLOCKED.get(omp) + " §7§o(§a§oLvL " + level + "§7§o)") || level == 1 && item.getType() == kitpvpkit.getMaterial() && item.getItemMeta().getLore().contains("§d§lFREE Kit " + KitPvPMessages.WORD_SATURDAY.get(omp) + "!")){
                    if(a == InventoryAction.PICKUP_HALF){
                        omp.giveKit(kitpvpkit, level);
                        omp.teleportToMap();
                        p.closeInventory();
                    }
                    else if(a == InventoryAction.PICKUP_ALL){
                        if(level == kitpvpkit.getMaxLevel()){
                            new KitInv(kitpvpkit, level).open(p);
                        }
                        else{
                            new KitInv(kitpvpkit, level +1).open(p);
                        }
                    }
                    break;
                }
            }
        }
    }

    private ItemStack[] getContents(Player player){
        KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer(player);
        ItemStack[] contents = new ItemStack[getInventory().getSize()];

        contents[9] = getItem(omp, KitPvPKit.KNIGHT);
        contents[10] = getItem(omp, KitPvPKit.ARCHER);
        contents[11] = getItem(omp, KitPvPKit.SOLDIER);
        contents[12] = getItem(omp, KitPvPKit.WIZARD);
        contents[13] = getItem(omp, KitPvPKit.TANK);
        contents[14] = getItem(omp, KitPvPKit.DRUNK);
        contents[15] = getItem(omp, KitPvPKit.PYRO);
        contents[16] = getItem(omp, KitPvPKit.BUNNY);
        contents[17] = getItem(omp, KitPvPKit.NECROMANCER);
        contents[18] = getItem(omp, KitPvPKit.KING);
        contents[19] = getItem(omp, KitPvPKit.TREE);
        contents[20] = getItem(omp, KitPvPKit.BLAZE);
        contents[21] = getItem(omp, KitPvPKit.TNT);
        contents[22] = getItem(omp, KitPvPKit.FISHERMAN);
        contents[23] = getItem(omp, KitPvPKit.SNOWGOLEM);
        contents[24] = getItem(omp, KitPvPKit.LIBRARIAN);
        contents[25] = getItem(omp, KitPvPKit.SPIDER);
        contents[26] = getItem(omp, KitPvPKit.VILLAGER);
        contents[27] = getItem(omp, KitPvPKit.ASSASSIN);
        contents[28] = getItem(omp, KitPvPKit.LORD);
        contents[29] = getItem(omp, KitPvPKit.VAMPIRE);
        contents[30] = getItem(omp, KitPvPKit.DARKMAGE);
        contents[31] = getItem(omp, KitPvPKit.BEAST);
        contents[32] = getItem(omp, KitPvPKit.FISH);
        contents[33] = getItem(omp, KitPvPKit.HEAVY);
        contents[34] = getItem(omp, KitPvPKit.GRIMREAPER);
        contents[35] = getItem(omp, KitPvPKit.MINER);
        contents[36] = getItem(omp, KitPvPKit.FARMER);
        contents[37] = getItem(omp, KitPvPKit.UNDEATH_KING);
        contents[38] = getItem(omp, KitPvPKit.ENGINEER);

        {
            ItemStack item = ItemUtils.itemstack(Material.STAINED_GLASS_PANE, 1, "§o" + KitPvPMessages.WORD_COMING_SOON.get(omp) + "...");
            contents[39] = item;
            contents[40] = item;
            contents[41] = item;
            contents[42] = item;
            contents[43] = item;
            contents[44] = item;
        }

        return contents;
    }

    private ItemStack getItem(KitPvPPlayer omp, KitPvPKit kit){
        int kitLevel = omp.getUnlockedLevel(kit);
        if(kit.getVIPRank() != null && omp.hasPerms(kit.getVIPRank()))
            kitLevel = 1;

        ItemStack item = new ItemStack(kit.getMaterial(), kitLevel);
        ItemMeta itemmeta = item.getItemMeta();
        itemmeta.setDisplayName(kit.getKitName(kitLevel));
        itemmeta.setLore(kit.getKitLore(omp, kitLevel));
        item.setItemMeta(itemmeta);
        item.setDurability(kit.getDurability());
        if(kitPvP.isFreeKitEnabled() || kitLevel != 0)
            item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);

        return kitPvP.getApi().getNms().customItem().hideFlags(item, 1, 2, 32);
    }
}
