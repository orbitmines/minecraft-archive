package com.orbitmines.archive.minecraft._2019.utils.serializers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public interface Serializer<E> {

    JsonElement serialize(E src);

    E deserialize(JsonElement json) throws JsonParseException;

}
