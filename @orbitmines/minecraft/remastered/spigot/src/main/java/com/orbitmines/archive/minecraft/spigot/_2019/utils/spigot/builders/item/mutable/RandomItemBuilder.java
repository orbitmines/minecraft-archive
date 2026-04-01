package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.utils.RandomUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilderInstance;

public class RandomItemBuilder<B extends ItemBuilderInstance> implements MutableItemBuilder<B> {

    private final MutableItemBuilder<B>[] mutableBuilders;

    public RandomItemBuilder(MutableItemBuilder<B>... mutableBuilders) {
        this.mutableBuilders = mutableBuilders;
    }

    @Override
    public B toBuilder() {
        return RandomUtils.randomFrom(mutableBuilders).toBuilder();
    }
}
