package fadidev.orbitmines.survival.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.PlayerUtils;
import fadidev.orbitmines.api.utils.enums.ranks.StaffRank;
import fadidev.orbitmines.api.utils.enums.ranks.VIPRank;
import org.bukkit.entity.Player;

public class FlyCommand extends Command {

	String[] alias = { "/fly" };
	
	@Override
	public String[] getAlias() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		Player p = omp.getPlayer();
		
		if(omp.hasPerms(StaffRank.MODERATOR) || omp.hasPerms(VIPRank.DIAMOND_VIP)){
			if(a.length == 2 && omp.hasPerms(StaffRank.MODERATOR)){
	    		Player p2 = PlayerUtils.getPlayer(a[1]);
	    		OMPlayer omp2 = OMPlayer.getOMPlayer(p2);
	    		
	    		if(p2 != null){
	    			if(p2 == p){
			    		p.setAllowFlight(!p.getAllowFlight());
			    		p.setFlying(p.getAllowFlight());
			    		p.sendMessage(Messages.CMD_TOGGLE_FLY.get(omp));
	    			}
	    			else{
			    		p2.setAllowFlight(!p2.getAllowFlight());
			    		p2.setFlying(p2.getAllowFlight());
						p.sendMessage(Messages.CMD_TOGGLE_FLY_PLAYER.get(omp, omp2.getName()));
						p2.sendMessage(Messages.CMD_FLY_TOGGLED.get(omp2, omp.getName()));
	    			}
	    		}
	    		else{
	    			p.sendMessage(Messages.CMD_NOT_ONLINE.get(omp, a[1]));
	    		}
	    	}
	    	else{
	    		p.setAllowFlight(!p.getAllowFlight());
	    		p.setFlying(p.getAllowFlight());
	    		p.sendMessage(Messages.CMD_TOGGLE_FLY.get(omp));
	    	}
		}
		else{
			omp.requiredVIPRank(VIPRank.DIAMOND_VIP);
		}
	}
}
