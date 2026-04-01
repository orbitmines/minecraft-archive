package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.achievement.PersonalAchievement;
import com.orbitmines.archive.minecraft._2019.libs.achievement.TopVoterReward;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.leaderboard.cmd.DefaultCommandLeaderBoard;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.text.TextBuilder;
import net.md_5.bungee.api.chat.HoverEvent;

@Deprecated
public interface CommandVoteHelper {

    DefaultCommandLeaderBoard getLeaderBoardCommand();

    default void sendVoteRewardMessage(OMPlayer omp) {
        omp.sendMessage(" §a§lRewards");

        TopVoterReward[] values = TopVoterReward.values();
        for (int i = 0; i < values.length; i++) {
            TopVoterReward reward = values[i];

            Color color;
            switch (i) {
                case 0:
                    color = Color.ORANGE;
                    break;
                case 1:
                    color = Color.SILVER;
                    break;
                case 2:
                    color = Color.RED;
                    break;
                default:
                    color = Color.GRAY;
                    break;
            }

            int place = i + 1;
            TextBuilder<OMPlayer> builder = new TextBuilder<>();
            builder.add(color, p -> "  " + place + ". ").bold()
                    .add(Color.TEAL, p -> reward.getCount() + "\u20AC").bold().space()
                    .add(Color.SILVER, p -> "(OrbitMines Shop Voucher)")
                        .hover(HoverEvent.Action.SHOW_TEXT, p -> "§3§l" + reward.getCount() + "\u20AC\n" + "§7§oOrbitMines Shop Voucher");

            builder.send(omp);
        }

        {
            /* Community Goal */
            int totalCount = getLeaderBoardCommand().getTotalCount();

            String description = "§a§lCommunity Goal\n" +
                (totalCount > TopVoterReward.COMMUNITY_GOAL ? "§d§l" + omp.translate("spigot", "player.command.vote.community_goal.achieved") : "§c§l" + omp.translate("spigot", "player.command.vote.community_goal.not_achieved")) + "\n" +
                "\n";

            for (String s : omp.getLanguage().getStringArray("spigot", "player.command.vote.community_goal.hover", "§e§l" + TopVoterReward.COMMUNITY_GOAL_SOLARS_PER_VOTE + " Solars§7")) {
                description += "§7" + s + "\n";
            }

            for (String s : omp.getLanguage().getStringArray("spigot", "player.command.vote.community_goal.hover.footer", (100 * TopVoterReward.COMMUNITY_GOAL_SOLARS_PER_VOTE) + " Solars")) {
                description += "\n§7§o" + s;
            }

            TextBuilder<OMPlayer> builder = new TextBuilder<>();

            String hover = description;
            builder.
                add(Color.LIME, p -> " Community Goal\n").bold().
                    hover(HoverEvent.Action.SHOW_TEXT, p -> hover)
                .add(Color.SILVER, p -> "  - ")
                .add(Color.BLUE, p -> "§9§l" + NumberUtils.locale(totalCount) + " §7§l/ " + NumberUtils.locale(TopVoterReward.COMMUNITY_GOAL) + " Votes")
                    .hover(HoverEvent.Action.SHOW_TEXT, p -> hover);

            builder.send(omp);
        }

        {
            /* Personal Achievements */

            int votes = omp.getVotesThisMonth(false).getVotes();
            PersonalAchievement current = null;
            for (int i = 0; i < PersonalAchievement.MONTHLY_ACHIEVEMENT_VOTES.length; i++) {
                if (i == 0 || votes > PersonalAchievement.MONTHLY_ACHIEVEMENT_VOTES[i - 1].getVotes())
                    current = PersonalAchievement.MONTHLY_ACHIEVEMENT_VOTES[i];
            }

            TextBuilder<OMPlayer> builder = new TextBuilder<>();
            StringBuilder description = new StringBuilder("§a§l" + omp.translate("spigot", "player.command.vote.personal_achievements.monthly_achievement"));

            for (PersonalAchievement achievement : PersonalAchievement.MONTHLY_ACHIEVEMENT_VOTES) {
                description.append("\n\n").append("§a§lTier ").append(NumberUtils.toRoman(achievement.getTier())).append(" §7» ");

                if (votes > achievement.getVotes())
                    description.append("§d§l" + omp.translate("spigot", "player.command.vote.personal_achievements.achieved"));
                else
                    description.append("§9§l").append(votes).append(" §7§l/ ").append(achievement.getVotes());

                if (achievement.getPrisms() != 0)
                    description.append("\n §7- §9§l").append(NumberUtils.locale(achievement.getPrisms())).append(" Prisms");
                if (achievement.getSolars() != 0)
                    description.append("\n §7- §e§l").append(NumberUtils.locale(achievement.getSolars())).append(" Solars");
            }

            PersonalAchievement c = current;
            builder
                .add(Color.LIME, p -> " " + p.translate("spigot", "player.command.vote.personal_achievements.monthly_achievement") + " " + NumberUtils.toRoman(c.getTier()) + "\n").bold()
                    .hover(HoverEvent.Action.SHOW_TEXT, p -> description.toString())
                .add(Color.SILVER, p -> "  - ")
                .add(Color.BLUE, p -> "§9§l" + votes + " §7§l/ " + c.getVotes() + " Votes")
                    .hover(HoverEvent.Action.SHOW_TEXT, p -> description.toString());

            builder.send(omp);
        }
    }
}
