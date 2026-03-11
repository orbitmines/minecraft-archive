package fadidev.orbitmines.api.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.InventorySeeInv;
import fadidev.orbitmines.api.utils.PlayerUtils;
import fadidev.orbitmines.api.utils.enums.ranks.StaffRank;
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
		
		if(omp.hasPerms(StaffRank.MODERATOR)){
			if(a.length == 2){
				Player p2 = PlayerUtils.getPlayer(a[1]);
				
				if(p2 != null){
					new InventorySeeInv(p2).open(p);
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
			omp.unknownCommand(a[0]);
		}
	}
}
