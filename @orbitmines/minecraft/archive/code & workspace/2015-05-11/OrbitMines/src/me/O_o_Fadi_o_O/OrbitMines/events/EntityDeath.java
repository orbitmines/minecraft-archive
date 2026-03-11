package me.O_o_Fadi_o_O.OrbitMines.events;

import java.util.ArrayList;
import java.util.Random;

import me.O_o_Fadi_o_O.OrbitMines.Start;
import me.O_o_Fadi_o_O.OrbitMines.utils.OMPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData.KitPvPServer;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.ActiveBooster;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPPlayer;
import me.O_o_Fadi_o_O.OrbitMines.utils.kitpvp.KitPvPUtils.ItemType;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class EntityDeath implements Listener{
	
	@EventHandler
	public void onDeath(EntityDeathEvent e){
		if(ServerData.isServer(Server.HUB) && e.getEntity() instanceof Creeper){
			e.getDrops().clear();
		}
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e){
		final Player p = (Player) e.getEntity();
		final OMPlayer omp = OMPlayer.getOMPlayer(p);
		
		if(ServerData.isServer(Server.KITPVP)){
			final KitPvPServer kitpvp = ServerData.getKitPvP();
			KitPvPPlayer kp = omp.getKitPvPPlayer();
			
			e.getDrops().clear();

			for(int i = 0; i < 6; i++){
				ItemStack itemD = Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.INK_SACK), "Blood " + p.getName() + i), 1);
				int delay = 40;
				if(i > 2){
					itemD = Utils.setDurability(Utils.setDisplayname(new ItemStack(Material.BONE), "Bone " + p.getName() + i), 1);
					delay = 60;
				}
				
				final Item item = p.getWorld().dropItem(p.getLocation(), itemD);
				item.setPickupDelay(Integer.MAX_VALUE);
				item.setVelocity(Utils.getRandomVelocity());
				
				new BukkitRunnable(){
					public void run(){
						item.remove();
					}
				}.runTaskLater(Start.getInstance(), delay);
			}
			
			p.setHealth(20);
			p.teleport(kitpvp.getSpawn());
			p.setFireTicks(0);
			omp.clearInventory();
			
			new BukkitRunnable(){
				public void run(){
					p.setVelocity(new Vector(0, 0, 0));
					kitpvp.giveLobbyItems(omp);
				}
			}.runTaskLater(Start.getInstance(), 1);
			
			kp.setKitSelected(null);
			kp.setKitLevelSelected(1);
			kp.setCurrentStreak(0);
			kp.addDeath();
			
			if(p.getKiller() instanceof Player){
				Player pK = (Player) p.getKiller();
				final OMPlayer ompK = OMPlayer.getOMPlayer(pK);
				KitPvPPlayer kpK = ompK.getKitPvPPlayer();
				ItemStack item = pK.getItemInHand();
				
				{// Necromancer Wither I \\
					ItemStack stick = Utils.hideFlags(Utils.addEnchantment(Utils.setLore(Utils.setDisplayname(new ItemStack(Material.STICK), "§b§lNecromancer §a§lLvL 3§8 || §8Necromancer's Staff"), ItemType.WITHER_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1), 5);
					
					if(pK.getInventory().containsAtLeast(stick, 1)){
						pK.getInventory().addItem(Utils.setDisplayname(new ItemStack(Material.REDSTONE), "§b§lNecromancer §a§lLvL 3§8 || §cSoul"));
					}
				}
				
				ActiveBooster booster = kitpvp.getBooster();
				if(booster == null){
					kpK.addMoney(50);
					kp.createKillHologram(pK, 50);
					pK.sendMessage("§7You've killed §6" + p.getName() + "§7! §6§l+50 Coins");
				}
				else{
					int money = (int) (50 * booster.getBooster().getMultiplier());
					int extra = money - 50;
					
					kpK.addMoney(money);
					kp.createKillHologram(pK, money);
					
					pK.sendMessage("§7You've killed §6" + p.getName() + "§7! §6§l+50 Coins");
					pK.sendMessage("§6§l+" + extra + " Coins §7(§a" + booster.getPlayer() + "'s Booster§7)");
				}
				kpK.setCurrentStreak(kpK.getCurrentStreak() +1);
				kpK.addKill();
				pK.sendMessage("§f§lCurrent Streak: §c§l" + kpK.getCurrentStreak() + " §f§lBest Streak: §c§l" + kpK.getBestStreak());
				if(kpK.getBestStreak() < kpK.getCurrentStreak()){
					kpK.setBestStreak(kpK.getCurrentStreak());
					
					pK.sendMessage("§f§lNew Best Streak: §c§l" + kpK.getCurrentStreak());
					pK.playSound(pK.getLocation(), Sound.LEVEL_UP, 5, 1);
				}
				
				if(item == null || item.getType() != Material.BOW){
					if(new Random().nextBoolean()){
						e.setDeathMessage("§c§lKitPvP §8| §6" + p.getName() + "§7 was killed by §6" + pK.getName() + "§7!");
					}
					else{
						e.setDeathMessage("§c§lKitPvP §8| §6" + p.getName() + "§7 was slaughtered by §6" + pK.getName() + "§7!");
					}
				}
				else{
					if(new Random().nextBoolean()){
						e.setDeathMessage("§c§lKitPvP §8| §6" + p.getName() + "§7 was shot by §6" + pK.getName() + "§7!");
					}
					else{
						e.setDeathMessage("§c§lKitPvP §8| §6" + p.getName() + "§7 was sniped by §6" + pK.getName() + "§7!");
					}
				}
				
				// REQUIRED ON LEVEL 0 : (10 * 1) + (5 * (3 * (0 / 10) * 0)) = 10
				// REQUIRED ON LEVEL 1 : (10 * 2) + (5 * (3 * (1 / 10) * 1)) = 20 + (5 * 0.3 * 1) = 22
				// REQUIRED ON LEVEL 2 : (10 * 3) + (5 * (3 * (2 / 10) * 2)) = 30 + (5 * 0.6 * 2) = 36
				// REQUIRED ON LEVEL 3 : (10 * 4) + (5 * (3 * (3 / 10) * 3)) = 40 + (5 * 0.9 * 3) = 54
				
				int level = kpK.getLevels();
				int killsrequired = 10;
				if(level != 0){
					killsrequired = (10 * (level + 1)) + (5 * (3 * (level / 10) * level));
				}
				
				if(kpK.getKills() >= killsrequired){
					kpK.addLevel();
					Bukkit.broadcastMessage("§6" + ompK.getName() + " §7reached level §6" + kpK.getLevels() + "§7!");
				}
				
				final int streak = kpK.getCurrentStreak();
				if(streak == 3 || streak == 5 || streak >= 10){
					new BukkitRunnable(){
						public void run(){
							Bukkit.broadcastMessage("§c§l" + ompK.getName() + "§7 has a §c§l" + streak + " Kill Streak§7!");
						}
					}.runTaskLater(Start.getInstance(), 1);
				}
			}
			else{
				e.setDeathMessage("§c§lKitPvP §8| §6" + p.getName() + "§7 died.");
			}
		}
	}
}
