package com.orbitmines.archive.minecraft._2019.utils.database.lib.builder;

import com.orbitmines.archive.minecraft._2019.utils.database.lib.Column;

/*
    Created by robin On 27-2-2019
    Copyrighted By OrbitMines ©2018
*/
public interface QueryValueBuilder<QVB extends QueryValueBuilder, C extends Column> {

    String getValues();

    QVB value(C column, Object value);

    default boolean hasValues() {
        return !getValues().isEmpty();
    }
}
