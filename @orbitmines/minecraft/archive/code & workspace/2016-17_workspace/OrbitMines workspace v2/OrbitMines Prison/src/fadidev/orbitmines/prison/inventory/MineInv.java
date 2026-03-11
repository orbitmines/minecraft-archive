package fadidev.orbitmines.prison.inventory;

import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.OMInventory;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.prison.OrbitMinesPrison;
import fadidev.orbitmines.prison.handlers.Mine;
import fadidev.orbitmines.prison.handlers.PrisonMessages;
import fadidev.orbitmines.prison.handlers.PrisonPlayer;
import fadidev.orbitmines.prison.utils.enums.MineType;
import fadidev.orbitmines.prison.utils.enums.Rank;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fadi on 9-9-2016.
 */
public class MineInv extends OMInventory {

    private OrbitMinesPrison prison;

    public MineInv(){
        prison = OrbitMinesPrison.getPrison();

        setInventory(Bukkit.createInventory(null, 45, "§0§lMines"));
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

        if(item.getType() == Material.BANNER && item.getItemMeta().getDisplayName().startsWith("§7§lMine §a§l")){
            Rank rank = Rank.valueOf(item.getItemMeta().getDisplayName().substring(13));
            Mine mine = Mine.getMine(rank, MineType.NORMAL);

            p.teleport(mine.getSpawn());
            p.sendMessage("§7" + PrisonMessages.WORD_TELEPORTED_TO.get(omp) + " Mine §a§l" + mine.getRank().toString() + "§7.");
            p.playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 5, 1);
        }
    }

    private ItemStack[] getContents(Player p){
        ItemStack[] contents = new ItemStack[getInventory().getSize()];
        PrisonPlayer omp = PrisonPlayer.getPrisonPlayer(p);

        contents[10] = getMineItem(omp, Rank.Z);
        contents[11] = getMineItem(omp, Rank.Y);
        contents[12] = getMineItem(omp, Rank.X);
        contents[13] = getMineItem(omp, Rank.W);
        contents[14] = getMineItem(omp, Rank.V);
        contents[15] = getMineItem(omp, Rank.U);
        contents[16] = getMineItem(omp, Rank.T);

        contents[19] = getMineItem(omp, Rank.S);
        contents[20] = getMineItem(omp, Rank.R);
        contents[21] = getMineItem(omp, Rank.Q);
        contents[22] = getMineItem(omp, Rank.P);
        contents[23] = getMineItem(omp, Rank.O);
        contents[24] = getMineItem(omp, Rank.N);

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

    private ItemStack getMineItem(PrisonPlayer omp, Rank rank){
        boolean hasPerms = omp.hasPerms(rank);
        ItemStack item = rank.getBanner(hasPerms);

        ItemMeta meta = item.getItemMeta();
        if(hasPerms){
            meta.setDisplayName("§7§lMine §a§l" + rank.toString());
        }
        else{
            meta.setDisplayName("§7§lMine §c§l" + rank.toString());
        }
        List<String> lore = new ArrayList<>();
        if(hasPerms){
            if(rank.getNextRank() != null){
                lore.add(" §7Rankup " + Messages.WORD_PRICE.get(omp) + ": §a" + rank.getRankupPrice() + " Gold ");
            }
            else{
                lore.add(" §7" + PrisonMessages.WORD_HIGHEST_RANK.get(omp));
            }
            lore.add("");
            lore.add(PrisonMessages.INV_CLICK_TO_TELEPORT.get(omp));
        }
        else{
            if(rank.getNextRank() != null){
                lore.add(" §7Rankup " + Messages.WORD_PRICE.get(omp) + ": §c" + rank.getRankupPrice() + " Gold ");
            }
            else{
                lore.add(" §7" + PrisonMessages.WORD_HIGHEST_RANK.get(omp));
            }
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        item.setDurability(Utils.statusDurability(hasPerms));

        return prison.getApi().getNms().customItem().hideFlags(item, 32, 2);
    }
}
