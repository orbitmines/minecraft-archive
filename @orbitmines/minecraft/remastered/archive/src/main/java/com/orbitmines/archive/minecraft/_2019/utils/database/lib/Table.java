package com.orbitmines.archive.minecraft._2019.utils.database.lib;

import com.orbitmines.archive.minecraft._2019.utils.database.Database;
import com.orbitmines.archive.minecraft._2019.utils.database.DatabaseManager;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/*
    Created By Robin Egberts On 12/18/2018
    Copyrighted By OrbitMines ©2018
*/

public abstract class Table implements From {

    @Getter private final String name;

    @Getter private final Column[] columns;
    @Getter private final List<Constraint> constraints;
    @Getter private final List<Index> indexes;

    public Table(String name, Column... columns) {
        this.name = name;
        this.columns = columns;
        this.constraints = new ArrayList<>();
        this.indexes = new ArrayList<>();

        for (Column column : columns) {
            if (column.hasIndex())
                addIndex("index_" + column.getName(), column);
        }
    }

    /* SETTERS */
    protected final void addConstraint(Constraint constraint) {
        this.constraints.add(constraint);
    }

    protected final void addIndex(String name, Column... columns) {
        this.indexes.add(new Index(name, columns));
    }

    /* OVERRIDABLE */
    @Override
    public final boolean containsColumn(Selectable column) {
        if (!(column instanceof Column))
            return true;

        for (Column c : columns) {
            if (c.equals(column))
                return true;
        }

        return false;
    }

    @Override
    public final String toString() {
        return name;
    }

    /* STATIC - GETTER */
    public static Table getTable(Column column) {
        for (Database d : DatabaseManager.getInstance().getDatabases()) {
            Table t = d.getTable(column);

            if (t != null) {
                return t;
            }
        }
        return null;
    }
}
