package com.orbitmines.archive.minecraft._2019.libs.database.models.discord;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.squad.DiscordSquad;
import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.squad.DiscordSquadMember;
import com.orbitmines.archive.minecraft._2019.libs.database.mysql.OMMySQLModel;
import com.orbitmines.archive.minecraft._2019.libs.discord.CustomChannel;
import com.orbitmines.archive.minecraft._2019.libs.discord.CustomRole;
import com.orbitmines.archive.minecraft._2019.libs.discord.OMDiscordBot;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.player.OfflinePlayer;
import com.orbitmines.archive.minecraft._2019.libs.player.PlayerInstance;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Column;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.ColumnKey;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumn;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumnInstance;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumnKey;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLTable;
import com.orbitmines.archive.minecraft._2019.utils.database.model.ModelSelector;
import com.orbitmines.archive.minecraft._2019.utils.database.model.mysql.MySQLModelColumn;
import com.orbitmines.archive.minecraft._2019.utils.discord.DiscordBot;
import com.orbitmines.archive.minecraft._2019.utils.jedis.JedisManager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.*;

@AllArgsConstructor
public class DiscordUser extends OMMySQLModel<DiscordUser, DiscordUser.column> {

    public static MySQLTable TABLE = new MySQLTable("discord_users", MySQLModelColumn.toColumns(column.values()));

    @Getter @Setter private UUID uuid;
    @Getter @Setter private Long discordUserId;
    @Getter private MinecraftLinkResult result;

    public DiscordUser() {
    }

    public DiscordUser(UUID uuid, Long discordUserId) {
        this(uuid, discordUserId, MinecraftLinkResult.SETUP_COMPLETE);
    }

    public User getDiscordUser(DiscordBot bot) {
        if (this.discordUserId == null)
            return null;

        return bot.getUserById(this.discordUserId);
    }

    public Member getDiscordMember(DiscordBot bot) {
        if (this.discordUserId == null)
            return null;

        return bot.getGuild().getMemberById(this.discordUserId);
    }

    public void updateDiscordRanks(OMDiscordBot bot) {
        Guild guild = bot.getGuild();
        Member member = getDiscordMember(bot);

        if (member == null)
            return;

        List<Role> toRemove = new ArrayList<>(member.getRoles());
        List<Role> toAdd = new ArrayList<>();

        /* Muted */
        //TODO: MUTED
//        if (PunishmentHandler.getHandler(uuid).getActivePunishment(Punishment.Type.MUTE) != null)
//            add(bot.getRole(CustomRole.MUTED), toRemove, toAdd);

        if (isInserted() && !isDestroyed()) {
            OfflinePlayer player = OfflinePlayer.get(this.uuid);

            StaffRank staffRank = player.getStaffRank();
            if (!staffRank.isNone()) {
                add(bot.getRole(CustomRole.from(staffRank)), toRemove, toAdd);
                add(bot.getRole(CustomRole.STAFF), toRemove, toAdd);
            }

            VipRank vipRank = player.getVipRank();
            if (!vipRank.isNone()) {
                add(bot.getRole(CustomRole.from(vipRank)), toRemove, toAdd);
                add(bot.getRole(CustomRole.VIP), toRemove, toAdd);
            }

            if (staffRank.isNone() && vipRank.isNone())
                add(bot.getRole(CustomRole.MEMBER), toRemove, toAdd);

            /* Update Groups */
            DiscordSquad squad = DiscordSquad.findBy(DiscordSquad.class, DiscordSquad.column.UUID.is(player.getUUID()));
            if (squad != null)
                add(squad.getRole(bot), toRemove, toAdd);

            for (DiscordSquadMember squadMember : DiscordSquadMember.getAll(DiscordSquadMember.class, DiscordSquadMember.column.UUID.is(player.getUUID()))) {
                squad = DiscordSquad.findBy(DiscordSquad.class, DiscordSquad.column.ID.is(squadMember.getDiscordSquadId()));

                add(squad.getRole(bot), toRemove, toAdd);
            }
        } else {
            add(bot.getRole(CustomRole.MEMBER), toRemove, toAdd);
        }

        toAdd.removeIf(Objects::isNull);
        toRemove.removeIf(Objects::isNull);

        guild.modifyMemberRoles(member, toAdd, toRemove).queue();

        TextChannel channel = bot.getTextChannel(CustomChannel.DISCORD_LINK_LOG);
        if (toAdd.size() > 0)
            channel.sendMessage("Added " + stringifyRoles(toAdd) + " to " + member.getAsMention() + ".").queue();
        if (toRemove.size() > 0)
            channel.sendMessage("Removed " + stringifyRoles(toRemove) + " from " + member.getAsMention() + ".").queue();
    }

    private void add(Role role, List<Role> toRemove, List<Role> toAdd) {
        if (toRemove.contains(role))
            toRemove.remove(role);
        else
            toAdd.add(role);
    }

    private String stringifyRoles(List<Role> roles) {
        StringBuilder stringBuilder = new StringBuilder();

        int index = 0;
        for (Role role : roles) {
            if (index != 0)
                stringBuilder.append(", ");

            stringBuilder.append(role.getAsMention());

            index++;
        }

        return stringBuilder.toString();
    }

    @Override
    protected void load() {
        this.uuid = getUUID(column.UUID);
        this.discordUserId = getLong(column.DISCORD_USER_ID);
        this.result = MinecraftLinkResult.SETUP_COMPLETE;
    }

    @Override
    protected MySQLTable getTable() {
        return TABLE;
    }

    @Override
    protected ModelSelector[] getUniqueIdentifier() {
        return localIdentifiers(column.UUID);
    }

    @Override
    protected column getSortedIdentifier() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected String stringifyValue(column column) {
        switch (column) {

            case UUID:
                return stringify(this.uuid);
            case DISCORD_USER_ID:
                return stringify(this.discordUserId);
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Override
    protected column[] getColumns() {
        return column.values();
    }

    @AllArgsConstructor
    public enum column implements MySQLModelColumn {

        UUID(new MySQLColumnKey("uuid", Column.Type.VARCHAR, ColumnKey.Key.PRIMARY, 36)),
        DISCORD_USER_ID(new MySQLColumn("discord_user_id", Column.Type.BIGINT).indexed());

        @Getter private final MySQLColumnInstance column;
    }

    public static class Linker {

        private final OMDiscordBot bot;

        public Linker(OMDiscordBot bot) {
            this.bot = bot;
        }

        public DiscordUser link(Member member, PlayerInstance linkingTo) {
            DiscordUser user = DiscordUser.findBy(DiscordUser.class, column.DISCORD_USER_ID.is(member.getUser().getIdLong()));

            if (user != null && user.getUuid().equals(linkingTo.getUUID()))
                return new DiscordUser(user.getUuid(), member.getUser().getIdLong(), MinecraftLinkResult.ALREADY_LINKED);

            Long linkingDiscordId = getLinkingDiscordUserId(linkingTo.getUUID());
            if (linkingDiscordId == null || !linkingDiscordId.equals(member.getUser().getIdLong())) {
                setLinkingUUID(member.getUser().getIdLong(), linkingTo.getUUID());

                return new DiscordUser(linkingTo.getUUID(), member.getUser().getIdLong(), MinecraftLinkResult.SETTING_UP);
            }

            /* Revert previous link */
            if (user != null)
                revertRanks(user);

            return finalizeLink(linkingTo, member);
        }

        public DiscordUser link(PlayerInstance player, Member linkingTo) {
            DiscordUser user = DiscordUser.findBy(DiscordUser.class, column.UUID.is(player.getUUID()));

            if (user != null && user.getDiscordUserId() == linkingTo.getUser().getIdLong())
                return new DiscordUser(user.getUuid(), linkingTo.getUser().getIdLong(), MinecraftLinkResult.ALREADY_LINKED);

            String linkingUuid = getLinkingUUID(linkingTo.getUser().getIdLong());
            if (linkingUuid == null || !linkingUuid.equals(player.getUUID().toString())) {
                setLinkingDiscordUserId(player.getUUID(), linkingTo.getUser().getIdLong());

                return new DiscordUser(player.getUUID(), linkingTo.getUser().getIdLong(), MinecraftLinkResult.SETTING_UP);
            }

            /* Revert previous link */
            if (user != null)
                revertRanks(user);

            return finalizeLink(player, linkingTo);
        }

        private DiscordUser finalizeLink(PlayerInstance player, Member member) {
            DiscordUser discordUser = new DiscordUser(player.getUUID(), member.getUser().getIdLong());
            discordUser.insert();

            User user = discordUser.getDiscordUser(bot);
            bot.withPlayerEmote(player.getUUID(), player.getName(Name.RAW), false, emote -> {
                getChannel().sendMessage("Linking " + user.getAsMention() + " (Id: " + user.getId() + ") to " + bot.getPlayerDisplay(player, emote, player.getName(Name.RAW)) + "...").queue();

                discordUser.updateDiscordRanks(bot);
            });

            clearRedis(discordUser.getDiscordUserId(), discordUser.getUuid());

            return discordUser;
        }

        private void revertRanks(DiscordUser discordUser) {
            discordUser.delete();

            OfflinePlayer player = OfflinePlayer.get(discordUser.getUuid());
            User user = discordUser.getDiscordUser(bot);
            bot.withPlayerEmote(player.getUUID(), player.getName(Name.RAW), false, emote -> {
                getChannel().sendMessage("Unlinking " + user.getAsMention() + " (Id: " + user.getId() + ") from " + bot.getPlayerDisplay(player, emote, player.getName(Name.RAW)) + "...").queue();
            });

            discordUser.setUuid(null);
            discordUser.updateDiscordRanks(bot);
        }

        private TextChannel getChannel() {
            return bot.getTextChannel(CustomChannel.DISCORD_LINK_LOG);
        }

        private Long getLinkingDiscordUserId(UUID uuid) {
            try (Jedis jedis = JedisManager.get()) {
                String discordUserId = jedis.get("discord_link:uuid:" + uuid.toString());

                return discordUserId != null ? Long.parseLong(discordUserId) : null;
            }
        }

        private String getLinkingUUID(long discordUserId) {
            try (Jedis jedis = JedisManager.get()) {
                return jedis.get("discord_link:discord_user_id:" + discordUserId);
            }
        }

        private void setLinkingDiscordUserId(UUID uuid, long discordUserId) {
            try (Jedis jedis = JedisManager.get()) {
                Pipeline pipeline = jedis.pipelined();
                pipeline.set("discord_link:uuid:" + uuid.toString(), discordUserId + "");
                pipeline.expire("discord_link:uuid:" + uuid.toString(), 60 * 60);

                pipeline.sync();
            }
        }

        private void setLinkingUUID(long discordUserId, UUID uuid) {
            try (Jedis jedis = JedisManager.get()) {
                Pipeline pipeline = jedis.pipelined();
                pipeline.set("discord_link:discord_user_id:" + discordUserId, uuid.toString());
                pipeline.expire("discord_link:discord_user_id:" + discordUserId, 60 * 60);

                pipeline.sync();
            }
        }

        private void clearRedis(long discordUserId, UUID uuid) {
            try (Jedis jedis = JedisManager.get()) {
                jedis.del("discord_link:discord_user_id:" + discordUserId);
                jedis.del("discord_link:uuid:" + uuid.toString());
            }
        }
    }

    public enum MinecraftLinkResult {

        SETTING_UP,
        ALREADY_LINKED,
        SETUP_COMPLETE;

    }
}
