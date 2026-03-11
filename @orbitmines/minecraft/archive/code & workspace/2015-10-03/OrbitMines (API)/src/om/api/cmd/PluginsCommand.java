package om.api.cmd;

import om.api.handlers.Command;
import om.api.handlers.players.OMPlayer;

public class PluginsCommand extends Command {

	String[] alias = { "/plugins", "/pl" };
	
	@Override
	public String[] getCMDs() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		omp.unknownCommand(a[0]);
	}
}
