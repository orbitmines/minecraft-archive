package fadidev.orbitmines.creative.inventories;

import fadidev.orbitmines.api.handlers.Kit;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.OMInventory;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.creative.OrbitMinesCreative;
import fadidev.orbitmines.creative.handlers.CreativeMessages;
import fadidev.orbitmines.creative.handlers.CreativePlayer;
import fadidev.orbitmines.creative.handlers.Plot;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
public class PlotSetupInv extends OMInventory {

    private OrbitMinesCreative creative;

    public PlotSetupInv(){
        creative = OrbitMinesCreative.getCreative();

        setInventory(Bukkit.createInventory(null, 27, "§0§lPlot Setup"));
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

        CreativePlayer omp = (CreativePlayer) omPlayer;
        Player p = omp.getPlayer();
        Plot plot = omp.getPlot();

        if(item.getType() == Material.STAINED_GLASS_PANE){
            if(item.getItemMeta().getDisplayName().startsWith("§7" + CreativeMessages.WORD_SET.get(omp) + " Max Players: §a§l")){
                plot.setPvPMaxPlayers(plot.getPvPMaxPlayers() +1);
                new PlotSetupInv().open(p);
            }
            else if(item.getItemMeta().getDisplayName().startsWith("§7" + CreativeMessages.WORD_SET.get(omp) + " Max Players: §c§l")){
                plot.setPvPMaxPlayers(plot.getPvPMaxPlayers() -1);
                new PlotSetupInv().open(p);
            }
            else if(item.getItemMeta().getDisplayName().startsWith("§7" + CreativeMessages.WORD_SET.get(omp) + " §a§lSpawnpoint #")){
                List<Location> spawns = plot.getPvPSpawns();
                spawns.add(p.getLocation());
                plot.setPvPSpawns(spawns);

                p.closeInventory();
                p.sendMessage(CreativeMessages.INV_SET_SPAWNPOINT.get(omp, spawns.size() + ""));
            }
            else if(item.getItemMeta().getDisplayName().equals("§7Reset §c§lSpawnpoints")){
                List<Location> spawns = plot.getPvPSpawns();
                spawns.clear();
                plot.setPvPSpawns(spawns);
                plot.setSetupFinished(false);

                p.closeInventory();
                p.sendMessage(CreativeMessages.INV_RESET_SPAWNPOINTS.get(omp));
            }
            else if(item.getItemMeta().getDisplayName().equals("§7" + CreativeMessages.WORD_SET.get(omp) + " §a§lLobby§7")){
                plot.setPvPLobbyLocation(p.getLocation());

                p.closeInventory();
                p.sendMessage(CreativeMessages.INV_SET_LOBBY.get(omp));
            }
            else if(item.getItemMeta().getDisplayName().equals("§7Arrow Entities: " + Utils.statusString(omp.getLanguage(), true))){
                plot.setPvPArrows(true);
                new PlotSetupInv().open(p);
            }
            else if(item.getItemMeta().getDisplayName().equals("E§7Arrow Entities: " + Utils.statusString(omp.getLanguage(), false))){
                plot.setPvPArrows(false);
                new PlotSetupInv().open(p);
            }
            else if(item.getItemMeta().getDisplayName().equals("§7" + CreativeMessages.WORD_BUILDING.get(omp) + ": " + Utils.statusString(omp.getLanguage(), true))){
                plot.setPvPBuild(true);
                new PlotSetupInv().open(p);
            }
            else if(item.getItemMeta().getDisplayName().equals("§7" + CreativeMessages.WORD_BUILDING.get(omp) + ": " + Utils.statusString(omp.getLanguage(), false))){
                plot.setPvPBuild(false);
                new PlotSetupInv().open(p);
            }
            else if(item.getItemMeta().getDisplayName().equals("§7Item Drops: " + Utils.statusString(omp.getLanguage(), true))){
                plot.setPvPDrops(true);
                new PlotSetupInv().open(p);
            }
            else if(item.getItemMeta().getDisplayName().equals("§7Item Drops: " + Utils.statusString(omp.getLanguage(), false))){
                plot.setPvPDrops(false);
                new PlotSetupInv().open(p);
            }
            else if(item.getItemMeta().getDisplayName().equals(CreativeMessages.INV_ADD_KIT.get(omp))){
                new PlotKitNameGUI(omp).open();
            }
        }
        else if(item.getType() == Material.STAINED_CLAY && item.getItemMeta().getDisplayName().equals("§a§l" + CreativeMessages.WORD_FINISH.get(omp) + " Setup")){
            plot.setSetupFinished(true);

            p.closeInventory();
            p.sendMessage(CreativeMessages.INV_SETUP_FINISHED.get(omp));
            p.sendMessage(CreativeMessages.INV_CAN_NOW_PVP.get(omp));
        }
    }

    private ItemStack[] getContents(Player player){
        CreativePlayer omp = CreativePlayer.getCreativePlayer(player);
        ItemStack[] contents = new ItemStack[getInventory().getSize()];

        Plot plot = omp.getPlot();

        {
            ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
            item.setDurability((short) 5);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§7" + CreativeMessages.WORD_SET.get(omp) + " Max Players: §a§l" + (plot.getPvPMaxPlayers() +1));
            if(plot.getPvPMaxPlayers() == 50){
                item.setDurability((short) 7);
                meta.setDisplayName("§8" + CreativeMessages.WORD_UNAVAILABLE.get(omp));
            }
            item.setItemMeta(meta);

            contents[0] = item;
        }
        {
            ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
            item.setDurability((short) 5);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§7" + CreativeMessages.WORD_SET.get(omp) + " §a§lSpawnpoint #" + (plot.getPvPSpawns().size() +1));
            if(plot.getPvPSpawns().size() == 50){
                item.setDurability((short) 7);
                meta.setDisplayName("§8" + CreativeMessages.WORD_UNAVAILABLE.get(omp));
            }
            item.setItemMeta(meta);

            contents[1] = item;
        }
        {
            ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
            item.setDurability((short) 5);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§7" + CreativeMessages.WORD_SET.get(omp) + " §a§lLobby§7");
            item.setItemMeta(meta);

            contents[2] = item;
        }
        {
            ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
            item.setDurability((short) 5);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§7Arrow Entities: " + Utils.statusString(omp.getLanguage(), true));
            if(plot.hasPvPArrows()){
                item.setDurability((short) 7);
                meta.setDisplayName("§8" + CreativeMessages.WORD_UNAVAILABLE.get(omp));
            }
            item.setItemMeta(meta);

            contents[3] = item;
        }
        {
            ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
            item.setDurability((short) 5);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§7" + CreativeMessages.WORD_BUILDING.get(omp) + ": " + Utils.statusString(omp.getLanguage(), true));
            if(plot.canPvPBuild()){
                item.setDurability((short) 7);
                meta.setDisplayName("§8" + CreativeMessages.WORD_UNAVAILABLE.get(omp));
            }
            item.setItemMeta(meta);

            contents[4] = item;
        }
        {
            ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
            item.setDurability((short) 5);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§7Item Drops: " + Utils.statusString(omp.getLanguage(), true));
            if(plot.hasPvPDrops()){
                item.setDurability((short) 7);
                meta.setDisplayName("§8" + CreativeMessages.WORD_UNAVAILABLE.get(omp));
            }
            item.setItemMeta(meta);

            contents[5] = item;
        }
        {
            ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
            item.setDurability((short) 5);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(CreativeMessages.INV_ADD_KIT.get(omp));
            if(plot.getPvPKits().size() == 4){
                item.setDurability((short) 7);
                meta.setDisplayName("§8" + CreativeMessages.WORD_UNAVAILABLE.get(omp));
            }
            item.setItemMeta(meta);

            contents[6] = item;
        }
        {
            ItemStack item = new ItemStack(Material.SKULL_ITEM, plot.getPvPMaxPlayers());
            item.setDurability((short) 3);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§7Max Players: §7§l" + plot.getPvPMaxPlayers());
            item.setItemMeta(meta);

            contents[9] = item;
        }
        {
            ItemStack item = new ItemStack(Material.ENDER_PEARL, plot.getPvPSpawns().size());
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§7Spawnpoints: §7§l" + plot.getPvPSpawns().size());
            item.setItemMeta(meta);

            contents[10] = item;
        }
        {
            ItemStack item = new ItemStack(Material.EYE_OF_ENDER, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§7Lobby");
            item.setItemMeta(meta);

            contents[11] = item;
        }
        {
            ItemStack item = new ItemStack(Material.ARROW, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§7Arrow Entities: " + Utils.statusString(omp.getLanguage(), plot.hasPvPArrows()));
            item.setItemMeta(meta);

            contents[12] = item;
        }
        {
            ItemStack item = new ItemStack(Material.GRASS, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§7" + CreativeMessages.WORD_BUILDING.get(omp) + ": " + Utils.statusString(omp.getLanguage(), plot.canPvPBuild()));
            item.setItemMeta(meta);

            contents[13] = item;
        }
        {
            ItemStack item = new ItemStack(Material.GOLDEN_APPLE, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§7Item Drops: " + Utils.statusString(omp.getLanguage(), plot.hasPvPDrops()));
            item.setItemMeta(meta);

            contents[14] = item;
        }
        {
            ItemStack item = new ItemStack(Material.DIAMOND_SWORD, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§7Kits: §7§l" + plot.getPvPKits().size());
            if(plot.getPvPKits().size() > 0){
                List<String> lore = new ArrayList<>();
                lore.add("");
                for(Kit kit : plot.getPvPKits()){
                    lore.add(" §7- §7§l" + kit.getName() + " ");
                }
                meta.setLore(lore);
            }
            item.setItemMeta(meta);

            contents[15] = creative.getApi().getNms().customItem().hideFlags(item, 2);
        }
        {
            boolean canfinish = true;

            ItemStack item = new ItemStack(Material.STAINED_CLAY, 1);
            item.setDurability((short) 5);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§a§l" + CreativeMessages.WORD_FINISH.get(omp) + " Setup");
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(" §7Max Players: §a§l✔ ");
            if(plot.getPvPSpawns().size() > 0){
                lore.add(" §7Spawnpoints: §a§l✔ ");
            }
            else{
                lore.add(" §7Spawnpoints: §4§l✘ ");
                canfinish = false;
            }
            if(plot.getPvPLobbyLocation() != null){
                lore.add(" §7Lobby: §a§l✔ ");
            }
            else{
                lore.add(" §7Lobby: §4§l✘ ");
                canfinish = false;
            }
            lore.add(" §7Arrow Entities: §a§l✔ ");
            lore.add(" §7" + CreativeMessages.WORD_BUILDING.get(omp) + ": §a§l✔ ");
            lore.add(" §7Item Drops: §a§l✔ ");
            if(plot.getPvPKits().size() > 0){
                lore.add(" §7Kits: §a§l✔ ");
            }
            else{
                lore.add(" §7Kits: §4§l✘ ");
                canfinish = false;
            }
            lore.add("");
            if(canfinish){
                lore.add(" " + CreativeMessages.INV_CAN_FINISH_SETUP.get(omp) + ": §a§l✔ ");
            }
            else{
                meta.setDisplayName("§c§l" + CreativeMessages.WORD_FINISH.get(omp) + " Setup");
                item.setDurability((short) 14);
                lore.add(" " + CreativeMessages.INV_CAN_FINISH_SETUP.get(omp) + ": §4§l✘ ");
            }
            meta.setLore(lore);
            item.setItemMeta(meta);

            contents[17] = item;
        }
        {
            ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
            item.setDurability((short) 14);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§7" + CreativeMessages.WORD_SET.get(omp) + " Max Players: §c§l" + (plot.getPvPMaxPlayers() -1));
            if(plot.getPvPMaxPlayers() == 4){
                item.setDurability((short) 7);
                meta.setDisplayName("§8" + CreativeMessages.WORD_UNAVAILABLE.get(omp));
            }
            item.setItemMeta(meta);

            contents[18] = item;
        }
        {
            ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
            item.setDurability((short) 14);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§7Reset §c§lSpawnpoints");
            if(plot.getPvPSpawns().size() == 0){
                item.setDurability((short) 7);
                meta.setDisplayName("§8" + CreativeMessages.WORD_UNAVAILABLE.get(omp));
            }
            item.setItemMeta(meta);

            contents[19] = item;
        }
        {
            ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
            item.setDurability((short) 7);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§8" + CreativeMessages.WORD_UNAVAILABLE.get(omp));
            item.setItemMeta(meta);

            contents[20] = item;
        }
        {
            ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
            item.setDurability((short) 14);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§7Arrow Entities: " + Utils.statusString(omp.getLanguage(), false));
            if(!plot.hasPvPArrows()){
                item.setDurability((short) 7);
                meta.setDisplayName("§8" + CreativeMessages.WORD_UNAVAILABLE.get(omp));
            }
            item.setItemMeta(meta);

            contents[21] = item;
        }
        {
            ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
            item.setDurability((short) 14);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§7" + CreativeMessages.WORD_BUILDING.get(omp) + ": " + Utils.statusString(omp.getLanguage(), false));
            if(!plot.canPvPBuild()){
                item.setDurability((short) 7);
                meta.setDisplayName("§8" + CreativeMessages.WORD_UNAVAILABLE.get(omp));
            }
            item.setItemMeta(meta);

            contents[22] = item;
        }
        {
            ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
            item.setDurability((short) 14);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§7Item Drops: " + Utils.statusString(omp.getLanguage(), false));
            if(!plot.hasPvPDrops()){
                item.setDurability((short) 7);
                meta.setDisplayName("§8" + CreativeMessages.WORD_UNAVAILABLE.get(omp));
            }
            item.setItemMeta(meta);

            contents[23] = item;
        }
        {
            ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
            item.setDurability((short) 7);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§8" + CreativeMessages.WORD_UNAVAILABLE.get(omp));
            item.setItemMeta(meta);

            contents[24] = item;
        }

        return contents;
    }
}
