package fadidev.orbitmines.api.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.enums.ranks.VIPRank;
import org.bukkit.entity.Player;

public class AFKCommand extends Command {

	String[] alias = { "/afk" };

	@Override
	public String[] getAlias() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		Player p = omp.getPlayer();
		
		if(omp.hasPerms(VIPRank.IRON_VIP)){
			if(omp.isAfk()){
				omp.noLongerAfk();
    		}
    		else{
	    		if(a.length == 1){
	    			omp.nowAfk(null);
	    		}
	    		else if(a.length == 2){
		    		if(omp.hasPerms(VIPRank.DIAMOND_VIP)){
			    		if(a[1].length() <= 20){
			    			String afkMessage = a[1];
			    			if(omp.hasPerms(VIPRank.EMERALD_VIP))
								afkMessage = a[1].replaceAll("&a", "§a").replaceAll("&b", "§b").replaceAll("&c", "§c").replaceAll("&d", "§d").replaceAll("&e", "§e").replaceAll("&f", "§f").replaceAll("&0", "§0").replaceAll("&1", "§1").replaceAll("&2", "§2").replaceAll("&3", "§3").replaceAll("&4", "§4").replaceAll("&5", "§5").replaceAll("&6", "§6").replaceAll("&7", "§7").replaceAll("&8", "§8").replaceAll("&9", "§9");

			    			omp.nowAfk(afkMessage);
			    		}
			    		else{
			    			p.sendMessage(Messages.CMD_LONG_AFK_TEXT.get(omp));
			    		}
		    		}
		    		else{
		    			omp.requiredVIPRank(VIPRank.DIAMOND_VIP);
		    		}
		    	}
		    	else{
		    		p.sendMessage(Messages.CMD_INVALID_AFK_USAGE.get(omp));
		    	}
    		}
		}
		else{
			omp.requiredVIPRank(VIPRank.IRON_VIP);
		}
	}
}
