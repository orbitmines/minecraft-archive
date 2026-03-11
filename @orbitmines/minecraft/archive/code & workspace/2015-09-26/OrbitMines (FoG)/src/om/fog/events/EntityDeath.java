package om.fog.events;

import java.util.Random;

import om.fog.FoG;
import om.fog.handlers.players.FoGPlayer;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class EntityDeath implements Listener {

	private FoG fog;
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e){
		this.fog = FoG.getInstance();
		final Player p = (Player) e.getEntity();
		final FoGPlayer omp = FoGPlayer.getFoGPlayer(p);
		
		//e.getDrops().clear();
		
		omp.clearPotionEffects();
		omp.addDeath();
		
		if(p.getKiller() instanceof Player && p.getKiller() != p){
			Player pK = (Player) p.getKiller();
			FoGPlayer ompK = FoGPlayer.getFoGPlayer(pK);
			ItemStack item = pK.getItemInHand();
			
			//ompK.addMoney(money);
			
			ompK.addKill();
			
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
			
			//ompK.addExp(2);
			if(ompK.isLevelUp()){
				ompK.setExp(ompK.getExp() - (int) ompK.getExpRequired());
				ompK.addLevel();
			}
			ompK.updateLevel();
			
			//ActionBar ab = new ActionBar("§6+" + money + " Coins§7, §e+" + (int) (2 * ompK.getExpBooster()) + " XP");
			//ab.send(pK);
		}
		else{
			e.setDeathMessage("§6" + omp.getColorName() + "§7 died.");
		}
		
		p.setHealth(20);
		//p.teleport(kitpvp.getSpawn()); Base Spawn
		
		new BukkitRunnable(){
			public void run(){
				p.setVelocity(new Vector(0, 0, 0));
				p.setFireTicks(0);
			}
		}.runTaskLater(fog, 1);
	}
}
