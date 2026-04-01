package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.utils.RandomUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilderInstance;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotPlayer;

public class RandomPlayerItemBuilder<B extends ItemBuilderInstance, P extends SpigotPlayer> implements MutablePlayerItemBuilder<B, P> {

    private final MutablePlayerItemBuilder<B, P>[] mutableBuilders;

    public RandomPlayerItemBuilder(MutablePlayerItemBuilder<B, P>... mutableBuilders) {
        this.mutableBuilders = mutableBuilders;
    }

    @Override
    public B toBuilder(P player) {
        return RandomUtils.randomFrom(mutableBuilders).toBuilder(player);
    }
}
