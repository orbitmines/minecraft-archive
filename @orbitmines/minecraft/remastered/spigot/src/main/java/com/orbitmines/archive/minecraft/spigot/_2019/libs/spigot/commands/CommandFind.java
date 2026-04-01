package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.jedis.OnlinePlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.GlobalPlayerArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor1;
import com.orbitmines.archive.minecraft._2019.libs.utils.Message;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.text.TextBuilder;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;

public class CommandFind<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends Command<S, P> {

    public CommandFind(S plugin) {
        super(plugin, "find");

        withArg(
            new GlobalPlayerArgument<S, P>(true).executes((Executor1<S, P,
                OnlinePlayer, GlobalPlayerArgument<S, P>>
            ) (player, target) -> {
                Server server = target.getServer();

                TextBuilder<P> builder = new TextBuilder<>();
                builder.
                    add(Color.INFO, p -> Message.format("Server", Color.INFO, "")).
                    add(target.getRankColor(), p -> target.getRawName()).space().
                    add(Color.SILVER, p -> p.translate("spigot", "player.command.find.currently_playing_on")).space().
                    add(server.getColor(), p -> server.getName()).bold().
                        click(ClickEvent.Action.RUN_COMMAND, p -> "/server " + server.getName()).
                        hover(HoverEvent.Action.SHOW_TEXT, p -> "§7" + p.translate("spigot", "player.connect_to_server.hover", server.getDisplayName() + "§7")).
                    add(Color.SILVER, p -> ".");

                builder.send(player);
            })
        );
    }

    @Override
    public String getDescription(P player) {
        return player.translate("spigot", "player.command.find.description");
    }
}
