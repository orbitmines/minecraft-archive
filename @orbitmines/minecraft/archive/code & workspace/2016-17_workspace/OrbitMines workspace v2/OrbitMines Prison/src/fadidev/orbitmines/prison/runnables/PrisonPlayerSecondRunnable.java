package fadidev.orbitmines.prison.runnables;

import fadidev.orbitmines.api.handlers.Cooldowns;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.chat.Title;
import fadidev.orbitmines.api.runnables.orbitmines.PlayerSecondRunnable;
import fadidev.orbitmines.api.utils.UUIDUtils;
import fadidev.orbitmines.prison.OrbitMinesPrison;
import fadidev.orbitmines.prison.handlers.PrisonMessages;
import fadidev.orbitmines.prison.handlers.PrisonPlayer;
import org.bukkit.entity.Player;

/**
 * Created by Fadi on 19-9-2016.
 */
public class PrisonPlayerSecondRunnable extends PlayerSecondRunnable {

    private OrbitMinesPrison prison;

    public PrisonPlayerSecondRunnable(){
        prison = OrbitMinesPrison.getPrison();
    }

    @Override
    protected void run(OMPlayer omPlayer) {
        PrisonPlayer omp = (PrisonPlayer) omPlayer;
        Player p = omp.getPlayer();

        if(p.getWorld().getName().equals(prison.getLobby().getName())){
            if(!omp.isInPvP() && (p.getLocation().getY() <= 55 || p.getLocation().distance(prison.getSpawn()) >= 100 && !omp.isOpMode()))
                p.teleport(prison.getSpawn());

            if(omp.isInPvP() && !omp.isOpMode()){
                if(p.getAllowFlight()){
                    p.setFlying(false);
                    p.setAllowFlight(false);
                }

                if(omp.isDisguised())
                    omp.unDisguise();
            }
        }

        if(omp.getCooldowns().containsKey(Cooldowns.TELEPORTING)){
            if(omp.onCooldown(Cooldowns.TELEPORTING)){
                int seconds = (int) ((Cooldowns.TELEPORTING.getCooldown(omp) /1000) - ((System.currentTimeMillis() - omp.getCooldown(Cooldowns.TELEPORTING)) / 1000));

                if(omp.getTeleportingTo() == null) {
                    Title t = new Title("", "§7" + PrisonMessages.WORD_TELEPORTING_TO.get(omp) + " §6Spawn§7 in §6" + seconds + "§7...", 0, 40, 0);
                    t.send(p);
                }
                else{
                    if(omp.getTeleportingTo() == omp.getCell()) {
                        Title t = new Title("", "§7" + PrisonMessages.WORD_TELEPORTING_TO.get(omp) + " " + PrisonMessages.WORD_YOUR_SMALL.get(omp) + " §cCell§7 in §c" + seconds + "§7...", 0, 40, 0);
                        t.send(p);
                    }
                    else{
                        Title t = new Title("", "§7" + PrisonMessages.WORD_TELEPORTING_TO.get(omp) + " §c" + UUIDUtils.getName(omp.getTeleportingTo().getOwnerUUID()) + "'s Cell§7 in §c" + seconds + "§7...", 0, 40, 0);
                        t.send(p);
                    }
                }

                if(omp.isDisguised())
                    omp.unDisguise();
            }
            else{
                omp.removeCooldown(Cooldowns.TELEPORTING);

                if(omp.getTeleportingTo() == null) {
                    Title t = new Title("", "§7" + PrisonMessages.WORD_TELEPORTED_TO.get(omp) + " §6Spawn§7!", 0, 40, 20);
                    t.send(p);

                    p.teleport(prison.getSpawn());
                }
                else{
                    if(omp.getTeleportingTo() == omp.getCell()) {
                        Title t = new Title("", "§7" + PrisonMessages.WORD_TELEPORTED_TO.get(omp) + " " + PrisonMessages.WORD_YOUR_SMALL.get(omp) + " §cCell§7!", 0, 40, 0);
                        t.send(p);
                    }
                    else{
                        Title t = new Title("", "§7" + PrisonMessages.WORD_TELEPORTED_TO.get(omp) + " §c" + UUIDUtils.getName(omp.getTeleportingTo().getOwnerUUID()) + "'s Cell§7!", 0, 40, 0);
                        t.send(p);
                    }

                    p.teleport(omp.getTeleportingTo().getHomeLocation());
                    omp.setTeleportingTo(null);
                }
            }
        }
    }

    @Override
    protected void giveLobbyItems(OMPlayer omPlayer) {

    }
}
