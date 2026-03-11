package om.api.cmd;

import om.api.handlers.Command;
import om.api.handlers.players.OMPlayer;
import om.api.utils.enums.ranks.StaffRank;

import org.bukkit.entity.Player;

public class UndisguiseCommand extends Command {

	String[] alias = { "/undisguise", "/undis", "/und" };
	
	@Override
	public String[] getCMDs() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		Player p = omp.getPlayer();
		
		if(omp.hasPerms(StaffRank.Owner)){
			if(omp.isDisguised()){
				omp.undisguise();
				p.sendMessage("§7You are no longer §6disguised§7.");
			}
			else{
				p.sendMessage("§7You aren't §6disguised§7!");
			}
		}
		else{
			omp.unknownCommand(a[0]);
		}
	}
}
