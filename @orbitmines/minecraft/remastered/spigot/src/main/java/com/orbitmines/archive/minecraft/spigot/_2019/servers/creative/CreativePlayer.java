package com.orbitmines.archive.minecraft.spigot._2019.servers.creative;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.creative.CreativePlayerModel;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.gui.WorldListGUI;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.scoreboard.CreativeScoreboard;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.PlayerUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.ActionBar;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutablePlayerItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.kits.interactive.InteractiveKit;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CreativePlayer extends OMPlayer<Creative, CreativePlayer> {

    @Getter private List<CreativeWorld> worlds;
    /** @deprecated Use {@code server.getWorldByBukkitWorld(player.getWorld())} instead */
    @Deprecated @Getter @Setter private CreativeWorld currentWorld;
    @Getter private CreativePlayerModel model;

    public CreativePlayer(Player player, Creative server) {
        super(player, server);

        this.worlds = new ArrayList<>();

        setGameMode(GameMode.CREATIVE);
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
    public CreativePlayer getInstance() {
        return this;
    }

    @Override
    public boolean onJoin() {
        super.onJoin();

        /* Load player model & worlds from database */
        this.model = CreativePlayerModel.findOrInitializeBy(getUUID());
        this.worlds = server.loadPlayerWorlds(getUUID());

        server.runSync(() -> {
            resetScoreboard();
            setScoreboard(new CreativeScoreboard(server, this));

            giveSpawnInventory();

            teleport(server.getLobbySpawn());

            /* Find the world matching the logout location (can be own or someone else's) */
            CreativeWorld world = null;
            String logoutWorldName = model.getLogoutWorldName();
            if (logoutWorldName != null) {
                world = server.getWorldByFileName(logoutWorldName);
                if (world == null)
                    world = getWorldByFileName(logoutWorldName);
            }
            if (world == null && !worlds.isEmpty())
                world = worlds.get(0);

            if (world != null) {
                sendMessage("Creative", Color.FUCHSIA, "creative", "player.world.preparing");
                new ActionBar(bukkit(), () -> "§d§l" + translate("creative", "player.world.preparing"), 20 * 10) {
                    @Override
                    public void onRun() {
                        if (server.getWorldByBukkitWorld(getWorld()) != null)
                            forceStop();
                    }
                }.send();

                server.loadAndTeleport(this, world, true);
            } else {
                /* Custom lobby teleport — after all default setup */
                if (getLobbyPreferenceMap() != null)
                    server.teleportToPlayerLobby(this, null);
            }
        });

        return true;
    }

    @Override
    public void beforeQuitSync() {
        /* Persist logout location */
        if (server.getWorldByBukkitWorld(getWorld()) != null) {
            model.setLogoutLocation(getLocation());
        } else {
            model.setLogoutLocation(null);
        }

        if (!model.isInserted())
            model.insert();
        else
            model.update(CreativePlayerModel.column.LOGOUT_LOCATION);

        /* Unload worlds where this player is the last one */
        for (CreativeWorld world : server.getAllWorlds()) {
            if (!world.isLoaded())
                continue;

            boolean hasOtherPlayers = false;
            for (Player p : world.getWorld().getPlayers()) {
                if (!p.getUniqueId().equals(getUUID())) {
                    hasOtherPlayers = true;
                    break;
                }
            }

            if (!hasOtherPlayers)
                world.unload();
        }

        super.beforeQuitSync();
    }

    @Override
    public void onFirstLogin() {
        server.runSync(() -> server.teleportToPlayerLobby(this, () -> {
            giveSpawnInventory();
            bukkit().getInventory().setHeldItemSlot(4);
        }));
    }

    public void giveSpawnInventory() {
        InteractiveKit<Creative, CreativePlayer> kit = server.getLobbyKit();
        if (kit == null)
            return;

        PlayerInventory inventory = getInventory();
        MutablePlayerItemBuilder<?, CreativePlayer>[] contents = kit.getContents();
        for (int i = 0; i < contents.length; i++) {
            if (contents[i] == null)
                continue;

            /* Only set hotbar items if the slot is empty */
            if (i < 9 && inventory.getItem(i) != null && inventory.getItem(i).getType() != Material.AIR)
                continue;

            inventory.setItem(i, kit.build(contents[i], this));
        }

        PlayerUtils.updateInventory(bukkit());
    }

    public void openWorldManager() {
        new WorldListGUI(server, this).open();
    }

    public CreativeWorld getWorld(String name) {
        for (CreativeWorld world : worlds) {
            if (world.getName().equalsIgnoreCase(name))
                return world;
        }
        return null;
    }

    public CreativeWorld getWorldByFileName(String worldFileName) {
        for (CreativeWorld world : worlds) {
            if (world.getWorldFileName().equals(worldFileName))
                return world;
        }
        return null;
    }

    public int getNextWorldId() {
        int max = 0;
        String uuidPrefix = getUUID().toString().toLowerCase() + "_";

        /* Check DB worlds */
        for (CreativeWorld world : worlds) {
            max = Math.max(max, parseWorldId(world.getWorldFileName(), uuidPrefix));
        }

        /* Check world folders on disk (covers deleted-from-DB but not-from-disk) */
        File worldsDir = Bukkit.getWorldContainer().getAbsoluteFile();
        if (worldsDir.isDirectory()) {
            File[] dirs = worldsDir.listFiles(File::isDirectory);
            if (dirs != null) {
                for (File dir : dirs) {
                    if (dir.getName().startsWith(uuidPrefix)) {
                        max = Math.max(max, parseWorldId(dir.getName(), uuidPrefix));
                    }
                }
            }
        }

        return max + 1;
    }

    private static int parseWorldId(String fileName, String uuidPrefix) {
        if (!fileName.startsWith(uuidPrefix))
            return 0;

        try {
            return Integer.parseInt(fileName.substring(uuidPrefix.length()));
        } catch (NumberFormatException ignored) {
            return 0;
        }
    }
}
