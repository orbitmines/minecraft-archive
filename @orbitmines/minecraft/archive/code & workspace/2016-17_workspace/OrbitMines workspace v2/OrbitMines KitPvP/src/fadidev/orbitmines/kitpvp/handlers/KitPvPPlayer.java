package fadidev.orbitmines.kitpvp.handlers;

import fadidev.orbitmines.api.handlers.*;
import fadidev.orbitmines.api.handlers.chat.Title;
import fadidev.orbitmines.api.handlers.npc.Hologram;
import fadidev.orbitmines.kitpvp.OrbitMinesKitPvP;
import fadidev.orbitmines.kitpvp.utils.enums.KitPvPKit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Fadi on 10-9-2016.
 */
public class KitPvPPlayer extends OMPlayer {

    private static OrbitMinesKitPvP kitPvP;

    private int kills;
    private int deaths;
    private int levels;
    private int exp;
    private int money;
    private int bestStreak;
    private int currentStreak;
    private HashMap<KitPvPKit, Integer> unlockedKits;
    private KitPvPKit kitSelected;
    private Masteries masteries;
    private int kitLevelSelected;
    private boolean isSpectator;
    private int arrowSeconds;
    private List<Entity> summonedUndeath;

    public KitPvPPlayer(Player player, boolean loaded) {
        super(player, loaded);
    }

    @Override
    public String getPrefix() {
        return isSpectator() ? "§eSpec §8| " : "";
    }

    @Override
    public void vote() {
        updateVotes();
        addMoney(500);

        new Title("§b§lVote", "§6+500 Coins", 20, 40, 20).send(getPlayer());
    }

    @Override
    public void unloadPlayerData() {
        getPlayer().teleport(kitPvP.getSpawn());
        clearInventory();

        if(getKitSelected() != null)
            addDeath();

        kitPvP.getKitPvPPlayers().remove(this);

        sendQuitMessage();
    }

    @Override
    public void loadPlayerData() {
        kitPvP = OrbitMinesKitPvP.getKitPvP();

        kitPvP.getPlayers().put(getPlayer(), this);
        kitPvP.getKitPvPPlayers().add(this);

        this.currentStreak = 0;
        this.isSpectator = false;
        this.summonedUndeath = new ArrayList<>();

        String uuid = getUUID().toString();
        try{
            clearInventory();
            kitPvP.getLobbyKit().get(getLanguage()).setItems(getPlayer());

            loadKits(uuid);
            loadMasteries(uuid);
            loadStats(uuid);

            updateLevel();
        }catch(NullPointerException ex){
            ex.printStackTrace();
        }

        sendJoinMessage();

        setScoreboard(new KitPvPScoreboard(this));
    }

    @Override
    public boolean canReceiveVelocity() {
        return true;
    }

    /* Getters & Setters */

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getLevels() {
        return levels;
    }

    public int getExp() {
        return exp;
    }

    public int getMoney() {
        return money;
    }

    public int getBestStreak() {
        return bestStreak;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    public HashMap<KitPvPKit, Integer> getUnlockedKits() {
        return unlockedKits;
    }

    public KitPvPKit getKitSelected() {
        return kitSelected;
    }

    public Masteries getMasteries() {
        return masteries;
    }

    public int getKitLevelSelected() {
        return kitLevelSelected;
    }

    public void setKitLevelSelected(int kitLevelSelected) {
        this.kitLevelSelected = kitLevelSelected;
    }

    public boolean isSpectator() {
        return isSpectator;
    }

    public void setSpectator(boolean spectator) {
        isSpectator = spectator;
    }

    public boolean isPlayer(){
        return !isSpectator;
    }

    public int getArrowSeconds() {
        return arrowSeconds;
    }

    public void setArrowSeconds(int arrowSeconds) {
        this.arrowSeconds = arrowSeconds;
    }

    public List<Entity> getSummonedUndeath() {
        return summonedUndeath;
    }

    /* Other */
    public double getExpRequired(){
        int level = this.levels +1;
        return ((level * 10) / 3 + level * level) * 3;
    }

    public boolean isLevelUp(){
        return getExp() >= getExpRequired();
    }

    public boolean hasMoney(int money){
        return getMoney() >= money;
    }

    public int getUnlockedLevel(KitPvPKit kit){
        if(this.unlockedKits.containsKey(kit))
            return this.unlockedKits.get(kit);
        return 0;
    }

    public void tickArrowTimer(){
        this.arrowSeconds--;
    }

    public void giveKit(KitPvPKit kitPvPKit, int level){
        clearInventory();
        clearPotionEffects();

        Kit kit = kitPvPKit.getKit(level);
        kit.setItems(getPlayer());

        setKitSelected(kitPvPKit);
        setKitLevelSelected(level);

        getPlayer().sendMessage(KitPvPMessages.SELECT_KIT.get(this, kitPvPKit.getName(), level + ""));
        Title t = new Title("", KitPvPMessages.SELECT_KIT.get(this, kitPvPKit.getName(), level + ""), 20, 40, 20);
        t.send(getPlayer());
    }

    public void setKitSelected(KitPvPKit kitSelected){
        this.kitSelected = kitSelected;

        // Reset Cooldowns \\
        this.arrowSeconds = -1;
        if(kitSelected == null){
            OMPlayer omp = OMPlayer.getOMPlayer(getPlayer());

            omp.getCooldowns().clear();
        }
    }

    public void updateLevel(){
        getPlayer().setLevel(this.levels);
        getPlayer().setExp((float) getExp() / (float) getExpRequired());
    }

    public void teleportToMap(){
        KitPvPMap map = kitPvP.getCurrentMap();

        if(isPlayer())
            getPlayer().teleport(map.getRandomSpawn());
        else
            getPlayer().teleport(map.getSpectatorSpawn());

        map.sendJoinMessage(this);
        getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 5, 1);
    }

    public void createKillHologram(Player killer, int coinsAdded){
        final Hologram h = new Hologram(getPlayer().getLocation());
        h.addLine(KitPvPMessages.YOU_KILLED.get(this, getColorName()));
        h.addLine("§6§l+" + coinsAdded + " Coins");
        h.create(killer);

        new BukkitRunnable(){
            public void run(){
                h.delete();
            }
        }.runTaskLater(kitPvP, 100);
    }

    public void requiredMoney(int price){
        Player p = getPlayer();

        p.playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_SCREAM, 5, 1);
        int needed = price - getMoney();
        p.sendMessage(Messages.REQUIRED_CURRENCY.get(this, "§6§l", needed + "", needed == 1 ? "Coin" : "Coins"));
    }

    public double getVIPBooster(){
        switch(getVipRank()){
            case DIAMOND_VIP:
                return 1.20;
            case EMERALD_VIP:
                return 1.50;
            default:
                return 1.00;
        }
    }

    public double getExpBooster(){
        switch(getVipRank()){
            case DIAMOND_VIP:
                return 2.00;
            case EMERALD_VIP:
                return 2.50;
            case GOLD_VIP:
                return 1.50;
            default:
                return 1.00;
        }
    }

    public boolean hasVoted(){
        for(KitPvPMap map : kitPvP.getMaps()){
            if(map.getVotes().contains(getUUID()))
                return true;
        }
        return false;
    }

    public void vote(KitPvPMap map){
        map.getVotes().add(getUUID());

        for(KitPvPMap kitPvPMap : kitPvP.getMaps()) {
            kitPvPMap.updateVoteSign(this);
        }
    }

    public static KitPvPPlayer getKitPvPPlayer(Player player){
        for(KitPvPPlayer kitPvPPlayer : kitPvP.getKitPvPPlayers()){
            if(kitPvPPlayer.getPlayer().getName().equals(player.getName()))
                return kitPvPPlayer;
        }

        return null;
    }

    /* Database */
    public void setKills(int kills){
        this.kills = kills;

        Database.get().update("KitPvP-Kills", "kills", "" + this.kills, "uuid", getUUID().toString());
    }

    public void addKill(){
        this.kills = getKills() +1;

        Database.get().update("KitPvP-Kills", "kills", "" + this.kills, "uuid", getUUID().toString());
    }

    public void setDeaths(int deaths){
        this.deaths = deaths;

        Database.get().update("KitPvP-Deaths", "deaths", "" + this.deaths, "uuid", getUUID().toString());
    }
    public void addDeath(){
        this.deaths += 1;

        Database.get().update("KitPvP-Deaths", "deaths", "" + this.deaths, "uuid", getUUID().toString());
    }

    public void setLevels(int levels){
        this.levels = levels;

        Database.get().update("KitPvP-Levels", "levels", "" + this.levels, "uuid", getUUID().toString());
    }

    public void addLevel(){
        this.levels += 1;

        getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 1);
        getPlayer().sendMessage("§a§lLevel Up! §7(§c§l+ 1 Mastery Point§7)");
        getMasteries().addPoints(1);
        getMasteries().update();
        Database.get().update("KitPvP-Levels", "levels", "" + this.levels, "uuid", getUUID().toString());
    }

    public void setExp(int exp){
        this.exp = exp;

        Database.get().update("KitPvP-Exp", "exp", "" + this.exp, "uuid", getUUID().toString());
    }

    public void addExp(int exp){
        this.exp += exp * getExpBooster();

        Database.get().update("KitPvP-Exp", "exp", "" + this.exp, "uuid", getUUID().toString());
    }

    public void setMoney(int money){
        this.money = money;

        Database.get().update("KitPvP-Money", "money", "" + this.money, "uuid", getUUID().toString());
    }

    public void addMoney(int money){
        this.money += money;

        Database.get().update("KitPvP-Money", "money", "" + this.money, "uuid", getUUID().toString());
    }

    public void removeMoney(int money){
        this.money -= money;

        Database.get().update("KitPvP-Money", "money", "" + this.money, "uuid", getUUID().toString());
    }

    public void setBestStreak(int bestStreak){
        this.bestStreak = bestStreak;

        Database.get().update("KitPvP-BestStreak", "beststreak", "" + this.bestStreak, "uuid", getUUID().toString());
    }

    public void setUnlockedKitLevel(KitPvPKit kit, int level){
        this.unlockedKits.put(kit, level);

        if(!Database.get().containsPath("Kits-" + kit.getName(), "uuid", "uuid", getUUID().toString()))
            Database.get().insert("Kits-" + kit.getName(), "uuid`, `" + kit.getName().toLowerCase(), getUUID().toString() + "', '" + 1);
        else
            Database.get().update("Kits-" + kit.getName(), kit.getName().toLowerCase(), "" + level, "uuid", getUUID().toString());
    }

    private void loadKits(String uuid){
        if(!Database.get().containsPath("Kits-Knight", "uuid", "uuid", uuid))
            Database.get().insert("Kits-Knight", "uuid`, `knight", uuid + "', '" + 1);
        if(!Database.get().containsPath("Kits-Archer", "uuid", "uuid", uuid))
            Database.get().insert("Kits-Archer", "uuid`, `archer", uuid + "', '" + 1);
        if(!Database.get().containsPath("Kits-Soldier", "uuid", "uuid", uuid))
            Database.get().insert("Kits-Soldier", "uuid`, `soldier", uuid + "', '" + 1);

        this.unlockedKits = new HashMap<>();
        for(KitPvPKit kit : KitPvPKit.values()){
            if(kit != KitPvPKit.FARMER && kit != KitPvPKit.UNDEATH_KING && kit != KitPvPKit.ENGINEER){
                if(Database.get().containsPath("Kits-" + kit.getName(), "uuid", "uuid", uuid)){
                    this.unlockedKits.put(kit, Database.get().getInt("Kits-" + kit.getName(), kit.getName().toLowerCase(), "uuid", uuid));
                }
            }
        }
    }

    private void loadMasteries(String uuid){
        String masteries = levels + "|0|0|0|0";
        if(Database.get().containsPath("KitPvP-Masteries", "uuid", "uuid", uuid)){
            masteries = Database.get().getString("KitPvP-Masteries", "masteries", "uuid", uuid);
        }
        else{
            Database.get().insert("KitPvP-Masteries", "uuid`, `masteries", uuid + "', '" + masteries);
        }

        this.masteries = Masteries.fromString(getPlayer(), masteries);
    }

    private void loadStats(String uuid){
        if(Database.get().containsPath("KitPvP-Kills", "uuid", "uuid", uuid)){
            kills = Database.get().getInt("KitPvP-Kills", "kills", "uuid", uuid);
        }
        else{
            Database.get().insert("KitPvP-Kills", "uuid`, `kills", uuid + "', '" + 0);
        }

        if(Database.get().containsPath("KitPvP-Deaths", "uuid", "uuid", uuid)){
            deaths = Database.get().getInt("KitPvP-Deaths", "deaths", "uuid", uuid);
        }
        else{
            Database.get().insert("KitPvP-Deaths", "uuid`, `deaths", uuid + "', '" + 0);
        }

        if(Database.get().containsPath("KitPvP-Levels", "uuid", "uuid", uuid)){
            levels = Database.get().getInt("KitPvP-Levels", "levels", "uuid", uuid);
        }
        else{
            Database.get().insert("KitPvP-Levels", "uuid`, `levels", uuid + "', '" + 0);
        }

        if(Database.get().containsPath("KitPvP-Exp", "uuid", "uuid", uuid)){
            exp = Database.get().getInt("KitPvP-Exp", "exp", "uuid", uuid);
        }
        else{
            Database.get().insert("KitPvP-Exp", "uuid`, `exp", uuid + "', '" + 0);
        }

        if(Database.get().containsPath("KitPvP-Money", "uuid", "uuid", uuid)){
            money = Database.get().getInt("KitPvP-Money", "money", "uuid", uuid);
        }
        else{
            Database.get().insert("KitPvP-Money", "uuid`, `money", uuid + "', '" + 0);
        }

        if(Database.get().containsPath("KitPvP-BestStreak", "uuid", "uuid", uuid)){
            bestStreak = Database.get().getInt("KitPvP-BestStreak", "beststreak", "uuid", uuid);
        }
        else{
            Database.get().insert("KitPvP-BestStreak", "uuid`, `beststreak", uuid + "', '" + 0);
        }
    }
}
