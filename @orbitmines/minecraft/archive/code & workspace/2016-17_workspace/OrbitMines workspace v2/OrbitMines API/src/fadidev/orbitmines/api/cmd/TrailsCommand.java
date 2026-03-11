package fadidev.orbitmines.api.cmd;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.perks.TrailInv;

public class TrailsCommand extends Command {

	String[] alias = { "/trails" };
	private OrbitMinesAPI api;

	public TrailsCommand(){
		api = OrbitMinesAPI.getApi();
	}
	
	@Override
	public String[] getAlias() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		if(!api.getServerPlugin().trailsEnabled()){
			omp.unknownCommand(a[0]);
			return;
		}
		new TrailInv().open(omp.getPlayer());
	}
}
