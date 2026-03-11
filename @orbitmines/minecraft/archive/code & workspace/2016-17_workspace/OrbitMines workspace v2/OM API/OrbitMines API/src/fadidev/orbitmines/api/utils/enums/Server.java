package fadidev.orbitmines.api.utils.enums;

import fadidev.orbitmines.api.OrbitMinesAPI;
import org.bukkit.Material;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Server {

	KITPVP(100, "§c§lKitPvP", "§c", Material.IRON_SWORD),
	PRISON(100, "§4§lPrison", "§4", Material.DIAMOND_PICKAXE),
	CREATIVE(100, "§d§lCreative", "§d", Material.WOOD_AXE),
	HUB(1000, "§3§lHub", "§3", Material.WATCH),
	SURVIVAL(100, "§a§lSurvival", "§a", Material.STONE_HOE),
	SKYBLOCK(100, "§5§lSkyBlock", "§5", Material.FISHING_ROD),
	MINIGAMES(1000, "§f§lMiniGames", "§f", Material.BOW),
	FOG(100, "§e§lFoG", "§e", Material.EMPTY_MAP);
	
	private OrbitMinesAPI api;
	private int maxPlayers;
	private String name;
	private String color;
	private Material material;
	
	Server(int maxPlayers, String name, String color, Material material){
		this.api = OrbitMinesAPI.getApi();
		this.maxPlayers = maxPlayers;
		this.name = name;
		this.color = color;
		this.material = material;
	}
	
	public String statusString(){
		if(isOnline())
			return "§a§lOnline";
		return "§4§lOffline";
	}
	
	public boolean isOnline(){
		if(api.getOnlinePlayers().containsKey(this))
			return api.getOnlinePlayers(this) != -1;
		return false;
	}
	
	public int getMaxPlayers(){
		return maxPlayers;
	}
	
	public void updateNPC(Entity entity){
		if(!isOnline())
			entity.setCustomName("§4§lOffline");
		else
			entity.setCustomName("§7Players in " + getName() + "§7: §6§l" + api.getOnlinePlayers(this) + "§7/§6§l" + getMaxPlayers());
		entity.setCustomNameVisible(true);
	}
	
	public List<String> getVoteRewardMessages(){
		switch(this){
			case HUB:
				return Collections.singletonList("§e§l1 OrbitMines Token");
			case KITPVP:
				return Collections.singletonList("§6§l500 Coins");
			case MINIGAMES:
				return Collections.singletonList("§f§l50 Coins");
			case PRISON:
				return Collections.singletonList("§6§l1 Vote Pickaxe");
			case SKYBLOCK:
				return Arrays.asList("§8§l32 Cobblestone", "§7§l1 Iron Ingot", "§0§l4 Coal");
			case SURVIVAL:
				return Arrays.asList("§2§l25$", "§8§l100 Claimblocks");
			case FOG:
				return Collections.singletonList("§7§l750 Silver");
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
