package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.database.models.PlayerModel;
import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.NicknameArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Namespace;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor1;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.pubsub.publishers.PlayerNicknameChangePublisher;
import com.orbitmines.archive.minecraft._2019.utils.state.StateProvider;

import java.util.Map;

public class CommandNickname<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends Command<S, P> {

    public CommandNickname(S plugin) {
        super(plugin, "nick");

        withArg(
            new NicknameArgument<S, P>().executes((Executor1<S, P,
                String, NicknameArgument<S, P>>
            ) (player, nickName) -> {
                setNickname(player, nickName);
            })
        ).namespace(
            new Namespace<S, P>("off", player -> player.translate("spigot", "player.command.namespace.nick.off"), (Executor0<S, P>
            ) (player) -> {
                clearNickName(player);
            })
        );

        requires(VipRank.GOLD);
    }

    @Override
    public String getDescription(P player) {
        return player.translate("spigot", "player.command.nick.description");
    }

    private void setNickname(P player, String nickName) {
        if (nickName.equalsIgnoreCase(player.getRawName())) {
            clearNickName(player);
            return;
        }

        player.setNickName(nickName);
        player.update(PlayerModel.column.NICK_NAME);

        player.sendMessage("Nick", Color.SUCCESS, "spigot", "player.command.nick.changed", "§a" + nickName + "§7");

        new PlayerNicknameChangePublisher().publish(
            player,
            nickName
        );

        /* Update Online Player */
        StateProvider.getInstance().setPlayerField(player.getUUID(), "nick_name", nickName);
    }

    private void clearNickName(P player) {
        player.setNickName(null);
        player.update(PlayerModel.column.NICK_NAME);

        player.sendMessage("Nick", Color.SUCCESS, "spigot", "player.command.nick.removed");

        new PlayerNicknameChangePublisher().publish(
            player,
            null
        );

        /* Update Online Player */
        Map<String, String> data = StateProvider.getInstance().getPlayerData(player.getUUID());
        if (data != null) {
            data.remove("nick_name");
            StateProvider.getInstance().setPlayerData(player.getUUID(), data);
        }
    }
}
