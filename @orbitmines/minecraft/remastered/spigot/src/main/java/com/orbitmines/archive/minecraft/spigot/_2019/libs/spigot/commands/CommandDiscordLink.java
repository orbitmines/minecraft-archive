package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.DiscordUser;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.achievements.EmptyAchievementHandler;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.achievements.servers.HubAchievement;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.DiscordMemberArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor1;
import net.dv8tion.jda.api.entities.Member;

public class CommandDiscordLink<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends Command<S, P> {

    public CommandDiscordLink(S plugin) {
        super(plugin, "discordlink", "link");

        if (plugin.getDiscordBot() == null) {
            executes((Executor0<S, P>) (player) -> {
                player.sendMessage("Discord", Color.ERROR, "spigot", "discord.not_enabled");
            });
            return;
        }

        withArg(
            new DiscordMemberArgument<S, P>(plugin.getDiscordBot(), "name#id").executes((Executor1<S, P,
                Member, DiscordMemberArgument<S, P>>
            ) (player, member) -> {
                DiscordUser.Linker linker = new DiscordUser.Linker(plugin.getDiscordBot());

                DiscordUser user = linker.link(player, member);
                switch (user.getResult()) {

                    case SETTING_UP:
                        player.sendMessage("Discord", Color.ERROR, "spigot", "player.command.discordlink.setting_up", "§9!discordlink " + player.getRawName() + "§7");
                        break;
                    case ALREADY_LINKED:
                        player.sendMessage("Discord", Color.ERROR, "spigot", "player.command.discordlink.already_linked");
                        break;
                    case SETUP_COMPLETE:
                        player.sendMessage("Discord", Color.INFO, "spigot", "player.command.discordlink.completed");

                        EmptyAchievementHandler handler = (EmptyAchievementHandler) HubAchievement.DISCORD_LINK.getHandler();
                        handler.complete(player, true);

                        player.reloadDiscordUser();
                        break;
                }
            })
        );
    }

    @Override
    public String getDescription(P player) {
        return player.translate("spigot", "player.command.discordlink.description");
    }
}
