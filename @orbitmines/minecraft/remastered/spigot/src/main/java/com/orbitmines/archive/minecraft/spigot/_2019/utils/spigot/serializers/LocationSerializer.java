package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.serializers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.orbitmines.archive.minecraft._2019.utils.serializers.Serializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationSerializer implements Serializer<Location> {

    @Override
    public Location deserialize(JsonElement json) throws JsonParseException {
        if (json == null)
            return null;

        JsonObject object = json.getAsJsonObject();

        try {
            return new Location(
                    Bukkit.getWorld(object.get("world").getAsString()),
                    object.get("x").getAsDouble(),
                    object.get("y").getAsDouble(),
                    object.get("z").getAsDouble(),
                    object.get("yaw").getAsFloat(),
                    object.get("pitch").getAsFloat()
            );
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public JsonElement serialize(Location src) {
        if (src == null)
            return null;

        JsonObject object = new JsonObject();

        object.addProperty("world", src.getWorld().getName());
        object.addProperty("x", src.getX());
        object.addProperty("y", src.getY());
        object.addProperty("z", src.getZ());
        object.addProperty("yaw", src.getYaw());
        object.addProperty("pitch", src.getPitch());

        return object;
    }
}
