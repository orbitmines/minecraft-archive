package fadidev.orbitmines.survival.handlers;

import fadidev.orbitmines.api.handlers.Cooldowns;
import fadidev.orbitmines.api.utils.ConfigUtils;
import fadidev.orbitmines.api.utils.PlayerUtils;
import fadidev.orbitmines.api.utils.UUIDUtils;
import fadidev.orbitmines.survival.OrbitMinesSurvival;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Warp {

	private static OrbitMinesSurvival survival;
	private int warpId;
	private UUID uuid;
	private String name;
	private Location location;
	private boolean enabled;
	private ItemStack item;
	
	public Warp(int warpId, UUID uuid, String name, Location location, boolean enabled, Material material, short durability){
		this.warpId = warpId;
		this.uuid = uuid;
		this.name = name;
		this.location = location;
		this.enabled = enabled;
		
		updateItemStack(material, durability);
	}

	public int getWarpId() {
		return warpId;
	}

	public UUID getUUID() {
		return uuid;
	}
	
	public String getUUIDName() {
		Player p = PlayerUtils.getPlayer(this.uuid);
		
		if(p != null)
			return p.getName();
		return UUIDUtils.getName(this.uuid);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}

	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public ItemStack getItemStack() {
		return item;
	}
	
	public void updateItemStack(Material material, int durability){
		ItemStack item = new ItemStack(material, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§7§lWarp: §3§l" + name);
		List<String> lore = new ArrayList<>();
		lore.add(" §7Owner: §3" + getUUIDName());
		lore.add(" §7XYZ: §3" + location.getBlockX() + "§7 / §3" + location.getBlockY() + "§7 / §3" + location.getBlockZ());
		meta.setLore(lore);
		item.setItemMeta(meta);
		item.setDurability((short) durability);
		
		this.item = item;
	}
	
	public void teleport(SurvivalPlayer omp){
		omp.resetCooldown(Cooldowns.TELEPORTING);
        omp.setWarpTeleporting(true);
        omp.setWarpingTo(this);

		omp.getPlayer().sendMessage("§7" + SurvivalMessages.WORD_TELEPORTING_TO.get(omp) + " §3" + this.name + "§7...");
	}
	
	public void teleportInstantly(SurvivalPlayer omp){
		omp.setBackLocation(omp.getPlayer().getLocation());
		omp.getPlayer().teleport(this.location);
	}
	
	public static List<Warp> readFromConfig(){
		survival = OrbitMinesSurvival.getSurvival();

		List<Warp> warps = new ArrayList<>();
		
		for(String warpstring : survival.getConfigManager().get("warps").getStringList("warps")){
			String[] warpparts = warpstring.split("\\;");
			
			warps.add(new Warp(Integer.parseInt(warpparts[0]), UUID.fromString(warpparts[6]), warpparts[1], ConfigUtils.parseLocation(warpparts[2]), Boolean.parseBoolean(warpparts[5]), Material.valueOf(warpparts[3]), Short.parseShort(warpparts[4])));
		}
		return warps;
	}
	
	public static void saveToConfig(){
		List<String> warps = new ArrayList<>();
		
		for(Warp warp : survival.getWarps()){
			warps.add(warp.getWarpId() + ";" + warp.getName() + ";" + ConfigUtils.parseString(warp.getLocation()) + ";" + warp.getItemStack().getType().toString() + ";" + warp.getItemStack().getDurability() + ";" + warp.isEnabled() + ";" + warp.getUUID().toString());
		}
		
		survival.getConfigManager().get("warps").set("warps", warps);
		survival.getConfigManager().save("warps");
	}

	public static List<Warp> getWarps(Player player){
		List<Warp> warps = new ArrayList<>();
		
		for(Warp warp : survival.getWarps()){
			if(warp.getUUID().toString().equals(player.getUniqueId().toString()))
				warps.add(warp);
		}
		return warps;
	}
	
	public static Warp getWarp(int warpId){
		for(Warp warp : survival.getWarps()){
			if(warp.getWarpId() == warpId)
				return warp;
		}
		return null;
	}

	public static Warp getWarp(String name){
		for(Warp warp : survival.getWarps()){
			if(warp.getName().equalsIgnoreCase(name))
				return warp;
		}
		return null;
	}
}
