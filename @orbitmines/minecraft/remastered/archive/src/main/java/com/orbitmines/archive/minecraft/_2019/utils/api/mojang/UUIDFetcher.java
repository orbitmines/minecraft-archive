package com.orbitmines.archive.minecraft._2019.utils.api.mojang;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.orbitmines.archive.minecraft._2019.utils.URLUtils;
import com.orbitmines.archive.minecraft._2019.utils.UUIDUtils;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.*;
import java.util.concurrent.Callable;

public class UUIDFetcher implements Callable<Map<String, UUID>> {

    private static final String PROFILE_API = "https://api.mojang.com/profiles/minecraft";

    private final Collection<String> names;

    public UUIDFetcher(Collection<String> names) {
        this.names = new ArrayList<>(names);
    }

    @Override
    public Map<String, UUID> call() throws Exception {
        Gson gson = new Gson();

        HttpURLConnection connection = URLUtils.createJsonConnection(PROFILE_API);
        JsonArray request = new JsonArray();
        for (String name : names) {
            request.add(name);
        }
        URLUtils.writeRequest(connection, gson.toJson(request));

        // TODO: Error code 415
        JsonProfile[] response = gson.fromJson(new InputStreamReader(connection.getInputStream()), JsonProfile[].class);

        Map<String, UUID> uuidMap = new HashMap<>();
        for (JsonProfile profile : response) {
            UUID uuid = UUIDUtils.parse(profile.id);
            String name = profile.name;

            uuidMap.put(name, uuid);
        }

        return uuidMap;
    }

    private class JsonProfile {

        String id;
        String name;
        boolean legacy;
        boolean demo;

    }
}
