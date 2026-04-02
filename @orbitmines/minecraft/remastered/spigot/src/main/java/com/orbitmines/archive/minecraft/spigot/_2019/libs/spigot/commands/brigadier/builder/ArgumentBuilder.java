package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.builder;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.rank.Rank;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Argument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Executor;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Namespace;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static com.mojang.brigadier.arguments.StringArgumentType.*;
import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;
import static com.mojang.brigadier.builder.RequiredArgumentBuilder.argument;

public abstract class ArgumentBuilder<S extends OMServer<S, P>, P extends OMPlayer<S, P>, B extends ArgumentBuilder> {

    @Getter protected Command<?, P> command;
    @Getter protected ArgumentBuilder<S, P, ?> parent;
    @Getter protected final List<ArgumentBuilder<S, P, ?>> children;
    protected Executor<S, P> executor;
    @Getter protected boolean executable;
    @Getter protected Rank rank;

    public ArgumentBuilder() {
        this.children = new ArrayList<>();
        this.rank = VipRank.NONE;
    }

    protected abstract B getInstance();

    public B withArg(ArgumentBuilder argument) {
        return addChild(argument);
    }

    public B namespace(ArgumentBuilder namespace) {
        return addChild(namespace);
    }

    private B addChild(ArgumentBuilder builder) {
        this.children.add(builder);
        return getInstance();
    }

    public B executes(Executor<S, P> executor) {
        this.executor = executor;
        return executable();
    }

    public B executable() {
        this.executable = true;
        return getInstance();
    }

    public B requires(Rank rank) {
        this.rank = rank;
        return getInstance();
    }

    public void assignParents(Command<S, P> command, ArgumentBuilder<S, P, ?> parent) {
        this.command = command;
        this.parent = parent;

        if (this.rank == null || this.rank.toString().equals(Rank.NONE) && !parent.toString().equals(Rank.NONE))
            this.rank = parent.rank;

        for (ArgumentBuilder<S, P, ?> child : children) {
            child.assignParents(command, this);
        }
    }

    public <T> void build(S plugin, com.mojang.brigadier.builder.ArgumentBuilder<T, ?> parent) {
        try {
            buildThis(plugin, parent);

            if (this.children.size() == 0) {
                if (this instanceof Argument && ((Argument) this).getType() == StringType.GREEDY_PHRASE)
                    return;

                /* Add greedy string at the end of each command */
                com.mojang.brigadier.builder.ArgumentBuilder<T, ?> child = argument("", greedyString());
                buildThis(plugin, child);

                parent.then(child);
                return;
            }

            for (ArgumentBuilder child : this.children) {
                com.mojang.brigadier.builder.ArgumentBuilder<T, ?> childBuilder;

                if (child instanceof Namespace) {
                    Namespace namespace = (Namespace) child;

                    childBuilder = literal(namespace.getName());
                } else {
                    Argument argument = (Argument) child;

                    RequiredArgumentBuilder<T, ?> argBuilder = argument(argument.getName(), stringArg(argument.getType()));

                    if (argument.hasSuggestions())
                        argBuilder.suggests((context, builder) -> argument.getSuggestions(getPlayer(plugin, context), context, builder));

                    childBuilder = argBuilder;
                }

                child.build(plugin, childBuilder);

                parent.then(childBuilder);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private <T> void buildThis(S plugin, com.mojang.brigadier.builder.ArgumentBuilder<T, ?> parent) {
        Executor<S, P> executor = getExecutor();

        /* StaffRank has its own check; don't display the command to players if they are not staff */
        if (this.rank instanceof StaffRank)
            parent.requires((context) -> {
                if (this.rank.isNone())
                    return true;

                CommandSender sender = OMServer.getInstance().getNms().brigadier().getSender(context);
                if (!(sender instanceof Player))
                    return false;

                P player = executor.getPlayer(plugin, context);

                return player.isEligible(this.rank);
            });
        else
            /* Vip commands we want to show to everyone, show we check permission for that in the execute statement */
            parent.requires((context) -> true);

        /* Executing */
        parent.executes((context) -> {
            OMServer.getInstance().runAsync(() -> {
                P player = executor.getPlayer(plugin, context);

                if (this.rank instanceof VipRank && !player.isEligible(this.rank)) {
                    player.sendMessage("Rank", Color.ERROR, "spigot", "player.insufficient_rank", this.rank.getDisplayName() + "§7");
                    return;
                }

                try {
                    executor.onExecute(plugin, player, this.getCommand(), ArgumentBuilder.this, context);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    executor.onError(plugin, player, this.command, ArgumentBuilder.this, context);
                }
            });
            return 1;
        });
    }

    protected Executor<S, P> getExecutor() {
        if (!isExecutable())
            return new CommandHelpBuilder<S, P>().asExecutor();

        if (this.executor != null)
            return this.executor;

        return this.parent.getExecutor();
    }

    private P getPlayer(S plugin, CommandContext context) {
        return getExecutor().getPlayer(plugin, context);
    }

    private StringArgumentType stringArg(StringArgumentType.StringType type) {
        switch (type) {

            case SINGLE_WORD:
                return word();
            case QUOTABLE_PHRASE:
                return string();
            case GREEDY_PHRASE:
            default:
                return greedyString();
        }
    }

    public int getLevel() {
        int level = 0;

        ArgumentBuilder<S, P, ?> arg = this;
        while (arg.getParent() != null) {
            level++;
            arg = arg.getParent();
        }

        return level;
    }
}
