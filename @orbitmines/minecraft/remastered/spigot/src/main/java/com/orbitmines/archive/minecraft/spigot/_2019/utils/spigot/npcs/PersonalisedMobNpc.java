package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.npcs;
/*
 * OrbitMines - @author Fadi Shawki - 15-6-2018
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.entities.Mob;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.mutable.MutablePlayerString;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import lombok.Getter;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PersonalisedMobNpc<P extends SpigotPlayer> extends MobNpc<P> {

    //TODO FIX AFTER SOME TIME APPEARING
    //TODO ACTUALLY MAKE THE MOB ALSO PERSONALIZED, NOT JUST THE NAMETAG
    private Map<P, Hologram> nameTags;

    @Getter private MutablePlayerString<P>[] lines;

    public PersonalisedMobNpc(Mob mob, Location spawnLocation, MutablePlayerString<P>... lines) {
        super(mob, spawnLocation);

        nameTags = new HashMap<>();
        this.lines = lines;
    }

    @Override
    public void update() {
        for (Hologram hologram : new ArrayList<>(nameTags.values())) {
            hologram.setYOff(getYOff());
            hologram.update();
        }
    }

    @Override
    protected void spawn() {
        super.spawn();

        for (P player : (Collection<P>) SpigotServer.getInstance().getPlayers()) {
            afterLogin(player);
        }
    }

    @Override
    protected void despawn() {
        super.despawn();

        for (P player : (Collection<P>) SpigotServer.getInstance().getPlayers()) {
            afterLogout(player);
        }
    }

    @Override
    public void setMob(Mob mob) {
        super.setMob(mob);

        for (Hologram hologram : nameTags.values()) {
            hologram.setYOff(getYOff());
            hologram.update();
        }
    }

    @Override
    public Hologram getNameTag() {
        throw new IllegalStateException();
    }

    public Map<P, Hologram> getNameTags() {
        return nameTags;
    }

    public void toggle(P player) {
        if (!player.getWorld().equals(getSpawnLocation().getWorld())) {
            afterLogout(player);
            return;
        }

        if (nameTags.containsKey(player))
            return;

        afterLogin(player);
    }

    public void afterLogin(P player) {
        Hologram hologram = new Hologram(spawnLocation, getYOff(), Hologram.Face.UP);

        nameTags.put(player, hologram);

        for (MutablePlayerString<P> line : this.lines) {
            hologram.addLine(() -> line.toString(player), false);
        }

        hologram.create(player.bukkit());
    }

    public void afterLogout(P player) {
        if (!nameTags.containsKey(player))
            return;

        /* Destroy hologram on logout, cause the hologram was only meant for this player */
        nameTags.get(player).destroy();

        nameTags.remove(player);
    }
}
