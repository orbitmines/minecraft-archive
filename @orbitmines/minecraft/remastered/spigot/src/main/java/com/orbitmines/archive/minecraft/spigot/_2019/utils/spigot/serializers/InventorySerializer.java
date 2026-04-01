package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.serializers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.orbitmines.archive.minecraft._2019.utils.serializers.Serializer;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Deprecated
public class InventorySerializer implements Serializer<Inventory> {
    // UPDATE_TAG

    @Override
    public Inventory deserialize(JsonElement json) throws JsonParseException {
        if (json == JsonNull.INSTANCE)
            return null;

        JsonObject object = json.getAsJsonObject();

        int size = object.get("size").getAsInt();
        InventoryType inventoryType = InventoryType.valueOf(object.get("type").getAsString());
        JsonElement titleElement = object.get("title");
        String title = titleElement == JsonNull.INSTANCE ? null : titleElement.getAsString();
        ItemStack[] storageContents = new ItemStackArraySerializer().deserialize(object.get("storage_contents"));

        Inventory inventory;

        switch (inventoryType) {
            case CHEST:
            case ENDER_CHEST:
                inventory = Bukkit.createInventory(null, size, title);
                break;
            default:
                inventory = Bukkit.createInventory(null, inventoryType, title);
                break;
        }

        inventory.setStorageContents(storageContents);

        return inventory;
    }

    @Override
    public JsonElement serialize(Inventory src) {
        if (src == null)
            return JsonNull.INSTANCE;

        JsonObject object = new JsonObject();

        object.addProperty("size", src.getSize());

        object.addProperty("type", src.getType().toString());

        // TODO: Fix
//        if (src.getTitle() != null)
//            object.addProperty("title", src.getTitle());
//        else
//            object.add("title", JsonNull.INSTANCE);

        object.add("storage_contents", new ItemStackArraySerializer().serialize(src.getStorageContents()));

        return object;
    }
}
