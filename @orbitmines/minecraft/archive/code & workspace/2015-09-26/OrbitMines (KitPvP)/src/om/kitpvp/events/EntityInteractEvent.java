package om.kitpvp.events;

import om.api.handlers.NPC;
import om.api.utils.enums.Cooldown;
import om.api.utils.enums.cp.Pet;
import om.kitpvp.KitPvP;
import om.kitpvp.handlers.players.KitPvPPlayer;
import om.kitpvp.invs.KitPvPOMTShopInv;
import om.kitpvp.invs.KitSelectorInv;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class EntityInteractEvent implements Listener{
	
	private KitPvP kitpvp;
	
	@EventHandler
	public void onPlayerEntityInteract(PlayerInteractEntityEvent e){
		this.kitpvp = KitPvP.getInstance();
		final Player p = e.getPlayer();
		KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer(p);
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
						case OMT_SHOP:
							new KitPvPOMTShopInv().open(p);
							break;
						case KIT_SELECTOR:
							new KitSelectorInv().open(p);
							break;
						case SPECTATE:
							if(omp.getPet() != null){
								omp.disablePet();
							}
							if(omp.hasTrailEnabled()){
								omp.disableTrail();
							}
							
							omp.setSpectator();
							omp.teleportToMap();
							kitpvp.giveSpectatorKit(omp);
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
