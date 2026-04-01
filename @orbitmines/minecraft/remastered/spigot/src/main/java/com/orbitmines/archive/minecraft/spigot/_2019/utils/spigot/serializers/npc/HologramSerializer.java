package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.serializers.npc;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.orbitmines.archive.minecraft._2019.utils.EnumUtils;
import com.orbitmines.archive.minecraft._2019.utils.serializers.Serializer;
import com.orbitmines.archive.minecraft._2019.utils.serializers.StringListSerializer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.npcs.Hologram;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.serializers.LocationSerializer;
import org.bukkit.Location;

import java.util.List;

public class HologramSerializer implements Serializer<Hologram> {

    @Override
    public JsonElement serialize(Hologram src) {
        JsonObject object = new JsonObject();
        object.addProperty("y_off", src.getYOff());
        object.addProperty("face", src.getFace().ordinal());
        object.add("location", new LocationSerializer().serialize(src.getSpawnLocation().subtract(0, src.getYOff(), 0)));
        object.add("lines", new StringListSerializer().serialize(src.getLines()));

        return object;
    }

    @Override
    public Hologram deserialize(JsonElement json) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();

        double yOff = object.get("y_off").getAsDouble();
        Hologram.Face face = EnumUtils.byOrdinal(Hologram.Face.class, object.get("face").getAsInt());
        Location location = new LocationSerializer().deserialize(object.get("location"));
        List<String> lines = new StringListSerializer().deserialize(object.get("lines"));

        Hologram hologram = new Hologram(location, yOff, face);

        for (String line : lines) {
            hologram.addLine(() -> line, false);
        }

        return hologram;
    }
}
