package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.freezer;

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.npcs.ArmorStandNpc;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.Interval;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.PassiveRunnable;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.SpigotRunnable;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.TimeUnit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */
public class ArmorStandPlayerFreezer extends ArmorStandNpc {

    private static Set<ArmorStandPlayerFreezer> freezers = new HashSet<>();

    private final Player player;
    private final SpigotRunnable runnable;

    public ArmorStandPlayerFreezer(Player player) {
        this(player, player.getLocation().subtract(0, 1, 0)); //TODO right subtraction?
    }

    public ArmorStandPlayerFreezer(Player player, Location spawnLocation) {
        super(spawnLocation);

        this.player = player;
        this.gravity = false;

        spawn();

        runnable = new PassiveRunnable<SpigotServer>(SpigotServer.getInstance(), Interval.of(TimeUnit.TICK, 1)) {
            @Override
            public void onRun() {
                if (!armorStand.getPassengers().contains(player))
                    armorStand.addPassenger(player);
            }
        }.start();
    }

    @Override
    protected void spawn() {
        super.spawn();

        nms.entity().setInvisible(armorStand, true);
    }

    @Override
    protected void register() {
        super.register();

        /* Clear previous */
        ArmorStandPlayerFreezer prev = getFreezer(player);
        if (prev != null)
            prev.destroy();

        freezers.add(this);
    }

    @Override
    protected void unregister() {
        super.unregister();

        freezers.remove(this);
    }

    @Override
    public void destroy() {
        super.destroy();

        runnable.cancel();
    }

    public Player getPlayer() {
        return player;
    }

    public SpigotRunnable getRunnable() {
        return runnable;
    }

    public static Set<ArmorStandPlayerFreezer> getFreezers() {
        return freezers;
    }

    public static ArmorStandPlayerFreezer getFreezer(Player player) {
        for (ArmorStandPlayerFreezer freezer : freezers) {
            if (freezer.player.equals(player))
                return freezer;
        }
        return null;
    }
}
