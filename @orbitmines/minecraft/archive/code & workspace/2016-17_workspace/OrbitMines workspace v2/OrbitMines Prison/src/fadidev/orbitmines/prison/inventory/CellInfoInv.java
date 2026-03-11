package fadidev.orbitmines.prison.inventory;

import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.OMInventory;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.prison.OrbitMinesPrison;
import fadidev.orbitmines.prison.handlers.Cell;
import fadidev.orbitmines.prison.handlers.PrisonMessages;
import fadidev.orbitmines.prison.handlers.PrisonPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fadi on 9-9-2016.
 */
public class CellInfoInv extends OMInventory {

    private OrbitMinesPrison prison;

    public CellInfoInv(){
        prison = OrbitMinesPrison.getPrison();

        setInventory(Bukkit.createInventory(null, 9, "§0§lCell Info"));
    }

    @Override
    public void open(Player player) {
        PrisonPlayer omp = PrisonPlayer.getPrisonPlayer(player);

        if(omp.hasCell()) {
            getInventory().setContents(getContents(player));
            player.openInventory(getInventory());

            registerLast(player);
        }
        else{
            player.sendMessage(PrisonMessages.CMD_PLOT_NO_PLOT.get(omp));
        }
    }

    @Override
    public void onClick(OMPlayer omPlayer, InventoryClickEvent e) {
        e.setCancelled(true);

        ItemStack item = e.getCurrentItem();

        if(ItemUtils.isNull(item))
            return;

        PrisonPlayer omp = (PrisonPlayer) omPlayer;
        Player p = omp.getPlayer();

        if(item.getType() == Material.SKULL_ITEM && item.getItemMeta().getDisplayName().startsWith("§6§lCell Members")){
            new CellMembersInv().open(p);
        }
    }

    private ItemStack[] getContents(Player player){
        PrisonPlayer omp = PrisonPlayer.getPrisonPlayer(player);
        ItemStack[] contents = new ItemStack[getInventory().getSize()];

        Cell cell = omp.getCell();

        {
            ItemStack item = new ItemStack(Material.SKULL_ITEM, 1);
            item.setDurability((short) 3);
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            meta.setDisplayName("§6§lCell Members §6(§7" + (cell.getMemberUUIDs().size() +1) + "§6/§7" + omp.getMaxMembers() + "§6)");
            meta.setOwner(player.getName());
            item.setItemMeta(meta);

            contents[3] = item;
        }
        {
            ItemStack item = new ItemStack(Material.PAPER, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§c§lCell " + cell.getCellId());
            List<String> lore = new ArrayList<>();
            lore.add("  §cOwner: §7" + player.getName());
            lore.add("  §c" + PrisonMessages.WORD_CREATED.get(omp) + ": §7" + cell.getCreatedDate());
            meta.setLore(lore);
            item.setItemMeta(meta);

            contents[5] = item;
        }

        return contents;
    }
}
