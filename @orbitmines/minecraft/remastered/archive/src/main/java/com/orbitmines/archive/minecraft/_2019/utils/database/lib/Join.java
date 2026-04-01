package com.orbitmines.archive.minecraft._2019.utils.database.lib;

/*
    Created By Robin Egberts On 2/27/2019
    Copyrighted By OrbitMines ©2019
*/

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public abstract class Join<T extends Join, C extends Column> implements From {

    @Getter
    private List<T> children;

    @Getter
    private Type type;

    @Getter
    private C leftColumn;

    @Getter
    private C rightColumn;

    public Join(Type type, C leftColumn, C rightColumn) {
        this.children = new ArrayList<>();

        this.type = type;

        this.leftColumn = leftColumn;
        this.rightColumn = rightColumn;

        if (getLeftTable() == null)
            throw new IllegalStateException("The left table doesn't exist!");

        if (getRightTable() == null)
            throw new IllegalStateException("The right table doesn't exist!");
    }

    /* OVERRIDABLE */
    @Override
    public abstract String toString();

    @Override
    public final boolean containsColumn(Selectable c) {
        if (!(c instanceof Column))
            return false;

        if (getLeftTable().containsColumn(c))
            return true;

        if (getRightTable().containsColumn(c))
            return true;

        for (Join join : getChildren()) {
            if (join.containsColumn(c))
                return true;
        }

        return false;
    }

    public final void addChild(T join) {
        children.add(join);
    }

    /* GETTERS */
    protected final Table getLeftTable() {
        return Table.getTable(leftColumn);
    }

    protected final Table getRightTable() {
        return Table.getTable(rightColumn);
    }

    protected final Table getCommon(Join join) {
        if (join.getLeftTable().equals(getLeftTable()))
            return join.getLeftTable();

        if (join.getLeftTable().equals(getRightTable()))
            return join.getLeftTable();

        if (join.getRightTable().equals(getRightTable()))
            return join.getRightTable();

        if (join.getRightTable().equals(getLeftTable()))
            return join.getRightTable();

        throw new IllegalStateException("One of the joins doesn't have anything in common with the parent join!");
    }

    protected final Table getUncommon(Table table) {
        return table.equals(getLeftTable()) ? getRightTable() : getLeftTable();
    }

    /* SUB-ENUM */
    public enum Type {

        INNER_JOIN("INNER JOIN"),
        LEFT_JOIN("LEFT JOIN"),
        LEFT_OUTER_JOIN("LEFT OUTER JOIN"),
        RIGHT_JOIN("RIGHT JOIN"),
        RIGHT_OUTER_JOIN("RIGHT OUTER JOIN"),
        CROSS_JOIN("CROSS JOIN"),
        FULL_OUTER_JOIN("FULL OUTER JOIN");

        private final String query;

        Type(String query) {
            this.query = query;
        }

        @Override
        public final String toString() {
            return query;
        }
    }
}
