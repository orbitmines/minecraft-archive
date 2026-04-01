package com.orbitmines.archive.minecraft._2019.utils.database.lib.froms;

/*
    Created By Robin Egberts On 4/7/2019
    Copyrighted By OrbitMines ©2019
*/

import com.orbitmines.archive.minecraft._2019.utils.database.lib.Column;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Table;

public abstract class MySQLColumnInstance<T, C extends Column> extends Column<T, C> {

    MySQLColumnInstance(String name, Type type, int... args) {
        super(name, type, args);
    }

    @Override
    public String toString() {
        return String.format("%s`%s`", Table.getTable(this) == null ? "" : "`" + Table.getTable(this) + "`.", getName());
    }

    @Override
    public String toQuery() {
        StringBuilder sb = new StringBuilder().
                append("`").append(getName()).append("`").append(" ").append(getType().toString(getArgs())).append(" ");

        if (isNotNull())
            sb.append("NOT NULL ");

        if (isAutoIncrement())
            sb.append("AUTO_INCREMENT ");

        if (getValue() != null) {
            sb.append("DEFAULT ");

            if (!this.isValueIsStatement())
                sb.append("'");

            sb.append(getValue().toString());

            if (!this.isValueIsStatement())
                sb.append("'");

            sb.append(" ");
        }

        return sb.substring(0, sb.length() - 1);
    }
}
