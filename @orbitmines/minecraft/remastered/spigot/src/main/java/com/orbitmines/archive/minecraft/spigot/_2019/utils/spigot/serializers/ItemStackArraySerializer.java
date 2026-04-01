package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.serializers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.orbitmines.archive.minecraft._2019.utils.serializers.ArraySerializer;
import org.bukkit.inventory.ItemStack;

public class ItemStackArraySerializer extends ArraySerializer<ItemStack> {

    @Override
    protected JsonElement serializeElement(ItemStack src) {
        return new ItemStackSerializer().serialize(src);
    }

    @Override
    protected ItemStack deserializeElement(JsonElement json) throws JsonParseException {
        return new ItemStackSerializer().deserialize(json);
    }

    @Override
    protected ItemStack[] initializeArray(int size) {
        return new ItemStack[size];
    }
}
