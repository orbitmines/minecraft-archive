package om.fog.events;

import net.minecraft.server.v1_8_R3.EnumParticle;
import om.api.handlers.Particle;
import om.api.utils.enums.cp.Pet;
import om.fog.FoG;
import om.fog.handlers.players.FoGPlayer;
import om.fog.utils.enums.SwordLore;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class DamageEvent implements Listener {
	
	private FoG fog;
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDamage(EntityDamageByEntityEvent e){
		fog = FoG.getInstance();
		
		if(!e.isCancelled()){
			if(e.getDamager() instanceof Player && e.getEntity() instanceof LivingEntity && !(e.getEntity() instanceof ArmorStand)){
				Player pD = (Player) e.getDamager();
				FoGPlayer ompD = FoGPlayer.getFoGPlayer(pD);
				ItemStack item = pD.getItemInHand();
				final LivingEntity en = (LivingEntity) e.getEntity();
				Player pE = null;
				FoGPlayer ompE = null;
				
				boolean cancontinue = true;
				if(e.getEntity() instanceof Player){
					pE = (Player) e.getEntity();
					ompE = FoGPlayer.getFoGPlayer(pE);
					
					if(ompD.getFaction() == ompE.getFaction()){
						e.setCancelled(true);
						cancontinue = false;
					}
				}
				
				if(cancontinue && item != null && (item.getType() == Material.WOOD_SWORD || item.getType() == Material.STONE_SWORD || item.getType() == Material.IRON_SWORD || item.getType() == Material.GOLD_SWORD || item.getType() == Material.DIAMOND_SWORD)){
					double damage = 0;
					double damageToShield = 0;
					
					for(SwordLore sl : SwordLore.getCorrectOrder()){
						int level = sl.getLevel(item);
						
						if(level != -1){
							double r = Math.random();
							switch(sl){
								case BLINDNESS:
									if(r < (0.05 * level)){//5%, 10%, 15%
										en.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 0));
										Particle p = new Particle(EnumParticle.SMOKE_LARGE, en.getLocation());
										p.setAmount(15);
										p.send(Bukkit.getOnlinePlayers());
									}
									break;
								case DAMAGE:
									damage = level;
									break;
								case FIRE_DAMAGE:
									en.setFireTicks(level * 40);
									break;
								case SHIELD_BUSTER:
									if(ompE != null && r < (0.03 * level)){//3%, 6%, 9%
										if(ompE.getCurrentShield() != 0){
											pE.getWorld().playSound(pE.getLocation(), Sound.ANVIL_LAND, 1, 0.1F);
											Particle p = new Particle(EnumParticle.EXPLOSION_HUGE, pE.getLocation());
											p.send(Bukkit.getOnlinePlayers());
											
											ompE.damageShield(ompE.getCurrentShield() * (0.1 * level));
										}
									}
									break;
								case STRENGTH:
									for(PotionEffect effect : pD.getActivePotionEffects()){
										if(effect.getType() == PotionEffectType.INCREASE_DAMAGE){
											damage *= 1.30 * (effect.getAmplifier() +1);
										}
									}
									
									if(r < (0.02 * level)){//2%, 4%, 6%
										pD.getWorld().playSound(pD.getLocation(), Sound.ZOMBIE_PIG_HURT, 1, 1);
										Particle p = new Particle(EnumParticle.LAVA, en.getLocation());
										p.setAmount(15);
										p.send(Bukkit.getOnlinePlayers());
										
										if(level == 3){
											pD.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 120, 1));
										}
										else{
											pD.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 120, 0));
										}
									}
									break;
							}
						}	
					}
					
					ompD.setInCombat();
					
					damage = damage * ompD.getSuit().getExtraDamage();
					
					if(ompE != null){
						ompE.setInCombat();
						
						if(ompE.getCurrentShield() != 0){
							if(ompE.getCurrentShield() - damage >= 0){
								damageToShield += damage;
								damage = 0;
							}
							else{
								damageToShield = ompE.getCurrentShield();
								damage -= damageToShield;
							}
						}
						
						ompE.damageShield(damageToShield);
						ompE.updateShieldBar();
					}
					else{
						if(en.getCustomName() != null && en.getCustomName().contains("§r")){
							new BukkitRunnable(){
								public void run(){
									if(!en.isDead()){
										double red = (((CraftLivingEntity) en).getHealth() / ((CraftLivingEntity) en).getMaxHealth() * 20) + 2;
										String bar = "§a||||||||||||||||||||";
										bar = bar.substring(0, (int) red) + "§c" + bar.substring((int) red);
										en.setCustomName(en.getCustomName().substring(0, en.getCustomName().indexOf("§r")) + "§r §8[" + bar + "§8]");
									}
								}
							}.runTaskLater(fog, 1);
						}
					}
					e.setDamage(damage);
				}
				else{
					e.setDamage(0D);
				}
			}
			else if(Pet.pets.contains(e.getDamager())){
				e.setCancelled(true);
			}
			else if(e.getEntity() instanceof Player){
				Player pE = (Player) e.getEntity();
				FoGPlayer ompE = FoGPlayer.getFoGPlayer(pE);
				
				ompE.setInCombat();

				double damage = e.getDamage();
				double damageToShield = 0;
				
				if(ompE.getCurrentShield() != 0){
					if(ompE.getCurrentShield() - damage >= 0){
						damageToShield += damage;
						damage = 0;
					}
					else{
						damageToShield = ompE.getCurrentShield();
						damage -= damageToShield;
					}
				}
				
				ompE.damageShield(damageToShield);
				ompE.updateShieldBar();
				e.setDamage(damage);
			}
			else{}
		}
	}
}
