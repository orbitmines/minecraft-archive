package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.discord.commands;

import com.orbitmines.archive.minecraft._2019.libs.database.models.PlayerModel;
import com.orbitmines.archive.minecraft._2019.libs.database.models.vote.MonthlyVotes;
import com.orbitmines.archive.minecraft._2019.libs.discord.CustomEmote;
import com.orbitmines.archive.minecraft._2019.libs.discord.CustomRole;
import com.orbitmines.archive.minecraft._2019.libs.discord.DiscordCommand;
import com.orbitmines.archive.minecraft._2019.libs.discord.OMDiscordBot;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.operators.mysql.Order;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

public class TopvotersCommand extends DiscordCommand {

    public TopvotersCommand(OMDiscordBot bot, String... alias) {
        super(bot, "Display the top voters of this month", alias);
    }

    @Override
    public void onDispatch(MessageReceivedEvent event, User user, Member member, MessageChannel channel, Message msg, String[] args) {
        LocalDate date = LocalDate.now();
        List<MonthlyVotes> votes = MonthlyVotes.getAll(MonthlyVotes.class,
                MonthlyVotes.column.MONTH.is(date.getMonth()),
                MonthlyVotes.column.YEAR.is(date.getYear()),
                MonthlyVotes.column.VOTES.ordered(Order.DESC),
                MonthlyVotes.limitedTo(5)
        );

        new TopVoters(date, (TextChannel) channel, votes.iterator()).broadcast();
    }

    public class TopVoters {

        private LocalDate date;
        private TextChannel channel;
        private Iterator<MonthlyVotes> iterator;
        private int index = 0;
        private StringBuilder message;

        public TopVoters(LocalDate date, TextChannel channel, Iterator<MonthlyVotes> iterator) {
            this.date = date;
            this.channel = channel;
            this.iterator = iterator;
            this.message = new StringBuilder();
        }

        public void broadcast() {
            message.append("**TOP VOTERS OF " + date.getMonth().toString() + " " + date.getYear() + "**");
            next();
        }

        public void next() {
            if (!iterator.hasNext()) {
                channel.sendMessage(message.toString()).queue();
                return;
            }


            MonthlyVotes vote = iterator.next();
            PlayerModel player = PlayerModel.findBy(PlayerModel.class, PlayerModel.column.UUID.is(vote.getUuid()));

            bot.withPlayerEmote(player.getUUID(), player.getRawName(), false, emote -> {
                message.append("\n" +
                        bot.getRole(CustomRole.valueOf("TOP_VOTER_" + (index +1))).getAsMention() +  " " +
                        bot.getPlayerDisplay(player, emote, player.getRawName()) +
                        " with " +
                        "**" + vote.getVotes() + " Votes" + "**"
                );

                index++;
                next();
            });
        }
    }
}
