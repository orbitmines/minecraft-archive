package om.api.cmd;

import om.api.handlers.Command;
import om.api.handlers.players.OMPlayer;
import om.api.invs.cp.PetInv;

public class PetsCommand extends Command {

	String[] alias = { "/pets" };
	
	@Override
	public String[] getCMDs() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		new PetInv().open(omp.getPlayer());
	}
}
