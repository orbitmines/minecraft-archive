package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.discord.commands;


import com.orbitmines.archive.minecraft._2019.libs.Image;
import com.orbitmines.archive.minecraft._2019.libs.database.models.PlayerModel;
import com.orbitmines.archive.minecraft._2019.libs.database.models.TimePlayed;
import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.DiscordUser;
import com.orbitmines.archive.minecraft._2019.libs.database.models.vote.MonthlyVotes;
import com.orbitmines.archive.minecraft._2019.libs.discord.CustomEmote;
import com.orbitmines.archive.minecraft._2019.libs.discord.DiscordCommand;
import com.orbitmines.archive.minecraft._2019.libs.discord.OMDiscordBot;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;
import com.orbitmines.archive.minecraft._2019.utils.TimeUtils;
import com.orbitmines.archive.minecraft._2019.utils.language.Language;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.time.LocalDate;
import java.util.List;


public class StatsCommand extends DiscordCommand {

    public StatsCommand(OMDiscordBot bot, String... alias) {
        super(bot, "Display player stats", alias);
    }

    @Override
    public void onDispatch(MessageReceivedEvent event, User user, Member member, MessageChannel channel, Message msg, String[] args) {
        DiscordUser dUser = DiscordUser.findBy(DiscordUser.class, DiscordUser.column.DISCORD_USER_ID.is(user.getId()));
        if (dUser == null) {
            channel.sendMessage(member.getAsMention() + " Your discord isn't linked yet. Please use `!discordlink <in game name>`").queue();
            return;
        }

        PlayerModel player = PlayerModel.findBy(PlayerModel.class, PlayerModel.column.UUID.is(dUser.getUuid()));
        if (player == null) {
            channel.sendMessage(member.getAsMention() + " An error occurred while looking up your stats").queue();
            return;
        }

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(player.getRank().getPrefixColor().getAwtColor());

        builder.addField((!player.getVipRank().isNone() ? bot.getEmote(CustomEmote.from(player.getVipRank())).getAsMention():"") + "**" + player.getName() + "**", "", false);

        if (player.getNickName() != null) {
            builder.addField("Nickname", player.getNickName(), true);
        }


        StringBuilder ranks = new StringBuilder();
        if (!player.getStaffRank().isNone())
            ranks.append(bot.getEmote(CustomEmote.orbitmines).getAsMention() + player.getStaffRank().getName() + (!player.getVipRank().isNone() ? "\n" : ""));

        if (player.getVipRank() != null || player.getStaffRank().isNone())
            ranks.append(bot.getEmote(CustomEmote.from(player.getVipRank())).getAsMention() + player.getVipRank().getName());

        builder.addField(!player.getVipRank().isNone() && !player.getStaffRank().isNone() ? "Ranks": "Rank", ranks.toString(), true);

        builder.addField("Solars", NumberUtils.locale(player.getSolars()), true);
        builder.addField("Prisms", NumberUtils.locale(player.getPrisms()), true);


        List<TimePlayed> playtimes = TimePlayed.getAll(TimePlayed.class, TimePlayed.column.UUID.is(dUser.getUuid()));
        long timePlayed = 0;
        if (playtimes != null) {
            for (TimePlayed playtime : playtimes) {
                timePlayed += playtime.getSeconds();
            }
        }
        builder.addField("Playing time", TimeUtils.humanFriendlyTimeUnit(Language.ENGLISH, timePlayed*1000, TimeUtils.Unit.HOURS), true);
        builder.addField("Member since", DateUtils.format(player.getFirstLoginAt(), DateUtils.DATE_TIME_FORMAT), true);


        List<MonthlyVotes> votes = MonthlyVotes.getAll(MonthlyVotes.class, MonthlyVotes.column.UUID.is(dUser.getUuid()));
        int totalVotes = 0;
        int monthVotes = 0;
        LocalDate date = LocalDate.now();
        if (votes != null) {
            for(MonthlyVotes vote : votes) {
                totalVotes += vote.getVotes();
                if (vote.getMonth() == date.getMonth() && vote.getYear() == date.getYear()) monthVotes = vote.getVotes();
            }
        }
        builder.addField("Votes in " + DateUtils.humanFriendlyMonth(date.getMonth()) + " " + date.getYear(), NumberUtils.locale(monthVotes) + " votes", true);
        builder.addField("Total votes", NumberUtils.locale(totalVotes) + " votes", true);


        builder.setThumbnail(Image.ORBITMINES_ICON.getUrl()); //TODO Add player image
        channel.sendMessageEmbeds(builder.build()).queue();

    }
}
