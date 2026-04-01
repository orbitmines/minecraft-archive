package com.orbitmines.archive.minecraft._2019.utils.database.model.mysql;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumnInstance;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.operators.mysql.Order;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.operators.mysql.Where;
import com.orbitmines.archive.minecraft._2019.utils.database.model.ModelColumn;

import java.util.Arrays;
import java.util.Collection;

public interface MySQLModelColumn extends ModelColumn<MySQLColumnInstance> {

    MySQLColumnInstance getColumn();

    default <T> MySQLModelSelector is(Where operator, T value) {
        return (queryBuilder) -> queryBuilder.where(operator, getColumn(), value);
    }

    default <T> MySQLModelSelector is(T value) {
        return (queryBuilder) -> queryBuilder.where(getColumn(), value);
    }

    default MySQLModelSelector is(boolean b) {
        return (queryBuilder) -> queryBuilder.where(getColumn(), b ? "1" : "0");
    }

    default MySQLModelSelector is(Collection collection) {
        return (queryBuilder) -> queryBuilder.where(Where.IN, getColumn(), collection);
    }

    default MySQLModelSelector ordered(Order order) {
        return (queryBuilder) -> queryBuilder.order(getColumn(), order);
    }

    static MySQLColumnInstance[] toColumns(MySQLModelColumn... columns) {
        return Arrays.stream(columns).map(MySQLModelColumn::getColumn).toArray(MySQLColumnInstance[]::new);
    }
}
