package com.orbitmines.archive.minecraft._2019.utils.serializers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

import java.util.UUID;

public class UUIDListSerializer extends ListSerializer<UUID> {

    @Override
    protected JsonElement serializeElement(UUID src) {
        return new JsonPrimitive(src.toString());
    }

    @Override
    protected UUID deserializeElement(JsonElement json) throws JsonParseException {
        if (json == JsonNull.INSTANCE)
            return null;

        return UUID.fromString(json.getAsString());
    }
}
