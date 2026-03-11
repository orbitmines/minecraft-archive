package om.api.managers;

import om.api.API;
import om.api.handlers.players.OMPlayer;
import om.api.utils.enums.ranks.StaffRank;
import om.api.utils.enums.ranks.VIPRank;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public abstract class ScoreBoardManager {

	private API api;
	private int titleIndex;
	private String title;
	
	public ScoreBoardManager(){
		api = API.getInstance();
		titleIndex = 0;
		nextTitle();
	}
	
	public abstract void requestUpdate(Player p);
	public abstract void nextTitle();
	
	public int getTitleIndex(){
		return titleIndex;
	}
	
	public void setTitleIndex(int titleIndex){
		this.titleIndex = titleIndex;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@SuppressWarnings("deprecation")
	public void updateRankTeams(Scoreboard b){
		Team IronVIP = b.registerNewTeam("Iron" + api.server().toString());
		IronVIP.setPrefix(VIPRank.Iron_VIP.getScoreboardPrefix());
		Team GoldVIP = b.registerNewTeam("Gold" + api.server().toString());
		GoldVIP.setPrefix(VIPRank.Gold_VIP.getScoreboardPrefix());
		Team DiamondVIP = b.registerNewTeam("Diamond" + api.server().toString());
		DiamondVIP.setPrefix(VIPRank.Diamond_VIP.getScoreboardPrefix());
		Team EmeraldVIP = b.registerNewTeam("Emerald" + api.server().toString());
		EmeraldVIP.setPrefix(VIPRank.Emerald_VIP.getScoreboardPrefix());
		Team Builder = b.registerNewTeam("Builder" + api.server().toString());
		Builder.setPrefix(StaffRank.Builder.getScoreboardPrefix());
		Team Moderator = b.registerNewTeam("Mod" + api.server().toString());
		Moderator.setPrefix(StaffRank.Moderator.getScoreboardPrefix());
		Team Owner = b.registerNewTeam("Owner" + api.server().toString());
		Owner.setPrefix(StaffRank.Owner.getScoreboardPrefix());
		
		for(Player player : Bukkit.getOnlinePlayers()){
			OMPlayer omplayer = OMPlayer.getOMPlayer(player);
			
			StaffRank staff = omplayer.getStaffRank();
			VIPRank vip = omplayer.getVIPRank();
			
			if(staff == StaffRank.Owner){
				Owner.addPlayer(player);
			}
			else if(staff == StaffRank.Moderator){
				Moderator.addPlayer(player);
			}
			else if(staff == StaffRank.Builder){
				Builder.addPlayer(player);
			}
			else if(vip == VIPRank.Emerald_VIP){
				EmeraldVIP.addPlayer(player);
			}
			else if(vip == VIPRank.Diamond_VIP){
				DiamondVIP.addPlayer(player);
			}
			else if(vip == VIPRank.Gold_VIP){
				GoldVIP.addPlayer(player);
			}
			else if(vip == VIPRank.Iron_VIP){
				IronVIP.addPlayer(player);
			}
			else{}
		}
	}
}
