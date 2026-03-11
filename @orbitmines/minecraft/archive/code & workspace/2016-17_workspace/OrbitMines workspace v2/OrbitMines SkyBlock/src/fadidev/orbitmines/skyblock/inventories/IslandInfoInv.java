package fadidev.orbitmines.skyblock.inventories;

import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.OMInventory;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.api.utils.Utils;
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

/**
 * Created by Fadi on 9-9-2016.
 */
public class IslandInfoInv extends OMInventory {

    private OrbitMinesSkyBlock skyBlock;

    public IslandInfoInv(){
        skyBlock = OrbitMinesSkyBlock.getSkyBlock();

        setInventory(Bukkit.createInventory(null, 9, "§0§lIsland Info"));
    }

    @Override
    public void open(Player player) {
        SkyBlockPlayer omp = SkyBlockPlayer.getSkyBlockPlayer(player);
        if(omp.hasIsland()){
            player.openInventory(getInventory());
            getInventory().setContents(getContents(player));

            registerLast(player);
        }
        else{
            omp.getPlayer().sendMessage(SkyBlockMessages.NO_ISLAND.get(omp));
        }
    }

    @Override
    public void onClick(OMPlayer omPlayer, InventoryClickEvent e) {
        e.setCancelled(true);

        ItemStack item = e.getCurrentItem();

        if(ItemUtils.isNull(item))
            return;

        SkyBlockPlayer omp = (SkyBlockPlayer) omPlayer;
        Player p = omp.getPlayer();

        if(item.getType() == Material.SKULL_ITEM && item.getItemMeta().getDisplayName().startsWith("§6§lIsland Party")){
            new IslandMembersInv().open(p);
        }
        else if((item.getType() == Material.ENDER_PEARL || item.getType() == Material.EYE_OF_ENDER) && item.getItemMeta().getDisplayName().startsWith("§3§lTeleport")){
            if(omp.isOwner()){
                omp.getIsland().setTeleportEnabled(!omp.getIsland().isTeleportEnabled());
                new IslandInfoInv().open(p);
            }
            else{
                p.sendMessage(SkyBlockMessages.NOT_OWNER.get(omp));
            }
        }
        else if(item.getType() == Material.STAINED_GLASS_PANE && item.getItemMeta().getDisplayName().startsWith("§7§lIsland " + SkyBlockMessages.WORD_PROTECTION.get(omp))){
            if(omp.isOwner()){
                omp.getIsland().setIslandProtection(!omp.getIsland().hasIslandProtection());
                new IslandInfoInv().open(p);
            }
            else{
                p.sendMessage(SkyBlockMessages.NOT_OWNER.get(omp));
            }
        }
    }

    private ItemStack[] getContents(Player player){
        ItemStack[] contents = new ItemStack[getInventory().getSize()];
        SkyBlockPlayer omp = SkyBlockPlayer.getSkyBlockPlayer(player);
        Island is = omp.getIsland();

        {
            ItemStack item = new ItemStack(Material.SKULL_ITEM, 1);
            item.setDurability((short) 3);
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            meta.setDisplayName("§6§lIsland Party §6(§7" + (is.getMembers().size() +1) + "§6/§7" + is.getMaxMembers() + "§6)");
            meta.setOwner(omp.getPlayer().getName());
            item.setItemMeta(meta);

            contents[1] = item;
        }
        {
            ItemStack item = new ItemStack(Material.PAPER, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§d§lIsland " + is.getIslandId());
            List<String> lore = new ArrayList<>();
            lore.add("  §dOwner: §7" + is.getOwnerName());
            lore.add("  §d" + SkyBlockMessages.WORD_CREATED.get(omp) + ": §7" + is.getCreatedDate());
            meta.setLore(lore);
            item.setItemMeta(meta);

            contents[3] = item;
        }
        {
            ItemStack item = new ItemStack(Material.ENDER_PEARL, 1);
            if(is.isTeleportEnabled()){
                item.setType(Material.EYE_OF_ENDER);
            }
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§3§lTeleport " + Utils.statusString(omp.getLanguage(), is.isTeleportEnabled()));
            item.setItemMeta(meta);

            contents[5] = item;
        }
        {
            ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§7§lIsland " + SkyBlockMessages.WORD_PROTECTION.get(omp) + " " + Utils.statusString(omp.getLanguage(), is.hasIslandProtection()));
            item.setItemMeta(meta);
            item.setDurability(Utils.statusDurability(is.hasIslandProtection()));

            contents[7] = item;
        }

        return contents;
    }
}
