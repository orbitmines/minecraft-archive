package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.database.models;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalWarp;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.teleportable.Teleportable;
import lombok.Getter;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Warp implements Teleportable {

    public static int MAX_CHARACTERS = 20;

    @Getter private final SurvivalWarp model;

    public Warp(SurvivalWarp model) {
        this.model = model;
    }

    public Warp(UUID owner, String name, SurvivalWarp.Slot slot) {
        this.model = new SurvivalWarp(owner, name, slot);
    }

    @Override
    public int getDuration(SurvivalPlayer player) {
        return 3;
    }

    @Override
    public Color getColor() {
        return Color.TEAL;
    }

    @Override
    public void onTeleport(SurvivalPlayer player, Location from, Location to) {
        player.setBackLocation(from);
    }

    /*

        SurvivalWarp Delegates

     */

    public void setName(String name) {
        model.setName(name);
    }

    public void setLocation(Location location) {
        model.setLocation(location);
    }

    public void setEnabled(boolean enabled) {
        model.setEnabled(enabled);
    }

    public void setIcon(SurvivalWarp.Icon icon) {
        model.setIcon(icon);
    }

    public Long getId() {
        return model.getId();
    }

    public UUID getOwner() {
        return model.getOwner();
    }

    public void addTimeUsed() {
        model.addTimeUsed();
    }

    @Override
    public String getName() {
        return model.getName();
    }

    @Override
    public Location getLocation() {
        return model.getLocation();
    }

    public boolean isEnabled() {
        return model.isEnabled();
    }

    public SurvivalWarp.Slot getSlot() {
        return model.getSlot();
    }

    public SurvivalWarp.Icon getIcon() {
        return model.getIcon();
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

    public void update(SurvivalWarp.column... columns) {
        model.update(columns);
    }

    public void delete() {
        model.delete();
    }

    public boolean isDestroyed() {
        return model.isDestroyed();
    }

    public boolean isInserted() {
        return model.isInserted();
    }

    public void reload() {
        model.reload();
    }

    public void insertOrUpdate(SurvivalWarp.column... columns) {
        model.insertOrUpdate(columns);
    }

    public static ArrayList<Warp> getAll() {
        ArrayList<Warp> warps = new ArrayList<>();

        for (SurvivalWarp model : SurvivalWarp.getAll(SurvivalWarp.class)) {
            warps.add(new Warp(model));
        }

        return warps;
    }
}
