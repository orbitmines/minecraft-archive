package fadidev.orbitmines.api.events;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
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

	private OrbitMinesAPI api;

	public DamageEvent(){
		this.api = OrbitMinesAPI.getApi();
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDamage(EntityDamageByEntityEvent e){
		if(e.getEntity() instanceof Player && e.getDamager() instanceof Spider && !api.getPets().contains(e.getDamager())){
			final Player p = (Player) e.getEntity();

			p.playSound(p.getLocation(), Sound.ENTITY_SPIDER_AMBIENT, 5, 1);
			p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0));
			new BukkitRunnable(){
				public void run(){
					p.playSound(p.getLocation(), Sound.ENTITY_SPIDER_AMBIENT, 5, 1);
				}
			}.runTaskLater(api, 1);
			new BukkitRunnable(){
				public void run(){
					p.playSound(p.getLocation(), Sound.ENTITY_SPIDER_AMBIENT, 5, 1);
				}
			}.runTaskLater(api, 3);
			new BukkitRunnable(){
				public void run(){
					p.playSound(p.getLocation(), Sound.ENTITY_SPIDER_AMBIENT, 5, 1);
				}
			}.runTaskLater(api, 5);
		}
        else if(e.getDamager() instanceof Snowball){
			Entity ent = e.getDamager();
			if(!api.getGadgets().getSnowGolemAttackBalls().contains(ent))
			    return;

            for(Entity en : ent.getNearbyEntities(0.5, 0.5, 0.5)){
                if(!(en instanceof Player))
                    continue;

                Player p = (Player) en;
                OMPlayer omp = OMPlayer.getOMPlayer(p);

                if(omp.canReceiveVelocity()){
                    p.playSound(p.getLocation(), Sound.ENTITY_WITHER_AMBIENT, 5, 1);
                    p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 300, 0));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 300, 4));
                }
            }
		}
        else if(e.getEntity() instanceof Player && e.getDamager() instanceof Player){
			ItemStack i = ((Player) e.getDamager()).getItemInHand();

			if(i != null && i.getType() == Material.LEASH && i.getItemMeta().getDisplayName().equals("§6§nStacker")){
				Player pE = (Player) e.getEntity();
				Player pD = (Player) e.getDamager();
				OMPlayer ompE = OMPlayer.getOMPlayer(pE);
				OMPlayer ompD = OMPlayer.getOMPlayer(pD);

				if(!ompE.canReceiveVelocity() || !ompD.canReceiveVelocity() || !ompE.isLoaded() || !ompD.isLoaded())
				    return;

                if(ompE.hasStackerEnabled()){
                    if(ompD.hasStackerEnabled()){
                        if(ompE.hasPlayersEnabled()){
							Entity vehicle = pD;
                            while(vehicle.getPassenger() != null){
                                vehicle = vehicle.getPassenger();
                            }

                            if(vehicle.getEntityId() == pE.getEntityId())
                                return;

                            vehicle.setPassenger(pE);

                            if(vehicle instanceof Player)
                                api.getNms().entity().mountUpdate(vehicle);

                            api.getNms().entity().mountUpdate(pE);

                            pD.sendMessage(Messages.STACK.get(ompD, ompE.getName()));
                            pD.playEffect(pD.getLocation(), Effect.STEP_SOUND, 152);
							pE.sendMessage(Messages.STACK_PLAYER.get(ompE, ompD.getName()));
                            e.setCancelled(true);
                        }
                        else{
							pD.sendMessage(Messages.PLAYERS_DISABLED.get(ompD, ompE.getName()));
                        }
                    }
                    else{
						pD.sendMessage(Messages.STACK_DISABLED.get(ompD));
                    }
                }
                else{
					pD.sendMessage(Messages.STACKER_DISABLED.get(ompD, ompE.getName()));
                }
			}
		}
	}
}
