package om.api.cmd;

import om.api.handlers.Command;
import om.api.handlers.players.OMPlayer;
import om.api.utils.enums.ranks.VIPRank;

import org.bukkit.entity.Player;

public class AFKCommand extends Command {

	String[] alias = { "/afk" };
	
	@Override
	public String[] getCMDs() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		Player p = omp.getPlayer();
		
		if(omp.hasPerms(VIPRank.Iron_VIP)){
			if(omp.isAFK()){
				omp.noLongerAFK();
    		}
    		else{
	    		if(a.length == 1){
	    			omp.nowAFK(null);
	    		}
	    		else if(a.length == 2){
		    		if(omp.hasPerms(VIPRank.Diamond_VIP)){
			    		if(a[1].length() <= 20){
			    			String afkmessage = a[1];
			    			if(omp.hasPerms(VIPRank.Emerald_VIP)){
				    			afkmessage = a[1].replaceAll("&a", "§a").replaceAll("&b", "§b").replaceAll("&c", "§c").replaceAll("&d", "§d").replaceAll("&e", "§e").replaceAll("&f", "§f").replaceAll("&0", "§0").replaceAll("&1", "§1").replaceAll("&2", "§2").replaceAll("&3", "§3").replaceAll("&4", "§4").replaceAll("&5", "§5").replaceAll("&6", "§6").replaceAll("&7", "§7").replaceAll("&8", "§8").replaceAll("&9", "§9");
			    			}
			    			omp.nowAFK(afkmessage);
			    		}
			    		else{
			    			p.sendMessage("§7Your §6afk text§7 can't be longer than §620 characters§7!");
			    		}
		    		}
		    		else{
		    			omp.requiredVIPRank(VIPRank.Diamond_VIP);
		    		}
		    	}
		    	else{
		    		p.sendMessage("§7Invalid Usage. (§6/afk §7or §6/afk <reason>§7)");
		    	}
    		}
		}
		else{
			omp.requiredVIPRank(VIPRank.Iron_VIP);
		}
	}
}
