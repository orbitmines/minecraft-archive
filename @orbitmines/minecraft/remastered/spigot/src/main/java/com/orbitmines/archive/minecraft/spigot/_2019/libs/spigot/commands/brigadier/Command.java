package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.builder.ArgumentBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ReflectionUtils;
import lombok.Getter;

import java.util.*;

import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */
public abstract class Command<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends ArgumentBuilder<S, P, Command<S, P>> {

    @Getter private static List<Command> commands = new ArrayList<>();

    @Getter S plugin;
    @Getter protected final Server server;
    @Getter protected final String name;
    @Getter protected final String[] aliases;

//    static {
//        CommandDispatcher dispatcher = ReflectionUtils.getCommandDispatcher(false);
//
//        for (CommandNode node : (Collection<CommandNode>) new ArrayList<>(dispatcher.getRoot().getChildren())) {
//            System.out.println("Removing command " + node.getName() + "...");
//            dispatcher.getRoot().removeCommand(node.getName());
//        }
//
//        CommandDispatcher vanillaDispatcher = ReflectionUtils.getCommandDispatcher(true);
//        for (CommandNode node : (Collection<CommandNode>) new ArrayList<>(vanillaDispatcher.getRoot().getChildren())) {
//            System.out.println("Removing vanilla command " + node.getName() + "...");
//            vanillaDispatcher.getRoot().removeCommand(node.getName());
//        }
//    }

    public Command(S plugin, String name, String... aliases) {
        this(plugin, null, name, aliases);
    }

    public Command(S plugin, Server server, String name, String... aliases) {
        this.plugin = plugin;
        this.server = server;
        this.name = name;
        this.aliases = aliases;
        this.command = this;
    }

    public abstract String getDescription(P player);

//    /* Register command to CommandDispatcher */
//    protected LiteralCommandNode register(LiteralArgumentBuilder builder) {
//        CommandDispatcher dispatcher = ReflectionUtils.getCommandDispatcher(false);
//
//        LiteralCommandNode node = dispatcher.register(builder);
//        commands.add(this);
//
//        return node;
//    }

    /* Unregister command from Root */
    public void unregister() {
        CommandDispatcher dispatcher = ReflectionUtils.getCommandDispatcher(false);

        for (String alias : getAllCommands()) {
            dispatcher.getRoot().removeCommand(alias);
        }

        commands.remove(this);
    }

    @Override
    protected Command<S, P> getInstance() {
        return this;
    }

    @Override
    public Command<S, P> getCommand() {
        return this;
    }

    public void register() {
        /* Assign Parents TODO: Fix for this */
        for (ArgumentBuilder<S, P, ?> child : children) {
            child.assignParents(this, this);
        }

        /* Override vanilla commands */
        unregister();

        CommandDispatcher dispatcher = ReflectionUtils.getCommandDispatcher(false);
        for (String alias : getAllCommands()) {
            LiteralArgumentBuilder builder = literal(alias);

            /* Build command */
            build(this.plugin, builder);

            dispatcher.register(builder);
        }
        commands.add(this);
    }

    public Set<String> getAllCommands() {
        Set<String> all = new HashSet<>(Arrays.asList(aliases));
        all.add(name);

        return all;
    }
}
