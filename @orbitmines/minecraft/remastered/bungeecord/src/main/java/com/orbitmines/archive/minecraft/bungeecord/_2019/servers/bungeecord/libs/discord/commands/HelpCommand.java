package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.discord.commands;

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.discord.DiscordCommand;
import com.orbitmines.archive.minecraft._2019.libs.Image;
import com.orbitmines.archive.minecraft._2019.libs.discord.OMDiscordBot;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft._2019.utils.discord.commands.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;

public class HelpCommand extends DiscordCommand {

    public HelpCommand(OMDiscordBot bot, String... alias) {
        super(bot, "Display all commands", alias);
    }

    @Override
    public void onDispatch(MessageReceivedEvent event, User user, Member member, MessageChannel channel, Message msg, String[] a) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor("OrbitMines Discord Commands");
        builder.setColor(Color.BLUE.getAwtColor());

        for (Command command : getCommands()) {
            if (!(command instanceof DiscordCommand)) continue;

            DiscordCommand cmd = (DiscordCommand) command;

            if (cmd.getRank() != null && cmd.getRank() instanceof StaffRank && !cmd.isEligible(member))
                continue;

            ArrayList<String> cmds = new ArrayList<>();
            cmd.getAlias().forEach(al -> cmds.add("!" + al + " "));

            builder.addField(cmds.toString().replaceAll("[\\[\\]]", ""), cmd.getDescription(), false);
        }

        builder.setThumbnail(Image.ORBITMINES_ICON.getUrl());
        channel.sendMessageEmbeds(builder.build()).queue();
    }
}
