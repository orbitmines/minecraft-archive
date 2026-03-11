package fadidev.orbitmines.survival.runnables;

import fadidev.orbitmines.api.handlers.Cooldowns;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.chat.Title;
import fadidev.orbitmines.api.runnables.orbitmines.PlayerSecondRunnable;
import fadidev.orbitmines.survival.OrbitMinesSurvival;
import fadidev.orbitmines.survival.handlers.SurvivalCooldowns;
import fadidev.orbitmines.survival.handlers.SurvivalMessages;
import fadidev.orbitmines.survival.handlers.SurvivalPlayer;
import org.bukkit.entity.Player;

/**
 * Created by Fadi on 15-9-2016.
 */
public class SurvivalPlayerSecondRunnable extends PlayerSecondRunnable {

    private OrbitMinesSurvival survival;

    public SurvivalPlayerSecondRunnable(){
        survival = OrbitMinesSurvival.getSurvival();
    }

    @Override
    protected void run(OMPlayer omPlayer) {
        SurvivalPlayer omp = (SurvivalPlayer) omPlayer;
        Player p = omp.getPlayer();

        if(p.getWorld().getName().equals(survival.getLobby().getName())){
            if(!omp.isOpMode() && omp.isInPvP() && p.getAllowFlight()){
                p.setFlying(false);
                p.setAllowFlight(false);
            }

            if(omp.isDisguised())
                omp.unDisguise();
        }

        if(omp.getCooldowns().containsKey(SurvivalCooldowns.PVP_CONFIRM)){
            if(omp.onCooldown(SurvivalCooldowns.PVP_CONFIRM)){
                int seconds = (int) ((SurvivalCooldowns.PVP_CONFIRM.getCooldown(omp) /1000) - ((System.currentTimeMillis() - omp.getCooldown(SurvivalCooldowns.PVP_CONFIRM)) / 1000));

                Title t = new Title("§7Type §a§l/confirm", SurvivalMessages.TIME_REMAINING.get(omp, "" + seconds), 0, 40, 0);
                t.send(p);
            }
            else{
                omp.removeCooldown(SurvivalCooldowns.PVP_CONFIRM);

                Title t = new Title("", SurvivalMessages.CANCELLED_PVP_TP.get(omp), 0, 40, 20);
                t.send(p);
            }
        }

        if(omp.getCooldowns().containsKey(Cooldowns.TELEPORTING)){
            if(omp.onCooldown(Cooldowns.TELEPORTING)){
                int seconds = (int) ((Cooldowns.TELEPORTING.getCooldown(omp) /1000) - ((System.currentTimeMillis() - omp.getCooldown(Cooldowns.TELEPORTING)) / 1000));
                if(omp.isHomeTeleporting()){
                    Title t = new Title("", "§7" + SurvivalMessages.WORD_TELEPORTING_TO.get(omp) + " §6" + omp.getTeleportingTo().getName() + "§7 in §6" + seconds + "§7...", 0, 40, 0);
                    t.send(p);
                }
                else if(omp.isWarpTeleporting()){
                    Title t = new Title("", "§7" + SurvivalMessages.WORD_TELEPORTING_TO.get(omp) + " §3" + omp.getWarpingTo().getName() + "§7 in §3" + seconds + "§7...", 0, 40, 0);
                    t.send(p);
                }
                else{
                    Title t = new Title("", "§7" + SurvivalMessages.WORD_TELEPORTING_TO.get(omp) + " §6Spawn§7 in §6" + seconds + "§7...", 0, 40, 0);
                    t.send(p);
                }
            }
            else{
                omp.removeCooldown(Cooldowns.TELEPORTING);

                if(omp.isHomeTeleporting()){
                    Title t = new Title("", "§7" + SurvivalMessages.WORD_TELEPORTED_TO.get(omp) + " §6" + omp.getTeleportingTo().getName() + "§7!", 0, 40, 20);
                    t.send(p);

                    omp.getTeleportingTo().teleportInstantly();
                    omp.setHomeTeleporting(false);
                    omp.setTeleportingTo(null);
                }
                else if(omp.isWarpTeleporting()){
                    Title t = new Title("", "§7" + SurvivalMessages.WORD_TELEPORTED_TO.get(omp) + " §3" + omp.getWarpingTo().getName() + "§7!", 0, 40, 20);
                    t.send(p);

                    omp.getWarpingTo().teleportInstantly(omp);
                    omp.setWarpTeleporting(false);
                    omp.setWarpingTo(null);
                }
                else{
                    Title t = new Title("", "§7" + SurvivalMessages.WORD_TELEPORTED_TO.get(omp) + " §6Spawn§7!", 0, 40, 20);
                    t.send(p);

                    omp.setBackLocation(p.getLocation());
                    p.teleport(survival.getSpawn());
                }
            }
        }
    }

    @Override
    protected void giveLobbyItems(OMPlayer omPlayer) {

    }
}
