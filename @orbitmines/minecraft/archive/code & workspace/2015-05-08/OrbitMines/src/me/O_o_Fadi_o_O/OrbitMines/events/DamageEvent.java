package me.O_o_Fadi_o_O.OrbitMines.events;

import me.O_o_Fadi_o_O.OrbitMines.Start;
import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.ServerStorage;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Spider;
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
	}
}
