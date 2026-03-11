package om.api.cmd;

import om.api.API;
import om.api.handlers.Command;
import om.api.handlers.players.OMPlayer;

public class ServersCommand extends Command {

	API api = API.getInstance();
	String[] alias = { "/servers" };
	
	public ServersCommand() {
		api = API.getInstance();
	}
	
	@Override
	public String[] getCMDs() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		api.getServerSelector().open(omp.getPlayer());
	}
}
