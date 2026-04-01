package com.orbitmines.archive.minecraft._2019.utils.database;


import com.orbitmines.archive.minecraft._2019.libs.Environment;
import com.orbitmines.archive.minecraft._2019.utils.database.exceptions.DatabaseConnectionException;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.*;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.mysql.MySQLQueryBuilder;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.mysql.MySQLQuerySetBuilder;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.mysql.MySQLQueryValueBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/*
    Created by robin On 23-2-2019
    Copyrighted By OrbitMines ©2018
*/
public class MySQLDatabase implements Database<MySQLQueryBuilder, MySQLQuerySetBuilder, MySQLQueryValueBuilder> {

    private List<Table> ALL;

    private final String hostName;
    private final int port;
    private final String databaseName;
    private final String userName;
    private final String password;
    private final String options;

    private Connection connection;

    public MySQLDatabase(String hostName, int port, String databaseName, String userName, String password) {
        this(hostName, port, databaseName, userName, password, "");
    }

    public MySQLDatabase(String hostName, int port, String databaseName, String userName, String password, String options) {
        this.hostName = hostName;
        this.port = port;
        this.databaseName = databaseName;
        this.userName = userName;
        this.password = password;
        this.options = options;

        this.ALL = new ArrayList<>();
    }

    @Override
    public void openConnection() throws DatabaseConnectionException {
        String url = String.format("jdbc:mysql://%s:%d/%s%s", hostName, port, databaseName, options);

        try {
            this.connection = DriverManager.getConnection(url, userName, password);
        } catch (Exception ex) {
            throw new DatabaseConnectionException(url, ex);
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

            /* Create table if it does not exist */
            StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS `").append(table.toString()).append("` ("); //CREATE TABLE IF NOT EXISTS `Naam` (column1 tinyint, column2 int);

            for (int i = 0; i < table.getColumns().length; i++) {
                if (i != 0)
                    query.append(", ");

                Column column = table.getColumns()[i];
                query.append(column.toQuery());
            }

            for (Constraint constraint : table.getConstraints())
                query.append(", ").append(constraint.toString());

            query.append(");");

            this.executeQuery(query.toString());

            for (Index index : table.getIndexes()) {
                StringBuilder indexQuery = new StringBuilder();

                // Only create index if it does not exist
                if (hasValues("SELECT 1 FROM INFORMATION_SCHEMA.STATISTICS WHERE TABLE_SCHEMA='" + this.databaseName + "' AND TABLE_NAME='" + table.getName() + "' AND INDEX_NAME='" + index.getName() + "';"))
                    continue;

                // Index
                indexQuery.append("CREATE INDEX ").append("`").append(index.getName()).append("`").append(" ON ").append("`").append(table.toString()).append("`").append(" (");

                for (Column c : index.getColumns()) {
                    indexQuery.append("`").append(c.getName()).append("`").append(", ");
                }

                indexQuery = new StringBuilder(indexQuery.substring(0, indexQuery.length() - 2)).append(");");

                this.executeQuery(indexQuery.toString());
            }
        }
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
        return !getEntry(queryBuilder, columns).isEmpty();
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
                System.out.println(query);

            ResultSet rs = connection.prepareStatement(query).executeQuery();

            while (rs.next()) {
                value = rs.getObject(Selectable.getName(s));
            }

            rs.close();

            if (Environment.get() == Environment.development) {
                long duration = System.nanoTime() - start;
                System.out.println("query executed in " + String.format("%.1f", ((double) duration / 1000000D)) + "ms");
            }
        } catch (Exception ex) {
            System.out.println(query);
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
                System.out.println(query);

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
                System.out.println("query executed in " + String.format("%.1f", ((double) duration / 1000000D)) + "ms");
            }
        } catch (Exception ex) {
            System.out.println(query);
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
                System.out.println(query);

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
                System.out.println("query executed in " + String.format("%.1f", ((double) duration / 1000000D)) + "ms");
            }
        } catch (Exception ex) {
            System.out.println(query);
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
        return DatabaseType.MYSQL;
    }

    //executors
    private void executeQuery(String query) {
        long start = System.nanoTime();

        try {
            checkConnection();

            if (Environment.get() == Environment.development)
                System.out.println(query);

            PreparedStatement ps = connection.prepareStatement(query);
            ps.execute();
            ps.close();

            if (Environment.get() == Environment.development) {
                long duration = System.nanoTime() - start;
                System.out.println("query executed in " + String.format("%.1f", ((double) duration / 1000000D)) + "ms");
            }
        } catch (Exception ex) {
            System.out.println(query);
            ex.printStackTrace();
        }
    }

    private void executeUpdate(String query) {
        long start = System.nanoTime();

        try {
            checkConnection();

            if (Environment.get() == Environment.development)
                System.out.println(query);

            PreparedStatement ps = connection.prepareStatement(query);
            ps.executeUpdate();
            ps.close();

            if (Environment.get() == Environment.development) {
                long duration = System.nanoTime() - start;
                System.out.println("query executed in " + String.format("%.1f", ((double) duration / 1000000D)) + "ms");
            }
        } catch (Exception ex) {
            System.out.println(query);
            ex.printStackTrace();
        }
    }

    private boolean hasValues(String query) {
        long start = System.nanoTime();

        try {
            boolean hasValues = false;

            checkConnection();

            if (Environment.get() == Environment.development)
                System.out.println(query);

            ResultSet rs = connection.prepareStatement(query).executeQuery();

            while (rs.next()) {
                hasValues = true;
            }

            rs.close();

            if (Environment.get() == Environment.development) {
                long duration = System.nanoTime() - start;
                System.out.println("query executed in " + String.format("%.1f", ((double) duration / 1000000D)) + "ms");
            }

            return hasValues;
        } catch (Exception ex) {
            System.out.println(query);
            ex.printStackTrace();
            return false;
        }
    }
}
