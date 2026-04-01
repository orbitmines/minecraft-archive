package com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.mysql;


import com.orbitmines.archive.minecraft._2019.utils.database.lib.Column;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.From;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Selectable;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.QueryBuilder;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.sql.SQLQueryBuilder;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumnInstance;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.operators.mysql.Order;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.operators.mysql.Where;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/*
    Created by robin On 23-2-2019
    Copyrighted By OrbitMines ©2018
*/

public abstract class MySQLBaseQueryBuilder<QB extends MySQLBaseQueryBuilder> extends QueryBuilder<QB, Where> implements SQLQueryBuilder<MySQLColumnInstance, QB, Where, Order> {

    @Getter protected String distinct;
    @Getter protected String groupBy;
    @Getter protected String having;
    @Getter protected String orderBy;
    @Getter protected String limit;

    public MySQLBaseQueryBuilder(From from) {
        super(from);

        this.distinct = "";
        this.groupBy = "";
        this.having = "";
        this.orderBy = "";
        this.limit = "";
    }

    protected abstract QB getInstance();

    @Override
    public QB where(Where where, Selectable column, Object value) {
        return appendWhere(where, column, value, true);
    }

    @Override
    public QB where(Selectable column, Object value) {
        return where(value != null ? Where.EQUALS : Where.IS, column, value);
    }

    public QB or(Where where, Selectable column, Object value) {
        return appendWhere(where, column, value, false);
    }

    public QB or(Selectable column, Object value) {
        return or(value != null ? Where.EQUALS : Where.IS, column, value);
    }

    private QB appendWhere(Where where, Selectable column, Object value, boolean and) {
        if (hasWhere())
            this.where += and ? " AND " : " OR ";
        else
            this.where += " WHERE ";

        String valueString;
        if (value != null)
            if (where == Where.IN && value instanceof Collection)
                valueString = "('" + StringUtils.join(stringifyCollection((Collection) value), "', '") + "')";
            else
                valueString = valueAsString(value);
        else
            valueString = "NULL";

        this.where += column.toString() + where.getOperator() + valueString;

        return getInstance();
    }

    //TODO: This is a temporary solution
    private final List<String> whitelist = Arrays.asList("NOW()", "CURDATE()");
    private String valueAsString(Object value) {
        if (!(value instanceof String))
            return "'" + value.toString() + "'";

        String val = (String) value;

        return whitelist.contains(val) ? val : "'" + val + "'";
    }

    private Collection<String> stringifyCollection(Collection<?> collection) {
        return collection.stream().map(Object::toString).collect(Collectors.toList());
    }

    @Override
    public QB distinct() {
        this.distinct = " DISTINCT";
        return getInstance();
    }

    @Override
    public QB group(Column column) {
        if (isGrouping())
            this.groupBy += ", ";
        else
            this.groupBy += " GROUP BY ";

        this.groupBy += column.toString();

        return getInstance();
    }

    @Override
    public QB having(Where where, Selectable column, Object value) {
        return appendHaving(where, column, value, true);
    }

    @Override
    public QB having(Selectable column, Object value) {
        return having(value != null ? Where.EQUALS : Where.IS, column, value);
    }

    public QB orHaving(Where where, Selectable column, Object value) {
        return appendHaving(where, column, value, false);
    }

    public QB orHaving(Selectable column, Object value) {
        return orHaving(value != null ? Where.EQUALS : Where.IS, column, value);
    }

    private QB appendHaving(Where where, Selectable column, Object value, boolean and) {
        if (isHaving())
            this.having += and ? " AND " : " OR ";
        else
            this.having += " HAVING ";

        this.having += column.toString() + where.getOperator() + (value == null ? "NULL" : "'" + value.toString() + "'");

        return getInstance();
    }

    @Override
    public QB order(MySQLColumnInstance column, Order operator) {
        if (isOrdering())
            this.orderBy += ", ";
        else
            this.orderBy += " ORDER BY ";

        this.orderBy += column.toString() + " " + operator.getOperator();

        return getInstance();
    }

    @Override
    public QB order(MySQLColumnInstance column) {
        return order(column, Order.DESC);
    }

    @Override
    public QB limit(int limit) {
        this.limit = " LIMIT " + limit;
        return getInstance();
    }
}
