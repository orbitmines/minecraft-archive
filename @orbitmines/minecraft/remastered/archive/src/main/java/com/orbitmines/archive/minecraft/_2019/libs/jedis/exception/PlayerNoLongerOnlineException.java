package com.orbitmines.archive.minecraft._2019.libs.jedis.exception;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

public class PlayerNoLongerOnlineException extends RuntimeException {

    public PlayerNoLongerOnlineException() {
        super("Player is no longer online!");
    }
}
