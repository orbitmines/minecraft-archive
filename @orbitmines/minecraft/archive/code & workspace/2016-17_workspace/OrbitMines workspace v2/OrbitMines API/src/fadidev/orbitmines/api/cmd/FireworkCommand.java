package fadidev.orbitmines.api.cmd;


import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.perks.FireworkInv;

public class FireworkCommand extends Command {

	String[] alias = { "/fireworks" };
	private OrbitMinesAPI api;

	public FireworkCommand(){
		api = OrbitMinesAPI.getApi();
	}
	
	@Override
	public String[] getAlias() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		if(!api.getServerPlugin().fireworksEnabled()){
			omp.unknownCommand(a[0]);
			return;
		}
		new FireworkInv().open(omp.getPlayer());
	}
}
