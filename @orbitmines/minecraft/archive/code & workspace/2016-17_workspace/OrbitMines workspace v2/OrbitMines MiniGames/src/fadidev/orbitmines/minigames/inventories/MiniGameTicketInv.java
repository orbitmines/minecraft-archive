package fadidev.orbitmines.minigames.inventories;

import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.OMInventory;
import fadidev.orbitmines.api.nms.customitem.CustomItemNms;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.minigames.OrbitMinesMiniGames;
import fadidev.orbitmines.minigames.handlers.MiniGamesMessages;
import fadidev.orbitmines.minigames.handlers.Ticket;
import fadidev.orbitmines.minigames.handlers.players.MiniGamesPlayer;
import fadidev.orbitmines.minigames.utils.enums.InvType;
import fadidev.orbitmines.minigames.utils.enums.MiniGameType;
import fadidev.orbitmines.minigames.utils.enums.TicketType;
import fadidev.orbitmines.minigames.utils.enums.Uses;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static fadidev.orbitmines.minigames.utils.enums.MiniGameType.ULTRA_HARD_CORE;

/**
 * Created by Fadi on 9-9-2016.
 */
public class MiniGameTicketInv extends OMInventory {

    private CustomItemNms itemNms;
    private MiniGameType type;
    private InvType invType;

    public MiniGameTicketInv(MiniGameType type, InvType invType){
        setInventory(Bukkit.createInventory(null, invType == InvType.KITS || type == ULTRA_HARD_CORE || type == MiniGameType.CHICKEN_FIGHT ? 45 : 54, "§0§l" + type.getName()));

        this.type = type;
        this.invType = invType;
        this.itemNms = OrbitMinesMiniGames.getMiniGames().getApi().getNms().customItem();
    }

    @Override
    public void open(Player player) {
        getInventory().setContents(getContents(player));
        player.openInventory(getInventory());

        registerLast(player);
    }

    public MiniGameType getType() {
        return type;
    }

    public InvType getInvType() {
        return invType;
    }

    @Override
    public void onClick(OMPlayer omp, InventoryClickEvent e) {
        e.setCancelled(true);

        ItemStack item = e.getCurrentItem();

        if(ItemUtils.isNull(item))
            return;

        if(item.getType() == Material.WORKBENCH && item.getItemMeta().getDisplayName().equals("§7§lKits"))
            new MiniGameTicketInv(type, InvType.KITS).open(omp.getPlayer());
        else if(item.getType() == Material.SKULL_ITEM && item.getItemMeta().getDisplayName().equals("§2§lStats"))
            new StatsInv().open(omp.getPlayer());
        else if(item.getType() == Material.APPLE && item.getItemMeta().getDisplayName().equals("§7§lPerks"))
            new MiniGameTicketInv(type, InvType.PERKS).open(omp.getPlayer());
    }

    private ItemStack[] getContents(Player player){
        MiniGamesPlayer omp = MiniGamesPlayer.getMiniGamesPlayer(player);
        ItemStack[] contents = new ItemStack[getInventory().getSize()];

        switch(getType()){
            case CHICKEN_FIGHT:
                if(getInvType() == InvType.KITS){
                    for(TicketType type : Ticket.CF_KITS){
                        contents[type.getSlot()] = getItemStack(omp, type);
                    }
                }
                else{
                    for(TicketType type : Ticket.CF_PERKS){
                        contents[type.getSlot()] = getItemStack(omp, type);
                    }
                }
                break;
            case GHOST_ATTACK:
                break;
            case SKYWARS:
                if(getInvType() == InvType.KITS){
                    for(TicketType type : Ticket.SKYWARS_KITS){
                        contents[type.getSlot()] = getItemStack(omp, type);
                    }
                }
                else{
                    for(TicketType type : Ticket.SKYWARS_PERKS){;
                        contents[type.getSlot()] = getItemStack(omp, type);
                    }
                }
                break;
            case SPLATCRAFT:
                break;
            case SPLEEF:
                break;
            case SURVIVAL_GAMES:
                if(getInvType() == InvType.KITS){
                    for(TicketType type : Ticket.SG_KITS){
                        contents[type.getSlot()] = getItemStack(omp, type);
                    }
                }
                else{
                    for(TicketType type : Ticket.SG_PERKS){
                        contents[type.getSlot()] = getItemStack(omp, type);
                    }
                }
                break;
            case ULTRA_HARD_CORE:
                if(getInvType() == InvType.KITS){
                    for(TicketType type : Ticket.UHC_KITS){
                        contents[type.getSlot()] = getItemStack(omp, type);
                    }
                }
                else{
                    for(TicketType type : Ticket.UHC_PERKS){
                        contents[type.getSlot()] = getItemStack(omp, type);
                    }
                }
                break;
        }

        if(getInvType() == InvType.KITS){
            contents[39] = itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.WORKBENCH, 1, "§a§lKits"), Enchantment.DURABILITY, 1), 1);
            contents[40] = ItemUtils.getSkull(player.getName(), "§2§lStats");
            contents[41] = ItemUtils.itemstack(Material.APPLE, 1, "§7§lPerks");
        }
        else{
            if(getType() != ULTRA_HARD_CORE && getType() != MiniGameType.CHICKEN_FIGHT){
                contents[48] = ItemUtils.itemstack(Material.WORKBENCH, 1, "§7§lKits");
                contents[49] = ItemUtils.getSkull(player.getName(), "§2§lStats");
                contents[50] = itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.APPLE, 1, "§a§lPerks"), Enchantment.DURABILITY, 1), 1);
            }
            else{
                contents[39] = ItemUtils.itemstack(Material.WORKBENCH, 1, "§7§lKits");
                contents[40] = ItemUtils.getSkull(player.getName(), "§2§lStats");
                contents[41] = itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.APPLE, 1, "§a§lPerks"), Enchantment.DURABILITY, 1), 1);
            }
        }

        return contents;
    }

    private ItemStack getItemStack(MiniGamesPlayer omp, TicketType type){
        ItemStack item = type.getItemStack();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(type.getRarity().getColor() + type.getName());
        List<String> lore = new ArrayList<>();
        if(type.getUses() == Uses.ONE_TIME){
            lore.add(" §7" + Messages.WORD_AMOUNT.get(omp) + ": " + type.getRarity().getColor() + omp.getTicketAmount(type) + " ");
        }
        else{
            if(omp.getTicketAmount(type) != 0){
                lore.add(" §a§l" + Messages.WORD_UNLOCKED.get(omp));
            }
            else{
                lore.add(" §4§l" + Messages.WORD_LOCKED.get(omp));
            }
        }
        lore.add(" §7" + MiniGamesMessages.WORD_RARITY.get(omp) + ": " + type.getRarity().getName() + " ");
        lore.add("");
        for(String s : type.getDescription(omp)){
            lore.add(" " + type.getRarity().getColor() + "- " + s);
        }
        meta.setLore(lore);
        item.setItemMeta(meta);

        return itemNms.hideFlags(item, 34);
    }
}
