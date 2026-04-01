package com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.mysql;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */


import com.orbitmines.archive.minecraft._2019.utils.database.lib.From;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Selectable;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumn;
import com.orbitmines.archive.minecraft._2019.utils.database.exceptions.DatabaseInsertException;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.QueryValueBuilder;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumnInstance;
import lombok.Getter;

public class MySQLQueryValueBuilder extends MySQLBaseQueryBuilder<MySQLQueryValueBuilder> implements QueryValueBuilder<MySQLQueryValueBuilder, MySQLColumnInstance> {

    @Getter private String columnValues;
    @Getter private String values;

    public MySQLQueryValueBuilder(From from) {
        super(from);
        this.columnValues = "";
        this.values = "";
    }

    @Override
    protected MySQLQueryValueBuilder getInstance() {
        return this;
    }

    @Override
    public MySQLQueryValueBuilder value(MySQLColumnInstance column, Object value) {
        if (hasValues()) {
            this.columnValues += ", ";
            this.values += ", ";
        }

        this.columnValues += column.toString();
        this.values += value != null ? "'" + value.toString() + "'" : "NULL";

        return getInstance();
    }

    @Override
    public String toString() {
        if (!hasValues())
            throw new DatabaseInsertException("No values given in INSERT query.");

        return "`" + getFrom().toString() + "` (" + this.columnValues + ") VALUES (" + this.values + ");";
    }

    @Override
    public String select(Selectable... columns) {
        throw new UnsupportedOperationException();
    }
}
