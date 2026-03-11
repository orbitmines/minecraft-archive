package me.O_o_Fadi_o_O.OrbitMines.managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import me.O_o_Fadi_o_O.OrbitMines.Start;
import me.O_o_Fadi_o_O.OrbitMines.NMS.NoAttackPigZombie;
import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.CreativeServer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.HubServer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.KitPvPServer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.ServerStorage;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.SkyBlockServer;
import me.O_o_Fadi_o_O.OrbitMines.utils.Title;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Cooldown;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.GameState;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.MindCraftColor;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.MiniGameType;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Pet;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.StaffRank;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Trail;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.VIPRank;
import me.O_o_Fadi_o_O.OrbitMines.utils.creative.CreativePlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.hub.MindCraftPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.hub.Inventories.CosmeticPerksInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.hub.Inventories.ServerSelectorInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.hub.Inventories.SettingsInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.hub.Inventories.TeleporterInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.Inventories.BoosterInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.Inventories.KitSelectorInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPUtils.ItemType;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPUtils.ProjectileType;
import me.O_o_Fadi_o_O.OrbitMines.utils.minigames.Arena;
import me.O_o_Fadi_o_O.OrbitMines.utils.minigames.CageBuilder;
import me.O_o_Fadi_o_O.OrbitMines.utils.minigames.Inventories.GameEffectsInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.minigames.Inventories.StatsInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.prison.Mine;
import me.O_o_Fadi_o_O.OrbitMines.utils.prison.PrisonPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.prison.Shop;
import me.O_o_Fadi_o_O.OrbitMines.utils.skyblock.SkyBlockPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.survival.Region;
import me.O_o_Fadi_o_O.OrbitMines.utils.survival.ShopSign;
import me.O_o_Fadi_o_O.OrbitMines.utils.survival.SurvivalUtils.ShopType;
import net.minecraft.server.v1_8_R3.AttributeInstance;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import om.api.handlers.Database;
import om.api.handlers.MGArena;
import om.api.handlers.Particle;
import om.api.utils.enums.cp.FireWork;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Ocelot.Type;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Spider;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.WitherSkull;
import org.bukkit.entity.Wolf;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class InteractManager {
	
	public void handleCageBuilder(){
		if(omp.hasPerms(StaffRank.Owner) && item.getType() == CageBuilder.getWand().getType() && item.getItemMeta().getDisplayName().equals(CageBuilder.getWand().getItemMeta().getDisplayName())){
			e.setCancelled(true);
			
			if(a == Action.LEFT_CLICK_BLOCK){
				if(omp.getCageBuilder() != null){
					omp.getCageBuilder().setL1(b.getLocation());
					p.sendMessage("§7Set §eLocation 1§7 to §e" + b.getLocation().getBlockX() + "§7, §e" + b.getLocation().getBlockY() + "§7, §e" + b.getLocation().getBlockZ() + "§7.");
				}
				else{
					p.sendMessage("§7You have to create a new Cage Builder first!");
				}
			}
			else if(a == Action.RIGHT_CLICK_BLOCK){
				if(omp.getCageBuilder() != null){
					omp.getCageBuilder().setL2(b.getLocation());
					p.sendMessage("§7Set §eLocation 2§7 to §e" + b.getLocation().getBlockX() + "§7, §e" + b.getLocation().getBlockY() + "§7, §e" + b.getLocation().getBlockZ() + "§7.");
				}
				else{
					p.sendMessage("§7You have to create a new Cage Builder first!");
				}
			}
			else{}
		}
	}
	
	public void handleMineSigns(){
		if((a == Action.RIGHT_CLICK_BLOCK || a == Action.LEFT_CLICK_BLOCK) && (b.getType() == Material.WALL_SIGN || b.getType() == Material.SIGN_POST) && p.getWorld().getName().equals(ServerData.getPrison().getPrisonWorld().getName())){
			for(Mine mine : Mine.getMines()){
				if(pp.hasPerms(mine.getRank())){
					if(Utils.equals(mine.getClockSign(), b.getLocation())){
						if(!omp.onCooldown(Cooldown.NPC_INTERACT)){
							pp.setClockEnabled(!pp.isClockEnabled());
						
							omp.resetCooldown(Cooldown.NPC_INTERACT);
						}
					}
				}
			}
		}
	}
	
	public void handleShops(){
		if(!omp.isOpMode() && p.getWorld().getName().equals(ServerData.getLobbyWorld().getName()) && b != null && (pp.getShop() == null || b.getType() == Material.TNT && item != null && item.getType() == Material.FLINT_AND_STEEL || item != null && (item.getType() == Material.WATER_BUCKET || item.getType() == Material.LAVA_BUCKET) || (b.getType() == Material.POWERED_RAIL || b.getType() == Material.DETECTOR_RAIL || b.getType() == Material.ACTIVATOR_RAIL || b.getType() == Material.RAILS) || (b.getType() == Material.CHEST || b.getType() == Material.ENDER_CHEST || b.getType() == Material.TRAPPED_CHEST || b.getType() == Material.WORKBENCH || b.getType() == Material.FURNACE || b.getType() == Material.ANVIL || b.getType() == Material.DROPPER || b.getType() == Material.HOPPER || b.getType() == Material.ENCHANTMENT_TABLE || b.getType() == Material.DISPENSER || b.getType() == Material.JUKEBOX || b.getType() == Material.BEACON) && !pp.getShop().isInShop(b.getLocation()))){
			e.setCancelled(true);
		}
	}
	
	public void handleShopSigns(){
		if(b != null && (b.getType() == Material.SIGN_POST || b.getType() == Material.WALL_SIGN)){
			Shop shop = Shop.getShop(b.getLocation());
			
			if(shop != null){
				if(pp.getShop() == null){
					if(shop.canRent()){
						if(!omp.onCooldown(Cooldown.MESSAGE)){
							if(pp.hasGold(5000)){
								p.sendMessage("§7This Shop is yours for 2 days!");
								
								shop.rent(omp);
								pp.removeGold(5000);
							}
							else{
								pp.requiredGold(5000);
							}
							
							omp.resetCooldown(Cooldown.MESSAGE);
						}
					}
				}
				else{
					if(shop.getShopID() == pp.getShop().getShopID()){
						if(!omp.onCooldown(Cooldown.MESSAGE)){
							if(pp.hasGold(5000)){
								p.sendMessage("§7You've added 2 days to your shop rental.");
								
								shop.addDays();
								pp.removeGold(5000);
							}
							else{
								pp.requiredGold(5000);
							}
							
							omp.resetCooldown(Cooldown.MESSAGE);
						}
					}
				}
			}
		}
	}
	
	public void handleFeatherAttack(){
		if(item.getType() == Material.FEATHER && item.getItemMeta().getDisplayName().equals("§f§lFeather Attack")){
			e.setCancelled(true);
			omp.updateInventory();
			
			if(!omp.onCooldown(Cooldown.FEATHER_ATTACK)){
				p.sendMessage("§7You used §f§lFeather Attack§7!");
				p.getWorld().playSound(p.getLocation(), Sound.CHICKEN_HURT, 5, 1);
				
				for(Entity en : p.getNearbyEntities(2, 2, 2)){
					if(en instanceof Player){
						Player player = (Player) en;
						OMPlayer omplayer = OMPlayer.getOMPlayer(player);
						
						if(!omplayer.getArena().isSpectator(omplayer)){
							Vector v = omplayer.getCFPlayer().getVelocity(player.getLocation().subtract(p.getLocation()).toVector().normalize().multiply(2)).add(new Vector(0, omplayer.getCFPlayer().getKnockbackMotifier() / 2, 0));
							player.setVelocity(v);
							player.damage(Utils.getRandom(2, 4), p);
						}
					}
				}
				
				for(int i = 0; i < 25; i++){
					final Item item = p.getWorld().dropItem(p.getLocation(), Utils.setDisplayname(new ItemStack(Material.FEATHER), "Feather " + p.getName() + i));
					item.setPickupDelay(Integer.MAX_VALUE);
					item.setVelocity(Utils.getRandomVelocity());
					
					new BukkitRunnable(){
						public void run(){
							item.remove();
						}
					}.runTaskLater(Start.getInstance(), 40);
				}
				
				omp.resetCooldown(Cooldown.FEATHER_ATTACK);
			}
		}
	}
	
	public void handleEggBomb(){
		if(item.getType() == Material.EGG && item.getItemMeta().getDisplayName().equals("§f§lEgg Bomb")){
			e.setCancelled(true);
			omp.updateInventory();
			
			if(!omp.onCooldown(Cooldown.EGG_BOMB)){
				p.sendMessage("§7You used §f§lEgg Bomb§7!");
				p.getWorld().playSound(p.getLocation(), Sound.CLICK, 5, 1);
				
				p.launchProjectile(Egg.class);
				
				omp.resetCooldown(Cooldown.EGG_BOMB);
			}
		}
	}
	
	public void handleFireShield(){
		if(item.getType() == Material.FIREBALL && item.getItemMeta().getDisplayName().equals("§f§lFire Shield")){
			e.setCancelled(true);
			omp.updateInventory();
			
			if(!omp.onCooldown(Cooldown.FIRE_SHIELD)){
				p.sendMessage("§7You used §f§lFire Shield§7!");
				p.getWorld().playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 5, 1);
				
				omp.parseCylinderTrail(Trail.MOB_SPAWNER);

			    for(Entity en : p.getNearbyEntities(2.5, 2.5, 2.5)){
			    	if(en instanceof Player){
			    		Player player = (Player) en;
			    		
			    		player.setFireTicks(80);
			    		player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 5, 1);
			    	}
			    }
				
				omp.resetCooldown(Cooldown.FIRE_SHIELD);
			}
		}
	}
	
	public void handleIronFist(){
		if(item.getType() == Material.IRON_INGOT && item.getItemMeta().getDisplayName().equals("§f§lIron Fist")){
			e.setCancelled(true);
			omp.updateInventory();
			
			if(!omp.onCooldown(Cooldown.IRON_FIST)){
				if(p.getHealth() > 2){
					p.sendMessage("§7You used §f§lIron Fist§7!");
					p.getWorld().playSound(p.getLocation(), Sound.ANVIL_LAND, 1, 0.1F);
					

				    for(Entity en : p.getNearbyEntities(2.5, 2.5, 2.5)){
				    	if(en instanceof Player){
				    		Player player = (Player) en;
				    		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 0));
				    		player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200, 2));
				    	}
				    }
					p.damage(2D);
					
					omp.resetCooldown(Cooldown.IRON_FIST);
				}
				else{
					if(!omp.onCooldown(Cooldown.MESSAGE)){
						p.sendMessage("§7You cannot use §f§lIron Fist§7! (It will kill you)");
						
						omp.resetCooldown(Cooldown.MESSAGE);
					}
				}
			}
		}
	}
	
	public void handleBackToHub(){
		if(item.getType() == Material.ENDER_PEARL && item.getItemMeta().getDisplayName().equals("§3§nBack to the Hub")){
			e.setCancelled(true);
			omp.updateInventory();
			
			if(omp.getArena() != null){
				omp.getArena().leave(omp);
			}
		}
	}
	
	public void handleGameEffects(){
		if(item.getType() == Material.BREWING_STAND_ITEM && item.getItemMeta().getDisplayName().equals("§e§nGame Effects")){
			e.setCancelled(true);
			omp.updateInventory();
			
			if(omp.getArena() != null){
				new GameEffectsInv(omp.getArena().getType()).open(p);
			}
		}
	}
	
	public void handleStats(){
		if(item.getType() == Material.SKULL_ITEM && item.getItemMeta().getDisplayName().equals("§2§nStats")){
			e.setCancelled(true);
			
			new StatsInv().open(p);
		}
	}
	
	public void handleChests(){
		Arena arena = omp.getArena();
		
		if(arena != null && (arena.getType() == MiniGameType.SURVIVAL_GAMES || arena.getType() == MiniGameType.SKYWARS) && arena.isPlayer(omp) && b != null && (b.getType() == Material.CHEST || b.getType() == Material.TRAPPED_CHEST)){
			Chest c = (Chest) b.getState();
			
			if(arena.getType() == MiniGameType.SURVIVAL_GAMES){
				if(!arena.getSG().getLootedChests().contains(c)){
					arena.getSG().randomChest(omp, c);
					arena.getSG().getLootedChests().add(c);
					
					for(Block b2 : Arrays.asList(b.getRelative(BlockFace.NORTH), b.getRelative(BlockFace.EAST), b.getRelative(BlockFace.SOUTH), b.getRelative(BlockFace.WEST))){
						if(b2 != null && b2.getType() == b.getType()){
							Chest c2 = (Chest) b2.getState();
							arena.getSG().getLootedChests().add(c2);
						}
					}
				}
			}
			else if(arena.getType() == MiniGameType.SKYWARS){
				if(!arena.getSW().getLootedChests().contains(c)){
					if(!arena.getSW().getPlacedChests().contains(b)){
						arena.getSW().randomChest(omp, c);
						arena.getSW().getLootedChests().add(c);
						
						for(Block b2 : Arrays.asList(b.getRelative(BlockFace.NORTH), b.getRelative(BlockFace.EAST), b.getRelative(BlockFace.SOUTH), b.getRelative(BlockFace.WEST))){
							if(b2 != null && b2.getType() == b.getType()){
								Chest c2 = (Chest) b2.getState();
								arena.getSW().getLootedChests().add(c2);
							}
						}
					}
				}
			}
			else{}
		}
	}

	public void handleMiniGameSigns(){
		if(b != null && (b.getType() == Material.SIGN_POST || b.getType() == Material.WALL_SIGN)){
			MGArena arena = MGArena.getMGArena(b.getLocation());
			
			if(arena != null){
				String arenaname = "§7§l" + arena.getType().getSignName() + " " + arena.getArenaID();
				if(arena.getState() != GameState.CLOSED){
					if(arena.getState() != GameState.ENDING && arena.getState() != GameState.RESTARTING){
						if((arena.getState() == GameState.WAITING || arena.getState() == GameState.STARTING) && arena.getPlayers() == arena.getType().getMaxPlayers()){
							p.sendMessage(arenaname + "§7 is §4§lfull§7!");
						}
						else{
							p.sendMessage("§7Joining " + arenaname + "§7...");
							omp.toMiniGame(arena);
						}
					}
					else{
						p.sendMessage(arenaname + "§7 is §8§lrestarting§7!");
					}
				}
				else{
					p.sendMessage(arenaname + "§7 is §4§lclosed§7!");
				}
			}
		}
	}
	
	public void handleIsland(){
		SkyBlockServer skyblock = ServerData.getSkyBlock();
		SkyBlockPlayer sbp = omp.getSkyBlockPlayer();
		
		if(p.getWorld().getName().equals(skyblock.getSkyblockWorld().getName()) || p.getWorld().getName().equals(skyblock.getSkyblockNetherWorld().getName())){
			if(sbp.hasIsland()){
				if(a != Action.LEFT_CLICK_AIR && a != Action.RIGHT_CLICK_AIR && !sbp.onIsland(e.getClickedBlock().getLocation(), true)){
					e.setCancelled(true);
				}
			}
			else{
				e.setCancelled(true);
			}
		}
		else if(p.getWorld().getName().equals(skyblock.getLobbyWorld().getName())){
			e.setCancelled(true);
		}
		else{}
	}
	
	@SuppressWarnings("deprecation")
	public void handleChestShops(){
		if(b != null && (b.getType() == Material.SIGN_POST || b.getType() == Material.WALL_SIGN)){
			ShopSign sign = ShopSign.getShopSign(b.getLocation());
			
			if(sign != null){
				if(ServerData.isServer(Server.SURVIVAL) && !omp.getSurvivalPlayer().getShopSigns().contains(sign) || ServerData.isServer(Server.PRISON) && !omp.getPrisonPlayer().getShopSigns().contains(sign)){
					if(a == Action.LEFT_CLICK_BLOCK){
						Chest chest = sign.getChest();
						Inventory inv = Bukkit.createInventory(null, chest.getInventory().getSize(), "§0§lChest Shop Viewer");
						inv.setContents(chest.getInventory().getContents());
						
						p.openInventory(inv);
					}
					else if(a == Action.RIGHT_CLICK_BLOCK){
						if(sign.getShopType() == ShopType.BUY){
							if(!sign.isSold()){
								if(ServerData.isServer(Server.SURVIVAL) && omp.getSurvivalPlayer().hasMoney(sign.getPrice()) || ServerData.isServer(Server.PRISON) && omp.getPrisonPlayer().hasGold(sign.getPrice())){
									if(Utils.getEmptySlots(p.getInventory()) >= Utils.getSlotsRequired(sign.getAmount(), Material.getMaterial(sign.getMaterialID()))){
										sign.buyItems(omp);
									}
									else{
										p.sendMessage("§7Your inventory is full!");
									}
								}
								else{
									p.sendMessage("§7You don't have enough money!");
								}
							}
							else{
								p.sendMessage("§7You can no longer buy here, it's §csold out§7!");
							}
						}
						else{
							if(sign.canSell()){
								if(ServerData.isServer(Server.SURVIVAL) && sign.hasMoney() || ServerData.isServer(Server.PRISON) && sign.hasGold()){
									int amount = 0;
									for(ItemStack item : p.getInventory().getContents()){
										if(item != null && item.getType() == Material.getMaterial(sign.getMaterialID()) && item.getDurability() == sign.getDurability()){
											amount += item.getAmount();
										}
									}
									
									if(amount >= sign.getAmount()){
										sign.sellItems(omp);
									}
									else{
										p.sendMessage("§7You don't have enough items!");
									}
								}
								else{
									p.sendMessage("§7The owner of that shop can no longer efford that payment!");
								}
							}
							else{
								p.sendMessage("§7You can no longer sell here, the chest is §cfull§7!");
							}
						}
					}
					else{}
				}
				else{
					if(a == Action.RIGHT_CLICK_BLOCK){
						p.sendMessage("§7You cannot use your own Chest Shop!");
					}
				}
			}
		}
	}
	
	public void handleClaimHoe(){
		if(item != null && item.getType() == Material.STONE_HOE && p.getWorld().getName().equals(ServerData.getLobbyWorld().getName())){
			e.setCancelled(true);
			omp.updateInventory();
		}
	}
	
	public void handleSpawnInteract(){
		if(!omp.isOpMode() && p.getWorld().getName().equals(ServerData.getLobbyWorld().getName()) && e.getClickedBlock() != null){
			Material m = e.getClickedBlock().getType();
			
			if(item != null && (item.getType() == Material.WATER_BUCKET || item.getType() == Material.LAVA_BUCKET) || (m == Material.TRAP_DOOR || m == Material.CHEST || m == Material.TRAPPED_CHEST || m == Material.WORKBENCH || m == Material.FURNACE || m == Material.ENDER_CHEST || m == Material.POWERED_RAIL || m == Material.DETECTOR_RAIL || m == Material.ACTIVATOR_RAIL || m == Material.RAILS)){
				e.setCancelled(true);
				omp.updateInventory();
			}
		}
	}
	
	public void handleRegions(){
		if(e.getClickedBlock() != null && a != Action.RIGHT_CLICK_AIR && a != Action.LEFT_CLICK_AIR && p.getWorld().getName().equals(ServerData.getSurvival().getSurvivalWorld().getName()) && Region.isInRegion(omp, e.getClickedBlock().getLocation())){
			e.setCancelled(true);
			omp.updateInventory();
		}
	}
	
	public void handlePlotInteract(){
		CreativeServer creative = ServerData.getCreative();
		CreativePlayer cp = omp.getCreativePlayer();
		
		if(!omp.isOpMode()){
			if(p.getWorld().getName().equals(creative.getPlotWorld().getName()) && !cp.isInPvPPlot()){
				Location l = null;
				
				if(item != null && (item.getType() == Material.WATER_BUCKET || item.getType() == Material.LAVA_BUCKET)){
					if(a == Action.RIGHT_CLICK_BLOCK){
						l = e.getClickedBlock().getLocation();
					}
					else if(a == Action.LEFT_CLICK_BLOCK){
						l = e.getClickedBlock().getLocation();
					}
					else if(a == Action.PHYSICAL){
						l = e.getClickedBlock().getLocation();
					}
					else{
						l = p.getLocation();
					}
				}
				else if(item != null && item.getType() == Material.EXPLOSIVE_MINECART){
					e.setCancelled(true);
					omp.updateInventory();
				}
				else{
					l = p.getLocation();
				}
				
				if(l != null && !cp.isOnPlot(l)){
					e.setCancelled(true);
				}
			}
			else if(p.getWorld().getName().equals(creative.getCreativeWorld().getName())){
				e.setCancelled(true);
				omp.updateInventory();
			}
			else{}
		}
	}
		
	public void handleBackToLobby(){
		if(item.getType() == Material.ENDER_PEARL && item.getItemMeta().getDisplayName().equals("§3§nBack to the Lobby")){
			e.setCancelled(true);
			omp.updateInventory();
			
			if(kp.isSpectator()){
				KitPvPServer kitpvp = ServerData.getKitPvP();
				
				kp.setPlayer();
				p.teleport(kitpvp.getSpawn());
				omp.clearInventory();
				kitpvp.giveLobbyItems(omp);
				p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 5, 1);
				p.setFlying(false);
				p.setAllowFlight(false);
				
				omp.show();
			}
		}
	}
	
	public void handleKitSelector(){
		if(item.getType() == Material.DIAMOND_CHESTPLATE && item.getItemMeta().getDisplayName().equals("§b§nKit Selector")){
			e.setCancelled(true);
			omp.updateInventory();
			
			new KitSelectorInv().open(p);
		}
	}
	
	public void handleBoosters(){
		if(item.getType() == Material.GOLD_NUGGET && item.getItemMeta().getDisplayName().equals("§a§nBoosters")){
			e.setCancelled(true);
			omp.updateInventory();
			
			new BoosterInv().open(p);
		}
	}
	
	public void handleSpectator(){
		if(ServerData.isServer(Server.KITPVP)){
			if(kp.isSpectator() && !omp.isOpMode()){
				e.setCancelled(true);
			}
		}
		else if(ServerData.isServer(Server.MINIGAMES)){
			if(omp.getArena() != null && omp.getArena().isSpectator(omp) && !omp.isOpMode()){
				e.setCancelled(true);
			}
		}
		else{}
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
	public void handleSettings(){
		if(item.getType() == Material.REDSTONE_TORCH_ON && item.getItemMeta().getDisplayName().equals("§c§nSettings")){
			e.setCancelled(true);
			omp.updateInventory();
			
			if(!omp.isInLapisParkour()){
				new SettingsInv(p).open(p);
				p.playEffect(p.getLocation(), Effect.STEP_SOUND, 152);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void handleFly(){
		if(item.getType() == Material.FEATHER && item.getItemMeta().getDisplayName().equals("§f§nFly")){
			if(omp.hasPerms(VIPRank.Iron_VIP)){
				if(!omp.isInLapisParkour()){
					p.playEffect(p.getLocation(), Effect.STEP_SOUND, 80);
		    		p.setAllowFlight(!p.getAllowFlight());
		    		p.setFlying(p.getAllowFlight());
					p.sendMessage("§f§l§oFly " + Utils.statusString(p.getAllowFlight()));
				}
			}
			else{
				omp.requiredVIPRank(VIPRank.Iron_VIP);
			}
		}
	}
		
	public void handleAchievements(){
		if(item.getType() == Material.EXP_BOTTLE && item.getItemMeta().getDisplayName().equals("§d§nAchievements")){
			e.setCancelled(true);
			omp.updateInventory();
			
			p.sendMessage("§a§oComing Soon...");
			// TODO ADD ACHIEVEMENTS \\
		}
	}
	
	public void handleKickSign(){
		if(b != null && b.getType() == Material.WALL_SIGN){
			if(b.getLocation().getBlockX() == -18 && b.getLocation().getBlockZ() == 2){
				p.kickPlayer("§7Well that's unfortunate. :)");
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void handleMindCraft(){
		if(omp.isInMindCraft()){
			final MindCraftPlayer mcp = omp.getMCPlayer();
			HubServer hub = ServerData.getHub();
			
			if(item != null && item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null){
				if(item.getType() == Material.TNT && item.getItemMeta().getDisplayName().equals("§4§lReset Colors")){
					e.setCancelled(true);
					omp.updateInventory();
					
					p.playSound(p.getLocation(), Sound.EXPLODE, 5, 1);
					
					List<String> blocksfromturn = mcp.getBlocksFromTurns();
					blocksfromturn.set(0, "0|0|0|0");
					
					List<Block> blocks = hub.getMCBlocksForTurn().get(0);
					
					for(Block bl : blocks){
						p.sendBlockChange(bl.getLocation(), Material.WOOL, (byte) 0);
					}
				}
				if(item.getType() == Material.REDSTONE_TORCH_ON && item.getItemMeta().getDisplayName().equals("§c§lEnd Turn")){
					e.setCancelled(true);
					omp.updateInventory();
					
					List<String> blocksfromturn = Utils.asNewStringList(mcp.getBlocksFromTurns());
					
					String newturnnext = blocksfromturn.get(0);
					if(!newturnnext.contains("0")){
						List<String> blockstatusfromturn = Utils.asNewStringList(mcp.getStatusFromTurns());
						
						int currentturn = mcp.getCurrentTurn();
						String newturn = "0|0|0|0";
						
						blocksfromturn.set(0, newturn);
						blocksfromturn.set(currentturn, newturnnext);
						
						mcp.setBlocksFromTurns(blocksfromturn);
						
						String[] correctturn = mcp.getCorrectTurn().split("\\|");
						String[] thisturn = newturnnext.split("\\|");
						
						List<String> correctturnints = Arrays.asList(correctturn);
						List<String> thisturnints = Arrays.asList(thisturn);
						
						String status = "";
						int correct = 0;
						int otherplace = 0;
						int incorrect = 0;
						
						for(int iR = 3; iR > -1; iR--){
							if(correctturnints.get(iR).equals(thisturnints.get(iR))){
								correct++;
								correctturnints.set(iR, "0");
							}
						}
						for(int iR = 3; iR > -1; iR--){
							if(correctturnints.contains(thisturnints.get(iR))){
								otherplace++;
								correctturnints.set(iR, "0");
							}
							else{
								if(!correctturnints.get(iR).equals("0")){
									incorrect++;
								}
							}
						}
						
						if(correct + otherplace + incorrect == 5){
							otherplace--;
						}
						
						if(correct != 0){
							for(int iR = 0; iR < correct; iR++){
								status += "|" + "5";
							}
						}
						if(otherplace != 0){
							for(int iR = 0; iR < otherplace; iR++){
								status += "|" + "4";
							}
						}
						if(incorrect != 0){
							for(int iR = 0; iR < incorrect; iR++){
								status += "|" + "0";
							}
						}
						
						status = status.substring(1);
						
						blockstatusfromturn.set(currentturn, status);
						mcp.setStatusFromTurns(blockstatusfromturn);
						
						mcp.setCurrentTurn(currentturn +1);
						
						List<Block> blocks = hub.getMCBlocksForTurn().get(0);
						List<Block> blocks2 = hub.getMCBlocksForTurn().get(currentturn);
						
						for(Block bl : blocks){
							p.sendBlockChange(bl.getLocation(), Material.WOOL, (byte) 0);
						}
						for(Block bl : blocks2){
							p.sendBlockChange(bl.getLocation(), Material.WOOL, (byte) Integer.parseInt(thisturn[blocks2.indexOf(bl)]));
						}
						
						if(mcp.getCorrectTurn().equals(newturnnext)){
							p.getInventory().clear();
							p.sendMessage("§c§lMindCraft §8| §7You guessed the color combination!");
							p.sendMessage("§c§lMindCraft §8| §c§l+1 Win");
							p.playSound(p.getLocation(), Sound.LEVEL_UP, 5, 1);
							
							blocksfromturn.set(11, mcp.getCorrectTurn());
							mcp.setBlocksFromTurns(blocksfromturn);
							
							mcp.addWin();
							
							if(mcp.getBestGame() != -1){
								if(currentturn < mcp.getBestGame()){
									mcp.setBestGame(currentturn);
								}
							}
							else{
								Database.get().insert("MasterMind-BestGame", "uuid`, `turns", omp.getUUID().toString() + "', '" + currentturn);
							}
							
							FireWork fw = new FireWork(p.getLocation());
							fw.randomize();
							fw.build();
							
							Title t = new Title("§c§lMindCraft", "§7You won! §c+1 Win");
							t.send(p);
							
							p.getInventory().clear();
							omp.updateInventory();
							
							new BukkitRunnable(){
								public void run(){
									mcp.reset();
								}
							}.runTaskLater(Start.getInstance(), 100);
						}
						else if(currentturn == 10){
							blocksfromturn.set(11, mcp.getCorrectTurn());
							mcp.setBlocksFromTurns(blocksfromturn);
							
							p.sendMessage("§c§lMindCraft §8| §7You §cLost§7! Try again!");
							
							Title t = new Title("§c§lMindCraft", "§7You Lost! Try again!");
							t.send(p);
							
							p.getInventory().clear();
							omp.updateInventory();
							
							new BukkitRunnable(){
								public void run(){
									mcp.reset();
								}
							}.runTaskLater(Start.getInstance(), 100);
						}
						else{
							p.playSound(p.getLocation(), Sound.CLICK, 5, 1);
							p.sendMessage("§c§lMindCraft §8| §7Correct: §a" + correct + " §7Other Place: §e" + otherplace + " §7Incorrect: §c" + incorrect);
						}
					}
					else{
						p.sendMessage("§c§lMindCraft §8| §7You didn't use all color slots!");
					}
				}
			}
			if(a == Action.RIGHT_CLICK_BLOCK){
				List<Block> blocks = hub.getMCBlocksForTurn().get(0);
				if(blocks.contains(b)){
					if(item != null && item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null){
						for(final MindCraftColor color : MindCraftColor.values()){
							if(item.getType() == Material.WOOL && item.getItemMeta().getDisplayName().equals(color.getName())){
								e.setCancelled(true);
								omp.updateInventory();
								
								List<String> blocksfromturn = mcp.getBlocksFromTurns();
								String turn = blocksfromturn.get(0);
								String[] turnblocks = turn.split("\\|");
								turnblocks[blocks.indexOf(b)] = "" + color.getData();
								String newturn = "";
								for(String s : turnblocks){
									newturn += "|" + s;
								}
								newturn = newturn.substring(1);
								blocksfromturn.set(0, newturn);
								
								mcp.setBlocksFromTurns(blocksfromturn);
								
								new BukkitRunnable(){
									public void run(){
										p.sendBlockChange(b.getLocation(), Material.WOOL, color.getData());
									}
								}.runTaskLater(Start.getInstance(), 1);
							}
						}
					}
				}
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
						ItemStack item = Utils.setDisplayname(new ItemStack(Material.REDSTONE), "§b§lNecromancer §a§lLvL 3§8 || §cSoul");
						
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
					if(!omp.onCooldown(Cooldown.HEALING_I)){
						omp.addPotionEffect(PotionEffectType.REGENERATION, 4, 2);
						
						omp.resetCooldown(Cooldown.HEALING_I);
					}
				}
				else if(lore.contains(ItemType.HEALING_II.getName())){
					if(!omp.onCooldown(Cooldown.HEALING_II)){
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
						final KitPvPPlayer kp = omp.getKitPvPPlayer();
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
						    ee.setItemInHand(Utils.addEnchantment(new ItemStack(Material.GOLD_SWORD), Enchantment.DURABILITY, 20));
						    ee.setHelmet(Utils.addColor(new ItemStack(Material.LEATHER_HELMET), org.bukkit.Color.FUCHSIA));
						    ee.setChestplate(Utils.addColor(new ItemStack(Material.LEATHER_CHESTPLATE), org.bukkit.Color.FUCHSIA));
						    ee.setLeggings(Utils.addColor(new ItemStack(Material.LEATHER_LEGGINGS), org.bukkit.Color.FUCHSIA));
						    ee.setBoots(Utils.addColor(new ItemStack(Material.LEATHER_BOOTS), org.bukkit.Color.FUCHSIA));
						    ((PigZombie) e.getBukkitEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2000000, 0));
						    kp.getSummonedUndeath().add(e.getBukkitEntity());
						    
						    new BukkitRunnable(){
						    	public void run(){
						    		kp.getSummonedUndeath().remove(e.getBukkitEntity());
									if(!e.getBukkitEntity().isDead()){
										e.getBukkitEntity().getWorld().playEffect(e.getBukkitEntity().getLocation(), Effect.STEP_SOUND, 152);
										e.getBukkitEntity().remove();
									}
						    	}
						    }.runTaskLater(Start.getInstance(), 300);
						}
					    
						omp.resetCooldown(Cooldown.UNDEATH_SUMMON_I);
					}
				}
				else if(lore.contains(ItemType.PAINTBALLS_I.getName())){
					e.setCancelled(true);
					omp.updateInventory();
					
					if(!omp.onCooldown(Cooldown.PAINTBALLS_I)){
						KitPvPServer kitpvp = ServerData.getKitPvP();
						
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
							items[index] = Utils.addColor(item, color);
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
		}.runTaskLater(Start.getInstance(), 100);
	}
}
