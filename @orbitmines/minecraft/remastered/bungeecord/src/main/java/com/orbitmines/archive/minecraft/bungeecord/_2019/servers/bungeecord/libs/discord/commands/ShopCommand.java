package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.discord.commands;

import com.orbitmines.archive.minecraft._2019.libs.Environment;
import com.orbitmines.archive.minecraft._2019.libs.discord.DiscordCommand;
import com.orbitmines.archive.minecraft._2019.libs.discord.OMDiscordBot;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ShopCommand extends DiscordCommand {

    public ShopCommand(OMDiscordBot bot, String... alias) {
        super(bot, "Display the OrbitMines shop url", alias);
    }

    @Override
    public void onDispatch(MessageReceivedEvent event, User user, Member member, MessageChannel channel, Message msg, String[] args) {
        channel.sendMessage(member.getAsMention() + " **" + Environment.get("OM_SHOP_LINK", "https://OrbitMines.buycraft.net") + "**").queue();
    }
}
