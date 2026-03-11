package fadidev.orbitmines.api.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.OMPlayer;

public class PluginsCommand extends Command {

	String[] alias = { "/plugins", "/pl" };
	
	@Override
	public String[] getAlias() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		omp.unknownCommand(a[0]);
	}
}
