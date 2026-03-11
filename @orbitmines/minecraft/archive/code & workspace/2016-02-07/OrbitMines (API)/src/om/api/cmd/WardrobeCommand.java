package om.api.cmd;

import om.api.handlers.Command;
import om.api.handlers.players.OMPlayer;
import om.api.invs.cp.WardrobeInv;

public class WardrobeCommand extends Command {

	String[] alias = { "/wardrobe" };
	
	@Override
	public String[] getCMDs() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		new WardrobeInv().open(omp.getPlayer());
	}
}
