package com.orbitmines.archive.minecraft._2019.libs.discord;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.utils.discord.DiscordChannel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.dv8tion.jda.api.entities.channel.ChannelType;

@AllArgsConstructor
public enum CustomChannel implements DiscordChannel {

    WELCOME(null, "welcome", ChannelType.TEXT),
    ANNOUNCEMENTS(null, "announcements", ChannelType.TEXT),

    PATCH_NOTES("General", "patch_notes", ChannelType.TEXT),
    COMMUNITY("General", "general", ChannelType.TEXT),
    BOT_CHANNEL("General", "bot_channel", ChannelType.TEXT),

    NEW_PLAYERS("Miscellaneous", "new_players", ChannelType.TEXT),
    NAME_CHANGE("Miscellaneous", "name_change", ChannelType.TEXT),
    DONATIONS("Miscellaneous", "donations", ChannelType.TEXT),
    VOTES("Miscellaneous", "votes", ChannelType.TEXT),
    PUNISHMENTS("Miscellaneous", "punishments", ChannelType.TEXT),

    STAFF("Staff", "staff", ChannelType.TEXT),
    BUILDER("Staff", "builder", ChannelType.TEXT),
    ERRORS("Staff", "errors", ChannelType.TEXT),
    REPORTS("Staff", "reports", ChannelType.TEXT),
    TPS_ALERT("Staff", "tps_alert", ChannelType.TEXT),

    ENTITY_CLEAR_LOG("Logs", "entity_clear_log", ChannelType.TEXT),
    COMMAND_LOG("Logs", "command_log", ChannelType.TEXT),
    SIGN_LOG("Logs", "sign_log", ChannelType.TEXT),
    LOOT_LOG("Logs", "loot_log", ChannelType.TEXT),
    DISCORD_LINK_LOG("Logs", "discord_link_log", ChannelType.TEXT),
    PRIVATE_SERVER_LOG("Logs", "private_server_log", ChannelType.TEXT);

    @Getter private final String categoryName;
    @Getter private final String name;
    @Getter private final ChannelType channelType;

}
