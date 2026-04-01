package com.orbitmines.archive.minecraft._2019.utils.database.lib;

/*
    Created By Robin Egberts On 12/18/2018
    Copyrighted By OrbitMines ©2018
*/

public interface ColumnKey {

    Key getKey();

    default boolean isCandicate(){
        return getKey() == Key.CANDIDATE;
    }

    default boolean isPrimary(){
        return getKey() == Key.PRIMARY || getKey() == Key.BOTH;
    }

    default boolean isForeign(){
        return getKey() == Key.FOREIGN || getKey() == Key.BOTH;
    }

    /* SUB - ENUM */
    enum Key {

        PRIMARY,
        FOREIGN,
        BOTH,
        CANDIDATE
    }
}
