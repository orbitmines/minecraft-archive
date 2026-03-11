package om.api.cmd;

import om.api.handlers.Command;
import om.api.handlers.players.OMPlayer;
import om.api.invs.cp.DisguiseInv;

public class DisguisesCommand extends Command {

	String[] alias = { "/disguises" };
	
	@Override
	public String[] getCMDs() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		new DisguiseInv().open(omp.getPlayer());
	}
}
