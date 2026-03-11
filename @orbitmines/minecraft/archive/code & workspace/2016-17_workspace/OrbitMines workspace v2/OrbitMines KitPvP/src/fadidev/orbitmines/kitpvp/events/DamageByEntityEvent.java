package fadidev.orbitmines.kitpvp.events;

import fadidev.orbitmines.kitpvp.OrbitMinesKitPvP;
import fadidev.orbitmines.kitpvp.handlers.KitPvPPlayer;
import fadidev.orbitmines.kitpvp.handlers.Masteries;
import fadidev.orbitmines.kitpvp.utils.enums.ArmorType;
import fadidev.orbitmines.kitpvp.utils.enums.ItemType;
import fadidev.orbitmines.kitpvp.utils.enums.KitPvPKit;
import fadidev.orbitmines.kitpvp.utils.enums.Mastery;
import org.bukkit.entity.Entity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class DamageByEntityEvent implements Listener {

	private OrbitMinesKitPvP kitPvP;

	public DamageByEntityEvent(){
		kitPvP = OrbitMinesKitPvP.getKitPvP();
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDamage(EntityDamageByEntityEvent e){
		if(e.getDamager() instanceof Player){
			Player pD = (Player) e.getDamager();
			KitPvPPlayer ompD = KitPvPPlayer.getKitPvPPlayer(pD);
			Masteries mD = ompD.getMasteries();
			
			if(ompD.getKitSelected() == KitPvPKit.UNDEATH_KING && ompD.getSummonedUndeath().contains(e.getEntity()))
				e.setCancelled(true);
			
			if(ompD.isSpectator() || ompD.isPlayer() && ompD.getKitSelected() == null)
				e.setCancelled(true);
			
			if(e.getEntity() instanceof Player){
				Player pE = (Player) e.getEntity();
				KitPvPPlayer ompE = KitPvPPlayer.getKitPvPPlayer(pE);
				Masteries mE = ompE.getMasteries();
				
				if(ompD.getKitSelected() == KitPvPKit.UNDEATH_KING && ompD.getSummonedUndeath().size() > 0){
					for(Entity en : ompD.getSummonedUndeath()){
						((PigZombie) en).setTarget(pE);
					}
				}
				if(ompE.getKitSelected() == KitPvPKit.UNDEATH_KING && ompE.getSummonedUndeath().size() > 0){
					for(Entity en : ompE.getSummonedUndeath()){
						((PigZombie) en).setTarget(pD);
					}
				}
				
				// Masteries \\
				double meleedamage = e.getDamage() * mD.getMasteryEffect(Mastery.MELEE);
				double meleeprotected = e.getDamage() * mE.getMasteryEffect(Mastery.MELEE_PROTECTION);
				e.setDamage(e.getDamage() + meleedamage + meleeprotected);
				
				// Play Armor Enchantments \\
				for(ItemStack item : pE.getInventory().getArmorContents()){
					if(item != null && item.getItemMeta() != null && item.getItemMeta().getLore() != null){
						List<String> itemlore = item.getItemMeta().getLore();
						
						for(ArmorType type : ArmorType.getArmorTypes(ompE.getKitSelected())){
							if((type == ArmorType.WITHER_ARMOR_I || type == ArmorType.MOLTEN_ARMOR_I) && itemlore.contains(type.getName())){
								type.playEnchantment(ompD, ompE);
							}
						}
					}
				}
				
				ItemStack item = pD.getItemInHand();
				if(item != null && item.getItemMeta() != null && item.getItemMeta().getLore() != null){
					List<String> itemlore = item.getItemMeta().getLore();

					for(ItemType type : ItemType.getItemTypes(ompD.getKitSelected())){
						if(itemlore.contains(type.getName())){
							type.playEnchantment(ompD, ompE, e.getDamage());
						}
					}
				}
			}
		}
		else if(e.getDamager() instanceof PigZombie && e.getEntity() instanceof Player){
			Player pE = (Player) e.getEntity();
			KitPvPPlayer ompD = null;
			for(KitPvPPlayer omp : kitPvP.getKitPvPPlayers()){
				if(omp.getSummonedUndeath().contains(e.getDamager())){
					ompD = omp;
					break;
				}
			}
			
			e.setCancelled(true);
			pE.damage(e.getDamage(), ompD.getPlayer());
		}
		else if(e.getDamager() instanceof Projectile && e.getEntity() instanceof Player){
			Projectile proj = (Projectile) e.getDamager();
			Player pE = (Player) e.getEntity();
			KitPvPPlayer ompE = KitPvPPlayer.getKitPvPPlayer(pE);
			Masteries mE = ompE.getMasteries();
			
			if(proj.getShooter() instanceof Player){
				Player pD = (Player) proj.getShooter();
				KitPvPPlayer ompD = KitPvPPlayer.getKitPvPPlayer(pD);
				Masteries mD = ompD.getMasteries();
				
				// Masteries \\
				double rangedamage = e.getDamage() * mD.getMasteryEffect(Mastery.RANGE);
				double rangeprotected = e.getDamage() * mE.getMasteryEffect(Mastery.RANGE_PROTECTION);
				e.setDamage(e.getDamage() + rangedamage + rangeprotected);
				
				if(kitPvP.isProjectile(proj)){
                    kitPvP.getProjectileType(proj).playEnchantment(ompD, ompE, proj);

                    kitPvP.removeProjectile(proj);
				}
			}
		}
	}
}
