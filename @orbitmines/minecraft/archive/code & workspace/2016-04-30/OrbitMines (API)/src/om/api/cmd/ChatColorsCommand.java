package om.api.cmd;

import om.api.handlers.Command;
import om.api.handlers.players.OMPlayer;
import om.api.invs.cp.ChatColorInv;

public class ChatColorsCommand extends Command {

	String[] alias = { "/chatcolors" };
	
	@Override
	public String[] getCMDs() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		new ChatColorInv().open(omp.getPlayer());
	}
}
