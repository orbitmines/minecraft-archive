package com.orbitmines.archive.minecraft._2019.utils.database;

import com.orbitmines.archive.minecraft._2019.libs.Environment;
import com.orbitmines.archive.minecraft._2019.utils.database.exceptions.DatabaseConnectionException;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.*;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.mysql.MySQLQueryBuilder;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.mysql.MySQLQuerySetBuilder;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.mysql.MySQLQueryValueBuilder;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Logger;

public class SQLiteDatabase implements Database<MySQLQueryBuilder, MySQLQuerySetBuilder, MySQLQueryValueBuilder> {

    private static final Logger logger = Logger.getLogger(SQLiteDatabase.class.getName());

    private List<Table> ALL;

    private final String path;
    private Connection connection;

    public SQLiteDatabase(String path) {
        this.path = path;
        this.ALL = new ArrayList<>();

        File parent = new File(path).getParentFile();
        if (parent != null && !parent.exists())
            parent.mkdirs();
    }

    @Override
    public void openConnection() throws DatabaseConnectionException {
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + path);
            /* Enable foreign keys */
            connection.prepareStatement("PRAGMA foreign_keys = ON;").execute();
        } catch (Exception ex) {
            throw new DatabaseConnectionException("jdbc:sqlite:" + path, ex);
        }
    }

    @Override
    public void checkConnection() throws Exception {
        if (connection == null || connection.isClosed())
            openConnection();
    }

    @Override
    public void setupTables() {
        for (Table table : ALL) {
            StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS `").append(table.toString()).append("` (");

            boolean hasAutoIncrement = false;
            for (int i = 0; i < table.getColumns().length; i++) {
                if (i != 0)
                    query.append(", ");

                Column column = table.getColumns()[i];
                if (column.isAutoIncrement())
                    hasAutoIncrement = true;
                query.append(toSQLiteColumnDef(column));
            }

            for (Constraint constraint : table.getConstraints()) {
                /* SQLite: AUTOINCREMENT requires INTEGER PRIMARY KEY inline,
                   so skip the separate PRIMARY KEY constraint */
                if (hasAutoIncrement && constraint.getType() == Constraint.Type.PRIMARY)
                    continue;
                query.append(", ").append(constraint.toString());
            }

            query.append(");");

            this.executeQuery(query.toString());

            for (Index index : table.getIndexes()) {
                StringBuilder indexQuery = new StringBuilder();
                indexQuery.append("CREATE INDEX IF NOT EXISTS `").append(index.getName()).append("` ON `").append(table.toString()).append("` (");

                for (int i = 0; i < index.getColumns().length; i++) {
                    if (i != 0)
                        indexQuery.append(", ");
                    indexQuery.append("`").append(index.getColumns()[i].getName()).append("`");
                }

                indexQuery.append(");");
                this.executeQuery(indexQuery.toString());
            }
        }
    }

    private String toSQLiteColumnDef(Column column) {
        StringBuilder sb = new StringBuilder();
        sb.append("`").append(column.getName()).append("` ");

        /* Map MySQL types to SQLite types */
        String sqliteType = switch (column.getType()) {
            case INT, TINYINT, SMALLINT, MEDIUMINT, BIGINT, BIT -> "INTEGER";
            case FLOAT, DOUBLE, DECIMAL -> "REAL";
            case BLOB, TINYBLOB, MEDIUMBLOB, LONGBLOB -> "BLOB";
            default -> "TEXT";
        };
        sb.append(sqliteType).append(" ");

        if (column.isNotNull())
            sb.append("NOT NULL ");

        if (column.isAutoIncrement())
            sb.append("PRIMARY KEY AUTOINCREMENT ");

        if (column.getValue() != null) {
            sb.append("DEFAULT ");
            if (!column.isValueIsStatement())
                sb.append("'");
            sb.append(column.getValue().toString());
            if (!column.isValueIsStatement())
                sb.append("'");
            sb.append(" ");
        }

        return sb.substring(0, sb.length() - 1);
    }

    @Override
    public void registerTable(Table table) {
        this.ALL.add(table);
    }

    @Override
    public Table getTable(Column c) {
        for (Table t : ALL) {
            if (t.containsColumn(c))
                return t;
        }
        return null;
    }

    @Override
    public void insert(MySQLQueryValueBuilder query) {
        this.executeQuery(String.format("INSERT INTO %s", query.toString()));
    }

    @Override
    public boolean contains(MySQLQueryBuilder queryBuilder, Selectable... columns) {
        LinkedHashMap<Selectable, Object> entry = getEntry(queryBuilder, columns);
        return entry != null && !entry.isEmpty();
    }

    @Override
    public void update(MySQLQuerySetBuilder queryBuilder) {
        this.executeUpdate(String.format("UPDATE %s", queryBuilder.toString()));
    }

    @Override
    public Object get(MySQLQueryBuilder queryBuilder, Selectable s) {
        long start = System.nanoTime();
        Object value = null;
        String query = queryBuilder.select(s);

        try {
            checkConnection();

            if (Environment.get() == Environment.development)
                logger.info(query);

            ResultSet rs = connection.prepareStatement(query).executeQuery();

            while (rs.next()) {
                value = rs.getObject(Selectable.getName(s));
            }

            rs.close();

            if (Environment.get() == Environment.development) {
                long duration = System.nanoTime() - start;
                logger.info("query executed in " + String.format("%.1f", ((double) duration / 1000000D)) + "ms");
            }
        } catch (Exception ex) {
            logger.info(query);
            ex.printStackTrace();
        }

        return value;
    }

    @Override
    public Object getOrDefault(MySQLQueryBuilder queryBuilder, Selectable column, Object defaultValue) {
        Object value = get(queryBuilder, column);
        return value != null ? value : defaultValue;
    }

    @Override
    public LinkedHashMap<Selectable, Object> getEntry(MySQLQueryBuilder queryBuilder, Selectable... selectables) {
        long start = System.nanoTime();
        LinkedHashMap<Selectable, Object> values = null;
        String query = queryBuilder.select(selectables);

        try {
            checkConnection();

            if (Environment.get() == Environment.development)
                logger.info(query);

            ResultSet rs = connection.prepareStatement(query).executeQuery();

            while (rs.next()) {
                if (values == null)
                    values = new LinkedHashMap<>();

                for (Selectable s : selectables) {
                    values.put(s, rs.getObject(Selectable.getName(s)));
                }
            }

            rs.close();

            if (Environment.get() == Environment.development) {
                long duration = System.nanoTime() - start;
                logger.info("query executed in " + String.format("%.1f", ((double) duration / 1000000D)) + "ms");
            }
        } catch (Exception ex) {
            logger.info(query);
            ex.printStackTrace();
        }

        return values;
    }

    @Override
    public ArrayList<LinkedHashMap<Selectable, Object>> getEntries(MySQLQueryBuilder queryBuilder, Selectable... selectables) {
        long start = System.nanoTime();
        ArrayList<LinkedHashMap<Selectable, Object>> entries = new ArrayList<>();
        String query = queryBuilder.select(selectables);

        try {
            checkConnection();

            if (Environment.get() == Environment.development)
                logger.info(query);

            ResultSet rs = connection.prepareStatement(query).executeQuery();

            while (rs.next()) {
                LinkedHashMap<Selectable, Object> entry = new LinkedHashMap<>();

                for (Selectable s : selectables)
                    entry.put(s, rs.getObject(Selectable.getName(s)));

                entries.add(entry);
            }

            rs.close();

            if (Environment.get() == Environment.development) {
                long duration = System.nanoTime() - start;
                logger.info("query executed in " + String.format("%.1f", ((double) duration / 1000000D)) + "ms");
            }
        } catch (Exception ex) {
            logger.info(query);
            ex.printStackTrace();
        }

        return entries;
    }

    @Override
    public void delete(MySQLQueryBuilder queryBuilder) {
        this.executeUpdate(String.format("DELETE %s", queryBuilder.toString()));
    }

    @Override
    public DatabaseType getType() {
        return DatabaseType.SQLITE;
    }

    public void executeQuery(String query) {
        long start = System.nanoTime();

        try {
            checkConnection();

            if (Environment.get() == Environment.development)
                logger.info(query);

            PreparedStatement ps = connection.prepareStatement(query);
            ps.execute();
            ps.close();

            if (Environment.get() == Environment.development) {
                long duration = System.nanoTime() - start;
                logger.info("query executed in " + String.format("%.1f", ((double) duration / 1000000D)) + "ms");
            }
        } catch (Exception ex) {
            logger.info(query);
            ex.printStackTrace();
        }
    }

    private void executeUpdate(String query) {
        long start = System.nanoTime();

        try {
            checkConnection();

            if (Environment.get() == Environment.development)
                logger.info(query);

            PreparedStatement ps = connection.prepareStatement(query);
            ps.executeUpdate();
            ps.close();

            if (Environment.get() == Environment.development) {
                long duration = System.nanoTime() - start;
                logger.info("query executed in " + String.format("%.1f", ((double) duration / 1000000D)) + "ms");
            }
        } catch (Exception ex) {
            logger.info(query);
            ex.printStackTrace();
        }
    }

    public Connection getConnection() throws Exception {
        checkConnection();
        return connection;
    }
}
