package fadidev.orbitmines.skyblock.inventories;

import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.OMInventory;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.api.utils.PlayerUtils;
import fadidev.orbitmines.api.utils.UUIDUtils;
import fadidev.orbitmines.skyblock.OrbitMinesSkyBlock;
import fadidev.orbitmines.skyblock.handlers.Island;
import fadidev.orbitmines.skyblock.handlers.SkyBlockMessages;
import fadidev.orbitmines.skyblock.handlers.SkyBlockPlayer;
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
public class IslandMembersInv extends OMInventory {

    private OrbitMinesSkyBlock skyBlock;

    public IslandMembersInv(){
        skyBlock = OrbitMinesSkyBlock.getSkyBlock();

        setInventory(Bukkit.createInventory(null, 45, "§0§lIsland Party"));
    }

    @Override
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

        SkyBlockPlayer omp = (SkyBlockPlayer) omPlayer;

        if(item.getType() == Material.PAPER && item.getItemMeta().getDisplayName().equals("§d§lIsland Info"))
            new IslandInfoInv().open(omp.getPlayer());
    }

    private ItemStack[] getContents(Player player){
        ItemStack[] contents = new ItemStack[getInventory().getSize()];
        SkyBlockPlayer omp = SkyBlockPlayer.getSkyBlockPlayer(player);
        Island is = omp.getIsland();

        {
            String owner = is.getOwnerName();

            ItemStack item = new ItemStack(Material.SKULL_ITEM, 1);
            item.setDurability((short) 3);
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            meta.setDisplayName("§d§l" + owner);
            List<String> lore = new ArrayList<>();
            lore.add("  §dRank: §7Owner  ");
            if(PlayerUtils.getPlayer(is.getOwner()) != null)
                lore.add("  §dStatus: §aOnline  ");
            else
                lore.add("  §dStatus: §cOffline  ");
            meta.setOwner(owner);
            meta.setLore(lore);
            item.setItemMeta(meta);

            contents[1] = item;
        }

        List<UUID> uuids = is.getMembers();
        int index = 2;
        for(int i = 1; i <= 20; i++){
            ItemStack item = new ItemStack(Material.SKULL_ITEM, 1);
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            if((is.getMaxMembers() -1) >= i){
                item.setDurability((short) 1);

                if(uuids.size() >= i){
                    item.setDurability((short) 3);
                    List<String> lore = new ArrayList<String>();
                    UUID uuid = uuids.get((i -1));
                    Player p = PlayerUtils.getPlayer(uuid);

                    if(p == null){
                        String pname = UUIDUtils.getName(uuid);
                        meta.setDisplayName("§d§l" + pname);
                        lore.add("  §dRank: §7Member  ");
                        lore.add("  §dStatus: §cOffline  ");
                        meta.setOwner(pname);
                    }
                    else{
                        meta.setDisplayName("§d§l" + p.getName());
                        lore.add("  §dRank: §7Member  ");
                        lore.add("  §dStatus: §aOnline  ");
                        meta.setOwner(p.getName());
                    }
                    meta.setLore(lore);
                }
                else{
                    meta.setDisplayName("§7§l" + SkyBlockMessages.WORD_EMPTY.get(omp) + " Slot");
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
            meta.setDisplayName("§d§lIsland Info");
            item.setItemMeta(meta);

            contents[40] = item;
        }

        return contents;
    }
}
