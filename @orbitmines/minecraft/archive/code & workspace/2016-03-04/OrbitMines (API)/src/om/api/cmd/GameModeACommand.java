package om.api.cmd;

import om.api.handlers.Command;
import om.api.handlers.players.OMPlayer;
import om.api.utils.PlayerUtils;
import om.api.utils.enums.ranks.StaffRank;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class GameModeACommand extends Command {

	String[] alias = { "/gma" };
	
	@Override
	public String[] getCMDs() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		Player p = omp.getPlayer();
		
		if(omp.hasPerms(StaffRank.Owner)){
			if(a.length == 2){
	    		Player p2 = PlayerUtils.getPlayer(a[1]);
	    		OMPlayer omp2 = OMPlayer.getOMPlayer(p2);
		    		
	    		if(p2 != null){
	    			if(p2 == p){
		    			p.setGameMode(GameMode.ADVENTURE);
		    			p.sendMessage("§7Set your §6GameMode§7 to §2§lAdventure§7!");
	    			}
	    			else{
		    			p2.setGameMode(GameMode.ADVENTURE);
		    			p.sendMessage("§7Set " + omp2.getName() + "'s §6GameMode§7 to §2§lAdventure§7!");
		    			p2.sendMessage("§7" + omp.getName() + " §7set your §6GameMode§7 to §2§lAdventure§7!");
	    			}
	    		}
	    		else{
	    			p.sendMessage("§7Player §6" + a[1] + " §7isn't §aOnline§7!");
	    		}
	    	}
	    	else{
	    		p.setGameMode(GameMode.ADVENTURE);
	    		p.sendMessage("§7Set your §6GameMode§7 to §2§lAdventure§7!");
	    	}
		}
		else{
			omp.unknownCommand(a[0]);
		}
	}
}
