package om.api.managers;

import java.util.List;
import java.util.UUID;

import om.api.API;
import om.api.handlers.Database;
import om.api.handlers.StringInt;
import om.api.handlers.players.OMPlayer;
import om.api.utils.ConfigUtils;
import om.api.utils.UUIDUtils;
import om.api.utils.enums.Config;
import om.api.utils.enums.Server;

public class VoteManager {
	
	private API api;
	
	public VoteManager(){
		api = API.getInstance();
	}
	
	private void registerNotOnline(UUID uuid){
		boolean hasVoteWaiting = false;
		
		List<StringInt> list = api.getPendingVoters();
		
		if(list.size() != 0){
			for(StringInt si : list){
				if(si.getString().equals(uuid.toString())){
					si.setInteger(si.getInteger());
				}
			}
		}
		
		if(!hasVoteWaiting){
			list.add(new StringInt(uuid.toString(), 1));
		}
		
		api.getConfigManager().get(Config.VOTERS).set("VoteRewardsNotGiven", ConfigUtils.parseStringIntList(list));
		api.getConfigManager().save(Config.VOTERS);
	}
	
	public void check(OMPlayer omp){
		UUID uuid = omp.getUUID();
		
		List<StringInt> list = api.getPendingVoters();
		
		if(list.size() != 0){
			for(StringInt si : list){
				if(si.getString().equals(uuid.toString())){
					list.remove(si);
					
					for(int i = 1; i <= si.getInteger(); i++){
						omp.vote();
					}

					api.getConfigManager().get(Config.VOTERS).set("VoteRewardsNotGiven", ConfigUtils.parseStringIntList(list));
					api.getConfigManager().save(Config.VOTERS);
				}
			}
		}
	}
	
	public void registerVote(String playername){
		OMPlayer omp = null;
		
		for(OMPlayer omplayer : api.getOMPlayers()){
			if(omplayer.getPlayer().getName().equalsIgnoreCase(playername)){
				omp = omplayer;
			}
		}
		
		if(omp != null){
			omp.vote();
			
			if(api.isServer(Server.HUB)){
				UUID uuid = omp.getUUID();
				
				if(Database.get().containsPath("Votes", "votes", "uuid", uuid.toString())){
					Database.get().update("Votes", "votes", "" + (Database.get().getInt("Votes", "votes", "uuid", uuid.toString()) + 1), "uuid", uuid.toString());
				}
				else{
					Database.get().insert("Votes", "uuid`, `votes", uuid.toString() + "', '" + 1);
				}
			}
		}
		else{
			UUID uuid = UUIDUtils.getUUID(playername);
			
			if(uuid != null){
				registerNotOnline(uuid);
				
				if(api.isServer(Server.HUB)){
					if(Database.get().containsPath("Votes", "votes", "uuid", uuid.toString())){
						Database.get().update("Votes", "votes", "" + (Database.get().getInt("Votes", "votes", "uuid", uuid.toString()) + 1), "uuid", uuid.toString());
					}
					else{
						Database.get().insert("Votes", "uuid`, `votes", uuid.toString() + "', '" + 1);
					}
				}
			}
		}
	}
}
