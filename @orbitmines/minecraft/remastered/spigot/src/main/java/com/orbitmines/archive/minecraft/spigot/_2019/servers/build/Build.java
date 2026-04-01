package com.orbitmines.archive.minecraft.spigot._2019.servers.build;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.player.PlayerInstance;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.ChatHandler;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.TabListHandler;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.OMMap;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.events.CommandEvents;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.loot.LootHandler;
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.chat.BuildChatHandler;
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.commands.*;
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.database.models.BuildMap;
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.events.BuildCommandEvents;
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.loot.BuildLootHandler;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPointHandler;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Build extends OMServer<Build, BuildPlayer> {

    public Build(JavaPlugin plugin) {
        super(plugin);
    }

    public static final String tWE_PERMS = "worldedit.*";

    @Getter private ArrayList<BuildMap> maps = new ArrayList<>();

    @Override
    public BuildPlayer newPlayerInstance(Player player) {
        return new BuildPlayer(player, this);
    }

    @Override
    public void onStartup() {
        super.onStartup();


    }

    @Override
    public void afterStartupSync() {
        registerCommands(
            new CommandSkull(this),
            new CommandGamemode(this, GameMode.SURVIVAL, "survival", "s", "gms"),
            new CommandGamemode(this, GameMode.ADVENTURE, "adventure", "a", "gma"),
            new CommandGamemode(this, GameMode.CREATIVE, "creative", "c", "gmc"),
            new CommandGamemode(this, GameMode.SPECTATOR, "spectator", "spec", "gmspec"),
            new CommandSpeed(this),
            new CommandTeleport(this),
            new CommandTeleportHere(this),
            new CommandCreate(this),
            new CommandDelete(this),
            new CommandMap(this),
            new CommandMapList(this),
            new CommandRename(this)
        );

        loadMaps();
    }

    @Override
    public CommandEvents<Build, BuildPlayer> newCommandEventsInstance() {
        return new BuildCommandEvents(this);
    }

    @Override
    public void onStop() {


        super.onStop();
    }

    private void loadMaps() {
        for (OMMap map : OMMap.getAll(OMMap.class)) {
            BuildMap buildMap = new BuildMap(this, map);
            buildMap.loadOrCreateWorld();

            maps.add(buildMap);
        }
    }

    public BuildMap getMap(World world) {
        for (BuildMap map : maps) {
            if (map.getWorld().equals(world))
                return map;
        }

        return null;
    }

    public BuildMap getMap(String name) {
        for (BuildMap map : maps) {
            if (map.getName().equalsIgnoreCase(name))
                return map;
        }

        return null;
    }

    public World getFallbackWorld() {
        for (BuildMap map : maps) {
            if (map.getServer() == Server.BUILD && map.getWorldFileName().equals("build_lobby"))
                return map.getWorld();
        }
        return Bukkit.getWorlds().get(0);
    }

    private void registerCommands(Command<Build, BuildPlayer>... commands) {
        for (Command<Build, BuildPlayer> command : commands) {
            command.register();
        }
    }

    @Override
    public Server getType() {
        return Server.BUILD;
    }

    @Override
    protected Build instance() {
        return this;
    }

    @Override
    public ChatHandler newChatHandler(PlayerInstance sender, ChatHandler.Type type, String message) {
        return new BuildChatHandler(this, type, sender, message);
    }

    @Override
    public TabListHandler newTabListHandler(BuildPlayer player) {
        return new TabListHandler<>(this, player);
    }

    @Override
    public LootHandler newLootHandler(BuildPlayer player) {
        return new BuildLootHandler(player);
    }

    @Override
    public boolean clearPlayerData() {
        return false;
    }

    @Override
    public boolean saveChunksOnRestart() {
        return true;
    }

    @Override
    public boolean broadcastWhenSaving() {
        return false;
    }

    @Override
    public boolean shouldSetupLobby() {
        return false;
    }

    @Override
    public DataPointHandler createDataPointHandler(OMMap.Type type) {
        return null;
    }

    @Override
    public void setupNpc(String string, Location location) {

    }

    @Override
    protected void instantiateLeaderBoards() {

    }
}
