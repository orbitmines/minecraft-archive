package com.orbitmines.minecraft.spigot.servers.fog.util;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Tags FoG-spawned hologram entities (ArmorStand markers + their ridden Item) with
 * a plugin-scoped {@link NamespacedKey} so we can distinguish them from player-placed
 * armor stands / items.
 *
 * <p>Cleanup runs on {@code WorldLoadEvent} and once on plugin startup. The tag
 * is the only safe way to identify "our" entities — we must not touch untagged
 * entities in any world.</p>
 */
public class HologramTag {

    private static NamespacedKey KEY;

    public static void init(JavaPlugin plugin) {
        KEY = new NamespacedKey(plugin, "fog_hologram");
    }

    public static NamespacedKey key() { return KEY; }

    public static void tag(Entity entity) {
        if (entity == null || KEY == null) return;
        entity.getPersistentDataContainer().set(KEY, PersistentDataType.BYTE, (byte) 1);
        /* Also mark non-persistent so Minecraft doesn't save this to level.dat — even
           if cleanup somehow misses one on shutdown, it won't come back next boot. */
        entity.setPersistent(false);
    }

    public static boolean isTagged(Entity entity) {
        if (entity == null || KEY == null) return false;
        return entity.getPersistentDataContainer().has(KEY, PersistentDataType.BYTE);
    }

    /** Remove every tagged entity in one world. */
    public static int cleanupWorld(World world) {
        if (KEY == null || world == null) return 0;
        int removed = 0;
        for (Entity entity : world.getEntities()) {
            if (isTagged(entity)) {
                entity.remove();
                removed++;
            }
        }
        return removed;
    }

    /** Sweep every currently-loaded world. Call once on startup. */
    public static int cleanupAllLoaded() {
        int total = 0;
        for (World w : Bukkit.getWorlds()) total += cleanupWorld(w);
        return total;
    }
}
