package fadidev.orbitmines.api.cmd;


import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.perks.CosmeticPerksInv;

public class PerksCommand extends Command {

	String[] alias = { "/perks" };
	
	@Override
	public String[] getAlias() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		new CosmeticPerksInv().open(omp.getPlayer());
	}
}
