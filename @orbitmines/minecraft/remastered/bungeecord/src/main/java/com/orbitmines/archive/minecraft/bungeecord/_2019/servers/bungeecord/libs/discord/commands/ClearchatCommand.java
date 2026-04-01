package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.discord.commands;

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.discord.DiscordCommand;
import com.orbitmines.archive.minecraft._2019.libs.discord.OMDiscordBot;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ClearchatCommand extends DiscordCommand {

    public static List<String> channelBlacklist = Arrays.asList("welcome", "announcements", "patch_notes", "polls", "new_players", "donations");

    public ClearchatCommand(OMDiscordBot bot, String... alias) {
        super(bot, "Clears the chat", alias);

        requires(StaffRank.MODERATOR);
    }

    @Override
    public void onDispatch(MessageReceivedEvent event, User user, Member member, MessageChannel channel, Message msg, String[] args) {

        if (channelBlacklist.contains(channel.getName().toLowerCase())) {
            sendError(channel, "Channel " + channel.getName(), "Cannot be cleared");
            return;
        }

        int amount = 10;
        if (args.length > 1) {
            try {
                amount = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                sendError(channel, "Invalid argument", "Argument must be a number");
                return;
            }

            if (amount > 50 || amount < 1) {
                sendError(channel, "Invalid amount", "Please enter an amount between **1** and **50**");
                return;
            }
        }

        MessageHistory history = new MessageHistory(channel);
        try {
            List<Message> msgs = history.retrievePast(amount).complete();
            amount = msgs.size();

            for (int i = 0; i < msgs.size(); i++) {
                msgs.get(i).delete().complete();
            }
        } catch (Exception e) {}
        sendConfirm(channel, amount);

    }

    private void sendConfirm(MessageChannel channel, int amount) {
        EmbedBuilder result = new EmbedBuilder();
        result.addField("Channel " + channel.getName(), "Cleared " + amount + " messages succesfully", false);
        result.setColor(Color.GREEN.getAwtColor());

        Message resMsg = channel.sendMessageEmbeds(result.build()).complete();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                resMsg.delete().queue();
            }
        }, 3000);
    }

    private void sendError(MessageChannel channel, String header, String content) {
        EmbedBuilder error = new EmbedBuilder();
        error.addField(header,  content, false);
        error.setColor(Color.RED.getAwtColor());
        channel.sendMessageEmbeds(error.build()).queue();
    }
}