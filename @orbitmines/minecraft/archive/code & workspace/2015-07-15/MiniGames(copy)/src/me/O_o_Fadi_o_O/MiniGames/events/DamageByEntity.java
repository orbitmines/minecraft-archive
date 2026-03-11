package me.O_o_Fadi_o_O.MiniGames.events;

import me.O_o_Fadi_o_O.MiniGames.Start;
import me.O_o_Fadi_o_O.MiniGames.managers.PlayerManager;
import me.O_o_Fadi_o_O.MiniGames.managers.StorageManager;
import me.O_o_Fadi_o_O.MiniGames.utils.ChickenFightState;
import me.O_o_Fadi_o_O.MiniGames.utils.Game;
import me.O_o_Fadi_o_O.MiniGames.utils.SurvivalGamesState;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Spider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class DamageByEntity implements Listener{
	
	Start start = Start.getInstance();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e){
		if(e.getDamager() instanceof Player && e.getEntity() instanceof Player){
			
			if(StorageManager.playersinchickenfight.contains(damager) && StorageManager.playersinchickenfight.contains(damaged)){
				
				if(StorageManager.chickenfightstate.get(StorageManager.playersarena.get(damager)) == ChickenFightState.INGAME){
					damaged.getWorld().playEffect(damaged.getLocation(), Effect.STEP_SOUND, 152, 3);
				}
			}
			
			if(c != null && c.getType() == Material.FEATHER){
				
				new BukkitRunnable(){
					
					public void run(){
						if(StorageManager.chickenfightkit.get(damaged).equals("Baby Chicken")){
							Vector velocity = damaged.getLocation().subtract(damager.getLocation()).toVector().normalize().multiply(1.4);
							damaged.setVelocity(velocity.add(new Vector(0, 0.6, 0)));
						}
						if(StorageManager.chickenfightkit.get(damaged).equals("Chicken Mama")){
							Vector velocity = damaged.getLocation().subtract(damager.getLocation()).toVector().normalize().multiply(1.2);
							damaged.setVelocity(velocity.add(new Vector(0, 0.45, 0)));
						}
						if(StorageManager.chickenfightkit.get(damaged).equals("Hot Wing")){
							Vector velocity = damaged.getLocation().subtract(damager.getLocation()).toVector().normalize().multiply(1.3);
							damaged.setVelocity(velocity.add(new Vector(0, 0.53, 0)));
						}
						if(StorageManager.chickenfightkit.get(damaged).equals("Chicken Warrior")){
							Vector velocity = damaged.getLocation().subtract(damager.getLocation()).toVector().normalize().multiply(1.05);
							damaged.setVelocity(velocity.add(new Vector(0, 0.24, 0)));
						}
					}
				}.runTaskLater(this.start, 1L);
				
			}
		}
	}

}
