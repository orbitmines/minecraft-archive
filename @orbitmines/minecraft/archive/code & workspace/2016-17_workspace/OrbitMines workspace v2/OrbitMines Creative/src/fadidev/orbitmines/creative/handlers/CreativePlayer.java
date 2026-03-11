package fadidev.orbitmines.creative.handlers;

import fadidev.orbitmines.api.handlers.Cooldowns;
import fadidev.orbitmines.api.handlers.Kit;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.chat.Title;
import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.api.utils.enums.ranks.VIPRank;
import fadidev.orbitmines.creative.OrbitMinesCreative;
import fadidev.orbitmines.creative.inventories.PlotKitInv;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fadi on 14-9-2016.
 */
public class CreativePlayer extends OMPlayer {

    private static OrbitMinesCreative creative;

    private Plot plot;
    private List<String> worldEditCommands;
    private Kit kitSelected;
    private Plot pvPPlot;

    public CreativePlayer(Player player, boolean loaded) {
        super(player, loaded);
    }

    @Override
    public String getPrefix() {
        return "";
    }

    @Override
    public void vote() {

    }

    @Override
    public void unloadPlayerData() {
        if(isInPvPPlot()){
            setKitSelected(null);
            setPvPPlot(null);
            clearInventory();
        }

        sendQuitMessage();
    }

    @Override
    public void loadPlayerData() {
        creative = OrbitMinesCreative.getCreative();

        creative.getPlayers().put(getPlayer(), this);
        creative.getCreativePlayers().add(this);

        this.worldEditCommands = new ArrayList<>();

        FileConfiguration config = creative.getConfigManager().get("playerdata");
        if(config.contains("players." + getUUID().toString() + ".PlotID")){
            this.plot = Plot.getPlot(config.getInt("players." + getUUID().toString() + ".PlotID"));
        }
        if(config.contains("players." + getUUID().toString() + ".WorldEdit")){
            this.worldEditCommands = config.getStringList("players." + getUUID().toString() + ".WorldEdit");
        }

        Permission permission = creative.getPermission();
        String plotWorld = creative.getPlotWorld().getName();

        permission.playerAddTransient(plotWorld, getPlayer(), "worldedit.wand");
        permission.playerAddTransient(plotWorld, getPlayer(), "worldedit.selection.pos");
        permission.playerAddTransient(plotWorld, getPlayer(), "worldedit.region.set");
        permission.playerAddTransient(plotWorld, getPlayer(), "worldedit.region.line");
        permission.playerAddTransient(plotWorld, getPlayer(), "worldedit.region.walls");
        permission.playerAddTransient(plotWorld, getPlayer(), "worldedit.region.replace");

        if(hasPerms(VIPRank.DIAMOND_VIP)){
            permission.playerAddTransient(plotWorld, getPlayer(), "worldedit.history.undo");
            permission.playerAddTransient(plotWorld, getPlayer(), "worldedit.history.redo");
            permission.playerAddTransient(plotWorld, getPlayer(), "worldedit.navigation.ascend");
            permission.playerAddTransient(plotWorld, getPlayer(), "worldedit.navigation.descend");
        }
        if(hasPerms(VIPRank.EMERALD_VIP)){
            permission.playerAddTransient(plotWorld, getPlayer(), "worldedit.navigation.thru.command");
            permission.playerAddTransient(plotWorld, getPlayer(), "worldedit.navigation.jumpto.command");
            permission.playerAddTransient(plotWorld, getPlayer(), "worldedit.navigation.up");
        }

        setScoreboard(new CreativeScoreboard(this));

        sendJoinMessage();
    }

    @Override
    public boolean canReceiveVelocity() {
        return true;
    }

    /* Getters & Setters */

    public Plot getPlot() {
        return plot;
    }

    public void setPlot(Plot plot) {
        this.plot = plot;

        creative.getConfigManager().get("playerdata").set("players." + getUUID().toString() + ".PlotID", plot.getPlotId());
        creative.getConfigManager().save("playerdata");
    }

    public List<String> getWorldEditCommands() {
        return worldEditCommands;
    }

    public Kit getKitSelected() {
        return kitSelected;
    }

    public void setKitSelected(Kit kitSelected) {
        this.kitSelected = kitSelected;
    }

    public Plot getPvPPlot() {
        return pvPPlot;
    }

    public void setPvPPlot(Plot pvPPlot) {
        this.pvPPlot = pvPPlot;
    }

    /* Others */
    public boolean hasPlot(){
        return plot != null;
    }

    public void addWECommand(String worldEditCommand) {
        this.worldEditCommands.add(worldEditCommand);

        creative.getConfigManager().get("playerdata").set("players." + getUUID().toString() + ".WorldEdit", this.worldEditCommands);
        creative.getConfigManager().save("playerdata");
    }

    public boolean hasWEAccess(String cmd){
        return this.worldEditCommands.contains(cmd);
    }

    public boolean isInPvPPlot(){
        return pvPPlot != null;
    }

    public void selectKit(Kit kitSelected){
        Plot plot = this.pvPPlot;

        this.kitSelected = kitSelected;
        getPlayer().teleport(plot.getPvPSpawns().get(Utils.RANDOM.nextInt(plot.getPvPSpawns().size())));
        kitSelected.setItems(getPlayer());
        clearPotionEffects();

        Title t = new Title("", CreativeMessages.SELECTED_KIT.get(this, kitSelected.getName()), 20, 40, 20);
        t.send(getPlayer());
    }

    public int getMaxMembers(){
        if(hasPerms(VIPRank.EMERALD_VIP))
            return 21;
        else if(hasPerms(VIPRank.DIAMOND_VIP))
            return 14;
        else if(hasPerms(VIPRank.GOLD_VIP))
            return 9;
        else if(hasPerms(VIPRank.IRON_VIP))
            return 5;
        else
            return 3;
    }

    public boolean isOnPlot(Location l){
        boolean onPlot = false;
        if(l.getBlockY() != 0){
            if(!isInPvPPlot()){
                if(getPvPPlot() == null && hasPlot())
                    onPlot = onPlot(getPlot(), l);

                if(!onPlot){
                    for(Plot plot : Plot.getMemberOn(getUUID())){
                        if(!onPlot)
                            onPlot = onPlot(plot, l);
                    }
                }
            }
            else{
                if(getPvPPlot().canPvPBuild() && getKitSelected() != null)
                    onPlot = onPlot(getPvPPlot(), l);
            }
        }
        if(!onPlot){
            if(!onCooldown(Cooldowns.MESSAGE)){
                getPlayer().sendMessage(CreativeMessages.CANT_DO_THAT_HERE.get(this));

                resetCooldown(Cooldowns.MESSAGE);
            }
        }

        return onPlot;
    }

    private boolean onPlot(Plot plot, Location l2){
        Location l = plot.getLocation();
        int x = l.getBlockX();
        int z = l.getBlockZ();

        double bDistance = 0;
        double xB = l2.getBlockX() -x;
        double zB = l2.getBlockZ() -z;

        if(xB < 0)
            xB = -xB -1;
        if(zB < 0)
            zB = -zB -1;

        if(xB <= zB)
            bDistance = zB;
        else
            bDistance = xB;

        bDistance = 43.5 - bDistance;

        return bDistance >= 0;
    }

    public void openKitInventory(Kit kit){
        if(getPlot().getPvPInventories().containsKey(kit)){
            getPlot().getPvPInventories().get(kit).open(getPlayer());
        }
        else{
            PlotKitInv inv = new PlotKitInv(kit);
            getPlot().getPvPInventories().put(kit, inv);
            inv.open(getPlayer());
        }
    }

    public void updateNPCInventory(final Inventory inv){
        new BukkitRunnable(){
            public void run(){
                Plot plot = getPlot();
                String kitName = inv.getName().substring(9);

                ItemStack[] contents = new ItemStack[36];
                ItemStack[] armorContents = new ItemStack[4];
                armorContents[0] = inv.getItem(3);
                armorContents[1] = inv.getItem(2);
                armorContents[2] = inv.getItem(1);
                armorContents[3] = inv.getItem(0);

                contents[0] = inv.getItem(18);
                contents[1] = inv.getItem(19);
                contents[2] = inv.getItem(20);
                contents[3] = inv.getItem(21);
                contents[4] = inv.getItem(22);
                contents[5] = inv.getItem(23);
                contents[6] = inv.getItem(24);
                contents[7] = inv.getItem(25);
                contents[8] = inv.getItem(26);

                int index = 0;
                for(ItemStack item : armorContents){
                    if(item != null && item.getType() == Material.STAINED_GLASS_PANE && item.getItemMeta() != null && (item.getItemMeta().getDisplayName().endsWith("§7§lEmpty") || item.getItemMeta().getDisplayName().endsWith("§7§lLeeg"))){
                        armorContents[index] = null;
                    }

                    index++;
                }

                int index2 = 0;
                for(ItemStack item : contents){
                    if(item != null && item.getType() == Material.STAINED_GLASS_PANE && item.getItemMeta() != null && (item.getItemMeta().getDisplayName().endsWith("§7§lEmpty") || item.getItemMeta().getDisplayName().endsWith("§7§lLeeg"))){
                        contents[index2] = null;
                    }

                    index2++;
                }

                for(Kit kit : plot.getPvPKits()){
                    if(kit.getName().equals(kitName)){
                        kit.setContents(contents);
                        kit.setArmorContents(armorContents);
                        plot.setPvPKits(plot.getPvPKits());
                        plot.updateNPC(kit);
                        inv.setContents(plot.getPvPInventories().get(kit).getContents(getPlayer()));
                    }
                }
            }
        }.runTaskLater(creative, 1);
    }

    public static CreativePlayer getCreativePlayer(Player player){
        for(CreativePlayer creativePlayer : creative.getCreativePlayers()){
            if(creativePlayer.getPlayer().getName().equals(player.getName()))
                return creativePlayer;
        }

        return null;
    }
}
