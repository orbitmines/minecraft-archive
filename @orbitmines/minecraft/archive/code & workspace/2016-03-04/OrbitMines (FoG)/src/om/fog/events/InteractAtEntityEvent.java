package om.fog.events;

import om.api.API;
import om.api.handlers.Hologram;
import om.api.handlers.NPCArmorStand;
import om.api.utils.enums.NPCType;
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
						omp.setSuit(Suit.TRAINING_SUIT);
						new SuitInv(Suit.TRAINING_SUIT, omp.getFaction()).open(p);
						break;
					case DEFENDER_SUIT:
						openSuitInv(omp, npc.getNPCType());
						break;
					case SHIELD_SUIT:
						openSuitInv(omp, npc.getNPCType());
						break;
					case SPEEDSTER_SUIT:
						openSuitInv(omp, npc.getNPCType());
						break;
					case EXO_SUIT:
						openSuitInv(omp, npc.getNPCType());
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
	
	private void openSuitInv(FoGPlayer omp, NPCType type){
		Player p = omp.getPlayer();
		Suit suit = Suit.valueOf(type.toString());
		
		if(omp.getRepairTokens().containsKey(suit)){
			if(omp.getRepairTokens(suit) != 0){
				if(omp.getSuit() != null){
					new SuitInv(suit, omp.getFaction()).open(p);
				}
				else{
					//TODO Repair
				}
			}
			else{
				p.sendMessage("§7You have no " + omp.getFaction().getColor() + "Repair Tokens§7 left for the " + suit.getName(omp.getFaction()) + "§7.");
			}
		}
		else{
			//TODO Open Buy Inv
		}
	}
}
