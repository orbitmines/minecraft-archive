package fadidev.orbitmines.skyblock.runnables;

import fadidev.orbitmines.api.handlers.Cooldowns;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.chat.Title;
import fadidev.orbitmines.api.runnables.orbitmines.PlayerSecondRunnable;
import fadidev.orbitmines.skyblock.OrbitMinesSkyBlock;
import fadidev.orbitmines.skyblock.handlers.Island;
import fadidev.orbitmines.skyblock.handlers.SkyBlockMessages;
import fadidev.orbitmines.skyblock.handlers.SkyBlockPlayer;
import org.bukkit.entity.Player;

/**
 * Created by Fadi on 20-9-2016.
 */
public class SkyBlockPlayerSecondRunnable extends PlayerSecondRunnable {

    private OrbitMinesSkyBlock skyBlock;

    public SkyBlockPlayerSecondRunnable(){
        skyBlock = OrbitMinesSkyBlock.getSkyBlock();
    }

    @Override
    protected void run(OMPlayer omPlayer) {
        SkyBlockPlayer omp = (SkyBlockPlayer) omPlayer;
        Player p = omp.getPlayer();

        if(!omp.isOpMode() && p.getWorld().getName().equals(skyBlock.getLobby().getName()) && p.getLocation().distance(skyBlock.getSpawn()) >= 75){
            p.teleport(skyBlock.getSpawn());
        }

        if(omp.getCooldowns().containsKey(Cooldowns.TELEPORTING)){
            if(omp.onCooldown(Cooldowns.TELEPORTING)){
                int seconds = (int) ((Cooldowns.TELEPORTING.getCooldown(omp) /1000) - ((System.currentTimeMillis() - omp.getCooldown(Cooldowns.TELEPORTING)) / 1000));

                if(omp.getTeleportingTo() != null){
                    Island is = omp.getTeleportingTo();

                    if(omp.getIsland().getIslandId() == is.getIslandId()){
                        Title t = new Title("", "§7" + SkyBlockMessages.WORD_TELEPORTING_TO.get(omp) + " " + SkyBlockMessages.WORD_YOUR.get(omp) + " §dIsland§7 in §d" + seconds + "§7...", 0, 40, 0);
                        t.send(p);
                    }
                    else{
                        Title t = new Title("", "§7" + SkyBlockMessages.WORD_TELEPORTING_TO.get(omp) + " §dIsland " + is.getIslandId() + "§7 in §d" + seconds + "§7...", 0, 40, 0);
                        t.send(p);
                    }
                }
                else{
                    Title t = new Title("", "§7" + SkyBlockMessages.WORD_TELEPORTING_TO.get(omp) + " §6Spawn§7 in §6" + seconds + "§7...", 0, 40, 0);
                    t.send(p);
                }
            }
            else{
                omp.removeCooldown(Cooldowns.TELEPORTING);

                if(omp.getTeleportingTo() != null){
                    Island is = omp.getTeleportingTo();

                    if(omp.hasIsland() && omp.getIsland().getIslandId() == is.getIslandId()){
                        p.teleport(omp.getHomeLocation());

                        Title t = new Title("", "§7" + SkyBlockMessages.WORD_TELEPORTED_TO.get(omp) + " " + SkyBlockMessages.WORD_YOUR.get(omp) + " §dIsland§7!", 0, 40, 20);
                        t.send(p);
                    }
                    else{
                        p.teleport(is.getOwnersHomeLocation());

                        Title t = new Title("", "§7" + SkyBlockMessages.WORD_TELEPORTED_TO.get(omp) + " §dIsland " + is.getIslandId() + "§7!", 0, 40, 20);
                        t.send(p);
                    }
                    omp.setTeleportingTo(null);
                }
                else{
                    p.teleport(skyBlock.getSpawn());

                    Title t = new Title("", "§7" + SkyBlockMessages.WORD_TELEPORTED_TO.get(omp) + " §6Spawn§7!", 0, 40, 20);
                    t.send(p);
                }
            }
        }
    }

    @Override
    protected void giveLobbyItems(OMPlayer omPlayer) {

    }
}
