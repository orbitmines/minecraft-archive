package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.MessageArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Namespace;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor1;
import com.orbitmines.archive.minecraft._2019.libs.utils.Message;
import com.orbitmines.archive.minecraft._2019.utils.state.StateProvider;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.text.TextBuilder;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;

public class CommandMotd<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends Command<S, P> {

    public CommandMotd(S plugin) {
        super(plugin, "motd");

        namespace(
            new Namespace<S, P>("view", player -> player.translate("spigot", "player.command.namespace.motd.view"), (Executor0<S, P>
            ) (player) -> {
                player.sendRawMessage("Motd", Color.INFO, "§lMessage of the day:");

                String motd = getMotd();
                displayLine(player, 1, getFirstLine(motd));
                displayLine(player, 2, getSecondLine(motd));
            })
        ).namespace(
            new Namespace<S, P>("1", player -> player.translate("spigot", "player.command.namespace.motd.1"))
                .withArg(
                    new MessageArgument<S, P>().executes((Executor1<S, P,
                        String, MessageArgument<S, P>>
                    ) (player, firstLine) -> {
                        setFirstLine(getMotd(), firstLine);

                        player.sendMessage("Motd", Color.SUCCESS, "spigot", "player.command.motd.changed_line", 1 + "", Color.translateAlternateColorCodes('&', firstLine) + "§7");
                    })
                )
        ).namespace(
            new Namespace<S, P>("2", player -> player.translate("spigot", "player.command.namespace.motd.2"))
                .withArg(
                    new MessageArgument<S, P>().executes((Executor1<S, P,
                        String, MessageArgument<S, P>>
                    ) (player, secondLine) -> {
                        setSecondLine(getMotd(), secondLine);

                        player.sendMessage("Motd", Color.SUCCESS,  "spigot", "player.command.motd.changed_line", 2 + "", Color.translateAlternateColorCodes('&', secondLine) + "§7");
                    })
                )
        );

        requires(StaffRank.DEVELOPER);
    }

    @Override
    public String getDescription(P player) {
        return player.translate("spigot", "player.command.motd.description");
    }

    private void displayLine(P player, int lineNumber, String line) {
        TextBuilder<P> builder = new TextBuilder<>();
        builder.
            add(Color.INFO, p -> Message.format("Motd", Color.INFO, "§7§l" + lineNumber + ".§r ")).
            add(Color.WHITE, p -> line.replace("&", "§")).
                click(ClickEvent.Action.SUGGEST_COMMAND, p -> "/" + this.name.toLowerCase() + " " + lineNumber + " " + Color.translateAlternateColorCodes('&', line)).
                hover(HoverEvent.Action.SHOW_TEXT, p -> "§7" + p.translate("spigot", "word.edit"));

        builder.send(player);
    }

    private void setFirstLine(String motd, String firstLine) {
        String secondLine = getSecondLine(motd);
        String newMotd = firstLine + "\n" + secondLine;

        saveMotd(newMotd);
    }

    private void setSecondLine(String motd, String secondLine) {
        String firstLine = getFirstLine(motd);
        String newMotd = firstLine + "\n" + secondLine;

        saveMotd(newMotd);
    }

    private void saveMotd(String motd) {
        StateProvider.getInstance().setString("motd", motd);
    }

    private String getMotd() {
        String motd = StateProvider.getInstance().getString("motd");

        return motd == null ? "" : motd;
    }

    private String getFirstLine(String motd) {
        if (motd.isEmpty())
            return "";

        return motd.split("\n")[0];
    }

    private String getSecondLine(String motd) {
        if (motd.isEmpty())
            return "";

        String[] s = motd.split("\n");
        return s.length > 1 ? s[1] : "";
    }
}
