package fadidev.orbitmines.api.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.enums.ranks.VIPRank;
import org.bukkit.entity.Player;

public class NickCommand extends Command {

	String[] alias = { "/nick" };
	
	@Override
	public String[] getAlias() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		Player p = omp.getPlayer();
		
		if(omp.hasPerms(VIPRank.GOLD_VIP)){
			if(a.length == 2){
	    		if(a[1].length() <= 30){
	    			
	    			if(a[1].equalsIgnoreCase("off")){
	    				p.sendMessage(Messages.CMD_NICK_REMOVE.get(omp));
	    				omp.setNickName(null);
	    			}
	    			else{
		    			if(omp.hasPerms(VIPRank.EMERALD_VIP)){
		    				String newNickName = a[1].replace("&a", "§a").replace("&b", "§b").replace("&c", "§c").replace("&d", "§d").replace("&e", "§e").replace("&f", "§f").replace("&0", "§0").replace("&1", "§1").replace("&2", "§2").replace("&3", "§3").replace("&4", "§4").replace("&5", "§5").replace("&6", "§6").replace("&7", "§7").replace("&8", "§8").replace("&9", "§9").replace("&r", "§r").replace("&k", "§k").replace("&m", "§m").replace("&n", "§n").replace("&l", "§l");
                            p.sendMessage(Messages.CMD_NICK_CHANGE.get(omp, newNickName));
                            omp.setNickName(newNickName);
		    			}
		    			else if(omp.hasPerms(VIPRank.DIAMOND_VIP)){
		    				String newNickName = a[1].replace("&a", "§a").replace("&b", "§b").replace("&c", "§c").replace("&d", "§d").replace("&e", "§e").replace("&f", "§f").replace("&0", "§0").replace("&1", "§1").replace("&2", "§2").replace("&3", "§3").replace("&4", "§4").replace("&5", "§5").replace("&6", "§6").replace("&7", "§7").replace("&8", "§8").replace("&9", "§9");
                            p.sendMessage(Messages.CMD_NICK_CHANGE.get(omp, newNickName));
                            omp.setNickName(newNickName);
		    			}
		    			else{
		    			    p.sendMessage(Messages.CMD_NICK_CHANGE.get(omp, a[1]));
		    				omp.setNickName(a[1]);
		    			}
	    			}
	    		}
	    		else{
	    			p.sendMessage(Messages.CMD_NICK_TOO_LONG.get(omp));
	    		}
	    	}
	    	else{
	    		p.sendMessage("§7" + Messages.WORD_INVALID_COMMAND.get(omp) + ". (§6/nick <nickname | off>§7)");
	    	}
		}
		else{
    		omp.requiredVIPRank(VIPRank.GOLD_VIP);
		}
	}
}
