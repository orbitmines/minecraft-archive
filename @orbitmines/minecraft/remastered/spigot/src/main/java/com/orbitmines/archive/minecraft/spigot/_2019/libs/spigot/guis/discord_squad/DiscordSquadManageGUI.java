package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.guis.discord_squad;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.database.models.IPEntry;
import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.DiscordUser;
import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.squad.DiscordSquad;
import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.squad.DiscordSquadMember;
import com.orbitmines.archive.minecraft._2019.libs.discord.OMDiscordBot;
import com.orbitmines.archive.minecraft._2019.libs.jedis.OnlinePlayer;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.player.OfflinePlayer;
import com.orbitmines.archive.minecraft._2019.libs.player.PlayerInstance;
import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.utils.SkullTexture;
import com.orbitmines.archive.minecraft._2019.utils.TimeUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ColorUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilderInstance;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.PlayerSkullBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.PaginatableGUI;
import lombok.Getter;
import net.dv8tion.jda.api.entities.User;
import org.bukkit.Material;

import java.util.*;

public class DiscordSquadManageGUI<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends PaginatableGUI<P, DiscordSquad, DiscordSquadMember> implements DiscordSquadGUIInstance<S, P> {

    @Getter private final DiscordSquad squad;
    @Getter private final OMDiscordBot bot;

    @Getter private Map<DiscordSquadMember, OnlinePlayer> onlineMembers = new HashMap<>();
    @Getter private Map<DiscordSquadMember, OfflinePlayer> offlineMembers = new HashMap<>();

    public DiscordSquadManageGUI(P viewer, DiscordSquad key) {
        super(54, "§0§lDiscord Squads", viewer, key, 9, 3);

        this.squad = key;
        this.bot = viewer.server().getDiscordBot();

        set(0, 1, new Item<P, MutableItemBuilder>(() -> {
            int size = squad.getInvites().size();
            ItemBuilder item = new ItemBuilder(Material.BOOK, 1, "§7§l" + viewer.translate("spigot", "player.discord_squad.gui.invites", "§9§l" + size + "§7§l"));

            if (size != 0)
                item.glow();

            return item;
        }, event -> {
            if (!anyOutgoingRequests(squad))
                return;

            new DiscordSquadInviteGUI<>(viewer, key).open();
        }));

        set(0, 2, new Item<P, MutableItemBuilder>(() -> {
            return new ItemBuilder(Material.WRITABLE_BOOK, 1, "§9§l" + viewer.translate("spigot", "player.discord_squad.gui.send_request"));
        }, event -> {
            openMemberPicker(this, squad);
        }));

        set(0, 4, new Item<P, MutableItemBuilder>(() -> {
            ItemBuilderInstance item = asIcon(squad, new PlayerSkullBuilder(viewer.getUUID(), 1, squad.getDisplayName()));

            item.addLore("");
            item.addLore("§9§o« " + viewer.translate("spigot", "player.discord_squad.gui.back_to_overview"));

            return item;
        }, event -> {
            new DiscordSquadGUI<>(viewer, viewer).open();
        }));

        set(0, 6, new Item<P, MutableItemBuilder>(() -> {
            return new ItemBuilder(Material.FILLED_MAP, 1, "§7§l" + viewer.translate("spigot", "player.discord_squad.gui.change_name"));
        }, event -> {
            openNamePicker(this, squad);
        }));

        set(0, 7, new Item<P, MutableItemBuilder>(() -> {
            return new ItemBuilder(ColorUtils.getTerracottaMaterial(squad.getColor()), 1, "§7§l" + viewer.translate("spigot", "player.discord_squad.gui.change_color"));
        }, event -> {
            new DiscordSquadColorGUI<>(viewer, squad, this).open();
        }));

        set(1, 4, new Item<P, MutableItemBuilder>(() -> {
            return new ItemBuilder(Material.BARRIER, 1, "§c§l" + viewer.translate("spigot", "player.discord_squad.gui.delete"));
        }, event -> {
            new DiscordSquadRemoveGUI<>(viewer, squad, this).open();
        }));

        paginate(2, 0);

        setPreviousPage(5, 0, () -> new PlayerSkullBuilder("Blue Arrow Left", SkullTexture.BLUE_ARROW_LEFT, 1, "§7« " + viewer.translate("spigot", "player.discord_squad.gui.manage.paginate")));
        setPreviousPage(5, 8, () -> new PlayerSkullBuilder("Blue Arrow Right", SkullTexture.BLUE_ARROW_RIGHT, 1, "§7" + viewer.translate("spigot", "player.discord_squad.gui.manage.paginate") + " »"));
    }

    @Override
    public void beforeUpdateAsync() {
        updateOnlineMembers();
    }

    private void updateOnlineMembers() {
        onlineMembers = getOnlineMembers(squad);
        offlineMembers = getOfflineMembers(squad);
    }

    private OnlinePlayer getOnlineMember(DiscordSquadMember member) {
        return onlineMembers.getOrDefault(member, null);
    }

    @Override
    public Item<P, MutableItemBuilder> getItem(PageItem<DiscordSquadMember> pageItem) {
        return new Item<>(() -> {
            DiscordSquadMember memberModel = pageItem.getObject();

            if (memberModel == null)
                return null;

            OnlinePlayer onlineMember = getOnlineMember(memberModel);
            PlayerInstance member = onlineMember != null ? onlineMember : offlineMembers.get(memberModel);

            ItemBuilderInstance builder;
            if (onlineMember != null)
                builder = new PlayerSkullBuilder(member.getUUID(), 1, member.getName(Name.RAW_COLORED));
            else
                builder = new ItemBuilder(Material.WITHER_SKELETON_SKULL, 1, member.getName(Name.RAW_COLORED));

            DiscordUser discordUser = member.getDiscordUser();
            User user = discordUser != null ? discordUser.getDiscordUser(bot) : null;
            builder.addLore("§7Discord: " + (user != null ? "§9§l" + user.getName() + "#" + user.getDiscriminator() : VipRank.NONE.getDisplayName()));

            builder.addLore("");

            if (onlineMember != null) {
                builder.addLore("§7Status: " + Server.Status.ONLINE.getDisplayName());
                builder.addLore("§7Server: " + onlineMember.getServer().getDisplayName());
            } else {
                builder.addLore("§7Status: " + Server.Status.OFFLINE.getDisplayName());

                IPEntry entry = member.getLastIPEntry();
                Date lastSeen = entry.getLogoutAt() != null ? entry.getLogoutAt() : entry.getLoginAt();

                builder.addLore("§7" + viewer.translate("spigot", "player.friends.last_seen", "§b§l" + TimeUtils.humanFriendlyTimer(viewer.getLanguage(), System.currentTimeMillis() - lastSeen.getTime()) + "§7"));
            }

            builder.addLore("");
            builder.addLore("§c" + viewer.translate("spigot", "player.discord_squad.gui.invite.hover"));

            return builder;
        }, event -> {
            DiscordSquadMember member = pageItem.getObject();

            if (member == null)
                return;

            close();

            removeMember(squad, member);
        });
    }

    @Override
    public List<DiscordSquadMember> getCollection() {
        /* Order List Online, Offline */
        List<DiscordSquadMember> ordered = new ArrayList<>();
        ordered.addAll(this.onlineMembers.keySet());
        ordered.addAll(this.offlineMembers.keySet());

        return ordered;
    }

    @Override
    public Class<DiscordSquadMember> getTypeClass() {
        return DiscordSquadMember.class;
    }
}
