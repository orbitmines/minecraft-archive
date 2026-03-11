package fadidev.orbitmines.prison.inventory;

import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.OMInventory;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.prison.OrbitMinesPrison;
import fadidev.orbitmines.prison.handlers.PrisonCooldowns;
import fadidev.orbitmines.prison.handlers.PrisonMessages;
import fadidev.orbitmines.prison.handlers.PrisonPlayer;
import fadidev.orbitmines.prison.utils.enums.Rank;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fadi on 9-9-2016.
 */
public class KitInv extends OMInventory {

    private OrbitMinesPrison prison;

    public KitInv(){
        prison = OrbitMinesPrison.getPrison();

        setInventory(Bukkit.createInventory(null, 45, "§0§lKits"));
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

        if(item.getType() == Material.BANNER && item.getItemMeta().getDisplayName().startsWith("§7§lKit §a§l")){
            Rank rank = Rank.valueOf(item.getItemMeta().getDisplayName().substring(12));

            if(!omp.onCooldown(PrisonCooldowns.STARTER_KIT) || rank == Rank.Z){
                rank.getKit().addItems(p);
                p.sendMessage(PrisonMessages.SELECT_KIT.get(omp, rank.getName()));

                omp.resetCooldown(PrisonCooldowns.STARTER_KIT);
            }
            else{
                p.sendMessage(PrisonMessages.KIT_COOLDOWN.get(omp));
            }

            p.closeInventory();
        }
    }

    private ItemStack[] getContents(Player p){
        ItemStack[] contents = new ItemStack[getInventory().getSize()];
        PrisonPlayer omp = PrisonPlayer.getPrisonPlayer(p);

        contents[10] = getKitItem(omp, Rank.Z);
        contents[11] = getKitItem(omp, Rank.Y);
        contents[12] = getKitItem(omp, Rank.X);
        contents[13] = getKitItem(omp, Rank.W);
        contents[14] = getKitItem(omp, Rank.V);
        contents[15] = getKitItem(omp, Rank.U);
        contents[16] = getKitItem(omp, Rank.T);

        contents[19] = getKitItem(omp, Rank.S);
        contents[20] = getKitItem(omp, Rank.R);
        contents[21] = getKitItem(omp, Rank.Q);
        contents[22] = getKitItem(omp, Rank.P);
        contents[23] = getKitItem(omp, Rank.O);
        contents[24] = getKitItem(omp, Rank.N);

        {
            ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(PrisonMessages.WORD_COMING_SOON.get(omp) + "...");
            item.setItemMeta(meta);
            item.setDurability((short) 15);

            for(int i = 25; i < 35; i++){
                if(i != 26 && i != 27)
                    contents[i] = new ItemStack(item);
            }
        }

        return contents;
    }

    private ItemStack getKitItem(PrisonPlayer omp, Rank rank){
        boolean hasPerms = omp.hasPerms(rank);
        ItemStack item = rank.getBanner(hasPerms);

        ItemMeta meta = item.getItemMeta();
        if(hasPerms){
            meta.setDisplayName("§7§lKit §a§l" + rank.toString());
        }
        else{
            meta.setDisplayName("§7§lKit §c§l" + rank.toString());
        }
        List<String> lore = new ArrayList<>();
        lore.add("");
        if(hasPerms){
            lore.add("§a§l" + Messages.WORD_UNLOCKED.get(omp));
            lore.add("");
            lore.add(PrisonMessages.INV_CLICK_TO_RECEIVE.get(omp));
        }
        else{
            lore.add("§4§l" + Messages.WORD_LOCKED.get(omp));
            lore.add("");
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        item.setDurability(Utils.statusDurability(hasPerms));

        return prison.getApi().getNms().customItem().hideFlags(item, 32, 2);
    }
}
