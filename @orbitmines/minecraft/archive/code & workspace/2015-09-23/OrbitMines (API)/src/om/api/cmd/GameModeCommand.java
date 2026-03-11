package om.api.cmd;

import om.api.handlers.Command;
import om.api.handlers.players.OMPlayer;
import om.api.utils.PlayerUtils;
import om.api.utils.enums.ranks.StaffRank;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class GameModeCommand extends Command {

	String[] alias = { "/gamemode", "/gm" };
	
	@Override
	public String[] getCMDs() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		Player p = omp.getPlayer();
		
		if(omp.hasPerms(StaffRank.Owner)){
			if(a.length == 2){
    			if(PlayerUtils.inGameMode(GameMode.SURVIVAL, a)){
    				p.setGameMode(GameMode.SURVIVAL);
    				p.sendMessage("§7Set your §6GameMode§7 to §a§lSurvival§7!");
    			}
    			else if(PlayerUtils.inGameMode(GameMode.CREATIVE, a)){
    				p.setGameMode(GameMode.CREATIVE);
    				p.sendMessage("§7Set your §6GameMode§7 to §d§lCreative§7!");
    			}
    			else if(PlayerUtils.inGameMode(GameMode.ADVENTURE, a)){
    				p.setGameMode(GameMode.ADVENTURE);
    				p.sendMessage("§7Set your §6GameMode§7 to §2§lAdventure§7!");
    			}
    			else if(PlayerUtils.inGameMode(GameMode.SPECTATOR, a)){
    				p.setGameMode(GameMode.SPECTATOR);
    				p.sendMessage("§7Set your §6GameMode§7 to §e§lSpectate§7!");
    			}
    			else{
    				p.sendMessage("§7Invalid Usage. (§6" + a[0] + " s|c|a|spec§7)");
    			}
	    	}
	    	else if(a.length == 3){
	    		Player p2 = PlayerUtils.getPlayer(a[2]);
	    		
	    		if(p2 != null){
	    			if(p2 == p){
		    			if(PlayerUtils.inGameMode(GameMode.SURVIVAL, a)){
		    				p.setGameMode(GameMode.SURVIVAL);
		    				p.sendMessage("§7Set your §6GameMode§7 to §a§lSurvival§7!");
		    			}
		    			else if(PlayerUtils.inGameMode(GameMode.CREATIVE, a)){
		    				p.setGameMode(GameMode.CREATIVE);
		    				p.sendMessage("§7Set your §6GameMode§7 to §d§lCreative§7!");
		    			}
		    			else if(PlayerUtils.inGameMode(GameMode.ADVENTURE, a)){
		    				p.setGameMode(GameMode.ADVENTURE);
		    				p.sendMessage("§7Set your §6GameMode§7 to §2§lAdventure§7!");
		    			}
		    			else if(PlayerUtils.inGameMode(GameMode.SPECTATOR, a)){
		    				p.setGameMode(GameMode.SPECTATOR);
		    				p.sendMessage("§7Set your §6GameMode§7 to §e§lSpectate§7!");
		    			}
		    			else{
		    				p.sendMessage("§7Invalid Usage. (§6" + a[0] + " s|c|a|spec <player>§7)");
		    			}
	    			}
	    			else{
	    				OMPlayer omp2 = OMPlayer.getOMPlayer(p2);
		    			if(PlayerUtils.inGameMode(GameMode.SURVIVAL, a)){
		    				p2.setGameMode(GameMode.SURVIVAL);
		    				p.sendMessage("§7Set " + omp2.getName() + "'s §6GameMode§7 to §a§lSurvival§7!");
		    				p2.sendMessage("§7" + omp.getName() + " §7set your §6GameMode§7 to §a§lSurvival§7!");
		    			}
		    			else if(PlayerUtils.inGameMode(GameMode.CREATIVE, a)){
		    				p2.setGameMode(GameMode.CREATIVE);
		    				p.sendMessage("§7Set " + omp2.getName() + "'s §6GameMode§7 to §d§lCreative§7!");
		    				p2.sendMessage("§7" + omp.getName() + " §7set your §6GameMode§7 to §d§lCreative§7!");
		    			}
		    			else if(PlayerUtils.inGameMode(GameMode.ADVENTURE, a)){
		    				p2.setGameMode(GameMode.ADVENTURE);
		    				p.sendMessage("§7Set " + omp2.getName() + "'s §6GameMode§7 to §2§lAdventure§7!");
		    				p2.sendMessage("§7" + omp.getName() + " §7set your §6GameMode§7 to §2§lAdventure§7!");
		    			}
		    			else if(PlayerUtils.inGameMode(GameMode.SPECTATOR, a)){
		    				p2.setGameMode(GameMode.SPECTATOR);
		    				p.sendMessage("§7Set " + omp2.getName() + "'s §6GameMode§7 to §e§lSpectate§7!");
		    				p2.sendMessage("§7" + omp.getName() + " §7set your §6GameMode§7 to §e§lSpectate§7!");
		    			}
		    			else{
		    				p.sendMessage("§7Invalid Usage. (§6" + a[0] + " s|c|a|spec <player>§7)");
		    			}
	    			}
	    		}
	    		else{
	    			p.sendMessage("§7Player §6" + a[2] + " §7isn't §aOnline§7!");
	    		}
	    	}
	    	else{
    			p.sendMessage("§7Invalid Usage. (§6" + a[0] + " s|c|a|spec§7)");
	    	}
		}
		else{
			omp.unknownCommand(a[0]);
		}
	}
}
