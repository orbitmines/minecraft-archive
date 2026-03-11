package om.api.cmd;

import om.api.handlers.Command;
import om.api.handlers.players.OMPlayer;
import om.api.utils.enums.ranks.StaffRank;

public class SilentCommand extends Command {

	String[] alias = { "/silent" };
	
	@Override
	public String[] getCMDs() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		if(omp.hasPerms(StaffRank.Moderator)){
			omp.setSilentMode(!omp.isSilentMode());
		}
		else{
			omp.unknownCommand(a[0]);
		}
	}
}
