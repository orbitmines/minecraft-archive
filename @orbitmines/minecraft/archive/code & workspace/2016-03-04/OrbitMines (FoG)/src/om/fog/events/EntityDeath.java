package om.fog.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import om.fog.FoG;
import om.fog.handlers.Quest;
import om.fog.handlers.map.FoGMap;
import om.fog.handlers.map.SpawnArea;
import om.fog.handlers.players.FoGPlayer;
import om.fog.handlers.quests.MobKillQuest;
import om.fog.utils.enums.Mob;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class EntityDeath implements Listener {

	private FoG fog;
	
	@EventHandler
	public void onDeath(EntityDeathEvent e){
		this.fog = FoG.getInstance();
		Entity en = e.getEntity();
		e.setDroppedExp(0);
		if(!(en instanceof Player)){
			e.getDrops().clear();
		}
		
		if(en instanceof Zombie){
			Zombie z = (Zombie) en;
			String name = en.getCustomName();
			
			if(name != null && name.contains("LvL")){
				Mob mob = Mob.getMob(name);
				int level = Mob.parseLevel(name);
				mob.dropItems(en.getLocation(), level);
				
				for(FoGMap map : fog.getMaps()){
					if(map.inMap(en.getLocation())){
						for(SpawnArea area : map.getSpawnAreas()){
							if(area.getEntities(mob) != null && area.getEntities(mob).contains(en)){
								List<Entity> entities = area.getEntities(mob);
								entities.remove(en);
								area.getEntities().put(mob, entities);
								break;
							}
						}
							
						break;
					}
				}
				
				if(z.getKiller() instanceof Player){
					Player pK = (Player) z.getKiller();
					FoGPlayer ompK = FoGPlayer.getFoGPlayer(pK);
					int exp = mob.getEXP(level);
					
					ompK.addExp(exp);
					
					for(Quest quest : ompK.getCurrentQuestsAsNewList()){
						if(quest instanceof MobKillQuest){
							MobKillQuest mQuest = (MobKillQuest) quest;
							
							if(mQuest.getMob().getMob() == mob && mQuest.getMob().getLevel() == level){
								mQuest.addProgress(ompK);
							}
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e){
		this.fog = FoG.getInstance();
		final Player p = (Player) e.getEntity();
		final FoGPlayer omp = FoGPlayer.getFoGPlayer(p);

		List<ItemStack> toRemove = new ArrayList<ItemStack>();
		for(ItemStack item : e.getDrops()){
			if(item != null && item.getItemMeta() != null && item.getItemMeta().getLore() != null && item.getItemMeta().getLore().contains("§fSoulbound")){
				toRemove.add(item);
			}
		}
		e.getDrops().removeAll(toRemove);
		if(p.getOpenInventory().getTopInventory() instanceof CraftingInventory){
			for(ItemStack item : p.getOpenInventory().getTopInventory().getContents()){
				e.getDrops().remove(item);	
			}
		}
		
		omp.clearPotionEffects();
		omp.addDeath();
		omp.setInCombat(false);
		
		if(p.getKiller() instanceof Player && p.getKiller() != p){
			Player pK = (Player) p.getKiller();
			FoGPlayer ompK = FoGPlayer.getFoGPlayer(pK);
			ItemStack item = pK.getItemInHand();
			
			//TODO Add EXP
			
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
		}
		else{
			e.setDeathMessage("§6" + omp.getColorName() + "§7 died.");
		}
		
		p.setHealth(((CraftPlayer) p).getMaxHealth());
		omp.setCurrentShield(omp.getShield());
		omp.setSuit(null);
		
		new BukkitRunnable(){
			public void run(){
				p.teleport(omp.getFaction().getBaseLocation());
				p.setVelocity(new Vector(0, 0, 0));
				p.setFireTicks(0);
			}
		}.runTaskLater(fog, 1);
	}
}
