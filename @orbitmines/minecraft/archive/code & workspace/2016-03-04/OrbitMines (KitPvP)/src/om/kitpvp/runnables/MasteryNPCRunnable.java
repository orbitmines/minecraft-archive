package om.kitpvp.runnables;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import om.api.handlers.NPCArmorStand;
import om.api.runnables.OMRunnable;
import om.api.utils.enums.NPCType;

public class MasteryNPCRunnable extends OMRunnable {

	public MasteryNPCRunnable(Duration duration) {
		super(duration);
	}

	@Override
	protected void run() {
		NPCArmorStand npc = NPCArmorStand.getNPCArmorStand(NPCType.MASTERIES);
		
		if(npc != null && npc.getItem() != null){
			ItemStack item = npc.getItem().getItemStack();
			if(item.getType() == Material.DIAMOND_SWORD){
				item.setType(Material.IRON_CHESTPLATE);
			}
			else if(item.getType() == Material.IRON_CHESTPLATE){
				item.setType(Material.BOW);
			}
			else if(item.getType() == Material.BOW){
				item.setType(Material.ARROW);
			}
			else if(item.getType() == Material.ARROW){
				item.setType(Material.DIAMOND_SWORD);
			}
			else{
				item.setType(Material.DIAMOND_SWORD);
			}
			npc.getItem().setItemStack(item);
		}
	}
}
