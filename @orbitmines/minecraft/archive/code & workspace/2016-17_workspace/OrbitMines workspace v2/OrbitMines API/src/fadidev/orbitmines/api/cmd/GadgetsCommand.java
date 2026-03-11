package fadidev.orbitmines.api.cmd;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.perks.GadgetInv;

public class GadgetsCommand extends Command {

	String[] alias = { "/gadgets" };
	private OrbitMinesAPI api;

	public GadgetsCommand(){
		api = OrbitMinesAPI.getApi();
	}
	
	@Override
	public String[] getAlias() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		if(!api.getServerPlugin().gadgetsEnabled()){
			omp.unknownCommand(a[0]);
			return;
		}
		new GadgetInv().open(omp.getPlayer());
	}
}
