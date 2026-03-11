package me.O_o_Fadi_o_O.OrbitMines.runnables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import me.O_o_Fadi_o_O.OrbitMines.Start;
import me.O_o_Fadi_o_O.OrbitMines.managers.BossBarManager;
import me.O_o_Fadi_o_O.OrbitMines.managers.ScoreboardManager;
import me.O_o_Fadi_o_O.OrbitMines.managers.VoteManager;
import me.O_o_Fadi_o_O.OrbitMines.utils.Database;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.ConfirmInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.ServerSelectorInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.Inventories.WardrobeInv;
import me.O_o_Fadi_o_O.OrbitMines.utils.NPC;
import me.O_o_Fadi_o_O.OrbitMines.utils.NPCArmorStand;
import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.Particle;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.HubServer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.ServerStorage;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.NPCType;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.ReflectionUtil;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.StaffRank;
import me.O_o_Fadi_o_O.OrbitMines.utils.hub.MindCraftPlayer;
import net.minecraft.server.v1_8_R2.EnumParticle;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Snowman;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;

public class Runnables {

	public void start(Plugin plugin){
		new DatabaseRunnable().runTaskAsynchronously(plugin);
		new MindCraftNPCRunnable().runTaskTimer(plugin, 0, 10);
		new NPCRunnable().runTaskTimer(plugin, 0, 100);
		new VoteRunnable().runTaskTimer(plugin, 100, 72000);
		new VoteRunnable().runTaskTimer(plugin, 100, 72000);
		new PlayerRunnable().runTaskTimer(plugin, 0, 20);
		new ScoreboardRunnable().runTaskTimer(plugin, 0, 5);
		new SGARunnable().runTaskTimer(plugin, 0, 3);
		new EntityRunnable().runTaskTimer(plugin, 40, 1);
	}
	
	public class DatabaseRunnable extends BukkitRunnable {
		
		@Override
		public void run(){//Asynchronously
			Database.get().openConnection();
		}
	}
	
	public class MindCraftNPCRunnable extends BukkitRunnable {
		
		@Override
		public void run(){//0, 10
			NPC npc = NPC.getNPC(NPCType.MINDCRAFT);
			
			if(npc != null){
			    ItemStack i = ((LivingEntity) npc.getEntity()).getEquipment().getItemInHand();
			    i.setDurability((short) (int) Arrays.asList(1, 3, 4, 5, 11, 14).get(new Random().nextInt(6)));
			    ((LivingEntity) npc.getEntity()).getEquipment().setItemInHand(i);
			}
		}
	}
	
	public class NPCRunnable extends BukkitRunnable	{
		
		@Override
		public void run(){//0, 100
			for(NPCArmorStand npc : NPCArmorStand.getNPCArmorStands()){
				ArmorStand as = npc.getArmorStand();
				
				if(npc.getNPCType() == NPCType.TOP_DONATOR){
					ItemStack item = as.getHelmet();
					SkullMeta meta = (SkullMeta) item.getItemMeta();
					String lastdonatorstring = ServerData.getHub().getLastDonatorString();
					
					if(lastdonatorstring == null){
						meta.setOwner(null);
						item.setDurability((short) 0); 
					}
					else{
						item.setDurability((short) 3); 
						meta.setOwner(lastdonatorstring);
						as.setCustomName("§7Recent Donator: §6§l" + lastdonatorstring);
						as.setCustomNameVisible(true);
					}
					item.setItemMeta(meta);
					as.setHelmet(item);
				}
				else if(npc.getNPCType() == NPCType.SERVER_INFO_CREATIVE){
					Server.CREATIVE.updateNPC(as);
				}
				else if(npc.getNPCType() == NPCType.SERVER_INFO_KITPVP){
					Server.KITPVP.updateNPC(as);
				}
				else if(npc.getNPCType() == NPCType.SERVER_INFO_MINIGAMES){
					Server.MINIGAMES.updateNPC(as);
				}
				else if(npc.getNPCType() == NPCType.SERVER_INFO_PRISON){
					Server.PRISON.updateNPC(as);
				}
				else if(npc.getNPCType() == NPCType.SERVER_INFO_SKYBLOCK){
					Server.SKYBLOCK.updateNPC(as);
				}
				else if(npc.getNPCType() == NPCType.SERVER_INFO_SURVIVAL){
					Server.SURVIVAL.updateNPC(as);
				}
				else{}
			}
		}
	}
	
	public class VoteRunnable extends BukkitRunnable {
		
		@Override
		public void run(){//100, 72000
			
			HashMap<String, Integer> uuidvoters = Database.get().getIntEntries("Votes", "uuid", "votes");
			HashMap<String, Integer> playervoters = new HashMap<String, Integer>();
			
			int votes1 = 0;
			int votes2 = 0;
			int votes3 = 0;
			int votes4 = 0;
			int votes5 = 0;
			
			String uuid1 = null;
			String uuid2 = null;
			String uuid3 = null;
			String uuid4 = null;
			String uuid5 = null;
			
			for(String uuid : uuidvoters.keySet()){
				int votes = uuidvoters.get(uuid);
				if(votes >= votes1){
					votes5 = votes4;
					votes4 = votes3;
					votes3 = votes2;
					votes2 = votes1;
					votes1 = votes;

					uuid5 = uuid4;
					uuid4 = uuid3;
					uuid3 = uuid2;
					uuid2 = uuid1;
					uuid1 = uuid;
					
				}
				else if(votes >= votes2){
					votes5 = votes4;
					votes4 = votes3;
					votes3 = votes2;
					votes2 = votes;

					uuid5 = uuid4;
					uuid4 = uuid3;
					uuid3 = uuid2;
					uuid2 = uuid;
				}
				else if(votes >= votes3){
					votes5 = votes4;
					votes4 = votes3;
					votes3 = votes;

					uuid5 = uuid4;
					uuid4 = uuid3;
					uuid3 = uuid;
				}
				else if(votes >= votes4){
					votes5 = votes4;
					votes4 = votes;

					uuid5 = uuid4;
					uuid4 = uuid;
				}
				else if(votes >= votes5){
					votes5 = votes;

					uuid5 = uuid;
				}else{}
			}
			
			uuidvoters.clear();
			uuidvoters.put(uuid1, votes1);
			uuidvoters.put(uuid2, votes2);
			uuidvoters.put(uuid3, votes3);
			uuidvoters.put(uuid4, votes4);
			uuidvoters.put(uuid5, votes5);
			
			for(String uuidstring : uuidvoters.keySet()){
				String playername = Utils.getName(UUID.fromString(uuidstring));
				
				if(playername != null){
					playervoters.put(playername, uuidvoters.get(uuidstring));
				}
			}
			
			ServerStorage.voters = playervoters;
		}
	}
	
	public class PlayerRunnable extends BukkitRunnable {
		
		@SuppressWarnings("deprecation")
		@Override
		public void run(){//0, 20
			BossBarManager.i++;
			
			new VoteManager().updateTop();
			
			for(Player p : Bukkit.getOnlinePlayers()){
				OMPlayer omp = OMPlayer.getOMPlayer(p);
				omp.setScoreboard();
				omp.checkLastLocation();
				
				if(omp.isLoaded()){
					World lobby = ServerData.getLobbyWorld();
					
					if(ServerData.isServer(Server.HUB, Server.MINIGAMES, Server.CREATIVE)){
						if(omp.isWardrobeDisco()){
							omp.discoWardrobe();
						}
						if(p.getOpenInventory().getTopInventory().getName() != null){
							if(p.getOpenInventory().getTopInventory().getName().equals("§0§lWardrobe")){
								WardrobeInv.setDiscoItem(p.getOpenInventory().getTopInventory(), omp);
							}
							if(p.getOpenInventory().getTopInventory().getName().equals("§0§lConfirm your Purchase") && p.getOpenInventory().getTopInventory().getItem(13).getItemMeta().getDisplayName().endsWith("Disco Armor")){
								ConfirmInv.setDiscoItem(p.getOpenInventory().getTopInventory(), omp);
							}
						}
					}
					
					if(ServerData.isServer(Server.HUB, Server.MINIGAMES)){
						/*
						 * Players Disabled
						 */
						if(!omp.hasPlayersEnabled()){
							for(Player player : Bukkit.getOnlinePlayers()){
								p.hidePlayer(player);
							}
						}
						
						/*
						 * Pet Rename GUI
						 */
						if(p.getOpenInventory().getTopInventory() instanceof AnvilInventory){
							{
								ItemStack item = new ItemStack(Material.ENDER_CHEST, 1);
								ItemMeta meta = item.getItemMeta();
								meta.setDisplayName("§9§nCosmetic Perks");
								item.setItemMeta(meta);
								p.getInventory().setItem(12, item);
							}
							{
								ItemStack item = new ItemStack(Material.NAME_TAG, 1);
								ItemMeta meta = item.getItemMeta();
								meta.setDisplayName("§f§oClick the §6§oRight§f§o Egg to rename your Pet!");
								item.setItemMeta(meta);
								p.getInventory().setItem(14, item);
							}
						}
						else{
							p.getInventory().setItem(12, null);
							p.getInventory().setItem(14, null);
						}
					
						/*
						 * Pet Inventories
						 */
						if(ServerData.isServer(Server.HUB, Server.MINIGAMES)){
							if(omp.getPet() != null){
								if(p.getVehicle() != null && p.getVehicle() == omp.getPet()){
									omp.givePetInventory();
								}
								else{
									if(!omp.isOpMode()){
										if(ServerData.isServer(Server.HUB)){
											ServerData.getHub().giveLobbyItems(omp);
										}
									}
								}
							}
							else{
								if(!omp.isOpMode()){
									if(ServerData.isServer(Server.HUB) && !p.getWorld().getName().equals(ServerData.getHub().getBuilderWorld().getName())){
										ServerData.getHub().giveLobbyItems(omp);
									}
								}
							}
						}
						
						/*
						 * SnowGolemAttack
						 */
						if(omp.getSGASeconds() != -1){
							int seconds = omp.getSGASeconds();
							omp.setSGASeconds(seconds +1);
							
							if(omp.getSGAItem() != null && omp.getSGASnowGolems() == null){
								Location l = omp.getSGAItem().getLocation();
								
								if(seconds <= 10){
									l.getWorld().playSound(l, Sound.WITHER_SPAWN, 5, 1);
									Particle pa = new Particle(EnumParticle.FLAME, l);
									pa.setSize(1, 1, 1);
									pa.setAmount(30);
									pa.send(Bukkit.getOnlinePlayers());
								}
								if(seconds == 11){
									l.getWorld().playSound(l, Sound.WITHER_DEATH, 5, 1);
									Snowman s1 = (Snowman) l.getWorld().spawnEntity(l, EntityType.SNOWMAN);
									Snowman s2 = (Snowman) l.getWorld().spawnEntity(l, EntityType.SNOWMAN);
									Snowman s3 = (Snowman) l.getWorld().spawnEntity(l, EntityType.SNOWMAN);
									
									s1.setPassenger(s2);
									s2.setPassenger(s3);
									omp.getSGAItem().remove();
									omp.setSGAItem(null);
									
									List<Entity> snowgolems = new ArrayList<Entity>();
									snowgolems.add(s1);
									snowgolems.add(s2);
									snowgolems.add(s3);
									omp.setSGASnowGolems(snowgolems);
								}
							}
						}
					}
					
					if(lobby != null && ServerData.isServer(Server.HUB)){
						HubServer hub = ServerData.getHub();
						
						/*
						 * ServerPortals
						 */
						if(!p.getWorld().getName().equals(hub.getBuilderWorld().getName())){
							if(!p.getWorld().getName().equals(lobby.getName())){
								p.teleport(hub.getSpawn());
							}
							else{
								if(p.getLocation().getY() <= 50 || p.getLocation().distance(hub.getSpawn()) >= 100){
									p.teleport(hub.getSpawn());
								}
							}
							for(Server server : Server.values()){
								if(hub.getServerPortals().containsKey(server)){
									for(Block b : hub.getServerPortals().get(server)){
										if(b.isEmpty()){
											if(server != Server.SURVIVAL && server != Server.SKYBLOCK){
												p.sendBlockChange(b.getLocation(), Material.PORTAL, (byte) 2);
											}
											else{
												p.sendBlockChange(b.getLocation(), Material.PORTAL, (byte) 1);
											}
										}
									}
								}
							}
						}
						
						/*
						 * LapisParkour
						 */
						if(omp.isInLapisParkour()){
							if(lobby.getName().equals(p.getWorld().getName())){
								if(p.getLocation().distance(hub.getLapisParkour()) > 2){
									Location l = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() -1, p.getLocation().getZ());
	
									 Material m = p.getWorld().getBlockAt(l).getType();
									
									if(m != Material.GLOWSTONE){
										if(!p.isSprinting()){
											p.teleport(hub.getLapisParkour());
											p.sendMessage("§1§lLapis Parkour §8| §7You can't stop sprinting!");
										}
										else if(p.isOnGround()){
											 if(m == Material.GLOWSTONE){
												if(!omp.CompletedLapisParkour()){
													 omp.setCompletedLapisParkour(true);
													 
													 p.sendMessage("§1§lLapis Parkour §8| §7You received §b§l250 VIP Points§7!");
													 
													 p.teleport(hub.getLapisParkour());
													 omp.setInLapisParkour(false);
													 
													 omp.addVIPPoints(250);
													 Database.get().insert("ParkourCompleted", "uuid", p.getUniqueId().toString());
													 Database.get().update("VIPPoints", "points", "" + omp.getVIPPoints(), "uuid", p.getUniqueId().toString());
												 }
												 
												 Bukkit.broadcastMessage("§1§lLapis Parkour §8| §6" + p.getName() + "§7 completed the §1Lapis Parkour§7!");
												 for(Player player : Bukkit.getOnlinePlayers()){
													 player.playSound(player.getLocation(), Sound.LEVEL_UP, 5, 1);
												 }
											 }
											 else{
												 if(m != Material.LAPIS_BLOCK && m != Material.AIR){
													 p.teleport(hub.getLapisParkour());
												 }
											 }
										}
										else if(p.getLocation().getY() <= 74){
											p.teleport(hub.getLapisParkour());
										}
										else{}
									}
									else{
										if(!omp.CompletedLapisParkour()){
											 omp.setCompletedLapisParkour(true);
											 
											 p.sendMessage("§1§lLapis Parkour §8| §7You received §b§l250 VIP Points§7!");
											 
											 p.teleport(hub.getLapisParkour());
											 omp.setInLapisParkour(false);

											 omp.addVIPPoints(250);
											 Database.get().insert("ParkourCompleted", "uuid", p.getUniqueId().toString());
											 Database.get().update("VIPPoints", "points", "" + omp.getVIPPoints(), "uuid", p.getUniqueId().toString());
										 }
										 
										 Bukkit.broadcastMessage("§1§lLapis Parkour §8| " + omp.getName() + "§7 completed the §1Lapis Parkour§7!");
										 for(Player player : Bukkit.getOnlinePlayers()){
											 player.playSound(player.getLocation(), Sound.LEVEL_UP, 5, 1);
										 }
									}
								}
							}
							else{
								 p.teleport(hub.getLapisParkour());
							}
						
							if(p.getAllowFlight() == true){
								p.setAllowFlight(false);
								p.setFlying(false);
								p.sendMessage("§1§lLapis Parkour §8| §f§l§oFly §c§lDISABLED");
							}
						}
						
						/*
						 * MindCraft
						 */
						if(lobby.getName().equals(p.getWorld().getName())){
							Location winsign = hub.getMCWinsSign();
							if(p.getLocation().distance(winsign) < 16){
								MindCraftPlayer mcp = omp.getMCPlayer();
								String[] sign = new String[4];
								sign[0] = "";
								sign[1] = "§lWins";
								if(mcp != null){
									sign[2] = "" + mcp.getWins();
								}
								else{
									sign[2] = "Loading...";
								}
								sign[3] = "";
								p.sendSignChange(winsign, sign);
							}
							Location bestgamesign = hub.getMCBestGameSign();
							if(p.getLocation().distance(bestgamesign) < 16){
								MindCraftPlayer mcp = omp.getMCPlayer();
								String[] sign = new String[4];
								sign[0] = "";
								sign[1] = "§lBest Game";
								if(mcp != null && mcp.getBestGame() != 0){
									if(mcp.getBestGame() != 1){
										sign[2] = "" + mcp.getBestGame() + " Turns";
									}
									else{
										sign[2] = "" + mcp.getBestGame() + " Turn";
									}
								}
								else{
									sign[2] = "None";
								}
								sign[3] = "";
								p.sendSignChange(bestgamesign, sign);
							}
						}
					
						if(omp.isInMindCraft()){
							MindCraftPlayer mcp = omp.getMCPlayer();
							
							for(int i = 0; i <= 11; i++){
								List<Block> blocks = hub.getMCBlocksForTurn().get(i);
								
								List<String> blocksfromturns = mcp.getBlocksFromTurns();
								String turn = blocksfromturns.get(i);
								String[] turnblocks = turn.split("\\|");
								
								for(Block b : blocks){
									int bint = Integer.parseInt(turnblocks[blocks.indexOf(b)]);
									p.sendBlockChange(b.getLocation(), Material.WOOL, (byte) bint);
								}
								
								if(i != 0 && i != 11){
									List<Block> blocks2 = hub.getMCBlocksForTurnStatus().get(i);
									
									List<String> blockstatusfromturns = mcp.getStatusFromTurns();
									String status = blockstatusfromturns.get(i);
									String[] statusblocks = status.split("\\|");
									
									for(Block b : blocks2){
										int data = Integer.parseInt(statusblocks[blocks2.indexOf(b)]);
										
										p.sendBlockChange(b.getLocation(), Material.STAINED_GLASS, (byte) data);
									}
								}
							}
						}
						else{
							for(int i = 0; i <= 11; i++){
								List<Block> blocks = hub.getMCBlocksForTurn().get(i);
								
								for(Block b : blocks){
									p.sendBlockChange(b.getLocation(), Material.WOOL, (byte) 0);
								}
								
								if(i != 0 && i != 11){
									List<Block> blocks2 = hub.getMCBlocksForTurnStatus().get(i);
									for(Block b : blocks2){
										p.sendBlockChange(b.getLocation(), Material.STAINED_GLASS, (byte) 0);
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	public class ScoreboardRunnable extends BukkitRunnable {
		
		@Override
		public void run(){//0, 5
			ScoreboardManager.setNextTitle();
			
			for(Player p : Bukkit.getOnlinePlayers()){
				if(p.getScoreboard() != null && p.getScoreboard().getObjective(DisplaySlot.SIDEBAR) != null){
					p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(ScoreboardManager.title);
				}
			}
		}
	}
	
	public class SGARunnable extends BukkitRunnable {
		
		@SuppressWarnings("deprecation")
		@Override
		public void run(){//0, 3
			if(ServerData.isServer(Server.HUB)){
				ServerData.getHub().updateWaterfalls();
			}
			
			for(Player p : Bukkit.getOnlinePlayers()){
				OMPlayer omp = OMPlayer.getOMPlayer(p);
				
				if(omp.isLoaded()){
					if(ServerData.isServer(Server.HUB, Server.MINIGAMES)){
						if(omp.getPet() != null){
							Entity pet = omp.getPet();
							
							if(omp.hasPetSheepDisco()){
								Random r = new Random();
								int rInt = r.nextInt(15);
								
								Sheep s = (Sheep) pet;
								DyeColor c = DyeColor.getByDyeData((byte) rInt);
								s.setColor(c);
							}
							if(omp.hasPetShroomTrail()){
								for(int i = 0; i <= 5; i++){
									ItemStack item = Utils.setDisplayname(new ItemStack(Material.RED_MUSHROOM, 1), p.getName() + i);
									final Item iEn = pet.getWorld().dropItem(pet.getLocation(), item);
						            iEn.setVelocity(pet.getLocation().getDirection().multiply(-1).add(Utils.getRandomVelocity()));
						            
									new BukkitRunnable(){
										@Override
										public void run() {
											iEn.remove();
										}
									}.runTaskLater(Start.getInstance(), 60);
								}
							}
							if(omp.hasPetBabyPigs()){
								List<Entity> list = omp.getPetBabyPigEntities();
								
								LivingEntity pig1 = (LivingEntity) list.get(0);
								LivingEntity pig2 = (LivingEntity) list.get(1);
								
								Location enL = pet.getLocation();
								Location pig1L = pig1.getLocation();
								Location pig2L = pig2.getLocation();
								
								if(getDistance(enL, pig1L) < 7 && getDistance(enL, pig1L) > 1){
									navigate(pig1, enL, 1.2);
								}
								else{
									pig1.teleport(enL);
								}
								if(getDistance(pig1L, pig2L) < 7 && getDistance(pig1L, pig2L) > 1){
									navigate(pig2, pig1L, 1.2);
								}
								else{
									pig2.teleport(pig1L);
								}
							}
						}
					}
					
					if(omp.getSGASeconds() != -1 && omp.getSGASnowGolems() != null){
						int seconds = omp.getSGASeconds();
						
						List<Entity> snowgolems = omp.getSGASnowGolems();
						
						List<Player> players = new ArrayList<Player>();
						for(Player player : Bukkit.getOnlinePlayers()){
							players.add(player);
						}
						
						if(seconds >= 12){
							Entity en = snowgolems.get(2);
							Player player = players.get(new Random().nextInt(players.size()));
							
							Location l1 = en.getLocation();
							Location l2 = player.getLocation();
							 
							Snowball s = ((Snowman) en).launchProjectile(Snowball.class, l2.toVector().subtract(l1.toVector()).multiply(0.15));
							en.getWorld().playSound(l1, Sound.DIG_SNOW, 2, 1);
							ServerStorage.snowgolemattackballs.add(s);
						}
						
						if(seconds == 50){
							Location l = snowgolems.get(1).getLocation();
							l.getWorld().playSound(l, Sound.WITHER_DEATH, 5, 1);
							
							Particle pa = new Particle(EnumParticle.FLAME, l);
							pa.setSize(2, 2, 2);
							pa.setAmount(30);
							pa.send(Bukkit.getOnlinePlayers());
							
							for(Entity en : snowgolems){
								en.remove();
							}
							
							omp.setSGASeconds(-1);
							omp.setSGAItem(null);
							omp.setSGASnowGolems(null);
						}
					}
				}
			}
		}
	}
	
	public class EntityRunnable extends BukkitRunnable {
		
		@Override
		public void run(){//40, 1
			
			/*
			 * ServerSelector
			 */
			ServerStorage.serverselectori++;
			
			ServerSelectorInv.get().update();
			
			if(ServerStorage.serverselectori == 18){
				ServerStorage.serverselectori = 0;
			}
			
			{
				for(NPCArmorStand npc : NPCArmorStand.getNPCArmorStands()){
				    npc.checkEntity();
				}
			}
			
			for(NPC npc : NPC.getNPCs()){
				npc.checkEntity();
			}
			
			for(Player player : Bukkit.getOnlinePlayers()){
				OMPlayer omplayer = OMPlayer.getOMPlayer(player);
				
				if(omplayer.isLoaded()){
					if(omplayer != null && player.isOp() && omplayer.getStaffRank() != StaffRank.Owner && !omplayer.isOpMode()){
						player.setOp(false);
					}
					
					omplayer.playTrail();
					omplayer.updateCooldownActionBar();
	
					Entity petentity = omplayer.getPet();
					if(petentity != null && petentity.getWorld().getName().equals(player.getWorld().getName())){
						if(petentity instanceof LivingEntity){
							LivingEntity pet = (LivingEntity) petentity;
							
							Location l = player.getLocation();
							
							if(getDistance(l, pet.getLocation()) < 20){
								navigate(pet, l, 1.2);
							}
							else{
								pet.teleport(l);
							}
						}
					}
				}
			}
			
			if(ServerData.isServer(Server.HUB)){
				HubServer hub = ServerData.getHub();
				hub.updateWaterfalls();
			}
			
			World lobby = ServerData.getLobbyWorld();
			
			if(lobby != null && ServerData.isServer(Server.HUB, Server.MINIGAMES)){
				for(Entity en : lobby.getEntities()){
					if(ServerStorage.fireballs.contains(en) || ServerStorage.soccermagmacubes.contains(en)){
						Particle p = new Particle(EnumParticle.FLAME, en.getLocation());
						p.send(Bukkit.getOnlinePlayers());
					}
					else if(ServerStorage.eggbombs.contains(en)){
						Particle p = new Particle(EnumParticle.FIREWORKS_SPARK, en.getLocation());
						p.send(Bukkit.getOnlinePlayers());
					}
					else if(ServerStorage.silverfishbombs.contains(en)){
						if(en.isOnGround()){
							Location l = en.getLocation();
							l.getWorld().playSound(l, Sound.EXPLODE, 5, 1);
							l.getWorld().playEffect(l, Effect.EXPLOSION_HUGE, 4);
							Location l2 = new Location(l.getWorld(), l.getX() +1, l.getY(), l.getZ() +0);
							Location l3 = new Location(l.getWorld(), l.getX() +0, l.getY(), l.getZ() +1);
							Location l4 = new Location(l.getWorld(), l.getX() -1, l.getY(), l.getZ() +0);
							Location l5 = new Location(l.getWorld(), l.getX() +0, l.getY(), l.getZ() -1);
							
							for(Location lo : Arrays.asList(l, l2, l3, l4, l5)){
								final Silverfish s = (Silverfish) en.getWorld().spawnEntity(lo, EntityType.SILVERFISH);
								new BukkitRunnable(){
									@Override
									public void run() {
										s.remove();
									}
								}.runTaskLater(Start.getInstance(), 60);
							}
							
							en.remove();
							ServerStorage.silverfishbombs.remove(en);
						}
					}
					else if(ServerStorage.inkbombs.contains(en)){
						Particle p = new Particle(EnumParticle.SMOKE_LARGE, en.getLocation());
						p.send(Bukkit.getOnlinePlayers());
						ServerStorage.inkbombtime.put(en, ServerStorage.inkbombtime.get(en) -1);
						
						if(ServerStorage.inkbombtime.get(en) == 0){
							Location l1 = en.getLocation();
							Location l2 = new Location(l1.getWorld(), l1.getX() +1, l1.getY(), l1.getZ() +0);
							Location l3 = new Location(l1.getWorld(), l1.getX() +0, l1.getY(), l1.getZ() +1);
							Location l4 = new Location(l1.getWorld(), l1.getX() -1, l1.getY(), l1.getZ() -0);
							Location l5 = new Location(l1.getWorld(), l1.getX() -0, l1.getY(), l1.getZ() -1);
							
							Particle p2 = new Particle(EnumParticle.LAVA, l1);
							p2.setAmount(3);
							p2.send(Bukkit.getOnlinePlayers());
							p2.setLocation(l2);
							p2.send(Bukkit.getOnlinePlayers());
							p2.setLocation(l3);
							p2.send(Bukkit.getOnlinePlayers());
							p2.setLocation(l4);
							p2.send(Bukkit.getOnlinePlayers());
							p2.setLocation(l5);
							p2.send(Bukkit.getOnlinePlayers());
							
							l1.getWorld().playSound(l1, Sound.EXPLODE, 10, 1);
							
							for(Entity ens : en.getNearbyEntities(3, 3, 3)){
								if(ens instanceof Player){
									Player player = (Player) ens;
									player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 0));
								}
							}
							
							en.remove();
							ServerStorage.inkbombs.remove(en);
							ServerStorage.inkbombtime.remove(en);
						}
					}
					else if(ServerStorage.swapteleporter.containsKey(en)){
						Particle p = new Particle(EnumParticle.SMOKE_LARGE, en.getLocation());
						p.setAmount(10);
						p.send(Bukkit.getOnlinePlayers());
						
						OMPlayer omplayer = ServerStorage.swapteleporter.get(en);
						Player player = omplayer.getPlayer();
						for(Entity e : en.getNearbyEntities(0.5, 1, 0.5)){
							if(e instanceof Player){
								if(player != e){
									Player player2 = (Player) e;
									OMPlayer omplayer2 = OMPlayer.getOMPlayer(player2);
									
									if(!omplayer.isInLapisParkour() && !omplayer2.isInLapisParkour()){
										if(omplayer2.hasPlayersEnabled()){
											Location l1 = player.getLocation();
											Location l2 = player2.getLocation();
											
											player.teleport(l2);
											player2.teleport(l1);
											
											player.sendMessage("§7You've §2§lswapped§7 positions with " + omplayer2.getName() + "§7!");
											player2.sendMessage(omplayer.getName() + " §2§lswapped§7 positions with you!");
											
											player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 5, 1);
											player2.playSound(player2.getLocation(), Sound.ENDERMAN_TELEPORT, 5, 1);
											
											ServerStorage.swapteleporter.remove(en);
											en.remove();
										}
										else{
											player.sendMessage("§7This player has §c§lDISABLED §3§lPlayers§7!");
											ServerStorage.swapteleporter.remove(en);
											en.remove();
										}
									}
								}
							}
						}
					}
					else{}
				}
			}
		}
	}
	
	public double getDistance(Location l1, Location l2){
		double distance = l1.distance(l2);
		return distance;
	}
	
	public void navigate(LivingEntity le, Location l, double v){
		try{
			
			Object entityLiving = ReflectionUtil.getMethod("getHandle", le.getClass(), 0).invoke(le);
			Object nav = ReflectionUtil.getMethod("getNavigation", entityLiving.getClass(), 0).invoke(entityLiving);
			ReflectionUtil.getMethod("a", nav.getClass(), 4).invoke(nav, l.getX(), l.getY(), l.getZ(), v);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
