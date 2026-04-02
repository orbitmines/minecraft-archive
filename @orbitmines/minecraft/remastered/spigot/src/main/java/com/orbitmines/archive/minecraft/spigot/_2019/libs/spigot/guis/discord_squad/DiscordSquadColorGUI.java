package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.guis.discord_squad;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.squad.DiscordSquad;
import com.orbitmines.archive.minecraft._2019.libs.discord.OMDiscordBot;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.utils.SkullTexture;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ColorUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.PlayerSkullBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.GUI;
import lombok.Getter;

public class DiscordSquadColorGUI<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends GUI<P> implements DiscordSquadGUIInstance<S, P> {

    @Getter private final DiscordSquad squad;
    @Getter private final OMDiscordBot bot;

    public DiscordSquadColorGUI(P viewer, DiscordSquad squad, GUI<P> overview) {
        super(54, "§0§lDiscord Squads", viewer);

        this.squad = squad;
        this.bot = viewer.server().getDiscordBot();

        if (this.bot == null)
            return;


        Color[] colors = Color.values();
        int i = 0;

        for (int row = 1; row < 4; row++) {
            for (int slot = 1; slot < 8; slot++) {
                if (slot == 4 || row == 2 && (slot == 2 || slot == 6))
                    continue;

                if (i >= 16)
                    break;

                Color color = colors[i];

                set(row, slot, new Item<P, MutableItemBuilder>(() -> {
                    ItemBuilder item = new ItemBuilder(ColorUtils.getTerracottaMaterial(color), 1, color.getCc() + "§l" + color.getName());

                    if (squad.getColor() == color)
                        item.glow();

                    return item;
                }, event -> {
                    squad.setColor(bot, color);
                    squad.update(DiscordSquad.column.COLOR);

                    viewer.sendMessage("Discord", Color.SUCCESS, "spigot", "player.discord_squad.recolored", squad.getColor().getCc() + squad.getColor().getName() + "§7");

                    close();
                }));

                i++;
            }
        }

        set(4, 4, new Item<P, MutableItemBuilder>(() -> {
            return new PlayerSkullBuilder("Blue Left Arrow", SkullTexture.BLUE_ARROW_LEFT, 1, "§9« " + viewer.translate("spigot", "player.discord_squad.gui.back_to_overview"));
        }, event -> overview.open()));
    }
}
