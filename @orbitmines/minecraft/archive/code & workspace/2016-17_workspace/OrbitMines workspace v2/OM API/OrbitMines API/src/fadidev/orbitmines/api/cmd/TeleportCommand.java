package fadidev.orbitmines.api.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.PlayerUtils;
import fadidev.orbitmines.api.utils.enums.ranks.StaffRank;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeleportCommand extends Command {

	String[] alias = { "/teleport", "/tp" };
	
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
	    		OMPlayer omp2 = OMPlayer.getOMPlayer(p2);
	    		
	    		if(p2 != null){
	    			if(p2 != p){
			    		p.teleport(p2);
			    		p.sendMessage(Messages.CMD_TP.get(omp, omp2.getName()));
	    			}
	    			else{
                        p.sendMessage(Messages.CMD_TP_TO_SELF.get(omp));
	    			}
	    		}
	    		else{
	    			p.sendMessage(Messages.CMD_NOT_ONLINE.get(omp, a[1]));
	    		}
	    	}
			else if(a.length == 3){
	    		Player p2 = PlayerUtils.getPlayer(a[1]);
	    		Player p3 = PlayerUtils.getPlayer(a[2]);
	    		OMPlayer omp2 = OMPlayer.getOMPlayer(p2);
	    		OMPlayer omp3 = OMPlayer.getOMPlayer(p3);
	    		
	    		if(p2 != null){
	    			if(p3 != null){
				    	p2.teleport(p3);
                        p.sendMessage(Messages.CMD_TP_PLAYER.get(omp, omp2.getName(), omp3.getName()));
	    			}
	    			else{
	    				p.sendMessage(Messages.CMD_NOT_ONLINE.get(omp, a[2]));
	    			}
	    		}
	    		else{
	    			p.sendMessage(Messages.CMD_NOT_ONLINE.get(omp, a[1]));
	    		}
	    	}
	    	else if(a.length == 5){
	    		Player p2 = PlayerUtils.getPlayer(a[1]);
	    		OMPlayer omp2 = OMPlayer.getOMPlayer(p2);
	    		
	    		if(p2 != null){
	    			try{
		    			int x = Integer.parseInt(a[2]);
		    			int y = Integer.parseInt(a[3]);
		    			int z = Integer.parseInt(a[4]);
		    			
		    			Location l = new Location(p2.getWorld(), x, y, z, p2.getLocation().getYaw(), p2.getLocation().getPitch());
		    			
				    	p2.teleport(l);
				    	
				    	if(p2 != p){
                            p.sendMessage(Messages.CMD_TP_PLAYER_CORDS.get(omp, omp2.getName(), x + "", y + "", z + ""));
				    	}
				    	else{
                            p.sendMessage(Messages.CMD_TP_CORDS.get(omp, x + "", y + "", z + ""));
				    	}
				    }catch(NumberFormatException ex){
	    				p.sendMessage(Messages.WORD_INVALID.get(omp) + " Cords. (x/y/z)");
	    			}
	    		}
	    		else{
	    			p.sendMessage(Messages.CMD_NOT_ONLINE.get(omp, a[1]));
	    		}
	    	}
    		else{
		    	p.sendMessage("§7" + Messages.WORD_INVALID_COMMAND.get(omp) + ". §7(§6" + a[0].toLowerCase() + " <player | player1> (player2 | x) (y) (z)");
    		}
		}
		else{
			omp.unknownCommand(a[0]);
		}
	}
}
