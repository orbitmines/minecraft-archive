package fadidev.orbitmines.skyblock.handlers;

import fadidev.orbitmines.api.handlers.Cooldowns;
import fadidev.orbitmines.api.handlers.Kit;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.chat.Title;
import fadidev.orbitmines.api.utils.ConfigUtils;
import fadidev.orbitmines.api.utils.enums.ranks.StaffRank;
import fadidev.orbitmines.api.utils.enums.ranks.VIPRank;
import fadidev.orbitmines.skyblock.OrbitMinesSkyBlock;
import fadidev.orbitmines.skyblock.utils.enums.IslandRank;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fadi on 20-9-2016.
 */
public class SkyBlockPlayer extends OMPlayer {

    private static OrbitMinesSkyBlock skyBlock;
    private Island island;
    private IslandRank islandRank;
    private Location homeLocation;
    private Map<Challenge, Integer> challenges;
    private Island teleportingTo;
    private Island invited;
    private String nextUsage;

    public SkyBlockPlayer(Player player, boolean loaded) {
        super(player, loaded);
    }

    @Override
    public String getPrefix() {
        return "";
    }

    @Override
    public void vote() {
        updateVotes();

        getPlayer().getInventory().addItem(new ItemStack(Material.COBBLESTONE, 32));
        getPlayer().getInventory().addItem(new ItemStack(Material.IRON_INGOT, 1));
        getPlayer().getInventory().addItem(new ItemStack(Material.COAL, 4));

        Title t = new Title("§b§lVote", "§832 Cobblestone§7, 1 Iron Ingot, §04 Coal", 20, 40, 20);
        t.send(getPlayer());
    }

    @Override
    public void unloadPlayerData() {
        sendQuitMessage();
    }

    @Override
    public void loadPlayerData() {
        skyBlock = OrbitMinesSkyBlock.getSkyBlock();
        skyBlock.getPlayers().put(getPlayer(), this);
        skyBlock.getSkyBlockPlayers().add(this);
        
        this.challenges = new HashMap<>();

        FileConfiguration playerdata = skyBlock.getConfigManager().get("playerdata");
        this.nextUsage = playerdata.getString("players." + getUUID().toString() + ".NextKitUsage");
        if(playerdata.contains("players." + getUUID().toString() + ".IslandInfo")){
            this.island = Island.getIsland(playerdata.getInt("players." + getUUID().toString() + ".IslandInfo.IslandNumber"));
            this.islandRank = IslandRank.valueOf(playerdata.getString("players." + getUUID().toString() + ".IslandInfo.IslandRank"));
            this.homeLocation = ConfigUtils.parseLocation(playerdata.getString("players." + getUUID().toString() + ".IslandInfo.HomeLocation"));
        }
        if(playerdata.contains("players." + getUUID().toString() + ".Challenges")){
            String[] challengeParts = playerdata.getString("players." + getUUID().toString() + ".Challenges").split("\\|");
            for(String challenge : challengeParts){
                String[] cParts = challenge.split("\\:");

                this.challenges.put(Challenge.getChallenge(Integer.parseInt(cParts[0])), Integer.parseInt(cParts[1]));
            }
        }

        sendJoinMessage();

        setScoreboard(new SkyBlockScoreboard(this));
    }

    @Override
    public boolean canReceiveVelocity() {
        return true;
    }

    /* Getters & Setters */
    public Island getIsland() {
        return island;
    }

    public void setIsland(Island island, IslandRank islandRank) {
        this.island = island;
        this.islandRank = islandRank;
        this.challenges = new HashMap<>();

        clearInventory();
        clearPotionEffects();
        clearLevels();

        if(island != null){
            FileConfiguration playerdata = skyBlock.getConfigManager().get("playerdata");
            playerdata.set("players." + getUUID().toString() + ".IslandInfo.IslandNumber", island.getIslandId());
            playerdata.set("players." + getUUID().toString() + ".IslandInfo.IslandRank", islandRank.toString());
            if(isOwner()){
                Location l = island.getLocation();
                Location l2 = new Location(l.getWorld(), l.getBlockX() +0.5, l.getBlockY(), l.getBlockZ() +2.5, 180, 0);
                playerdata.set("players." + getUUID().toString() + ".IslandInfo.HomeLocation", ConfigUtils.parseString(l2));
                this.homeLocation = l2;
            }
            else{
                Location l = island.getOwnersHomeLocation();
                playerdata.set("players." + getUUID().toString() + ".IslandInfo.HomeLocation",  ConfigUtils.parseString(l));
                this.homeLocation = l;
            }
            playerdata.set("players." + getUUID().toString() + ".Challenges", null);
            skyBlock.getConfigManager().save("playerdata");
        }
        else{
            this.homeLocation = null;
        }
    }

    public IslandRank getIslandRank() {
        return islandRank;
    }

    public void setIslandRank(IslandRank islandRank) {
        this.islandRank = islandRank;

        skyBlock.getConfigManager().get("playerdata").set("players." + getUUID().toString() + ".IslandInfo.IslandRank", islandRank.toString());
        skyBlock.getConfigManager().save("playerdata");
    }

    public Location getHomeLocation() {
        return homeLocation;
    }

    public void setHomeLocation(Location homeLocation) {
        this.homeLocation = homeLocation;

        skyBlock.getConfigManager().get("playerdata").set("players." + getUUID().toString() + ".IslandInfo.HomeLocation", ConfigUtils.parseString(this.homeLocation));
        skyBlock.getConfigManager().save("playerdata");
    }

    public Map<Challenge, Integer> getChallenges() {
        return challenges;
    }

    public Island getTeleportingTo() {
        return teleportingTo;
    }

    public void setTeleportingTo(Island teleportingTo) {
        this.teleportingTo = teleportingTo;
    }

    public Island getInvited() {
        return invited;
    }

    public void setInvited(Island invited) {
        this.invited = invited;
    }
    
    /* Other */

    public boolean hasIsland(){
        return this.island != null;
    }

    public boolean isOwner(){
        return this.islandRank != null && this.islandRank == IslandRank.OWNER;
    }

    public boolean isMember(){
        return this.islandRank != null && this.islandRank == IslandRank.MEMBER;
    }

    public void setChallengeCompleted(Challenge challenge){
        if(this.challenges.containsKey(challenge))
            this.challenges.put(challenge, this.challenges.get(challenge) +1);
        else
            this.challenges.put(challenge, 1);

        saveChallenges();
    }
    
    public int getChallengeCompleted(Challenge challenge){
        if(this.challenges.containsKey(challenge))
            return this.challenges.get(challenge);
        return 0;
    }
    
    private void saveChallenges(){
        String cstring = null;

        for(Challenge c : this.challenges.keySet()){
            if(cstring == null){
                cstring = c.getChallengeID() + ":" + this.challenges.get(c);
            }
            else{
                cstring += "|" + c.getChallengeID() + ":" + this.challenges.get(c);
            }
        }

        skyBlock.getConfigManager().get("playerdata").set("players." + getUUID().toString() + ".Challenges", cstring);
        skyBlock.getConfigManager().save("playerdata");
    }
    
    public void resetChallenges(){
        this.challenges = new HashMap<Challenge, Integer>();

        skyBlock.getConfigManager().get("playerdata").set("players." + getUUID().toString() + ".Challenges", null);
        skyBlock.getConfigManager().save("playerdata");
    }
    
    public boolean isInvited(){
        return this.invited != null;
    }

    public int getMaxMembers(){
        if(hasPerms(VIPRank.EMERALD_VIP) || hasPerms(StaffRank.OWNER))
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

    public boolean inVoid(){
        Location l = getPlayer().getLocation();

        for(int i = 1; i <= l.getBlockY(); i++){
            Block b = l.getWorld().getBlockAt(new Location(l.getWorld(), l.getBlockX(), i, l.getBlockZ()));

            if(!b.isEmpty())
                return false;
        }
        return true;
    }

    public boolean onOwnIsland(Location l2, boolean message){
        Location l = getIsland().getLocation();
        int x = l.getBlockX();
        int z = l.getBlockZ();

        int bDistance = 0;
        int xB = l2.getBlockX() -x;
        int zB = l2.getBlockZ() -z;

        if(xB < 0){
            xB = -xB;
        }
        if(zB < 0){
            zB = -zB;
        }

        if(xB <= zB){
            bDistance = zB;
        }
        else{
            bDistance = xB;
        }

        bDistance = 50 - bDistance;

        if(bDistance < 0){
            OMPlayer omp = OMPlayer.getOMPlayer(getPlayer());
            if(message && !omp.onCooldown(Cooldowns.MESSAGE)){
                getPlayer().sendMessage(SkyBlockMessages.NOT_ON_OWN_ISLAND.get(this));

                omp.resetCooldown(Cooldowns.MESSAGE);
            }

            return false;
        }
        return true;
    }

    public boolean onIsland(Location l2, boolean message){
        if(onOwnIsland(l2, false)){
            return true;
        }

        boolean onIsland = false;
        for(Island is : Island.getUnprotectedIslands()){
            if(!onIsland){
                Location l = is.getLocation();
                int x = l.getBlockX();
                int z = l.getBlockZ();

                int bDistance = 0;
                int xB = l2.getBlockX() -x;
                int zB = l2.getBlockZ() -z;

                if(xB < 0){
                    xB = -xB;
                }
                if(zB < 0){
                    zB = -zB;
                }

                if(xB <= zB){
                    bDistance = zB;
                }
                else{
                    bDistance = xB;
                }

                bDistance = 50 - bDistance;

                if(bDistance >= 0){
                    onIsland = true;
                }
            }
        }

        if(!onIsland && message){
            if(!onCooldown(Cooldowns.MESSAGE)){
                getPlayer().sendMessage(SkyBlockMessages.NOT_ON_OWN_ISLAND.get(this));

                resetCooldown(Cooldowns.MESSAGE);
            }
        }
        return onIsland;
    }

    public void useKit(VIPRank viprank){
        Player p = getPlayer();
        Calendar nextUse = Calendar.getInstance();
        nextUse.add(Calendar.DATE, 1);

        SimpleDateFormat df = new SimpleDateFormat();
        df.applyPattern( "dd-MM-yyyy HH:mm:ss" );
        String nextUsage = df.format(new Date(nextUse.getTimeInMillis()));

        Kit.getKit(viprank.toString()).addItems(p);
        switch(viprank){
            case DIAMOND_VIP:
                p.setLevel(p.getLevel() + 5);
                break;
            case EMERALD_VIP:
                p.setLevel(p.getLevel() + 8);
                break;
            case GOLD_VIP:
                p.setLevel(p.getLevel() + 2);
                break;
            case IRON_VIP:
                p.setLevel(p.getLevel() + 1);
                break;
            default:
                break;
        }

        this.nextUsage = nextUsage;
        skyBlock.getConfigManager().get("playerdata").set("players." + getUUID().toString() + ".NextKitUsage", nextUsage);
        skyBlock.getConfigManager().save("playerdata");
    }
    public boolean canUseKit(){
        if(nextUsage == null)
            return true;

        try{
            return new Date(Calendar.getInstance().getTimeInMillis()).compareTo(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(this.nextUsage)) >= 0;
        }catch(ParseException e){
            return false;
        }
    }

    public static SkyBlockPlayer getSkyBlockPlayer(Player p){
        for(SkyBlockPlayer omp : skyBlock.getSkyBlockPlayers()){
            if(omp.getPlayer() == p)
                return omp;
        }
        return null;
    }
}
