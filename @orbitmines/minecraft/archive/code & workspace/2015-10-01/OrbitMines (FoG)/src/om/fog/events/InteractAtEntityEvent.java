package om.fog.events;

import om.api.API;
import om.api.handlers.Hologram;
import om.api.handlers.NPCArmorStand;
import om.fog.FoG;
import om.fog.handlers.players.FoGPlayer;
import om.fog.invs.SuitInv;
import om.fog.utils.enums.Suit;

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
			this.api = FoG.getInstance().getAPI();
			Player p = e.getPlayer();
			FoGPlayer omp = FoGPlayer.getFoGPlayer(p);
			NPCArmorStand npc = NPCArmorStand.getNPCArmorStand((ArmorStand) e.getRightClicked());
			ItemStack item = p.getItemInHand();

			if(item != null && item.getType() == Material.MONSTER_EGG){
				e.setCancelled(true);
				omp.updateInventory();
			}
			
			if(npc != null){
				e.setCancelled(true);
				
				switch(npc.getNPCType()){
					case SERVER_SELECTOR:
						api.getServerSelector().open(p);
						break;
					case TRAINING_SUIT:
						new SuitInv(Suit.TRAINING_SUIT, omp.getFaction());
						break;
					case DEFENDER_SUIT:
						new SuitInv(Suit.DEFENDER_SUIT, omp.getFaction());
						break;
					case SHIELD_SUIT:
						new SuitInv(Suit.SHIELD_SUIT, omp.getFaction());
						break;
					case SPEEDSTER_SUIT:
						new SuitInv(Suit.SPEEDSTER_SUIT, omp.getFaction());
						break;
					case EXO_SUIT:
						new SuitInv(Suit.EXO_SUIT, omp.getFaction());
						break;
					default:
						break;
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
