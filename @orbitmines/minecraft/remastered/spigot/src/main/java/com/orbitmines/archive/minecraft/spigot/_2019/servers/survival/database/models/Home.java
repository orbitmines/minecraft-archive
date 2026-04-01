package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.database.models;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalHome;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.teleportable.Teleportable;
import org.bukkit.Location;

import java.util.Date;
import java.util.UUID;

public class Home implements Teleportable {

    private SurvivalHome model;

    public Home(SurvivalHome model) {
        this.model = model;
    }

    public Home(UUID uuid, String name, Location location) {
        this.model = new SurvivalHome(uuid, name, location);
    }

    @Override
    public int getDuration(SurvivalPlayer player) {
        return 3;
    }

    @Override
    public Color getColor() {
        return Color.ORANGE;
    }

    @Override
    public void onTeleport(SurvivalPlayer player, Location from, Location to) {
        player.setBackLocation(from);
    }

    /*

        SurvivalHome delegates

     */

    public void setName(String name) {
        model.setName(name);
    }

    public void setLocation(Location location) {
        model.setLocation(location);
    }

    public void setLastUsageOn(Date lastUsageOn) {
        model.setLastUsageOn(lastUsageOn);
    }

    public void addTimesUsed() {
        model.addTimesUsed();
    }

    public Long getId() {
        return model.getId();
    }

    public UUID getUuid() {
        return model.getUuid();
    }

    @Override
    public String getName() {
        return model.getName();
    }

    @Override
    public Location getLocation() {
        return model.getLocation();
    }

    public Date getLastUsageOn() {
        return model.getLastUsageOn();
    }

    public int getTimesUsed() {
        return model.getTimesUsed();
    }

    public Date getCreatedOn() {
        return model.getCreatedOn();
    }

    public void insert() {
        model.insert();
    }

    public void update(SurvivalHome.column... columns) {
        model.update(columns);
    }

    public void delete() {
        model.delete();
    }

    public boolean isInserted() {
        return model.isInserted();
    }

    public boolean isDestroyed() {
        return model.isDestroyed();
    }

    public void reload() {
        model.reload();
    }

    public void insertOrUpdate(SurvivalHome.column... columns) {
        model.insertOrUpdate(columns);
    }
}
