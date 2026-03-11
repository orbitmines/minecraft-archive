package om.kitpvp.managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.minecraft.server.v1_8_R3.EnumParticle;
import om.api.handlers.Particle;
import om.api.handlers.players.OMPlayer;
import om.api.managers.InteractManager;
import om.api.nms.npc.NoAttackPigZombie;
import om.api.utils.ItemUtils;
import om.api.utils.enums.Cooldown;
import om.kitpvp.KitPvP;
import om.kitpvp.handlers.players.KitPvPPlayer;
import om.kitpvp.invs.BoosterInv;
import om.kitpvp.invs.KitSelectorInv;
import om.kitpvp.utils.enums.ItemType;
import om.kitpvp.utils.enums.ProjectileType;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class KitPvPInteractManager extends InteractManager {

	private KitPvP kitpvp;
	private KitPvPPlayer omp;
	
	public KitPvPInteractManager(PlayerInteractEvent e) {
		super(e);
		
		this.kitpvp = KitPvP.getInstance();
		this.omp = KitPvPPlayer.getKitPvPPlayer(p);
	}
	
	public boolean handleTeleporter(){
		if(item.getType() == Material.NAME_TAG && item.getItemMeta().getDisplayName().equals("§e§nTeleporter")){
			e.setCancelled(true);
			omp.updateInventory();
			
			kitpvp.getTeleporterInv().open(p);
			
			return true;
		}
		return false;
	}

	public boolean handleBackToLobby(){
		if(item.getType() == Material.ENDER_PEARL && item.getItemMeta().getDisplayName().equals("§3§nBack to the Lobby")){
			e.setCancelled(true);
			omp.updateInventory();
			
			if(omp.isSpectator()){
				omp.setPlayer();
				p.teleport(kitpvp.getSpawn());
				omp.clearInventory();
				kitpvp.giveLobbyKit(omp);
				p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 5, 1);
				p.setFlying(false);
				p.setAllowFlight(false);
				
				omp.show();
			}
			
			return true;
		}
		return false;
	}
	
	public boolean handleKitSelector(){
		if(item.getType() == Material.DIAMOND_CHESTPLATE && item.getItemMeta().getDisplayName().equals("§b§nKit Selector")){
			e.setCancelled(true);
			omp.updateInventory();
			
			new KitSelectorInv().open(p);
			
			return true;
		}
		return false;
	}
	
	public boolean handleBoosters(){
		if(item.getType() == Material.GOLD_NUGGET && item.getItemMeta().getDisplayName().equals("§a§nBoosters")){
			e.setCancelled(true);
			omp.updateInventory();
			
			new BoosterInv().open(p);
			
			return true;
		}
		return false;
	}
	
	public void handleSpectator(){
		if(omp.isSpectator() && !omp.isOpMode()){
			e.setCancelled(true);
		}
	}
	
	public void handlePhysicalAction(){
		if(!omp.isOpMode()){
			if(a == Action.PHYSICAL && (b == null || b.getType() != Material.STONE_PLATE && b.getType() != Material.WOOD_PLATE)){
				e.setCancelled(true);
			}
		}
	}
	
	public void handleSnowball(){
		if(item.getType() == Material.SNOW_BALL){
			if(a == Action.RIGHT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK){
				e.setCancelled(true);
				omp.updateInventory();
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void handleKitPvPEnchantments(){
		if(item.getItemMeta().getLore() != null){
			List<String> lore = item.getItemMeta().getLore();
			
			if(a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK){
				if(lore.contains(ItemType.FIRE_SPELL_I.getName())){
					if(!omp.onCooldown(Cooldown.FIRE_SPELL_I)){
						Vector velocity = p.getLocation().getDirection().multiply(1.1);
						double speed = velocity.length();
						Vector direction = new Vector(velocity.getX() / speed, velocity.getY() / speed, velocity.getZ() / speed);
						double spray = 5D;
						
						for(int i = 0; i < 3; i++){
							FallingBlock block = p.getWorld().spawnFallingBlock(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() +1, p.getLocation().getZ()), Material.FIRE, (byte) 0);
							
						  	block.setVelocity(new Vector(direction.getX() + (Math.random() - 1.5) / spray, direction.getY() + (Math.random() - 1.5) / spray, direction.getZ() + (Math.random() - 1.5) / spray).normalize().multiply(speed));
							block.setDropItem(false);
						}
						
						omp.resetCooldown(Cooldown.FIRE_SPELL_I);
					}
				}
				else if(lore.contains(ItemType.FIRE_SPELL_II.getName())){
					if(!omp.onCooldown(Cooldown.FIRE_SPELL_II)){
						Vector velocity = p.getLocation().getDirection().multiply(1.1);
						double speed = velocity.length();
						Vector direction = new Vector(velocity.getX() / speed, velocity.getY() / speed, velocity.getZ() / speed);
						double spray = 5D;
						
						for(int i = 0; i < 8; i++){
							FallingBlock block = p.getWorld().spawnFallingBlock(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() +1, p.getLocation().getZ()), Material.FIRE, (byte) 0);
							
						  	block.setVelocity(new Vector(direction.getX() + (Math.random() - 1.5) / spray, direction.getY() + (Math.random() - 1.5) / spray, direction.getZ() + (Math.random() - 1.5) / spray).normalize().multiply(speed));
							block.setDropItem(false);
						}

						omp.resetCooldown(Cooldown.FIRE_SPELL_II);
					}
				}
				else if(lore.contains(ItemType.POTION_LAUNCHER_I.getName())){
					if(!omp.onCooldown(Cooldown.POTION_LAUNCHER_I)){
						ItemStack item = new ItemStack(Material.POTION, 1);
						Potion po = new Potion(PotionType.INSTANT_DAMAGE, 1);
						po.setSplash(true);
						po.apply(item);
						
						ThrownPotion sp = p.launchProjectile(ThrownPotion.class);
						sp.setItem(item);
						sp.setVelocity(p.getLocation().getDirection().multiply(2));
						
						omp.resetCooldown(Cooldown.POTION_LAUNCHER_I);
					}
				}
				else if(lore.contains(ItemType.WITHER_I.getName())){
					if(!omp.onCooldown(Cooldown.WITHER_I)){
						ItemStack item = ItemUtils.setDisplayname(new ItemStack(Material.REDSTONE), "§b§lNecromancer §a§lLvL 3§8 || §cSoul");
						
						if(p.getInventory().containsAtLeast(item, 1)){
							Vector velocity = p.getEyeLocation().getDirection().multiply(1);
							double speed = velocity.length();
							Vector direction = new Vector(velocity.getX() / speed, velocity.getY() / speed, velocity.getZ() / speed);
							double spray = 3.5D;
							 
							for(int i = 0; i < 3; i++){
								WitherSkull ws = p.launchProjectile(WitherSkull.class);
								ws.setVelocity(new Vector(direction.getX() + (Math.random() - 0.5) / spray, direction.getY() + (Math.random() - 0.5) / spray, direction.getZ() + (Math.random() - 0.5) / spray).normalize().multiply(speed));
							}

							p.playSound(p.getLocation(), Sound.WITHER_SHOOT, 4, 2);
							p.getInventory().removeItem(item);
							omp.updateInventory();
							
							omp.resetCooldown(Cooldown.WITHER_I);
						}
					}
				}
				else if(lore.contains(ItemType.HEALING_I.getName())){
					if(p.isSneaking() && !omp.onCooldown(Cooldown.HEALING_I)){
						omp.addPotionEffect(PotionEffectType.REGENERATION, 4, 2);
						
						omp.resetCooldown(Cooldown.HEALING_I);
					}
				}
				else if(lore.contains(ItemType.HEALING_II.getName())){
					if(p.isSneaking() && !omp.onCooldown(Cooldown.HEALING_II)){
						omp.addPotionEffect(PotionEffectType.REGENERATION, 5, 2);
						
						omp.resetCooldown(Cooldown.HEALING_II);
					}
				}
				else if(lore.contains(ItemType.BARRIER_I.getName())){
					if(!omp.onCooldown(Cooldown.BARRIER_I)){
						createBarrier(p);
						omp.addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 5, 0);

						omp.resetCooldown(Cooldown.BARRIER_I);
					}
				}
				else if(lore.contains(ItemType.BARRIER_II.getName())){
					if(!omp.onCooldown(Cooldown.BARRIER_II)){
						createBarrier(p);
						omp.addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 5, 1);

						omp.resetCooldown(Cooldown.BARRIER_II);
					}
				}
				else if(lore.contains(ItemType.TNT_I.getName())){
					if(!omp.onCooldown(Cooldown.TNT_I)){
						TNTPrimed tnt = (TNTPrimed) p.getWorld().spawn(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() +1, p.getLocation().getZ()), TNTPrimed.class);
						tnt.setFuseTicks(15);
						tnt.setVelocity(p.getEyeLocation().getDirection().multiply(2));
						
						omp.resetCooldown(Cooldown.TNT_I);
					}
				}
				else if(lore.contains(ItemType.HEALING_KIT_I.getName())){
					p.getInventory().removeItem(item);
					p.setHealth(20);
				}
				else if(lore.contains(ItemType.MAGIC_I.getName())){
					if(!omp.onCooldown(Cooldown.MAGIC_I)){
						p.playSound(p.getLocation(), Sound.WITHER_SHOOT, 4, 2);
						for(Entity en : p.getNearbyEntities(5, 5, 5)){
							if(en instanceof Player){
								Player p2 = (Player) en;
								OMPlayer omp2 = OMPlayer.getOMPlayer(p2);
								omp2.addPotionEffect(PotionEffectType.WITHER, 8, 1);
								p2.playSound(p2.getLocation(), Sound.WITHER_SHOOT, 4, 2);
							}
						}
						
						omp.resetCooldown(Cooldown.MAGIC_I);
					}
				}
				else if(lore.contains(ItemType.FISH_ATTACK_I.getName())){
					if(!omp.onCooldown(Cooldown.FISH_ATTACK_I)){
						p.playSound(p.getLocation(), Sound.AMBIENCE_RAIN, 4, 4);
						for(Entity en : p.getNearbyEntities(4, 4, 4)){
							if(en instanceof Player){
								Player p2 = (Player) en;
								OMPlayer omp2 = OMPlayer.getOMPlayer(p2);
								omp2.addPotionEffect(PotionEffectType.POISON, 4, 2);
								p2.playSound(p2.getLocation(), Sound.AMBIENCE_RAIN, 4, 4);
							}
						}
						
						Particle pa = new Particle(EnumParticle.WATER_SPLASH, p.getLocation());
						pa.setSize(2, 2, 2);
						pa.setAmount(50);
						pa.send(Bukkit.getOnlinePlayers());

						omp.resetCooldown(Cooldown.FISH_ATTACK_I);
					}
				}
				else if(lore.contains(ItemType.SHIELD_I.getName())){
					if(!omp.onCooldown(Cooldown.SHIELD_I)){
						p.playSound(p.getLocation(), Sound.ANVIL_LAND, 4, 1);
						omp.addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 10, 0);

						omp.resetCooldown(Cooldown.SHIELD_I);
					}
				}
				else if(lore.contains(ItemType.SHIELD_II.getName())){
					if(!omp.onCooldown(Cooldown.SHIELD_II)){
						p.playSound(p.getLocation(), Sound.ANVIL_LAND, 4, 1);
						omp.addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 12, 1);

						omp.resetCooldown(Cooldown.SHIELD_II);
					}
				}
				else if(lore.contains(ItemType.BLOCK_EXPLOSION_I.getName())){
					if(!omp.onCooldown(Cooldown.BLOCK_EXPLOSION_I)){
						FallingBlock fb = p.getWorld().spawnFallingBlock(p.getLocation().add(0, 1, 0), item.getType(), (byte) item.getDurability());
			            fb.setDropItem(false);
			            fb.setCustomName("BlockExplosion");
			            fb.setVelocity(p.getLocation().getDirection().multiply(0.8));

						omp.resetCooldown(Cooldown.BLOCK_EXPLOSION_I);
					}
				}
				else if(lore.contains(ItemType.UNDEATH_SUMMON_I.getName())){
					if(!omp.onCooldown(Cooldown.UNDEATH_SUMMON_I)){
						Location l = p.getLocation();

					    net.minecraft.server.v1_8_R3.World nmsWorld = ((CraftWorld) l.getWorld()).getHandle();
					    final NoAttackPigZombie e1 = new NoAttackPigZombie(nmsWorld);
					    e1.setPositionRotation(l.getX(), l.getY() +2, l.getZ() +2, l.getYaw(), l.getPitch());
					    final NoAttackPigZombie e2 = new NoAttackPigZombie(nmsWorld);
					    e2.setPositionRotation(l.getX(), l.getY() +2, l.getZ() -2, l.getYaw(), l.getPitch());
						for(final NoAttackPigZombie e : Arrays.asList(e1, e2)){
						    nmsWorld.addEntity(e);
							e.setBaby(true);
						    e.setCustomName("§4Undeath Baby");
						    e.setCustomNameVisible(true);
						    EntityEquipment ee = ((LivingEntity) e.getBukkitEntity()).getEquipment();
						    ee.setItemInHand(ItemUtils.addEnchantment(new ItemStack(Material.GOLD_SWORD), Enchantment.DURABILITY, 20));
						    ee.setHelmet(ItemUtils.addColor(new ItemStack(Material.LEATHER_HELMET), org.bukkit.Color.FUCHSIA));
						    ee.setChestplate(ItemUtils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), org.bukkit.Color.FUCHSIA));
						    ee.setLeggings(ItemUtils.addColor(new ItemStack(Material.LEATHER_LEGGINGS), org.bukkit.Color.FUCHSIA));
						    ee.setBoots(ItemUtils.addColor(new ItemStack(Material.LEATHER_BOOTS), org.bukkit.Color.FUCHSIA));
						    ((PigZombie) e.getBukkitEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2000000, 0));
						    omp.getSummonedUndeath().add(e.getBukkitEntity());
						    
						    new BukkitRunnable(){
						    	public void run(){
						    		omp.getSummonedUndeath().remove(e.getBukkitEntity());
									if(!e.getBukkitEntity().isDead()){
										e.getBukkitEntity().getWorld().playEffect(e.getBukkitEntity().getLocation(), Effect.STEP_SOUND, 152);
										e.getBukkitEntity().remove();
									}
						    	}
						    }.runTaskLater(kitpvp, 300);
						}
					    
						omp.resetCooldown(Cooldown.UNDEATH_SUMMON_I);
					}
				}
				else if(lore.contains(ItemType.PAINTBALLS_I.getName())){
					e.setCancelled(true);
					omp.updateInventory();
					
					if(!omp.onCooldown(Cooldown.PAINTBALLS_I)){
						EnderPearl e = (EnderPearl) p.launchProjectile(EnderPearl.class);
						e.setCustomName(item.getItemMeta().getDisplayName());

						kitpvp.addProjectile((Projectile) e, ProjectileType.PAINTBALLS_I);
						
						omp.resetCooldown(Cooldown.PAINTBALLS_I);
					}
				}
				else{}
			}
			else if(a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK){
				if(lore.contains(ItemType.PAINTBALLS_I.getName())){
					int itemindex = p.getInventory().first(item);
					
					String name = item.getItemMeta().getDisplayName();
					org.bukkit.Color color = org.bukkit.Color.RED;
					if(name.endsWith("Red §8|| §bWeapon§f")){
						name = name.replace("§c§lRed", "§b§lBlue");
						color = org.bukkit.Color.AQUA;
					}
					else if(name.endsWith("Blue §8|| §bWeapon§f")){
						name = name.replace("§b§lBlue", "§0§lBlack");
						color = org.bukkit.Color.BLACK;
					}
					else if(name.endsWith("Black §8|| §bWeapon§f")){
						name = name.replace("§0§lBlack", "§7§lGray");
						color = org.bukkit.Color.SILVER;
					}
					else if(name.endsWith("Gray §8|| §bWeapon§f")){
						name = name.replace("§7§lGray", "§a§lGreen");
						color = org.bukkit.Color.LIME;
					}
					else if(name.endsWith("Green §8|| §bWeapon§f")){
						name = name.replace("§a§lGreen", "§c§lRed");
						color = org.bukkit.Color.RED;
					}
					else{}
					ItemMeta meta = item.getItemMeta();
					meta.setDisplayName(name);
					item.setItemMeta(meta);
					p.getInventory().setItem(itemindex, item);
					
					int index = 0;
					ItemStack[] items = new ItemStack[4];
					for(ItemStack item : p.getInventory().getArmorContents()){
						if(item != null){
							items[index] = ItemUtils.addColor(item, color);
						}
						
						index++;
					}
					p.getInventory().setArmorContents(items);
					
					p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
				}
			}
		}
	}
	
	private void createBarrier(Player p){
		World w = p.getWorld();
		Location l = p.getLocation();
		int x = l.getBlockX();
		int y = l.getBlockY();
		int z = l.getBlockZ();
		
		Block b1 = w.getBlockAt(x, y -2, z);
		Block b2 = w.getBlockAt(x + 1, y -2, z);
		Block b3 = w.getBlockAt(x - 1, y -2, z);
		Block b4 = w.getBlockAt(x, y -2, z - 1);
		Block b5 = w.getBlockAt(x + 1, y -2, z - 1);
		Block b6 = w.getBlockAt(x - 1, y -2, z - 1);
		Block b7 = w.getBlockAt(x, y -2, z + 1);
		Block b8 = w.getBlockAt(x + 1, y -2, z + 1);
		Block b9 = w.getBlockAt(x - 1, y -2, z + 1);
		Block b10 = w.getBlockAt(x, y +3, z);
		Block b11 = w.getBlockAt(x + 1, y +3, z);
		Block b12 = w.getBlockAt(x - 1, y +3, z);
		Block b13 = w.getBlockAt(x, y +3, z - 1);
		Block b14 = w.getBlockAt(x + 1, y +3, z - 1);
		Block b15 = w.getBlockAt(x - 1, y +3, z - 1);
		Block b16 = w.getBlockAt(x, y +3, z + 1);
		Block b17 = w.getBlockAt(x + 1, y +3, z + 1);
		Block b18 = w.getBlockAt(x - 1, y +3, z + 1);
		Block b19 = w.getBlockAt(x, y -1, z + 2);
		Block b20 = w.getBlockAt(x + 1, y -1, z + 2);
		Block b21 = w.getBlockAt(x - 1, y -1, z + 2);
		Block b22 = w.getBlockAt(x - 2, y -1, z);
		Block b23 = w.getBlockAt(x - 2, y -1, z + 1);
		Block b24 = w.getBlockAt(x - 2, y -1, z - 1);
		Block b25 = w.getBlockAt(x, y -1, z - 2);
		Block b26 = w.getBlockAt(x + 1, y -1, z - 2);
		Block b27 = w.getBlockAt(x - 1, y -1, z - 2);
		Block b28 = w.getBlockAt(x + 2, y -1, z);
		Block b29 = w.getBlockAt(x + 2, y -1, z + 1);
		Block b30 = w.getBlockAt(x + 2, y -1, z - 1);
		Block b31 = w.getBlockAt(x, y +2, z + 2);
		Block b32 = w.getBlockAt(x + 1, y +2, z + 2);
		Block b33 = w.getBlockAt(x - 1, y +2, z + 2);
		Block b34 = w.getBlockAt(x - 2, y +2, z);
		Block b35 = w.getBlockAt(x - 2, y +2, z + 1);
		Block b36 = w.getBlockAt(x - 2, y +2, z - 1);
		Block b37 = w.getBlockAt(x, y +2, z - 2);
		Block b38 = w.getBlockAt(x + 1, y +2, z - 2);
		Block b39 = w.getBlockAt(x - 1, y +2, z - 2);
		Block b40 = w.getBlockAt(x + 2, y +2, z);
		Block b41 = w.getBlockAt(x + 2, y +2, z + 1);
		Block b42 = w.getBlockAt(x + 2, y +2, z - 1);
		Block b43 = w.getBlockAt(x - 2, y, z + 2);
		Block b44 = w.getBlockAt(x - 2, y + 1, z + 2);
		Block b45 = w.getBlockAt(x - 2, y, z - 2);
		Block b46 = w.getBlockAt(x - 2, y + 1, z - 2);
		Block b47 = w.getBlockAt(x + 2, y, z + 2);
		Block b48 = w.getBlockAt(x + 2, y + 1, z + 2);
		Block b49 = w.getBlockAt(x + 2, y, z - 2);
		Block b50 = w.getBlockAt(x + 2, y + 1, z - 2);
		Block b51 = w.getBlockAt(x, y, z + 3);
		Block b52 = w.getBlockAt(x + 1, y, z + 3);
		Block b53 = w.getBlockAt(x - 1, y, z + 3);
		Block b54 = w.getBlockAt(x, y + 1, z + 3);
		Block b55 = w.getBlockAt(x + 1, y + 1, z + 3);
		Block b56 = w.getBlockAt(x - 1, y + 1, z + 3);
		Block b57 = w.getBlockAt(x, y, z - 3);
		Block b58 = w.getBlockAt(x + 1, y, z - 3);
		Block b59 = w.getBlockAt(x - 1, y, z - 3);
		Block b60 = w.getBlockAt(x, y + 1, z - 3);
		Block b61 = w.getBlockAt(x + 1, y + 1, z - 3);
		Block b62 = w.getBlockAt(x - 1, y + 1, z - 3);
		Block b63 = w.getBlockAt(x + 3, y, z);
		Block b64 = w.getBlockAt(x + 3, y, z - 1);
		Block b65 = w.getBlockAt(x + 3, y, z + 1);
		Block b66 = w.getBlockAt(x + 3, y + 1, z);
		Block b67 = w.getBlockAt(x + 3, y + 1, z - 1);
		Block b68 = w.getBlockAt(x + 3, y + 1, z + 1);
		Block b69 = w.getBlockAt(x - 3, y, z);
		Block b70 = w.getBlockAt(x - 3, y, z - 1);
		Block b71 = w.getBlockAt(x - 3, y, z + 1);
		Block b72 = w.getBlockAt(x - 3, y + 1, z);
		Block b73 = w.getBlockAt(x - 3, y + 1, z - 1);
		Block b74 = w.getBlockAt(x - 3, y + 1, z + 1);
		
		final HashMap<Block, Material> materials = new HashMap<Block, Material>();
		List<Block> blocks = Arrays.asList(b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18, b19, b20, b21, b22, b23, b24, b25, b26, b27, b28, b29, b30, b31, b32, b33, b34, b35, b36, b37, b38, b39, b40, b41, b42, b43, b44, b45, b46, b47, b48, b49, b50, b51, b52, b53, b54, b55, b56, b57, b58, b59, b60, b61, b62, b63, b64, b65, b66, b67, b68, b69, b70, b71, b72, b73, b74);
		final List<Block> emptyblocks = new ArrayList<Block>();
		
		for(Block b : blocks){
			if(b.isEmpty()){
				materials.put(b, b.getType());
				emptyblocks.add(b);
				
				if(new Random().nextBoolean()){
					b.setType(Material.LEAVES);
				}
				else{
					b.setType(Material.LOG);
				}
			}
		}
		
		new BukkitRunnable(){
			public void run(){
				for(Block b : emptyblocks){
					b.setType(materials.get(b));
				}
			}
		}.runTaskLater(kitpvp, 100);
	}
}
