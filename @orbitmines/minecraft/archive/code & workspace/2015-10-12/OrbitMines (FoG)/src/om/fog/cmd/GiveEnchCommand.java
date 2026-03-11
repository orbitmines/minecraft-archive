package om.fog.cmd;

import om.api.handlers.Command;
import om.api.handlers.players.OMPlayer;
import om.api.utils.enums.ranks.StaffRank;
import om.fog.utils.FoGUtils;
import om.fog.utils.enums.Rarity;
import om.fog.utils.enums.ShieldLore;
import om.fog.utils.enums.SwordLore;
import om.fog.utils.enums.ToolLore;

import org.bukkit.entity.Player;

public class GiveEnchCommand extends Command {

	String[] alias = { "/giveench", "/giveenchantment" };
	
	@Override
	public String[] getCMDs() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		Player p = omp.getPlayer();
		
		if(omp.hasPerms(StaffRank.Owner)){
			if(a.length == 3){
				if(a[1].equalsIgnoreCase("rarity")){
					try{
						Rarity rarity = Rarity.valueOf(a[2].toUpperCase());
						p.getInventory().addItem(rarity.getRandomItem());
						if(rarity == Rarity.UNCOMMON){
							p.sendMessage("§7You have received an " + rarity.getName() + " Enchantment§7 Book.");
						}
						else{
							p.sendMessage("§7You have received a " + rarity.getName() + " Enchantment§7 Book.");
						}
					}catch(IllegalArgumentException ex){
						p.sendMessage("§7Unknown Rarity: '§6" + a[2] + "§7'.");
					}
				}
				else{
					try{
						int level = Integer.parseInt(a[2]);
						SwordLore l1 = SwordLore.getByName(a[1]);
						
						if(l1 != null){
							if(l1.getMaxLevel() >= level){
								p.getInventory().addItem(l1.getEnchantment(level));
								p.sendMessage("§7You have received a " + l1.getRarity(level).getColor() + "§l" + l1.getName() + " " + FoGUtils.parseString(level) + "§7 Enchantment.");
							}
							else{
								p.sendMessage("§7Enchantment §6" + l1.getName() + "§7 max level is §6" + l1.getMaxLevel() + "§7.");
							}
						}
						else{
							ShieldLore l2 = ShieldLore.getByName(a[1]);
		
							if(l2 != null){
								if(l2.getMaxLevel() >= level){
									p.getInventory().addItem(l2.getEnchantment(level));
									p.sendMessage("§7You have received a " + l2.getRarity(level).getColor() + "§l" + l2.getName() + " " + FoGUtils.parseString(level) + "§7 Enchantment.");
								}
								else{
									p.sendMessage("§7Enchantment §6" + l2.getName() + "§7 max level is §6" + l2.getMaxLevel() + "§7.");
								}
							}
							else{
								ToolLore l3 = ToolLore.getByName(a[1]);
								
								if(l3 != null){
									if(l3.getMaxLevel() >= level){
										p.getInventory().addItem(l3.getEnchantment(level));
										p.sendMessage("§7You have received a " + l3.getRarity(level).getColor() + "§l" + l3.getName() + " " + FoGUtils.parseString(level) + "§7 Enchantment.");
									}
									else{
										p.sendMessage("§7Enchantment §6" + l3.getName() + "§7 max level is §6" + l3.getMaxLevel() + "§7.");
									}
								}
								else{
									p.sendMessage("§7Unknown Enchantment: '§6" + a[1] + "§7'.");
								}
							}
						}
					}catch(NumberFormatException ex){
						p.sendMessage("§7Unknown level.");
					}
				}
			}
			else{
				p.sendMessage("§7Use §6" + a[0] + " <ench>|rarity <level>|<rarity>§7.");
			}
		}
		else{
			omp.unknownCommand(a[0]);
		}
	}
}
