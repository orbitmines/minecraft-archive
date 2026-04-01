package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.exceptions;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import java.util.UUID;

public class ProcessVoteException extends RuntimeException {

    public ProcessVoteException(UUID uuid, int votes, Throwable throwable) {
        super("An error occurred while processing " + votes + " votes for " + uuid.toString(), throwable);
    }
}
