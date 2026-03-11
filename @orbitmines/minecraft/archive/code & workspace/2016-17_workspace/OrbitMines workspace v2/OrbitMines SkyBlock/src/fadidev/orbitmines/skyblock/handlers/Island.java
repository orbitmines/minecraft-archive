package fadidev.orbitmines.skyblock.handlers;

import fadidev.orbitmines.api.handlers.Database;
import fadidev.orbitmines.api.handlers.Message;
import fadidev.orbitmines.api.handlers.chat.Title;
import fadidev.orbitmines.api.utils.ConfigUtils;
import fadidev.orbitmines.api.utils.PlayerUtils;
import fadidev.orbitmines.api.utils.UUIDUtils;
import fadidev.orbitmines.api.utils.enums.ranks.StaffRank;
import fadidev.orbitmines.api.utils.enums.ranks.VIPRank;
import fadidev.orbitmines.skyblock.OrbitMinesSkyBlock;
import fadidev.orbitmines.skyblock.utils.SkyBlockUtils;
import fadidev.orbitmines.skyblock.utils.enums.IslandRank;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Island {

	private static OrbitMinesSkyBlock skyBlock;
	private int islandId;
	private Location location;
	private String createdDate;
	private UUID owner;
	private List<UUID> members;
	private boolean teleportEnabled;
	private boolean islandProtection;
	private int maxMembers;
	private boolean netherGenerated;

	public Island(int islandId, Location location, String createdDate, UUID owner, List<UUID> members, boolean teleportEnabled, boolean islandProtection, boolean netherGenerated){
		skyBlock = OrbitMinesSkyBlock.getSkyBlock();
		this.islandId = islandId;
		this.location = location;
		this.createdDate = createdDate;
		this.owner = owner;
		this.members = members;
		this.teleportEnabled = teleportEnabled;
		this.islandProtection = islandProtection;
		this.netherGenerated = netherGenerated;
		this.maxMembers = 0;
	}

	public int getIslandId() {
		return islandId;
	}

	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
		
		skyBlock.getConfigManager().get("islands").set("islands." + this.islandId + ".IslandLocation", ConfigUtils.parseString(this.location));
		skyBlock.getConfigManager().save("islands");
	}
	
	public Location getNetherLocation(){
		return new Location(skyBlock.getSkyBlockNetherWorld(), getLocation().getX(), 50.0, getLocation().getZ());
	}
	
	public Location getNetherHomeLocation(){
		return new Location(skyBlock.getSkyBlockNetherWorld(), getLocation().getX() +0.5, 51.0, getLocation().getZ() +0.5, 180, 0);
	}

	public String getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
		
		skyBlock.getConfigManager().get("islands").set("islands." + this.islandId + ".CreatedDate", this.createdDate);
		skyBlock.getConfigManager().save("islands");
	}

	public UUID getOwner() {
		return owner;
	}
	
	public void setOwner(UUID owner) {
		this.owner = owner;
		
		skyBlock.getConfigManager().get("islands").set("islands." + this.islandId + ".Players.Owner", this.owner.toString());
		skyBlock.getConfigManager().save("islands");
	}
	
	public String getOwnerName(){
		Player p = PlayerUtils.getPlayer(this.owner);
		
		if(p != null){
			return p.getName();
		}
		return UUIDUtils.getName(this.owner);
	}
	
	public Location getOwnersHomeLocation(){
		Player p = PlayerUtils.getPlayer(this.owner);
		
		if(p != null){
			return SkyBlockPlayer.getSkyBlockPlayer(p).getHomeLocation();
		}
		return ConfigUtils.parseLocation(skyBlock.getConfigManager().get("playerdata").getString("players." + getOwner().toString() + ".IslandInfo.HomeLocation"));
	}
	
	public List<UUID> getMembers() {
		return members;
	}

	public void setMembers(List<UUID> members) {
		this.members = members;
		
		skyBlock.getConfigManager().get("islands").set("islands." + this.islandId + ".Players.Members", ConfigUtils.parseStringList(this.members));
		skyBlock.getConfigManager().save("islands");
	}

	public void addMember(UUID member){
		this.members.add(member);
		
		skyBlock.getConfigManager().get("islands").set("islands." + this.islandId + ".Players.Members", ConfigUtils.parseStringList(this.members));
		skyBlock.getConfigManager().save("islands");
	}

	public void removeMember(UUID member){
		this.members.remove(member);
		
		skyBlock.getConfigManager().get("islands").set("islands." + this.islandId + ".Players.Members", ConfigUtils.parseStringList(this.members));
		skyBlock.getConfigManager().save("islands");
	}

	public boolean isTeleportEnabled() {
		return teleportEnabled;
	}

	public void setTeleportEnabled(boolean teleportEnabled) {
		this.teleportEnabled = teleportEnabled;
		
		skyBlock.getConfigManager().get("islands").set("islands." + this.islandId + ".TeleportEnabled", this.teleportEnabled);
		skyBlock.getConfigManager().save("islands");
	}

	public boolean hasIslandProtection() {
		return islandProtection;
	}

	public void setIslandProtection(boolean islandProtection) {
		this.islandProtection = islandProtection;
		
		skyBlock.getConfigManager().get("islands").set("islands." + this.islandId + ".IslandProtection", this.islandProtection);
		skyBlock.getConfigManager().save("islands");
	}
	
	public void sendMessageToMembers(Message message, String... args){
		List<String> stringUuids = ConfigUtils.parseStringList(this.members);
		for(SkyBlockPlayer omp : skyBlock.getSkyBlockPlayers()){
			if(stringUuids.contains(omp.getUUID().toString()))
				omp.getPlayer().sendMessage(message.get(omp, args));
		}
	}

	public int getMaxMembers() {
		if(maxMembers == 0){
			Player p = PlayerUtils.getPlayer(this.owner);
			
			if(p != null){
				SkyBlockPlayer omp = SkyBlockPlayer.getSkyBlockPlayer(p);
				
				if(omp.hasPerms(VIPRank.EMERALD_VIP)){
					this.maxMembers = 21;
					return 21;
				}
				else if(omp.hasPerms(VIPRank.DIAMOND_VIP)){
					this.maxMembers = 14;
					return 14;
				}
				else if(omp.hasPerms(VIPRank.GOLD_VIP)){
					this.maxMembers = 9;
					return 9;
				}
				else if(omp.hasPerms(VIPRank.IRON_VIP)){
					this.maxMembers = 5;
					return 5;
				}
				else{
					this.maxMembers = 3;
					return 3;
				}
			}
			else{
				VIPRank viprank = VIPRank.USER;
				StaffRank staffrank = null;
				
				if(Database.get().containsPath("Rank-VIP", "uuid", "uuid", this.owner.toString()))
					viprank = VIPRank.valueOf(Database.get().getString("Rank-VIP", "vip", "uuid", this.owner.toString()));
				
				if(Database.get().containsPath("Rank-Staff", "uuid", "uuid", this.owner.toString()))
					staffrank = StaffRank.valueOf(Database.get().getString("Rank-Staff", "staff", "uuid", this.owner.toString()));
				
				if(viprank == VIPRank.EMERALD_VIP || staffrank == StaffRank.OWNER){
					this.maxMembers = 21;
					return 21;
				}
				else if(viprank == VIPRank.DIAMOND_VIP){
					this.maxMembers = 14;
					return 14;
				}
				else if(viprank == VIPRank.GOLD_VIP){
					this.maxMembers = 9;
					return 9;
				}
				else if(viprank == VIPRank.IRON_VIP){
					this.maxMembers = 5;
					return 5;
				}
				else{
					this.maxMembers = 3;
					return 3;
				}
			}
		}
		return maxMembers;
	}

	public void setMaxMembers(int maxMembers) {
		this.maxMembers = maxMembers;
	}

	public boolean isNetherGenerated() {
		return netherGenerated;
	}
	public void setNetherGenerated(boolean netherGenerated) {
		this.netherGenerated = netherGenerated;
		
		skyBlock.getConfigManager().get("islands").set("islands." + this.islandId + ".NetherGenerated", netherGenerated);
		skyBlock.getConfigManager().save("islands");
	}
	
	public void saveAll(){
		FileConfiguration islands = skyBlock.getConfigManager().get("islands");
		islands.set("islands." + this.islandId + ".IslandLocation", ConfigUtils.parseString(this.location));
		islands.set("islands." + this.islandId + ".CreatedDate", this.createdDate);
		islands.set("islands." + this.islandId + ".TeleportEnabled", this.teleportEnabled);
		islands.set("islands." + this.islandId + ".NetherGenerated", this.netherGenerated);
		islands.set("islands." + this.islandId + ".Players.Owner", this.owner.toString());
		islands.set("islands." + this.islandId + ".Players.Members", ConfigUtils.parseStringList(this.members));
		skyBlock.getConfigManager().save("islands");
	}
	
	public void delete(){
		skyBlock.getIslands().remove(this);
		
		skyBlock.getConfigManager().get("islands").set("islands." + this.islandId, null);
		skyBlock.getConfigManager().save("islands");
	}
	
	public static List<Island> getUnprotectedIslands(){
		List<Island> islands = new ArrayList<>();
		for(SkyBlockPlayer omp : skyBlock.getSkyBlockPlayers()){
			Island is = omp.getIsland();
			
			if(omp.hasIsland() && !is.hasIslandProtection() && !islands.contains(is))
				islands.add(is);
		}
		return islands;
	}
	
	public static Island getIsland(int islandId){
		for(Island is : skyBlock.getIslands()){
			if(is.getIslandId() == islandId)
				return is;
		}
		return null;
	}
	
	public static Island getIsland(Player player){
		for(Island is : skyBlock.getIslands()){
			if(is.getOwner().toString().equals(player.getUniqueId().toString()) || ConfigUtils.parseStringList(is.getMembers()).contains(player.getUniqueId().toString()))
				return is;
		}
		return null;
	}
	
	public static void generate(final SkyBlockPlayer omp){
        if(skyBlock == null)
            skyBlock = OrbitMinesSkyBlock.getSkyBlock();

		Player p = omp.getPlayer();
		int islandId = skyBlock.getTotalIslands() +1;
		
		World w = skyBlock.getSkyBlockWorld();
				
		Location l = SkyBlockUtils.getNextLocation(w);
		for(SkyBlockPlayer omp2 : skyBlock.getSkyBlockPlayers()){
			if(omp2.hasPerms(StaffRank.MODERATOR)){
				if(l != null)
					omp2.getPlayer().sendMessage(SkyBlockMessages.GENERATING_ISLAND.get(omp2, p.getName()) + " §7(§d" + l.getWorld().getName() + "§7, §d" + l.getBlockX() + "§7, §d" + l.getBlockY() + "§7, §d" + l.getBlockZ() + "§7)");
			}
		}
		
		SkyBlockUtils.generateIsland(l);
					
		Island is = new Island(islandId, l, new SimpleDateFormat("dd-MM-yyyy").format(new Date()), p.getUniqueId(), new ArrayList<UUID>(), false, true, false);
		is.saveAll();
		skyBlock.getIslands().add(is);
        skyBlock.setTotalIslands(islandId);

        skyBlock.setLastLocation(l);
		
		omp.setIsland(is, IslandRank.OWNER);
								
		p.teleport(omp.getHomeLocation());
        p.setFallDistance(0);
		p.playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 5, 1);

		Title t = new Title("", "§7" + SkyBlockMessages.WORD_TELEPORTED_TO.get(omp) + " " + SkyBlockMessages.WORD_YOUR.get(omp) + " §dIsland§7.", 20, 40, 20);
		t.send(p);
	}
}