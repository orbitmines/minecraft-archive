package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.datapoints.map;

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPointLoader;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPointSign;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */
@Deprecated
public class KitPvPDataPointMapSpawnpoint extends DataPointSign {

    @Getter private List<Location> spawns;

    public KitPvPDataPointMapSpawnpoint() {
        super("SPAWNPOINT", Type.GOLD_PLATE, Material.GREEN_WOOL);

        spawns = new ArrayList<>();
    }

    @Override
    public boolean buildAt(DataPointLoader loader, Location location, String[] data) {
        float yaw;
        if (data.length >= 1) {
            try {
                yaw = Float.parseFloat(data[0]);
            } catch(NumberFormatException ex) {
                failureMessage = "Invalid Yaw.";
                return false;
            }
        } else {
            failureMessage = "Yaw not given.";
            return false;
        }

        float pitch;
        if (data.length >= 2) {
            try {
                pitch = Float.parseFloat(data[1]);
            } catch(NumberFormatException ex) {
                failureMessage = "Invalid Pitch.";
                return false;
            }
        } else {
            failureMessage = "Pitch not given.";
            return false;
        }

        location.setYaw(yaw);
        location.setPitch(pitch);
        spawns.add(location.add(0.5, 0.5, 0.5));
        return true;
    }

    @Override
    public boolean setup() {
        if (spawns.size() == 0) {
            failureMessage = "No Spawnpoints located.";
            return false;
        }
        return true;
    }
}
