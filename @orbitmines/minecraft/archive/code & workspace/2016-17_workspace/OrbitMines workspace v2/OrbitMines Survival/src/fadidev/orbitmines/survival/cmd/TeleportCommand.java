package fadidev.orbitmines.survival.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.PlayerUtils;
import fadidev.orbitmines.api.utils.enums.ranks.StaffRank;
import fadidev.orbitmines.api.utils.enums.ranks.VIPRank;
import fadidev.orbitmines.survival.handlers.SurvivalMessages;
import fadidev.orbitmines.survival.handlers.SurvivalPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeleportCommand extends Command {

	String[] alias = { "/teleport", "/tp" };
	
	@Override
	public String[] getAlias() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omPlayer, String[] a) {
        SurvivalPlayer omp = (SurvivalPlayer) omPlayer;
		Player p = omp.getPlayer();
		
		if(omp.hasPerms(StaffRank.MODERATOR)){
			if(a.length == 2){
	    		Player p2 = PlayerUtils.getPlayer(a[1]);
	    		SurvivalPlayer omp2 = SurvivalPlayer.getSurvivalPlayer(p2);
	    		
	    		if(p2 != null){
	    			if(p2 != p){
						omp.setBackLocation(p.getLocation());
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
	    		SurvivalPlayer omp2 = SurvivalPlayer.getSurvivalPlayer(p2);
	    		SurvivalPlayer omp3 = SurvivalPlayer.getSurvivalPlayer(p3);
	    		
	    		if(p2 != null){
	    			if(p3 != null){
                        omp2.setBackLocation(p2.getLocation());
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
	    		SurvivalPlayer omp2 = SurvivalPlayer.getSurvivalPlayer(p2);
	    		
	    		if(p2 != null){
	    			try{
		    			int x = Integer.parseInt(a[2]);
		    			int y = Integer.parseInt(a[3]);
		    			int z = Integer.parseInt(a[4]);
		    			
		    			Location l = new Location(p2.getWorld(), x, y, z, p2.getLocation().getYaw(), p2.getLocation().getPitch());

                        omp2.setBackLocation(l);
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
		else if(omp.hasPerms(VIPRank.GOLD_VIP)){
			if(a.length == 2){
				Player p2 = PlayerUtils.getPlayer(a[1]);

				if(p2 != null){
					if(p2 != p){
						SurvivalPlayer omp2 = SurvivalPlayer.getSurvivalPlayer(p2);

                        omp2.setTphereRequest(null);
                        omp2.setTpRequest(omp);
						p.sendMessage(SurvivalMessages.CMD_TP_HERE.get(omp, omp2.getName()));
						p2.sendMessage(SurvivalMessages.CMD_TP_PLAYER.get(omp2, omp.getName()));
					}
					else{
                        p.sendMessage(Messages.CMD_TP_TO_SELF.get(omp));
					}
				}
				else{
                    p.sendMessage(Messages.CMD_NOT_ONLINE.get(omp, a[1]));
				}
			}
			else{
				p.sendMessage("§7" + Messages.WORD_USE.get(omp) + " §6/tp <player>§7.");
			}
		}
		else{
			omp.requiredVIPRank(VIPRank.GOLD_VIP);
		}
	}
}
