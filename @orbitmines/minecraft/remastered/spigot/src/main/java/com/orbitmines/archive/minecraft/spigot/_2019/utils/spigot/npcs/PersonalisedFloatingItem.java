package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.npcs;
/*
 * OrbitMines - @author Fadi Shawki - 15-6-2018
 */

import com.orbitmines.archive.minecraft._2019.utils.mutable.MutableString;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import org.bukkit.Location;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class PersonalisedFloatingItem<P extends SpigotPlayer> extends FloatingItem {

    //TODO FIX AFTER SOME TIME APPEARING
    //TODO CONVERT TO MutablePlayerItemBuilder instead of MutableItemBuilder and actually make the items personal
    private Map<P, Hologram> nameTags;

    public PersonalisedFloatingItem(MutableItemBuilder itemBuilder, Location spawnLocation) {
        super(itemBuilder, spawnLocation);

        nameTags = new HashMap<>();
    }

    public abstract MutableString[] getLines(P player);

    @Override
    public void update() {
        for (Hologram hologram : nameTags.values()) {
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
        Hologram hologram = new Hologram(spawnLocation.clone().subtract(0, 1, 0));
        hologram.create(player.bukkit());

        nameTags.put(player, hologram);

        for (MutableString string : getLines(player)) {
            hologram.addLine(string, true);
        }
    }

    public void afterLogout(P player) {
        if (!nameTags.containsKey(player))
            return;

        /* Destroy hologram on logout, cause the hologram was only meant for this player */
        nameTags.get(player).destroy();

        nameTags.remove(player);
    }
}
