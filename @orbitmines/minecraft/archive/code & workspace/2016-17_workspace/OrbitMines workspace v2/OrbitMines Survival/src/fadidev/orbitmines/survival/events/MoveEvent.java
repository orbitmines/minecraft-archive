package fadidev.orbitmines.survival.events;

import fadidev.orbitmines.api.handlers.Cooldowns;
import fadidev.orbitmines.api.handlers.chat.Title;
import fadidev.orbitmines.survival.OrbitMinesSurvival;
import fadidev.orbitmines.survival.handlers.Region;
import fadidev.orbitmines.survival.handlers.SurvivalMessages;
import fadidev.orbitmines.survival.handlers.SurvivalPlayer;
import fadidev.orbitmines.survival.handlers.WorldPortal;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.PlayerData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fadi-Laptop on 16-9-2016.
 */
public class MoveEvent implements Listener {

    private GriefPrevention griefPrevention;
    private OrbitMinesSurvival survival;
    private Map<Player, BukkitTask> fly;

    public MoveEvent(){
        survival = OrbitMinesSurvival.getSurvival();
        griefPrevention = GriefPrevention.instance;
        fly = new HashMap<>();
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        final Player p = e.getPlayer();
        final SurvivalPlayer omp = SurvivalPlayer.getSurvivalPlayer(p);

        if(omp.isLoaded()){
            if(!omp.isOpMode()){
                if(p.getWorld().getName().equals("SurvivalWorld_the_end")) {
                    if(p.isFlying()){
                        disableFly(p);

                        p.sendMessage(SurvivalMessages.CMD_FLY_DISABLED_IN_END.get(omp, "§lEnd"));
                    }
                }
                else if(p.getWorld().getName().equals("SurvivalWorld_nether")) {
                    if(p.isFlying()){
                        disableFly(p);

                        p.sendMessage(SurvivalMessages.CMD_FLY_DISABLED_IN_END.get(omp, "§c§lNether"));
                    }
                }
                else{
                    PlayerData playerData = griefPrevention.dataStore.getPlayerData(p.getUniqueId());
                    Claim claim = griefPrevention.dataStore.getClaimAt(p.getLocation(), true, playerData.lastClaim);

                    if(claim == null || claim.allowBuild(p, Material.AIR) != null){
                        if(p.isFlying()){
                            disableFly(p);

                            p.sendMessage(SurvivalMessages.CMD_FLY_ONLY_IN_CLAIM.get(omp));
                        }
                    }
                    else if(fly.containsKey(p)){
                        p.setAllowFlight(true);
                        p.setFlying(true);

                        survival.getNoFall().remove(p);
                        fly.get(p).cancel();
                        fly.remove(p);
                    }
                }
            }

            if(omp.isInPvP() && p.isFlying()){
                p.setFlying(false);
                p.setAllowFlight(false);
            }

            if(omp.getCooldowns().containsKey(Cooldowns.TELEPORTING)){
                Location l = omp.getTpLocation();
                if(p.getLocation().getX() == l.getX() && p.getLocation().getY() == l.getY() && p.getLocation().getZ() == l.getZ())
                    return;

                omp.removeCooldown(Cooldowns.TELEPORTING);

                if(omp.isHomeTeleporting()){
                    Title t = new Title("", SurvivalMessages.CANCELLED_TELEPORTATION.get(omp, "§6" + omp.getTeleportingTo().getName()), 0, 40, 20);
                    t.send(p);

                    omp.setHomeTeleporting(false);
                    omp.setTeleportingTo(null);
                }
                else if(omp.isWarpTeleporting()){
                    Title t = new Title("", SurvivalMessages.CANCELLED_TELEPORTATION.get(omp, "§3" + omp.getWarpingTo().getName()), 0, 40, 20);
                    t.send(p);

                    omp.setWarpTeleporting(false);
                    omp.setWarpingTo(null);
                }
                else{
                    Title t = new Title("", SurvivalMessages.CANCELLED_TELEPORTATION.get(omp, "§6Spawn"), 0, 40, 20);
                    t.send(p);
                }
            }

            if(p.getWorld().getName().equals(survival.getApi().getName())){
                Block b = p.getWorld().getBlockAt(p.getLocation());

                for(WorldPortal portal : survival.getWorldPortals()){
                    if(portal.getBlocks().contains(b)){
                        if(!omp.onCooldown(Cooldowns.PORTAL_USAGE)){
                            Region.random().teleport(omp);
                            omp.resetCooldown(Cooldowns.PORTAL_USAGE);
                        }
                        break;
                    }
                }
            }
        }
    }

    private void disableFly(final Player p){
        p.setFlying(false);
        p.setAllowFlight(false);
        survival.getNoFall().add(p);

        fly.put(p, new BukkitRunnable(){
            @Override
            public void run() {
                if(survival.getNoFall().contains(p))
                    survival.getNoFall().remove(p);
            }
        }.runTaskLater(survival, 150));
    }
}
