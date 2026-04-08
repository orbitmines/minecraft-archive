package com.orbitmines.archive.minecraft._2019.utils.database;

import com.orbitmines.archive.minecraft._2019.libs.Environment;
import com.orbitmines.archive.minecraft._2019.libs.Server;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Seeds the maps table from the world directories in the archive.
 */
public class WorldSeeder {

    private static final Logger logger = Logger.getLogger(WorldSeeder.class.getName());

    // Matches: YYYY-MM-DD, YYYY-MM, or YYYY at the start, followed by _ and the world name
    private static final Pattern DATE_NAME_PATTERN = Pattern.compile("^(\\d{4}(?:-\\d{2}(?:-\\d{2})?)?)_(.+)$");

    // Standalone world/world_nether/world_the_end (exact match, no prefix like "survival")
    private static final Pattern STANDALONE_VANILLA = Pattern.compile("^(?:world|world_nether|world_the_end)$", Pattern.CASE_INSENSITIVE);

    public static void seed(SQLiteDatabase database, String worldsPath) throws Exception {
        File worldsDir = new File(worldsPath);
        if (!worldsDir.exists() || !worldsDir.isDirectory()) {
            logger.warning("Worlds directory not found: " + worldsPath);
            return;
        }

        File[] dirs = worldsDir.listFiles(File::isDirectory);
        if (dirs == null || dirs.length == 0) {
            logger.warning("No world directories found in: " + worldsPath);
            return;
        }

        Connection conn = database.getConnection();
        conn.setAutoCommit(false);

        try (Statement clearStmt = conn.createStatement()) {
            clearStmt.execute("DELETE FROM `maps`");

            String sql = "INSERT INTO `maps` (`server`, `name`, `world_file_name`, `world_generator`, `world_type`, `enabled`, `authors`, `created_at`) VALUES (?, ?, ?, ?, ?, ?, '[\"OM Staff Team\"]', ?)";

            try (PreparedStatement insert = conn.prepareStatement(sql)) {
                for (File dir : dirs) {
                    String dirName = dir.getName();

                    Matcher m = DATE_NAME_PATTERN.matcher(dirName);
                    if (!m.matches())
                        continue;

                    String dateStr = m.group(1);
                    String worldName = m.group(2);

                    // Skip standalone world/world_nether/world_the_end
                    if (STANDALONE_VANILLA.matcher(worldName).matches())
                        continue;

                    // Normalize date to datetime format
                    String createdAt = normalizeDate(dateStr);

                    Server server = classifyServer(worldName);
                    String worldType = classifyWorldType(worldName, server);
                    String worldGenerator = classifyWorldGenerator(worldName, server, worldType);
                    boolean enabled = isEnabled(dirName, worldType, server);

                    insert.setString(1, server.name());
                    insert.setString(2, dirName);
                    insert.setString(3, dirName);
                    insert.setString(4, worldGenerator);
                    insert.setString(5, worldType);
                    insert.setInt(6, enabled ? 1 : 0);
                    insert.setString(7, createdAt);
                    insert.addBatch();
                }

                insert.executeBatch();
            }

            conn.commit();
            logger.info("Maps table seeded from worlds directory.");
        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    private static final Set<String> ENABLED_LOBBIES = Set.of(
        "2019-06-17_Lobby_HUB",
        "2019-06-17_lobby_kitpvp",
        "2019-06-17_lobby_survival",
        "2015-08-02_CreativeLobby"
    );

    private static final Set<String> ENABLED_KITPVP_GAMEMAPS = Set.of(
        "2019-06-17_kitpvp_desert"
    );

    private static boolean isEnabled(String dirName, String worldType, Server server) {
        if ("LOBBY".equals(worldType))
            return ENABLED_LOBBIES.contains(dirName);

        if (server == Server.KITPVP && "GAMEMAP".equals(worldType))
            return ENABLED_KITPVP_GAMEMAPS.contains(dirName);

        return true;
    }

    private static String normalizeDate(String dateStr) {
        switch (dateStr.length()) {
            case 4: // YYYY
                return dateStr + "-01-01 00:00:00";
            case 7: // YYYY-MM
                return dateStr + "-01 00:00:00";
            default: // YYYY-MM-DD
                return dateStr + " 00:00:00";
        }
    }

    static Server classifyServer(String name) {
        String lower = name.toLowerCase();

        // Strip common prefixes (server context prefixes like "openmod_", "hub_", "alpha_", etc.)
        // These indicate the server the world was ON, but the world name after tells us the category
        String stripped = stripServerPrefix(lower);

        // Standalone vanilla worlds (with prefix like "survival_world", "kitpvp_world")
        // These belong to their prefix server, skip them only if truly standalone
        if (isVanillaWorldName(stripped))
            return classifyByPrefix(lower);

        // VoteShop -> PRISON
        if (lower.contains("voteshop"))
            return Server.PRISON;

        // ClaimTutorial -> SURVIVAL
        if (lower.contains("claimtutorial"))
            return Server.SURVIVAL;

        // KitPvP (must check before generic pvp)
        if (lower.contains("kitpvp"))
            return Server.KITPVP;

        // PvP (but NOT kitpvp) -> FOG
        if (lower.contains("pvp"))
            return Server.FOG;

        // Hub-like worlds
        if (lower.contains("hub") || lower.contains("orbitmines") || lower.contains("goldenrod")
                || lower.contains("joep") || lower.contains("faction"))
            return Server.HUB;

        // SurvivalGames -> MINIGAMES (must check before generic survival)
        if (lower.contains("survivalgame"))
            return Server.MINIGAMES;

        // Survival
        if (lower.contains("survival"))
            return Server.SURVIVAL;

        // Creative
        if (lower.contains("creative"))
            return Server.CREATIVE;

        // Prison
        if (lower.contains("prison"))
            return Server.PRISON;

        // Skyblock / Skyworld (NOT SkyWars)
        if (lower.contains("skyblock") || lower.contains("skyworld"))
            return Server.SKYBLOCK;

        // MiniGames
        if (lower.contains("minigame") || lower.contains("survivalgame") || lower.contains("chickenfight")
                || lower.contains("paintball") || lower.contains("quake") || lower.contains("spleef")
                || lower.contains("oitc") || lower.contains("hideandseek") || lower.contains("h&s")
                || lower.contains("uhc") || lower.contains("dropper") || lower.contains("sg")
                || lower.contains("skywar"))
            return Server.MINIGAMES;

        // Pixelmon -> MINIGAMES (no specific server)
        if (lower.contains("pixelmon"))
            return Server.MINIGAMES;

        // Build/Test -> HUB
        if (lower.contains("build") || lower.contains("test"))
            return Server.HUB;

        // Lobby without specific server context - check prefix
        if (lower.contains("lobby"))
            return classifyLobbyByName(lower);

        // Event worlds - try to classify by name context
        if (lower.contains("event"))
            return classifyEventByName(lower);

        // Default: MINIGAMES
        return Server.MINIGAMES;
    }

    private static String stripServerPrefix(String lower) {
        // Strip known server-context prefixes
        String[] prefixes = {
            "openmod_", "hub_", "alpha_", "prullenbak_", "prullenbak_nieuwe_",
            "summer-event_", "summer-event_openmod_", "orbitmines-test_",
            "luckyblock_", "prison_", "kitpvp_", "creative_", "skyblock_",
            "minigames_", "survival_", "spigot-test_", "1.7.9-test_",
            "torchcraft_", "uhc_", "mc-fs_",
            "test-build_", "test-hub_", "test-kitpvp_", "test-prison_", "test-survival_",
            "build_", "survival_ftp_",
        };

        for (String prefix : prefixes) {
            if (lower.startsWith(prefix))
                return lower.substring(prefix.length());
        }

        // Also handle "lobby_" prefix (e.g., "lobby_hub", "lobby_kitpvp")
        if (lower.startsWith("lobby_"))
            return lower.substring("lobby_".length());

        return lower;
    }

    private static boolean isVanillaWorldName(String name) {
        return name.equals("world") || name.equals("world_nether") || name.equals("world_the_end")
                || name.equals("worlds");
    }

    private static Server classifyByPrefix(String lower) {
        if (lower.startsWith("survival") || lower.startsWith("test-survival"))
            return Server.SURVIVAL;
        if (lower.startsWith("kitpvp") || lower.startsWith("test-kitpvp"))
            return Server.KITPVP;
        if (lower.startsWith("creative") || lower.startsWith("test-creative"))
            return Server.CREATIVE;
        if (lower.startsWith("prison") || lower.startsWith("test-prison"))
            return Server.PRISON;
        if (lower.startsWith("skyblock"))
            return Server.SKYBLOCK;
        if (lower.startsWith("minigames"))
            return Server.MINIGAMES;
        if (lower.startsWith("hub") || lower.startsWith("test-hub"))
            return Server.HUB;
        if (lower.startsWith("build") || lower.startsWith("test-build"))
            return Server.HUB;
        if (lower.startsWith("mc-fs"))
            return Server.HUB;
        // Summer event, openmod, etc. with vanilla world -> HUB
        return Server.HUB;
    }

    private static Server classifyLobbyByName(String stripped) {
        if (stripped.contains("kitpvp"))
            return Server.KITPVP;
        if (stripped.contains("survival"))
            return Server.SURVIVAL;
        if (stripped.contains("prison"))
            return Server.PRISON;
        if (stripped.contains("creative"))
            return Server.CREATIVE;
        if (stripped.contains("skyblock"))
            return Server.SKYBLOCK;
        if (stripped.contains("hub"))
            return Server.HUB;
        if (stripped.contains("minigame"))
            return Server.MINIGAMES;
        return Server.HUB;
    }

    private static Server classifyEventByName(String stripped) {
        if (stripped.contains("kitpvp"))
            return Server.KITPVP;
        if (stripped.contains("survival"))
            return Server.SURVIVAL;
        if (stripped.contains("prison"))
            return Server.PRISON;
        if (stripped.contains("creative"))
            return Server.CREATIVE;
        return Server.MINIGAMES;
    }

    static String classifyWorldType(String name, Server server) {
        String lower = name.toLowerCase();

        if (lower.contains("lobby") || lower.contains("hub") || lower.contains("halloween"))
            return "LOBBY";

        return "GAMEMAP";
    }

    static String classifyWorldGenerator(String name, Server server, String worldType) {
        String lower = name.toLowerCase();

        // Survival non-lobby worlds use NORMAL generator (except ClaimTutorial which is VOID)
        if (server == Server.SURVIVAL && worldType.equals("GAMEMAP") && !lower.contains("claimtutorial")) {
            if (lower.contains("nether"))
                return "NETHER";
            if (lower.contains("the_end") || lower.contains("the end"))
                return "END";
            return "NORMAL";
        }

        // Everything else is VOID
        return "VOID";
    }
}
