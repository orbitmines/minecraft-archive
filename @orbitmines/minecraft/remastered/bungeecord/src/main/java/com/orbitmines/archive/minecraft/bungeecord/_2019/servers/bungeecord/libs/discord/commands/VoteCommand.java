package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.discord.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Image;
import com.orbitmines.archive.minecraft._2019.libs.ServerList;
import com.orbitmines.archive.minecraft._2019.libs.discord.DiscordCommand;
import com.orbitmines.archive.minecraft._2019.libs.discord.OMDiscordBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class VoteCommand extends DiscordCommand {

    private final ServerList[] serverLists = ServerList.values();

    public VoteCommand(OMDiscordBot bot, String... alias) {
        super(bot, "Display all vote links.", alias);
    }

    @Override
    public void onDispatch(MessageReceivedEvent event, User user, Member member, MessageChannel channel, Message msg, String[] args) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor("OrbitMines Vote Links");
        builder.setColor(Color.BLUE.getAwtColor());

        for (int i = 1; i <= serverLists.length; i++) {
            ServerList serverList = serverLists[i - 1];
            builder.addField(i + ". " + serverList.getDisplayName(), serverList.getUrl(), false);
        }

        builder.setThumbnail(Image.PRISMARINE_SHARD.getUrl());

        channel.sendMessageEmbeds(builder.build()).queue();
    }
}
