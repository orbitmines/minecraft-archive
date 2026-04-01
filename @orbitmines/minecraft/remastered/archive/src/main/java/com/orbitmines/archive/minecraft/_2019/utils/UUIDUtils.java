package com.orbitmines.archive.minecraft._2019.utils;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.database.models.PlayerModel;
import com.orbitmines.archive.minecraft._2019.utils.api.mojang.NameHistoryFetcher;
import com.orbitmines.archive.minecraft._2019.utils.api.mojang.UUIDFetcher;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UUIDUtils {

    public static String trim(UUID uuid) {
        return uuid.toString().replaceAll("-", "");
    }

    public static UUID parse(String trimmed) throws IndexOutOfBoundsException {
        return UUID.fromString(trimmed.substring(0, 8) + "-" + trimmed.substring(8, 12) + "-" + trimmed.substring(12, 16) + "-" + trimmed.substring(16, 20) + "-" + trimmed.substring(20, 32));
    }

    public static List<UUID> parseUUIDList(Collection<String> list) {
        List<UUID> uuids = new ArrayList<>();

        for (String s : list) {
            if (s.isEmpty())
                continue;

            uuids.add(UUID.fromString(s));
        }

        return uuids;
    }

    /* Set forceApiCall = true, when the last known username in our database is not good enough */

    public static UUID getUUID(String playerName) {
        return getUUID(playerName, false, false);
    }

    public static UUID getUUID(String playerName, boolean forceApiCall) {
        return getUUID(playerName, true, forceApiCall);
    }

    public static UUID getUUID(String playerName, boolean allowApiCall, boolean forceApiCall) {
        if (!allowApiCall || !forceApiCall) {
            /* Get from cache */
            if (cachedUuids.containsKey(playerName.toLowerCase()))
                return cachedUuids.get(playerName.toLowerCase());

            /* Otherwise from database */
            UUID uuid = getUUIDFromDatabase(playerName);

            if (uuid != null || !allowApiCall)
                return uuid;
        }

        /* Otherwise from Mojang API */
        Map<String, UUID> uuids = fetchUUIDs(Collections.singletonList(playerName));
        if (uuids == null)
            return null;

        return uuids.get(playerName);
    }

    public static Map<String, UUID> getUUIDs(String... playerNames) {
        return getUUIDs(playerNames, false);
    }

    public static Map<String, UUID> getUUIDs(String[] playerNames, boolean forceApiCall) {
        return getUUIDs(Arrays.asList(playerNames), forceApiCall);
    }

    public static Map<String, UUID> getUUIDs(Collection<String> playerNames) {
        return getUUIDs(playerNames, false);
    }

    public static Map<String, UUID> getUUIDs(Collection<String> playerNames, boolean forceApiCall) {
        Map<String, UUID> uuids;

        if (!forceApiCall) {
            /* Get from cache */
            uuids = playerNames.stream().
                    map(String::toLowerCase).
                    filter(cachedUuids::containsKey).
                    collect(Collectors.toMap(Function.identity(), cachedUuids::get));

            if (uuids.size() == playerNames.size())
                return uuids;

            playerNames.removeAll(uuids.keySet());

            /* Otherwise from database */
            uuids.putAll(getUUIDsFromDatabase(playerNames));

            if (uuids.size() == playerNames.size())
                return uuids;

            playerNames.removeAll(uuids.keySet());
        } else {
            uuids = new HashMap<>();
        }

        /* Otherwise from Mojang API */
        Map<String, UUID> fetchedUuids = fetchUUIDs(playerNames);

        if (fetchedUuids == null)
            return uuids;

        uuids.putAll(fetchedUuids);

        return uuids;
    }

    private final static Map<String, UUID> cachedUuids = new HashMap<>();
    private final static Map<UUID, String> cachedPlayerNames = new HashMap<>();

    private static Map<String, UUID> fetchUUIDs(Collection<String> playerNames) {
        Map<String, UUID> uuids = new HashMap<>();

        try {
            /* Get from Mojang API */
            Map<String, UUID> fetchedUuids = new UUIDFetcher(playerNames).call();

            for (String playerName : fetchedUuids.keySet()) {
                UUID uuid = fetchedUuids.get(playerName);

                cachedUuids.put(playerName.toLowerCase(), uuid);
                cachedPlayerNames.put(uuid, playerName);
            }
            uuids.putAll(fetchedUuids);

            return uuids;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getName(UUID uuid) {
        return getName(uuid, false, false);
    }

    public static String getName(UUID uuid, boolean forceApiCall) {
        return getName(uuid, true, forceApiCall);
    }

    public static String getName(UUID uuid, boolean allowApiCall, boolean forceApiCall) {
        if (!allowApiCall || !forceApiCall) {
            /* Get from cache */
            if (cachedPlayerNames.containsKey(uuid))
                return cachedPlayerNames.get(uuid);

            /* Otherwise from database */
            String name = getNameFromDatabase(uuid);

            if (name != null || !allowApiCall)
                return name;
        }

        /* Otherwise fetch from Mojang API */
        NameHistoryFetcher.JsonNameHistory[] history = getNameHistory(uuid, true);

        if (history == null)
            return null;

        return history[history.length - 1].name;
    }

    public static Map<UUID, String> getNames(Collection<UUID> uuids) {
        return getNames(uuids, false);
    }

    public static Map<UUID, String> getNames(Collection<UUID> uuids, boolean forceApiCall) {
        Map<UUID, String> names;

        if (!forceApiCall) {
            /* Get from cache */
            names = uuids.stream().
                    filter(cachedPlayerNames::containsKey).
                    collect(Collectors.toMap(Function.identity(), cachedPlayerNames::get));

            if (names.size() == uuids.size())
                return names;

            uuids.removeAll(names.keySet());

            /* Otherwise from database */
            names.putAll(getNamesFromDatabase(uuids));

            if (names.size() == uuids.size())
                return names;

            uuids.removeAll(names.keySet());
        } else {
            names = new HashMap<>();
        }

        /* Otherwise fetch from Mojang API */
        for (UUID uuid : uuids) {
            names.put(uuid, getName(uuid, true));
        }

        return names;
    }

    private final static Map<UUID, NameHistoryFetcher.JsonNameHistory[]> cachedNameHistory = new HashMap<>();

    public static NameHistoryFetcher.JsonNameHistory[] getNameHistory(UUID uuid, boolean forceApiCall) {
        /* Get from cache */
        if (!forceApiCall && !cachedNameHistory.containsKey(uuid))
            return cachedNameHistory.get(uuid);

        try {
            /* Otherwise from Mojang API */
            NameHistoryFetcher.JsonNameHistory[] nameHistory = new NameHistoryFetcher(uuid).call();

            if (nameHistory != null) {
                String name = nameHistory[nameHistory.length - 1].name;

                cachedNameHistory.put(uuid, nameHistory);
                cachedUuids.put(name.toLowerCase(), uuid);
                cachedPlayerNames.put(uuid, name);
            }

            return nameHistory;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Map<UUID, String> getNamesFromDatabase(Collection<UUID> uuids) {
        List<PlayerModel> models = PlayerModel.getAll(PlayerModel.class, PlayerModel.column.UUID.is(uuids));

        Map<UUID, String> names = new HashMap<>();
        for (PlayerModel model : models) {
            String name = model.getName();
            UUID uuid = model.getUUID();

            cachedUuids.put(name.toLowerCase(), uuid);
            cachedPlayerNames.put(uuid, name);

            names.put(uuid, name);
        }

        return names;
    }

    private static String getNameFromDatabase(UUID uuid) {
        PlayerModel model = PlayerModel.findBy(PlayerModel.class, PlayerModel.column.UUID.is(uuid));

        if (model == null)
            return null;

        String name = model.getName();

        cachedUuids.put(name.toLowerCase(), uuid);
        cachedPlayerNames.put(uuid, name);

        return model.getName();
    }

    private static UUID getUUIDFromDatabase(String playerName) {
        PlayerModel model = PlayerModel.findBy(PlayerModel.class, PlayerModel.column.NAME.is(playerName));

        if (model == null)
            return null;

        String name = model.getName(); /* playerName can be case insensitive */
        UUID uuid = model.getUUID();

        cachedUuids.put(name.toLowerCase(), uuid);
        cachedPlayerNames.put(uuid, name);

        return uuid;
    }

    private static Map<String, UUID> getUUIDsFromDatabase(Collection<String> playerNames) {
        List<PlayerModel> models = PlayerModel.getAll(PlayerModel.class, PlayerModel.column.NAME.is(playerNames));

        Map<String, UUID> uuids = new HashMap<>();
        for (PlayerModel model : models) {
            String name = model.getName();
            UUID uuid = model.getUUID();

            cachedUuids.put(name.toLowerCase(), uuid);
            cachedPlayerNames.put(uuid, name);

            uuids.put(name, uuid);
        }

        return uuids;
    }

    public static void clearCacheFor(UUID uuid) {
        cachedNameHistory.remove(uuid);
        cachedPlayerNames.remove(uuid);
    }

    public static void clearCacheFor(String playerName) {
        cachedUuids.remove(playerName.toLowerCase());
    }
}
