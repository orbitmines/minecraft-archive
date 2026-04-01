package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.world;

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.entities.Mob;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.ConduitBlockEntity;
import net.minecraft.world.level.block.entity.EnderChestBlockEntity;
import org.bukkit.Location;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Player;

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

        CreatureSpawner spawner = (CreatureSpawner) location.getBlock().getState();
        spawner.setSpawnedType(org.bukkit.entity.EntityType.valueOf(mob.name()));
        spawner.update();
    }

    @Override
    public Mob getSpawner(Location location) {
        CreatureSpawner spawner = (CreatureSpawner) location.getBlock().getState();
        org.bukkit.entity.EntityType type = spawner.getSpawnedType();
        try {
            return Mob.valueOf(type.name());
        } catch (IllegalArgumentException e) {
            return Mob.PIG;
        }
    }
}
