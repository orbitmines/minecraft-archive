package com.orbitmines.archive.minecraft._2019.utils.discord.events;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Environment;
import com.orbitmines.archive.minecraft._2019.utils.discord.DiscordBot;
import com.orbitmines.archive.minecraft._2019.utils.discord.commands.Command;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {

    private final DiscordBot discordBot;

    public MessageListener(DiscordBot discordBot) {
        this.discordBot = discordBot;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!discordBot.getServerId().equals(event.getGuild().getId()))
            return;

        User user = event.getAuthor();
        MessageChannel channel = event.getChannel();
        Message message = event.getMessage();
        String contentRaw = message.getContentRaw();

        if (contentRaw.startsWith(Command.PREFIX)) {
            String[] a = message.getContentRaw().split(" ");
            Command command = Command.parse(a[0]);

            if (command == null) {
                if (Environment.BUNGEECORD)
                    channel.sendMessage(user.getAsMention() + " that command doesn't exist. Use **!help** for help.").queue();

                return;
            }

            command.dispatch(event, user, channel, message, a);
            return;
        }

        discordBot.processMessage(event);
    }
}
