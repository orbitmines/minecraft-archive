package fadidev.orbitmines.api.cmd;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.perks.PetInv;

public class PetsCommand extends Command {

	String[] alias = { "/pets" };
	private OrbitMinesAPI api;

    public PetsCommand(){
        api = OrbitMinesAPI.getApi();
    }

	@Override
	public String[] getAlias() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
        if(!api.getServerPlugin().petsEnabled()){
            omp.unknownCommand(a[0]);
            return;
        }
		new PetInv().open(omp.getPlayer());
	}
}
