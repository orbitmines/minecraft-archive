package com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.mysql;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.utils.database.lib.From;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Join;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Selectable;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumn;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLJoin;

public class MySQLQueryBuilder extends MySQLBaseQueryBuilder<MySQLQueryBuilder> {

    public MySQLQueryBuilder(From from) {
        super(from);
    }

    public MySQLQueryBuilder(Join.Type type, MySQLColumn left, MySQLColumn right) {
        super(new MySQLJoin(type, left, right));
    }

    public MySQLQueryBuilder(MySQLColumn left, MySQLColumn right) {
        this(Join.Type.INNER_JOIN, left, right);
    }

    @Override
    protected MySQLQueryBuilder getInstance() {
        return this;
    }

    @Override
    public String select(Selectable... columns) {
        if (columns.length == 0)
            throw new IllegalStateException("Cannot execute query with zero columns given");

        StringBuilder sb = new StringBuilder("SELECT ");
        sb.append(this.distinct);

        for (Selectable c : columns) {
            if (getFrom().containsColumn(c))
                sb.append(String.format("%s, ", c.getQuery()));
        }

        return sb.substring(0, sb.length() - 2) + " " + toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("FROM ").append("`").append(getFrom().toString()).append("`");

        sb.append(this.where).
                append(this.groupBy).
                append(this.having).
                append(this.orderBy).
                append(this.limit);

        return sb.append(";").toString();
    }
}
