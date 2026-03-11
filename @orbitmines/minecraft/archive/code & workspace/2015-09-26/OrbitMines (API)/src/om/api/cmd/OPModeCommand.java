package om.api.cmd;

import om.api.handlers.Command;
import om.api.handlers.players.OMPlayer;
import om.api.utils.Utils;
import om.api.utils.enums.ranks.StaffRank;

import org.bukkit.entity.Player;

public class OPModeCommand extends Command {

	String[] alias = { "/opmode" };
	
	@Override
	public String[] getCMDs() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		Player p = omp.getPlayer();
		
		if(omp.hasPerms(StaffRank.Owner)){
			omp.setOpMode(!omp.isOpMode());
			p.sendMessage("§7Your §4§lOP Mode§7 is now " + Utils.statusString(omp.isOpMode()) + "§7!");
		}
		else{
			omp.unknownCommand(a[0]);
		}
	}
}
