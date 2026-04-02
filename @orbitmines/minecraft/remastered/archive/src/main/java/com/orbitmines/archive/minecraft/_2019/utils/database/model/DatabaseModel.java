package com.orbitmines.archive.minecraft._2019.utils.database.model;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import com.orbitmines.archive.minecraft._2019.utils.EnumUtils;
import com.orbitmines.archive.minecraft._2019.utils.database.exceptions.DatabaseModelReloadException;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Column;
import com.orbitmines.archive.minecraft._2019.utils.database.Database;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Selectable;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Table;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.QueryBuilder;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.QuerySetBuilder;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.QueryValueBuilder;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.*;

public abstract class DatabaseModel<DB extends Database<QB, QSB, QVB>, T extends Table, QB extends QueryBuilder, QSB extends QueryBuilder & QuerySetBuilder, QVB extends QueryBuilder & QueryValueBuilder, Model extends DatabaseModel, C extends ModelColumn> {

    @Getter protected boolean inserted;
    @Getter protected boolean destroyed = false;

    public DatabaseModel() {
    }

    protected LinkedHashMap<Selectable, Object> entry;
    
    protected abstract void load();

    protected abstract DB getDatabase();

    protected abstract QB getQueryBuilder(T table);

    protected abstract QSB getQuerySetBuilder(T table);

    protected abstract QVB getQueryValueBuilder(T table);

    protected abstract T getTable();

    protected abstract ModelSelector[] getUniqueIdentifier();

    /* Sorted identifier for first/last */
    protected abstract C getSortedIdentifier();

    protected abstract String stringifyValue(C column);

    protected abstract C[] getColumns();

    private Object get(C column) {
        return entry.get(column.getColumn());
    }

    protected Boolean getBoolean(C column) {
        Object value = get(column);
        if (value == null) return null;
        if (value instanceof Boolean) return (Boolean) value;
        return ((Number) value).intValue() != 0;
    }

    protected Long getLong(C column) {
        Object value = get(column);
        if (value == null) return null;
        return ((Number) value).longValue();
    }

    protected Integer getInteger(C column) {
        Object value = get(column);
        if (value == null) return null;
        return ((Number) value).intValue();
    }

    protected Double getDouble(C column) {
        Object value = get(column);
        if (value == null) return null;
        return ((Number) value).doubleValue();
    }

    protected UUID getUUID(C column) {
        return UUID.fromString(getString(column));
    }

    protected Date getDate(C column, SimpleDateFormat format) {
        Object value = get(column);
        if (value == null) return null;
        if (value instanceof Date) return (Date) value;
        try {
            return format.parse(value.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected JsonElement getJson(C column) {
        String string = getString(column);

        if (string == null)
            return null;

        return new JsonParser().parse(string);
    }

    protected String getString(C column) {
        return (String) get(column);
    }

    protected <E extends Enum<E>> E getEnum(C column, Class<E> clazz) {
        String string = getString(column);

        if (string == null)
            return null;

        return EnumUtils.valueOf(clazz, string);
    }

    protected String stringify(Long l) {
        return l != null ? l + "" : null;
    }

    protected String stringify(Integer integer) {
        return integer != null ? integer + "" : null;
    }

    protected String stringify(Double d) {
        return d != null ? d + "" : null;
    }

    protected String stringify(Boolean b) {
        if (b == null)
            return null;

        return b ? "1" : "0";
    }

    protected String stringify(UUID uuid) {
        if (uuid == null)
            return null;

        return uuid.toString();
    }

    protected String stringify(Date date, SimpleDateFormat format) {
        if (date == null)
            return null;

        return DateUtils.format(date, format);
    }

    protected String stringify(JsonElement jsonElement) {
        if (jsonElement == null)
            return null;

        return new GsonBuilder().create().toJson(jsonElement);
    }

    protected String stringify(String string) {
        return string;
    }

    protected <E extends Enum<E>> String stringify(E value) {
        if (value == null)
            return null;

        return value.toString();
    }

    public void reload() {
        DB database = getDatabase();
        T table = getTable();
        QB queryBuilder = getQueryBuilder(table);

        for (ModelSelector identifier : getUniqueIdentifier()) {
            identifier.applyToQueryBuilder(queryBuilder);
        }

        this.entry = database.getEntry(queryBuilder);

        if (this.entry == null)
            throw new DatabaseModelReloadException();

        load();
    }

    public void insertOrUpdate(C... columns) {
        if (isInserted())
            update(columns);
        else
            insert();
    }

    public void update(C... columns) {
        DB database = getDatabase();
        T table = getTable();
        QSB queryBuilder = getQuerySetBuilder(table);

        for (C column : columns) {
            queryBuilder.set(column.getColumn(), stringifyValue(column));
        }

        for (ModelSelector identifier : getUniqueIdentifier()) {
            identifier.applyToQueryBuilder(queryBuilder);
        }

        database.update(queryBuilder);
    }

    public void delete() {
        DB database = getDatabase();
        T table = getTable();
        QB queryBuilder = getQueryBuilder(table);

        for (ModelSelector identifier : getUniqueIdentifier()) {
            identifier.applyToQueryBuilder(queryBuilder);
        }

        database.delete(queryBuilder);

        destroyed = true;
    }

    public void insert() {
        DB database = getDatabase();
        T table = getTable();
        QVB queryBuilder = getQueryValueBuilder(table);

        for (C column : getColumns()) {
            queryBuilder.value(column.getColumn(), stringifyValue(column));
        }

        database.insert(queryBuilder);

        this.inserted = true;
    }

    public static <DB extends Database<QB, QSB, QVB>, T extends Table, QB extends QueryBuilder, QSB extends QueryBuilder & QuerySetBuilder, QVB extends QueryBuilder & QueryValueBuilder, Model extends DatabaseModel<DB, T, QB, QSB, QVB, Model, Columns>, Columns extends ModelColumn> ArrayList<Model> getAll(Class<Model> clazz) {
        Model dummy = dummy(clazz);
        DB database = dummy.getDatabase();
        T table = dummy.getTable();
        QB queryBuilder = dummy.getQueryBuilder(table);

        return queryEntries(clazz, database, queryBuilder, table.getColumns());
    }

    public static <DB extends Database<QB, QSB, QVB>, T extends Table, QB extends QueryBuilder, QSB extends QueryBuilder & QuerySetBuilder, QVB extends QueryBuilder & QueryValueBuilder, Model extends DatabaseModel<DB, T, QB, QSB, QVB, Model, Columns>, Columns extends ModelColumn> ArrayList<Model> getAll(Class<Model> clazz, ModelSelector... selectors) {
        Model dummy = dummy(clazz);
        DB database = dummy.getDatabase();
        T table = dummy.getTable();
        QB queryBuilder = dummy.getQueryBuilder(table);

        for (ModelSelector selector : selectors) {
            selector.applyToQueryBuilder(queryBuilder);
        }

        return queryEntries(clazz, database, queryBuilder, table.getColumns());
    }

    public static <DB extends Database<QB, QSB, QVB>, T extends Table, QB extends QueryBuilder, QSB extends QueryBuilder & QuerySetBuilder, QVB extends QueryBuilder & QueryValueBuilder, Model extends DatabaseModel<DB, T, QB, QSB, QVB, Model, Columns>, Columns extends ModelColumn> Model findBy(Class<Model> clazz, ModelSelector... selectors) {
        Model dummy = dummy(clazz);
        DB database = dummy.getDatabase();
        T table = dummy.getTable();
        QB queryBuilder = dummy.getQueryBuilder(table);

        for (ModelSelector selector : selectors) {
            selector.applyToQueryBuilder(queryBuilder);
        }

        return queryEntry(clazz, database, queryBuilder, table.getColumns());
    }

    protected static <DB extends Database<QB, QSB, QVB>, T extends Table, QB extends QueryBuilder, QSB extends QueryBuilder & QuerySetBuilder, QVB extends QueryBuilder & QueryValueBuilder, Model extends DatabaseModel<DB, T, QB, QSB, QVB, Model, Columns>, Columns extends ModelColumn> ArrayList<Model> queryEntries(Class<Model> clazz, DB database, QB queryBuilder, Column[] columns) {
        ArrayList<LinkedHashMap<Selectable, Object>> entries = database.getEntries(queryBuilder, columns);

        ArrayList<Model> models = new ArrayList<>();
        for (LinkedHashMap<Selectable, Object> entry : entries) {
            models.add(newInstance(clazz, entry));
        }

        return models;
    }

    protected static <DB extends Database<QB, QSB, QVB>, T extends Table, QB extends QueryBuilder, QSB extends QueryBuilder & QuerySetBuilder, QVB extends QueryBuilder & QueryValueBuilder, Model extends DatabaseModel<DB, T, QB, QSB, QVB, Model, Columns>, Columns extends ModelColumn> Model queryEntry(Class<Model> clazz, DB database, QB queryBuilder, Column[] columns) {
        LinkedHashMap<Selectable, Object> entry = database.getEntry(queryBuilder, columns);

        if (entry == null)
            return null;

        return newInstance(clazz, entry);
    }

    protected static <Model extends DatabaseModel> Model dummy(Class<Model> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static <Model extends DatabaseModel> Model newInstance(Class<Model> clazz, LinkedHashMap<Selectable, Object> entry) {
        Model model = dummy(clazz);
        model.entry = entry;
        model.load();
        model.inserted = true;

        return model;
    }
}
