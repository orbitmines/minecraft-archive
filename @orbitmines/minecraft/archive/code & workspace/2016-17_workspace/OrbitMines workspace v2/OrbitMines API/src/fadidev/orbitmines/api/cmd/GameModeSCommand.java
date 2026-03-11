package fadidev.orbitmines.api.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.PlayerUtils;
import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.api.utils.enums.ranks.StaffRank;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class GameModeSCommand extends Command {

	String[] alias = { "/gms" };
	
	@Override
	public String[] getAlias() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		Player p = omp.getPlayer();
		
		if(omp.hasPerms(StaffRank.OWNER)){
			String gameMode = Utils.getName(GameMode.SURVIVAL);

			if(a.length == 2){
				Player p2 = PlayerUtils.getPlayer(a[1]);
				OMPlayer omp2 = OMPlayer.getOMPlayer(p2);

				if(p2 != null){
					if(p2 == p){
						p.setGameMode(GameMode.SURVIVAL);
						p.sendMessage(Messages.CMD_GM_SET.get(omp, gameMode));
					}
					else{
						p2.setGameMode(GameMode.SURVIVAL);
						p.sendMessage(Messages.CMD_GM_SET_PLAYER.get(omp, gameMode, omp2.getName()));
						p2.sendMessage(Messages.CMD_GM_SET_BY.get(omp2, gameMode, omp.getName()));
					}
				}
				else{
					p.sendMessage(Messages.CMD_NOT_ONLINE.get(omp, a[1]));
				}
			}
			else{
				p.setGameMode(GameMode.SURVIVAL);
				p.sendMessage(Messages.CMD_GM_SET.get(omp, gameMode));
			}
		}
		else{
			omp.unknownCommand(a[0]);
		}
	}
}
