package com.orbitmines.archive.minecraft._2019.utils.database.model;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.utils.database.lib.Column;

public interface ModelColumn<C extends Column> {

    C getColumn();

}
