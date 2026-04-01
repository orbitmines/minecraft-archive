package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.utils.language.Language;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ConsoleUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.Nms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.events.SpigotPlayerEvents;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.events.WorldAdvancementsFix;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class SpigotServer<P extends SpigotPlayer> extends JavaPlugin {

    @Getter protected static SpigotServer instance;
    protected Map<UUID, P> players;
    @Getter protected Nms nms;

    public abstract P newPlayerInstance(Player player);

    public abstract void onStartup();

    public abstract void onStart();

    public abstract void onStop();

    public abstract String getPluginName();

    @Override
    public void onLoad() {
        instance = this;
        /* Setup Languages */
        Language.initialize("spigot", false);

        onStartup();
    }

    @Override
    public void onEnable() {
        this.players = new HashMap<>();

        ConsoleUtils.msg("Initializing Nms...");
        this.nms = new Nms(this);
        ConsoleUtils.msg("Finished initializing Nms.");

        registerEvents(
                new SpigotPlayerEvents(this),
                new WorldAdvancementsFix(this)
        );

        ConsoleUtils.msg("Starting " + getPluginName() + "...");
        onStart();
    }

    @Override
    public void onDisable() {
        ConsoleUtils.msg("Stopping " + getPluginName() + "...");

        onStop();
    }

    public void runAsync(Runnable runnable) {
        getServer().getScheduler().runTaskAsynchronously(this, runnable);
    }

    public void runSync(Runnable runnable) {
        getServer().getScheduler().scheduleSyncDelayedTask(this, runnable);
    }

    public void triggerJoinEvent(Player player) {
        getPlayer(player);
    }

    public P getPlayer(Player player) {
        if (player == null)
            return null;

        if (this.players.containsKey(player.getUniqueId()))
            return this.players.get(player.getUniqueId());

        P spigotPlayer = newPlayerInstance(player);
        spigotPlayer.register();

        runAsync(spigotPlayer::processJoinEventAsync);

        return spigotPlayer;
    }

    public P getPlayer(UUID uuid) {
        return this.players.get(uuid);
    }

    public void registerPlayer(P player) {
        this.players.put(player.getUniqueId(), player);
    }

    public void unregisterPlayer(P player) {
        this.players.remove(player.getUniqueId());
    }

    public Collection<P> getPlayers() {
        return this.players.values();
    }

    protected void registerEvents(Listener... listeners) {
        PluginManager pluginManager = getServer().getPluginManager();

        for (Listener l : listeners) {
            pluginManager.registerEvents(l, this);
        }
    }
}
