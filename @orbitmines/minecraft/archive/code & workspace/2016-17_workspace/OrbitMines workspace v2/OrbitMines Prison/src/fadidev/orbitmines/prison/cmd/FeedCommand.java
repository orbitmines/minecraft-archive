package fadidev.orbitmines.prison.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.PlayerUtils;
import fadidev.orbitmines.api.utils.enums.ranks.StaffRank;
import fadidev.orbitmines.api.utils.enums.ranks.VIPRank;
import org.bukkit.entity.Player;

public class FeedCommand extends Command {

	String[] alias = { "/feed", "/eat" };

	@Override
	public String[] getAlias() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		Player p = omp.getPlayer();

		if(omp.hasPerms(StaffRank.OWNER)){
			if(a.length == 2){
	    		Player p2 = PlayerUtils.getPlayer(a[1]);
	    		OMPlayer omp2 = OMPlayer.getOMPlayer(p2);
	    		
	    		if(p2 != null){
	    			if(p2 == p){
	    				p.setFoodLevel(20);
	    				p.sendMessage(Messages.CMD_RESTORE_FOODBAR.get(omp));
	    			}
	    			else{
	    				p.sendMessage(Messages.CMD_RESTORE_FOODBAR_PLAYER.get(omp, omp2.getName()));
	    				p2.sendMessage(Messages.CMD_FOODBAR_RESTORED.get(omp2, omp.getName()));
	    				p2.setFoodLevel(20);
	    			}
	    		}
	    		else{
	    			p.sendMessage(Messages.CMD_NOT_ONLINE.get(omp, a[1]));
	    		}
	    	}
			else{
	    		p.setFoodLevel(20);
	    		p.sendMessage(Messages.CMD_RESTORE_FOODBAR.get(omp));
	    	}
		}
		else if(omp.hasPerms(VIPRank.GOLD_VIP)){
			p.setFoodLevel(20);
            p.sendMessage(Messages.CMD_RESTORE_FOODBAR.get(omp));
		}
		else{
			omp.requiredVIPRank(VIPRank.GOLD_VIP);
		}
	}
}
