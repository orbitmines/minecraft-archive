package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.events;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.brigadier.ExecuteCommandExceptionEvent;
import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.discord.CustomChannel;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CommandEvents<S extends OMServer<S, P>, P extends OMPlayer<S, P>> implements Listener {

    protected final S plugin;

    public CommandEvents(S plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onRequest(PlayerCommandSendEvent event) {
        Set<String> commands = getAllCommands();

        for (String command : new ArrayList<>(event.getCommands())) {
            if (!commands.contains(command))
                event.getCommands().remove(command);
        }
    }

    @EventHandler
    public void onHelp(PlayerCommandPreprocessEvent event) {
        if (!event.getMessage().startsWith("/"))
            return;

        Set<String> commands = getAllCommands();

        P player = plugin.getPlayer(event.getPlayer());

        if (commands.contains(event.getMessage().split(" ")[0].substring(1).toLowerCase())) {
            plugin.discord(bot -> bot.withPlayerEmote(player.getUUID(), player.getRawName(), false, emote -> {
                bot.getTextChannel(CustomChannel.COMMAND_LOG).sendMessage(bot.getPlayerDisplay(player, emote, player.getRawName()) + " executed command **" + event.getMessage() + "**.").queue();
            }));

            return;
        }

        if (player.isOpMode())
            return;

        event.setCancelled(true);
        player.sendMessage("Commands", Color.RED, "spigot", "player.command.unknown", "§9/help§7");
    }

    @EventHandler
    public void onError(ExecuteCommandExceptionEvent event) {
        CommandSender sender = event.getCommandSender();

        /* When command parsed error; in our case has no staff privileges, send unknown command message */
        if (!(sender instanceof Player))
            return;

        P player = plugin.getPlayer((Player) sender);

        if (player.isOpMode())
            return;

        event.setCancelled(true);
        player.sendMessage("Commands", Color.RED, "spigot", "player.command.unknown", "§9/help§7");
    }

    protected Set<String> getAllCommands() {
        Set<String> commands = new HashSet<>();

        for (Command command : Command.getCommands()) {
            commands.addAll(command.getAllCommands());
        }

        return commands;
    }

}
