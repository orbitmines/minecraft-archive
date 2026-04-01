package com.orbitmines.archive.minecraft._2019.utils.database.exceptions;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

public class DatabaseModelReloadException extends RuntimeException {

    public DatabaseModelReloadException() {
        super("Could not reload model, is it setup correctly?");
    }
}
