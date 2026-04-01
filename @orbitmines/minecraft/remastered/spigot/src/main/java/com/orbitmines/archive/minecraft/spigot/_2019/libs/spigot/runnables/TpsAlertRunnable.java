package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.runnables;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Environment;
import com.orbitmines.archive.minecraft._2019.libs.Image;
import com.orbitmines.archive.minecraft._2019.libs.discord.CustomChannel;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import com.orbitmines.archive.minecraft._2019.utils.discord.DiscordBot;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.Interval;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.SpigotRunnable;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.TimeUnit;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.minecraft.server.MinecraftServer;

public class TpsAlertRunnable<S extends OMServer<S, ?>> extends SpigotRunnable<S> {

    private final S server;

    public TpsAlertRunnable(S server) {
        super(server, Interval.of(TimeUnit.MINUTE, 1));

        this.server = server;
    }

    @Override
    public void run() {
        double[] recentTps = MinecraftServer.getServer().recentTps;

        if (recentTps[0] > Environment.get("OM_TPS_THRESHOLD", 19.50))
            return;

        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor("Low TPS: " + humanReadableTps(recentTps[0]));
        builder.setColor(Color.RED.getAwtColor());

        builder.addField("1m", humanReadableTps(recentTps[0]) + " TPS", true);
        builder.addField("5m", humanReadableTps(recentTps[1]) + " TPS", true);
        builder.addField("15m", humanReadableTps(recentTps[2]) + " TPS", true);

        builder.setFooter(server.getType().getName() + " / " + Environment.get().toString() + " / " + DateUtils.format(DateUtils.now(), DateUtils.DATE_TIME_FORMAT), Image.icon(server.getType()).getUrl());

        DiscordBot bot = server.getDiscordBot();
        TextChannel channel = bot.getTextChannel(CustomChannel.TPS_ALERT);

        channel.sendMessage(Environment.getEveryoneOrDev(bot) + " Low TPS in " + server.getType().getName() + ": " + humanReadableTps(recentTps[0])).queue();
        channel.sendMessage(builder.build()).queue();
    }

    private String humanReadableTps(double tps) {
        return String.format("%.2f", tps);
    }
}
