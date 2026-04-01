package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.events;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Environment;
import com.orbitmines.archive.minecraft._2019.libs.Image;
import com.orbitmines.archive.minecraft._2019.libs.discord.CustomChannel;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.discord.SpigotDiscordBot;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.LocationUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import java.util.Date;

public class SignLogEvent<S extends OMServer<S, P>, P extends OMPlayer<S, P>> implements Listener {

    private final S server;

    public SignLogEvent(S server) {
        this.server = server;
    }

    @EventHandler
    public void onSignPlace(SignChangeEvent event) {
        P player = server.getPlayer(event.getPlayer());

        SpigotDiscordBot bot = server.getDiscordBot();
        Date date = DateUtils.now();

        bot.withPlayerEmote(player.getUUID(), player.getRawName(), false, emote -> {
            TextChannel channel = bot.getTextChannel(CustomChannel.SIGN_LOG);

            channel.sendMessage(bot.getPlayerDisplay(player, emote, player.getRawName()) + " has placed a sign.").queue();

            EmbedBuilder builder = new EmbedBuilder();
            builder.setAuthor("SIGN PLACEMENT");
            builder.setDescription(LocationUtils.humanFriendlyString(event.getBlock().getLocation()) + " (world: " + event.getBlock().getWorld().getName() + ")");
            builder.setColor(Color.ORANGE.getAwtColor());

            builder.addField("Player", player.getRawName(), true);
            builder.addField("UUID", player.getUUID().toString(), true);
            builder.addField("1.", event.getLine(0), false);
            builder.addField("2.", event.getLine(1), false);
            builder.addField("3.", event.getLine(2), false);
            builder.addField("4.", event.getLine(3), false);

            builder.setThumbnail(Image.SIGN.getUrl());

            builder.setFooter(server.getType().getName() + " / " + Environment.get().toString() + " / " + DateUtils.format(date, DateUtils.DATE_TIME_FORMAT), Image.icon(server.getType()).getUrl());

            channel.sendMessage(builder.build()).queue();
        });
    }
}
