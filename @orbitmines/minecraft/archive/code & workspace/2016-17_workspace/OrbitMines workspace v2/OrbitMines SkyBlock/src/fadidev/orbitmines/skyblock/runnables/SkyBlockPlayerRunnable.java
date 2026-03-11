package fadidev.orbitmines.skyblock.runnables;

import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.runnables.orbitmines.PlayerRunnable;
import fadidev.orbitmines.skyblock.OrbitMinesSkyBlock;
import fadidev.orbitmines.skyblock.handlers.Island;
import fadidev.orbitmines.skyblock.handlers.SkyBlockCooldowns;
import fadidev.orbitmines.skyblock.handlers.SkyBlockPlayer;
import fadidev.orbitmines.skyblock.utils.SkyBlockUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Created by Fadi on 20-9-2016.
 */
public class SkyBlockPlayerRunnable extends PlayerRunnable {

    private OrbitMinesSkyBlock skyBlock;

    public SkyBlockPlayerRunnable(){
        skyBlock = OrbitMinesSkyBlock.getSkyBlock();
    }

    @Override
    protected void run(OMPlayer omPlayer) {
        SkyBlockPlayer omp = (SkyBlockPlayer) omPlayer;
        Player p = omp.getPlayer();

        if(omp.hasIsland() && p.getWorld().getName().equals(skyBlock.getSkyBlockWorld().getName()) && omp.onOwnIsland(p.getLocation(), false)){
            if(p.getLocation().getBlock().getType() == Material.PORTAL){
                if(!omp.getCooldowns().containsKey(SkyBlockCooldowns.NETHER_TELEPORTING)){
                    omp.resetCooldown(SkyBlockCooldowns.NETHER_TELEPORTING);
                }

                if(!omp.onCooldown(SkyBlockCooldowns.NETHER_TELEPORTING)){
                    omp.removeCooldown(SkyBlockCooldowns.NETHER_TELEPORTING);

                    Island is = omp.getIsland();
                    if(is.isNetherGenerated()){
                        p.teleport(is.getNetherHomeLocation());
                    }
                    else{
                        SkyBlockUtils.generateNetherIsland(is.getNetherLocation());
                        is.setNetherGenerated(true);
                        p.teleport(is.getNetherHomeLocation());
                    }
                }
            }
            else{
                if(omp.onCooldown(SkyBlockCooldowns.NETHER_TELEPORTING))
                    omp.removeCooldown(SkyBlockCooldowns.NETHER_TELEPORTING);
            }
        }
    }
}
