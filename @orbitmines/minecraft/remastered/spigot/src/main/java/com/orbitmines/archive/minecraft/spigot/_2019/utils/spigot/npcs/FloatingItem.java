package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.npcs;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemStack;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotPlayer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;

import java.util.*;

public class FloatingItem<P extends SpigotPlayer> extends Hologram<P> {

    @Getter private static Map<World, List<FloatingItem>> floatingItems = new HashMap<>();

    @Getter private Item item;
    @Getter private ArmorStand clickBox;

    @Getter @Setter private MutableItemStack itemBuilder;

    public FloatingItem(MutableItemStack itemBuilder, Location spawnLocation) {
        super(spawnLocation);

        this.itemBuilder = itemBuilder;
        addLine(() -> null, true);
        addLine(() -> null, true);
    }

    public FloatingItem(MutableItemStack itemBuilder, Location spawnLocation, double yOff) {
        super(spawnLocation, yOff, Face.DOWN);

        this.itemBuilder = itemBuilder;
        addLine(() -> null, true);
        addLine(() -> null, true);
    }

    @Override
    protected void spawn() {
        super.spawn();

        clickBox = nms.armorStand().spawn(spawnLocation.clone().subtract(0, getYOff() - Hologram.Y_OFFSET_PER_LINE * 3, 0), false);
        clickBox.setGravity(false);

        item = spawnLocation.getWorld().dropItem(spawnLocation, itemBuilder.toItemStack());
        item.setPickupDelay(Integer.MAX_VALUE);
        item.setUnlimitedLifetime(true);
        item.setInvulnerable(true);

        lines.get(0).getArmorStand().addPassenger(item);
    }

    @Override
    protected void despawn() {
        super.despawn();

        if (clickBox != null)
            clickBox.remove();

        if (item != null)
            item.remove();
    }

    @Override
    public void update() {
        super.update();

        item.setItemStack(itemBuilder.toItemStack());
    }

    @Override
    public Collection<Entity> getEntities() {
        Collection<Entity> entities = super.getEntities();
        entities.add(item);
        entities.add(clickBox);

        return entities;
    }

    @Override
    protected void register() {
        super.register();

        floatingItems.computeIfAbsent(getWorld(), key -> new ArrayList<>()).add(this);
    }

    @Override
    protected void unregister() {
        super.unregister();

        floatingItems.get(getWorld()).remove(this);
    }

    public static FloatingItem getFloatingItem(Entity entity) {
        if (!floatingItems.containsKey(entity.getWorld()))
            return null;

        for (FloatingItem npc : getFloatingItemsIn(entity.getWorld())) {
            if (Npc.containsEntity(npc.getEntities(), entity))
                return npc;
        }
        return null;
    }

    public static List<FloatingItem> getFloatingItemsIn(World world) {
        return floatingItems.get(world);
    }
}
