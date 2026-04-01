package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.ServerList;
import com.orbitmines.archive.minecraft._2019.libs.database.models.vote.LastVote;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.leaderboard.cmd.DefaultCommandLeaderBoard;
import com.orbitmines.archive.minecraft._2019.utils.TimeUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.text.TextBuilder;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;

@Deprecated
public class CommandVote<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends Command<S, P> implements CommandVoteHelper {

    public CommandVote(S plugin) {
        super(plugin, "vote");

        executes((Executor0<S, P>) player -> {
            player.sendMessage("");
            player.sendMessage(" §8§lOrbit§7§lMines §9§lVote Links");

            int count = 1;
            for (ServerList serverList : ServerList.ACTIVE) {
                LastVote vote = player.getLastVote(serverList, false);
                boolean canVote = vote.canVote();
                Color color = canVote ? Color.BLUE : Color.RED;

                TextBuilder<P> builder = new TextBuilder<>();

                int linkNumber = count;
                builder.
                    add(Color.SILVER, p -> "  - ")
                    .add(color, p -> "Vote Link " + linkNumber).bold()
                        .click(ClickEvent.Action.OPEN_URL, p -> serverList.getUrl())
                        .hover(HoverEvent.Action.SHOW_TEXT, p -> "§7" + (canVote ?
                            player.translate("spigot", "player.command.vote.hover.open_vote_link", "§9Vote Link " + linkNumber + "§7") :
                            player.translate("spigot", "player.command.vote.hover.vote_in", "§9" + TimeUtils.humanFriendlyTimer(player.getLanguage(), vote.getMillisLeft()))));

                builder.send(player);

                count++;
            }

            sendVoteRewardMessage(player);
        });
    }

    @Override
    public DefaultCommandLeaderBoard getLeaderBoardCommand() {
        return CommandTopVoters.INSTANCE;
    }

    @Override
    public String getDescription(P player) {
        return player.translate("spigot", "player.command.vote.description");
    }
}
