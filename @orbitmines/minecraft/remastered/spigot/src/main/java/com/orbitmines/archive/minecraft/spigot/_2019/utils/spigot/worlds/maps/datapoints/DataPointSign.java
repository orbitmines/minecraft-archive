package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints;

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.BlockUtils;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;

import java.util.ArrayList;
import java.util.List;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */
public  abstract class DataPointSign extends DataPoint {

    @Getter private final String firstLine;

    public DataPointSign(String firstLine, Type type, Material material) {
        super(type, material);

        this.firstLine = firstLine;
    }

    public abstract boolean buildAt(DataPointLoader loader, Location location, String[] data);

    @Override
    public boolean buildAt(DataPointLoader loader, Location location) {
        Sign sign = BlockUtils.getSignAround(location.getBlock());

        String[] lines = sign.getLines();

        if (!(loader instanceof DataPointTester))
            sign.getBlock().setType(Material.AIR);

        /* All three lines into one string */
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < 4; i++) {
            if (i != 1)
                stringBuilder.append(" ");

            stringBuilder.append(lines[i]);
        }

        String[] rawData = stringBuilder.toString().split(" ");

        List<String> data = new ArrayList<>();
        /* There might be some double spaces, let's clean that up */
        for (String raw : rawData) {
            if (!raw.equals(""))
                data.add(raw);
        }

        return buildAt(loader, location, data.toArray(new String[0]));
    }

    @Override
    public boolean equals(BlockState blockState) {
        if (!super.equals(blockState))
            return false;

        /* Material & Data are the same, now we check the first line */

        Sign sign = BlockUtils.getSignAround(blockState.getBlock());

        return sign != null && sign.getLine(0).equalsIgnoreCase(firstLine);
    }
}
