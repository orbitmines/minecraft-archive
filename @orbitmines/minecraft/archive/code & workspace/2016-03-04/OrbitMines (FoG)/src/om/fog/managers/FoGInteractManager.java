package om.fog.managers;

import om.api.managers.InteractManager;
import om.fog.utils.enums.Rarity;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class FoGInteractManager extends InteractManager {
	
	public FoGInteractManager(PlayerInteractEvent e) {
		super(e);
	}
	
	public boolean handleRandomEnchants(){
		if(item.getType() == Material.BOOK && item.getItemMeta().getDisplayName().endsWith("Enchantment §7(Right Click)")){
			e.setCancelled(true);
			omp.updateInventory();
			
			Rarity rarity = Rarity.getByName(item.getItemMeta().getDisplayName().split(" ")[0]);
			if(rarity != null){
				if(item.getAmount() == 1){
					p.getInventory().remove(item);
				}
				else{
					item.setAmount(item.getAmount() -1);
					p.getInventory().setItem(p.getInventory().getHeldItemSlot(), item);
					omp.updateInventory();
				}
				
				p.getWorld().playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
				ItemStack rItem = rarity.randomEnchantment();
				
				p.sendMessage(rarity.getColor() + "You have received a " + rItem.getItemMeta().getDisplayName() + rarity.getColor() + " Enchantment.");
				p.getInventory().addItem(rItem);
			}
			
			return true;
		}
		return false;
	}
}
