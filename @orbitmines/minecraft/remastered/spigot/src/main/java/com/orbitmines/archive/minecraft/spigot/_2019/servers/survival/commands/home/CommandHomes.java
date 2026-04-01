package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.commands.home;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.database.models.PlayerModel;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.PlayerModelArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor1;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalHome;
import com.orbitmines.archive.minecraft._2019.libs.utils.Message;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.database.models.Home;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.text.TextBuilder;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;

import java.util.ArrayList;
import java.util.List;

public class CommandHomes extends Command<Survival, SurvivalPlayer> {

    public CommandHomes(Survival plugin) {
        super(plugin, Server.SURVIVAL, "homes");

        executes((Executor0<Survival, SurvivalPlayer>) (player) -> {
            displayHomes(player, player.getHomes(false), player.getHomesAllowed());
        }).withArg(
            new PlayerModelArgument<Survival, SurvivalPlayer>(true).executes((Executor1<Survival, SurvivalPlayer,
                PlayerModel, PlayerModelArgument<Survival, SurvivalPlayer>>
            ) (player, model) -> {
                List<Home> homes = new ArrayList<>();
                for (SurvivalHome home : SurvivalHome.getAll(SurvivalHome.class, SurvivalHome.column.UUID.is(model.getUUID()))) {
                    homes.add(new Home(home));
                }

                displayHomes(player, homes, -1);
            })
        );
    }

    @Override
    public String getDescription(SurvivalPlayer player) {
        return player.translate("survival", "player.command.homes.description");
    }

    private void displayHomes(SurvivalPlayer player, List<Home> homes, int allowed) {
        if (homes.size() == 0) {
            player.sendMessage("Home", Color.RED, "survival", "player.command.home.none");
            return;
        }

        TextBuilder<SurvivalPlayer> builder = new TextBuilder<>();
        builder.add(Color.SILVER, p -> Message.format("Home", Color.BLUE, "§7" + player.translate("survival", "player.command.homes.your_homes") + " (§6" + homes.size() + "§7 / " + allowed + "): "));

        for (int i = 0; i < homes.size(); i++) {
            if (i != 0)
                builder.add(Color.SILVER, p -> ", ");

            Home home = homes.get(i);
            builder.add(Color.ORANGE, p -> home.getName())
                .click(ClickEvent.Action.RUN_COMMAND, p -> "/home " + home.getName())
                .hover(HoverEvent.Action.SHOW_TEXT, p -> "§7" + player.translate("survival", "player.command.homes.hover", "§6" + home.getName() + "§7"));
        }

        builder.send(player);
    }
}
