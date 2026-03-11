package fadidev.orbitmines.skyblock.events;

import fadidev.orbitmines.api.handlers.Cooldowns;
import fadidev.orbitmines.api.handlers.chat.Title;
import fadidev.orbitmines.skyblock.OrbitMinesSkyBlock;
import fadidev.orbitmines.skyblock.handlers.Island;
import fadidev.orbitmines.skyblock.handlers.SkyBlockMessages;
import fadidev.orbitmines.skyblock.handlers.SkyBlockPlayer;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by Fadi on 20-9-2016.
 */
public class MoveEvent implements Listener {

    private OrbitMinesSkyBlock skyBlock;

    public MoveEvent(){
        skyBlock = OrbitMinesSkyBlock.getSkyBlock();
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        SkyBlockPlayer omp = SkyBlockPlayer.getSkyBlockPlayer(p);

        if(omp.isLoaded()){
            if(p.getWorld().getName().equals(skyBlock.getLobby().getName()) && p.getLocation().getY() <= 70){
                if(!omp.onCooldown(Cooldowns.MESSAGE)){
                    if(omp.hasIsland()){
                        p.teleport(omp.getHomeLocation());
                        p.setFallDistance(0F);
                        p.playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 5, 1);

                        Title t = new Title("", "§7" + SkyBlockMessages.WORD_TELEPORTED_TO.get(omp) + " " + SkyBlockMessages.WORD_YOUR.get(omp) + " §dIsland§7.", 20, 40, 20);
                        t.send(p);
                    }
                    else{
                        Island.generate(omp);
                    }

                    omp.resetCooldown(Cooldowns.MESSAGE);
                }
            }

            if(omp.getCooldowns().containsKey(Cooldowns.TELEPORTING)){
                Location l = omp.getTpLocation();
                if(p.getLocation().getX() == l.getX() && p.getLocation().getY() == l.getY() && p.getLocation().getZ() == l.getZ())
                    return;

                omp.removeCooldown(Cooldowns.TELEPORTING);

                if(omp.getTeleportingTo() != null){
                    Title t = new Title("", SkyBlockMessages.CANCELLED_TELEPORT.get(omp, "§dIsland"), 0, 40, 20);
                    t.send(p);

                    omp.setTeleportingTo(null);
                }
                else{
                    Title t = new Title("", SkyBlockMessages.CANCELLED_TELEPORT.get(omp, "§6Spawn"), 0, 40, 20);
                    t.send(p);
                }
            }
        }
    }
}
