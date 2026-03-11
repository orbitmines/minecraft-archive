package fadidev.orbitmines.prison.handlers;

import fadidev.orbitmines.api.handlers.Cooldowns;
import fadidev.orbitmines.api.handlers.Database;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.chat.ActionBar;
import fadidev.orbitmines.api.handlers.chat.Title;
import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.api.utils.enums.ranks.VIPRank;
import fadidev.orbitmines.prison.OrbitMinesPrison;
import fadidev.orbitmines.prison.utils.enums.MineType;
import fadidev.orbitmines.prison.utils.enums.Rank;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fadi on 19-9-2016.
 */
public class PrisonPlayer extends OMPlayer {

    private static OrbitMinesPrison prison;

    private Rank rank;
    private int gold;
    private boolean clockEnabled;
    private List<ShopSign> shopSigns;
    private Shop shop;
    private Cell cell;
    private Cell teleportingTo;

    public PrisonPlayer(Player player, boolean loaded) {
        super(player, loaded);
    }

    @Override
    public String getPrefix() {
        return "§a§l" + getRank().toString() + " §8| ";
    }

    @Override
    public void vote() {
        updateVotes();

        ItemStack item = new ItemStack(Material.GOLD_PICKAXE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6Vote Pickaxe");
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.DIG_SPEED, 10);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        item.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 10);
        getPlayer().getInventory().addItem(item);

        Title t = new Title("§b§lVote", "§61 Vote Pickaxe", 20, 40, 20);
        t.send(getPlayer());
    }

    @Override
    public void unloadPlayerData() {
        for(Mine mine : prison.getMines()){
            if(mine.getType() == MineType.NORMAL && hasPerms(mine.getRank()) && mine.isInMine(getPlayer().getLocation()))
                getPlayer().teleport(mine.getResetLocation());
        }

        if(getCooldowns().containsKey(PrisonCooldowns.RESET_MINE) || getCooldowns().containsKey(PrisonCooldowns.STARTER_KIT)){
            long resetMine = getCooldown(PrisonCooldowns.RESET_MINE);
            long starterKit = getCooldown(PrisonCooldowns.STARTER_KIT);
            getCooldowns().clear();

            if(resetMine != -1)
                getCooldowns().put(PrisonCooldowns.RESET_MINE, resetMine);
            if(starterKit != -1)
                getCooldowns().put(PrisonCooldowns.STARTER_KIT, starterKit);

            PrisonCooldowns.PREV_COOLDOWNS.put(getUUID(), getCooldowns());
        }

        Database.get().update("Prison-ClockEnabled", "clockenabled", isClockEnabled() + "", "uuid", getUUID().toString());

        sendQuitMessage();
    }

    @Override
    public void loadPlayerData() {
        prison = OrbitMinesPrison.getPrison();
        prison.getPlayers().put(getPlayer(), this);
        prison.getPrisonPlayers().add(this);

        this.shopSigns = ShopSign.getShopSigns(getPlayer());
        this.shop = Shop.getShop(getUUID());

        if(getShop() != null)
            getShop().setOwnerName(getPlayer().getName());

        FileConfiguration config = prison.getConfigManager().get("playerdata");
        if(config.contains("players." + getUUID().toString() + ".CellID")){
            this.cell = Cell.getCell(config.getInt("players." + getUUID().toString() + ".CellID"));
        }

        String uuid = getUUID().toString();
        boolean tp = false;
        try{

            tp = loadRank(uuid);
            loadGold(uuid);
            loadClockEnabled(uuid);

        }catch(NullPointerException ex){
            ex.printStackTrace();
        }

        sendJoinMessage();

        setScoreboard(new PrisonScoreboard(this));

        if(PrisonCooldowns.PREV_COOLDOWNS.containsKey(getUUID())){
            setCooldowns(PrisonCooldowns.PREV_COOLDOWNS.get(getUUID()));
            PrisonCooldowns.PREV_COOLDOWNS.remove(getUUID());
        }

        if(!tp)
            return;

        new BukkitRunnable(){
            public void run(){
                getPlayer().teleport(prison.getSpawn());
            }
        }.runTaskLater(prison, 5);
    }

    @Override
    public boolean canReceiveVelocity() {
        return false;
    }

    /* Getters & Setters */
    public Rank getRank() {
        return rank;
    }

    public int getGold() {
        return gold;
    }

    public boolean isClockEnabled() {
        return clockEnabled;
    }

    public List<ShopSign> getShopSigns() {
        return shopSigns;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;

        prison.getConfigManager().get("playerdata").set("players." + getUUID().toString() + ".CellID", cell.getCellId());
        prison.getConfigManager().save("playerdata");
    }

    public Cell getTeleportingTo() {
        return teleportingTo;
    }

    public void setTeleportingTo(Cell teleportingTo) {
        this.teleportingTo = teleportingTo;
    }

    /* Others */
    public boolean hasCell(){
        return cell != null;
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

    public boolean isOnCell(Location l){
        boolean onCell = false;
        if(l.getBlockY() != 0){
            if(hasCell())
                onCell = onCell(getCell(), l);

            if(!onCell){
                for(Cell cell : Cell.getMemberOn(getUUID())){
                    if(!onCell)
                        onCell = onCell(cell, l);
                }
            }
        }
        if(!onCell){
            if(!onCooldown(Cooldowns.MESSAGE)){
                getPlayer().sendMessage(PrisonMessages.CANT_DO_THAT_HERE.get(this));

                resetCooldown(Cooldowns.MESSAGE);
            }
        }

        return onCell;
    }

    private boolean onCell(Cell cell, Location l2){
        Location l = cell.getLocation();

        if(l2.getY() < 51 || l2.getY() > 80)
            return false;

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

        bDistance = 13.5 - bDistance;

        return bDistance >= 0;
    }
    
    public List<Rank> getRankPerms(){
        List<Rank> ranks = new ArrayList<>();

        for(Rank rank : Rank.values()){
            if(hasPerms(rank)){
                ranks.add(rank);
            }
        }

        return ranks;
    }

    public boolean hasPerms(Rank rank){
        return getRank().ordinal() >= rank.ordinal();
    }

    public boolean canRankup(){
        return hasGold(getRank().getRankupPrice());
    }

    public void rankup(){
        Rank nextRank = getRank().getNextRank();

        getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 1);
        PrisonMessages.RANKUP.broadcast(getName(), nextRank.getName());

        if(nextRank.getNextRank() == null)
            getPlayer().sendMessage(PrisonMessages.CMD_RANKUP_HIGHEST.get(this));

        removeGold(getRank().getRankupPrice());
        setRank(nextRank);
        removeCooldown(PrisonCooldowns.STARTER_KIT);
    }

    public boolean hasGold(int gold){
        return getGold() >= gold;
    }

    public Mine inMine(Location l, boolean message){
        Mine inMine = null;

        for(Mine mine : prison.getMines()){
            if(hasPerms(mine.getRank()) && mine.isInMine(l)) {
                inMine = mine;
                break;
            }
        }

        if(inMine == null && message){
            if(!onCooldown(Cooldowns.MESSAGE)){
                getPlayer().sendMessage(PrisonMessages.CANT_DO_THAT_HERE.get(this));

                resetCooldown(Cooldowns.MESSAGE);
            }
        }

        return inMine;
    }

    public boolean isInPvP(){
        return getPlayer().getWorld().getName().equals(prison.getLobby().getName()) && (getPlayer().getLocation().getZ() <= -14 || getPlayer().getLocation().getY() <= 0);
    }

    public void requiredGold(int gold){
        Player p = getPlayer();

        p.playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_SCREAM, 5, 1);
        int required = gold - getGold();
        getPlayer().sendMessage(Messages.REQUIRED_CURRENCY.get(this, "§6§l", required + "", "Gold"));
    }

    public double getGoldMultiplier(){
        if(hasPerms(VIPRank.EMERALD_VIP))
            return 1.75;
        else if(hasPerms(VIPRank.DIAMOND_VIP))
            return 1.4;
        else if(hasPerms(VIPRank.GOLD_VIP))
            return 1.2;
        else if(hasPerms(VIPRank.IRON_VIP))
            return 1.1;
        else
            return 1;
    }

    public static PrisonPlayer getPrisonPlayer(Player p){
        for(PrisonPlayer omp : prison.getPrisonPlayers()){
            if(omp.getPlayer() == p)
                return omp;
        }
        return null;
    }

    /* Database */
    public void setRank(Rank rank) {
        this.rank = rank;

        Database.get().update("Prison-Rank", "rank", "" + getRank().ordinal(), "uuid", getUUID().toString());
    }

    public void setGold(int gold) {
        this.gold = gold;

        Database.get().update("Prison-Gold", "gold", "" + getGold(), "uuid", getUUID().toString());
    }

    public void addGold(int gold) {
        this.gold += gold;

        ActionBar ab = new ActionBar(getPlayer(), "§6+" + gold + " Gold", 40) ;
        ab.send();

        Database.get().update("Prison-Gold", "gold", "" + getGold(), "uuid", getUUID().toString());
    }

    public void removeGold(int gold) {
        this.gold -= gold;

        ActionBar ab = new ActionBar(getPlayer(), "§6-" + gold + " Gold", 40);
        ab.send();

        Database.get().update("Prison-Gold", "gold", "" + getGold(), "uuid", getUUID().toString());
    }

    public void setClockEnabled(boolean clockEnabled) {
        this.clockEnabled = clockEnabled;

        getPlayer().sendMessage("§7" + PrisonMessages.WORD_CLOCK.get(this) + " " + Utils.statusString(getLanguage(), clockEnabled) + "§7!");

        if(clockEnabled)
            return;
        
        for(Mine mine : prison.getMines()){
            if(mine.getType() == MineType.NORMAL && hasPerms(mine.getRank()))
                mine.hideTimer(getPlayer());
        }
    }

    private boolean loadRank(String uuid){
        if(Database.get().containsPath("Prison-Rank", "uuid", "uuid", uuid)){
            rank = Rank.fromId(Database.get().getInt("Prison-Rank", "rank", "uuid", uuid));
            return false;
        }
        else{
            Database.get().insert("Prison-Rank", "uuid`, `rank", uuid + "', '" + 0);
            rank = Rank.fromId(0);
            return true;
        }
    }

    private void loadGold(String uuid){
        if(Database.get().containsPath("Prison-Gold", "uuid", "uuid", uuid)){
            gold = Database.get().getInt("Prison-Gold", "gold", "uuid", uuid);
        }
        else{
            Database.get().insert("Prison-Gold", "uuid`, `gold", uuid + "', '" + 0);
            this.gold = 0;
        }
    }

    private void loadClockEnabled(String uuid){
        if(Database.get().containsPath("Prison-ClockEnabled", "uuid", "uuid", uuid)){
            clockEnabled = Boolean.parseBoolean(Database.get().getString("Prison-ClockEnabled", "clockenabled", "uuid", uuid));
        }
        else{
            Database.get().insert("Prison-ClockEnabled", "uuid`, `clockenabled", uuid + "', '" + false);
            this.clockEnabled = false;
        }
    }
}
