package fadidev.orbitmines.kitpvp.events;

import fadidev.orbitmines.api.handlers.chat.ActionBar;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.kitpvp.OrbitMinesKitPvP;
import fadidev.orbitmines.kitpvp.handlers.ActiveBooster;
import fadidev.orbitmines.kitpvp.handlers.KitPvPMessages;
import fadidev.orbitmines.kitpvp.handlers.KitPvPPlayer;
import fadidev.orbitmines.kitpvp.utils.enums.ItemType;
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

public class DeathEvent implements Listener {

	private OrbitMinesKitPvP kitPvP;

	public DeathEvent(){
		kitPvP = OrbitMinesKitPvP.getKitPvP();
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e){
		final Player p = e.getEntity();
		final KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer(p);
		
		e.getDrops().clear();
		
		omp.clearPotionEffects();
		omp.setKitSelected(null);
		omp.setKitLevelSelected(1);
		omp.setCurrentStreak(0);
		omp.addDeath();
		
		if(p.getKiller() != null && p.getKiller() != p){
			Player pK = p.getKiller();
			final KitPvPPlayer ompK = KitPvPPlayer.getKitPvPPlayer(pK);
			ItemStack item = pK.getItemInHand();
			
			{// Necromancer Wither I \\
				ItemStack stick = kitPvP.getApi().getNms().customItem().hideFlags(ItemUtils.addEnchantment(ItemUtils.itemstack(Material.STICK, 1, "§b§lNecromancer §a§lLvL 3§8 || §8Necromancer's Staff", 0, ItemType.WITHER_I.getName()), Enchantment.DURABILITY, 1), 5);
				
				if(pK.getInventory().containsAtLeast(stick, 1))
					pK.getInventory().addItem(ItemUtils.itemstack(Material.REDSTONE, 1, "§b§lNecromancer §a§lLvL 3§8 || §cSoul"));
			}
			
			int money;
			ActiveBooster booster = kitPvP.getBooster();
			if(booster == null){
				money = (int) (50 * ompK.getVIPBooster());
				int vipExtra = money - 50;
				
				ompK.addMoney(money);
				omp.createKillHologram(pK, money);
				pK.sendMessage("§6§l+50 Coins");
				if(vipExtra != 0){
					pK.sendMessage("§6§l+" + vipExtra + " Coins §7(" + ompK.getVipRank().getRankString() + " §lVIP§7)");
				}
			}
			else{
				money = (int) (50 * booster.getBooster().getMultiplier());
				int extra = money - 50;
				money *= ompK.getVIPBooster();
				int vipExtra = money - 50 - extra;
				
				ompK.addMoney(money);
				omp.createKillHologram(pK, money);
				
				pK.sendMessage("§6§l+50 Coins");
				pK.sendMessage("§6§l+" + extra + " Coins §7(§a" + booster.getPlayer() + "'s Booster§7)");
				if(vipExtra != 0){
					pK.sendMessage("§6§l+" + vipExtra + " Coins §7(" + ompK.getVipRank().getRankString() + " §lVIP§7)");
				}
			}

			ompK.setCurrentStreak(ompK.getCurrentStreak() +1);
			ompK.addKill();
			pK.sendMessage("§f§l" + KitPvPMessages.WORD_CURRENT.get(ompK) + " Streak: §c§l" + ompK.getCurrentStreak() + " §f§l" + KitPvPMessages.WORD_BEST.get(ompK) + " Streak: §c§l" + ompK.getBestStreak());
			if(ompK.getBestStreak() < ompK.getCurrentStreak()){
				ompK.setBestStreak(ompK.getCurrentStreak());

                pK.sendMessage(KitPvPMessages.NEW_BEST_STREAK.get(ompK, ompK.getCurrentStreak() + ""));
				pK.playSound(pK.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 1);
			}
			
			if(item == null || item.getType() != Material.BOW){
                KitPvPMessages.KILLED_BY.broadcast(omp.getColorName(), ompK.getColorName());
			}
			else{
                KitPvPMessages.SHOT_BY.broadcast(omp.getColorName(), ompK.getColorName());
			}
			
			ompK.addExp(2);
			if(ompK.isLevelUp()){
				ompK.setExp(ompK.getExp() - (int) ompK.getExpRequired());
				ompK.addLevel();
                KitPvPMessages.REACHED_LEVEL.broadcast(ompK.getName(), ompK.getLevels() + "");
			}
			ompK.updateLevel();
			
			ActionBar ab = new ActionBar(pK, "§6+" + money + " Coins§7, §e+" + (int) (2 * ompK.getExpBooster()) + " XP", 40);
			ab.send();
			
			final int streak = ompK.getCurrentStreak();
			if(streak == 3 || streak == 5 || streak >= 10){
				new BukkitRunnable(){
					public void run(){
                        KitPvPMessages.KILL_STREAK.broadcast(ompK.getName(), "" + streak);
					}
				}.runTaskLater(kitPvP, 1);
			}
		}
		else{
            KitPvPMessages.DIED.broadcast(omp.getColorName());
		}

		e.setDeathMessage(null);
		
		p.setHealth(20);
		p.teleport(kitPvP.getSpawn());
		omp.clearInventory();
		
		new BukkitRunnable(){
			public void run(){
				p.setVelocity(new Vector(0, 0, 0));
				p.setFireTicks(0);
                kitPvP.getLobbyKit().get(omp.getLanguage()).setItems(p);
			}
		}.runTaskLater(kitPvP, 1);
	}
}
