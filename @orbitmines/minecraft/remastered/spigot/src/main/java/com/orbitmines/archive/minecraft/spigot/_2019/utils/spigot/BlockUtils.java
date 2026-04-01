package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import org.bukkit.Location;
import org.bukkit.block.*;
import org.bukkit.block.data.type.WallSign;

import java.util.ArrayList;
import java.util.List;

public class BlockUtils {

    private static final BlockFace[] touchingFaces = { BlockFace.UP, BlockFace.DOWN, BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH };

    public static boolean equals(Block block1, Block block2) {
        return equals(block1.getLocation(), block2.getLocation());
    }
    public static boolean equals(Location l1, Location l2) {
        return l1.getWorld().getName().equals(l2.getWorld().getName()) && l1.getBlockX() == l2.getBlockX() && l1.getBlockY() == l2.getBlockY() && l1.getBlockZ() == l2.getBlockZ();
    }

    public static List<Block> getBlocksBetween(Location l1, Location l2) {
        List<Block> blocks = new ArrayList<>();

        int topBlockX = (l1.getBlockX() < l2.getBlockX() ? l2.getBlockX() : l1.getBlockX());
        int bottomBlockX = (l1.getBlockX() > l2.getBlockX() ? l2.getBlockX() : l1.getBlockX());

        int topBlockY = (l1.getBlockY() < l2.getBlockY() ? l2.getBlockY() : l1.getBlockY());
        int bottomBlockY = (l1.getBlockY() > l2.getBlockY() ? l2.getBlockY() : l1.getBlockY());

        int topBlockZ = (l1.getBlockZ() < l2.getBlockZ() ? l2.getBlockZ() : l1.getBlockZ());
        int bottomBlockZ = (l1.getBlockZ() > l2.getBlockZ() ? l2.getBlockZ() : l1.getBlockZ());

        for (int x = bottomBlockX; x <= topBlockX; x++) {
            for (int z = bottomBlockZ; z <= topBlockZ; z++) {
                for (int y = bottomBlockY; y <= topBlockY; y++) {
                    Block block = l1.getWorld().getBlockAt(x, y, z);

                    blocks.add(block);
                }
            }
        }

        return blocks;
    }

    /* Get BlockFace direction based on 'lookAt' location */
    public static BlockFace getFacing(Location loc, Location lookAt) {
        double vX = loc.getX() - lookAt.getX();
        double vY = loc.getY() - lookAt.getY();
        double vZ = loc.getZ() - lookAt.getZ();

        double d = Math.sqrt(vX * vX + vY * vY + vZ * vZ);

        vX /= d;
        vZ /= d;

        float yaw = (float) (Math.toDegrees(Math.atan2(vZ, vX)) + 90);

        return getBlockFaceFromYaw(yaw);
    }

    public static BlockFace getBlockFaceFromYaw(float yaw) {
        if (yaw >= -22.5 && yaw <= 22.5)
            return BlockFace.SOUTH;
        else if (yaw >= 22.5 && yaw <= 67.5)
            return BlockFace.SOUTH_WEST;
        else if (yaw >= 67.5 && yaw <= 112.5)
            return BlockFace.WEST;
        else if (yaw >= 112.5 && yaw <= 157.5)
            return BlockFace.NORTH_WEST;
        else if (yaw >= -112.5 && yaw <= -67.5)
            return BlockFace.EAST;
        else if (yaw >= -67.5 && yaw <= -22.5)
            return BlockFace.SOUTH_EAST;
        else if (yaw >= -157.5 && yaw <= -112.5 || yaw >= 202.5)
            return BlockFace.NORTH_EAST;
        else
            return BlockFace.NORTH;
    }

    /* Get all blocks with the same type touching block */
    public static List<Block> getIdenticalBlocksTouching(Block block) {
        return addIdenticalBlocksTouching(block, new ArrayList<>());
    }
    private static List<Block> addIdenticalBlocksTouching(Block block, List<Block> blocks) {
        blocks.add(block);

        for (BlockFace blockFace : touchingFaces) {
            Block b = block.getRelative(blockFace);

            if (!blocks.contains(b) && b.getType() == block.getType())
                addIdenticalBlocksTouching(b, blocks);
        }

        return blocks;
    }

    /* Get chest sign is supposed to be linked to, behind the sign, or around it */
    public static Chest getChestAtSign(Location signLocation) {
        return getChestAtSign(signLocation.getBlock());
    }
    public static Chest getChestAtSign(Block sign) {
        /* First check chest behind sign, then we check for other nearby chests */
        if (sign.getState().getBlockData() instanceof WallSign) {
            WallSign signData = (WallSign) sign.getState().getBlockData();
            BlockFace opposite = signData.getFacing().getOppositeFace();

            Block block = sign.getRelative(opposite);

            if (block.getState() instanceof Chest)
                return (Chest) block.getState();
        }

        /* Otherwise switch through all nearby chest locations */
        for (BlockFace face : touchingFaces) {
            BlockState relative = sign.getRelative(face).getState();

            if (relative instanceof Chest)
                return (Chest) relative;
        }

        return null;
    }

    public static Sign getSignAround(Block block) {
        for (BlockFace face : touchingFaces) {
            BlockState relative = block.getRelative(face).getState();

            if (relative instanceof Sign)
                return (Sign) relative;
        }

        return null;
    }
}
