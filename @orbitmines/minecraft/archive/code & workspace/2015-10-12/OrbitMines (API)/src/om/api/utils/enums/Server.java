package om.api.utils.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import om.api.API;

import org.bukkit.Material;
import org.bukkit.entity.Entity;

public enum Server {

	KITPVP(100, "§c§lKitPvP", "§c", Material.IRON_SWORD), 
	PRISON(100, "§4§lPrison", "§4", Material.DIAMOND_PICKAXE), 
	CREATIVE(100, "§d§lCreative", "§d", Material.WOOD_AXE), 
	HUB(1000, "§3§lHub", "§3", Material.WATCH), 
	SURVIVAL(100, "§a§lSurvival", "§a", Material.STONE_HOE), 
	SKYBLOCK(100, "§5§lSkyBlock", "§5", Material.FISHING_ROD),
	MINIGAMES(1000, "§f§lMiniGames", "§f", Material.BOW), 
	FOG(100, "§e§lFoG", "§e", Material.EMPTY_MAP);
	
	private API api;
	private int maxplayers;
	private String name;
	private String color;
	private Material material;
	
	Server(int maxplayers, String name, String color, Material material){
		this.api = API.getInstance();
		this.maxplayers = maxplayers;
		this.name = name;
		this.color = color;
		this.material = material;
	}
	
	public String statusString(){
		if(isOnline()){
			return "§a§lOnline";
		}
		return "§4§lOffline";
	}
	
	public boolean isOnline(){
		if(api.getOnlinePlayers().containsKey(this)){
			return api.getOnlinePlayers(this) != -1;
		}
		return false;
	}
	
	public int getMaxPlayers(){
		return maxplayers;
	}
	
	public void updateNPC(Entity entity){
		if(!isOnline()){
			entity.setCustomName("§4§lOffline");
		}
		else{
			entity.setCustomName("§7Players in " + getName() + "§7: §6§l" + api.getOnlinePlayers(this) + "§7/§6§l" + getMaxPlayers());
		}
		entity.setCustomNameVisible(true);
	}
	
	public List<String> getVoteRewardMessages(){
		switch(this){
			case HUB:
				return Arrays.asList("§e§l1 OrbitMines Token");
			case KITPVP:
				return Arrays.asList("§6§l500 Coins");
			case MINIGAMES:
				return Arrays.asList("§f§l50 Coins");
			case PRISON:
				return Arrays.asList("§6§l1 Vote Pickaxe");
			case SKYBLOCK:
				return Arrays.asList("§8§l32 Cobblestone", "§7§l1 Iron Ingot", "§0§l4 Coal");
			case SURVIVAL:
				return Arrays.asList("§2§l25$", "§8§l100 Claimblocks");
			case FOG:
				return Arrays.asList("§7§l750 Silver");
			default:
				return new ArrayList<String>();
		}
	}
	
	public String getName(){
		return name;
	}
	
	public String getVersion(){
		return "1.8";
	}
	
	public String getColor(){
		return color;
	}
	
	public Material getMaterial(){
		return material;
	}
}
