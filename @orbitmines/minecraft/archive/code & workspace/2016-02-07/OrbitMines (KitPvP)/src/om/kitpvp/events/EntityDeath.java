package om.kitpvp.events;

import java.util.ArrayList;
import java.util.Random;

import om.api.handlers.ActionBar;
import om.api.utils.ItemUtils;
import om.kitpvp.KitPvP;
import om.kitpvp.handlers.ActiveBooster;
import om.kitpvp.handlers.players.KitPvPPlayer;
import om.kitpvp.utils.enums.ItemType;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class EntityDeath implements Listener {

	private KitPvP kitpvp;
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e){
		this.kitpvp = KitPvP.getInstance();
		final Player p = (Player) e.getEntity();
		final KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer(p);
		
		e.getDrops().clear();
		
		omp.clearPotionEffects();
		omp.setKitSelected(null);
		omp.setKitLevelSelected(1);
		omp.setCurrentStreak(0);
		omp.addDeath();
		
		if(p.getKiller() instanceof Player && p.getKiller() != p){
			Player pK = (Player) p.getKiller();
			final KitPvPPlayer ompK = KitPvPPlayer.getKitPvPPlayer(pK);
			ItemStack item = pK.getItemInHand();
			
			{// Necromancer Wither I \\
				ItemStack stick = ItemUtils.hideFlags(ItemUtils.addEnchantment(ItemUtils.setLore(ItemUtils.setDisplayname(new ItemStack(Material.STICK), "§b§lNecromancer §a§lLvL 3§8 || §8Necromancer's Staff"), ItemType.WITHER_I.addEnchantment(new ArrayList<String>())), Enchantment.DURABILITY, 1), 5);
				
				if(pK.getInventory().containsAtLeast(stick, 1)){
					pK.getInventory().addItem(ItemUtils.setDisplayname(new ItemStack(Material.REDSTONE), "§b§lNecromancer §a§lLvL 3§8 || §cSoul"));
				}
			}
			
			int money = 0;
			ActiveBooster booster = kitpvp.getBooster();
			if(booster == null){
				money = (int) (50 * ompK.getVIPBooster());
				int vipextra = money - 50;
				
				ompK.addMoney(money);
				omp.createKillHologram(pK, money);
				pK.sendMessage("§6§l+50 Coins");
				if(vipextra != 0){
					pK.sendMessage("§6§l+" + vipextra + " Coins §7(" + ompK.getVIPRank().getRankString() + " §lVIP§7)");
				}
			}
			else{
				money = (int) (50 * booster.getBooster().getMultiplier());
				int extra = money - 50;
				money *= ompK.getVIPBooster();
				int vipextra = money - 50 - extra;
				
				ompK.addMoney(money);
				omp.createKillHologram(pK, money);
				
				pK.sendMessage("§6§l+50 Coins");
				pK.sendMessage("§6§l+" + extra + " Coins §7(§a" + booster.getPlayer() + "'s Booster§7)");
				if(vipextra != 0){
					pK.sendMessage("§6§l+" + vipextra + " Coins §7(" + ompK.getVIPRank().getRankString() + " §lVIP§7)");
				}
			}
			
			ompK.setCurrentStreak(ompK.getCurrentStreak() +1);
			ompK.addKill();
			pK.sendMessage("§f§lCurrent Streak: §c§l" + ompK.getCurrentStreak() + " §f§lBest Streak: §c§l" + ompK.getBestStreak());
			if(ompK.getBestStreak() < ompK.getCurrentStreak()){
				ompK.setBestStreak(ompK.getCurrentStreak());
				
				pK.sendMessage("§f§lNew Best Streak: §c§l" + ompK.getCurrentStreak());
				pK.playSound(pK.getLocation(), Sound.LEVEL_UP, 5, 1);
			}
			
			if(item == null || item.getType() != Material.BOW){
				if(new Random().nextBoolean()){
					e.setDeathMessage("§6" + omp.getColorName() + "§7 was killed by §6" + ompK.getColorName() + "§7!");
				}
				else{
					e.setDeathMessage("§6" + omp.getColorName() + "§7 was slaughtered by §6" + ompK.getColorName() + "§7!");
				}
			}
			else{
				if(new Random().nextBoolean()){
					e.setDeathMessage("§6" + omp.getColorName() + "§7 was shot by §6" + ompK.getColorName() + "§7!");
				}
				else{
					e.setDeathMessage("§6" + omp.getColorName() + "§7 was sniped by §6" + ompK.getColorName() + "§7!");
				}
			}
			
			ompK.addExp(2);
			if(ompK.isLevelUp()){
				ompK.setExp(ompK.getExp() - (int) ompK.getExpRequired());
				ompK.addLevel();
				Bukkit.broadcastMessage("§6" + ompK.getName() + " §7reached level §6" + ompK.getLevels() + "§7!");
			}
			ompK.updateLevel();
			
			ActionBar ab = new ActionBar("§6+" + money + " Coins§7, §e+" + (int) (2 * ompK.getExpBooster()) + " XP");
			ab.send(pK);
			
			final int streak = ompK.getCurrentStreak();
			if(streak == 3 || streak == 5 || streak >= 10){
				new BukkitRunnable(){
					public void run(){
						Bukkit.broadcastMessage("§c§l" + ompK.getName() + "§7 has a §c§l" + streak + " Kill Streak§7!");
					}
				}.runTaskLater(kitpvp, 1);
			}
		}
		else{
			e.setDeathMessage("§6" + omp.getColorName() + "§7 died.");
		}
		
		p.setHealth(20);
		p.teleport(kitpvp.getSpawn());
		omp.clearInventory();
		
		new BukkitRunnable(){
			public void run(){
				p.setVelocity(new Vector(0, 0, 0));
				p.setFireTicks(0);
				kitpvp.giveLobbyKit(omp);
			}
		}.runTaskLater(kitpvp, 1);
	}
}
