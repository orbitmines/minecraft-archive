package om.api.cmd;

import om.api.handlers.Command;
import om.api.handlers.players.OMPlayer;
import om.api.utils.enums.ranks.VIPRank;

import org.bukkit.entity.Player;

public class NickCommand extends Command {

	String[] alias = { "/nick" };
	
	@Override
	public String[] getCMDs() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		Player p = omp.getPlayer();
		
		if(omp.hasPerms(VIPRank.Gold_VIP)){
			if(a.length == 2){
	    		if(a[1].length() <= 30){
	    			
	    			if(a[1].equalsIgnoreCase("off")){
	    				p.sendMessage("§7Removed your §6nickname§7!");
	    				omp.setNickname(null);
	    			}
	    			else{
		    			if(omp.hasPerms(VIPRank.Emerald_VIP)){
		    				String newnickname = a[1].replace("&a", "§a").replace("&b", "§b").replace("&c", "§c").replace("&d", "§d").replace("&e", "§e").replace("&f", "§f").replace("&0", "§0").replace("&1", "§1").replace("&2", "§2").replace("&3", "§3").replace("&4", "§4").replace("&5", "§5").replace("&6", "§6").replace("&7", "§7").replace("&8", "§8").replace("&9", "§9").replace("&r", "§r").replace("&k", "§k").replace("&m", "§m").replace("&n", "§n").replace("&l", "§l");
		    				p.sendMessage("§7Changed your §6nickname§7 to '§a" + newnickname + "§7'.");
		    				omp.setNickname(newnickname);
		    			}
		    			else if(omp.hasPerms(VIPRank.Diamond_VIP)){
		    				String newnickname = a[1].replace("&a", "§a").replace("&b", "§b").replace("&c", "§c").replace("&d", "§d").replace("&e", "§e").replace("&f", "§f").replace("&0", "§0").replace("&1", "§1").replace("&2", "§2").replace("&3", "§3").replace("&4", "§4").replace("&5", "§5").replace("&6", "§6").replace("&7", "§7").replace("&8", "§8").replace("&9", "§9");
		    				p.sendMessage("§7Changed your §6nickname§7 to '§9" + newnickname + "§7'.");
		    				omp.setNickname(newnickname);
		    			}
		    			else{
		    				p.sendMessage("§7Changed your §6nickname§7 to '§6" + a[1] + "§7'.");
		    				omp.setNickname(a[1]);
		    			}
	    			}
	    		}
	    		else{
	    			p.sendMessage("§7Your §6nickname§7 cannot be longer than §630 characters§7!");
	    		}
	    	}
	    	else{
	    		p.sendMessage("§7Invalid Usage. (§6/nick <nickname | off>§7)");
	    	}
		}
		else{
    		omp.requiredVIPRank(VIPRank.Gold_VIP);
		}
	}
}
