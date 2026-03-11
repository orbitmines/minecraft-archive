package fadidev.orbitmines.api.cmd;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.OMPlayer;

public class ServersCommand extends Command {

	OrbitMinesAPI api;
	String[] alias = { "/servers" };
	
	public ServersCommand() {
		api = OrbitMinesAPI.getApi();
	}
	
	@Override
	public String[] getAlias() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		api.getServerSelector().open(omp.getPlayer());
	}
}
