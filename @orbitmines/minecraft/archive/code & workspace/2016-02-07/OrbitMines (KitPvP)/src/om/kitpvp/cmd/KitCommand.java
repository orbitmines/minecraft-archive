package om.kitpvp.cmd;

import om.api.handlers.Command;
import om.api.handlers.Kit;
import om.api.handlers.players.OMPlayer;
import om.api.utils.enums.ranks.StaffRank;
import om.kitpvp.handlers.players.KitPvPPlayer;
import om.kitpvp.invs.KitSelectorInv;
import om.kitpvp.utils.enums.KitPvPKit;

import org.bukkit.entity.Player;

public class KitCommand extends Command {

	String[] alias = { "/kit" };
	
	@Override
	public String[] getCMDs() {
		return alias;
	}
	
	@Override
	public void dispatch(OMPlayer omP, String[] a) {
		Player p = omP.getPlayer();
		KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer(p);
		
		if(a.length > 1 && omp.hasPerms(StaffRank.Owner)){
			if(a.length == 3){
				try{
					KitPvPKit kitpvpkit = KitPvPKit.valueOf(a[1].toUpperCase());
					
					try{
						int level = Integer.parseInt(a[2]);
						
						if(level > 0 && level <= kitpvpkit.getMaxLevel()){
							Kit kit = kitpvpkit.getKit(level);
							
							kit.setItems(p);
							omp.setKitSelected(kitpvpkit);
							omp.setKitLevelSelected(level);
							p.sendMessage("§7Selected Kit: '§b§l" + kitpvpkit.getName() + "§7' §7§o(§a§oLvL " + level + "§7§o)");
						}
						else{
							p.sendMessage("§7Kit '§b§l" + kitpvpkit.getName() +"§7' cannot be selected at §a§lLevel " + level + "§7!");
						}
					}catch(Exception ex){
						p.sendMessage("§6" + a[2] + " §7isn't a number!");
					}
				}catch(IllegalArgumentException ex){
					p.sendMessage("§6" + a[1] + " §7isn't a Kit!");
				}
			}
			else{
				p.sendMessage("§7Use §6/kit <kit> <level>§7.");
			}
		}
		else{
			if(omp.getKitSelected() == null){
				if(omp.isPlayer()){
					new KitSelectorInv().open(p);
				}
				else{
					p.sendMessage("§7You can't use §6/kit§7 while §espectating§7!");
				}
			}
			else{
				p.sendMessage("§7You can't use §6/kit§7 while playing!");
			}
		}
	}
}
