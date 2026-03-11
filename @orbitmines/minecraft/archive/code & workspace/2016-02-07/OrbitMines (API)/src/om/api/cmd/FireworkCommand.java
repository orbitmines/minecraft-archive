package om.api.cmd;

import om.api.handlers.Command;
import om.api.handlers.players.OMPlayer;
import om.api.invs.cp.FireworkInv;

public class FireworkCommand extends Command {

	String[] alias = { "/fireworks" };
	
	@Override
	public String[] getCMDs() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		new FireworkInv().open(omp.getPlayer());
	}
}
