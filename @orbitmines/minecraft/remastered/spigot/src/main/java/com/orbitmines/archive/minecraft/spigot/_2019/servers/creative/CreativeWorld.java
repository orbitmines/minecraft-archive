package com.orbitmines.archive.minecraft.spigot._2019.servers.creative;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.google.gson.JsonArray;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import com.orbitmines.archive.minecraft._2019.utils.FileUtils;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.OMMap;
import com.orbitmines.archive.minecraft.spigot._2019.servers.minigames.Minigame;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.generators.DefaultWorldType;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;

public class CreativeWorld {

    private final Creative creative;
    private final OMMap map;

    @Getter private final UUID ownerUuid;
    @Getter @Setter private String ownerName;
    @Getter @Setter private volatile boolean loading;

    private List<CreativeWorldMember> members;

    public CreativeWorld(Creative creative, OMMap map, UUID ownerUuid) {
        this.creative = creative;
        this.map = map;
        this.ownerUuid = ownerUuid;
        this.members = CreativeWorldMember.getAll(CreativeWorldMember.class, CreativeWorldMember.column.MAP_ID.is(getId()));
    }

    public CreativeWorld(Creative creative, UUID ownerUuid, String name, String worldFileName, DefaultWorldType worldType) {
        this.creative = creative;

        JsonArray authors = new JsonArray();
        authors.add(ownerUuid.toString());

        this.map = new OMMap(Server.CREATIVE, name, worldFileName, worldType, OMMap.Type.GAMEMAP, false, authors, DateUtils.now());
        this.ownerUuid = ownerUuid;
        this.members = new ArrayList<>();
    }

    /** Copy world files from archive if needed. Safe to call from any thread. */
    public void ensureWorldFiles() throws java.io.IOException {
        File worldDir = new File(Bukkit.getWorldContainer().getAbsoluteFile(), getWorldFileName());
        if (!worldDir.exists()) {
            File source = creative.findWorldSource(getWorldFileName());
            if (source != null) {
                Bukkit.getLogger().info("[creative] Copying world: " + source.getAbsolutePath() + " -> " + worldDir.getAbsolutePath());
                FileUtils.copyDirectory(source, worldDir);

                /* Remove old entity region files — they have misplaced chunks from pre-1.17 conversion
                   and cause ReportedException "Chunk found in invalid location". Minecraft regenerates
                   entity data from chunk data during world upgrade. */
                File entitiesDir = new File(worldDir, "entities");
                if (entitiesDir.isDirectory()) {
                    Bukkit.getLogger().info("[creative] Removing old entities dir: " + entitiesDir.getAbsolutePath());
                    FileUtils.deleteDirectory(entitiesDir);
                }

                /* Delete uid.dat so Minecraft generates a fresh unique world ID */
                File uidFile = new File(worldDir, "uid.dat");
                if (uidFile.exists())
                    uidFile.delete();
            } else {
                Bukkit.getLogger().warning("[creative] Source not found for world: " + getWorldFileName());
            }
        }
    }

    /** Load/create the Bukkit world and configure it. MUST be called on the main thread. */
    public World loadOrCreateWorld() {
        try {
            World world = creative.getWorldLoader().loadWorld(getWorldFileName(), false, getWorldGenerator());
            Bukkit.getLogger().info("[creative] loadOrCreateWorld: requested=" + getWorldFileName() + " got=" + (world != null ? world.getName() : "null"));
            world.setAutoSave(true);

            world.setTime(6000);

            world.setGameRule(GameRule.SPAWN_MOBS, false);
            world.setGameRule(GameRule.ADVANCE_TIME, false);
            world.setGameRule(GameRule.KEEP_INVENTORY, true);
            world.setGameRule(GameRule.MOB_GRIEFING, false);
            world.setGameRule(GameRule.MOB_DROPS, false);
            world.setGameRule(GameRule.COMMAND_BLOCK_OUTPUT, false);
            world.setGameRule(GameRule.BLOCK_DROPS, false);
            world.setGameRule(GameRule.ENTITY_DROPS, false);

            world.setDifficulty(Difficulty.PEACEFUL);
            world.setPVP(false);

            map.setup(world);

            return world;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void unload() {
        World world = getWorld();
        if (world == null)
            return;

        /* Teleport remaining players to fallback */
        for (Player player : world.getPlayers()) {
            player.teleport(creative.getLobbySpawn());
        }

        Bukkit.unloadWorld(world, true);
        map.setup(null);
    }

    /** Teleport players out and unload the world. Must be called on the main thread. */
    public void unloadForDelete() {
        World world = getWorld();
        if (world == null)
            return;

        for (Player player : world.getPlayers()) {
            player.teleport(creative.getLobbySpawn());
        }

        Bukkit.unloadWorld(world, false);
    }

    /** Delete all database records. Must be called on an async thread. */
    public void deleteFromDatabase() {
        for (CreativeWorldMember member : getMembers(true)) {
            member.delete();
        }

        map.delete();
    }

    public boolean isLoaded() {
        return getWorld() != null;
    }

    public boolean canBuild(UUID uuid) {
        if (ownerUuid.equals(uuid))
            return true;

        return getMember(uuid, false) != null;
    }

    public String getOwnerName() {
        return ownerName != null ? ownerName : ownerUuid.toString();
    }

    /*

        Members (persistent)

     */

    public void reloadMembers() {
        this.members = CreativeWorldMember.getAll(CreativeWorldMember.class, CreativeWorldMember.column.MAP_ID.is(getId()));
    }

    public List<CreativeWorldMember> getMembers(boolean reload) {
        if (reload)
            reloadMembers();

        return members;
    }

    public CreativeWorldMember getMember(UUID uuid, boolean reload) {
        for (CreativeWorldMember member : getMembers(reload)) {
            if (member.getUuid().equals(uuid))
                return member;
        }
        return null;
    }

    public List<UUID> getMemberUuids() {
        List<UUID> uuids = new ArrayList<>();
        for (CreativeWorldMember member : getMembers(false)) {
            uuids.add(member.getUuid());
        }
        return uuids;
    }

    public void addMember(UUID uuid) {
        if (getMember(uuid, false) != null)
            return;

        CreativeWorldMember member = new CreativeWorldMember(getId(), uuid);
        member.insert();
        reloadMembers();
    }

    public void removeMember(UUID uuid) {
        CreativeWorldMember member = getMember(uuid, false);
        if (member == null)
            return;

        member.delete();
        reloadMembers();
    }

    /*

        Minigame (encoded in OMMap.Type)

     */

    public Minigame getMinigame() {
        return Minigame.fromMapType(getWorldType());
    }

    public void setMinigame(Minigame minigame) {
        if (minigame == null) {
            map.setWorldType(OMMap.Type.GAMEMAP);
        } else {
            map.setWorldType(minigame.getMapType());
        }
    }

    /*

        Spawn Location (persistent via Bukkit world level.dat)

     */

    public void setSpawnLocation(Location location) {
        World world = getWorld();
        if (world != null) {
            world.setSpawnLocation(location);
        }
    }

    /*

        OMMap Delegates

     */

    public Long getId() {
        return map.getId();
    }

    public Server getServer() {
        return map.getServer();
    }

    public void setServer(Server server) {
        map.setServer(server);
    }

    public String getName() {
        return map.getName();
    }

    public void setName(String name) {
        map.setName(name);
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

    public void setWorldType(OMMap.Type type) {
        map.setWorldType(type);
    }

    public Boolean getEnabled() {
        return map.getEnabled();
    }

    public void setEnabled(Boolean enabled) {
        map.setEnabled(enabled);
    }

    public JsonArray getAuthors() {
        return map.getAuthors();
    }

    public Date getCreatedAt() {
        return map.getCreatedAt();
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

    public void reload() {
        map.reload();
    }

}
