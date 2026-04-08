package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.PlayerArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor1;
import org.bukkit.Sound;

public class CommandTeleportAccept<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends Command<S, P> {

    public CommandTeleportAccept(S plugin) {
        this(plugin, "tpaccept");
    }

    public CommandTeleportAccept(S plugin, String commandName) {
        super(plugin, commandName);

        withArg(
            new PlayerArgument<S, P>(plugin, false).executes((Executor1<S, P,
                P, PlayerArgument<S, P>>
            ) (player, target) -> {
                if (player.hasTpHereRequestFrom(target.getRawName())) {
                    beforeTeleport(player, target, true);

                    plugin.runSync(() -> {
                        player.teleport(target.getLocation());
                        player.playSound(Sound.ENTITY_ENDERMAN_TELEPORT);
                    });

                    player.sendMessage("Teleport", Color.LIME, "spigot", "player.command.accept.tphere.accepted", target.getName(Name.RAW_COLORED) + "§7");
                    target.sendMessage("Teleport", Color.LIME, "spigot", "player.command.accept.tp.accepted", player.getName(Name.RAW_COLORED) + "§7");

                    player.getTpHereRequests().remove(target.getRawName());
                } else if (player.hasTpRequestFrom(target.getRawName())) {
                    beforeTeleport(player, target, false);

                    plugin.runSync(() -> {
                        target.teleport(player.getLocation());
                        target.playSound(Sound.ENTITY_ENDERMAN_TELEPORT);
                    });

                    player.sendMessage("Teleport", Color.LIME, "spigot", "player.command.accept.tp.accepted", target.getName(Name.RAW_COLORED) + "§7");
                    target.sendMessage("Teleport", Color.LIME, "spigot", "player.command.accept.tphere.accepted", player.getName(Name.RAW_COLORED) + "§7");

                    player.getTpRequests().remove(target.getRawName());
                } else {
                    player.sendMessage("Teleport", Color.ERROR, "spigot", "player.command.accept.non_existent", target.getName(Name.RAW_COLORED) + "§7");
                }
            })
        );
    }

    /**
     * Hook called before the teleport happens. Override to add behavior like saving back location.
     * @param accepter the player who accepted the request
     * @param requester the player who sent the request
     * @param isTpHere true if this is a tphere request (accepter teleports to requester), false if tp request (requester teleports to accepter)
     */
    protected void beforeTeleport(P accepter, P requester, boolean isTpHere) {
    }

    @Override
    public String getDescription(P player) {
        return player.translate("spigot", "player.command.accept.description");
    }
}
