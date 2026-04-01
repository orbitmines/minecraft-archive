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

    WELCOME("MAIN CHATS", "welcome", ChannelType.TEXT),
    ANNOUNCEMENTS("MAIN CHATS", "announcements", ChannelType.TEXT),
    PATCH_NOTES("MAIN CHATS", "patch_notes", ChannelType.TEXT),
    COMMUNITY("MAIN CHATS", "community", ChannelType.TEXT),

    NEW_PLAYERS("SIDE CHATS", "new_players", ChannelType.TEXT),
    NAME_CHANGE("SIDE CHATS", "name_change", ChannelType.TEXT),
    DONATIONS("SIDE CHATS", "donations", ChannelType.TEXT),
    VOTES("SIDE CHATS", "votes", ChannelType.TEXT),
    PUNISHMENTS("SIDE CHATS", "punishments", ChannelType.TEXT),

    BOT_CHANNEL("VOICE CHANNELS", "bot_channel", ChannelType.TEXT),

    STAFF("STAFF", "staff", ChannelType.TEXT),
    BUILDER("STAFF", "builder", ChannelType.TEXT),
    ERRORS("STAFF", "errors", ChannelType.TEXT),
    REPORTS("STAFF", "reports", ChannelType.TEXT),
    TPS_ALERT("STAFF", "tps_alert", ChannelType.TEXT),

    ENTITY_CLEAR_LOG("LOGS", "entity_clear_log", ChannelType.TEXT),
    COMMAND_LOG("LOGS", "command_log", ChannelType.TEXT),
    SIGN_LOG("LOGS", "sign_log", ChannelType.TEXT),
    LOOT_LOG("LOGS", "loot_log", ChannelType.TEXT),
    DISCORD_LINK_LOG("LOGS", "discord_link_log", ChannelType.TEXT),
    PRIVATE_SERVER_LOG("LOGS", "private_server_log", ChannelType.TEXT);

    @Getter private final String categoryName;
    @Getter private final String name;
    @Getter private final ChannelType channelType;

}
