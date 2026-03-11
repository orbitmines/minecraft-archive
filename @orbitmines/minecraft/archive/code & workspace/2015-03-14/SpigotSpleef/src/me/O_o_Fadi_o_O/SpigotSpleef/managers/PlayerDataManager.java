package me.O_o_Fadi_o_O.SpigotSpleef.managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import me.O_o_Fadi_o_O.SpigotSpleef.utils.Kit;
import me.O_o_Fadi_o_O.SpigotSpleef.utils.SpleefPlayer;

import org.bukkit.entity.Player;

public class PlayerDataManager {

	public static void loadPlayerData(Player p){
		checkNewUser(p);
		
		UUID uuid = p.getUniqueId();
		SpleefPlayer sp = new SpleefPlayer(p, getKits(uuid), null, null, getTokens(uuid), getKills(uuid), getWins(uuid), getLoses(uuid), false);
		StorageManager.spleefplayer.put(p, sp);
	}
	
	public static void checkNewUser(Player p){
		UUID uuid = p.getUniqueId();
		if(!ConfigManager.playerdata.contains("players." + uuid.toString())){
			String unlockedkits = "";
			for(String kit : StorageManager.newplayerunlockedkits){
				if(unlockedkits.equals("")){
					unlockedkits = kit;
				}
				else{
					unlockedkits = unlockedkits + " | " + kit;
				}
			}
			
			ConfigManager.playerdata.set("players." + uuid.toString() + ".Kits", unlockedkits);
			ConfigManager.savePlayerData();
			setKills(uuid, 0);
			setWins(uuid, 0);
			setLoses(uuid, 0);
			setTokens(uuid, StorageManager.newplayertokens);
		}
	}
	
	public static void setKits(UUID uuid, List<Kit> kits){
		String unlockedkits = "";
		for(Kit kit : kits){
			if(unlockedkits.equals("")){
				unlockedkits = "" + kit.getKitID();
			}
			else{
				unlockedkits = unlockedkits + " | " + kit.getKitID();
			}
		}
		ConfigManager.playerdata.set("players." + uuid.toString() + ".Kits", unlockedkits);
		ConfigManager.savePlayerData();
	}
	public static List<Kit> getKits(UUID uuid){
		List<Kit> kits = new ArrayList<Kit>();
		String[] kitids = ConfigManager.playerdata.getString("players." + uuid.toString() + ".Kits").replace(" ", "").split("\\|");
		List<String> kitidslist = Arrays.asList(kitids);
		for(Kit kit : StorageManager.kits){
			if(kitidslist.contains("" + kit.getKitID())){
				kits.add(kit);
			}
		}
		return kits;
	}
	
	public static void setKills(UUID uuid, int kills){
		ConfigManager.playerdata.set("players." + uuid.toString() + ".Kills", kills);
		ConfigManager.savePlayerData();
	}
	public static int getKills(UUID uuid){
		return ConfigManager.playerdata.getInt("players." + uuid.toString() + ".Kills");
	}
	
	public static void setWins(UUID uuid, int wins){
		ConfigManager.playerdata.set("players." + uuid.toString() + ".Wins", wins);
		ConfigManager.savePlayerData();
	}
	public static int getWins(UUID uuid){
		return ConfigManager.playerdata.getInt("players." + uuid.toString() + ".Wins");
	}
	
	public static void setLoses(UUID uuid, int loses){
		ConfigManager.playerdata.set("players." + uuid.toString() + ".Loses", loses);
		ConfigManager.savePlayerData();
	}
	public static int getLoses(UUID uuid){
		return ConfigManager.playerdata.getInt("players." + uuid.toString() + ".Loses");
	}
	
	public static void setTokens(UUID uuid, int tokens){
		ConfigManager.playerdata.set("players." + uuid.toString() + ".Tokens", tokens);
		ConfigManager.savePlayerData();
	}
	public static int getTokens(UUID uuid){
		return ConfigManager.playerdata.getInt("players." + uuid.toString() + ".Tokens");
	}
}
