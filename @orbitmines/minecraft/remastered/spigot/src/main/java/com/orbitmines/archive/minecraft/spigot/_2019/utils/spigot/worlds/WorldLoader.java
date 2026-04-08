package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.utils.FileUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.WorldUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.generators.DefaultWorldType;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.generators.WorldType;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WorldLoader {

    private final String resourceFolder;

    @Getter private List<World> worlds;
    private List<World> normalWorlds;
    private boolean cleanUpPlayerData;

    public WorldLoader(String resourceFolder, boolean cleanUpPlayerData) {
        this.resourceFolder = resourceFolder;

        this.worlds = new ArrayList<>();
        this.normalWorlds = new ArrayList<>();
        this.cleanUpPlayerData = cleanUpPlayerData;

        cleanUpData();
    }

    /*
        loadWorld : Create normal worlds; need saving.
     */
    public World loadWorld(String worldFile) {
        return loadWorld(worldFile, false);
    }

    public World loadWorld(String worldFile, boolean removeEntities) {
        return loadWorld(worldFile, removeEntities, DefaultWorldType.NORMAL);
    }

    public World loadWorld(String worldFile, boolean removeEntities, WorldType type) {
        try {
            World world = Bukkit.createWorld(type.getWorldCreator().getConstructor(String.class).newInstance(worldFile).generateStructures(false).environment(type.getEnvironment()));
            world.setAutoSave(true);
            normalWorlds.add(world);

            if (removeEntities)
                WorldUtils.removeEntities(world);

            return world;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     *   The following is only for lobbies & maps that don't need saving.
     *   They are stored in {resources.yml-path}/<world name>.zip
     *   The zip will look like this:
     *
     *   DO NOT use spaces in world names.
     *
     *   World.zip
     *      -> World
     *         -> regions
     *         -> data
     *         etc.
     * */

    /*
        fromZip : Pre-configured worlds.
     */

    public World fromDirectory(File sourceDir, String worldName, boolean removeEntities, WorldType type) {
        cleanUpPreviousWorld(worldName);

        try {
            File destDir = new File(Bukkit.getWorldContainer().getAbsoluteFile(), worldName);
            FileUtils.copyDirectory(sourceDir, destDir);

            /* Delete uid.dat so Minecraft generates a fresh unique world ID (avoids duplicate world rejection) */
            File uidFile = new File(destDir, "uid.dat");
            if (uidFile.exists())
                uidFile.delete();

            World world = Bukkit.createWorld(type.getWorldCreator().getConstructor(String.class).newInstance(worldName).generateStructures(false).environment(type.getEnvironment()));
            worlds.add(world);

            if (removeEntities)
                WorldUtils.removeEntities(world);

            return world;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public World fromZip(String worldFile) {
        return fromZip(worldFile, false);
    }

    public World fromZip(String worldFile, boolean removeEntities) {
        return fromZip(worldFile, removeEntities, DefaultWorldType.NORMAL);
    }

    public World fromZip(String worldFile, boolean removeEntities, WorldType type) {
        cleanUpPreviousWorld(worldFile);

        try {
            FileUtils.extractZip(new File(resourceFolder + "/" + worldFile + ".zip"), Bukkit.getWorldContainer().getAbsoluteFile());
            World world = Bukkit.createWorld(type.getWorldCreator().getConstructor(String.class).newInstance(worldFile).generateStructures(false).environment(type.getEnvironment()));
            worlds.add(world);

            if (removeEntities)
                WorldUtils.removeEntities(world);

            return world;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void cleanUpPreviousWorld(String worldFile) {
        File file = new File(resourceFolder + "/" + worldFile);

        if (file.exists())
            FileUtils.deleteDirectory(file);
    }

    public void cleanUp() {
        for (World world : worlds) {
            cleanUp(world);
        }
        for (World world : normalWorlds) {
            Bukkit.unloadWorld(world, true);
        }

        cleanUpData();
    }

    private void cleanUpData() {
        if (cleanUpPlayerData) {
            deletePlayerData("world");
            deletePlayerData("world_nether");
            deletePlayerData("world_the_end");
        }

        /* Cleanup MacOS compiled zips */
        FileUtils.deleteDirectory(new File(Bukkit.getWorldContainer().getAbsoluteFile().getPath() + "/__MACOSX"));
    }

    public void cleanUp(World world) {
        Bukkit.unloadWorld(world, false);
        FileUtils.deleteDirectory(world.getWorldFolder());
    }

    private void deletePlayerData(String worldName) {
        deleteWorldData(worldName, "advancements");
        deleteWorldData(worldName, "playerdata");
        deleteWorldData(worldName, "stats");
    }

    private void deleteWorldData(String worldName, String directoryName) {
        FileUtils.deleteDirectory(new File(worldName + "/" + directoryName));
    }

}
