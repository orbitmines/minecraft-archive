package com.orbitmines.archive.minecraft._2019.utils.database.lib.operators.mysql;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.utils.database.lib.operators.OrderOperator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Order implements OrderOperator {

    DESC("DESC"),
    ASC("ASC");

    @Getter
    private final String operator;
}