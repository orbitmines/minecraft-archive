package fadidev.orbitmines.skyblock.runnables;

import fadidev.orbitmines.api.runnables.OMRunnable;
import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.api.utils.WorldUtils;
import fadidev.orbitmines.skyblock.OrbitMinesSkyBlock;
import fadidev.orbitmines.skyblock.handlers.Island;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Fadi-Laptop on 16-9-2016.
 */
public class NetherSkyBlockRunnable extends OMRunnable {

    private OrbitMinesSkyBlock skyBlock;

    public NetherSkyBlockRunnable() {
        super(TimeUnit.SECOND, 15);

        skyBlock = OrbitMinesSkyBlock.getSkyBlock();
    }

    @Override
    public void run() {
        for(Island is : skyBlock.getIslands()){
            if(!is.isNetherGenerated())
                continue;
            
            Location l = is.getNetherLocation();

            if(Utils.RANDOM.nextBoolean()){
                List<Block> blocks = WorldUtils.getBlocksBetween(new Location(l.getWorld(), l.getX() -4, l.getY() +8, l.getZ() -3), new Location(l.getWorld(), l.getX() -37, l.getY() +8, l.getZ() +33));
                List<Block> newBlocks = new ArrayList<>();

                for(Block b : blocks){
                    if(b != null && b.getType() != Material.AIR){
                        Block b2 = b.getRelative(BlockFace.UP);

                        if(b2 != null && b2.getType() == Material.AIR){
                            newBlocks.add(b2);
                        }
                    }
                }

                if(newBlocks.size() > 0){
                    Block b = newBlocks.get(new Random().nextInt(newBlocks.size()));

                    Skeleton s = (Skeleton) b.getWorld().spawnEntity(b.getLocation(), EntityType.SKELETON);
                    s.setSkeletonType(Skeleton.SkeletonType.WITHER);

                    int amount = 0;
                    for(Entity en : s.getNearbyEntities(15, 15, 15)){
                        if(en instanceof Skeleton){
                            amount++;
                        }
                    }

                    if(amount > 2)
                        s.remove();
                }
            }
        }
    }
}
