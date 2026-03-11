package fadidev.orbitmines.kitpvp.inventories;

import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.TeleporterInv;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.kitpvp.OrbitMinesKitPvP;
import fadidev.orbitmines.kitpvp.handlers.KitPvPMessages;
import fadidev.orbitmines.kitpvp.handlers.KitPvPPlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fadi on 13-9-2016.
 */
public class KitPvPTeleporterInv extends TeleporterInv {

    private OrbitMinesKitPvP kitPvP;

    public KitPvPTeleporterInv(){
        kitPvP = OrbitMinesKitPvP.getKitPvP();
    }

    @Override
    protected List<OMPlayer> getPlayers() {
        List<OMPlayer> players = new ArrayList<>();
        for(KitPvPPlayer omp : kitPvP.getKitPvPPlayers()){
            if(omp.isSpectator() || omp.getKitSelected() == null)
                continue;

            players.add(omp);
        }
        return players;
    }

    @Override
    protected ItemStack getItem(OMPlayer omPlayer) {
        KitPvPPlayer omp = (KitPvPPlayer) omPlayer;

        ItemStack item = ItemUtils.getSkull(omp.getPlayer().getName());
        ItemMeta itemmeta = item.getItemMeta();
        itemmeta.setDisplayName(omp.getName());
        List<String> itemLore = new ArrayList<>();
        itemLore.add("");
        itemLore.add("§7Kit: " + omp.getKitSelected().getSelectedKitName(omp.getKitLevelSelected()));
        itemLore.add("§cHealth: §f" + String.format("%.1f", omp.getPlayer().getHealth() / 2).replaceAll(",", ".") + "/10.0");
        itemLore.add("§6Food: §f" + String.format("%.1f", (double) omp.getPlayer().getFoodLevel() / 2).replaceAll(",", ".") + "/10.0");
        itemLore.add("§9" + KitPvPMessages.WORD_CURRENT.get(omp) + " Streak: §f" + omp.getCurrentStreak());
        itemLore.add("");
        itemLore.add("§c§lKitPvP Stats:");
        itemLore.add("§cKills: §f" + omp.getKills());
        itemLore.add("§4Deaths: §f" + omp.getDeaths());
        itemLore.add("§eLevel: §f" + omp.getLevels());
        itemLore.add("§b" + KitPvPMessages.WORD_BEST.get(omp) + " Streak: §f" + omp.getBestStreak());
        itemLore.add("");
        itemLore.add(KitPvPMessages.INV_CLICK_TO_TELEPORT.get(omp));
        itemLore.add("");
        itemmeta.setLore(itemLore);
        item.setItemMeta(itemmeta);

        return item;
    }
}
