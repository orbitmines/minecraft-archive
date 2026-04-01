package com.orbitmines.archive.minecraft._2019.utils.jedis;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.utils.database.DatabaseManager;
import com.orbitmines.archive.minecraft._2019.utils.exceptions.SyncRedisAccessException;
import lombok.Getter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class JedisManager {

    @Getter private static JedisPool pool;

    public static void initialize(JedisPool jedisPool) {
        pool = jedisPool;
    }

    public static Jedis get() {
        if (DatabaseManager.getInstance().getAsyncChecker() != null && !DatabaseManager.getInstance().getAsyncChecker().isAsync())
            throw new SyncRedisAccessException();

//        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
//        System.out.println(" ");
//        System.out.println("[Jedis] Giving resource to " + stackTraceElement.getClassName() + "#" + stackTraceElement.getMethodName() + "(" + stackTraceElement.getLineNumber() + ")");
//        System.out.println("[Jedis] Active: " + pool.getNumActive() + ", Idle: " + pool.getNumIdle() + ", Waiters: " + pool.getNumWaiters());

        return pool.getResource();
    }

    public static void checkConnection() throws JedisConnectionException {
        try (Jedis jedis = get()) {
            // TODO Test connection
        }
    }

    public static Set<String> scanAll(String pattern) {
        return scanAll(pattern, null);
    }

    public static Set<String> scanAll(String pattern, Integer limit) {
        Set<String> keys = new HashSet<>();
        ScanParams params = new ScanParams().match(pattern);

        try (Jedis jedis = get()) {
            String nextCursor = "0";

            do {
                ScanResult<String> scan = jedis.scan(nextCursor, params);

                keys.addAll(scan.getResult());

                nextCursor = scan.getCursor();
            } while (!nextCursor.equals("0") && (limit == null || keys.size() < limit));
        }

        if (limit == null || keys.size() <= limit)
            return keys;

        return new HashSet<>(new ArrayList<>(keys).subList(0, limit));
    }
}
