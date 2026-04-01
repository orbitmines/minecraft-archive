package com.orbitmines.archive.minecraft._2019.utils.discord.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import lombok.Getter;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.*;

public abstract class Command {

    public final static String PREFIX = "!";
    private final static ArrayList<Command> commands = new ArrayList<>();

    @Getter protected final Set<String> alias;

    public Command(String... alias) {
        commands.add(this);
        this.alias = new HashSet<>(Arrays.asList(alias));
    }

    /* a[0] = '!<commands>' */
    public abstract void dispatch(MessageReceivedEvent event, User user, MessageChannel channel, Message msg, String[] a);

    public void unregister() {
        commands.remove(this);
    }

    public static Command parse(String cmd) {
        for (Command command : commands) {
            if (!command.alias.contains(cmd.toLowerCase().substring(PREFIX.length())))
                continue;

            return command;
        }

        return null;
    }

    public static List<Command> getCommands() {
        return commands;
    }
}
