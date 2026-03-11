package fadidev.orbitmines.survival.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.PlayerUtils;
import fadidev.orbitmines.api.utils.enums.ranks.StaffRank;
import fadidev.orbitmines.api.utils.enums.ranks.VIPRank;
import fadidev.orbitmines.survival.handlers.SurvivalMessages;
import fadidev.orbitmines.survival.handlers.SurvivalPlayer;
import org.bukkit.entity.Player;

public class InvSeeCommand extends Command {

	String[] alias = { "/invsee" };
	
	@Override
	public String[] getAlias() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		Player p = omp.getPlayer();
		
		if(omp.hasPerms(StaffRank.MODERATOR) || omp.hasPerms(VIPRank.DIAMOND_VIP)){
			if(a.length == 2){
				Player p2 = PlayerUtils.getPlayer(a[1]);
				
				if(p2 != null){
                    SurvivalPlayer omp2 = SurvivalPlayer.getSurvivalPlayer(p2);

                    omp2.setInvseeRequest((SurvivalPlayer) omp);
                    p.sendMessage(SurvivalMessages.CMD_INVSEE.get(omp, omp2.getName()));
                    p2.sendMessage(SurvivalMessages.CMD_INVSEE_PLAYER.get(omp2, omp.getName()));
	    		}
	    		else{
	    			p.sendMessage(Messages.CMD_NOT_ONLINE.get(omp, a[1]));
	    		}
	    	}
    		else{
		    	p.sendMessage("§7" + Messages.WORD_USE.get(omp) + " §6/invsee <player>§7.");
    		}
		}
		else{
			omp.requiredVIPRank(VIPRank.DIAMOND_VIP);
		}
	}
}
