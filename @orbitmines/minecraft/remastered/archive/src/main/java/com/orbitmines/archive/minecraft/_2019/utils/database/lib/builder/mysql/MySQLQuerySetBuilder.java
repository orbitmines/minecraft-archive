package com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.mysql;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.utils.database.exceptions.DatabaseUpdateException;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.From;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Selectable;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumn;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.QuerySetBuilder;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumnInstance;
import lombok.Getter;

public class MySQLQuerySetBuilder extends MySQLBaseQueryBuilder<MySQLQuerySetBuilder> implements QuerySetBuilder<MySQLQuerySetBuilder, MySQLColumnInstance> {

    @Getter private String set;

    public MySQLQuerySetBuilder(From from) {
        super(from);
        this.set = "";
    }

    @Override
    protected MySQLQuerySetBuilder getInstance() {
        return this;
    }

    @Override
    public MySQLQuerySetBuilder set(MySQLColumnInstance column, Object value) {
        if (hasSet())
            set += ", ";

        set += column.toString() + "=" + (value != null ? "'" + value.toString() + "'" : "NULL");

        return getInstance();
    }

    @Override
    public String toString() {
        if (!hasSet())
            throw new DatabaseUpdateException("No updates specified");

        return "`" + getFrom().toString() + "` SET " + this.set + this.where + ";";
    }

    @Override
    public String select(Selectable... columns) {
        throw new UnsupportedOperationException();
    }
}
