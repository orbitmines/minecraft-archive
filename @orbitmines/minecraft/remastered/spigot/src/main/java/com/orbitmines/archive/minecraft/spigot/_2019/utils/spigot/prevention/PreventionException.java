package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

public class PreventionException extends RuntimeException {

    public PreventionException(Throwable cause) {
        super("An error occurred while trying to prevent an event", cause);
    }
}
