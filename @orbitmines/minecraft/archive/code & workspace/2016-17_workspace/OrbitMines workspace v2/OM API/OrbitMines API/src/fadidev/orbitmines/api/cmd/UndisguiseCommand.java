package fadidev.orbitmines.api.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.enums.ranks.StaffRank;
import org.bukkit.entity.Player;

public class UndisguiseCommand extends Command {

	String[] alias = { "/undisguise", "/undis", "/und" };
	
	@Override
	public String[] getAlias() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		Player p = omp.getPlayer();
		
		if(omp.hasPerms(StaffRank.OWNER)){
			if(omp.isDisguised()){
				omp.unDisguise();
                p.sendMessage(Messages.CMD_UNDISGUISE.get(omp));
			}
			else{
				p.sendMessage(Messages.CMD_NOT_DISGUISED.get(omp));
			}
		}
		else{
			omp.unknownCommand(a[0]);
		}
	}
}
