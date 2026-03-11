package fadidev.orbitmines.api.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.enums.ranks.StaffRank;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AddEnchantmentCommand extends Command {

	String[] alias = { "/addench", "/addenchantment" };

	@Override
	public String[] getAlias() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		Player p = omp.getPlayer();

		if(omp.hasPerms(StaffRank.OWNER)){
			ItemStack item = p.getItemInHand();

			if(item != null){
				if(a.length == 3){
					Enchantment ench = Enchantment.getByName(a[1]);

					if(ench != null){
						try{
							int level = Integer.parseInt(a[2]);

							item.addUnsafeEnchantment(ench, level);
						}catch(NumberFormatException ex){
							p.sendMessage("§7" + Messages.WORD_INVALID.get(omp) + " Level. (§6" + a[2] + "§7)");
						}
					}
					else{
						p.sendMessage("§7" + Messages.WORD_UNKNOWN.get(omp) + " Enchantment. (§6" + a[1] + "§7)");
					}
				}
				else{
					p.sendMessage("§7" + Messages.WORD_USE.get(omp) + " §6" + a[0].toLowerCase() + " <enchantment> <level>§7.");
				}
			}
			else{
				p.sendMessage(Messages.CMD_NO_ITEM_IN_HAND.get(omp));
			}
		}
		else{
			omp.unknownCommand(a[0]);
		}
	}
}
