package com.orbitmines.archive.minecraft._2019.utils.database.exceptions;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

public class DatabaseConnectionException extends Exception {

    public DatabaseConnectionException(String url, Throwable throwable) {
        super("Cannot connect to database at " + url, throwable);
    }
}
