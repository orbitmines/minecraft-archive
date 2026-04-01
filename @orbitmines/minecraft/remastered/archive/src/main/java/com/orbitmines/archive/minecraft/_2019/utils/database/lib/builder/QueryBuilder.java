package com.orbitmines.archive.minecraft._2019.utils.database.lib.builder;

import com.orbitmines.archive.minecraft._2019.utils.database.lib.From;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Selectable;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.operators.WhereOperator;
import lombok.Getter;

/*
    Created By Robin Egberts On 2/22/2019
    Copyrighted By OrbitMines ©2019
*/
public abstract class QueryBuilder<QB extends QueryBuilder, Where extends WhereOperator> {

    @Getter private From from;

    @Getter protected String where;

    public QueryBuilder(From from) {
        this.from = from;
        this.where = "";
    }

    /*
        QueryBuilder Methods
    */
    public abstract QB where(Where where, Selectable column, Object value);

    public abstract QB where(Selectable column, Object value);

    /*
        Select() Method
    */
    public abstract String select(Selectable... columns);

    /*
        toString() Methods
    */
    @Override
    public abstract String toString();

    public boolean hasWhere() {
        return !this.where.isEmpty();
    }
}
