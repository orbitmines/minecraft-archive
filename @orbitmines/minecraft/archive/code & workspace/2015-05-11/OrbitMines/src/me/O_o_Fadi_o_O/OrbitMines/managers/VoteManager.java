package me.O_o_Fadi_o_O.OrbitMines.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import me.O_o_Fadi_o_O.OrbitMines.utils.Database;
import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.HubServer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.ServerStorage;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;

import org.bukkit.Bukkit;
import org.bukkit.SkullType;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;

public class VoteManager {
	
	private void registerNotOnline(UUID uuid){
		boolean hasVoteWaiting = false;
		
		List<String> list = new ArrayList<String>();
		
		for(String s : ServerStorage.pendingvoters){
			list.add(s);
		}
		
		if(list.size() != 0){
			try{
				for(String s : list){
					if(s.startsWith(uuid.toString())){
						list.remove(s);
						hasVoteWaiting = true;
						String[] sList = s.split("\\|");
						int amount = Integer.parseInt(sList[1]) +1;
						
						list.add(uuid.toString() + "|" + amount + "|");
					}
				}
			}catch(ArrayIndexOutOfBoundsException ex){}
		}
		
		if(hasVoteWaiting == false){
			list.add(uuid.toString() + "|1|");
		}
		
		ServerStorage.pendingvoters.clear();
		ServerStorage.pendingvoters.addAll(list);
		
		ConfigManager.config.set("VoteRewardsNotGiven", list);
		ConfigManager.saveConfig();
	}
	
	public void check(OMPlayer omp){
		UUID uuid = omp.getUUID();
		
		List<String> list = new ArrayList<String>();
		
		for(String s : ServerStorage.pendingvoters){
			list.add(s);
		}
		
		if(list.size() != 0){
			try{
				for(String s : list){
					if(s.startsWith(uuid.toString())){
						list.remove(s);
						String[] sList = s.split("\\|");
						int amount = Integer.parseInt(sList[1]);
						
						for(int i = 1; i <= amount; i++){
							omp.vote();
						}
						
						ServerStorage.pendingvoters = list;
						
						ConfigManager.config.set("VoteRewardsNotGiven", list);
						ConfigManager.saveConfig();
					}
				}
			}catch(ArrayIndexOutOfBoundsException ex){}
		}
	}
	
	public void registerVote(String playername){
		OMPlayer omp = null;
		
		for(OMPlayer omplayer : OMPlayer.getOMPlayers()){
			if(omplayer.getPlayer().getName().equalsIgnoreCase(playername)){
				omp = omplayer;
			}
		}
		
		if(omp != null){
			omp.vote();
			
			UUID uuid = omp.getUUID();
			
			if(ServerData.isServer(Server.HUB)){
				if(Database.get().containsPath("Votes", "votes", "uuid", uuid.toString())){
					Database.get().update("Votes", "votes", "" + (Database.get().getInt("Votes", "votes", "uuid", uuid.toString()) + 1), "uuid", uuid.toString());
				}
				else{
					Database.get().insert("Votes", "uuid`, `votes", uuid.toString() + "', '" + 1);
				}
			}
		}
		else{
			UUID uuid = Utils.getUUID(playername);
			
			if(uuid != null){
				registerNotOnline(uuid);
				
				if(ServerData.isServer(Server.HUB)){
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
	
	public void updateTop(){
		if(ServerData.isServer(Server.HUB)){
			int votes1 = 0;
			int votes2 = 0;
			int votes3 = 0;
			
			String player1 = null;
			String player2 = null;
			String player3 = null;
			
			for(String player : ServerStorage.voters.keySet()){
				int votes = ServerStorage.voters.get(player);
				if(votes >= votes1){
					votes3 = votes2;
					votes2 = votes1;
					votes1 = votes;
					
					player3 = player2;
					player2 = player1;
					player1 = player;
					
				}
				else if(votes >= votes2){
					votes3 = votes2;
					votes2 = votes;
					
					player3 = player2;
					player2 = player;
				}
				else if(votes >= votes3){
					votes3 = votes;
					
					player3 = player;
				}else{}
			}
			
			HubServer hub = ServerData.getHub();
			World hubworld = hub.getHubWorld();
			
			Skull topvoter1 = (Skull) hubworld.getBlockAt(hub.getTopVoter1()).getState();
			Skull topvoter2 = (Skull) hubworld.getBlockAt(hub.getTopVoter2()).getState();
			Skull topvoter3 = (Skull) hubworld.getBlockAt(hub.getTopVoter3()).getState();
			
			if(player1 == null){
				topvoter1.setOwner(null);
				topvoter1.setSkullType(SkullType.SKELETON);	
			}
			else{
				topvoter1.setSkullType(SkullType.PLAYER);
				topvoter1.setOwner(player1);
			}
			if(player2 == null){
				topvoter2.setOwner(null);
				topvoter2.setSkullType(SkullType.SKELETON);	
			}
			else{
				topvoter2.setSkullType(SkullType.PLAYER);
				topvoter2.setOwner(player2);
			}
			if(player3 == null){
				topvoter3.setOwner(null);
				topvoter3.setSkullType(SkullType.SKELETON);	
			}
			else{
				topvoter3.setSkullType(SkullType.PLAYER);
				topvoter3.setOwner(player3);
			}
			
			topvoter1.setRotation(BlockFace.WEST);
			topvoter2.setRotation(BlockFace.WEST);
			topvoter3.setRotation(BlockFace.WEST);
			
			topvoter1.update();
			topvoter2.update();
			topvoter3.update();
			
			for(Player p : Bukkit.getOnlinePlayers()){
				if(p.getWorld().getName().equals(hubworld.getName())){
					if(p.getLocation().distance(hub.getTopVoterSign1()) <= 20){
						String[] sign1 = new String[4];
						sign1[0] = "§lTop Voter (1)";
						sign1[1] = "";
						sign1[2] = player1;
						
						if(player1.equals("")){
							sign1[3] = "";
						}
						else{
							if(votes1 == 1){
								sign1[3] = "§o" + votes1 + " Vote";
							}
							else{
								sign1[3] = "§o" + votes1 + " Votes";
							}
							
							p.sendSignChange(hub.getTopVoterSign1(), sign1);
						}
						
						String[] sign2 = new String[4];
						sign2[0] = "§lTop Voter (2)";
						sign2[1] = "";
						sign2[2] = player2;
						if(player2.equals("")){
							sign2[3] = "";
						}
						else{
							if(votes2 == 1){
								sign2[3] = "§o" + votes2 + " Vote";
							}
							else{
								sign2[3] = "§o" + votes2 + " Votes";
							}
						}
						
						p.sendSignChange(hub.getTopVoterSign2(), sign2);
						
						String[] sign3 = new String[4];
						sign3[0] = "§lTop Voter (3)";
						sign3[1] = "";
						sign3[2] = player3;
						if(player3.equals("")){
							sign3[3] = "";
						}
						else{
							if(votes3 == 1){
								sign3[3] = "§o" + votes3 + " Vote";
							}
							else{
								sign3[3] = "§o" + votes3 + " Votes";
							}
						}
						
						p.sendSignChange(hub.getTopVoterSign3(), sign3);
					}
				}
			}
		}
	}
}
