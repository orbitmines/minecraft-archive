package com.orbitmines.archive.minecraft._2019.utils.api.mojang;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.google.gson.Gson;
import com.orbitmines.archive.minecraft._2019.utils.URLUtils;
import com.orbitmines.archive.minecraft._2019.utils.UUIDUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;
import java.util.concurrent.Callable;

public class NameHistoryFetcher implements Callable<NameHistoryFetcher.JsonNameHistory[]> {

    private static final String NAME_HISTORY_API = "https://api.mojang.com/user/profiles/";

    private final UUID uuid;

    public NameHistoryFetcher(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public NameHistoryFetcher.JsonNameHistory[] call() throws Exception {
        InputStream stream = URLUtils.readInputStream(NAME_HISTORY_API + UUIDUtils.trim(uuid) + "/names");
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        String string = in.readLine();
        in.close();

        return new Gson().fromJson(string, JsonNameHistory[].class);
    }

    public class JsonNameHistory {

        public String name;
        public long changedToAt;

    }
}
