package om.api.cmd;

import om.api.handlers.Command;
import om.api.handlers.players.OMPlayer;
import om.api.invs.others.InventorySeeInv;
import om.api.utils.PlayerUtils;
import om.api.utils.enums.ranks.StaffRank;

import org.bukkit.entity.Player;

public class InvSeeCommand extends Command {

	String[] alias = { "/invsee" };
	
	@Override
	public String[] getCMDs() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		Player p = omp.getPlayer();
		
		if(omp.hasPerms(StaffRank.Moderator)){
			if(a.length == 2){
				Player p2 = PlayerUtils.getPlayer(a[1]);
				
				if(p2 != null){
					new InventorySeeInv(p2).open(p);
	    		}
	    		else{
	    			p.sendMessage("§7Player §6" + a[1] + " §7isn't §aOnline§7!");
	    		}
	    	}
    		else{
		    	p.sendMessage("§7Use §6/invsee <player>§7.");
    		}
		}
		else{
			omp.unknownCommand(a[0]);
		}
	}
}
