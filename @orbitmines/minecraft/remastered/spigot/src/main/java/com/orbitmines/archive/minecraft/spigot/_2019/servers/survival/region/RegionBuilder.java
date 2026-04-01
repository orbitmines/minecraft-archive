package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.region;

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.BlockDataUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ConsoleUtils;
import lombok.Getter;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Beacon;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.potion.PotionEffectType;

/*
* OrbitMines - @author Fadi Shawki - 2017
*/
public class RegionBuilder {

    //TODO DIFFERENT SETS PER BIOME

    private World world;
    private int id;

    @Getter private int inventoryX;
    @Getter private int inventoryY;

    @Getter private boolean underWaterRegion;

    @Getter private Location location;
    @Getter private Location beaconLocation;

    public RegionBuilder(World world, int id) {
        this.world = world;
        this.id = id;
    }

    public Location getFixedSpawnLocation() {
        return location.clone().add(0, 1, 0);
    }

    public void build() {
        RegionLocator locator = new RegionLocator(world, id);
        locator.locate();

        int x = locator.getX();
        int y = locator.locateY();
        int z = locator.getZ();
        location = new Location(world, x + 0.5, y, z + 0.5, 0, 0);
        inventoryX = locator.getInventoryX();
        inventoryY = locator.getInventoryY();

        underWaterRegion = locator.isUnderWaterRegion();

        ConsoleUtils.msg("Location found (Region #" + id + ")! (" + x + ", " + y + ", " + z + ", under_water: " + underWaterRegion + ")");

        Chunk chunk = world.getChunkAt(location);
        if (chunk == null) {
            ConsoleUtils.warn("Error while detecting chunk at '" + world.getName() + "': " + x + ", " + y + ", " + z + ".");
            return;
        }
        chunk.load();

        ConsoleUtils.msg("Building Region SurvivalSpawn...");

        Material barrier = underWaterRegion ? Material.CONDUIT : Material.BARRIER;
        Material air = underWaterRegion ? Material.WATER : Material.AIR;
        Material lamp = underWaterRegion ? Material.SEA_LANTERN : Material.REDSTONE_LAMP;
        Material planks = underWaterRegion ? Material.DARK_PRISMARINE : Material.DARK_OAK_PLANKS;
        Material light_planks = underWaterRegion ? Material.PRISMARINE_BRICKS : Material.SPRUCE_PLANKS;
        BlockDataUtils.FenceType fenceType = underWaterRegion ? BlockDataUtils.FenceType.BIRCH : BlockDataUtils.FenceType.DARK_OAK;
        BlockDataUtils.StairsType stairsType = underWaterRegion ? BlockDataUtils.StairsType.DARK_PRISMARINE : BlockDataUtils.StairsType.DARK_OAK;

        /* Generated with SchematicGenerator */
        BlockDataUtils.setBlock(world, x + 1, y, z, lamp);
        BlockDataUtils.setBlock(world, x - 1, y, z, lamp);
        BlockDataUtils.setBlock(world, x, y, z - 1, lamp);
        BlockDataUtils.setBlock(world, x, y, z + 1, lamp);

        BlockDataUtils.setBlock(world, x - 3, y - 1, z - 2, planks);
        BlockDataUtils.setBlock(world, x - 3, y, z - 2, planks);
        BlockDataUtils.setFence(world, x - 3, y + 1, z - 2, fenceType, underWaterRegion);
        BlockDataUtils.setBlock(world, x - 3, y + 2, z - 2, air);
        BlockDataUtils.setBlock(world, x - 3, y + 3, z - 2, air);
        BlockDataUtils.setBlock(world, x - 3, y + 4, z - 2, air);
        BlockDataUtils.setStairs(world, x - 3, y - 2, z - 1, stairsType, Stairs.Shape.STRAIGHT, Bisected.Half.TOP, BlockFace.EAST, underWaterRegion);
        BlockDataUtils.setBlock(world, x - 3, y - 1, z - 1, planks);
        BlockDataUtils.setBlock(world, x - 3, y, z - 1, planks);
        BlockDataUtils.setFence(world, x - 3, y + 1, z - 1, fenceType, underWaterRegion);
        BlockDataUtils.setBlock(world, x - 3, y + 2, z - 1, air);
        BlockDataUtils.setBlock(world, x - 3, y + 3, z - 1, air);
        BlockDataUtils.setBlock(world, x - 3, y + 4, z - 1, air);
        BlockDataUtils.setStairs(world, x - 3, y - 2, z, stairsType, Stairs.Shape.STRAIGHT, Bisected.Half.TOP, BlockFace.EAST, underWaterRegion);
        BlockDataUtils.setBlock(world, x - 3, y - 1, z, planks);
        BlockDataUtils.setBlock(world, x - 3, y, z, light_planks);
        BlockDataUtils.setBlock(world, x - 3, y + 1, z, air);
        BlockDataUtils.setBlock(world, x - 3, y + 2, z, air);
        BlockDataUtils.setBlock(world, x - 3, y + 3, z, air);
        BlockDataUtils.setBlock(world, x - 3, y + 4, z, air);
        BlockDataUtils.setStairs(world, x - 3, y - 2, z + 1, stairsType, Stairs.Shape.STRAIGHT, Bisected.Half.TOP, BlockFace.EAST, underWaterRegion);
        BlockDataUtils.setBlock(world, x - 3, y - 1, z + 1, planks);
        BlockDataUtils.setBlock(world, x - 3, y, z + 1, planks);
        BlockDataUtils.setFence(world, x - 3, y + 1, z + 1, fenceType, underWaterRegion);
        BlockDataUtils.setBlock(world, x - 3, y + 2, z + 1, air);
        BlockDataUtils.setBlock(world, x - 3, y + 3, z + 1, air);
        BlockDataUtils.setBlock(world, x - 3, y + 4, z + 1, air);
        BlockDataUtils.setBlock(world, x - 3, y - 1, z + 2, planks);
        BlockDataUtils.setBlock(world, x - 3, y, z + 2, planks);
        BlockDataUtils.setFence(world, x - 3, y + 1, z + 2, fenceType, underWaterRegion);
        BlockDataUtils.setBlock(world, x - 3, y + 2, z + 2, air);
        BlockDataUtils.setBlock(world, x - 3, y + 3, z + 2, air);
        BlockDataUtils.setBlock(world, x - 3, y + 4, z + 2, air);
        BlockDataUtils.setBlock(world, x - 2, y - 2, z - 3, air);
        BlockDataUtils.setBlock(world, x - 2, y - 1, z - 3, planks);
        BlockDataUtils.setBlock(world, x - 2, y, z - 3, planks);
        BlockDataUtils.setFence(world, x - 2, y + 1, z - 3, fenceType, underWaterRegion);
        BlockDataUtils.setBlock(world, x - 2, y + 2, z - 3, air);
        BlockDataUtils.setBlock(world, x - 2, y + 3, z - 3, air);
        BlockDataUtils.setBlock(world, x - 2, y + 4, z - 3, air);
        BlockDataUtils.setBlock(world, x - 2, y - 2, z - 2, planks);
        BlockDataUtils.setBlock(world, x - 2, y - 1, z - 2, planks);
        BlockDataUtils.setBlock(world, x - 2, y, z - 2, planks);
        BlockDataUtils.setFence(world, x - 2, y + 1, z - 2, fenceType, underWaterRegion);
        BlockDataUtils.setBlock(world, x - 2, y + 2, z - 2, air);
        BlockDataUtils.setBlock(world, x - 2, y + 3, z - 2, air);
        BlockDataUtils.setBlock(world, x - 2, y + 4, z - 2, air);
        BlockDataUtils.setBlock(world, x - 2, y - 3, z - 1, planks);
        BlockDataUtils.setBlock(world, x - 2, y - 2, z - 1, planks);
        BlockDataUtils.setBlock(world, x - 2, y - 1, z - 1, planks);
        BlockDataUtils.setBlock(world, x - 2, y, z - 1, light_planks);
        BlockDataUtils.setBlock(world, x - 2, y + 1, z - 1, air);
        BlockDataUtils.setBlock(world, x - 2, y + 2, z - 1, air);
        BlockDataUtils.setBlock(world, x - 2, y + 3, z - 1, air);
        BlockDataUtils.setBlock(world, x - 2, y + 4, z - 1, air);
        BlockDataUtils.setBlock(world, x - 2, y - 3, z, planks);
        BlockDataUtils.setBlock(world, x - 2, y - 2, z, planks);
        BlockDataUtils.setBlock(world, x - 2, y - 1, z, planks);
        BlockDataUtils.setBlock(world, x - 2, y, z, light_planks);
        BlockDataUtils.setBlock(world, x - 2, y + 1, z, air);
        BlockDataUtils.setBlock(world, x - 2, y + 2, z, air);
        BlockDataUtils.setBlock(world, x - 2, y + 3, z, air);
        BlockDataUtils.setBlock(world, x - 2, y + 4, z, air);
        BlockDataUtils.setBlock(world, x - 2, y - 3, z + 1, planks);
        BlockDataUtils.setBlock(world, x - 2, y - 2, z + 1, planks);
        BlockDataUtils.setBlock(world, x - 2, y - 1, z + 1, planks);
        BlockDataUtils.setBlock(world, x - 2, y, z + 1, light_planks);
        BlockDataUtils.setBlock(world, x - 2, y + 1, z + 1, air);
        BlockDataUtils.setBlock(world, x - 2, y + 2, z + 1, air);
        BlockDataUtils.setBlock(world, x - 2, y + 3, z + 1, air);
        BlockDataUtils.setBlock(world, x - 2, y + 4, z + 1, air);
        BlockDataUtils.setBlock(world, x - 2, y - 2, z + 2, planks);
        BlockDataUtils.setBlock(world, x - 2, y - 1, z + 2, planks);
        BlockDataUtils.setBlock(world, x - 2, y, z + 2, planks);
        BlockDataUtils.setFence(world, x - 2, y + 1, z + 2, fenceType, underWaterRegion);
        BlockDataUtils.setBlock(world, x - 2, y + 2, z + 2, air);
        BlockDataUtils.setBlock(world, x - 2, y + 3, z + 2, air);
        BlockDataUtils.setBlock(world, x - 2, y + 4, z + 2, air);
        BlockDataUtils.setBlock(world, x - 2, y - 1, z + 3, planks);
        BlockDataUtils.setBlock(world, x - 2, y, z + 3, planks);
        BlockDataUtils.setFence(world, x - 2, y + 1, z + 3, fenceType, underWaterRegion);
        BlockDataUtils.setBlock(world, x - 2, y + 2, z + 3, air);
        BlockDataUtils.setBlock(world, x - 2, y + 3, z + 3, air);
        BlockDataUtils.setBlock(world, x - 2, y + 4, z + 3, air);
        BlockDataUtils.setStairs(world, x - 1, y - 2, z - 3, stairsType, Stairs.Shape.STRAIGHT, Bisected.Half.TOP, BlockFace.SOUTH, underWaterRegion);
        BlockDataUtils.setBlock(world, x - 1, y - 1, z - 3, planks);
        BlockDataUtils.setBlock(world, x - 1, y, z - 3, planks);
        BlockDataUtils.setFence(world, x - 1, y + 1, z - 3, fenceType, underWaterRegion);
        BlockDataUtils.setBlock(world, x - 1, y + 2, z - 3, air);
        BlockDataUtils.setBlock(world, x - 1, y + 3, z - 3, air);
        BlockDataUtils.setBlock(world, x - 1, y + 4, z - 3, air);
        BlockDataUtils.setBlock(world, x - 1, y - 3, z - 2, planks);
        BlockDataUtils.setBlock(world, x - 1, y - 2, z - 2, planks);
        BlockDataUtils.setBlock(world, x - 1, y - 1, z - 2, planks);
        BlockDataUtils.setBlock(world, x - 1, y, z - 2, light_planks);
        BlockDataUtils.setBlock(world, x - 1, y + 1, z - 2, air);
        BlockDataUtils.setBlock(world, x - 1, y + 2, z - 2, air);
        BlockDataUtils.setBlock(world, x - 1, y + 3, z - 2, air);
        BlockDataUtils.setBlock(world, x - 1, y + 4, z - 2, air);
        BlockDataUtils.setBlock(world, x - 1, y - 3, z - 1, planks);
        BlockDataUtils.setBlock(world, x - 1, y - 2, z - 1, Material.EMERALD_BLOCK);
        BlockDataUtils.setBlock(world, x - 1, y - 1, z - 1, planks);
        BlockDataUtils.setBlock(world, x - 1, y, z - 1, light_planks);
        BlockDataUtils.setBlock(world, x - 1, y + 1, z - 1, air);
        BlockDataUtils.setBlock(world, x - 1, y + 2, z - 1, air);
        BlockDataUtils.setBlock(world, x - 1, y + 3, z - 1, air);
        BlockDataUtils.setBlock(world, x - 1, y + 4, z - 1, air);
        BlockDataUtils.setBlock(world, x - 1, y - 3, z, planks);
        BlockDataUtils.setBlock(world, x - 1, y - 2, z, Material.EMERALD_BLOCK);
        BlockDataUtils.setBlock(world, x - 1, y - 1, z, Material.REDSTONE_BLOCK);
        BlockDataUtils.setBlock(world, x - 1, y + 1, z, air);
        BlockDataUtils.setBlock(world, x - 1, y + 2, z, air);
        BlockDataUtils.setBlock(world, x - 1, y + 3, z, air);
        BlockDataUtils.setBlock(world, x - 1, y - 3, z + 1, planks);
        BlockDataUtils.setBlock(world, x - 1, y - 2, z + 1, Material.EMERALD_BLOCK);
        BlockDataUtils.setBlock(world, x - 1, y - 1, z + 1, planks);
        BlockDataUtils.setBlock(world, x - 1, y, z + 1, light_planks);
        BlockDataUtils.setBlock(world, x - 1, y + 1, z + 1, air);
        BlockDataUtils.setBlock(world, x - 1, y + 2, z + 1, air);
        BlockDataUtils.setBlock(world, x - 1, y + 3, z + 1, air);
        BlockDataUtils.setBlock(world, x - 1, y + 4, z + 1, air);
        BlockDataUtils.setBlock(world, x - 1, y - 3, z + 2, planks);
        BlockDataUtils.setBlock(world, x - 1, y - 2, z + 2, planks);
        BlockDataUtils.setBlock(world, x - 1, y - 1, z + 2, planks);
        BlockDataUtils.setBlock(world, x - 1, y, z + 2, light_planks);
        BlockDataUtils.setBlock(world, x - 1, y + 1, z + 2, air);
        BlockDataUtils.setBlock(world, x - 1, y + 2, z + 2, air);
        BlockDataUtils.setBlock(world, x - 1, y + 3, z + 2, air);
        BlockDataUtils.setBlock(world, x - 1, y + 4, z + 2, air);
        BlockDataUtils.setStairs(world, x - 1, y - 2, z + 3, stairsType, Stairs.Shape.STRAIGHT, Bisected.Half.TOP, BlockFace.NORTH, underWaterRegion);
        BlockDataUtils.setBlock(world, x - 1, y - 1, z + 3, planks);
        BlockDataUtils.setBlock(world, x - 1, y, z + 3, planks);
        BlockDataUtils.setFence(world, x - 1, y + 1, z + 3, fenceType, underWaterRegion);
        BlockDataUtils.setBlock(world, x - 1, y + 2, z + 3, air);
        BlockDataUtils.setBlock(world, x - 1, y + 3, z + 3, air);
        BlockDataUtils.setBlock(world, x - 1, y + 4, z + 3, air);
        BlockDataUtils.setStairs(world, x, y - 2, z - 3, stairsType, Stairs.Shape.STRAIGHT, Bisected.Half.TOP, BlockFace.SOUTH, underWaterRegion);
        BlockDataUtils.setBlock(world, x, y - 1, z - 3, planks);
        BlockDataUtils.setBlock(world, x, y, z - 3, light_planks);
        BlockDataUtils.setBlock(world, x, y + 1, z - 3, air);
        BlockDataUtils.setBlock(world, x, y + 2, z - 3, air);
        BlockDataUtils.setBlock(world, x, y + 3, z - 3, air);
        BlockDataUtils.setBlock(world, x, y + 4, z - 3, air);
        BlockDataUtils.setBlock(world, x, y - 3, z - 2, planks);
        BlockDataUtils.setBlock(world, x, y - 2, z - 2, planks);
        BlockDataUtils.setBlock(world, x, y - 1, z - 2, planks);
        BlockDataUtils.setBlock(world, x, y, z - 2, light_planks);
        BlockDataUtils.setBlock(world, x, y + 1, z - 2, air);
        BlockDataUtils.setBlock(world, x, y + 2, z - 2, air);
        BlockDataUtils.setBlock(world, x, y + 3, z - 2, air);
        BlockDataUtils.setBlock(world, x, y + 4, z - 2, air);
        BlockDataUtils.setBlock(world, x, y - 3, z - 1, planks);
        BlockDataUtils.setBlock(world, x, y - 2, z - 1, Material.EMERALD_BLOCK);
        BlockDataUtils.setBlock(world, x, y - 1, z - 1, Material.REDSTONE_BLOCK);
        BlockDataUtils.setBlock(world, x, y + 1, z - 1, air);
        BlockDataUtils.setBlock(world, x, y + 2, z - 1, air);
        BlockDataUtils.setBlock(world, x, y + 3, z - 1, air);
        BlockDataUtils.setBlock(world, x, y - 3, z, planks);
        BlockDataUtils.setBlock(world, x, y - 2, z, Material.EMERALD_BLOCK);

        {
            Block beaconBlock = BlockDataUtils.setBlock(world, x, y - 1, z, Material.BEACON);
            Beacon beacon = (Beacon) beaconBlock.getState();
            beacon.setPrimaryEffect(underWaterRegion ? PotionEffectType.CONDUIT_POWER : PotionEffectType.SPEED);
            beacon.update(true);

            this.beaconLocation = beaconBlock.getLocation();
        }

        BlockDataUtils.setBlock(world, x, y, z, Material.LIME_STAINED_GLASS);
        BlockDataUtils.setBlock(world, x, y + 1, z, air);
        BlockDataUtils.setBlock(world, x, y + 2, z, air);
        BlockDataUtils.setBlock(world, x, y + 3, z, air);

        BlockDataUtils.setBlock(world, x, y + 3, z, barrier);

        BlockDataUtils.setBlock(world, x, y - 3, z + 1, planks);
        BlockDataUtils.setBlock(world, x, y - 2, z + 1, Material.EMERALD_BLOCK);
        BlockDataUtils.setBlock(world, x, y - 1, z + 1, Material.REDSTONE_BLOCK);
        BlockDataUtils.setBlock(world, x, y + 1, z + 1, air);
        BlockDataUtils.setBlock(world, x, y + 2, z + 1, air);
        BlockDataUtils.setBlock(world, x, y + 3, z + 1, air);
        BlockDataUtils.setBlock(world, x, y - 3, z + 2, planks);
        BlockDataUtils.setBlock(world, x, y - 2, z + 2, planks);
        BlockDataUtils.setBlock(world, x, y - 1, z + 2, planks);
        BlockDataUtils.setBlock(world, x, y, z + 2, light_planks);
        BlockDataUtils.setBlock(world, x, y + 1, z + 2, air);
        BlockDataUtils.setBlock(world, x, y + 2, z + 2, air);
        BlockDataUtils.setBlock(world, x, y + 3, z + 2, air);
        BlockDataUtils.setBlock(world, x, y + 4, z + 2, air);
        BlockDataUtils.setStairs(world, x, y - 2, z + 3, stairsType, Stairs.Shape.STRAIGHT, Bisected.Half.TOP, BlockFace.NORTH, underWaterRegion);
        BlockDataUtils.setBlock(world, x, y - 1, z + 3, planks);
        BlockDataUtils.setBlock(world, x, y, z + 3, light_planks);
        BlockDataUtils.setBlock(world, x, y + 1, z + 3, air);
        BlockDataUtils.setBlock(world, x, y + 2, z + 3, air);
        BlockDataUtils.setBlock(world, x, y + 3, z + 3, air);
        BlockDataUtils.setBlock(world, x, y + 4, z + 3, air);
        BlockDataUtils.setStairs(world, x + 1, y - 2, z - 3, stairsType, Stairs.Shape.STRAIGHT, Bisected.Half.TOP, BlockFace.SOUTH, underWaterRegion);
        BlockDataUtils.setBlock(world, x + 1, y - 1, z - 3, planks);
        BlockDataUtils.setBlock(world, x + 1, y, z - 3, planks);
        BlockDataUtils.setFence(world, x + 1, y + 1, z - 3, fenceType, underWaterRegion);
        BlockDataUtils.setBlock(world, x + 1, y + 2, z - 3, air);
        BlockDataUtils.setBlock(world, x + 1, y + 3, z - 3, air);
        BlockDataUtils.setBlock(world, x + 1, y + 4, z - 3, air);
        BlockDataUtils.setBlock(world, x + 1, y - 3, z - 2, planks);
        BlockDataUtils.setBlock(world, x + 1, y - 2, z - 2, planks);
        BlockDataUtils.setBlock(world, x + 1, y - 1, z - 2, planks);
        BlockDataUtils.setBlock(world, x + 1, y, z - 2, light_planks);
        BlockDataUtils.setBlock(world, x + 1, y + 1, z - 2, air);
        BlockDataUtils.setBlock(world, x + 1, y + 2, z - 2, air);
        BlockDataUtils.setBlock(world, x + 1, y + 3, z - 2, air);
        BlockDataUtils.setBlock(world, x + 1, y + 4, z - 2, air);
        BlockDataUtils.setBlock(world, x + 1, y - 3, z - 1, planks);
        BlockDataUtils.setBlock(world, x + 1, y - 2, z - 1, Material.EMERALD_BLOCK);
        BlockDataUtils.setBlock(world, x + 1, y - 1, z - 1, planks);
        BlockDataUtils.setBlock(world, x + 1, y, z - 1, light_planks);
        BlockDataUtils.setBlock(world, x + 1, y + 1, z - 1, air);
        BlockDataUtils.setBlock(world, x + 1, y + 2, z - 1, air);
        BlockDataUtils.setBlock(world, x + 1, y + 3, z - 1, air);
        BlockDataUtils.setBlock(world, x + 1, y + 4, z - 1, air);
        BlockDataUtils.setBlock(world, x + 1, y - 3, z, planks);
        BlockDataUtils.setBlock(world, x + 1, y - 2, z, Material.EMERALD_BLOCK);
        BlockDataUtils.setBlock(world, x + 1, y - 1, z, Material.REDSTONE_BLOCK);
        BlockDataUtils.setBlock(world, x + 1, y + 1, z, air);
        BlockDataUtils.setBlock(world, x + 1, y + 2, z, air);
        BlockDataUtils.setBlock(world, x + 1, y + 3, z, air);
        BlockDataUtils.setBlock(world, x + 1, y - 3, z + 1, planks);
        BlockDataUtils.setBlock(world, x + 1, y - 2, z + 1, Material.EMERALD_BLOCK);
        BlockDataUtils.setBlock(world, x + 1, y - 1, z + 1, planks);
        BlockDataUtils.setBlock(world, x + 1, y, z + 1, light_planks);
        BlockDataUtils.setBlock(world, x + 1, y + 1, z + 1, air);
        BlockDataUtils.setBlock(world, x + 1, y + 2, z + 1, air);
        BlockDataUtils.setBlock(world, x + 1, y + 3, z + 1, air);
        BlockDataUtils.setBlock(world, x + 1, y + 4, z + 1, air);
        BlockDataUtils.setBlock(world, x + 1, y - 3, z + 2, planks);
        BlockDataUtils.setBlock(world, x + 1, y - 2, z + 2, planks);
        BlockDataUtils.setBlock(world, x + 1, y - 1, z + 2, planks);
        BlockDataUtils.setBlock(world, x + 1, y, z + 2, light_planks);
        BlockDataUtils.setBlock(world, x + 1, y + 1, z + 2, air);
        BlockDataUtils.setBlock(world, x + 1, y + 2, z + 2, air);
        BlockDataUtils.setBlock(world, x + 1, y + 3, z + 2, air);
        BlockDataUtils.setBlock(world, x + 1, y + 4, z + 2, air);
        BlockDataUtils.setStairs(world, x + 1, y - 2, z + 3, stairsType, Stairs.Shape.STRAIGHT, Bisected.Half.TOP, BlockFace.NORTH, underWaterRegion);
        BlockDataUtils.setBlock(world, x + 1, y - 1, z + 3, planks);
        BlockDataUtils.setBlock(world, x + 1, y, z + 3, planks);
        BlockDataUtils.setFence(world, x + 1, y + 1, z + 3, fenceType, underWaterRegion);
        BlockDataUtils.setBlock(world, x + 1, y + 2, z + 3, air);
        BlockDataUtils.setBlock(world, x + 1, y + 3, z + 3, air);
        BlockDataUtils.setBlock(world, x + 1, y + 4, z + 3, air);
        BlockDataUtils.setBlock(world, x + 2, y - 1, z - 3, planks);
        BlockDataUtils.setBlock(world, x + 2, y, z - 3, planks);
        BlockDataUtils.setFence(world, x + 2, y + 1, z - 3, fenceType, underWaterRegion);
        BlockDataUtils.setBlock(world, x + 2, y + 2, z - 3, air);
        BlockDataUtils.setBlock(world, x + 2, y + 3, z - 3, air);
        BlockDataUtils.setBlock(world, x + 2, y + 4, z - 3, air);
        BlockDataUtils.setBlock(world, x + 2, y - 2, z - 2, planks);
        BlockDataUtils.setBlock(world, x + 2, y - 1, z - 2, planks);
        BlockDataUtils.setBlock(world, x + 2, y, z - 2, planks);
        BlockDataUtils.setFence(world, x + 2, y + 1, z - 2, fenceType, underWaterRegion);
        BlockDataUtils.setBlock(world, x + 2, y + 2, z - 2, air);
        BlockDataUtils.setBlock(world, x + 2, y + 3, z - 2, air);
        BlockDataUtils.setBlock(world, x + 2, y + 4, z - 2, air);
        BlockDataUtils.setBlock(world, x + 2, y - 3, z - 1, planks);
        BlockDataUtils.setBlock(world, x + 2, y - 2, z - 1, planks);
        BlockDataUtils.setBlock(world, x + 2, y - 1, z - 1, planks);
        BlockDataUtils.setBlock(world, x + 2, y, z - 1, light_planks);
        BlockDataUtils.setBlock(world, x + 2, y + 1, z - 1, air);
        BlockDataUtils.setBlock(world, x + 2, y + 2, z - 1, air);
        BlockDataUtils.setBlock(world, x + 2, y + 3, z - 1, air);
        BlockDataUtils.setBlock(world, x + 2, y + 4, z - 1, air);
        BlockDataUtils.setBlock(world, x + 2, y - 3, z, planks);
        BlockDataUtils.setBlock(world, x + 2, y - 2, z, planks);
        BlockDataUtils.setBlock(world, x + 2, y - 1, z, planks);
        BlockDataUtils.setBlock(world, x + 2, y, z, light_planks);
        BlockDataUtils.setBlock(world, x + 2, y + 1, z, air);
        BlockDataUtils.setBlock(world, x + 2, y + 2, z, air);
        BlockDataUtils.setBlock(world, x + 2, y + 3, z, air);
        BlockDataUtils.setBlock(world, x + 2, y + 4, z, air);
        BlockDataUtils.setBlock(world, x + 2, y - 3, z + 1, planks);
        BlockDataUtils.setBlock(world, x + 2, y - 2, z + 1, planks);
        BlockDataUtils.setBlock(world, x + 2, y - 1, z + 1, planks);
        BlockDataUtils.setBlock(world, x + 2, y, z + 1, light_planks);
        BlockDataUtils.setBlock(world, x + 2, y + 1, z + 1, air);
        BlockDataUtils.setBlock(world, x + 2, y + 2, z + 1, air);
        BlockDataUtils.setBlock(world, x + 2, y + 3, z + 1, air);
        BlockDataUtils.setBlock(world, x + 2, y + 4, z + 1, air);
        BlockDataUtils.setBlock(world, x + 2, y - 2, z + 2, planks);
        BlockDataUtils.setBlock(world, x + 2, y - 1, z + 2, planks);
        BlockDataUtils.setBlock(world, x + 2, y, z + 2, planks);
        BlockDataUtils.setFence(world, x + 2, y + 1, z + 2, fenceType, underWaterRegion);
        BlockDataUtils.setBlock(world, x + 2, y + 2, z + 2, air);
        BlockDataUtils.setBlock(world, x + 2, y + 3, z + 2, air);
        BlockDataUtils.setBlock(world, x + 2, y + 4, z + 2, air);
        BlockDataUtils.setBlock(world, x + 2, y - 1, z + 3, planks);
        BlockDataUtils.setBlock(world, x + 2, y, z + 3, planks);
        BlockDataUtils.setFence(world, x + 2, y + 1, z + 3, fenceType, underWaterRegion);
        BlockDataUtils.setBlock(world, x + 2, y + 2, z + 3, air);
        BlockDataUtils.setBlock(world, x + 2, y + 3, z + 3, air);
        BlockDataUtils.setBlock(world, x + 2, y + 4, z + 3, air);
        BlockDataUtils.setBlock(world, x + 3, y - 1, z - 2, planks);
        BlockDataUtils.setBlock(world, x + 3, y, z - 2, planks);
        BlockDataUtils.setFence(world, x + 3, y + 1, z - 2, fenceType, underWaterRegion);
        BlockDataUtils.setBlock(world, x + 3, y + 2, z - 2, air);
        BlockDataUtils.setBlock(world, x + 3, y + 3, z - 2, air);
        BlockDataUtils.setBlock(world, x + 3, y + 4, z - 2, air);
        BlockDataUtils.setStairs(world, x + 3, y - 2, z - 1, stairsType, Stairs.Shape.STRAIGHT, Bisected.Half.TOP, BlockFace.WEST, underWaterRegion);
        BlockDataUtils.setBlock(world, x + 3, y - 1, z - 1, planks);
        BlockDataUtils.setBlock(world, x + 3, y, z - 1, planks);
        BlockDataUtils.setFence(world, x + 3, y + 1, z - 1, fenceType, underWaterRegion);
        BlockDataUtils.setBlock(world, x + 3, y + 2, z - 1, air);
        BlockDataUtils.setBlock(world, x + 3, y + 3, z - 1, air);
        BlockDataUtils.setBlock(world, x + 3, y + 4, z - 1, air);
        BlockDataUtils.setStairs(world, x + 3, y - 2, z, stairsType, Stairs.Shape.STRAIGHT, Bisected.Half.TOP, BlockFace.WEST, underWaterRegion);
        BlockDataUtils.setBlock(world, x + 3, y - 1, z, planks);
        BlockDataUtils.setBlock(world, x + 3, y, z, light_planks);
        BlockDataUtils.setBlock(world, x + 3, y + 1, z, air);
        BlockDataUtils.setBlock(world, x + 3, y + 2, z, air);
        BlockDataUtils.setBlock(world, x + 3, y + 3, z, air);
        BlockDataUtils.setBlock(world, x + 3, y + 4, z, air);
        BlockDataUtils.setStairs(world, x + 3, y - 2, z + 1, stairsType, Stairs.Shape.STRAIGHT, Bisected.Half.TOP, BlockFace.WEST, underWaterRegion);
        BlockDataUtils.setBlock(world, x + 3, y - 1, z + 1, planks);
        BlockDataUtils.setBlock(world, x + 3, y, z + 1, planks);
        BlockDataUtils.setFence(world, x + 3, y + 1, z + 1, fenceType, underWaterRegion);
        BlockDataUtils.setBlock(world, x + 3, y + 2, z + 1, air);
        BlockDataUtils.setBlock(world, x + 3, y + 3, z + 1, air);
        BlockDataUtils.setBlock(world, x + 3, y + 4, z + 1, air);
        BlockDataUtils.setBlock(world, x + 3, y - 1, z + 2, planks);
        BlockDataUtils.setBlock(world, x + 3, y, z + 2, planks);
        BlockDataUtils.setFence(world, x + 3, y + 1, z + 2, fenceType, underWaterRegion);
        BlockDataUtils.setBlock(world, x + 3, y + 2, z + 2, air);
        BlockDataUtils.setBlock(world, x + 3, y + 3, z + 2, air);
        BlockDataUtils.setBlock(world, x + 3, y + 4, z + 2, air);

        for (Block block : new Block[] { BlockDataUtils.setWallSign(world, x - 1, y + 3, z, Material.OAK_WALL_SIGN, BlockFace.WEST, underWaterRegion), BlockDataUtils.setWallSign(world, x, y + 3, z - 1, Material.OAK_WALL_SIGN, BlockFace.NORTH, underWaterRegion), BlockDataUtils.setWallSign(world, x, y + 3, z + 1, Material.OAK_WALL_SIGN, BlockFace.SOUTH, underWaterRegion), BlockDataUtils.setWallSign(world, x + 1, y + 3, z, Material.OAK_WALL_SIGN, BlockFace.EAST, underWaterRegion) }) {
            if (!(block.getState() instanceof Sign)) {
                ConsoleUtils.warn("");
                ConsoleUtils.warn("");
                ConsoleUtils.warn("CANNOT CREATE SIGN");
                ConsoleUtils.warn("");
                ConsoleUtils.warn("");
                continue;
            }

            Sign sign = (Sign) block.getState();
            sign.setLine(1, "§0§lRegion");
            sign.setLine(2, "§0§l[" + id + "]");
            sign.update();
        }

        BlockDataUtils.update(world, x + 1, y, z);
        BlockDataUtils.update(world, x - 1, y, z);
        BlockDataUtils.update(world, x, y, z - 1);
        BlockDataUtils.update(world, x, y, z + 1);

        ConsoleUtils.success("Successfully created Region.");
    }
}
