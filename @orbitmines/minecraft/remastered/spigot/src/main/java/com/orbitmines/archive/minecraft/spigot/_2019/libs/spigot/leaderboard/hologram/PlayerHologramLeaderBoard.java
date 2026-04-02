package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.leaderboard.hologram;

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.leaderboard.LeaderBoard;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.mutable.MutablePlayerString;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.npcs.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */
public abstract class PlayerHologramLeaderBoard<P extends OMPlayer<?, P>> extends LeaderBoard {

    private Map<OMPlayer, Hologram> holograms;
    private MutablePlayerString<P>[] lines;

    public PlayerHologramLeaderBoard(Location location, MutablePlayerString<P>... lines) {
        super(location);

        holograms = new HashMap<>();
        this.lines = lines;
    }

    @Override
    public void update() {
        Bukkit.getScheduler().runTask(OMServer.getInstance().getPlugin(), () -> {
            for (Hologram hologram : holograms.values()) {
                hologram.update();
            }
        });
    }

    public void onLogin(P omp) {
        Hologram hologram = new Hologram(location, 0.5, Hologram.Face.UP);
        hologram.create(omp.bukkit());

        holograms.put(omp, hologram);

        for (MutablePlayerString<P> string : this.lines) {
            hologram.addLine(() -> string.toString(omp), true);
        }
    }

    public void onLogout(OMPlayer omp) {
        if (!holograms.containsKey(omp))
            return;

        /* Destroy hologram on logout, cause the hologram was only meant for this player */
        holograms.get(omp).destroy();

        holograms.remove(omp);
    }
}
