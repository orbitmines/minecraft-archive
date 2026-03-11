package om.api.cmd;

import om.api.handlers.Command;
import om.api.handlers.players.OMPlayer;
import om.api.utils.enums.ranks.StaffRank;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AddEnchantmentCommand extends Command {

	String[] alias = { "/addench", "/addenchantment" };
	
	@Override
	public String[] getCMDs() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		Player p = omp.getPlayer();
		
		if(omp.hasPerms(StaffRank.Owner)){
			ItemStack item = p.getItemInHand();
			
			if(item != null){
				if(a.length == 3){
					Enchantment ench = Enchantment.getByName(a[1]);
					
					if(ench != null){
						try{
							int level = Integer.parseInt(a[2]);
							
							item.addUnsafeEnchantment(ench, level);
						}catch(NumberFormatException ex){
							p.sendMessage("§7Invalid Level. (§6" + a[2] + "§7)");
						}
					}
					else{
						p.sendMessage("§7Unknown Enchantment. (§6" + a[1] + "§7)");
					}
				}
				else{
					p.sendMessage("§7Use §6" + a[0].toLowerCase() + " <enchantment> <level>§7.");
				}
			}
			else{
				p.sendMessage("§7You don't have an item in your hand.");
			}
		}
		else{
			omp.unknownCommand(a[0]);
		}
	}
}
