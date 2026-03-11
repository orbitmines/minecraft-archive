package me.O_o_Fadi_o_O.OrbitMines.utils.skyblock;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import me.O_o_Fadi_o_O.OrbitMines.Start;
import me.O_o_Fadi_o_O.OrbitMines.managers.ConfigManager;
import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.SkyBlockServer;
import me.O_o_Fadi_o_O.OrbitMines.utils.Title;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.StaffRank;
import me.O_o_Fadi_o_O.OrbitMines.utils.skyblock.SkyBlockUtils.IslandRank;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Island {

	private int islandid;
	private Location location;
	private String createddate;
	private UUID owner;
	private List<UUID> members;
	private boolean teleportenabled;

	public Island(int islandid, Location location, String createddate, UUID owner, List<UUID> members, boolean teleportenabled){
		this.islandid = islandid;
		this.location = location;
		this.createddate = createddate;
		this.owner = owner;
		this.members = members;
		this.teleportenabled = teleportenabled;
	}

	public int getIslandID() {
		return islandid;
	}

	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
		
		ConfigManager.islands.set("islands." + this.islandid + ".IslandLocation", Utils.getStringFromLocation(this.location));
		ConfigManager.saveIslands();
	}

	public String getCreatedDate() {
		return createddate;
	}
	public void setCreatedDate(String createddate) {
		this.createddate = createddate;
		
		ConfigManager.islands.set("islands." + this.islandid + ".CreatedDate", this.createddate);
		ConfigManager.saveIslands();
	}

	public UUID getOwner() {
		return owner;
	}
	public void setOwner(UUID owner) {
		this.owner = owner;
		
		ConfigManager.islands.set("islands." + this.islandid + ".Players.Owner", this.owner.toString());
		ConfigManager.saveIslands();
	}
	public Location getOwnersHomeLocation(){
		Player p = Utils.getPlayer(this.owner);
		
		if(p != null){
			return OMPlayer.getOMPlayer(p).getSkyBlockPlayer().getHomeLocation();
		}
		return Utils.getLocationFromString(ConfigManager.playerdata.getString("players." + getOwner().toString() + ".IslandInfo.HomeLocation"));
	}
	
	public List<UUID> getMembers() {
		return members;
	}
	public void setMembers(List<UUID> members) {
		this.members = members;
		
		ConfigManager.islands.set("islands." + this.islandid + ".Players.Members", Utils.getStringList(this.members));
		ConfigManager.saveIslands();
	}
	public void addMember(UUID member){
		this.members.add(member);
		
		ConfigManager.islands.set("islands." + this.islandid + ".Players.Members", Utils.getStringList(this.members));
		ConfigManager.saveIslands();
	}
	public void removeMember(UUID member){
		this.members.remove(member);
		
		ConfigManager.islands.set("islands." + this.islandid + ".Players.Members", Utils.getStringList(this.members));
		ConfigManager.saveIslands();
	}

	public boolean isTeleportEnabled() {
		return teleportenabled;
	}
	public void setTeleportEnabled(boolean teleportenabled) {
		this.teleportenabled = teleportenabled;
		
		ConfigManager.islands.set("islands." + this.islandid + ".TeleportEnabled", this.teleportenabled);
		ConfigManager.saveIslands();
	}
	
	public void saveAll(){
		FileConfiguration islands = ConfigManager.islands;
		islands.set("islands." + this.islandid + ".IslandLocation", Utils.getStringFromLocation(this.location));
		islands.set("islands." + this.islandid + ".CreatedDate", this.createddate);
		islands.set("islands." + this.islandid + ".TeleportEnabled", this.teleportenabled);
		islands.set("islands." + this.islandid + ".Players.Owner", this.owner.toString());
		islands.set("islands." + this.islandid + ".Players.Members", Utils.getStringList(this.members));
		ConfigManager.saveIslands();
	}
	
	public void delete(){
		ServerData.getSkyBlock().getIslands().remove(this);
		
		ConfigManager.islands.set("islands." + this.islandid, null);
		ConfigManager.saveIslands();
	}
	
	public static List<Island> getIslands(){
		return ServerData.getSkyBlock().getIslands();
	}
	
	public static Island getIsland(int islandid){
		for(Island is : ServerData.getSkyBlock().getIslands()){
			if(is.getIslandID() == islandid){
				return is;
			}
		}
		return null;
	}
	
	public static Island getIsland(Player player){
		for(Island is : ServerData.getSkyBlock().getIslands()){
			if(is.getOwner().toString().equals(player.getUniqueId().toString()) || Utils.getStringList(is.getMembers()).contains(player.getUniqueId().toString())){
				return is;
			}
		}
		return null;
	}
	
	public static void generate(final SkyBlockPlayer sbp){
		final SkyBlockServer skyblock = ServerData.getSkyBlock();
		final Player p = sbp.getPlayer();
		final int islandid = skyblock.getTotalIslands() +1;
		
		Title t = new Title("", "§dPreparing World...");
		t.send(p);
		p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
		
		new BukkitRunnable(){
			public void run(){
				final World w = skyblock.getSkyblockWorld();
				
				Title t = new Title("", "§dGenerating Island...");
				t.send(p);
				p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
				
				new BukkitRunnable(){
					public void run(){
						final Location l = SkyBlockUtils.getNextLocation(w);
						for(Player player : Bukkit.getOnlinePlayers()){
							OMPlayer omplayer = OMPlayer.getOMPlayer(player);
							if(omplayer.hasPerms(StaffRank.Moderator)){
								if(l != null){
									player.sendMessage("§7Generating §d" + p.getName() + "'s Island§7... §7(§d" + l.getWorld().getName() + "§7, §d" + l.getBlockX() + "§7, §d" + l.getBlockY() + "§7, §d" + l.getBlockZ() + "§7)");
								}
								else{
									player.sendMessage("§4§lERROR§7! §d" + p.getName() + "§7tried to generate an §dIsland§7.");
									Bukkit.getConsoleSender().sendMessage("§4§lERROR§7! §d" + p.getName() + "§7tried to generate an §dIsland§7.");
								}
							}
						}
						
						SkyBlockUtils.generateIsland(l);

						Title t = new Title("", "§dSaving Data...");
						t.send(p);
						p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
						
						new BukkitRunnable(){
							public void run(){
								Island is = new Island(islandid, l, new SimpleDateFormat("dd-MM-yyyy").format(new Date()), p.getUniqueId(), new ArrayList<UUID>(), false);
								is.saveAll();
								skyblock.getIslands().add(is);
								skyblock.setTotalIslands(islandid);
								skyblock.setLastLocation(l);
								
								sbp.setIsland(is, IslandRank.OWNER);

								Title t = new Title("§dIsland Ready!", "§7Teleporting to your §dIsland§7...");
								t.send(p);
								p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
								
								new BukkitRunnable(){
									public void run(){
										p.teleport(sbp.getHomeLocation());
										p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 5, 1);

										Title t = new Title("", "§7Teleporting to your §dIsland§7...", 0, 40, 20);
										t.send(p);
									}
								}.runTaskLater(Start.getInstance(), 10);
							}
						}.runTaskLater(Start.getInstance(), 20);
					}
				}.runTaskLater(Start.getInstance(), 20);
			}
		}.runTaskLater(Start.getInstance(), 5);
	}
}