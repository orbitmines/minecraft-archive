package fadidev.orbitmines.api.runnables.orbitmines;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.Cooldowns;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.Particle;
import fadidev.orbitmines.api.runnables.OMRunnable;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.api.utils.enums.perks.Trail;
import fadidev.orbitmines.api.utils.enums.perks.TrailType;
import fadidev.orbitmines.api.utils.enums.ranks.StaffRank;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static fadidev.orbitmines.api.utils.WorldUtils.navigate;

public class PlayerDataRunnable extends OMRunnable {

	private OrbitMinesAPI api;

	public PlayerDataRunnable() {
		super(TimeUnit.TICK, 2);

        this.api = OrbitMinesAPI.getApi();
	}

	@Override
	public void run() {
		for(OMPlayer omp : api.getOMPlayers()){
			Player p = omp.getPlayer();

			if(omp.isLoaded()){
				if(p.isOp() && omp.getStaffRank() != StaffRank.OWNER && !omp.isOpMode())
                    p.setOp(false);
				
				if(omp.getCooldowns().containsKey(Cooldowns.TELEPORTING)){
					if(omp.onCooldown(Cooldowns.TELEPORTING))
						omp.parseTrail(Trail.FIREWORK_SPARK, TrailType.CYLINDER_TRAIL, 1, false);
				}
				else{
					omp.playTrail();
				}
				omp.updateCooldownActionBar();

				Entity entity = omp.getPet();
				if(entity != null && entity instanceof LivingEntity){
					if(entity.getWorld().getName().equals(p.getWorld().getName())){
						LivingEntity pet = (LivingEntity) entity;
						
						Location l = p.getLocation();
						
						if(l.distance(pet.getLocation()) < 20)
							navigate(pet, l, 1.2);
						else
							pet.teleport(l);
					}
					else{
						omp.disablePet();
					}
				}

				if(!api.getServerPlugin().gadgetsEnabled())
				    continue;

                if(omp.getPet() != null){
                    Entity pet = omp.getPet();

                    if(omp.hasPetSheepDisco()){
                        Random r = new Random();
                        int rInt = r.nextInt(15);

                        Sheep s = (Sheep) pet;
                        DyeColor c = DyeColor.getByDyeData((byte) rInt);
                        s.setColor(c);
                    }
                    else if(omp.hasPetShroomTrail()){
                        for(int i = 0; i <= 5; i++){
                            ItemStack item = ItemUtils.itemstack(Material.RED_MUSHROOM, 1, p.getName() + i);
                            final Item iEn = pet.getWorld().dropItem(pet.getLocation(), item);
                            iEn.setVelocity(pet.getLocation().getDirection().multiply(-1).add(Utils.randomVelocity()));

                            new BukkitRunnable(){
                                @Override
                                public void run() {
                                    iEn.remove();
                                }
                            }.runTaskLater(api, 60);
                        }
                    }
                    else if(omp.hasPetBabyPigs()){
                        List<Entity> list = omp.getPetBabyPigEntities();

                        LivingEntity pig1 = (LivingEntity) list.get(0);
                        LivingEntity pig2 = (LivingEntity) list.get(1);

                        Location enL = pet.getLocation();
                        Location pig1L = pig1.getLocation();
                        Location pig2L = pig2.getLocation();

                        double d1 = enL.distance(pig1L);
                        double d2 = pig1L.distance(pig2L);

                        if(d1 < 7 && d1 > 1)
                            navigate(pig1, enL, 1.2);
                        else
                            pig1.teleport(enL);

                        if(d2 < 7 && d2 > 1)
                            navigate(pig2, pig1L, 1.2);
                        else
                            pig2.teleport(pig1L);
                    }
                }

                if(omp.getSgaSeconds() != -1 && omp.getSgaSnowGolems().size() > 0){
                    int seconds = omp.getSgaSeconds();

                    List<Entity> snowGolems = omp.getSgaSnowGolems();

                    List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());

                    if(seconds >= 12){
                        Entity en = snowGolems.get(2);
                        Player player = players.get(Utils.RANDOM.nextInt(players.size()));

                        Location l1 = en.getLocation();
                        Location l2 = player.getLocation();

                        Snowball s = ((Snowman) en).launchProjectile(Snowball.class, l2.toVector().subtract(l1.toVector()).multiply(0.15));
                        en.getWorld().playSound(l1, Sound.BLOCK_SNOW_BREAK, 2, 1);
                        api.getGadgets().getSnowGolemAttackBalls().add(s);
                    }

                    if(seconds == 50){
                        Location l = snowGolems.get(1).getLocation();
                        l.getWorld().playSound(l, Sound.ENTITY_WITHER_DEATH, 5, 1);

                        Particle pa = new Particle(org.bukkit.Particle.FLAME, l);
                        pa.setSize(2, 2, 2);
                        pa.setAmount(30);
                        pa.send(Bukkit.getOnlinePlayers());

                        for(Entity en : snowGolems){
                            en.remove();
                        }

                        omp.setSgaSeconds(-1);
                        omp.setSgaItem(null);
                        omp.getSgaSnowGolems().clear();
                    }
                }
            }
		}
	}
}
