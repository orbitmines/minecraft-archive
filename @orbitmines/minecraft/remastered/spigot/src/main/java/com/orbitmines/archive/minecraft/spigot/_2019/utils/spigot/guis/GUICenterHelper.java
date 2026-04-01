package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemStack;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotPlayer;

import java.util.List;

public interface GUICenterHelper<P extends SpigotPlayer, V> {

    GUI<P> getInstance();

    GUI.Item<P, ?> getItem(V object);

    default void center(int row, Centerer<V> centerer) {
        GUI<P> instance = getInstance();

        for (int i = 0; i < 9; i++) {
            int slot = i;

            instance.set(row, slot, new GUI.Item<P, MutableItemStack>(() -> {
                List<V> list = centerer.collect();

                if (!withinBounds(list, slot))
                    return null;

                int index = getIndex(list, slot);
                V object = list.get(index);

                GUI.Item<P, ?> item = getItem(object);

                if (item == null)
                    return null;

                return item.build();
            }).click(event -> {
                List<V> list = centerer.collect();

                if (!withinBounds(list, slot))
                    return;

                int index = getIndex(list, slot);
                V object = list.get(index);

                GUI.Item<P, ?> item = getItem(object);

                if (item == null || item.clickEvent == null)
                    return;

                item.clickEvent.onClick(event);
            }));
        }
    }

    default boolean withinBounds(List<V> collection, int slot) {
        return getIndex(collection, slot) != -1;
    }

    default int getIndex(List<V> collection, int slot) {
        int size = collection.size();
        int selectorSlot = 4 - (size % 2 == 0 ? size / 2 : (size - 1) / 2);

        for (int index = 0; index < size; index++) {
            boolean clearMiddle = slot == 4 && size % 2 == 0;

            if (clearMiddle)
                selectorSlot++;

            /* Found the collection index for the slot */
            if (selectorSlot == slot)
                return index;

            selectorSlot++;
        }

        return -1;
    }

    @FunctionalInterface
    interface Centerer<V> {

        List<V> collect();

    }
}
