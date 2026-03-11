package om.api.cmd;

import om.api.handlers.Command;
import om.api.handlers.players.OMPlayer;
import om.api.invs.cp.CosmeticPerksInv;

public class PerksCommand extends Command {

	String[] alias = { "/perks" };
	
	@Override
	public String[] getCMDs() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		new CosmeticPerksInv().open(omp.getPlayer());
	}
}
