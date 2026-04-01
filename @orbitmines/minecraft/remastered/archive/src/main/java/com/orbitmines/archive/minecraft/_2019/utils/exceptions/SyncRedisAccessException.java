package com.orbitmines.archive.minecraft._2019.utils.exceptions;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

public class SyncRedisAccessException extends RuntimeException {

    public SyncRedisAccessException() {
        super("You are not allowed to access redis on a sync thread.");
    }
}
