package fadidev.orbitmines.survival.handlers;

import fadidev.orbitmines.api.handlers.Database;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.chat.Title;
import fadidev.orbitmines.api.utils.ConfigUtils;
import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.api.utils.enums.ranks.VIPRank;
import fadidev.orbitmines.survival.OrbitMinesSurvival;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fadi on 15-9-2016.
 */
public class SurvivalPlayer extends OMPlayer {

    private static OrbitMinesSurvival survival;
    private int money;
    private int extraHomes;
    private int extraShops;
    private int extraWarps;
    private List<Home> homes;
    private boolean homeTeleporting;
    private boolean warpTeleporting;
    private Home teleportingTo;
    private Warp warpingTo;
    private List<ShopSign> shopSigns;
    private List<Warp> warps;
    private List<Warp> favoriteWarps;
    private SurvivalPlayer tpRequest;
    private SurvivalPlayer tphereRequest;
    private SurvivalPlayer invseeRequest;
    private Location backLocation;

    public SurvivalPlayer(Player player, boolean loaded) {
        super(player, loaded);
    }

    @Override
    public String getPrefix() {
        return "";
    }

    @Override
    public void vote() {
        updateVotes();
        addClaimBlocks(100);
        addMoney(25);

        Title t = new Title("§b§lVote", "§2+25$§7, §8+100 Claimblocks", 20, 40, 20);
        t.send(getPlayer());
    }

    @Override
    public void unloadPlayerData() {
        sendQuitMessage();
    }

    @Override
    public void loadPlayerData() {
        survival = OrbitMinesSurvival.getSurvival();
        survival.getPlayers().put(getPlayer(), this);
        survival.getSurvivalPlayers().add(this);

        this.homes = new ArrayList<>();
        this.shopSigns = ShopSign.getShopSigns(getPlayer());
        this.warps = Warp.getWarps(getPlayer());
        this.favoriteWarps = new ArrayList<>();
        this.extraHomes = 0;
        this.extraShops = 0;
        this.extraWarps = 0;

        boolean tp = false;
        String uuid = getUUID().toString();
        try{

            FileConfiguration playerdata = survival.getConfigManager().get("playerdata");
            if(playerdata.contains("players." + getUUID().toString() + ".FavoriteWarps")){
                String[] warpIds = playerdata.getString("players." + getUUID().toString() + ".FavoriteWarps").split("\\|");

                for(String warpId : warpIds){
                    this.favoriteWarps.add(Warp.getWarp(Integer.parseInt(warpId)));
                }
            }

            if(playerdata.contains("players." + getUUID().toString() + ".LastLocation")){
                this.backLocation = ConfigUtils.parseLocation(playerdata.getString("players." + getUUID().toString() + ".LastLocation"));
            }
            else{
                playerdata.set("players." + getUUID().toString() + ".LastLocation", ConfigUtils.parseString(getPlayer().getLocation()));
                tp = true;
            }

            if(playerdata.contains("players." + getUUID().toString() + ".Homes")){
                for(String homeName : playerdata.getConfigurationSection("players." + getUUID().toString() + ".Homes").getKeys(false)){
                    this.homes.add(new Home(this, homeName, ConfigUtils.parseLocation(playerdata.getString("players." + getUUID().toString() + ".Homes." + homeName))));
                }
            }

            loadMoney(uuid);
            loadExtraHomes(uuid);
            loadExtraShops(uuid);
            loadExtraWarps(uuid);

        }catch(NullPointerException ex){
            ex.printStackTrace();
        }

        sendJoinMessage();

        setScoreboard(new SurvivalScoreboard(this));

        if(!tp)
            return;

        new BukkitRunnable(){
            public void run(){
                getPlayer().teleport(survival.getSpawn());
            }
        }.runTaskLater(survival, 5);
    }

    @Override
    public boolean canReceiveVelocity() {
        return true;
    }

    /* Getters & Setters */
    public int getMoney() {
        return money;
    }

    public int getExtraHomes() {
        return extraHomes;
    }

    public int getExtraShops() {
        return extraShops;
    }

    public int getExtraWarps() {
        return extraWarps;
    }

    public List<Home> getHomes() {
        return homes;
    }

    public Home getTeleportingTo() {
        return teleportingTo;
    }

    public void setTeleportingTo(Home teleportingTo) {
        this.teleportingTo = teleportingTo;
    }

    public Warp getWarpingTo() {
        return warpingTo;
    }

    public void setWarpingTo(Warp warpingTo) {
        this.warpingTo = warpingTo;
    }

    public boolean isHomeTeleporting() {
        return homeTeleporting;
    }

    public void setHomeTeleporting(boolean homeTeleporting) {
        this.homeTeleporting = homeTeleporting;
    }

    public boolean isWarpTeleporting() {
        return warpTeleporting;
    }

    public void setWarpTeleporting(boolean warpTeleporting) {
        this.warpTeleporting = warpTeleporting;
    }

    public List<ShopSign> getShopSigns() {
        return shopSigns;
    }

    public List<Warp> getWarps() {
        return warps;
    }

    public List<Warp> getFavoriteWarps() {
        return favoriteWarps;
    }

    public SurvivalPlayer getTpRequest() {
        return tpRequest;
    }

    public void setTpRequest(SurvivalPlayer tpRequest) {
        this.tpRequest = tpRequest;
    }

    public SurvivalPlayer getTphereRequest() {
        return tphereRequest;
    }

    public void setTphereRequest(SurvivalPlayer tphereRequest) {
        this.tphereRequest = tphereRequest;
    }

    public SurvivalPlayer getInvseeRequest() {
        return invseeRequest;
    }

    public void setInvseeRequest(SurvivalPlayer invseeRequest) {
        this.invseeRequest = invseeRequest;
    }

    public Location getBackLocation() {
        return backLocation;
    }

    public void setBackLocation(Location backLocation) {
        this.backLocation = backLocation;

        survival.getConfigManager().get("playerdata").set("players." + getUUID().toString() + ".LastLocation", ConfigUtils.parseString(getBackLocation()));
        survival.getConfigManager().save("playerdata");
    }

    public static SurvivalPlayer getSurvivalPlayer(Player player){
        for(SurvivalPlayer sp : survival.getSurvivalPlayers()){
            if(sp.getPlayer() == player)
                return sp;
        }
        return null;
    }

    /* Other */
    public boolean hasMoney(int money) {
        return this.money >= money;
    }

    public Home getHome(String homename){
        for(Home home : this.homes){
            if(home.getName().equals(homename)){
                return home;
            }
        }
        return null;
    }

    public void removeHome(Home home){
        this.homes.remove(home);

        survival.getConfigManager().get("playerdata").set("players." + getUUID().toString() + ".Homes." + home.getName(), null);
        survival.getConfigManager().save("playerdata");
    }

    public void setHome(String homename){
        Home home = getHome(homename);

        survival.getConfigManager().get("playerdata").set("players." + getUUID().toString() + ".Homes." + homename, ConfigUtils.parseString(getPlayer().getLocation()));
        survival.getConfigManager().save("playerdata");

        if(home == null){
            this.homes.add(new Home(this, homename, getPlayer().getLocation()));
            getPlayer().sendMessage(SurvivalMessages.CMD_SET_NEW_HOME.get(this, homename));
        }
        else{
            home.setLocation(getPlayer().getLocation());
            getPlayer().sendMessage(SurvivalMessages.CMD_SET_HOME.get(this, home.getName()));
        }
    }

    public void setFavoriteWarps(List<Warp> favoriteWarps) {
        this.favoriteWarps = favoriteWarps;

        saveFavoriteWarps();
    }

    public void addFavoriteWarp(Warp favoriteWarp) {
        this.favoriteWarps.add(favoriteWarp);

        saveFavoriteWarps();
    }

    public void removeFavoriteWarp(Warp favoriteWarp) {
        this.favoriteWarps.remove(favoriteWarp);

        saveFavoriteWarps();
    }

    public boolean hasTPRequest(){
        return this.tpRequest != null;
    }

    public boolean hasTPHereRequest(){
        return this.tphereRequest != null;
    }

    public boolean hasInvseeRequest(){
        return this.invseeRequest != null;
    }

    public boolean hasBackLocation(){
        return this.backLocation != null;
    }

    private void saveFavoriteWarps(){
        String fwarps = "";
        for(Warp warp : this.favoriteWarps){
            if(fwarps.equals(""))
                fwarps = warp.getWarpId() + "";
            else
                fwarps += "|" + warp.getWarpId();
        }

        if(!fwarps.equals(""))
            survival.getConfigManager().get("playerdata").set("players." + getUUID().toString() + ".FavoriteWarps", fwarps);
        else
            survival.getConfigManager().get("playerdata").set("players." + getUUID().toString() + ".FavoriteWarps", null);
        survival.getConfigManager().save("playerdata");
    }

    public void addClaimBlocks(int claimBlocks) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adjustbonusclaimblocks " + getPlayer().getName() + " " + claimBlocks);
    }

    public int getHomesAllowed(){
        if(hasPerms(VIPRank.EMERALD_VIP))
            return 100 + this.extraHomes;
        else if(hasPerms(VIPRank.DIAMOND_VIP))
            return 50 + this.extraHomes;
        else if(hasPerms(VIPRank.GOLD_VIP))
            return 25 + this.extraHomes;
        else if(hasPerms(VIPRank.IRON_VIP))
            return 10 + this.extraHomes;
        else
            return 3 + this.extraHomes;
    }

    public int getShopsAllowed(){
        if(hasPerms(VIPRank.EMERALD_VIP))
            return 70 + this.extraShops;
        else if(hasPerms(VIPRank.DIAMOND_VIP))
            return 30 + this.extraShops;
        else if(hasPerms(VIPRank.GOLD_VIP))
            return 10 + this.extraShops;
        else if(hasPerms(VIPRank.IRON_VIP))
            return 5 + this.extraShops;
        else
            return 3 + this.extraShops;
    }

    public int getWarpsAllowed(){
        if(hasPerms(VIPRank.EMERALD_VIP))
            return 1 + this.extraWarps;
        else
            return this.extraWarps;
    }

    public boolean isInPvP(){
        if(getPlayer().getWorld().getName().equals(survival.getLobby().getName())){
            Location l = getPlayer().getLocation();

            return l.getX() >= 12 && l.getX() <= 29 && l.getZ() >= 40 && l.getZ() <= 65;
        }
        return false;
    }

    public void teleportToPvPArea(){
        List<Location> pvPSpawns = survival.getPvPSpawns();
        getPlayer().teleport(pvPSpawns.get(Utils.RANDOM.nextInt(pvPSpawns.size())));
    }

    /* Database */
    public void setMoney(int money) {
        this.money = money;

        Database.get().update("Survival-Money", "money", "" + getMoney(), "uuid", getUUID().toString());
    }

    public void addMoney(int money) {
        this.money += money;

        Database.get().update("Survival-Money", "money", "" + getMoney(), "uuid", getUUID().toString());

        Title t = new Title("", "§2+" + money + "$", 20, 40, 20);
        t.send(getPlayer());
    }

    public void removeMoney(int money) {
        this.money -= money;

        Database.get().update("Survival-Money", "money", "" + getMoney(), "uuid", getUUID().toString());
    }

    public void setExtraHomes(int extraHomes) {
        this.extraHomes = extraHomes;

        if(Database.get().containsPath("Survival-ExtraHomes", "uuid", "uuid", getUUID().toString())){
            Database.get().update("Survival-ExtraHomes", "extrahomes", "" + getExtraHomes(), "uuid", getUUID().toString());
        }
        else{
            Database.get().insert("Survival-ExtraHomes", "uuid`, `extrahomes", getUUID().toString() + "', '" + extraHomes);
        }
    }

    public void setExtraShops(int extraShops) {
        this.extraShops = extraShops;

        if(Database.get().containsPath("Survival-ExtraShops", "uuid", "uuid", getUUID().toString())){
            Database.get().update("Survival-ExtraShops", "extrashops", "" + getExtraShops(), "uuid", getUUID().toString());
        }
        else{
            Database.get().insert("Survival-ExtraShops", "uuid`, `extrashops", getUUID().toString() + "', '" + extraShops);
        }
    }

    public void setExtraWarps(int extraWarps) {
        this.extraWarps = extraWarps;

        if(Database.get().containsPath("Survival-ExtraWarps", "uuid", "uuid", getUUID().toString())){
            Database.get().update("Survival-ExtraWarps", "extrawarps", "" + getExtraWarps(), "uuid", getUUID().toString());
        }
        else{
            Database.get().insert("Survival-ExtraWarps", "uuid`, `extrawarps", getUUID().toString() + "', '" + extraWarps);
        }
    }

    private void loadMoney(String uuid){
        if(Database.get().containsPath("Survival-Money", "uuid", "uuid", uuid)){
            this.money = Database.get().getInt("Survival-Money", "money", "uuid", uuid);
        }
        else{
            this.money = 50;
            Database.get().insert("Survival-Money", "uuid`, `money", uuid + "', '" + 50);
        }
    }

    private void loadExtraHomes(String uuid){
        if(Database.get().containsPath("Survival-ExtraHomes", "uuid", "uuid", uuid)){
            extraHomes = Database.get().getInt("Survival-ExtraHomes", "extrahomes", "uuid", uuid);
        }
    }

    private void loadExtraShops(String uuid){
        if(Database.get().containsPath("Survival-ExtraShops", "uuid", "uuid", uuid)){
            extraShops = Database.get().getInt("Survival-ExtraShops", "extrashops", "uuid", uuid);
        }
    }

    private void loadExtraWarps(String uuid){
        if(Database.get().containsPath("Survival-ExtraWarps", "uuid", "uuid", uuid)){
            extraWarps = Database.get().getInt("Survival-ExtraWarps", "extrawarps", "uuid", uuid);
        }
    }
}
