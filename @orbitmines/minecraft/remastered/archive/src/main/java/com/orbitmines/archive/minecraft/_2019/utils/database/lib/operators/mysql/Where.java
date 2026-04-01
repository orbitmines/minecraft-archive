package com.orbitmines.archive.minecraft._2019.utils.database.lib.operators.mysql;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.utils.database.lib.operators.WhereOperator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Where implements WhereOperator {

    EQUALS("="),
    GREATER_THAN(">"),
    GREATER_THAN_OR_EQUAL(">="),
    IS(" IS "),
    IS_NOT(" IS NOT "),
    LESSER_THAN("<"),
    LESSER_THAN_OR_EQUAL("<="),
    LIKE(" LIKE "),
    NOT_EQUAL("!="),
    IN(" IN ");

    @Getter private final String operator;
}
