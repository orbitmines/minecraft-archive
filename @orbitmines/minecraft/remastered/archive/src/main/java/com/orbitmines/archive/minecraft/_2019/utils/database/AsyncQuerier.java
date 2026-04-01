package com.orbitmines.archive.minecraft._2019.utils.database;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

@FunctionalInterface
public interface AsyncQuerier {

    void queryAsync(Runnable runnable);

}
