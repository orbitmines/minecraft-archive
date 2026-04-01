package com.orbitmines.archive.minecraft._2019.utils.state;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import java.util.*;

public abstract class StateProvider {

    private static StateProvider instance;

    public static void initialize(StateProvider provider) {
        instance = provider;
    }

    public static StateProvider getInstance() {
        return instance;
    }

    /* Player data */
    public abstract Map<String, String> getPlayerData(UUID uuid);
    public abstract Set<UUID> getAllPlayerUUIDs();
    public abstract void setPlayerData(UUID uuid, Map<String, String> data);
    public abstract void setPlayerField(UUID uuid, String field, String value);
    public abstract void removePlayerData(UUID uuid);
    public abstract String getPlayerField(UUID uuid, String field);

    /* Server state */
    public abstract long getServerPlayerCount(String pluginName);
    public abstract String getServerStatus(String pluginName);
    public abstract void setServerStatus(String pluginName, String status);
    public abstract Set<String> getServerPlayers(String pluginName);
    public abstract void addServerPlayer(String pluginName, String playerName);
    public abstract void removeServerPlayer(String pluginName, String playerName);
    public abstract void clearServerPlayers(String pluginName);
    public abstract boolean isServerBlacklisted(String serverName);

    /* Generic key-value storage */
    public abstract String getString(String key);
    public abstract void setString(String key, String value);
    public abstract void deleteString(String key);

    /* Batch operations */
    public abstract Map<String, String> getServerStatusBatch(Collection<String> pluginNames);
    public abstract Map<UUID, Map<String, String>> getPlayerDataBatch(Collection<UUID> uuids);
}
