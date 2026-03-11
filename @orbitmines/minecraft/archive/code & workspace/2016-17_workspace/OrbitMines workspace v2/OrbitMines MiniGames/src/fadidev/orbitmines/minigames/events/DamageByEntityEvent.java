package fadidev.orbitmines.minigames.events;

import fadidev.orbitmines.api.handlers.Kit;
import fadidev.orbitmines.minigames.OrbitMinesMiniGames;
import fadidev.orbitmines.minigames.handlers.Arena;
import fadidev.orbitmines.minigames.handlers.MiniGamesMessages;
import fadidev.orbitmines.minigames.handlers.data.GhostAttackData;
import fadidev.orbitmines.minigames.handlers.data.UHCData;
import fadidev.orbitmines.minigames.handlers.players.MiniGamesPlayer;
import fadidev.orbitmines.minigames.utils.enums.State;
import fadidev.orbitmines.minigames.utils.enums.TicketType;
import org.bukkit.Effect;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import static fadidev.orbitmines.minigames.utils.enums.MiniGameType.SKYWARS;

/**
 * Created by Fadi on 1-10-2016.
 */
public class DamageByEntityEvent implements Listener {

    private OrbitMinesMiniGames miniGames;

    public DamageByEntityEvent(){
        miniGames = OrbitMinesMiniGames.getMiniGames();
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if(e.getEntity() instanceof Player && e.getDamager() instanceof Projectile){
            Player pE = (Player) e.getEntity();
            MiniGamesPlayer ompE = MiniGamesPlayer.getMiniGamesPlayer(pE);
            Arena arena = ompE.getArena();

            if(arena != null){
                if(arena.getType() == SKYWARS){
                    ProjectileSource ps = ((Projectile) e.getDamager()).getShooter();

                    if(ps instanceof Player){
                        Player pD = (Player) ps;
                        MiniGamesPlayer ompD = MiniGamesPlayer.getMiniGamesPlayer(pD);

                        ompE.getSkywarsPlayer().setLastProjectile(ompD);
                    }
                }
            }
            return;
        }
        else if(!(e.getDamager() instanceof Player)) {
            return;
        }

        Player pD = (Player) e.getDamager();
        MiniGamesPlayer ompD = MiniGamesPlayer.getMiniGamesPlayer(pD);
        Arena arena = ompD.getArena();

        if(arena == null)
            return;

        if(arena.isSpectator(ompD)){
            e.setCancelled(true);
        }
        else{
            if(arena.getState() != State.IN_GAME)
                e.setCancelled(true);
            
            switch(arena.getType()){
                case SURVIVAL_GAMES:
                    if(arena.getMinutes() == 19 && arena.getSeconds() >= 30){
                        Kit kitD = ompD.getSurvivalGamesPlayer().getKitSelected();
                        if(kitD != null && kitD.getName().equals(TicketType.RUNNER_KIT.getKit().getName())){
                            e.setCancelled(true);
                            pD.sendMessage(MiniGamesMessages.SG_RUNNER_DEAL_TO.get(ompD));
                        }
                        else if(e.getEntity() instanceof Player){
                            Player pE = (Player) e.getEntity();
                            MiniGamesPlayer ompE = MiniGamesPlayer.getMiniGamesPlayer(pE);
                            Kit kitE = ompE.getSurvivalGamesPlayer().getKitSelected();

                            if(kitE != null && kitE.getName().equals(TicketType.RUNNER_KIT.getKit().getName())){
                                e.setCancelled(true);
                                pD.sendMessage(MiniGamesMessages.SG_RUNNER_ATTACK.get(ompD, ompE.getName()));
                            }
                        }
                        else if(e.getEntity() instanceof Boat){
                            if(arena.isSpectator(ompD)){
                                e.setCancelled(true);
                            }
                        }
                    }
                    break;
                case ULTRA_HARD_CORE:
                    if(!((UHCData) arena.getData()).isInPvP() && e.getEntity() instanceof Player){
                        e.setCancelled(true);
                    }
                    else if(e.getEntity() instanceof Boat){
                        if(arena.isSpectator(ompD)){
                            e.setCancelled(true);
                        }
                    }
                    break;
                case SKYWARS:
                    if(e.getEntity() instanceof Player){
                        Player pE = (Player) e.getEntity();
                        MiniGamesPlayer ompE = MiniGamesPlayer.getMiniGamesPlayer(pE);

                        ompE.getSkywarsPlayer().setLastProjectile(ompD);
                    }
                    break;
                case CHICKEN_FIGHT:
                    if(e.getEntity() instanceof Player){
                        final Player pE = (Player) e.getEntity();
                        MiniGamesPlayer ompE = MiniGamesPlayer.getMiniGamesPlayer(pE);

                        pE.getWorld().playEffect(pE.getLocation(), Effect.STEP_SOUND, 152, 3);

                        final Vector v = ompE.getChickenFightPlayer().getVelocity(pE.getLocation().subtract(pD.getLocation()).toVector().normalize()).add(new Vector(0, ompE.getChickenFightPlayer().getKnockbackMotifier() / 4, 0));

                        new BukkitRunnable(){
                            public void run(){
                                pE.setVelocity(v);
                            }
                        }.runTaskLater(miniGames, 1);
                    }
                    break;
                case GHOST_ATTACK:
                    if(e.getEntity() instanceof Player){
                        Player pE = (Player) e.getEntity();
                        MiniGamesPlayer ompE = MiniGamesPlayer.getMiniGamesPlayer(pE);
                        GhostAttackData data = (GhostAttackData) arena.getData();

                        if(!data.isGhost(ompD) && !data.isGhost(ompE)){
                            e.setCancelled(true);
                        }
                        else if(data.isGhost(ompE)){
                            if(!data.isRevealed())
                                data.revealGhost();
                        }
                    }
                    break;
                case SPLEEF:
                    break;
                case SPLATCRAFT:
                    break;
            }
        }
    }
}
