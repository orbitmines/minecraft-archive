package com.orbitmines.archive.minecraft._2019.utils.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Imports a MySQL dump file into a SQLite database, converting MySQL-specific
 * syntax to SQLite-compatible SQL.
 */
public class MySQLDumpImporter {

    private static final Pattern MYSQL_COMMENT = Pattern.compile("/\\*!\\d+.*?\\*/;?");
    private static final Pattern ENGINE_CLAUSE = Pattern.compile("\\)\\s*ENGINE=.*?;", Pattern.CASE_INSENSITIVE);
    private static final Pattern AUTO_INCREMENT = Pattern.compile("AUTO_INCREMENT", Pattern.CASE_INSENSITIVE);
    private static final Pattern INT_SIZE = Pattern.compile("(bigint|int|tinyint|smallint|mediumint)\\(\\d+\\)", Pattern.CASE_INSENSITIVE);
    private static final Pattern VARCHAR_CHARSET = Pattern.compile("(varchar\\(\\d+\\))\\s+CHARACTER SET \\w+\\s*(COLLATE \\w+)?", Pattern.CASE_INSENSITIVE);
    private static final Pattern DEFAULT_CHARSET = Pattern.compile("\\s*DEFAULT CHARSET=\\w+\\s*(COLLATE=\\w+)?", Pattern.CASE_INSENSITIVE);
    private static final Pattern UNSIGNED = Pattern.compile("\\bunsigned\\b", Pattern.CASE_INSENSITIVE);

    public static void importDump(SQLiteDatabase database, Path dumpFile) throws Exception {
        List<String> statements = parseDump(dumpFile);

        Connection conn = database.getConnection();
        conn.setAutoCommit(false);

        try (Statement stmt = conn.createStatement()) {
            for (String sql : statements) {
                stmt.execute(sql);
            }
            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    private static List<String> parseDump(Path dumpFile) throws IOException {
        List<String> statements = new ArrayList<>();
        StringBuilder current = new StringBuilder();

        try (BufferedReader reader = Files.newBufferedReader(dumpFile)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();

                // Skip comments
                if (trimmed.startsWith("--") || trimmed.isEmpty())
                    continue;

                // Skip MySQL-specific SET statements and conditional comments
                if (MYSQL_COMMENT.matcher(trimmed).matches())
                    continue;
                // Remove inline MySQL conditional comments
                trimmed = MYSQL_COMMENT.matcher(trimmed).replaceAll("");
                if (trimmed.isEmpty())
                    continue;

                // Skip LOCK/UNLOCK TABLES
                if (trimmed.startsWith("LOCK TABLES") || trimmed.startsWith("UNLOCK TABLES"))
                    continue;

                // Skip SET statements (SET NAMES, SET character_set_client, etc.)
                if (trimmed.startsWith("SET "))
                    continue;

                // Skip DROP TABLE (we'll use IF NOT EXISTS on CREATE)
                if (trimmed.startsWith("DROP TABLE"))
                    continue;

                current.append(trimmed).append(" ");

                if (trimmed.endsWith(";")) {
                    String sql = current.toString().trim();
                    sql = convertToSQLite(sql);

                    if (sql != null && !sql.isEmpty())
                        statements.add(sql);

                    current.setLength(0);
                }
            }
        }

        return statements;
    }

    private static String convertToSQLite(String sql) {
        if (sql == null || sql.isEmpty())
            return null;

        // Convert CREATE TABLE
        if (sql.toUpperCase().startsWith("CREATE TABLE")) {
            sql = sql.replace("CREATE TABLE", "CREATE TABLE IF NOT EXISTS");

            // Remove ENGINE=... at end (including AUTO_INCREMENT=N on table)
            sql = ENGINE_CLAUSE.matcher(sql).replaceAll(");");

            // Remove DEFAULT CHARSET=...
            sql = DEFAULT_CHARSET.matcher(sql).replaceAll("");

            // Remove int sizes: bigint(20) -> bigint, int(11) -> int, tinyint(1) -> tinyint
            sql = INT_SIZE.matcher(sql).replaceAll("$1");

            // Remove UNSIGNED
            sql = UNSIGNED.matcher(sql).replaceAll("");

            // Remove CHARACTER SET / COLLATE on varchar columns
            sql = VARCHAR_CHARSET.matcher(sql).replaceAll("$1");

            // Map MySQL types to SQLite types
            sql = sql.replaceAll("(?i)\\bbigint\\b", "INTEGER");
            sql = sql.replaceAll("(?i)\\btinyint\\b", "INTEGER");
            sql = sql.replaceAll("(?i)\\bsmallint\\b", "INTEGER");
            sql = sql.replaceAll("(?i)\\bmediumint\\b", "INTEGER");
            sql = sql.replaceAll("(?i)\\bint\\b(?!e)", "INTEGER"); // avoid matching "INTO", "INTEGER"
            sql = sql.replaceAll("(?i)\\bREAL\\(\\d+,\\d+\\)", "REAL");
            sql = sql.replaceAll("(?i)\\bdouble\\b", "REAL");
            sql = sql.replaceAll("(?i)\\bfloat\\b", "REAL");
            sql = sql.replaceAll("(?i)\\bdecimal\\(\\d+,\\d+\\)", "REAL");
            sql = sql.replaceAll("(?i)\\bdatetime\\b", "TEXT");
            sql = sql.replaceAll("(?i)\\btimestamp\\b", "TEXT");
            sql = sql.replaceAll("(?i)\\bvarchar\\(\\d+\\)", "TEXT");
            sql = sql.replaceAll("(?i)\\blongtext\\b", "TEXT");
            sql = sql.replaceAll("(?i)\\bmediumtext\\b", "TEXT");
            sql = sql.replaceAll("(?i)\\bbit\\(1\\)", "INTEGER");
            sql = sql.replaceAll("(?i)\\benum\\([^)]+\\)", "TEXT");

            // Handle AUTO_INCREMENT columns: convert to INTEGER PRIMARY KEY AUTOINCREMENT
            // and remove the separate PRIMARY KEY constraint
            boolean hasAutoIncrement = sql.contains("AUTO_INCREMENT");
            if (hasAutoIncrement) {
                // Replace `colname` INTEGER NOT NULL AUTO_INCREMENT -> `colname` INTEGER PRIMARY KEY AUTOINCREMENT
                sql = sql.replaceAll("(?i)(`\\w+`)\\s+INTEGER\\s+NOT NULL\\s+AUTO_INCREMENT",
                    "$1 INTEGER PRIMARY KEY AUTOINCREMENT");
                // Remove the separate PRIMARY KEY constraint since it's now inline
                sql = sql.replaceAll(",\\s*PRIMARY KEY\\s+\\([^)]+\\)", "");
            }

            // Remove KEY declarations (SQLite doesn't support inline KEY in CREATE TABLE)
            // but keep PRIMARY KEY and UNIQUE KEY
            sql = sql.replaceAll(",\\s*KEY\\s+`[^`]+`\\s+\\([^)]+\\)", "");

            // Convert UNIQUE KEY to UNIQUE
            sql = sql.replaceAll("(?i)UNIQUE KEY\\s+`[^`]+`\\s+", "UNIQUE ");

            return sql;
        }

        // INSERT statements pass through (MySQL INSERT syntax is compatible with SQLite)
        if (sql.toUpperCase().startsWith("INSERT INTO"))
            return sql;

        // Skip anything else
        return null;
    }
}
