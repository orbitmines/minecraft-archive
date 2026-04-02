package com.orbitmines.archive.minecraft._2019.utils.state;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.database.models.StateEntry;

import java.util.*;

public class StateProvider {

    private static StateProvider instance;

    public static void initialize(StateProvider provider) {
        instance = provider;
    }

    public static StateProvider getInstance() {
        return instance;
    }

    /* Player data — stored as player:<uuid>:<field> */

    public Map<String, String> getPlayerData(UUID uuid) {
        String prefix = "player:" + uuid + ":";
        ArrayList<StateEntry> entries = StateEntry.getAllByPrefix(prefix);
        if (entries.isEmpty())
            return null;

        Map<String, String> data = new HashMap<>();
        for (StateEntry entry : entries) {
            data.put(entry.getKey().substring(prefix.length()), entry.getValue());
        }
        return data;
    }

    public Set<UUID> getAllPlayerUUIDs() {
        ArrayList<StateEntry> entries = StateEntry.getAllByPrefix("player:");
        Set<UUID> uuids = new HashSet<>();
        for (StateEntry entry : entries) {
            String[] parts = entry.getKey().split(":");
            if (parts.length >= 2)
                uuids.add(UUID.fromString(parts[1]));
        }
        return uuids;
    }

    public void setPlayerData(UUID uuid, Map<String, String> data) {
        StateEntry.removeAllByPrefix("player:" + uuid + ":");
        for (Map.Entry<String, String> field : data.entrySet()) {
            StateEntry.set("player:" + uuid + ":" + field.getKey(), field.getValue());
        }
    }

    public void setPlayerField(UUID uuid, String field, String value) {
        StateEntry.set("player:" + uuid + ":" + field, value);
    }

    public void removePlayerData(UUID uuid) {
        StateEntry.removeAllByPrefix("player:" + uuid + ":");
    }

    public String getPlayerField(UUID uuid, String field) {
        return StateEntry.get("player:" + uuid + ":" + field);
    }

    /* Server state — stored as server:<pluginName>:status, server:<pluginName>:player:<name> */

    public long getServerPlayerCount(String pluginName) {
        return StateEntry.getAllByPrefix("server:" + pluginName + ":player:").size();
    }

    public String getServerStatus(String pluginName) {
        return StateEntry.get("server:" + pluginName + ":status");
    }

    public void setServerStatus(String pluginName, String status) {
        StateEntry.set("server:" + pluginName + ":status", status);
    }

    public Set<String> getServerPlayers(String pluginName) {
        String prefix = "server:" + pluginName + ":player:";
        ArrayList<StateEntry> entries = StateEntry.getAllByPrefix(prefix);
        Set<String> players = new HashSet<>();
        for (StateEntry entry : entries) {
            players.add(entry.getKey().substring(prefix.length()));
        }
        return players;
    }

    public void addServerPlayer(String pluginName, String playerName) {
        StateEntry.set("server:" + pluginName + ":player:" + playerName, "1");
    }

    public void removeServerPlayer(String pluginName, String playerName) {
        StateEntry.remove("server:" + pluginName + ":player:" + playerName);
    }

    public void clearServerPlayers(String pluginName) {
        StateEntry.removeAllByPrefix("server:" + pluginName + ":player:");
    }

    public boolean isServerBlacklisted(String serverName) {
        return StateEntry.get("server:" + serverName + ":blacklisted") != null;
    }

    /* Generic key-value storage */

    public String getString(String key) {
        return StateEntry.get(key);
    }

    public void setString(String key, String value) {
        StateEntry.set(key, value);
    }

    public void deleteString(String key) {
        StateEntry.remove(key);
    }

    /* Batch operations */

    public Map<String, String> getServerStatusBatch(Collection<String> pluginNames) {
        Map<String, String> result = new HashMap<>();
        for (String name : pluginNames) {
            String status = getServerStatus(name);
            if (status != null)
                result.put(name, status);
        }
        return result;
    }

    public Map<UUID, Map<String, String>> getPlayerDataBatch(Collection<UUID> uuids) {
        Map<UUID, Map<String, String>> result = new HashMap<>();
        for (UUID uuid : uuids) {
            Map<String, String> data = getPlayerData(uuid);
            if (data != null)
                result.put(uuid, data);
        }
        return result;
    }

    /* Cleanup */

    public void clearAllPlayerData() {
        StateEntry.removeAllByPrefix("player:");
    }

    public void clearAllServerData() {
        StateEntry.removeAllByPrefix("server:");
    }
}
