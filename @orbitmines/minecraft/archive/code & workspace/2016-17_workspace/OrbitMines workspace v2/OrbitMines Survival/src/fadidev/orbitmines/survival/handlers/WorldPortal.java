package fadidev.orbitmines.survival.handlers;

import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.List;

/**
 * Created by Fadi on 15-9-2016.
 */
public class WorldPortal {

    private World world;
    private List<Block> blocks;

    public WorldPortal(World world, List<Block> blocks){
        this.world = world;
        this.blocks = blocks;
    }

    public World getWorld() {
        return world;
    }

    public List<Block> getBlocks() {
        return blocks;
    }
}
