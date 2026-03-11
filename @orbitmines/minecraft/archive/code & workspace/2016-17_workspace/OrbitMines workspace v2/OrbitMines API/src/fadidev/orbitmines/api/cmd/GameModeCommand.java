package fadidev.orbitmines.api.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.PlayerUtils;
import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.api.utils.enums.ranks.StaffRank;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class GameModeCommand extends Command {

	String[] alias = { "/gamemode", "/gm" };
	
	@Override
	public String[] getAlias() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		Player p = omp.getPlayer();
		
		if(omp.hasPerms(StaffRank.OWNER)){
			if(a.length == 2){
			    GameMode gameMode = PlayerUtils.getGameMode(a);

                if(gameMode != null){
                    p.setGameMode(gameMode);
                    p.sendMessage(Messages.CMD_GM_SET.get(omp, Utils.getName(gameMode)));
                }
                else{
    				p.sendMessage("§7" + Messages.WORD_INVALID_COMMAND.get(omp) + ". (§6" + a[0] + " s|c|a|spec§7)");
    			}
	    	}
	    	else if(a.length == 3){
	    		Player p2 = PlayerUtils.getPlayer(a[2]);
                GameMode gameMode = PlayerUtils.getGameMode(a);
	    		
	    		if(p2 != null){
	    			if(p2 == p){
                        if(gameMode != null){
                            p.setGameMode(gameMode);
                            p.sendMessage(Messages.CMD_GM_SET.get(omp, Utils.getName(gameMode)));
                        }
		    			else{
                            p.sendMessage("§7" + Messages.WORD_INVALID_COMMAND.get(omp) + ". (§6" + a[0] + " s|c|a|spec <player>§7)");
		    			}
	    			}
	    			else{
                        if(gameMode != null){
                            OMPlayer omp2 = OMPlayer.getOMPlayer(p2);
                            String name = Utils.getName(gameMode);

                            p2.setGameMode(gameMode);
                            p.sendMessage(Messages.CMD_GM_SET_PLAYER.get(omp, name, omp2.getName()));
                            p2.sendMessage(Messages.CMD_GM_SET_BY.get(omp2, name, omp.getName()));
                        }
                        else{
                            p.sendMessage("§7" + Messages.WORD_INVALID_COMMAND.get(omp) + ". (§6" + a[0] + " s|c|a|spec <player>§7)");
                        }
	    			}
	    		}
	    		else{
                    p.sendMessage(Messages.CMD_NOT_ONLINE.get(omp, a[2]));
	    		}
	    	}
	    	else{
    			p.sendMessage("§7" + Messages.WORD_INVALID_COMMAND.get(omp) + ". (§6" + a[0] + " s|c|a|spec§7)");
	    	}
		}
		else{
			omp.unknownCommand(a[0]);
		}
	}
}
