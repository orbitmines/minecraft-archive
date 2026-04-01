package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.teleportable;

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.Title;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.Interval;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.SpigotTimer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.TimeUnit;
import org.bukkit.Location;
import org.bukkit.Sound;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

/* If a certain class can be teleported to, through channeling */
public interface Teleportable {

    Location getLocation();

    /* In Seconds */
    int getDuration(SurvivalPlayer player);

    Color getColor();

    String getName();

    void onTeleport(SurvivalPlayer player, Location from, Location to);

    default void teleport(SurvivalPlayer player) {
        if (player.getTeleportingTo() != null)
            player.getTeleportingTimer().cancel();

        int duration = getDuration(player);

        SpigotTimer timer = new SpigotTimer(player.server(), Interval.of(TimeUnit.SECOND, duration), Interval.of(TimeUnit.SECOND, 1)) {

            @Override
            public void onInterval() {
                int seconds = (int) (getProgress() * ((float) (duration)));

                String to = getColor().getCc() + "§l" + getName() + "§7§l";
                String in = getColor().getCc() + "§l" + seconds + "§7§l";

                new Title<SurvivalPlayer>(p -> "", p -> "§7§l" + p.translate("survival", "player.teleporting_to_timer", to, in), 0, 40, 0).send(player);
            }

            @Override
            public void onFinish() {
                Location location = getLocation();
                location.getChunk().load();

                onTeleport(player, player.getLocation(), location);

                player.setTeleportingTo(null);
                player.setTeleportingTimer(null);

                player.teleport(location);
                player.playSound(Sound.ENTITY_ENDERMAN_TELEPORT);

                String to = getColor().getCc() + "§l" + getName() + "§7§l";

                new Title<SurvivalPlayer>(p -> "", p -> "§7§l" + p.translate("survival", "player.teleported_to", to), 0, 40, 0).send(player);
            }
        };
        timer.start();
        timer.onInterval();

        String to = getColor().getCc() + getName() + "§7";
        player.sendMessage("Teleporter", Color.LIME, "survival", "player.teleporting_to", to);

        player.setTeleportingTo(this);
        player.setTeleportingTimer(timer);
    }
}
