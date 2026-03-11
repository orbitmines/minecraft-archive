package om.api.cmd;

import om.api.handlers.Command;
import om.api.handlers.players.OMPlayer;
import om.api.utils.enums.ranks.StaffRank;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class ClearEntitiesCommand extends Command {

	String[] alias = { "/clearentities" };
	
	@Override
	public String[] getCMDs() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		Player p = omp.getPlayer();
		
		if(omp.hasPerms(StaffRank.Owner)){
			for(Entity en : p.getWorld().getEntities()){
				if(!(en instanceof Player)){
					en.remove();
				}
			}
		}
		else{
			omp.unknownCommand(a[0]);
		}
	}
}
