package com.orbitmines.archive.minecraft._2019.utils.serializers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.util.ArrayList;
import java.util.List;

public abstract class ListSerializer<E> implements Serializer<List<E>> {

    protected abstract JsonElement serializeElement(E src);

    protected abstract E deserializeElement(JsonElement json) throws JsonParseException;

    @Override
    public List<E> deserialize(JsonElement json) throws JsonParseException {
        JsonArray jsonArray = json.getAsJsonArray();

        List<E> object = new ArrayList<>();
        jsonArray.forEach(element -> object.add(deserializeElement(element)));

        return object;
    }

    @Override
    public JsonElement serialize(List<E> src) {
        JsonArray object = new JsonArray();

        src.forEach(element -> object.add(serializeElement(element)));

        return object;
    }
}
