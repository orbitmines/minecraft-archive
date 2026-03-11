package fadidev.orbitmines.api.runnables.orbitmines;

import fadidev.orbitmines.api.OrbitMinesAPI;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.Particle;
import fadidev.orbitmines.api.runnables.OMRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Silverfish;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;

public class GadgetRunnable extends OMRunnable {

	private OrbitMinesAPI api;

	public GadgetRunnable() {
		super(TimeUnit.TICK, 1);

        this.api = OrbitMinesAPI.getApi();
	}

	@Override
	public void run() {
        for(Entity en : api.getGadgets().getFireballs()){
            Particle p = new Particle(org.bukkit.Particle.FLAME, en.getLocation());
            p.send(Bukkit.getOnlinePlayers());
        }

        for(Entity en : api.getGadgets().getSoccerMagmaCubes()){
            Particle p = new Particle(org.bukkit.Particle.FLAME, en.getLocation());
            p.send(Bukkit.getOnlinePlayers());
        }

        for(Entity en : new ArrayList<>(api.getGadgets().getSilverFishBombs())){
            if(!en.isOnGround())
                continue;

            Location l = en.getLocation();
            l.getWorld().playSound(l, Sound.ENTITY_GENERIC_EXPLODE, 5, 1);
            l.getWorld().playEffect(l, Effect.EXPLOSION_HUGE, 4);
            Location l2 = new Location(l.getWorld(), l.getX() +1, l.getY(), l.getZ() +0);
            Location l3 = new Location(l.getWorld(), l.getX() +0, l.getY(), l.getZ() +1);
            Location l4 = new Location(l.getWorld(), l.getX() -1, l.getY(), l.getZ() +0);
            Location l5 = new Location(l.getWorld(), l.getX() +0, l.getY(), l.getZ() -1);

            for(Location lo : Arrays.asList(l, l2, l3, l4, l5)){
                final Silverfish s = (Silverfish) en.getWorld().spawnEntity(lo, EntityType.SILVERFISH);
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        s.remove();
                    }
                }.runTaskLater(api, 60);
            }

            en.remove();
            api.getGadgets().getSilverFishBombs().remove(en);
        }

        for(Entity en : new ArrayList<>(api.getGadgets().getInkBombs())){
            Particle p = new Particle(org.bukkit.Particle.SMOKE_LARGE, en.getLocation());
            p.send(Bukkit.getOnlinePlayers());
            api.getGadgets().getInkBombTime().put(en, api.getGadgets().getInkBombTime().get(en) -1);

            if(api.getGadgets().getInkBombTime().get(en) != 0)
                continue;

            Location l1 = en.getLocation();
            Location l2 = new Location(l1.getWorld(), l1.getX() +1, l1.getY(), l1.getZ() +0);
            Location l3 = new Location(l1.getWorld(), l1.getX() +0, l1.getY(), l1.getZ() +1);
            Location l4 = new Location(l1.getWorld(), l1.getX() -1, l1.getY(), l1.getZ() -0);
            Location l5 = new Location(l1.getWorld(), l1.getX() -0, l1.getY(), l1.getZ() -1);

            Particle p2 = new Particle(org.bukkit.Particle.LAVA, l1);
            p2.setAmount(3);
            p2.send(Bukkit.getOnlinePlayers());
            p2.setLocation(l2);
            p2.send(Bukkit.getOnlinePlayers());
            p2.setLocation(l3);
            p2.send(Bukkit.getOnlinePlayers());
            p2.setLocation(l4);
            p2.send(Bukkit.getOnlinePlayers());
            p2.setLocation(l5);
            p2.send(Bukkit.getOnlinePlayers());

            l1.getWorld().playSound(l1, Sound.ENTITY_GENERIC_EXPLODE, 10, 1);

            for(Entity ens : en.getNearbyEntities(3, 3, 3)){
                if(ens instanceof Player){
                    Player player = (Player) ens;
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 0));
                }
            }

            en.remove();
            api.getGadgets().getInkBombs().remove(en);
            api.getGadgets().getInkBombTime().remove(en);
        }

        for(Entity en : api.getGadgets().getSwapTeleporter().keySet()){
            Particle p = new Particle(org.bukkit.Particle.SMOKE_LARGE, en.getLocation());
            p.setAmount(10);
            p.send(Bukkit.getOnlinePlayers());

            OMPlayer omp = api.getGadgets().getSwapTeleporter().get(en);
            Player player = omp.getPlayer();
            for(Entity e : en.getNearbyEntities(0.5, 1, 0.5)){
                if(!(e instanceof Player) || player == e)
                    continue;

                Player player2 = (Player) e;
                OMPlayer omp2 = OMPlayer.getOMPlayer(player2);

                if(omp.canReceiveVelocity() && omp2.canReceiveVelocity()){
                    if(omp2.hasPlayersEnabled()){
                        Location l1 = player.getLocation();
                        Location l2 = player2.getLocation();

                        player.teleport(l2);
                        player2.teleport(l1);

                        player.sendMessage(Messages.SWAP_TP.get(omp, omp2.getName()));
                        player2.sendMessage(Messages.SWAP_TP_PLAYER.get(omp2, omp.getName()));

                        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 5, 1);
                        player2.playSound(player2.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 5, 1);

                        api.getGadgets().getSwapTeleporter().remove(en);
                        en.remove();
                    }
                    else{
                        player.sendMessage(Messages.PLAYERS_DISABLED.get(omp, omp2.getName()));
                        api.getGadgets().getSwapTeleporter().remove(en);
                        en.remove();
                    }
                }
            }
        }
	}
}
