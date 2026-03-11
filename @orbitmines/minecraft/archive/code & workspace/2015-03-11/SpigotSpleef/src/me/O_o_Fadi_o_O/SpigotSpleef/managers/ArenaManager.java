package me.O_o_Fadi_o_O.SpigotSpleef.managers;

import java.util.ArrayList;
import java.util.List;

import me.O_o_Fadi_o_O.SpigotSpleef.utils.Arena;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.ArenaSign;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.Kit;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.NPC;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.NPCType;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.SpleefPlayer;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.SpleefStatus;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ArenaManager {
	
	public static void loadArenas(){
		if(ConfigManager.arenas.contains("arenas")){
			for(String arenaid : ConfigManager.arenas.getConfigurationSection("arenas").getKeys(false)){
				if(Integer.parseInt(arenaid) >= StorageManager.lastarenaid){
					StorageManager.lastarenaid = Integer.parseInt(arenaid) +1;
				}
				
				Location lobbyspawn = getLocationFromString(ConfigManager.arenas.getString("arenas." + arenaid + ".Locations.Lobby"));
				
				Arena arena = new Arena(Integer.parseInt(arenaid), lobbyspawn, StorageManager.minplayers, StorageManager.maxplayers, null, new ArrayList<SpleefPlayer>(), new ArrayList<SpleefPlayer>(), SpleefStatus.WAITING, StorageManager.waittimeminutes, StorageManager.waittimeseconds);
				StorageManager.arenas.add(arena);
				
				for(String signid : ConfigManager.arenas.getConfigurationSection("arenas." + arenaid + ".Signs").getKeys(false)){
					ArenaSign sign = new ArenaSign(getLocationFromString(ConfigManager.arenas.getString("arenas." + arenaid + ".Signs." + signid)), arena);
					arena.addArenaSign(sign);
					StorageManager.signs.add(sign);
				}
			}
		}
		
		if(ConfigManager.arenas.contains("arena-selectors")){
			for(String arenaselector : ConfigManager.arenas.getStringList("arena-selectors")){
				String[] parts = arenaselector.split("\\;");
				EntityType type = EntityType.valueOf(parts[0]);
				String displayname = parts[1].replace("&", "§");
				Location location = getLocationFromString(parts[2]);
				NPC npc = new NPC(NPCType.ARENA_SELECTOR, parts[1], null, null, null);
				npc.newEntity(type, location, displayname);
				StorageManager.npcs.add(npc);
			}
		}
		if(ConfigManager.arenas.contains("kit-selectors")){
			for(String kitstring : ConfigManager.arenas.getStringList("kit-selectors")){
				String[] parts = kitstring.split("\\;");
				int kitid = Integer.parseInt(parts[0]);
				EntityType type = EntityType.valueOf(parts[1]);
				Kit kit = getKitFromID(kitid);
				String displayname = parts[2].replace("&", "§").replace("%kit-name%", kit.getName());
				Location location = getLocationFromString(parts[3]);
				NPC npc = new NPC(NPCType.KIT_SELECTOR, parts[4], null, null, kit);
				npc.newEntity(type, location, displayname);
				StorageManager.npcs.add(npc);
			}
		}
	}
	
	public static void enterSetupMode(Player p, int arenaid){
		p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
		if(Arena.getArenaFromID(arenaid) == null){
			Arena arena = new Arena(arenaid, null, StorageManager.minplayers, StorageManager.maxplayers, null, new ArrayList<SpleefPlayer>(), new ArrayList<SpleefPlayer>(), SpleefStatus.RESTARTING, -1, -1);
			StorageManager.arenas.add(arena);
			StorageManager.arenasetup.put(p, arena);
		}
		else{
			Arena arena = Arena.getArenaFromID(arenaid);
			StorageManager.arenasetup.put(p, arena);
		}
		p.sendMessage("");
		p.sendMessage("§6§lSpigot§7§lSpleef §7| Entered §6Arena Setup Mode§7!");
		p.sendMessage(" §6§lLeft Click§7 to open the Setup Menu.");
		p.sendMessage(" §6§lLeft Click a Sign§7 to make it connect to this Arena.");
	}
	
	public static void openSetupInventory(Player p, Arena arena){
		p.sendMessage("§6§lSpigot§7§lSpleef §7| §7Opening the §6Arena Setup Menu§7...");
		
		Inventory inv = Bukkit.createInventory(null, 27, "§0§lArena #" + arena.getArenaID());
	
		{
			ItemStack item = new ItemStack(Material.ENDER_PEARL, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§3§lSet Lobby Spawnpoint");
			List<String> lore = new ArrayList<String>();
			if(arena.getLobby() == null){
				lore.add(" §7Done: §4§l✘ ");
			}
			else{
				lore.add(" §7Done: §a§l✔ ");
			}
			meta.setLore(lore);
			item.setItemMeta(meta);
			inv.setItem(11, item);
		}
		{
			ItemStack item = new ItemStack(Material.SIGN, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§e§lSigns");
			List<String> lore = new ArrayList<String>();
			lore.add(" §7Amount: §a" + arena.getArenaSigns().size() + " ");
			meta.setLore(lore);
			item.setItemMeta(meta);
			inv.setItem(13, item);
		}
		{
			ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§2§lFinish Setup");
			List<String> lore = new ArrayList<String>();
			if(arena.getLobby() == null){
				lore.add(" §7Setup Complete: §4§l✘ ");
			}
			else{
				lore.add(" §7Setup Complete: §a§l✔ ");
			}
			meta.setLore(lore);
			item.setItemMeta(meta);
			item.setDurability((short) 13);
			inv.setItem(15, item);
		}
		
		p.openInventory(inv);
	}
	
	public static String getStringFromLocation(Location l){
		return l.getWorld().getName() + "|" + l.getX() + "|" + l.getY() + "|" + l.getZ() + "|" + l.getYaw() + "|" + l.getPitch();
	}
	
	public static Location getLocationFromString(String location){
		String[] l = location.split("\\|");
		return new Location(Bukkit.getWorld(l[0]), Double.parseDouble(l[1]), Double.parseDouble(l[2]), Double.parseDouble(l[3]), Float.parseFloat(l[4]), Float.parseFloat(l[5]));
	}
	
	public static Kit getKitFromID(int kitid){
		for(Kit kit : StorageManager.kits){
			if(kit.getKitID() == kitid){
				return kit;
			}
		}
		return null;
	}
	
	public static void saveKitSelectors(){
		List<String> npcs = new ArrayList<String>();
		for(NPC npc : StorageManager.npcs){
			if(npc.getNPCType() == NPCType.KIT_SELECTOR){
				npcs.add(npc.getEntity().getType().toString() + ";" + npc.getDisplayname() + ";" + getStringFromLocation(npc.getEntity().getLocation()));
			}
		}
		ConfigManager.arenas.set("kit-selectors", npcs);
		ConfigManager.saveArenas();
	}

	public static void saveArenaSelectors(){
		List<String> npcs = new ArrayList<String>();
		for(NPC npc : StorageManager.npcs){
			if(npc.getNPCType() == NPCType.ARENA_SELECTOR){
				npcs.add(npc.getEntity().getType().toString() + ";" + npc.getDisplayname() + ";" + getStringFromLocation(npc.getEntity().getLocation()));
			}
		}
		ConfigManager.arenas.set("arena-selectors", npcs);
		ConfigManager.saveArenas();
	}
	

	public static void setLobbySpawnpoint(Player p){
		p.closeInventory();
		p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
		Arena arena = StorageManager.arenasetup.get(p);
		arena.setLobby(p.getLocation());
		
		p.sendMessage("§6§lSpigot§7§lSpleef §7| §7Set Arena §6#" + arena.getArenaID() + "§7's lobby spawnpoint!");
	}
	
	public static void setArenaSign(Player p, Block b){
		if(!ArenaSign.isSignArenaSign(b.getLocation())){
			Arena arena = StorageManager.arenasetup.get(p);
			ArenaSign sign = new ArenaSign(b.getLocation(), arena);
			arena.addArenaSign(sign);

			p.sendMessage("§6§lSpigot§7§lSpleef §7| This sign is now an §6Arena Sign§7!");
		}
		else{
			p.sendMessage("§6§lSpigot§7§lSpleef §7| §7This sign is already an §6Arena Sign§7!");
		}
	}
	
	public static void finishSetup(Player p){
		p.closeInventory();
		p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
		Arena arena = StorageManager.arenasetup.get(p);
		saveArena(arena);
		if(arena.isStatus(SpleefStatus.RESTARTING)){
			arena.toWaiting();
		}
		
		p.sendMessage("");
		p.sendMessage("§6§lSpigot§7§lSpleef §7| §7Finished §6Arena Setup§7! (§6#" + arena.getArenaID() + "§7)");
		StorageManager.arenasetup.remove(p);
	}
	
	public static void saveArena(Arena arena){
		int arenaid = arena.getArenaID();
		ConfigManager.arenas.set("arenas." + arenaid + ".Locations.Lobby", getStringFromLocation(arena.getLobby()));
		int index = 1;
		for(ArenaSign sign : arena.getArenaSigns()){
			ConfigManager.arenas.set("arenas." + arenaid + ".Signs." + index, getStringFromLocation(sign.getLocation()));
			index++;
		}
		ConfigManager.saveArenas();
	}
}
