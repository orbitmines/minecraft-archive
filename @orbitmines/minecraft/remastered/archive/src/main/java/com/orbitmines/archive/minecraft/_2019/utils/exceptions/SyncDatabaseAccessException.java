package com.orbitmines.archive.minecraft._2019.utils.exceptions;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

public class SyncDatabaseAccessException extends RuntimeException {

    public SyncDatabaseAccessException() {
        super("You are not allowed to access the database on a sync thread.");
    }
}
