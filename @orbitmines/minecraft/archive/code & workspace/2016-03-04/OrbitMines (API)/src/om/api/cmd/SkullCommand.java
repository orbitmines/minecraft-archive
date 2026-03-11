package om.api.cmd;

import om.api.handlers.Command;
import om.api.handlers.players.OMPlayer;
import om.api.utils.enums.ranks.StaffRank;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class SkullCommand extends Command {

	String[] alias = { "/skull" };
	
	@Override
	public String[] getCMDs() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		Player p = omp.getPlayer();
		
		if(omp.hasPerms(StaffRank.Owner)){
			if(a.length == 2){
	    		p.sendMessage("§7You've been given §6" + a[1] + "'s§7 skull.");

				ItemStack item = new ItemStack(Material.SKULL_ITEM, 1);
				SkullMeta meta = (SkullMeta) item.getItemMeta();
				meta.setDisplayName("§6" + a[1] + "'s §7Skull");
				meta.setOwner(a[1]);
				item.setItemMeta(meta);
				item.setDurability((short) 3);
				
				p.getInventory().addItem(item);
	    	}
	    	else{
		    	p.sendMessage("§7Invalid Usage. (§6" + a[0].toLowerCase() + " <player>§7)");
	    	}
		}
		else{
			omp.unknownCommand(a[0]);
		}
	}
}
