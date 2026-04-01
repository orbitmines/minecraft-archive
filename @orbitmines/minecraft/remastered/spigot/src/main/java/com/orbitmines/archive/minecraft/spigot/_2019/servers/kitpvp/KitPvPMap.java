package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.google.gson.JsonArray;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.OMMap;
import com.orbitmines.archive.minecraft._2019.utils.RandomUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.generators.DefaultWorldType;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPointHandler;
import lombok.Getter;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.Date;
import java.util.List;

public class KitPvPMap {

    @Getter private final OMMap map;

    public KitPvPMap(OMMap map) {
        this.map = map;
    }
    
    @Getter private List<Location> spawns;
    @Getter private List<Location> spectatorSpawns;

    public void setup(List<Location> spawns, List<Location> spectatorSpawns) {
        this.spawns = spawns;
        this.spectatorSpawns = spectatorSpawns;
    }

    public void teleport(KitPvPPlayer player) {
        player.teleport(RandomUtils.randomFrom(player.getGameMode() != GameMode.SPECTATOR ? spawns : spectatorSpawns));
    }

    public static KitPvPMap load() {
        OMMap map = OMMap.findBy(OMMap.class, OMMap.column.SERVER.is(Server.KITPVP), OMMap.column.WORLD_TYPE.is(OMMap.Type.GAMEMAP), OMMap.column.ENABLED.is(true));

        if (map == null)
            throw new NullPointerException("Cannot load KitPvP map from database");

        return new KitPvPMap(map);
    }

    /*

        OMMap delegates

     */

    public void setServer(Server server) {
        getMap().setServer(server);
    }

    public void setName(String name) {
        getMap().setName(name);
    }

    public void setWorldFileName(String worldFileName) {
        getMap().setWorldFileName(worldFileName);
    }

    public void setEnabled(Boolean enabled) {
        getMap().setEnabled(enabled);
    }

    public Long getId() {
        return getMap().getId();
    }

    public Server getServer() {
        return getMap().getServer();
    }

    public String getName() {
        return getMap().getName();
    }

    public String getWorldFileName() {
        return getMap().getWorldFileName();
    }

    public DefaultWorldType getWorldGenerator() {
        return getMap().getWorldGenerator();
    }

    public OMMap.Type getWorldType() {
        return getMap().getWorldType();
    }

    public Boolean getEnabled() {
        return getMap().getEnabled();
    }

    public JsonArray getAuthors() {
        return getMap().getAuthors();
    }

    public Date getCreatedAt() {
        return getMap().getCreatedAt();
    }

    public World getWorld() {
        return getMap().getWorld();
    }

    public DataPointHandler getDataPointHandler() {
        return getMap().getDataPointHandler();
    }

    public void setup(World world) {
        getMap().setup(world);
    }

    public void setup(World world, DataPointHandler dataPointHandler) {
        getMap().setup(world, dataPointHandler);
    }

    public void setupDataPoints() {
        getMap().setupDataPoints();
    }

    public void insert() {
        getMap().insert();
    }

    public static OMMap getLobby(Server server) {
        return OMMap.getLobby(server);
    }

    public void update(OMMap.column... columns) {
        getMap().update(columns);
    }

    public void delete() {
        getMap().delete();
    }

    public boolean isInserted() {
        return getMap().isInserted();
    }

    public boolean isDestroyed() {
        return getMap().isDestroyed();
    }

    public void reload() {
        getMap().reload();
    }
}
