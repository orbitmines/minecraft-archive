package fadidev.orbitmines.api.cmd;


import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.perks.DisguiseInv;

public class DisguisesCommand extends Command {

	String[] alias = { "/disguises" };
	private OrbitMinesAPI api;

	public DisguisesCommand(){
		api = OrbitMinesAPI.getApi();
	}

	@Override
	public String[] getAlias() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		if(!api.getServerPlugin().disguisesEnabled()){
			omp.unknownCommand(a[0]);
			return;
		}
		new DisguiseInv().open(omp.getPlayer());
	}
}
