package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.passives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events.KitEvent;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class PassiveSpiderClimb implements Passive.Handler<PlayerMoveEvent> {

    @Override
    public void trigger(KitEvent<PlayerMoveEvent> passiveEvent, PlayerMoveEvent event, int level) {
        Player player = passiveEvent.getPlayer().bukkit();

        if (player.isOnGround())
            return;

        if (!isNextToWall(player))
            return;

        double climbSpeed = getClimbSpeed(level);

        /*
         * Pitch: -90 = straight up, 0 = level, +90 = straight down.
         * Map pitch to a climb factor:
         *   Looking up (-90 to -20): climb at full speed
         *   Level (-20 to +20): slow climb / cling (slight gravity so you slide down gently)
         *   Looking down (+20 to +90): slide down
         */
        double pitch = player.getLocation().getPitch();

        double targetY;
        if (pitch <= -20) {
            /* Looking up: full climb */
            targetY = climbSpeed;
        } else if (pitch <= 20) {
            /* Neutral zone: interpolate from climbSpeed to a slow slide */
            /* At -20: targetY = climbSpeed, at +20: targetY = -0.04 (gentle slide) */
            double t = (pitch + 20.0) / 40.0; /* 0.0 at -20, 1.0 at +20 */
            targetY = climbSpeed * (1.0 - t) + (-0.04) * t;
        } else {
            /* Looking down: slide down, faster the steeper you look */
            double t = (pitch - 20.0) / 70.0; /* 0.0 at +20, 1.0 at +90 */
            targetY = -0.04 - t * 0.25;
        }

        /* Smooth the velocity toward target to prevent jerky bouncing */
        Vector velocity = player.getVelocity();
        double currentY = velocity.getY();
        double smoothed = currentY + (targetY - currentY) * 0.4;
        velocity.setY(smoothed);

        player.setVelocity(velocity);
        player.setFallDistance(0);
    }

    private boolean isNextToWall(Player player) {
        Location loc = player.getLocation();

        /* Check 4 cardinal directions for solid blocks at body or head level */
        double[][] checks = {{0.4, 0}, {-0.4, 0}, {0, 0.4}, {0, -0.4}};
        for (double[] check : checks) {
            Block block = loc.clone().add(check[0], 0, check[1]).getBlock();
            Block blockAbove = loc.clone().add(check[0], 1, check[1]).getBlock();

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
