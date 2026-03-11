package fadidev.orbitmines.api.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.WorldUtils;
import fadidev.orbitmines.api.utils.enums.ranks.StaffRank;
import org.bukkit.entity.Player;

public class ClearEntitiesCommand extends Command {

	String[] alias = { "/clearentities" };
	
	@Override
	public String[] getAlias() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		Player p = omp.getPlayer();
		
		if(omp.hasPerms(StaffRank.OWNER)){
			WorldUtils.removeEntities(p.getWorld());
		}
		else{
			omp.unknownCommand(a[0]);
		}
	}
}
