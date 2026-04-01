package com.orbitmines.archive.minecraft.spigot._2019.servers.build;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.database.models.BuildMap;
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.scoreboard.BuildScoreboard;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class BuildPlayer extends OMPlayer<Build, BuildPlayer> {

    public BuildPlayer(Player player, Build server) {
        super(player, server);

        updateWorldEditPermissions();

        setGameMode(GameMode.CREATIVE);
    }

    public BuildMap getCurrentMap() {
        return server.getMap(getWorld());
    }

    @Override
    protected void register() {
        server.registerPlayer(this);
    }

    @Override
    protected void unregister() {
        server.unregisterPlayer(this);
    }

    @Override
    public BuildPlayer getInstance() {
        return this;
    }

    @Override
    public boolean onJoin() {
        super.onJoin();

        server.runSync(() -> {
            resetScoreboard();
            setScoreboard(new BuildScoreboard(server, this));

            updateWorldEditPermissions();
        });

        return true;
    }

    @Override
    public void onFirstLogin() {
        server.runSync(() -> teleport(server.getFallbackWorld().getSpawnLocation()));
    }

    @Override
    public boolean teleport(Location location) {
        super.teleport(location);
        updateWorldEditPermissions();

        return true;
    }

    private void updateWorldEditPermissions() {
        bukkit().setOp(true);
    }
}
