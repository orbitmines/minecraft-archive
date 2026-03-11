package fadidev.orbitmines.prison.inventory;

import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.OMInventory;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.api.utils.PlayerUtils;
import fadidev.orbitmines.api.utils.UUIDUtils;
import fadidev.orbitmines.prison.OrbitMinesPrison;
import fadidev.orbitmines.prison.handlers.PrisonMessages;
import fadidev.orbitmines.prison.handlers.PrisonPlayer;
import fadidev.orbitmines.prison.handlers.Cell;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Fadi on 9-9-2016.
 */
public class CellMembersInv extends OMInventory {

    public CellMembersInv(){
        setInventory(Bukkit.createInventory(null, 45, "§0§lCell Members"));
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

        Player p = omPlayer.getPlayer();

        if(item.getType() == Material.PAPER && item.getItemMeta().getDisplayName().equals("§c§lCell Info"))
            new CellInfoInv().open(p);
    }

    private ItemStack[] getContents(Player player){
        PrisonPlayer omp = PrisonPlayer.getPrisonPlayer(player);
        ItemStack[] contents = new ItemStack[getInventory().getSize()];

        Cell cell = omp.getCell();

        {
            ItemStack item = new ItemStack(Material.SKULL_ITEM, 1);
            item.setDurability((short) 3);
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            meta.setDisplayName("§c§l" + player.getName());
            List<String> lore = new ArrayList<>();
            lore.add("  §cRank: §7Owner  ");
            lore.add("  §cStatus: §aOnline  ");
            meta.setOwner(player.getName());
            meta.setLore(lore);
            item.setItemMeta(meta);

            contents[1] = item;
        }

        List<UUID> uuids = cell.getMemberUUIDs();
        int index = 2;
        for(int i = 1; i <= 20; i++){
            ItemStack item = new ItemStack(Material.SKULL_ITEM, 1);
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            if((omp.getMaxMembers() -1) >= i){
                item.setDurability((short) 1);

                if(uuids.size() >= i){
                    item.setDurability((short) 3);
                    List<String> lore = new ArrayList<>();
                    UUID uuid = uuids.get((i -1));
                    Player p = PlayerUtils.getPlayer(uuid);

                    if(p == null){
                        String playerName = UUIDUtils.getName(uuid);
                        meta.setDisplayName("§c§l" + playerName);
                        lore.add("  §cRank: §7Member  ");
                        lore.add("  §cStatus: §cOffline  ");
                        meta.setOwner(playerName);
                    }
                    else{
                        meta.setDisplayName("§c§l" + p.getName());
                        lore.add("  §cRank: §7Member  ");
                        lore.add("  §cStatus: §aOnline  ");
                        meta.setOwner(p.getName());
                    }
                    meta.setLore(lore);
                }
                else{
                    meta.setDisplayName("§7§l" + PrisonMessages.WORD_EMPTY.get(omp) + " Slot");
                }

                item.setItemMeta(meta);
            }
            else{
                if(i >= 14){
                    meta.setDisplayName("§a§lEmerald VIP §7§lSlot");
                }
                else if(i >= 9){
                    meta.setDisplayName("§9§lDiamond VIP §7§lSlot");
                }
                else if(i >= 5){
                    meta.setDisplayName("§6§lGold VIP §7§lSlot");
                }
                else if(i >= 3){
                    meta.setDisplayName("§7§lIron VIP §7§lSlot");
                }
                else{}
            }
            item.setItemMeta(meta);

            contents[index] = item;

            index++;
            if(index == 8){index = 10;}
            if(index == 17){index = 19;}
        }
        {
            ItemStack item = new ItemStack(Material.PAPER, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§c§lCell Info");
            item.setItemMeta(meta);

            contents[40] = item;
        }

        return contents;
    }
}
