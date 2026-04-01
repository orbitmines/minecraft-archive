package com.orbitmines.archive.minecraft._2019.utils.discord;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.ChannelType;

public interface DiscordChannel {

    String getCategoryName();

    String getName();

    ChannelType getChannelType();

    default Category getCategory(DiscordBot bot) {
        return bot.getCategory(getCategoryName());
    }
}
