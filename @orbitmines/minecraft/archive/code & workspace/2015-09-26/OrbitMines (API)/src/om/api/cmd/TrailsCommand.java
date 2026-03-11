package om.api.cmd;

import om.api.handlers.Command;
import om.api.handlers.players.OMPlayer;
import om.api.invs.cp.TrailInv;

public class TrailsCommand extends Command {

	String[] alias = { "/trails" };
	
	@Override
	public String[] getCMDs() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		new TrailInv().open(omp.getPlayer());
	}
}
