package com.orbitmines.archive.minecraft._2019.utils.database.model;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */


import com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.QueryBuilder;

@FunctionalInterface
public interface ModelSelector<QB extends QueryBuilder> {

    void applyToQueryBuilder(QB queryBuilder);

}
