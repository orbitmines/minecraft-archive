package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.passives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events.KitEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class PassiveSpiderClimb implements Passive.Handler<PlayerMoveEvent> {

    @Override
    public void trigger(KitEvent<PlayerMoveEvent> passiveEvent, PlayerMoveEvent event, int level) {
        Player player = passiveEvent.getPlayer().bukkit();

        /* Check if player is next to a wall and sneaking/moving against it */
        if (!isNextToWall(player))
            return;

        /* Allow climbing: if the player is touching a wall, give upward velocity */
        Vector velocity = player.getVelocity();
        velocity.setY(getClimbSpeed(level));
        player.setVelocity(velocity);
        player.setFallDistance(0);
    }

    private boolean isNextToWall(Player player) {
        Location loc = player.getLocation();

        /* Check 4 cardinal directions for solid blocks */
        int[][] checks = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] check : checks) {
            Block block = loc.getWorld().getBlockAt(
                loc.getBlockX() + check[0],
                loc.getBlockY(),
                loc.getBlockZ() + check[1]
            );
            Block blockAbove = loc.getWorld().getBlockAt(
                loc.getBlockX() + check[0],
                loc.getBlockY() + 1,
                loc.getBlockZ() + check[1]
            );

            if (block.getType().isSolid() || blockAbove.getType().isSolid())
                return true;
        }
        return false;
    }

    private double getClimbSpeed(int level) {
        switch (level) {
            case 1: return 0.2D;
            case 2: return 0.25D;
            case 3: return 0.3D;
            default: throw new ArrayIndexOutOfBoundsException();
        }
    }
}
