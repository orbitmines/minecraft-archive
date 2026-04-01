package com.orbitmines.archive.minecraft._2019.utils.serializers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

public class StringListSerializer extends ListSerializer<String> {

    @Override
    protected JsonElement serializeElement(String src) {
        return new JsonPrimitive(src);
    }

    @Override
    protected String deserializeElement(JsonElement json) throws JsonParseException {
        if (json == JsonNull.INSTANCE)
            return null;

        return json.getAsString();
    }
}
