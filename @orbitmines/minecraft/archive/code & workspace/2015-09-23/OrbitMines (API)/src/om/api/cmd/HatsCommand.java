package om.api.cmd;

import om.api.handlers.Command;
import om.api.handlers.players.OMPlayer;
import om.api.invs.cp.HatInv;

public class HatsCommand extends Command {

	String[] alias = { "/hats" };
	
	@Override
	public String[] getCMDs() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		new HatInv().open(omp.getPlayer());
	}
}
