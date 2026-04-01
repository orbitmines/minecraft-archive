package com.orbitmines.archive.minecraft._2019.utils.database;

import com.orbitmines.archive.minecraft._2019.utils.database.exceptions.DatabaseConnectionException;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Column;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Selectable;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Table;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.QueryBuilder;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.QuerySetBuilder;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.QueryValueBuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/*
    Created By Robin Egberts On 2/22/2019
    Copyrighted By OrbitMines ©2019
*/
public interface Database<B extends QueryBuilder, S extends QueryBuilder & QuerySetBuilder, V extends QueryBuilder & QueryValueBuilder> {

    void openConnection() throws DatabaseConnectionException;

    void checkConnection() throws Exception;

    void setupTables();

    void insert(V queryBuilder);

    boolean contains(B queryBuilder, Selectable... columns);

    void update(S queryBuilder);

    Object get(B queryBuilder, Selectable column);

    Object getOrDefault(B queryBuilder, Selectable column, Object defaultValue);

    LinkedHashMap<Selectable, Object> getEntry(B queryBuilder, Selectable... columns);

    ArrayList<LinkedHashMap<Selectable, Object>> getEntries(B queryBuilder, Selectable... columns);

    void delete(B queryBuilder);

    DatabaseType getType();

    void registerTable(Table table);

    default void registerTables(Table... tables) {
        for (Table table : tables) {
            registerTable(table);
        }
    }

    Table getTable(Column c);
}
