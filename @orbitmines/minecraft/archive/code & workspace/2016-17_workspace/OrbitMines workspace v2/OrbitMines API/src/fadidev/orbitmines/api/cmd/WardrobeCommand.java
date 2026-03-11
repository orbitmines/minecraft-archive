package fadidev.orbitmines.api.cmd;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.inventory.perks.WardrobeInv;

public class WardrobeCommand extends Command {

	String[] alias = { "/wardrobe" };
    private OrbitMinesAPI api;

    public WardrobeCommand(){
        api = OrbitMinesAPI.getApi();
    }
	
	@Override
	public String[] getAlias() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
        if(!api.getServerPlugin().wardrobeEnabled()){
            omp.unknownCommand(a[0]);
            return;
        }

		new WardrobeInv().open(omp.getPlayer());
	}
}
