package om.api.cmd;

import om.api.handlers.Command;
import om.api.handlers.players.OMPlayer;
import om.api.invs.cp.GadgetInv;

public class GadgetsCommand extends Command {

	String[] alias = { "/gadgets" };
	
	@Override
	public String[] getCMDs() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		new GadgetInv().open(omp.getPlayer());
	}
}
