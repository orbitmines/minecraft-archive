package me.O_o_Fadi_o_O.Hub.events;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import me.O_o_Fadi_o_O.Hub.Hub;
import me.O_o_Fadi_o_O.Hub.Inv.CosmeticPerks;
import me.O_o_Fadi_o_O.Hub.Inv.ServerSelector;
import me.O_o_Fadi_o_O.Hub.Inv.Settings;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class PlayerInteract implements Listener{
	
	Hub plugin;
	 
	public PlayerInteract(Hub instance) {
	plugin = instance;
	}
	
	private Map<String, Long> lastUsage = new HashMap<String, Long>();
	private final int cdtime = 2;
	private Map<String, Long> lastUsage2 = new HashMap<String, Long>();
	private final int cdtime2 = 10;
	
	ServerSelector sinv = new ServerSelector();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		
		ItemStack i = e.getItem();
		Player p = e.getPlayer();
		
		try{
			if(i != null){
				if(i.getType() == Material.SADDLE && i.getItemMeta().getDisplayName().equals("§e§nPet Ride")){
					e.setCancelled(true);
					p.updateInventory();
				}
				if(i.getType() == Material.LEASH){
					e.setCancelled(true);
					p.updateInventory();
				}
				if(i.getType() == Material.ENDER_PEARL){
					e.setCancelled(true);
					p.updateInventory();
				}
				if(i.getType() == Material.EXP_BOTTLE){
					e.setCancelled(true);
					p.updateInventory();
				}
				if(i.getType() == Material.SKULL_ITEM && i.getItemMeta().getDisplayName().equals("§a§nCreeper Launcher")){
					
					e.setCancelled(true);
					p.updateInventory();
					
					long lastUsed = 0;
					if(lastUsage.containsKey(p.getName())){
						lastUsed = lastUsage.get(p.getName());
					}
					int cdmillis = cdtime * 1000;
							
					if(System.currentTimeMillis()-lastUsed>=cdmillis){
						
						Creeper creeper = (Creeper) p.getWorld().spawnEntity(p.getLocation(), EntityType.CREEPER);
						creeper.setPowered(true);
						
						Vector vector = p.getLocation().getDirection().normalize().multiply(1.5);
						
						creeper.setVelocity(vector);
						
						lastUsage.put(p.getName(), System.currentTimeMillis());
					}
				}
				if(i.getType() == Material.SNOW_BALL && i.getItemMeta().getDisplayName().equals("§f§nPaintballs")){
					e.setCancelled(true);
					p.updateInventory();
					
					Snowball ball = p.launchProjectile(Snowball.class);
					Hub.Paintballs.add(ball);
				}
				if(i.getType() == Material.BOOK && i.getItemMeta().getDisplayName().equals("§7§nBook Explosion")){
					e.setCancelled(true);
					p.updateInventory();
					
					long lastUsed2 = 0;
					if(lastUsage2.containsKey(p.getName())){
						lastUsed2 = lastUsage2.get(p.getName());
					}
					int cdmillis2 = cdtime2 * 1000;
							
					if(System.currentTimeMillis()-lastUsed2>=cdmillis2){
						p.getWorld().playSound(p.getLocation(), Sound.EXPLODE, 5, 1);
						for(int i1 = 1; i1 <= 12; i1++){
							int upper1 = 5;
							Random random1 = new Random();
							random1.nextInt((upper1));
							int upper2 = 4;
							Random random2 = new Random();
							random2.nextInt((upper2));
							int upper3 = 6;
							Random random3 = new Random();
							random3.nextInt((upper3));
							
							ItemStack item = new ItemStack(Material.PAPER);
							ItemMeta meta = item.getItemMeta();
							meta.setDisplayName("Paper " + i1);
							item.setItemMeta(meta);
							
							final Item paper = p.getWorld().dropItem(p.getLocation(), item);
				            	
			            	if(i1 == 1 || i1 == 2 || i1 == 3){
					            float x = (float) (-0.03 + (float) (Math.random() * ((0.03 - -0.03) + 0.3))) * -3;
					            float y = (float) (-0.03 + (float) (Math.random() * ((0.03 - -0.03) + 0.3))) * 3;
					            float z = (float) (-0.03 + (float) (Math.random() * ((0.03 - -0.03) + 0.3))) * -3;
			            		paper.setVelocity(new Vector(x -0.2, y, z -0.2));
			            		paper.setVelocity(new Vector(x -0.2, y, z -0.2));
			            		paper.setVelocity(new Vector(x -0.2, y, z -0.2));
			            		paper.setVelocity(new Vector(x -0.2, y, z -0.2));
			            	}
			            	else if(i1 == 4 || i1 == 5 || i1 == 6){
					            float x = (float) (-0.03 + (float) (Math.random() * ((0.03 - -0.03) + 0.3)));
					            float y = (float) (-0.03 + (float) (Math.random() * ((0.03 - -0.03) + 0.3))) * 3;
					            float z = (float) (-0.03 + (float) (Math.random() * ((0.03 - -0.03) + 0.3)));
			            		paper.setVelocity(new Vector(x, y, z));
			            		paper.setVelocity(new Vector(x, y, z));
			            		paper.setVelocity(new Vector(x, y, z));
			            		paper.setVelocity(new Vector(x, y, z));
			            	}
			            	else if(i1 == 7 || i1 == 8 || i1 == 9){
					            float x = (float) (-0.03 + (float) (Math.random() * ((0.03 - -0.03) + 0.3))) * -3;
					            float y = (float) (-0.03 + (float) (Math.random() * ((0.03 - -0.03) + 0.3))) * 3;
					            float z = (float) (-0.03 + (float) (Math.random() * ((0.03 - -0.03) + 0.3)));
			            		paper.setVelocity(new Vector(x -0.2, y, z));
			            		paper.setVelocity(new Vector(x -0.2, y, z));
			            		paper.setVelocity(new Vector(x -0.2, y, z));
			            		paper.setVelocity(new Vector(x -0.2, y, z));
			            	}
			              	else if(i1 == 10 || i1 == 11 || i1 == 12){
					            float x = (float) (-0.03 + (float) (Math.random() * ((0.03 - -0.03) + 0.3)));
					            float y = (float) (-0.03 + (float) (Math.random() * ((0.03 - -0.03) + 0.3))) * 3;
					            float z = (float) (-0.03 + (float) (Math.random() * ((0.03 - -0.03) + 0.3))) * -3;
			            		paper.setVelocity(new Vector(x, y, z -0.2));
			            		paper.setVelocity(new Vector(x, y, z -0.2));
			            		paper.setVelocity(new Vector(x, y, z -0.2));
			            		paper.setVelocity(new Vector(x, y, z -0.2));
			            	}
							
							new BukkitRunnable(){
							  @Override
							  public void run(){
							    paper.remove();
							  }
							}.runTaskLater(this.plugin, 20 * 10L);
							
							lastUsage2.put(p.getName(), System.currentTimeMillis());
						}
					}
				}
				if(i.getType() == Material.FIREBALL && i.getItemMeta().getDisplayName().startsWith("§c§nFirework Gun")){
					e.setCancelled(true);
					p.updateInventory();
					
					if(Hub.FireworkPasses.get(p.getName()) >= 1){
						Firework fw = (Firework) p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
				        FireworkMeta fwmeta = fw.getFireworkMeta();
				        FireworkEffect.Builder builder = FireworkEffect.builder();
				        if(Hub.FireworkTrail.get(p.getName()) != null && Hub.FireworkTrail.get(p.getName()) == true){
				        	builder.withTrail();
				        }
				        if(Hub.FireworkFlicker.get(p.getName()) != null && Hub.FireworkFlicker.get(p.getName()) == true){
				        	builder.withFlicker();
				        }
				        if(Hub.FireworkFade1.get(p.getName()) != null){
				        	builder.withFade(Hub.FireworkFade1.get(p.getName()));
				        }
				        if(Hub.FireworkFade2.get(p.getName()) != null){
				        	builder.withFade(Hub.FireworkFade2.get(p.getName()));
				        }
				        if(Hub.FireworkColor1.get(p.getName()) != null){
				        	builder.withColor(Hub.FireworkColor1.get(p.getName()));
				        }
				        else{
				        	builder.withColor(Color.AQUA);
				        }
				        if(Hub.FireworkColor2.get(p.getName()) != null){
				        	builder.withColor(Hub.FireworkColor2.get(p.getName()));
				        }
				        if(Hub.FireworkType.get(p.getName()) != null){
				        	builder.with(Hub.FireworkType.get(p.getName()));
				        }
				        else{
				        	builder.with(Type.BALL);
				        }
				        
				        fwmeta.addEffects(builder.build());
				        fw.setFireworkMeta(fwmeta);
				        
				        fw.setVelocity(p.getLocation().getDirection().multiply(0.2));
				        
				        Hub.FireworkPasses.put(p.getName(), Hub.FireworkPasses.get(p.getName()) -1);
				        
						ItemStack item = new ItemStack(Material.FIREBALL, 1);
						ItemMeta itemmeta = item.getItemMeta();
						itemmeta.setDisplayName("§c§nFirework Gun§r §c(§6" + Hub.FireworkPasses.get(p.getName()) + "§c)");
						item.setItemMeta(itemmeta);
						p.getInventory().setItem(5, new ItemStack(item));
					}
					else{
						p.sendMessage("§c§oYou don't have any §6§lFirework Passes§c§o!");
					}
					
				}
				if(i.getType() == Material.REDSTONE_TORCH_ON && i.getItemMeta().getDisplayName().equals("§c§nSettings")){
					
					e.setCancelled(true);
					p.updateInventory();
					
					p.playEffect(p.getLocation(), Effect.STEP_SOUND, 152);
					Inventory invs = Bukkit.createInventory(null, 27, "§0§lSettings (" + p.getName() + ")");
					Settings.getSettingsInv(p, invs);
					
				}
				if(i.getType() == Material.ENDER_PEARL && i.getItemMeta().getDisplayName().equals("§3§nServer Selector")){
					
					p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 5, 1);
					p.openInventory(sinv.getServerSelectorInv(p, 1));
				
				}
				if(i.getType() == Material.ENDER_CHEST && i.getItemMeta().getDisplayName().equals("§9§nCosmetic Perks")){
					
					e.setCancelled(true);
					p.updateInventory();
					
					p.playSound(p.getLocation(), Sound.CHEST_OPEN, 5, 1);
					p.openInventory(CosmeticPerks.getCosmeticPerks(p));
					
				}
				if(i.getType() == Material.FEATHER && i.getItemMeta().getDisplayName().equals("§f§nFly")){
	
					if(p.hasPermission("Rank.Iron")){
						if(p.getAllowFlight() == false){
							p.playEffect(p.getLocation(), Effect.STEP_SOUND, 80);
							p.setAllowFlight(true);
							p.setFlying(true);
							p.sendMessage("§f§l§oFly §a§l§oENABLED");
						}
						else if(p.getAllowFlight() == true){
							p.playEffect(p.getLocation(), Effect.STEP_SOUND, 80);
							p.setAllowFlight(false);
							p.setFlying(false);
							p.sendMessage("§f§l§oFly §c§l§oDISABLED");
						}
					}
					else{
						p.playSound(p.getLocation(), Sound.LAVA_POP, 5, 1);
						p.sendMessage("§c§oYou need to be §7§lIron VIP§c§o to use this!");
					}
					
				}
				if(i.getType() == Material.EXP_BOTTLE && i.getItemMeta().getDisplayName().equals("§d§nAchievements")){
	
					/*
					 * 
					 * TODO: ADD ACHIEVEMENTS
					 * 
					 */
					
					p.sendMessage("§a§oComing Shortly...");
					
				}
			}
		}catch(Exception ex){
			
		}
	}

}
