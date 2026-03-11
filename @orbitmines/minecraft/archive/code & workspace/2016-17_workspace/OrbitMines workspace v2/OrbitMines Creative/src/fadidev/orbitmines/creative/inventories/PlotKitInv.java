package fadidev.orbitmines.creative.inventories;

import fadidev.orbitmines.api.handlers.Kit;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.OMInventory;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.creative.OrbitMinesCreative;
import fadidev.orbitmines.creative.handlers.CreativeMessages;
import fadidev.orbitmines.creative.handlers.CreativePlayer;
import fadidev.orbitmines.creative.handlers.Plot;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ConcurrentModificationException;

/**
 * Created by Fadi on 9-9-2016.
 */
public class PlotKitInv extends OMInventory {

    private OrbitMinesCreative creative;
    private Kit kit;

    public PlotKitInv(Kit kit){
        creative = OrbitMinesCreative.getCreative();
        this.kit = kit;

        setInventory(Bukkit.createInventory(null, 27, kit != null ? "§0§lKit: " + kit.getName() : "§0§lKit: "));
    }

    @Override
    public void open(Player player) {
        getInventory().setContents(getContents(player));
        player.openInventory(getInventory());

        registerLast(player);
    }

    @Override
    public void onClick(OMPlayer omPlayer, final InventoryClickEvent e) {
        ItemStack item = e.getCurrentItem();

        if(!ItemUtils.isNull(item) && item.getItemMeta().getDisplayName().endsWith("§7§l" + CreativeMessages.WORD_EMPTY.get(omPlayer)))
            e.setCancelled(true);

        CreativePlayer omp = (CreativePlayer) omPlayer;
        Player p = omp.getPlayer();

        final ItemStack cursor = e.getCursor();
        final int slot = e.getSlot();

        if(e.getClickedInventory() != null && e.getClickedInventory().getName().equals(getInventory().getTitle())){
            if(slot != 8 && cursor != null && cursor.getType() != Material.AIR){
                if(slot == 0 || slot == 1 || slot == 2 || slot == 3 || slot == 18 || slot == 19 || slot == 20 || slot == 21 || slot == 22 || slot == 23 || slot == 24 || slot == 25 || slot == 26){
                    e.getInventory().setItem(slot, cursor);
                    if(item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null && item.getItemMeta().getDisplayName().endsWith("§7§l" + CreativeMessages.WORD_EMPTY.get(omp))){
                        new BukkitRunnable(){
                            @Override
                            public void run() {
                                e.setCursor(null);
                            }
                        }.runTaskLater(creative, 1);
                    }
                    else{
                        e.setCancelled(true);
                        e.setCursor(item);
                    }
                }
                else{
                    e.setResult(Event.Result.DENY);
                    omp.updateInventory();
                }

                p.updateInventory();
            }

            if(!ItemUtils.isNull(item) && item.getType() == Material.BARRIER && item.getItemMeta().getDisplayName().equals(CreativeMessages.INV_DELETE_KIT.get(omp))){
                Plot plot = omp.getPlot();
                String kitName = e.getClickedInventory().getName().substring(9);

                try{
                    for(Kit kit : plot.getPvPKits()){
                        if(kit.getName().equals(kitName)){
                            plot.setSetupFinished(false);
                            plot.removeKit(kit);
                        }
                    }
                }catch(ConcurrentModificationException ex){}

                p.closeInventory();
                p.sendMessage(CreativeMessages.INV_KIT_DELETED.get(omp, kitName));

                omp.updateNPCInventory(e.getClickedInventory());
            }
        }

        omp.updateNPCInventory(p.getOpenInventory().getTopInventory());
    }

    public ItemStack[] getContents(Player player){
        CreativePlayer omp = CreativePlayer.getCreativePlayer(player);
        ItemStack[] contents = new ItemStack[getInventory().getSize()];

        if(contents[0] == null){
            if(kit.getHelmet() == null){
                ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("§fHelmet Slot: §7§l" + CreativeMessages.WORD_EMPTY.get(omp));
                item.setItemMeta(meta);
                contents[0] = item;
            }
            else{
                contents[0] = new ItemStack(kit.getHelmet());
            }
        }
        if(contents[1] == null){
            if(kit.getChestplate() == null){
                ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 2);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("§fChestplate Slot: §7§l" + CreativeMessages.WORD_EMPTY.get(omp));
                item.setItemMeta(meta);
                contents[1] = item;
            }
            else{
                contents[1] = new ItemStack(kit.getChestplate());
            }
        }
        if(contents[2] == null){
            if(kit.getLeggings() == null){
                ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 3);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("§fLeggings Slot: §7§l" + CreativeMessages.WORD_EMPTY.get(omp));
                item.setItemMeta(meta);
                contents[2] = item;
            }
            else{
                contents[2] = new ItemStack(kit.getLeggings());
            }
        }
        if(contents[3] == null){
            if(kit.getBoots() == null){
                ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 4);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("§fBoots Slot: §7§l" + CreativeMessages.WORD_EMPTY.get(omp));
                item.setItemMeta(meta);
                contents[3] = item;
            }
            else{
                contents[3] = new ItemStack(kit.getBoots());
            }
        }

        if(contents[8] == null){
            ItemStack item = new ItemStack(Material.BARRIER, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(CreativeMessages.INV_DELETE_KIT.get(omp));
            item.setItemMeta(meta);
            item.setDurability((short) 14);
            contents[8] = item;
        }

        for(int i = 18; i <= 26; i++){
            if(contents[i] == null){
                if(kit.getItem(i - 18) == null){
                    ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, (i - 17));
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName("§fHotbar Slot " + (i - 17) + ": §7§l" + CreativeMessages.WORD_EMPTY.get(omp));
                    item.setItemMeta(meta);
                    contents[i] = item;
                }
                else{
                    contents[i] = new ItemStack(kit.getItem(i - 18));
                }
            }
        }

        return contents;
    }
}
