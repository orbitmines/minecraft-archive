package fadidev.orbitmines.hub.inventories;

import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.OMInventory;
import fadidev.orbitmines.api.nms.customitem.CustomItemNms;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.api.utils.enums.Language;
import fadidev.orbitmines.hub.OrbitMinesHub;
import fadidev.orbitmines.hub.handlers.HubMessages;
import fadidev.orbitmines.hub.handlers.players.HubPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Fadi on 9-9-2016.
 */
public class SettingsInv extends OMInventory {

    public SettingsInv(Player player){
        setInventory(Bukkit.createInventory(null, 27, "§0§lSettings (" + player.getName() + ")"));
    }

    @Override
    public void open(Player player) {
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

        HubPlayer omp = (HubPlayer) omPlayer;
        Player p = omp.getPlayer();

        if(item.getType() == Material.EYE_OF_ENDER && item.getItemMeta().getDisplayName().startsWith("§3§lPlayers")){
            p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);

            if(omp.hasPlayersEnabled()){
                omp.hidePlayers();
                omp.setPlayersEnabled(false);
                p.sendMessage("§3§lPlayers " + HubMessages.WORD_STAAN_NU.get(omp, "staan") + Utils.statusString(omp.getLanguage(), false) + "§7.");
            }
            else{
                omp.showPlayers();
                omp.setPlayersEnabled(true);
                p.sendMessage("§3§lPlayers " + HubMessages.WORD_STAAN_NU.get(omp, "staan") + Utils.statusString(omp.getLanguage(), true) + "§7.");
            }

            p.closeInventory();
        }
        else if(item.getType() == Material.LEASH && item.getItemMeta().getDisplayName().startsWith("§6§lStacker")){
            p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);

            if(omp.hasStackerEnabled()){
                omp.setStackerEnabled(false);
                p.sendMessage("§6§lStacker " + HubMessages.WORD_STAAN_NU.get(omp, "staat") + Utils.statusString(omp.getLanguage(), false) + "§7.");
            }
            else{
                omp.setStackerEnabled(true);
                p.sendMessage("§6§lStacker " + HubMessages.WORD_STAAN_NU.get(omp, "staat") + Utils.statusString(omp.getLanguage(), true) + "§7.");
            }

            p.closeInventory();
        }
        else if(item.getType() == Material.PAPER && item.getItemMeta().getDisplayName().startsWith("§f§lScoreboard")){
            p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);

            if(omp.hasScoreboardEnabled()){
                omp.setScoreboardEnabled(false);
                p.sendMessage("§f§lScoreboard " + HubMessages.WORD_STAAN_NU.get(omp, "staat") + Utils.statusString(omp.getLanguage(), false) + "§7.");
            }
            else{
                omp.setScoreboardEnabled(true);
                p.sendMessage("§f§lScoreboard " + HubMessages.WORD_STAAN_NU.get(omp, "staat") + Utils.statusString(omp.getLanguage(), true) + "§7.");
            }

            p.closeInventory();
        }
        else if(item.getType() == Material.BANNER && item.getItemMeta().getDisplayName().startsWith("§7§l")){
            p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 5, 1);

            switch(omp.getLanguage()){
                case DUTCH:
                    omp.setLanguage(Language.ENGLISH);
                    p.sendMessage("§7Changed §c§lLanguage§7 to §c§lEnglish§7.");
                    break;
                case ENGLISH:
                    omp.setLanguage(Language.DUTCH);
                    p.sendMessage("§c§lTaal§7 veranderd in §c§lNederlands§7.");
                    break;
            }

            p.closeInventory();
        }
    }

    private ItemStack[] getContents(Player player){
        HubPlayer omp = HubPlayer.getHubPlayer(player);
        ItemStack[] contents = new ItemStack[getInventory().getSize()];
        CustomItemNms itemNms = OrbitMinesHub.getHub().getApi().getNms().customItem();

        if(omp.hasPlayersEnabled())
            contents[10] = itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.EYE_OF_ENDER, 1, "§3§lPlayers " + Utils.statusString(omp.getLanguage(), true)), Enchantment.DURABILITY, 1), 1);
        else
            contents[10] = ItemUtils.itemstack(Material.EYE_OF_ENDER, 1, "§3§lPlayers " + Utils.statusString(omp.getLanguage(), false));

        if(omp.hasScoreboardEnabled())
            contents[12] = itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.PAPER, 1, "§f§lScoreboard " + Utils.statusString(omp.getLanguage(), true)), Enchantment.DURABILITY, 1), 1);
        else
            contents[12] = ItemUtils.itemstack(Material.PAPER, 1, "§f§lScoreboard " + Utils.statusString(omp.getLanguage(), false));

        if(omp.hasStackerEnabled())
            contents[14] = itemNms.hideFlags(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.LEASH, 1, "§6§lStacker " + Utils.statusString(omp.getLanguage(), true)), Enchantment.DURABILITY, 1), 1);
        else
            contents[14] = ItemUtils.itemstack(Material.LEASH, 1, "§6§lStacker " + Utils.statusString(omp.getLanguage(), false));

        switch(omp.getLanguage()){
            case DUTCH:
                contents[16] = itemNms.hideFlags(Language.DUTCH.getFlag("§7§lTaal: §c§lNederlands", "§7Verander taal naar §c§lEngels§7.", "", "§7§lLanguage: §c§lDutch", "§7Change language to §c§lEnglish§7."), 32);
                break;
            case ENGLISH:
                contents[16] = itemNms.hideFlags(Language.ENGLISH.getFlag("§7§lLanguage: §c§lEnglish", "§7Change language to §c§lDutch§7.", "", "§7§lTaal: §c§lEngels", "§7Verander taal naar §c§lNederlands§7."), 32);
                break;
        }

        return contents;
    }
}
