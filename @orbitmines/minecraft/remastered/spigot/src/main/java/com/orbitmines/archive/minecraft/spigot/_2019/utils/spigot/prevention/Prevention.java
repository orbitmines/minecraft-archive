package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.prevention.events.*;
import lombok.Getter;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Prevention {

    CHUNK_UNLOAD(PreventChunkUnload.class),                                             // Prevent chunks from unloading
    FOOD_CHANGE(PreventFoodChange.class),                                               // Prevent food level from decaying
    PVP(PreventPvP.class),                                                              // Prevent pvp
    MOB_SPAWN(PreventMobSpawn.class),                                                   // Prevent mobs from spawning except with SpawnReason#CUSTOM
    WEATHER_CHANGE(PreventWeatherChange.class),                                         // Prevent weather from changing
    FALL_DAMAGE(PreventFallDamage.class),                                               // Prevent fall damage
    BLOCK_PLACE(PreventBlockPlace.class),                                               // Prevent blocks from being placed
    BLOCK_BREAK(PreventBlockBreak.class),                                               // Prevent blocks from being broken
    BLOCK_INTERACTING(PreventBlockInteracting.class),                                   // Prevent block interaction
    BLOCK_SPREAD(PreventBlockSpread.class),                                             // Prevent block spreading
    MONSTER_EGG_USAGE(                                                                  // Prevent monster eggs from being used
            PreventMonsterEggUsage.Interact.class,
            PreventMonsterEggUsage.InteractAtEntity.class,
            PreventMonsterEggUsage.InteractEntity.class
    ),
    SWAP_HAND_ITEMS(PreventSwapHandItems.class),                                        // Prevent being able to swap between main and offhand
    ITEM_DROP(PreventItemDrop.class),                                                   // Prevent items from being dropped by players
    ITEM_PICKUP(PreventItemPickup.class),                                               // Prevent items from being picked up by entities
    PHYSICAL_INTERACTING(PreventPhysicalInteracting.class),                             // Prevent physical interactions with blocks
    PHYSICAL_INTERACTING_EXCEPT_PLATES(PreventPhysicalExceptPlatesInteracting.class),   // Prevent physical interactions with blocks except pressure plates
    BUCKET_USAGE(PreventBucketUsage.class),                                             // Prevent bucket usage
    CLICK_PLAYER_INVENTORY(PreventClickPlayerInventory.class),                          // Prevent player from moving items in their inventory
    PLAYER_DAMAGE(PreventPlayerDamage.class),                                           // Prevent any damage done to the player
    LEAF_DECAY(PreventLeafDecay.class),                                                 // Prevent leaves from decaying
    ENTITY_INTERACTING(                                                                 // Prevent any interactions with entities
            PreventEntityInteracting.InteractAtEntity.class,
            PreventEntityInteracting.InteractEntity.class
    ),
    EXPLOSION_DAMAGE(PreventExplosionDamage.class);                                     // Prevent explosion damage to blocks

    @Getter private Class<? extends PreventionEvent>[] eventClasses;
    @Getter private List<PreventionEvent<?>> events;

    Prevention(Class<? extends PreventionEvent>... eventClasses) {
        this.eventClasses = eventClasses;
    }

    private List<PreventionEvent<?>> createEvents(World world) {
        if (events == null)
            events = new ArrayList<>();

        events.addAll(Arrays.stream(eventClasses).map((eventClass) -> {
            try {
                return (PreventionEvent<?>) eventClass.getConstructor(World.class).newInstance(world);
            } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toList()));

        return events;
    }

    public void unregister(World world) {
        for (PreventionEvent event : events) {
            event.unregister(world);
        }
    }

    public void unregister() {
        for (PreventionEvent event : events) {
            event.unregister();
        }
    }

    public static void unregisterAll() {
        for (Prevention prevention : Prevention.values()) {
            if (prevention.getEvents() == null)
                continue;

            prevention.unregister();
        }
    }

    public static void unregisterAll(World world) {
        for (Prevention prevention : Prevention.values()) {
            if (prevention.getEvents() == null)
                continue;

            prevention.unregister(world);
        }
    }

    public static void prevent(JavaPlugin plugin, World world, Prevention... preventions) {
        for (Prevention prevention : preventions) {
            if (prevention.getEvents() == null)
                for (PreventionEvent<?> event : prevention.createEvents(world))
                    plugin.getServer().getPluginManager().registerEvent(event.getEventClass(), event, event.getPriority(), (listener, e) -> event.execute(e), plugin);
            else
                for (PreventionEvent<?> event : prevention.getEvents())
                    event.getWorlds().add(world);
        }
    }
}
