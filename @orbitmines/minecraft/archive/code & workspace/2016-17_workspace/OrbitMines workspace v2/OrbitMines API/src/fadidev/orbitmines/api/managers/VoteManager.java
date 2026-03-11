package fadidev.orbitmines.api.managers;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.Database;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.StringInt;
import fadidev.orbitmines.api.utils.ConfigUtils;
import fadidev.orbitmines.api.utils.UUIDUtils;
import fadidev.orbitmines.api.utils.enums.Server;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VoteManager {
	
	private OrbitMinesAPI api;
	
	public VoteManager(){
		api = OrbitMinesAPI.getApi();
	}
	
	private void registerNotOnline(UUID uuid){
		boolean hasVoteWaiting = false;
		
		List<StringInt> list = api.getPendingVoters();
		
		if(list.size() != 0){
			for(StringInt si : list){
				if(!si.getString().equals(uuid.toString()))
				    continue;

                si.setInteger(si.getInteger() + 1);
                hasVoteWaiting = true;
			}
		}
		
		if(!hasVoteWaiting)
			list.add(new StringInt(uuid.toString(), 1));
		
		api.getConfigManager().get("voters").set("VoteRewardsNotGiven", ConfigUtils.parseStringIntList(list));
		api.getConfigManager().save("voters");
	}
	
	public void check(OMPlayer omp){
		UUID uuid = omp.getUUID();
		
		List<StringInt> list = api.getPendingVoters();
		List<StringInt> newList = new ArrayList<>(list);
		
		if(list.size() != 0){
			for(StringInt si : list){
				if(!si.getString().equals(uuid.toString()))
					continue;

                newList.remove(si);

                for(int i = 1; i <= si.getInteger(); i++){
                    omp.registerVote();
                }

                api.getConfigManager().get("voters").set("VoteRewardsNotGiven", ConfigUtils.parseStringIntList(list));
                api.getConfigManager().save("voters");
			}
		}
		api.setPendingVoters(newList);
	}
	
	public void registerVote(String playerName){
		OMPlayer omp = null;
		
		for(OMPlayer omplayer : api.getOMPlayers()){
			if(omplayer.getPlayer().getName().equalsIgnoreCase(playerName))
				omp = omplayer;
		}
		
		if(omp != null){
			omp.registerVote();
			
			if(api.getServerType() != Server.HUB)
			    return;

            UUID uuid = omp.getUUID();

            if(Database.get().containsPath("Votes", "votes", "uuid", uuid.toString()))
                Database.get().update("Votes", "votes", "" + (Database.get().getInt("Votes", "votes", "uuid", uuid.toString()) + 1), "uuid", uuid.toString());
            else
                Database.get().insert("Votes", "uuid`, `votes", uuid.toString() + "', '" + 1);
		}
		else{
			UUID uuid = UUIDUtils.getUUID(playerName);
			
			if(uuid == null)
			    return;

            registerNotOnline(uuid);
				
            if(api.getServerType() == Server.HUB){
                if(Database.get().containsPath("Votes", "votes", "uuid", uuid.toString()))
                    Database.get().update("Votes", "votes", "" + (Database.get().getInt("Votes", "votes", "uuid", uuid.toString()) + 1), "uuid", uuid.toString());
                else
                    Database.get().insert("Votes", "uuid`, `votes", uuid.toString() + "', '" + 1);
            }
		}
	}
}
