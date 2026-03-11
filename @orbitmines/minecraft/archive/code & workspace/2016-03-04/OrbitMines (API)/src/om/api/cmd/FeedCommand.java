package om.api.cmd;

import om.api.handlers.Command;
import om.api.handlers.players.OMPlayer;
import om.api.utils.PlayerUtils;
import om.api.utils.enums.ranks.StaffRank;

import org.bukkit.entity.Player;

public class FeedCommand extends Command {

	String[] alias = { "/feed", "/eat" };
	
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
	    				p.setFoodLevel(20);
	    				p.sendMessage("§7Restored your §6Hungerbar§7!");
	    			}
	    			else{
	    				p.sendMessage("§7Restored " + omp2.getName() + "'s §6Hungerbar§7!");
	    				p2.sendMessage("§7" + omp.getName() + "§7 restored your §6Hungerbar§7!");
	    				p2.setFoodLevel(20);
	    			}
	    		}
	    		else{
	    			p.sendMessage("§7Player §6" + a[1] + " §7isn't §aOnline§7!");
	    		}
	    	}
			else{
	    		p.setFoodLevel(20);
	    		p.sendMessage("§7Restored your §6Hungerbar§7!");
	    	}
		}
		else{
			omp.unknownCommand(a[0]);
		}
	}
}
