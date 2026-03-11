package om.kitpvp.events;

import om.api.API;
import om.api.handlers.Hologram;
import om.api.handlers.NPCArmorStand;
import om.api.utils.enums.NPCType;
import om.kitpvp.handlers.players.KitPvPPlayer;
import om.kitpvp.invs.MasteryInv;

import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;

public class InteractAtEntityEvent implements Listener {
	
	private API api;
	
	@EventHandler
	public void onInteract(PlayerInteractAtEntityEvent e){
		if(e.getRightClicked() instanceof ArmorStand){
			this.api = API.getInstance();
			Player p = e.getPlayer();
			KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer(p);
			NPCArmorStand npc = NPCArmorStand.getNPCArmorStand((ArmorStand) e.getRightClicked());
			ItemStack item = p.getItemInHand();

			if(item != null && item.getType() == Material.MONSTER_EGG){
				e.setCancelled(true);
				omp.updateInventory();
			}
			
			if(npc != null){
				e.setCancelled(true);
				
				if(npc.getNPCType() == NPCType.SERVER_SELECTOR){
					api.getServerSelector().open(p);
				}
				else if(npc.getNPCType() == NPCType.MASTERIES){
					new MasteryInv().open(p);
				}
			}
			else{
				Hologram h = Hologram.getHologram((ArmorStand) e.getRightClicked());
				if(h != null){
					e.setCancelled(true);
				}
			}
		}
	}
}
