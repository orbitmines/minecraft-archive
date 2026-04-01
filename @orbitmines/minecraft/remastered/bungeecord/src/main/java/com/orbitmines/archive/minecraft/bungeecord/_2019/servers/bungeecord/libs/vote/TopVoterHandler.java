package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.vote;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.achievement.TopVoterReward;
import com.orbitmines.archive.minecraft._2019.libs.database.models.loot.LootItem;
import com.orbitmines.archive.minecraft._2019.libs.database.models.vote.MonthlyVotes;
import com.orbitmines.archive.minecraft._2019.libs.discord.CustomChannel;
import com.orbitmines.archive.minecraft._2019.libs.discord.CustomRole;
import com.orbitmines.archive.minecraft._2019.libs.loot.Rarity;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.player.OfflinePlayer;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.Bungeecord;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.discord.BungeeDiscordBot;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import com.orbitmines.archive.minecraft._2019.utils.EnumUtils;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;
import com.orbitmines.archive.minecraft._2019.utils.database.DatabaseManager;
import com.orbitmines.archive.minecraft._2019.utils.database.MySQLDatabase;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Selectable;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.mysql.MySQLQueryBuilder;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.operators.mysql.Order;
import com.orbitmines.archive.minecraft._2019.utils.state.StateProvider;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.math.BigDecimal;
import java.time.Month;
import java.util.ArrayList;
import java.util.LinkedHashMap;

@Deprecated
public class TopVoterHandler {

    private final Bungeecord bungee;

    public TopVoterHandler(Bungeecord bungee) {
        this.bungee = bungee;
    }

    public void process() {
        setLastVoteMonth(DateUtils.humanFriendlyMonth(), DateUtils.getYear());

        Month prevMonth = DateUtils.getPrevMonth(1);
        int prevYear = prevMonth == Month.DECEMBER ? DateUtils.getYear() -1 : DateUtils.getYear();

        ArrayList<MonthlyVotes> topVotes = MonthlyVotes.getAll(MonthlyVotes.class,
            MonthlyVotes.column.MONTH.is(prevMonth),
            MonthlyVotes.column.YEAR.is(prevYear),
            MonthlyVotes.column.VOTES.ordered(Order.DESC),
            MonthlyVotes.limitedTo(3)
        );

        for (int i = 0; i < topVotes.size(); i++) {
            MonthlyVotes monthlyVotes = topVotes.get(i);
            TopVoterReward reward = TopVoterReward.values()[i];
            
            LootItem item = new LootItem(monthlyVotes.getUuid(), reward.getType(), Rarity.LEGENDARY, reward.getCount(), "§9§l§o#" + (i + 1) + " Voter (" + DateUtils.humanFriendlyMonth(prevMonth) + " " + prevYear + ")");
            item.insert();
        }

        /*
                Community Goal
         */
        Selectable totalVotesSelectable = () -> "SUM(`monthly_votes`.`votes`) AS total_votes";
        Selectable voterCountSelectable = () -> "COUNT(*) AS count";
        MySQLDatabase database = DatabaseManager.getInstance().getDefault();
        MySQLQueryBuilder builder = new MySQLQueryBuilder(MonthlyVotes.TABLE).
            where(MonthlyVotes.column.MONTH.getColumn(), prevMonth.toString()).
            where(MonthlyVotes.column.YEAR.getColumn(), prevYear + "");

        LinkedHashMap<Selectable, Object> result = database.getEntry(builder, totalVotesSelectable, voterCountSelectable);

        if (result == null) {
            bungee.getLogger().info("No votes found.");
            return;
        }

        int totalVotes = ((BigDecimal) result.get(totalVotesSelectable)).intValue();
        int voterCount;
        try {
            voterCount = ((BigDecimal) result.get(voterCountSelectable)).intValue();
        } catch(ClassCastException ex) {
            voterCount = ((Long) result.get(voterCountSelectable)).intValue();
        }

        boolean communityGoal = false;

        if (totalVotes >= TopVoterReward.COMMUNITY_GOAL) {
            for (MonthlyVotes monthlyVotes : MonthlyVotes.getAll(MonthlyVotes.class, MonthlyVotes.column.MONTH.is(prevMonth), MonthlyVotes.column.YEAR.is(prevYear))) {
                int votes = monthlyVotes.getVotes();

                if (votes == 0)
                    continue;

                String name = "§a§l§oCommunity Goal " + DateUtils.humanFriendlyMonth(prevMonth) + " " + prevYear + " (" + votes + " " + (votes == 1 ? "Vote" : "Votes") + ")";
                int solars = votes * TopVoterReward.COMMUNITY_GOAL_SOLARS_PER_VOTE;

                new LootItem(monthlyVotes.getUuid(), LootItem.Type.SOLARS, Rarity.RARE, solars, name).insert();
            }

            communityGoal = true;
        }

        announceTopVoters(prevMonth, prevYear, communityGoal, totalVotes, voterCount, topVotes);
    }

    private String toString(String month, int year) {
        return month + "-" + year;
    }

    private void setLastVoteMonth(String month, int year) {
        StateProvider.getInstance().setString("bungee:last_vote_month", toString(month, year));
    }

    private void announceTopVoters(Month month, int year, boolean communityGoal, int totalVotes, int voterCount, ArrayList<MonthlyVotes> topVotes) {
        if (topVotes.size() == 0)
            return;

        BungeeDiscordBot bot = bungee.getDiscordBot();
        TextChannel channel = bot.getTextChannel(CustomChannel.ANNOUNCEMENTS);

        channel.sendMessage("@everyone **TOP VOTERS OF " + month.toString() + " " + year + "**").queue();

        String message = "Another month of voting is concluded. We received a total of **" + NumberUtils.locale(totalVotes) + " Votes** from **" + NumberUtils.locale(voterCount) + " Players** this month.\n";

        if (communityGoal)
            message += " The **Community Goal** has been achieved (a total of " + NumberUtils.locale(TopVoterReward.COMMUNITY_GOAL) + " Votes). All **" + NumberUtils.locale(voterCount) + " Players** have received **" + TopVoterReward.COMMUNITY_GOAL_SOLARS_PER_VOTE + " Solars per Vote** (**" + NumberUtils.locale(totalVotes * TopVoterReward.COMMUNITY_GOAL_SOLARS_PER_VOTE) + " Solars** in total).\n";

        message += "_We would like to thank everyone on behalf of the **OrbitMines Team** for voting, not only does this give you rewards in game, it also helps our server immensely!_\n";

        message += "The Top Voters of this month are:";

        channel.sendMessage(message).queue();

        sendTopVoter(bot, channel, topVotes, 0, CustomRole.TOP_VOTER_1, TopVoterReward.FIRST_PLACE);
    }

    private void sendTopVoter(BungeeDiscordBot bot, TextChannel channel, ArrayList<MonthlyVotes> topVoters, int index, CustomRole role, TopVoterReward topVoterReward) {
        if (topVoters.size() <= index || index >= 3)
            return;

        MonthlyVotes monthlyVotes = topVoters.get(index);

        OfflinePlayer player = OfflinePlayer.get(monthlyVotes.getUuid());

        bot.withPlayerEmote(player.getUUID(), player.getName(Name.RAW), false, emote -> {
            channel.sendMessage(bot.getRole(role).getAsMention() + " " + bot.getPlayerDisplay(player, emote, player.getName(Name.RAW)) + " with **" + monthlyVotes.getVotes() + " Votes** (Received a **" + topVoterReward.getCount() + "\u20AC OrbitMines Shop Voucher**)").queue();

            sendTopVoter(bot, channel, topVoters, index + 1, EnumUtils.next(CustomRole.class, roles(), role), EnumUtils.next(TopVoterReward.class, rewards(), topVoterReward));
        });
    }

    private CustomRole[] roles() {
        return new CustomRole[] {
            CustomRole.TOP_VOTER_1,
            CustomRole.TOP_VOTER_2,
            CustomRole.TOP_VOTER_3
        };
    }

    private TopVoterReward[] rewards() {
        return new TopVoterReward[] {
            TopVoterReward.FIRST_PLACE,
            TopVoterReward.SECOND_PLACE,
            TopVoterReward.THIRD_PLACE
        };
    }
}
