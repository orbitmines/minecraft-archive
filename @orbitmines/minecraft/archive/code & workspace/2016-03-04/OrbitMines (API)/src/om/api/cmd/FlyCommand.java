package om.api.cmd;

import om.api.handlers.Command;
import om.api.handlers.players.OMPlayer;
import om.api.utils.PlayerUtils;
import om.api.utils.Utils;
import om.api.utils.enums.ranks.StaffRank;

import org.bukkit.entity.Player;

public class FlyCommand extends Command {

	String[] alias = { "/fly" };
	
	@Override
	public String[] getCMDs() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		Player p = omp.getPlayer();
		
		if(omp.hasPerms(StaffRank.Moderator)){
			if(a.length == 2 && omp.hasPerms(StaffRank.Moderator)){
	    		Player p2 = PlayerUtils.getPlayer(a[1]);
	    		OMPlayer omp2 = OMPlayer.getOMPlayer(p2);
	    		
	    		if(p2 != null){
	    			if(p2 == p){
			    		p.setAllowFlight(!p.getAllowFlight());
			    		p.setFlying(p.getAllowFlight());
			    		p.sendMessage(Utils.statusString(p.getAllowFlight()) + " §7your §fFly§7 mode!");
	    			}
	    			else{
			    		p2.setAllowFlight(!p2.getAllowFlight());
			    		p2.setFlying(p2.getAllowFlight());
			    		p.sendMessage(Utils.statusString(p2.getAllowFlight()) + " " + omp2.getName() + "'s §fFly§7 mode!");
			    		p2.sendMessage("§7" + omp.getName() + " " + Utils.statusString(p2.getAllowFlight()) + " §7your §fFly§7 mode!");
	    			}
	    		}
	    		else{
	    			p.sendMessage("§7Player §6" + a[1] + " §7isn't §aOnline§7!");
	    		}
	    	}
	    	else{
	    		p.setAllowFlight(!p.getAllowFlight());
	    		p.setFlying(p.getAllowFlight());
	    		p.sendMessage(Utils.statusString(p.getAllowFlight()) + " §7your §fFly§7 mode!");
	    	}
		}
		else{
			omp.unknownCommand(a[0]);
		}
	}
}
