package fadidev.orbitmines.api.utils;

import fadidev.orbitmines.api.handlers.uuid.NameFetcher;
import fadidev.orbitmines.api.handlers.uuid.UUIDFetcher;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UUIDUtils {
	
	public static Map<String, UUID> playerUUIDs = new HashMap<>();
	public static Map<UUID, String> playerNames = new HashMap<>();

	public static UUID getUUID(String playername){
		if(!playerUUIDs.containsKey(playername)){
			UUIDFetcher uuidFetcher = new UUIDFetcher(Arrays.asList(playername));
			try{
				UUID uuid = uuidFetcher.call().get(playername);
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
			NameFetcher nameFetcher = new NameFetcher(uuid);
			try{
				String name = nameFetcher.call().get(uuid).get(nameFetcher.call().get(uuid).size() -1);
				String[] nameParts = name.split(" ");
				
				playerNames.put(uuid, nameParts[0]);
				return nameParts[0];
			}catch(Exception ex){
				return null;
			}
		}

		return playerNames.get(uuid);
	}
	
	public static String getNameDate(UUID uuid){
		NameFetcher nameFetcher = new NameFetcher(uuid);
		try{
			String name = nameFetcher.call().get(uuid).get(nameFetcher.call().get(uuid).size() -1);
			String[] nameParts = name.split(" ", 1);
			return nameParts[1];
		}catch(Exception ex){
			return null;
		}
	}
	
	public static HashMap<String, String> getNames(UUID uuid){
		NameFetcher nameFetcher = new NameFetcher(uuid);
		try{
			HashMap<String, String> names = new HashMap<>();
			for(String name : nameFetcher.call().get(uuid)){
				String[] nameParts = name.split(" ", 1);
				if(nameParts.length > 1){
					names.put(nameParts[0], nameParts[1]);
				}
				else{
					names.put(nameParts[0], null);
				}
			}
			
			return names;
		}catch(Exception ex){
			return null;
		}
	}
}
