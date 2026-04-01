package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.serializers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.orbitmines.archive.minecraft._2019.utils.serializers.Serializer;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ItemStackSerializer implements Serializer<ItemStack> {

    private static Gson gson;

    static {
        gson = new GsonBuilder().create();
    }

    @Override
    public ItemStack deserialize(JsonElement json) throws JsonParseException {
        if (json == JsonNull.INSTANCE)
            return null;

        Map<String, Object> map = gson.fromJson(json, new TypeToken<Map<String, Object>>(){}.getType());
        return ItemStack.deserialize(map);
    }

    //TODO TEST IF NBT TAGS TRANSFER WITH
    @Override
    public JsonElement serialize(ItemStack src) {
        if (src == null)
            return JsonNull.INSTANCE;

        return new JsonPrimitive(gson.toJson(src.serialize()));
    }
}
