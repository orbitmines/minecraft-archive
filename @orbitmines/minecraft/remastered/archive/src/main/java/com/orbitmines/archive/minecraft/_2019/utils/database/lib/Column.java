package com.orbitmines.archive.minecraft._2019.utils.database.lib;

/*
    Created By Robin Egberts On 2/27/2019
    Copyrighted By OrbitMines ©2019
*/

import lombok.Getter;

//TODO: IMPLEMENT THE REMAINING FUNCTIONS

public abstract class Column<T, C extends Column> implements Selectable {

    @Getter
    private final String name;

    @Getter
    private final Type type;

    @Getter
    private final int[] args;

    @Getter
    private boolean notNull;

    @Getter
    private T value;

    @Getter
    private boolean valueIsStatement;

    @Getter
    private boolean index;

    @Getter
    private boolean autoIncrement;

    public Column(String name, Type type, int... args) {
        this.name = name;
        this.type = type;

        this.notNull = false;
        this.args = (args == null || args.length == 0) ? type.defaultArgs : args;
        this.value = null;
        this.valueIsStatement = false;
    }

    public C defaultValue(T value) {
        this.value = value;
        this.valueIsStatement = false;

        return getInstance();
    }

    public C defaultStatement(T value) {
        this.value = value;
        this.valueIsStatement = true;

        return getInstance();
    }

    public C notNull() {
        this.notNull = true;

        return getInstance();
    }

    public C indexed() {
        this.index = true;

        return getInstance();
    }

    public C autoIncrement() {
        this.autoIncrement = true;

        return getInstance();
    }

    public boolean hasIndex() {
        return index;
    }

    /* toString() METHODS */
    @Override
    public abstract String toString();

    public abstract String toQuery();

    /* equals() method */
    public final boolean equals(Column column) {
        if (column.type != type)
            return false;

        if (column.notNull != notNull)
            return false;

        if (column.args.length == args.length) {
            for (int i = 0; i < args.length; i++) {
                if (args[i] != column.args[i])
                    return false;
            }
        } else {
            return false;
        }

        return true;
    }

    @Override
    public String getQuery() {
        return name;
    }

    /* SUB-ENUM */
    public enum Type {

        /* Numeric */
        INT,
        TINYINT,
        SMALLINT,
        MEDIUMINT,
        BIGINT,
        FLOAT,
        DOUBLE,
        DECIMAL,
        BIT,

        /* Date and Time */
        DATE,/* YYYY-MM-DD */
        DATETIME,/* YYYY-MM-DD HH:MM:SS */
        TIMESTAMP,
        TIME,/* HH:MM:SS */
        YEAR,

        /* String */
        CHAR,
        VARCHAR(255),
        TEXT,
        BLOB,
        TINYTEXT,
        TINYBLOB,
        MEDIUMTEXT,
        MEDIUMBLOB,
        LONGTEXT,
        LONGBLOB,
        ENUM;

        @Getter
        private final int[] defaultArgs;

        Type(int... defaultArgs) {
            this.defaultArgs = defaultArgs;
        }

        @Override
        public String toString() {
            return toString((int[]) null);
        }

        public String toString(int... args) {
            if (args == null || args.length == 0)
                return super.toString();

            StringBuilder stringBuilder = new StringBuilder(super.toString());
            stringBuilder.append("(");

            for (int i = 0; i < args.length; i++) {
                if (i != 0)
                    stringBuilder.append(",");

                stringBuilder.append(args[i]);
            }

            stringBuilder.append(")");

            return stringBuilder.toString();
        }
    }

    protected abstract C getInstance();
}
