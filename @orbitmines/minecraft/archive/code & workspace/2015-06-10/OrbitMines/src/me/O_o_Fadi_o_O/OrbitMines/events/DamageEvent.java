package me.O_o_Fadi_o_O.OrbitMines.events;

import java.util.ArrayList;
import java.util.List;

import me.O_o_Fadi_o_O.OrbitMines.Start;
import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.CreativeServer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.KitPvPServer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.ServerStorage;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;
import me.O_o_Fadi_o_O.OrbitMines.utils.creative.CreativePlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPUtils.ArmorType;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPUtils.ItemType;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPUtils.Mastery;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.Masteries;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Spider;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class DamageEvent implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDamage(EntityDamageByEntityEvent e){
		if(ServerData.isServer(Server.HUB, Server.MINIGAMES)){
			if(e.getEntity() instanceof Player && e.getDamager() instanceof Spider && !ServerStorage.pets.contains(e.getDamager())){
				final Player p = (Player) e.getEntity();
				
				p.playSound(p.getLocation(), Sound.SPIDER_IDLE, 5, 1);
				p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0));
				new BukkitRunnable(){
					public void run(){
						p.playSound(p.getLocation(), Sound.SPIDER_IDLE, 5, 1);
					} 
				}.runTaskLater(Start.getInstance(), 1);
				new BukkitRunnable(){
					public void run(){
						p.playSound(p.getLocation(), Sound.SPIDER_IDLE, 5, 1);
					} 
				}.runTaskLater(Start.getInstance(), 3);
				new BukkitRunnable(){
					public void run(){
						p.playSound(p.getLocation(), Sound.SPIDER_IDLE, 5, 1);
					} 
				}.runTaskLater(Start.getInstance(), 5);
			}
			
			if(e.getDamager() instanceof Snowball){
				Entity ent = e.getDamager();
				if(ServerStorage.snowgolemattackballs.contains(ent)){
					
					for(Entity en : ent.getNearbyEntities(0.5, 0.5, 0.5)){
						if(en instanceof Player){
							Player p = (Player) en;
							OMPlayer omp = OMPlayer.getOMPlayer(p);
							
							if(!omp.isInLapisParkour()){
								p.playSound(p.getLocation(), Sound.WITHER_IDLE, 5, 1);
								p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 300, 0));
								p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 300, 4));
							}
						}
					}
				}
			}
			
			if(e.getEntity() instanceof Player && e.getDamager() instanceof Player){
				ItemStack i = ((Player) e.getDamager()).getItemInHand();
				
				if(i != null && i.getType() == Material.LEASH && i.getItemMeta().getDisplayName().equals("§6§nStacker")){
					Player pE = (Player) e.getEntity();
					Player pD = (Player) e.getDamager();
					OMPlayer ompE = OMPlayer.getOMPlayer(pE);
					OMPlayer ompD = OMPlayer.getOMPlayer(pD);
					
					if(!ompE.isInLapisParkour() && !ompD.isInLapisParkour()){
						if(ompE.isLoaded() && ompD.isLoaded())
							if(ompE.hasStackerEnabled()){
								if(ompD.hasStackerEnabled()){
									if(ompE.hasPlayersEnabled()){
										pD.setPassenger(pE);
										
										pD.sendMessage("§7You've §6§lstacked§f " + ompE.getName() + "§7 on your Head!");
										pD.playEffect(pD.getLocation(), Effect.STEP_SOUND, 152);
										pE.sendMessage("§7" + ompD.getName() + " §6§lstacked§7 you on their Head!");
										e.setCancelled(true);
								}
								else{
									pD.sendMessage("§7This player has §c§lDISABLED §3§lPlayers§7!");
								}
							}
							else{
								pD.sendMessage("§7You §c§lDISABLED§6§l stacking§7! Enable it in your §c§nSettings§7!");
							}
						}
						else{
							pD.sendMessage("§7This player has §c§lDISABLED §6§lstacking§7!");
						}
					}
				}
			}
		}
		else if(ServerData.isServer(Server.KITPVP)){
			if(e.getDamager() instanceof Player){
				Player pD = (Player) e.getDamager();
				OMPlayer ompD = OMPlayer.getOMPlayer(pD);
				KitPvPPlayer kpD = ompD.getKitPvPPlayer();
				Masteries mD = kpD.getMasteries();
				
				if(kpD.getSummonedUndeath().contains(e.getEntity())){
					e.setCancelled(true);
				}
				
				if(kpD.isSpectator() || kpD.isPlayer() && kpD.getKitSelected() == null){
					e.setCancelled(true);
				}
				
				if(e.getEntity() instanceof Player){
					Player pE = (Player) e.getEntity();
					OMPlayer ompE = OMPlayer.getOMPlayer(pE);
					KitPvPPlayer kpE = ompE.getKitPvPPlayer();
					Masteries mE = kpE.getMasteries();
					
					if(kpD.getSummonedUndeath().size() > 0){
						for(Entity en : kpD.getSummonedUndeath()){
							((PigZombie) en).setTarget(pE);
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
							
							if(itemlore.contains(ArmorType.MOLTEN_ARMOR_I.getName())){
								ArmorType.MOLTEN_ARMOR_I.playEnchantment(ompD, ompE);
							}
							if(itemlore.contains(ArmorType.WITHER_ARMOR_I.getName())){
								ArmorType.WITHER_ARMOR_I.playEnchantment(ompD, ompE);
							}
						}
					}
					
					ItemStack item = pD.getItemInHand();
					if(item != null && item.getItemMeta() != null && item.getItemMeta().getLore() != null){
						List<String> itemlore = item.getItemMeta().getLore();
						
						for(ItemType type : ItemType.values()){
							if(itemlore.contains(type.getName())){
								type.playEnchantment(ompD, ompE, e.getDamage());
							}
						}
					}
				}
			}
			else if(e.getDamager() instanceof Projectile && e.getEntity() instanceof Player){
				KitPvPServer kitpvp = ServerData.getKitPvP();
				Projectile proj = (Projectile) e.getDamager();
				Player pE = (Player) e.getEntity();
				OMPlayer ompE = OMPlayer.getOMPlayer(pE);
				Masteries mE = ompE.getKitPvPPlayer().getMasteries();
				
				if(proj.getShooter() instanceof Player){
					Player pD = (Player) proj.getShooter();
					Masteries mD = OMPlayer.getOMPlayer(pD).getKitPvPPlayer().getMasteries();
					
					// Masteries \\
					double rangedamage = e.getDamage() * mD.getMasteryEffect(Mastery.RANGE);
					double rangeprotected = e.getDamage() * mE.getMasteryEffect(Mastery.RANGE_PROTECTION);
					e.setDamage(e.getDamage() + rangedamage + rangeprotected);
				}
				
				if(kitpvp.isProjectile(proj)){
					switch(kitpvp.getProjectileType(proj)){
						case EXPLOSIVE_I:				
							TNTPrimed tnt = (TNTPrimed) proj.getWorld().spawn(proj.getLocation(), TNTPrimed.class);
							tnt.setFuseTicks(10);
							break;
						case LIGHTNING_I:
							proj.getWorld().strikeLightning(proj.getLocation());
							break;
						case UNDEATH_I:
							final List<Entity> undeath1 = new ArrayList<Entity>();
							
							for(int i = 0; i < 3; i++){
								Zombie z = (Zombie) (proj.getWorld().spawnEntity(proj.getLocation(), EntityType.ZOMBIE));
								z.setCustomName("§4Undeath Knight");
								z.setCustomNameVisible(true);
				        		z.setTarget((LivingEntity) e.getEntity());
								undeath1.add(z);
							}
							
							new BukkitRunnable(){
								public void run(){
									for(Entity en : undeath1){
										if(!en.isDead()){
											en.getWorld().playEffect(en.getLocation(), Effect.STEP_SOUND, 152);
											en.remove();
										}
									}
								}
							}.runTaskLater(Start.getInstance(), 300);
							break;
						case UNDEATH_II:
							final List<Entity> undeath2 = new ArrayList<Entity>();
							
							for(int i = 0; i < 3; i++){
								Zombie z = (Zombie) (proj.getWorld().spawnEntity(proj.getLocation(), EntityType.ZOMBIE));
								z.setCustomName("§4Undeath Knight");
								z.setCustomNameVisible(true);
				        		z.setTarget((LivingEntity) e.getEntity());
								undeath2.add(z);
							}
							
							final Skeleton s = (Skeleton) (proj.getWorld().spawnEntity(proj.getLocation(), EntityType.SKELETON));
							s.setSkeletonType(SkeletonType.NORMAL);
							s.setCustomName("§4Undeath Archer");
							s.setCustomNameVisible(true);
			        		s.setTarget((LivingEntity) e.getEntity());
							undeath2.add(s);
							
							new BukkitRunnable(){
								public void run(){
									for(Entity en : undeath2){
										if(!en.isDead()){
											en.getWorld().playEffect(en.getLocation(), Effect.STEP_SOUND, 152);
											en.remove();
										}
									}
								}
							}.runTaskLater(Start.getInstance(), 300);
							break;
						case WITHER_I:
							ompE.addPotionEffect(PotionEffectType.WITHER, 5, 0);
							break;
						default:
							break;
					}
					
					kitpvp.removeProjectile(proj);
				}
			}
			else{}
		}
		else if(ServerData.isServer(Server.CREATIVE)){
			CreativeServer creative = ServerData.getCreative();

			if(e.getDamager() instanceof Player){
				Player pD = (Player) e.getDamager();
				OMPlayer ompD = OMPlayer.getOMPlayer(pD);
				CreativePlayer cpD = ompD.getCreativePlayer();
				
				if(pD.getWorld().getName().equals(creative.getPlotWorld().getName())){
					if(e.getEntity() instanceof Player){
						Player pE = (Player) e.getEntity();
						OMPlayer ompE = OMPlayer.getOMPlayer(pE);
						CreativePlayer cpE = ompE.getCreativePlayer();
						
						if(pD.getGameMode() != GameMode.SURVIVAL || pE.getGameMode() != GameMode.SURVIVAL || !cpD.isInPvPPlot() || !cpE.isInPvPPlot() || cpD.getPvPPlot().getPlotID() != cpE.getPvPPlot().getPlotID() || cpD.getSelectedKit() == null || cpE.getSelectedKit() == null){
							e.setCancelled(true);
						}
					}
					else{
						if(!ompD.isOpMode()){
							e.setCancelled(!cpD.isOnPlot(e.getEntity().getLocation()));
						}
					}
				}
				else if(pD.getWorld().getName().equals(creative.getCreativeWorld().getName())){
					if(!ompD.isOpMode()){
						e.setCancelled(true);
					}
				}
				else{}
			}
		}
		else{}
	}
}
