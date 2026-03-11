package om.fog.events;

import om.api.handlers.NPC;
import om.api.utils.enums.Cooldown;
import om.api.utils.enums.cp.Pet;
import om.fog.handlers.players.FoGPlayer;
import om.fog.invs.FactionSelectInv;
import om.fog.invs.ShopInv;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class EntityInteractEvent implements Listener {
	
	@EventHandler
	public void onPlayerEntityInteract(PlayerInteractEntityEvent e){
		final Player p = e.getPlayer();
		FoGPlayer omp = FoGPlayer.getFoGPlayer(p);
		Entity en = e.getRightClicked();
		
		if(omp.isLoaded()){
			if(Pet.pets.contains(en)){
				e.setCancelled(true);
				omp.updateInventory();
			}
			
			NPC npc = NPC.getNPC(en);
			if(npc != null){
				e.setCancelled(true);
				omp.updateInventory();
				
				if(!omp.onCooldown(Cooldown.NPC_INTERACT)){
					switch(npc.getNPCType()){
						//case OMT_SHOP:
						//	new KitPvPOMTShopInv().open(p);
						//	break;
						case FACTION_SELECT:
							if(omp.getFaction() == null){
								new FactionSelectInv().open(p);
							}
							else{
								p.sendMessage("§7You're already in a Faction!");
							}
							break;
						case FOG_SHOP:
							new ShopInv().open(p);
							break;
						case BANK:
							omp.getBank().getBankInv().open(p);
							break;
						default:
							break;
					}
					
					omp.resetCooldown(Cooldown.NPC_INTERACT);
				}
			}
		}
		else{
			e.setCancelled(true);
			omp.updateInventory();
			omp.notLoaded();
		}
	}
}
