package com.orbitmines.archive.minecraft._2019.utils.database.lib.builder;

/*
    Created By Robin Egberts On 2/22/2019
    Copyrighted By OrbitMines ©2019
*/


import com.orbitmines.archive.minecraft._2019.utils.database.lib.Column;

public interface QuerySetBuilder<QSB extends QuerySetBuilder, C extends Column> {

    String getSet();

    QSB set(C column, Object value);

    default boolean hasSet() {
        return !getSet().isEmpty();
    }
}
