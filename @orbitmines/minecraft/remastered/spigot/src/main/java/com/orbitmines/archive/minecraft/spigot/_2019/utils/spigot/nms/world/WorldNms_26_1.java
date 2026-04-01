package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.world;

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.entities.Mob;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.ConduitBlockEntity;
import net.minecraft.world.level.block.entity.EnderChestBlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

public class WorldNms_26_1 implements WorldNms {

    @Override
    public void chestAnimation(Location location, boolean opened) {
        ServerLevel level = ((CraftWorld) location.getWorld()).getHandle();
        BlockPos position = new BlockPos(location.getBlockX(), location.getBlockY(), location.getBlockZ());

        ChestBlockEntity tileChest = (ChestBlockEntity) level.getBlockEntity(position);
        level.blockEvent(position, tileChest.getBlockState().getBlock(), 1, opened ? 1 : 0);
    }

    @Override
    public void enderchestAnimation(Location location, boolean opened) {
        ServerLevel level = ((CraftWorld) location.getWorld()).getHandle();
        BlockPos position = new BlockPos(location.getBlockX(), location.getBlockY(), location.getBlockZ());

        EnderChestBlockEntity tileChest = (EnderChestBlockEntity) level.getBlockEntity(position);
        level.blockEvent(position, tileChest.getBlockState().getBlock(), 1, opened ? 1 : 0);
    }

    @Override
    public void conduitAnimation(Location location, Collection<? extends Player> players) {
        ServerLevel level = ((CraftWorld) location.getWorld()).getHandle();
        BlockPos position = new BlockPos(location.getBlockX(), location.getBlockY(), location.getBlockZ());

        ConduitBlockEntity tileConduit = (ConduitBlockEntity) level.getBlockEntity(position);
        //TODO FIX
    }

    @Override
    public void setSpawner(Location location, Mob mob) {
        location.getBlock().setType(org.bukkit.Material.SPAWNER);

        ServerLevel level = ((CraftWorld) location.getWorld()).getHandle();
        BlockPos position = new BlockPos(location.getBlockX(), location.getBlockY(), location.getBlockZ());

        SpawnerBlockEntity tileSpawner = (SpawnerBlockEntity) level.getBlockEntity(position);

        net.minecraft.nbt.CompoundTag tag = new net.minecraft.nbt.CompoundTag();
        tag.putString("id", getKey(mob));
        tileSpawner.getSpawner().setNextSpawnData(null, null, new net.minecraft.world.level.SpawnData());
        tileSpawner.setChanged();

        new BukkitRunnable() {
            @Override
            public void run() {
                tileSpawner.setChanged();
            }
        }.runTaskLater(SpigotServer.getInstance(), 1);
    }

    private String getKey(Mob mob) {
        switch (mob) {
            case PIG: return "minecraft:pig";
            case CAVE_SPIDER: return "minecraft:cave_spider";
            case SILVERFISH: return "minecraft:silverfish";
            case ZOMBIE: return "minecraft:zombie";
            case SKELETON: return "minecraft:skeleton";
            case BLAZE: return "minecraft:blaze";
            case SPIDER: return "minecraft:spider";
            default: return "minecraft:pig";
        }
    }

    @Override
    public Mob getSpawner(Location location) {
        ServerLevel level = ((CraftWorld) location.getWorld()).getHandle();
        BlockPos position = new BlockPos(location.getBlockX(), location.getBlockY(), location.getBlockZ());

        SpawnerBlockEntity tileSpawner = (SpawnerBlockEntity) level.getBlockEntity(position);
        //TODO update for modern spawner API
        return Mob.PIG;
    }
}
