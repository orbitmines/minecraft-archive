package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.builder.ArgumentBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.mutable.MutablePlayerString;
import lombok.Getter;

public class Namespace<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends ArgumentBuilder<S, P, Namespace> {

    @Getter protected String name;
    @Getter protected MutablePlayerString<P> description;

    public Namespace(String name, MutablePlayerString<P> description) {
        this(name, description, null);
    }

    public Namespace(String name, MutablePlayerString<P> description, Executor executor) {
        this.name = name;
        this.description = description;
        this.executor = executor;
        this.executable = executor != null;
    }

    @Override
    protected Namespace<S, P> getInstance() {
        return this;
    }
}
