package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.builder.ArgumentBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ReflectionUtils;
import lombok.Getter;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.command.VanillaCommandWrapper;

import java.lang.reflect.Field;
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

    /* Unregister command from Root — uses reflection since removeCommand was removed in 26.1 */
    public void unregister() {
        /* Remove from brigadier dispatchers */
        for (boolean vanilla : new boolean[]{false, true}) {
            CommandDispatcher dispatcher = ReflectionUtils.getCommandDispatcher(vanilla);
            if (dispatcher != null) {
                for (String alias : getAllCommands()) {
                    removeFromNode(dispatcher.getRoot(), alias);
                    /* Also remove namespaced variants like minecraft:help, bukkit:help */
                    removeFromNode(dispatcher.getRoot(), "minecraft:" + alias);
                    removeFromNode(dispatcher.getRoot(), "bukkit:" + alias);
                }
            }
        }

        /* Remove from Bukkit's SimpleCommandMap */
        try {
            Object craftServer = Bukkit.getServer();
            Object commandMap = craftServer.getClass().getMethod("getCommandMap").invoke(craftServer);
            Field knownCommandsField = org.bukkit.command.SimpleCommandMap.class.getDeclaredField("knownCommands");
            knownCommandsField.setAccessible(true);
            Map<String, ?> knownCommands = (Map<String, ?>) knownCommandsField.get(commandMap);
            for (String alias : getAllCommands()) {
                knownCommands.remove(alias);
                knownCommands.remove("minecraft:" + alias);
                knownCommands.remove("bukkit:" + alias);
            }
        } catch (Exception ignored) {
        }

        commands.remove(this);
    }

    private static void removeFromNode(CommandNode<?> node, String name) {
        try {
            for (String fieldName : new String[]{"children", "literals", "arguments"}) {
                Field field = CommandNode.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                Map<String, ?> map = (Map<String, ?>) field.get(node);
                map.remove(name);
            }
        } catch (Exception ignored) {
        }
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
        Commands nmsCommands = MinecraftServer.getServer().getCommands();
        Map<String, org.bukkit.command.Command> knownCommands = getBukkitKnownCommands();

        for (String alias : getAllCommands()) {
            LiteralArgumentBuilder builder = literal(alias);

            /* Build command */
            build(this.plugin, builder);

            LiteralCommandNode node = dispatcher.register(builder);

            /* Also register in Bukkit's command map — CraftServer.dispatchCommand only checks this map */
            if (knownCommands != null) {
                VanillaCommandWrapper wrapper = new VanillaCommandWrapper(nmsCommands, (CommandNode<CommandSourceStack>) node);
                knownCommands.put(alias, wrapper);
            }
        }
        commands.add(this);
    }

    private static Map<String, org.bukkit.command.Command> getBukkitKnownCommands() {
        try {
            Object craftServer = Bukkit.getServer();
            Object commandMap = craftServer.getClass().getMethod("getCommandMap").invoke(craftServer);
            Field knownCommandsField = org.bukkit.command.SimpleCommandMap.class.getDeclaredField("knownCommands");
            knownCommandsField.setAccessible(true);
            return (Map<String, org.bukkit.command.Command>) knownCommandsField.get(commandMap);
        } catch (Exception e) {
            return null;
        }
    }

    public Set<String> getAllCommands() {
        Set<String> all = new HashSet<>(Arrays.asList(aliases));
        all.add(name);

        return all;
    }
}
