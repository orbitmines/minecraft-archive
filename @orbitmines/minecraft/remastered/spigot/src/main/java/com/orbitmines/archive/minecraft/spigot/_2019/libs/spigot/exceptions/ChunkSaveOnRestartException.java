package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.exceptions;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

public class ChunkSaveOnRestartException extends RuntimeException {

    public ChunkSaveOnRestartException(String message) {
        super("An error occurred while saving chunks on restart: " + message);
    }
}
