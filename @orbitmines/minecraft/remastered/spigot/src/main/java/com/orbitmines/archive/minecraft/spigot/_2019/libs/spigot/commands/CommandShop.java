package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Environment;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.text.TextBuilder;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;

public class CommandShop<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends Command<S, P> {

    public CommandShop(S plugin) {
        super(plugin, "shop", "buy", "buycraft", "shop", "store", "donate", "rank");

        executes((Executor0<S, P>) p -> {
            String shopLink = Environment.get("OM_SHOP_LINK", "https://shop.orbitmines.com");

            TextBuilder<P> builder = new TextBuilder<P>();
            builder.
                add(Color.INFO, player -> "Shop »").space().
                add(Color.TEAL, player -> shopLink).
                click(ClickEvent.Action.OPEN_URL, player -> shopLink).
                hover(HoverEvent.Action.SHOW_TEXT, player -> "§7" + player.translate("spigot", "word.open_link") + " " + Color.TEAL.getCc() + shopLink);

            builder.send(p);
        });
    }

    @Override
    public String getDescription(P player) {
        return player.translate("spigot", "player.command.shop.description");
    }
}
