package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.generators;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.generators.flat_generator.WorldCreatorFlat;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.generators.void_generator.WorldCreatorVoid;
import lombok.Getter;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public enum DefaultWorldType implements WorldType {

    NORMAL(WorldCreator .class, World.Environment.NORMAL),
    NETHER(WorldCreator.class, World.Environment.NETHER),
    END(WorldCreator.class, World.Environment.THE_END),
    FLAT(WorldCreatorFlat.class, World.Environment.NORMAL),
    VOID(WorldCreatorVoid.class, World.Environment.NORMAL);

    @Getter private final Class<? extends WorldCreator> worldCreator;
    @Getter private final World.Environment environment;

    DefaultWorldType(Class<? extends WorldCreator> worldCreator, World.Environment environment) {
        this.worldCreator = worldCreator;
        this.environment = environment;
    }
}
