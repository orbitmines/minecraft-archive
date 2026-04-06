package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.passives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events.KitEvent;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import org.bukkit.util.VoxelShape;

public class PassiveSpiderClimb implements Passive.Handler<PlayerMoveEvent> {

    @Override
    public void trigger(KitEvent<PlayerMoveEvent> passiveEvent, PlayerMoveEvent event, int level) {
        Player player = passiveEvent.getPlayer().bukkit();

        if (player.isOnGround())
            return;

        /* Only engage climbing when the player is falling or stationary in air.
           This lets normal jumps near walls complete their upward arc unaffected. */
        if (player.getVelocity().getY() > 0.1)
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
            /* At -20: targetY = climbSpeed, at +20: targetY = 0 (cling) */
            double t = (pitch + 20.0) / 40.0; /* 0.0 at -20, 1.0 at +20 */
            targetY = climbSpeed * (1.0 - t);
        } else {
            /* Looking down: descend smoothly, faster the steeper you look */
            double t = (pitch - 20.0) / 70.0; /* 0.0 at +20, 1.0 at +90 */
            targetY = -climbSpeed * t;
        }

        /* Set velocity directly for smooth, consistent movement */
        Vector velocity = player.getVelocity();
        velocity.setY(targetY);

        player.setVelocity(velocity);
        player.setFallDistance(0);
    }

    private boolean isNextToWall(Player player) {
        Location loc = player.getLocation();
        double feetY = loc.getY();

        /* Check 4 cardinal directions for solid blocks at body or head level */
        double[][] checks = {{0.4, 0}, {-0.4, 0}, {0, 0.4}, {0, -0.4}};
        for (double[] check : checks) {
            if (isWallBlock(loc.clone().add(check[0], 0, check[1]).getBlock(), feetY))
                return true;
            if (isWallBlock(loc.clone().add(check[0], 1, check[1]).getBlock(), feetY))
                return true;
        }
        return false;
    }

    private boolean isWallBlock(Block block, double feetY) {
        if (!block.getType().isSolid())
            return false;

        /* A block is only a wall if its collision box extends well above the player's feet.
           A threshold of 0.4 excludes bottom slabs (0.5 tall, so only 0.0 above feet when
           standing on them) and stair steps, while keeping full blocks detectable even when
           the player's feet are near the top of the block. */
        VoxelShape shape = block.getCollisionShape();
        for (BoundingBox box : shape.getBoundingBoxes()) {
            if (block.getY() + box.getMaxY() > feetY + 0.4)
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
