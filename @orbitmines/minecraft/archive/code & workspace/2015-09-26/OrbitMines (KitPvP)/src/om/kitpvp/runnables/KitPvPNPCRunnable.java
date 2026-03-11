package om.kitpvp.runnables;

import om.api.handlers.NPC;
import om.api.handlers.NPCArmorStand;
import om.api.runnables.NPCRunnable;
import om.api.utils.enums.NPCType;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class KitPvPNPCRunnable extends NPCRunnable {

	public KitPvPNPCRunnable(Duration duration) {
		super(duration);
	}

	@Override
	protected void run(NPCArmorStand npc) {
		if(npc.getNPCType() == NPCType.CRATES){
			if(npc.getItem() != null){
				ItemStack item = npc.getItem().getItemStack();
				if(item.getType() == Material.CHEST){
					item.setType(Material.ENDER_CHEST);
				}
				else if(item.getType() == Material.ENDER_CHEST){
					item.setType(Material.CHEST);
				}
				else{
					item.setType(Material.ENDER_CHEST);
				}
				npc.getItem().setItemStack(item);
			}
		}
	}

	@Override
	protected void run(NPC npc) {
		
	}
}
