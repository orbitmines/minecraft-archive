package fadidev.orbitmines.api.cmd;


import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.perks.ChatColorInv;

public class ChatColorsCommand extends Command {

	String[] alias = { "/chatcolors" };
	private OrbitMinesAPI api;

	public ChatColorsCommand(){
		api = OrbitMinesAPI.getApi();
	}
	
	@Override
	public String[] getAlias() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		if(!api.getServerPlugin().chatcolorsEnabled()){
			omp.unknownCommand(a[0]);
			return;
		}
		new ChatColorInv().open(omp.getPlayer());
	}
}
