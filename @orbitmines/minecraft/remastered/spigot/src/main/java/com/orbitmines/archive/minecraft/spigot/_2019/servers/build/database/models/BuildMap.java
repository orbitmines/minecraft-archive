package com.orbitmines.archive.minecraft.spigot._2019.servers.build.database.models;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.google.gson.JsonArray;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.OMMap;
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.Build;
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.utils.WorldBackup;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import com.orbitmines.archive.minecraft._2019.utils.FileUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.generators.DefaultWorldType;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Date;
import java.util.UUID;

public class BuildMap {

    private final Build build;
    private final OMMap map;

    public BuildMap(Build build, OMMap map) {
        this.build = build;
        this.map = map;
    }

    public BuildMap(Build build, UUID uuid, Server server, String name, DefaultWorldType worldType, OMMap.Type type) {
        this.build = build;

        JsonArray authors = new JsonArray();
        authors.add(uuid.toString());

        this.map = new OMMap(server, name, server.toString().toLowerCase() + "_" + name.toLowerCase() + "_" + System.currentTimeMillis(), worldType, type, false, authors, DateUtils.now());
    }

    public World loadOrCreateWorld() {
        try {
            World world = build.getWorldLoader().loadWorld(getWorldFileName(), false, getWorldGenerator());
            world.setAutoSave(true);

            world.setTime(6000);

            world.setGameRule(GameRule.SPAWN_MOBS, false);
            world.setGameRule(GameRule.ADVANCE_TIME, false);
            world.setGameRule(GameRule.KEEP_INVENTORY, true);
            world.setGameRule(GameRule.MOB_GRIEFING, false);
            /* GameRule.DO_FIRE_TICK removed in 26.1 */
            world.setGameRule(GameRule.MOB_DROPS, false);
            world.setGameRule(GameRule.COMMAND_BLOCK_OUTPUT, false);
            world.setGameRule(GameRule.BLOCK_DROPS, false);
            world.setGameRule(GameRule.ENTITY_DROPS, false);

            world.setDifficulty(Difficulty.NORMAL);

            map.setup(world);

            return world;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void delete() {
        map.delete();

        if (getWorld() == null)
            return;

        World world = getWorld();

        File worldFile = world.getWorldFolder();

        World fallBack = build.getFallbackWorld();
        for (Player player : world.getPlayers()) {
            player.teleport(fallBack.getSpawnLocation());
        }

        Bukkit.unloadWorld(world, true);

        WorldBackup.create(worldFile);
        FileUtils.deleteDirectory(worldFile);
    }

    /*

        OMMap Delegates

     */

    public Date getCreatedAt() {
        return map.getCreatedAt();
    }

    public void setServer(Server server) {
        map.setServer(server);
    }

    public void setName(String name) {
        map.setName(name);
    }

    public void setWorldFileName(String worldFileName) {
        map.setWorldFileName(worldFileName);
    }

    public void setEnabled(Boolean enabled) {
        map.setEnabled(enabled);
    }

    public Long getId() {
        return map.getId();
    }

    public Server getServer() {
        return map.getServer();
    }

    public String getName() {
        return map.getName();
    }

    public String getWorldFileName() {
        return map.getWorldFileName();
    }

    public DefaultWorldType getWorldGenerator() {
        return map.getWorldGenerator();
    }

    public OMMap.Type getWorldType() {
        return map.getWorldType();
    }

    public Boolean getEnabled() {
        return map.getEnabled();
    }

    public JsonArray getAuthors() {
        return map.getAuthors();
    }

    public World getWorld() {
        return map.getWorld();
    }

    public void insert() {
        map.insert();
    }

    public void update(OMMap.column... columns) {
        map.update(columns);
    }

    public boolean isInserted() {
        return map.isInserted();
    }

    public boolean isDestroyed() {
        return map.isDestroyed();
    }

    public void reload() {
        map.reload();
    }

    public void insertOrUpdate(OMMap.column... columns) {
        map.insertOrUpdate(columns);
    }
}
