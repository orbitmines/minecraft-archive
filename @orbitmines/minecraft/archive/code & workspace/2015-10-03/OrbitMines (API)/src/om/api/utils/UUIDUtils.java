package om.api.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import om.api.handlers.uuid.NameFetcher;
import om.api.handlers.uuid.UUIDFetcher;

public class UUIDUtils {
	
	private static Map<String, UUID> playerUUIDs = new HashMap<String, UUID>();
	private static Map<UUID, String> playerNames = new HashMap<UUID, String>();

	public static UUID getUUID(String playername){
		if(!playerUUIDs.containsKey(playername)){
			UUIDFetcher uuidf = new UUIDFetcher(Arrays.asList(playername));
			try{
				UUID uuid = uuidf.call().get(playername);
				if(uuid != null) playerUUIDs.put(playername, uuid);
				
				return uuid;
			}catch(Exception ex){
				return null;
			}
		}
		
		return playerUUIDs.get(playername);
	}
	
	public static String getName(UUID uuid){
		if(!playerNames.containsKey(uuid)){
			NameFetcher namef = new NameFetcher(uuid);
			try{
				String name = namef.call().get(uuid).get(namef.call().get(uuid).size() -1);
				String[] nameparts = name.split(" ");
				
				playerNames.put(uuid, nameparts[0]);
				return nameparts[0];
			}catch(Exception ex){
				return null;
			}
		}

		return playerNames.get(uuid);
	}
	
	public static String getNameDate(UUID uuid){
		NameFetcher namef = new NameFetcher(uuid);
		try{
			String name = namef.call().get(uuid).get(namef.call().get(uuid).size() -1);
			String[] nameparts = name.split(" ", 1);
			return nameparts[1];
		}catch(Exception ex){
			return null;
		}
	}
	
	public static HashMap<String, String> getNames(UUID uuid){
		NameFetcher namef = new NameFetcher(uuid);
		try{
			HashMap<String, String> names = new HashMap<String, String>();
			for(String name : namef.call().get(uuid)){
				String[] nameparts = name.split(" ", 1);
				if(nameparts.length > 1){
					names.put(nameparts[0], nameparts[1]);
				}
				else{
					names.put(nameparts[0], null);
				}
			}
			
			return names;
		}catch(Exception ex){
			return null;
		}
	}
	
	public static Map<UUID, String> getPlayerNames() {
		return playerNames;
	}
	public static Map<String, UUID> getPlayerUUIDs() {
		return playerUUIDs;
	}
}
