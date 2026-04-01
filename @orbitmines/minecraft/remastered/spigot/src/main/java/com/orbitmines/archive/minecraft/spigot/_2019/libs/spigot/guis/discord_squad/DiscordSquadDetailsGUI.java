package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.guis.discord_squad;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.squad.DiscordSquad;
import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.squad.DiscordSquadMember;
import com.orbitmines.archive.minecraft._2019.libs.discord.OMDiscordBot;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.utils.SkullTexture;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.PlayerSkullBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.GUI;
import lombok.Getter;
import org.bukkit.Material;

public class DiscordSquadDetailsGUI<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends GUI<P> implements DiscordSquadGUIInstance<S, P> {

    @Getter private final DiscordSquad squad;
    @Getter private final OMDiscordBot bot;

    public DiscordSquadDetailsGUI(P viewer, DiscordSquad squad, DiscordSquadMember member) {
        super(27, "§0§lDiscord Squads", viewer);

        this.squad = squad;
        this.bot = viewer.server().getDiscordBot();

        set(1, 1, new Item<P, MutableItemBuilder>(() -> {
            ItemBuilder item = new ItemBuilder(Material.EMERALD, 1, "§a§l" + viewer.translate("spigot", "player.discord_squad.gui.set_as_selected"));

            if (member.isSelected())
                item.glow();

            return item;
        }, event -> {
            if (member.isSelected())
                return;

            DiscordSquadMember previous = DiscordSquad.getSelectedMember(viewer.getUUID());
            if (previous != null) {
                previous.setSelected(false);
                previous.update(DiscordSquadMember.column.SELECTED);
            }

            member.setSelected(true);
            member.update(DiscordSquadMember.column.SELECTED);

            new DiscordSquadGUI<>(viewer, viewer).open();
        }));

        set(1, 4, new Item<P, MutableItemBuilder>(() -> {
            return new PlayerSkullBuilder("Blue Left Arrow", SkullTexture.BLUE_ARROW_LEFT, 1, "§9« " + viewer.translate("spigot", "player.discord_squad.gui.back_to_overview"));
        }, event -> {
            new DiscordSquadGUI<>(viewer, viewer).open();
        }));

        set(1, 7, new Item<P, MutableItemBuilder>(() -> {
            return new ItemBuilder(Material.BARRIER, 1, "§c§l" + viewer.translate("spigot", "player.discord_squad.gui.leave"));
        }, event -> {
            close();

            leave(squad, member);
        }));
    }
}
