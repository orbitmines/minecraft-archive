package com.orbitmines.archive.minecraft._2019.libs.database.models;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.database.mysql.OMMySQLModel;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Column;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.ColumnKey;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumn;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumnInstance;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumnKey;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLTable;
import com.orbitmines.archive.minecraft._2019.utils.database.model.ModelSelector;
import com.orbitmines.archive.minecraft._2019.utils.database.model.mysql.MySQLModelColumn;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.operators.mysql.Where;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class StateEntry extends OMMySQLModel<StateEntry, StateEntry.column> {

    public static MySQLTable TABLE = new MySQLTable("state", MySQLModelColumn.toColumns(column.values()));

    @Getter private Long id;
    @Getter @Setter private String key;
    @Getter @Setter private String value;

    public StateEntry() {
    }

    public StateEntry(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    protected void load() {
        this.id = getLong(column.ID);
        this.key = getString(column.KEY);
        this.value = getString(column.VALUE);
    }

    @Override
    protected MySQLTable getTable() {
        return TABLE;
    }

    @Override
    protected ModelSelector[] getUniqueIdentifier() {
        return localIdentifiers(column.KEY);
    }

    @Override
    protected column getSortedIdentifier() {
        return column.KEY;
    }

    @Override
    protected String stringifyValue(column column) {
        switch (column) {
            case ID:
                return stringify(this.id);
            case KEY:
                return this.key;
            case VALUE:
                return this.value;
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Override
    protected column[] getColumns() {
        return column.values();
    }

    @AllArgsConstructor
    public enum column implements MySQLModelColumn {

        ID(new MySQLColumnKey("id", Column.Type.BIGINT, ColumnKey.Key.PRIMARY).autoIncrement()),
        KEY(new MySQLColumn("key", Column.Type.VARCHAR, 255).notNull().indexed()),
        VALUE(new MySQLColumn("value", Column.Type.TEXT));

        @Getter
        private final MySQLColumnInstance column;
    }

    /* Static helpers */

    public static String get(String key) {
        StateEntry entry = findBy(StateEntry.class, column.KEY.is(key));
        return entry != null ? entry.getValue() : null;
    }

    public static void set(String key, String value) {
        StateEntry entry = findBy(StateEntry.class, column.KEY.is(key));
        if (entry == null) {
            new StateEntry(key, value).insert();
        } else {
            entry.setValue(value);
            entry.update(column.VALUE);
        }
    }

    public static void remove(String key) {
        StateEntry entry = findBy(StateEntry.class, column.KEY.is(key));
        if (entry != null)
            entry.delete();
    }

    public static ArrayList<StateEntry> getAllByPrefix(String prefix) {
        return getAll(StateEntry.class, column.KEY.is(Where.LIKE, prefix + "%"));
    }

    public static void removeAllByPrefix(String prefix) {
        ArrayList<StateEntry> entries = getAllByPrefix(prefix);
        for (StateEntry entry : entries) {
            entry.delete();
        }
    }
}
