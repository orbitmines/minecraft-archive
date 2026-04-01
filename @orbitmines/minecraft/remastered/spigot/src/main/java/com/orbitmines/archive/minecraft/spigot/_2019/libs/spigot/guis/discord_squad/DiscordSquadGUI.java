package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.guis.discord_squad;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.squad.DiscordSquad;
import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.squad.DiscordSquadInvite;
import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.squad.DiscordSquadMember;
import com.orbitmines.archive.minecraft._2019.libs.discord.OMDiscordBot;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.player.OfflinePlayer;
import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.discord.SpigotDiscordBot;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.utils.SkullTexture;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import com.orbitmines.archive.minecraft._2019.utils.EnumUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ColorUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilderInstance;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.PlayerSkullBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.PaginatableGUI;
import lombok.Getter;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class DiscordSquadGUI<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends PaginatableGUI<P, P, Object> implements DiscordSquadGUIInstance<S, P> {

    @Getter private final DiscordSquad squad;
    @Getter private final OMDiscordBot bot;

    public DiscordSquadGUI(P viewer, P key) {
        super(54, "§0§lDiscord Squads", viewer, key, 9, 1);

        this.squad = DiscordSquad.findBy(DiscordSquad.class, DiscordSquad.column.UUID.is(key.getUUID()));
        this.bot = viewer.server().getDiscordBot();

        set(1, 2, new Item<P, MutableItemBuilder>(() -> {
            DiscordSquad selected = DiscordSquad.getSelected(key.getUUID());
            PlayerSkullBuilder item = new PlayerSkullBuilder("Discord Skull", SpigotDiscordBot.SKULL_TEXTURE, 1, "§7§l" + viewer.translate("spigot", "player.discord_squad.gui.selected"));
            item.addLore("§7Squad " + (selected != null ? selected.getDisplayName() : VipRank.NONE.getDisplayName()));

            if (selected != null) {
                OfflinePlayer offlineOwner = selected.getOfflineOwner();
                item.addLore("§7" + viewer.translate("spigot", "player.discord_squad.gui.owner") + ": " + offlineOwner.getName(Name.RAW_COLORED));
            }

            return item;
        }));

        set(2, 2, new Item<P, MutableItemBuilder>(() -> new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE, 1, "§7" + viewer.translate("spigot", "player.discord_squad.gui.chat_example", "§9!<message>§7"))));

        if (squad == null) {
            boolean canCreate = key.isEligible(VipRank.EMERALD);

            boolean hasLinked = key.getDiscordUser() != null;


            set(1, 5, new Item<P, MutableItemBuilder>(() -> {
                ItemBuilder item = new ItemBuilder(Material.WRITABLE_BOOK, 1, "§7§l" + viewer.translate("spigot", "player.discord_squad.gui.your_squad"));
                item.addLore("§7Required: " + VipRank.EMERALD.getDisplayName());

                if (!hasLinked) {
                    item.addLore("");

                    for (String string : viewer.getLanguage().getStringArray("spigot", "player.discord_squad.gui.create.not_linked", "§9/discordlink§7")) {
                        item.addLore("§7" + string);
                    }
                }

                if (canCreate) {
                    item.addLore("");
                    item.addLore("§a" + viewer.translate("spigot", "player.discord_squad.gui.create.hover"));

                    item.glow();
                }

                return item;
            }, event -> {
                if (!canCreate || !hasLinked)
                    return;

                close();

                DiscordSquad newSquad = new DiscordSquad(key.getUUID(), key.getRawName() + "s_SQUAD", EnumUtils.random(Color.class), DateUtils.now());
                newSquad.setup(bot, viewer);
            }));

            Item<P, MutableItemBuilder> unavailable = new Item<P, MutableItemBuilder>(() -> new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, "§8" + viewer.translate("spigot", "player.discord_squad.gui.unavailable")));
            set(2, 4, unavailable);
            set(2, 5, unavailable);
            set(2, 6, unavailable);
        } else {
            set(0, 5, new Item<P, MutableItemBuilder>(() -> {
                return new ItemBuilder(Material.BARRIER, 1, "§c§l" + viewer.translate("spigot", "player.discord_squad.gui.delete"));
            }, event -> {
                new DiscordSquadRemoveGUI<>(viewer, squad, this).open();
            }));

            set(1, 5, new Item<P, MutableItemBuilder>(() -> {
                return asIcon(squad, new PlayerSkullBuilder(viewer.getUUID(), 1, squad.getDisplayName()));
            }, event -> {
                new DiscordSquadGUI<>(viewer, viewer).open();
            }));

            set(2, 4, new Item<P, MutableItemBuilder>(() -> {
                return new ItemBuilder(Material.WRITABLE_BOOK, 1, "§7§l" + viewer.translate("spigot", "player.discord_squad.gui.manage_members"));
            }, event -> {
                new DiscordSquadManageGUI<>(viewer, squad).open();
            }));

            set(2, 5, new Item<P, MutableItemBuilder>(() -> {
                return new ItemBuilder(Material.FILLED_MAP, 1, "§7§l" + viewer.translate("spigot", "player.discord_squad.gui.change_name"));
            }, event -> {
                openNamePicker(this, squad);
            }));

            set(2, 6, new Item<P, MutableItemBuilder>(() -> {
                return new ItemBuilder(ColorUtils.getTerracottaMaterial(squad.getColor()), 1, "§7§l" + viewer.translate("spigot", "player.discord_squad.gui.change_color"));
            }, event -> {
                new DiscordSquadColorGUI<>(viewer, squad, this).open();
            }));
        }

        paginate(4, 0);

        setPreviousPage(5, 0, () -> new PlayerSkullBuilder("Blue Arrow Left", SkullTexture.BLUE_ARROW_LEFT, 1, "§7« " + viewer.translate("spigot", "player.discord_squad.gui.overview.paginate")));
        setPreviousPage(5, 8, () -> new PlayerSkullBuilder("Blue Arrow Right", SkullTexture.BLUE_ARROW_RIGHT, 1, "§7" + viewer.translate("spigot", "player.discord_squad.gui.overview.paginate") + " »"));
    }

    @Override
    public Item<P, MutableItemBuilder> getItem(PageItem<Object> pageItem) {
        return new Item<>(() -> {
            Object object = pageItem.getObject();

            if (object == null)
                return null;

            if (object instanceof DiscordSquad) {
                DiscordSquad squad = (DiscordSquad) object;

                boolean isOwner = squad.getUuid().equals(key.getUUID());

                ItemBuilderInstance item = asIcon(squad, new PlayerSkullBuilder(squad.getUuid(), 1, squad.getDisplayName(), new ArrayList<>()));

                if (!isOwner) {
                    item.addLore("");
                    item.addLore("§a" + viewer.translate("spigot", "player.discord_squad.gui.select_or_delete.hover"));
                }

                return item;
            } else if (object instanceof DiscordSquadInvite) {
                DiscordSquadInvite invite = (DiscordSquadInvite) object;
                DiscordSquad squad = DiscordSquad.findBy(DiscordSquad.class, DiscordSquad.column.ID.is(invite.getDiscordSquadId()));

                ItemBuilderInstance item = asIcon(squad, new ItemBuilder(Material.WRITABLE_BOOK, 1, "§9§l" + viewer.translate("spigot", "player.discord_squad.gui.invite").toUpperCase() + " " + squad.getDisplayName(), new ArrayList<>()));
                item.glow();

                item.addLore("");
                item.addLore("§a" + viewer.translate("spigot", "player.discord_squad.gui.accept_or_ignore.hover"));

                return item;
            }

            throw new IllegalStateException();
        }, event -> {
            Object object = pageItem.getObject();

            if (object == null)
                return;


            if (object instanceof DiscordSquad) {
                DiscordSquad squad = (DiscordSquad) object;

                boolean isOwner = squad.getUuid().equals(key.getUUID());

                if (!isOwner)
                    new DiscordSquadDetailsGUI<>(viewer, squad, DiscordSquadMember.findBy(DiscordSquadMember.class, DiscordSquadMember.column.UUID.is(key.getUUID()), DiscordSquadMember.column.DISCORD_SQUAD_ID.is(squad.getId()))).open();

                return;
            } else if (object instanceof DiscordSquadInvite) {
                DiscordSquadInvite invite = (DiscordSquadInvite) object;
                DiscordSquad squad = DiscordSquad.findBy(DiscordSquad.class, DiscordSquad.column.ID.is(invite.getDiscordSquadId()));

                new DiscordSquadInviteDetailsGUI<>(viewer, squad, invite).open();
                return;
            }

            throw new IllegalStateException();
        });
    }

    @Override
    public List<Object> getCollection() {
        List<Object> ordered = new ArrayList<>();
        if (squad != null)
            ordered.add(squad);

        ordered.addAll(DiscordSquad.getSquadsAsMember(key.getUUID()));
        ordered.addAll(DiscordSquadInvite.getAll(DiscordSquadInvite.class, DiscordSquadInvite.column.UUID.is(key.getUUID())));

        return ordered;
    }

    @Override
    public Class<Object> getTypeClass() {
        return Object.class;
    }
}
