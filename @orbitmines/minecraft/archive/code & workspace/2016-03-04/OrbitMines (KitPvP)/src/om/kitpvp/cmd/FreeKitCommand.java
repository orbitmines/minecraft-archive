package om.kitpvp.cmd;

import org.bukkit.Bukkit;

import om.api.handlers.Command;
import om.api.handlers.players.OMPlayer;
import om.api.utils.Utils;
import om.api.utils.enums.ranks.StaffRank;
import om.kitpvp.KitPvP;

public class FreeKitCommand extends Command {

	private KitPvP kitpvp;
	String[] alias = { "/freekits" };
	
	public FreeKitCommand() {
		kitpvp = KitPvP.getInstance();
	}
	
	@Override
	public String[] getCMDs() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		if(omp.hasPerms(StaffRank.Owner)){
			kitpvp.setFreeKitEnabled(!kitpvp.isFreeKitEnabled());
			Bukkit.broadcastMessage("§d§lFree Kits§7 have been " + Utils.statusString(kitpvp.isFreeKitEnabled()) + " §7by " + omp.getName() + "§7.");
		}
		else{
			omp.unknownCommand(a[0]);
		}
	}
}
