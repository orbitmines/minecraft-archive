package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.database.models.punishment.Report;
import com.orbitmines.archive.minecraft._2019.libs.discord.CustomChannel;
import com.orbitmines.archive.minecraft._2019.libs.jedis.OnlinePlayer;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.GlobalPlayerArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.MessageArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor2;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.discord.SpigotDiscordBot;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class CommandReport<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends Command<S, P> {

    public CommandReport(S plugin) {
        super(plugin, "report");

        withArg(
            new GlobalPlayerArgument<S, P>(false).
                withArg(
                    new MessageArgument<S, P>().executes((Executor2<S, P,
                        OnlinePlayer, GlobalPlayerArgument<S, P>,
                        String, MessageArgument<S, P>>
                    ) (player, target, reason) -> {
                        Report report = new Report(plugin.getType(), target.getUUID(), player.getUUID(), reason);
                        report.insert();

                        player.sendMessage("Report", Color.SUCCESS, "spigot", "player.command.report.message", target.getName(Name.RAW_COLORED) + "§7", "§c" + reason + "§7");

                        sendToDiscord(report, player, target);
                    })
                )
        );
    }

    @Override
    public String getDescription(P player) {
        return player.translate("spigot", "player.command.report.description");
    }

    private void sendToDiscord(Report report, OMPlayer player, OnlinePlayer target) {
        SpigotDiscordBot bot = getPlugin().getDiscordBot();
        TextChannel channel = bot.getTextChannel(CustomChannel.REPORTS);

        bot.withPlayerEmote(player.getUUID(), player.getRawName(), false, playerEmote -> {
            bot.withPlayerEmote(target.getUUID(), target.getRawName(), false, targetEmote -> {
                channel.sendMessage("@everyone " + bot.getPlayerDisplay(player, playerEmote, player.getRawName()) + " has reported " + bot.getPlayerDisplay(target, targetEmote, target.getRawName()) + "!").queue();

                EmbedBuilder builder = new EmbedBuilder();
                builder.setAuthor("REPORT");
                builder.setDescription("");
                builder.setColor(Color.RED.getAwtColor());

                builder.addField("Id", report.getId() + "", true);
                builder.addField("Player", target.getRawName(), true);
                builder.addField("Server", this.getPlugin().getType().getName(), true);
                builder.addField("Date", DateUtils.format(report.getReportedAt(), DateUtils.DATE_TIME_FORMAT), true);
                builder.addField("Reported By", player.getRawName(), true);
                builder.addField("Reason", report.getReason(), true);

                //TODO FIX BODY_3D
//                builder.setThumbnail(SkinLibrary.getSkinUrl(SkinLibrary.Type.BODY_3D, player.getUUID()));

                channel.sendMessage(builder.build()).queue();
            });
        });
    }
}
