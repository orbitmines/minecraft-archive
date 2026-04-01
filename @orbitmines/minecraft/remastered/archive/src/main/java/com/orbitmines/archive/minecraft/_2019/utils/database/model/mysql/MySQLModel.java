package com.orbitmines.archive.minecraft._2019.utils.database.model.mysql;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.utils.database.SQLiteDatabase;
import com.orbitmines.archive.minecraft._2019.utils.database.exceptions.DatabaseModelReloadException;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.mysql.MySQLBaseQueryBuilder;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.mysql.MySQLQueryBuilder;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.mysql.MySQLQuerySetBuilder;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.mysql.MySQLQueryValueBuilder;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLTable;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.operators.mysql.Order;
import com.orbitmines.archive.minecraft._2019.utils.database.model.DatabaseModel;
import com.orbitmines.archive.minecraft._2019.utils.database.model.ModelSelector;

import java.util.List;

public abstract class MySQLModel<Model extends MySQLModel, C extends MySQLModelColumn> extends DatabaseModel<SQLiteDatabase, MySQLTable, MySQLQueryBuilder, MySQLQuerySetBuilder, MySQLQueryValueBuilder, Model, C> {

    public MySQLModel() {

    }

    @Override
    protected MySQLQueryBuilder getQueryBuilder(MySQLTable table) {
        return new MySQLQueryBuilder(table);
    }

    @Override
    protected MySQLQuerySetBuilder getQuerySetBuilder(MySQLTable table) {
        return new MySQLQuerySetBuilder(table);
    }

    @Override
    protected MySQLQueryValueBuilder getQueryValueBuilder(MySQLTable table) {
        return new MySQLQueryValueBuilder(table);
    }

    public void reloadAsLast(MySQLModelSelector... selectors) {
        SQLiteDatabase database = getDatabase();
        MySQLTable table = getTable();
        MySQLQueryBuilder queryBuilder = getQueryBuilder(table);

        queryBuilder.order(getSortedIdentifier().getColumn(), Order.DESC);
        queryBuilder.limit(1);

        for (MySQLModelSelector selector : selectors) {
            selector.applyToQueryBuilder(queryBuilder);
        }

        this.entry = database.getEntry(queryBuilder, table.getColumns());

        if (this.entry == null)
            throw new DatabaseModelReloadException();

        load();
    }

    protected MySQLModelSelector[] localIdentifiers(C... columns) {
        MySQLModelSelector[] selectors = new MySQLModelSelector[columns.length];

        for (int i = 0; i < columns.length; i++) {
            selectors[i] = localIdentifier(columns[i]);
        }

        return selectors;
    }

    protected MySQLModelSelector localIdentifier(C column) {
        return column.is(stringifyValue(column));
    }

    public static ModelSelector<MySQLBaseQueryBuilder> limitedTo(int limit) {
        return (queryBuilder) -> queryBuilder.limit(limit);
    }

    public static <Model extends MySQLModel<Model, C>, C extends MySQLModelColumn> Model getLast(Class<Model> clazz, MySQLModelSelector... selectors) {
        return getSorted(clazz, Order.DESC, selectors);
    }

    public static <Model extends MySQLModel<Model, C>, C extends MySQLModelColumn> Model getFirst(Class<Model> clazz, MySQLModelSelector... selectors) {
        return getSorted(clazz, Order.ASC, selectors);
    }

    private static <Model extends MySQLModel<Model, C>, C extends MySQLModelColumn> Model getSorted(Class<Model> clazz, Order order, MySQLModelSelector... selectors) {
        Model dummy = dummy(clazz);
        SQLiteDatabase database = dummy.getDatabase();
        MySQLTable table = dummy.getTable();
        MySQLQueryBuilder queryBuilder = dummy.getQueryBuilder(table);

        queryBuilder.order(dummy.getSortedIdentifier().getColumn(), order);
        queryBuilder.limit(1);

        for (MySQLModelSelector selector : selectors) {
            selector.applyToQueryBuilder(queryBuilder);
        }

        return queryEntry(clazz, database, queryBuilder, table.getColumns());
    }

    public static <Model extends MySQLModel<Model, C>, C extends MySQLModelColumn> List<Model> getLast(Class<Model> clazz, int limit, MySQLModelSelector... selectors) {
        return getSorted(clazz, Order.DESC, limit, selectors);
    }

    public static <Model extends MySQLModel<Model, C>, C extends MySQLModelColumn> List<Model> getFirst(Class<Model> clazz, int limit, MySQLModelSelector... selectors) {
        return getSorted(clazz, Order.ASC, limit, selectors);
    }

    private static <Model extends MySQLModel<Model, C>, C extends MySQLModelColumn> List<Model> getSorted(Class<Model> clazz, Order order, int limit, MySQLModelSelector... selectors) {
        Model dummy = dummy(clazz);
        SQLiteDatabase database = dummy.getDatabase();
        MySQLTable table = dummy.getTable();
        MySQLQueryBuilder queryBuilder = dummy.getQueryBuilder(table);

        queryBuilder.order(dummy.getSortedIdentifier().getColumn(), order);
        queryBuilder.limit(limit);

        for (MySQLModelSelector selector : selectors) {
            selector.applyToQueryBuilder(queryBuilder);
        }

        return queryEntries(clazz, database, queryBuilder, table.getColumns());
    }
}
