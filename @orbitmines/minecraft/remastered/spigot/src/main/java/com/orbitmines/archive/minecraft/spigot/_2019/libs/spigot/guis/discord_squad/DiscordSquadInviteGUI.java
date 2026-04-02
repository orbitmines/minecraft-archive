package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.guis.discord_squad;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.squad.DiscordSquad;
import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.squad.DiscordSquadInvite;
import com.orbitmines.archive.minecraft._2019.libs.discord.OMDiscordBot;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.player.OfflinePlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.utils.SkullTexture;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.PlayerSkullBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.PaginatableGUI;
import lombok.Getter;
import org.bukkit.Material;

import java.util.List;

public class DiscordSquadInviteGUI<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends PaginatableGUI<P, DiscordSquad, DiscordSquadInvite> implements DiscordSquadGUIInstance<S, P> {

    @Getter private final DiscordSquad squad;
    @Getter private final OMDiscordBot bot;

    public DiscordSquadInviteGUI(P viewer, DiscordSquad key) {
        super(54, "§0§lDiscord Squads", viewer, key, 9, 3);

        this.squad = key;
        this.bot = viewer.server().getDiscordBot();

        if (this.bot == null)
            return;

        set(0, 4, new Item<P, MutableItemBuilder>(() -> {
            return new PlayerSkullBuilder(this.key.getUuid(), 1, "§9« " + viewer.translate("spigot", "player.discord_squad.gui.back_to_overview"));
        }, event -> {
            new DiscordSquadManageGUI<>(viewer, key).open();
        }));

        paginate(2, 0);

        setPreviousPage(5, 0, () -> new PlayerSkullBuilder("Blue Arrow Left", SkullTexture.BLUE_ARROW_LEFT, 1, "§7« " + viewer.translate("spigot", "player.discord_squad.gui.invites.paginate")));
        setPreviousPage(5, 8, () -> new PlayerSkullBuilder("Blue Arrow Right", SkullTexture.BLUE_ARROW_RIGHT, 1, "§7" + viewer.translate("spigot", "player.discord_squad.gui.invites.paginate") + " »"));
    }

    @Override
    public Item<P, MutableItemBuilder> getItem(PageItem<DiscordSquadInvite> pageItem) {
        return new Item<>(() -> {
            DiscordSquadInvite invite = pageItem.getObject();

            if (invite == null)
                return null;

            OfflinePlayer member = OfflinePlayer.get(invite.getUuid());

            ItemBuilder item = new ItemBuilder(Material.MAP, 1, member.getName(Name.RAW_COLORED));

            item.addLore("§7§o" + viewer.translate("spigot", "player.discord_squad.gui.invite"));
            item.addLore("");
            item.addLore("§c" + viewer.translate("spigot", "player.discord_squad.gui.invite.hover"));
            return item;
        }, event -> {
            DiscordSquadInvite invite = pageItem.getObject();

            if (invite == null)
                return;

            revertInvite(squad, invite);

            if (anyOutgoingRequests(squad))
                update();
            else
                new DiscordSquadManageGUI<>(viewer, key).open();
        });
    }

    @Override
    public List<DiscordSquadInvite> getCollection() {
        return squad.getInvites();
    }

    @Override
    public Class<DiscordSquadInvite> getTypeClass() {
        return DiscordSquadInvite.class;
    }
}
