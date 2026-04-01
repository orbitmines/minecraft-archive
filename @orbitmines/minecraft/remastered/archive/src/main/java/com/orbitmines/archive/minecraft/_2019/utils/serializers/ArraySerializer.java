package com.orbitmines.archive.minecraft._2019.utils.serializers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public abstract class ArraySerializer<E> implements Serializer<E[]> {

    protected abstract JsonElement serializeElement(E src);

    protected abstract E deserializeElement(JsonElement json) throws JsonParseException;

    protected abstract E[] initializeArray(int size);

    @Override
    public E[] deserialize(JsonElement json) throws JsonParseException {
        JsonArray jsonArray = json.getAsJsonArray();

        E[] elements = initializeArray(jsonArray.size());

        for (JsonElement element : jsonArray) {
            JsonObject elementObject = element.getAsJsonObject();

            elements[elementObject.get("index").getAsInt()] = deserializeElement(elementObject.get("element"));
        }

        return elements;
    }

    @Override
    public JsonElement serialize(E[] src) {
        JsonArray object = new JsonArray();

        for (int i = 0; i < src.length; i++) {
            E element = src[i];

            JsonObject elementObject = new JsonObject();
            elementObject.addProperty("index", i);
            elementObject.add("element", serializeElement(element));
        }

        return object;
    }
}
