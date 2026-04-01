package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.database.models.PlayerModel;
import com.orbitmines.archive.minecraft._2019.libs.database.models.punishment.Punishment;
import com.orbitmines.archive.minecraft._2019.libs.jedis.OnlinePlayer;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.GlobalPlayerArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.MessageArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor2;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.pubsub.publishers.PlayerMessagePublisher;
import com.orbitmines.archive.minecraft._2019.utils.language.Language;

public class CommandMsg<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends Command<S, P> implements CommandMsgHelper {

    public CommandMsg(S plugin) {
        super(plugin, "msg", "message", "m", "tell", "t", "whisper", "w");

        withArg(
            new GlobalPlayerArgument<S, P>(false).
                withArg(
                    new MessageArgument<S, P>().executes((Executor2<S, P,
                        OnlinePlayer, GlobalPlayerArgument<S, P>,
                        String, MessageArgument<S, P>>
                    ) (player, target, message) -> {
                        if (Punishment.getActive(player.getUUID(), Punishment.Type.MUTE) != null) {
                            player.sendMessage("Mute", Color.ERROR, "spigot", "player.muted");
                            return;
                        }

                        if (!allowedBySettings(player, target))
                            return;

                        player.sendMessage("§7§l" + player.translate("spigot", "player.command.msg.you") + " §7» " + target.getName(Name.RAW_COLORED) + " §f§l" + message);

                        Language language = PlayerModel.findBy(PlayerModel.class, PlayerModel.column.UUID.is(target.getUUID())).getLanguage();

                        new PlayerMessagePublisher().publish(
                            target.getUUID(),
                            player.getName(Name.RAW_COLORED) + " §7» §7§l" + language.getString("spigot", "player.command.msg.you") + " §f§l" + message
                        );

                        setLastMessage(player.getUUID(), target.getUUID());
                    })
                )
        );
    }

    @Override
    public String getDescription(P player) {
        return player.translate("spigot", "player.command.msg.description");
    }
}
