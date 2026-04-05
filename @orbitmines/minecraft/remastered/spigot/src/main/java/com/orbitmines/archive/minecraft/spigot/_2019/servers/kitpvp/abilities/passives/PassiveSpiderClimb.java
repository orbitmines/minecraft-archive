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

        if (!isNextToWall(player))
            return;

            /* Only climb when the player is not on ground (holding space/jump) */
        if (player.isOnGround())
            return;

        /* Apply ladder-like climbing: steady upward speed, cancel gravity */
        Vector velocity = player.getVelocity();
        double climbSpeed = getClimbSpeed(level);

        /* If player is looking up, climb faster; looking down, descend */
        double pitch = player.getLocation().getPitch();
        if (pitch < -10) {
            /* Looking up: climb */
            velocity.setY(climbSpeed);
        } else if (pitch > 45) {
            /* Looking steeply down: descend slowly */
            velocity.setY(-0.1);
        } else {
            /* Neutral: hold position (hover against wall) */
            velocity.setY(0.0);
        }

        player.setVelocity(velocity);
        player.setFallDistance(0);
    }

    private boolean isNextToWall(Player player) {
        Location loc = player.getLocation();

        /* Check 4 cardinal directions for solid blocks at body or head level */
        double[][] checks = {{0.4, 0}, {-0.4, 0}, {0, 0.4}, {0, -0.4}};
        for (double[] check : checks) {
            Location checkLoc = loc.clone().add(check[0], 0, check[1]);
            Block block = checkLoc.getBlock();
            Block blockAbove = loc.clone().add(check[0], 1, check[1]).getBlock();

            if (block.getType().isSolid() || blockAbove.getType().isSolid())
                return true;
        }
        return false;
    }

    private double getClimbSpeed(int level) {
        switch (level) {
            case 1: return 0.25D;
            case 2: return 0.3D;
            case 3: return 0.35D;
            default: throw new ArrayIndexOutOfBoundsException();
        }
    }
}
