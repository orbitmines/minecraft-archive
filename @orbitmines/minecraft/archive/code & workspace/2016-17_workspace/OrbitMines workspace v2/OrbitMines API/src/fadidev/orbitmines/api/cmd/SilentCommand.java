package fadidev.orbitmines.api.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.enums.ranks.StaffRank;

public class SilentCommand extends Command {

	String[] alias = { "/silent" };
	
	@Override
	public String[] getAlias() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		if(omp.hasPerms(StaffRank.MODERATOR)){
			omp.setSilent(!omp.isSilent());
		}
		else{
			omp.unknownCommand(a[0]);
		}
	}
}
