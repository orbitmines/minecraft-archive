package com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.sql;

import com.orbitmines.archive.minecraft._2019.utils.database.lib.Column;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Selectable;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.QueryBuilder;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.operators.OrderOperator;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.operators.WhereOperator;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */
public interface SQLQueryBuilder<C extends Column, QB extends QueryBuilder, Where extends WhereOperator, Order extends OrderOperator> {

    /*

        DISTINCT

     */

    String getDistinct();

    QB distinct();

    default boolean isDistinct() {
        return !getDistinct().isEmpty();
    }

    /*

        GROUP BY

     */

    String getGroupBy();

    QB group(Column column);

    default boolean isGrouping() {
        return !getGroupBy().isEmpty();
    }

    /*

        HAVING

     */

    String getHaving();

    QB having(Where where, Selectable column, Object value);

    QB having(Selectable column, Object value);

    default boolean isHaving() {
        return !getHaving().isEmpty();
    }

    /*

        ORDER BY

     */

    String getOrderBy();

    QB order(C column, Order operator);

    QB order(C column);

    default boolean isOrdering() {
        return !getOrderBy().isEmpty();
    }

    /*

        LIMIT

     */

    String getLimit();

    QB limit(int limit);

    default boolean hasLimit() {
        return !getLimit().isEmpty();
    }
}
