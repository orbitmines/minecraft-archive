package com.orbitmines.archive.minecraft._2019.libs;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.player.PlayerInstance;
import com.orbitmines.archive.minecraft._2019.libs.rank.Rank;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;
import com.orbitmines.archive.minecraft._2019.utils.state.StateProvider;
import lombok.Getter;

import java.util.*;

public enum Server {

    /* Sorted on connection priority */
    BUNGEECORD(Color.BLUE, "Bungeecord", "bungeecord", -1, null),
    HUB(Color.TEAL, "Hub", "hub", 100, VipRank.NONE),
    SURVIVAL(Color.LIME, "Survival", "survival", 100, VipRank.NONE),
    KITPVP(Color.RED, "KitPvP", "kitpvp", 100, VipRank.NONE),
//    BUILD(Color.FUCHSIA, "Build", "build", 100, StaffRank.PROVISIONAL_BUILDER),
    PRISON(Color.MAROON, "Prison", "prison", 100),
    CREATIVE(Color.FUCHSIA, "Creative", "creative", 100),
    SKYBLOCK(Color.PURPLE, "SkyBlock", "skyblock", 100),
    FOG(Color.YELLOW, "Fractals of the Galaxy", "fog", 100),
    MINIGAMES(Color.WHITE, "Minigames", "minigames", 100);
//    UHSURVIVAL(Color.GREEN, "UHSurvival", "uhsurvival", 100);

    public static final String SERVER_DISPLAY_NAME = "§8§lOrbit§7§lMines";

    @Getter private final Color color;
    @Getter private final String name;
    @Getter private final String pluginName;
    @Getter private final int maxPlayers;
    @Getter private Rank rank;

    Server(Color color, String name, String pluginName, int maxPlayers) {
        this(color, name, pluginName, maxPlayers, VipRank.NONE);
    }

    Server(Color color, String name, String pluginName, int maxPlayers, Rank rank) {
        this.color = color;
        this.name = name;
        this.pluginName = pluginName;
        this.maxPlayers = maxPlayers;
        this.rank = rank;
    }

    public String getDisplayName() {
        return this.color.getCc() + "§l" + this.name;
    }

    public long getOnline() {
        return StateProvider.getInstance().getServerPlayerCount(pluginName);
    }

    public Status getStatus() {
        String status = StateProvider.getInstance().getServerStatus(pluginName);
        return status != null ? Status.valueOf(status) : Status.OFFLINE;
    }

    public void setStatus(Status status) {
        StateProvider.getInstance().setServerStatus(pluginName, status.toString());
    }

    /* Returns list of players currently online */
    public Set<String> getPlayers() {
        return StateProvider.getInstance().getServerPlayers(pluginName);
    }

    public long getPlayerCount() {
        return StateProvider.getInstance().getServerPlayerCount(pluginName);
    }

    public boolean isBlacklisted() {
        return StateProvider.getInstance().isServerBlacklisted(toString());
    }

    public static Server getFromPluginName(String pluginName) {
        for (Server server : Server.values()) {
            if (server.getPluginName().equals(pluginName))
                return server;
        }
        return null;
    }

    public static Server getPlayingOn(UUID uuid) {
        String server = StateProvider.getInstance().getPlayerField(uuid, "server");
        return server != null ? Server.valueOf(server) : null;
    }

    public static Server getPlayingOn(String playerName) {
        /* Search through all player data to find by name */
        StateProvider state = StateProvider.getInstance();
        for (UUID uuid : state.getAllPlayerUUIDs()) {
            Map<String, String> data = state.getPlayerData(uuid);
            if (data != null && playerName.equals(data.get("name"))) {
                String server = data.get("server");
                return server != null ? Server.valueOf(server) : null;
            }
        }
        return null;
    }

    /* Returns list of Servers which are currently active */
    public static Collection<Server> getAllowed(PlayerInstance player) {
        List<Server> active = new ArrayList<>();
        for (Server server : values()) {
            if (server.getRank() != null && player.isEligible(server.getRank()))
                active.add(server);
        }
        return active;
    }
    public static Collection<Server> getAllowed(PlayerInstance player, int limit) {
        List<Server> active = new ArrayList<>();
        for (Server server : values()) {
            if (server.getRank() != null && player.isEligible(server.getRank()))
                active.add(server);

            if (active.size() == limit)
                break;
        }
        return active;
    }

    /* Returns list of servers with the given status */
    public static Collection<Server> getWith(PlayerInstance player, Status... statuses) {
        Collection<Server> allowed = getAllowed(player);
        List<String> pluginNames = new ArrayList<>();
        for (Server server : allowed) {
            pluginNames.add(server.pluginName);
        }

        Map<String, String> statusMap = StateProvider.getInstance().getServerStatusBatch(pluginNames);

        List<Server> servers = new ArrayList<>();
        for (Server server : allowed) {
            String statusString = statusMap.get(server.pluginName);
            Status status = statusString != null ? Status.valueOf(statusString) : Status.OFFLINE;

            for (Status s : statuses) {
                if (status != s)
                    continue;

                servers.add(server);
                break;
            }
        }

        return servers;
    }

    /* Returns list of player names that are currently online */
    public static Set<String> getAllPlayers(PlayerInstance player) {
        Collection<Server> servers = getWith(player, Status.ONLINE, Status.MAINTENANCE);

        Set<String> players = new HashSet<>();
        for (Server server : servers) {
            players.addAll(StateProvider.getInstance().getServerPlayers(server.pluginName));
        }

        return players;
    }

    public static Set<Server> playable() {
        Set<Server> servers = new HashSet<>(Arrays.asList(values()));
        servers.remove(BUNGEECORD);

        return servers;
    }

    //
//    HUB("Hub", Color.TEAL, HubAchievements.class, TableTimePlayed.HUB),
//    SURVIVAL("Survival", Color.LIME, SurvivalAchievements.class, TableTimePlayed.SURVIVAL),
//    KITPVP("KitPvP", Color.RED, KitPvPAchievements.class, TableTimePlayed.KITPVP),
//
//    PRISON("Prison", Color.MAROON, PrisonAchievements.class, TableTimePlayed.PRISON),
//    CREATIVE("Creative", Color.FUCHSIA, CreativeAchievements.class, TableTimePlayed.CREATIVE),
//    SKYBLOCK("SkyBlock", Color.PURPLE, SkyBlockAchievements.class, TableTimePlayed.SKYBLOCK),
//    FOG("FoG", Color.YELLOW, FoGAchievements.class, TableTimePlayed.FOG),
//    MINIGAMES("MiniGames", Color.WHITE, MiniGamesAchievements.class, TableTimePlayed.MINIGAMES),
//    UHSURVIVAL("UHSurvival", Color.GREEN, UHSurvivalAchievements.class, TableTimePlayed.UHSURVIVAL);
//
//    @Getter private final String name;
//    @Getter private final Color color;
//    @Getter private final Class<? extends Enum> achievementClass;
////    private final Column playTimeColumn;
//
//    Server(String name, Color color, Class<? extends Enum> achievementClass/*, Column playTimeColumn*/) {
//        this.name = name;
//        this.color = color;
//        this.achievementClass = achievementClass;
////        this.playTimeColumn = playTimeColumn;
//    }
//
//    public String getDisplayName() {
//        return color.getCc() + "§l" + name;
//    }
//
//    public Achievement achievement(String value) {
//        return (Achievement) Enum.valueOf(achievementClass, value);
//    }
//
//    public Achievement[] achievements() {
//        return (Achievement[]) achievementClass.getEnumConstants();
//    }
//
    public enum Status {

        ONLINE("Online", Color.LIME),
        OFFLINE("Offline", Color.RED),
        MAINTENANCE("Maintenance", Color.FUCHSIA),
        RESTARTING("Restarting", Color.GRAY);

        @Getter private final String name;
        @Getter private final Color color;

        Status(String name, Color color) {
            this.name = name;
            this.color = color;
        }

        public String getDisplayName() {
            return color.getCc() + "§l" + name;
        }
    }
}
