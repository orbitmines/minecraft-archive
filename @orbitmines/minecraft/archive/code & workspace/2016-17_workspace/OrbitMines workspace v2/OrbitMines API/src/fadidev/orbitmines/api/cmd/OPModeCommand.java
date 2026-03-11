package fadidev.orbitmines.api.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.enums.ranks.StaffRank;
import org.bukkit.entity.Player;

public class OPModeCommand extends Command {

	String[] alias = { "/opmode" };
	
	@Override
	public String[] getAlias() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		Player p = omp.getPlayer();
		
		if(omp.hasPerms(StaffRank.OWNER)){
			omp.setOpMode(!omp.isOpMode());
			p.sendMessage(Messages.CMD_SET_OP.get(omp));
		}
		else{
			omp.unknownCommand(a[0]);
		}
	}
}
