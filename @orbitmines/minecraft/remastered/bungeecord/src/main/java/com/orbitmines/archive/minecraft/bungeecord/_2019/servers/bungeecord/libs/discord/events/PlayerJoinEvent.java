package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.discord.events;

import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.DiscordUser;
import com.orbitmines.archive.minecraft._2019.libs.discord.CustomChannel;
import com.orbitmines.archive.minecraft._2019.libs.discord.CustomEmote;
import com.orbitmines.archive.minecraft._2019.libs.discord.OMDiscordBot;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class PlayerJoinEvent extends ListenerAdapter {

    private TextChannel channel;
    private OMDiscordBot bot;

    public PlayerJoinEvent(OMDiscordBot bot, TextChannel channel) {
        this.bot = bot;
        this.channel = channel;
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        channel.sendMessage(event.getMember().getAsMention() + "  just joined the *official OrbitMines Discord server*!" +
                "\nWelcome to the "+ bot.getEmote(CustomEmote.orbitmines).getAsMention() + "OrbitMines community, we hope you enjoy your stay here!" +
                "\nYou can find all the important information in " + bot.getTextChannel(CustomChannel.WELCOME).getAsMention() + " and if you have any questions, make sure to ask them!").queue();

        DiscordUser user = DiscordUser.findBy(DiscordUser.class, DiscordUser.column.DISCORD_USER_ID.is(event.getUser().getIdLong()));
        if (user == null)
            user = new DiscordUser(null, event.getUser().getIdLong());

        user.updateDiscordRanks(bot);
    }
}
