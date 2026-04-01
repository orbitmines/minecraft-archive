package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.guis.discord_squad;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.squad.DiscordSquad;
import com.orbitmines.archive.minecraft._2019.libs.discord.OMDiscordBot;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.PlayerSkullBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.GUI;
import lombok.Getter;
import org.bukkit.Material;

public class DiscordSquadRemoveGUI<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends GUI<P> implements DiscordSquadGUIInstance<S, P> {

    @Getter private final DiscordSquad squad;
    @Getter private final OMDiscordBot bot;

    public DiscordSquadRemoveGUI(P viewer, DiscordSquad squad, GUI<P> overview) {
        super(54, "§0§lAre you sure?", viewer);

        this.squad = squad;
        this.bot = viewer.server().getDiscordBot();

        set(2, 4, new Item<P, MutableItemBuilder>(() -> new ItemBuilder(Material.BARRIER, 1, "§c§l" + viewer.translate("spigot", "player.discord_squad.gui.delete"))));

        set(3, 4, new Item<P, MutableItemBuilder>(() -> {
            PlayerSkullBuilder item = new PlayerSkullBuilder(viewer.getUUID(), 1, squad.getDisplayName());

            return asIcon(squad, item);
        }));

        Item<P, MutableItemBuilder> confirm = new Item<>(() -> {
            return new ItemBuilder(Material.LIME_STAINED_GLASS_PANE, 1, "§a§l" + viewer.translate("spigot", "player.discord_squad.gui.confirm"));
        }, event -> {
            close();

            squad.destroy(bot, viewer);
        });

        Item<P, MutableItemBuilder> cancel = new Item<>(() -> {
            return new ItemBuilder(Material.RED_STAINED_GLASS_PANE, 1, "§c§l" + viewer.translate("spigot", "player.discord_squad.gui.cancel"));
        }, event -> overview.open());

        for (int i = 0; i < 9; i++) {
            if (i > 2 && i < 6)
                continue;

            for (int j = 1; j < 5; j++) {
                set(j, i, i <= 2 ? confirm : cancel);
            }
        }
    }
}
