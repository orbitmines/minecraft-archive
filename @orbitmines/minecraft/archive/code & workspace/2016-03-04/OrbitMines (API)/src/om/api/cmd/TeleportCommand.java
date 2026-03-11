package om.api.cmd;

import om.api.handlers.Command;
import om.api.handlers.players.OMPlayer;
import om.api.utils.PlayerUtils;
import om.api.utils.enums.ranks.StaffRank;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeleportCommand extends Command {

	String[] alias = { "/teleport", "/tp" };
	
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
	    		OMPlayer omp2 = OMPlayer.getOMPlayer(p2);
	    		
	    		if(p2 != null){
	    			if(p2 != p){
			    		p.teleport(p2);
			    		p.sendMessage("§7Teleported to " + omp2.getName() + "§7!");
	    			}
	    			else{
	    				p.sendMessage("§7You can't §6teleport§7 to yourself!");
	    			}
	    		}
	    		else{
	    			p.sendMessage("§7Player §6" + a[1] + " §7isn't §aOnline§7!");
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
				    	p.sendMessage("§7Teleported " + omp2.getName() + "§7 to " + omp3.getName() + "§7!");
	    			}
	    			else{
	    				p.sendMessage("§7Player §6" + a[2] + " §7isn't §aOnline§7!");
	    			}
	    		}
	    		else{
	    			p.sendMessage("§7Player §6" + a[1] + " §7isn't §aOnline§7!");
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
				    		p.sendMessage("§7Teleported " + omp2.getName() + "§7 to §6" + x + "§7, §6" + y + "§7, §6" + z + "§7!");
				    	}
				    	else{
				    		p.sendMessage("§7Teleported to §6" + x + "§7, §6" + y + "§7, §6" + z + "§7!");
				    	}
				    }catch(NumberFormatException ex){
	    				p.sendMessage("§7The given coordinates aren't numbers!");
	    			}
	    		}
	    		else{
	    			p.sendMessage("§7Player §6" + a[1] + " §7isn't §aOnline§7!");
	    		}
	    	}
    		else{
		    	p.sendMessage("§7Invalid Usage. §7(§6" + a[0].toLowerCase() + " <player | player1> (player2 | x) (y) (z)");
    		}
		}
		else{
			omp.unknownCommand(a[0]);
		}
	}
}
