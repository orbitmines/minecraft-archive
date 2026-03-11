package fadidev.orbitmines.api.cmd;


import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.perks.HatInv;

public class HatsCommand extends Command {

	String[] alias = { "/hats" };
	private OrbitMinesAPI api;

	public HatsCommand(){
		api = OrbitMinesAPI.getApi();
	}
	
	@Override
	public String[] getAlias() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		if(!api.getServerPlugin().hatsEnabled()){
			omp.unknownCommand(a[0]);
			return;
		}
		new HatInv().open(omp.getPlayer());
	}
}
