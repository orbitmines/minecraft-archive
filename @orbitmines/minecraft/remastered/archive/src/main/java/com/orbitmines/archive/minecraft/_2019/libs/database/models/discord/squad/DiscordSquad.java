package com.orbitmines.archive.minecraft._2019.libs.database.models.discord.squad;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Environment;
import com.orbitmines.archive.minecraft._2019.libs.Image;
import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.DiscordUser;
import com.orbitmines.archive.minecraft._2019.libs.database.mysql.OMMySQLModel;
import com.orbitmines.archive.minecraft._2019.libs.discord.CustomChannel;
import com.orbitmines.archive.minecraft._2019.libs.discord.CustomEmote;
import com.orbitmines.archive.minecraft._2019.libs.discord.CustomRole;
import com.orbitmines.archive.minecraft._2019.libs.discord.OMDiscordBot;
import com.orbitmines.archive.minecraft._2019.libs.jedis.OnlinePlayer;
import com.orbitmines.archive.minecraft._2019.libs.player.Languageable;
import com.orbitmines.archive.minecraft._2019.libs.player.OfflinePlayer;
import com.orbitmines.archive.minecraft._2019.libs.player.PlayerInstance;
import com.orbitmines.archive.minecraft._2019.libs.rank.Rank;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Column;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.ColumnKey;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumn;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumnInstance;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumnKey;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLTable;
import com.orbitmines.archive.minecraft._2019.utils.database.model.ModelSelector;
import com.orbitmines.archive.minecraft._2019.utils.database.model.mysql.MySQLModelColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.emoji.RichCustomEmoji;
import net.dv8tion.jda.api.requests.restaction.ChannelAction;
import net.dv8tion.jda.api.requests.restaction.RoleAction;

import java.io.File;
import java.util.*;

@AllArgsConstructor
public class DiscordSquad extends OMMySQLModel<DiscordSquad, DiscordSquad.column> {

    public static MySQLTable TABLE = new MySQLTable("discord_squads", MySQLModelColumn.toColumns(column.values()));

    public static final int MAX_NAME_CHARACTERS = 30;

    @Getter private Long id;
    @Getter private UUID uuid;
    @Getter @Setter private Long textChannelId;
    @Getter @Setter private Long voiceChannelId;
    @Getter @Setter private Long categoryId;
    @Getter @Setter private Long roleId;
    @Getter private String name;
    @Getter private Color color;
    @Getter private Date createdAt;

    public DiscordSquad() {
    }

    public DiscordSquad(UUID uuid, String name, Color color, Date createdAt) {
        this.uuid = uuid;
        this.name = name;
        this.color = color;
        this.createdAt = createdAt;
    }

    public Role getRole(OMDiscordBot bot) {
        return bot.getGuild().getRoleById(this.roleId);
    }

    public TextChannel getTextChannel(OMDiscordBot bot) {
        return bot.getGuild().getTextChannelById(this.textChannelId);
    }

    public VoiceChannel getVoiceChannel(OMDiscordBot bot) {
        return bot.getGuild().getVoiceChannelById(this.voiceChannelId);
    }

    public Category getCategory(OMDiscordBot bot) {
        return bot.getGuild().getCategoryById(this.categoryId);
    }

    public String getDisplayName() {
        return this.color.getCc() + "@" + this.name;
    }

    public void setName(OMDiscordBot bot, String name) {
        this.name = name;

        getRole(bot).getManager().setName(name).queue(a -> {
            getTextChannel(bot).getManager().setName(name.toLowerCase()).queue(b -> {
                getVoiceChannel(bot).getManager().setName(name).queue(c -> {
                    OfflinePlayer player = getOfflineOwner();

                    bot.withPlayerEmote(player.getUUID(), player.getRawName(), false, emote -> {
                        getLogChannel(bot).sendMessage("Successfully changed " + bot.getPlayerDisplay(player, emote, player.getRawName()) + "'s Discord Squad Name to " + getRole(bot).getAsMention() + " » " + getTextChannel(bot).getAsMention() + ".").queue();
                    });

                    getTextChannel(bot).sendMessage("Successfully changed name to " + getRole(bot).getAsMention() + " » " + getTextChannel(bot).getAsMention() + ".").queue();
                }, throwable -> onError(bot, "Voice Channel", "updating"));
            }, throwable -> onError(bot, "Text Channel", "updating"));
        }, throwable -> onError(bot, "Role", "updating"));
    }

    public void setColor(OMDiscordBot bot, Color color) {
        this.color = color;

        getRole(bot).getManager().setColor(color.getAwtColor()).queue(a -> {
            String mention = bot.getRole(CustomRole.from(color)).getAsMention();
            OfflinePlayer player = getOfflineOwner();

            bot.withPlayerEmote(player.getUUID(), player.getRawName(), false, emote -> {
                getLogChannel(bot).sendMessage("Successfully changed " + bot.getPlayerDisplay(player, emote, player.getRawName()) + "'s Discord Squad Color to " + mention + " » " + getRole(bot).getAsMention() + ".").queue();
            });

            getTextChannel(bot).sendMessage("Successfully changed color to " + mention + " » " + getRole(bot).getAsMention() + ".").queue();
        }, throwable -> onError(bot, "Role", "updating"));
    }

    public boolean isSetup() {
        return this.inserted &&
            this.textChannelId != null &&
            this.voiceChannelId != null &&
            this.categoryId != null &&
            this.roleId != null;
    }

    public void onNameChange(OMDiscordBot bot) {
        Category category = getCategory(bot);
        String categoryName = generateCategoryName();

        /* Only update when the category name changed */
        if (category.getName().equals(categoryName))
            return;

        category.getManager().setName(categoryName).queue(channel -> {
            OfflinePlayer owner = getOfflineOwner();

            bot.withPlayerEmote(owner.getUUID(), owner.getRawName(), false, emote -> {
                getLogChannel(bot).sendMessage("Successfully changed " + bot.getPlayerDisplay(owner, emote, owner.getRawName()) + "'s Discord Squad Category to » **" + categoryName + "**.").queue();
            });
        });
    }

    public void onError(OMDiscordBot bot, String type, String verb) {
        OfflinePlayer owner = getOfflineOwner();

        bot.withPlayerEmote(owner.getUUID(), owner.getRawName(), false, emote -> {
            String playerDisplay = bot.getPlayerDisplay(owner, emote, owner.getRawName());

            getLogChannel(bot).sendMessage(
                bot.getEmote(CustomEmote.barrier).getAsMention() + " " + playerDisplay + ", " + Environment.getEveryoneOrDev(bot) + " » An error occurred while " + verb + " the **" + type + "** for " + playerDisplay + "'s **Discord Squad**."
            ).queue();
        });
    }

    private String generateCategoryName() {
        OfflinePlayer owner = getOfflineOwner();
        String prefix = owner.getRank().getName();

        return (prefix.equals(Rank.NONE) ? "" : prefix + " ") + owner.getRawName();
    }

    public OfflinePlayer getOfflineOwner() {
        return OfflinePlayer.get(this.uuid);
    }

    public OnlinePlayer getOnlineOwner() {
        return OnlinePlayer.get(this.uuid);
    }

    public List<OfflinePlayer> getOfflineMembers() {
        List<OfflinePlayer> members = new ArrayList<>();

        for (DiscordSquadMember member : getMembers()) {
            members.add(OfflinePlayer.get(member.getUuid()));
        }

        return members;
    }

    public List<OnlinePlayer> getOnlineMembers() {
        List<OnlinePlayer> members = new ArrayList<>();

        for (DiscordSquadMember member : getMembers()) {
            members.add(OnlinePlayer.get(member.getUuid()));
        }

        members.removeIf(Objects::isNull);

        return members;
    }

    public List<OnlinePlayer> getAllOnline() {
        List<OnlinePlayer> all = getOnlineMembers();
        all.add(getOnlineOwner());

        all.removeIf(Objects::isNull);

        return all;
    }

    public List<OfflinePlayer> getAllOffline() {
        List<OfflinePlayer> all = getOfflineMembers();
        all.add(getOfflineOwner());

        all.removeIf(Objects::isNull);

        return all;
    }

    public List<DiscordSquadMember> getMembers() {
        return DiscordSquadMember.getAll(DiscordSquadMember.class, DiscordSquadMember.column.DISCORD_SQUAD_ID.is(this.id));
    }

    public boolean isMember(UUID member) {
        for (DiscordSquadMember squadMember : getMembers()) {
            if (squadMember.getUuid().equals(member))
                return true;
        }
        return false;
    }

    public List<DiscordSquadInvite> getInvites() {
        return DiscordSquadInvite.getAll(DiscordSquadInvite.class, DiscordSquadInvite.column.DISCORD_SQUAD_ID.is(this.id));
    }

    public boolean hasInvited(UUID member) {
        for (DiscordSquadInvite invite : getInvites()) {
            if (invite.getUuid().equals(member))
                return true;
        }

        return false;
    }

    public TextChannel getLogChannel(OMDiscordBot bot) {
        return bot.getTextChannel(CustomChannel.PRIVATE_SERVER_LOG);
    }

    public <P extends PlayerInstance & Languageable> void setup(OMDiscordBot bot, P player) {
        new Setup<>(bot, player).start();
    }

    public <P extends PlayerInstance & Languageable> void destroy(OMDiscordBot bot, P player) {
        new Destroy<>(bot, player).start();
    }

    /*

        Mimic Languagable

     */

    public void sendMessage(String namespace, String key) {
        for (OnlinePlayer player : getAllOnline()) {
            player.sendMessage(namespace, key);
        }
    }

    public void sendMessage(String namespace, String key, Object... args) {
        for (OnlinePlayer player : getAllOnline()) {
            player.sendMessage(namespace, key, args);
        }
    }

    public void sendMessage(String prefix, Color color, String key) {
        for (OnlinePlayer player : getAllOnline()) {
            player.sendMessage(prefix, color, key);
        }
    }

    public void sendRawMessage(String prefix, Color color, String message) {
        for (OnlinePlayer player : getAllOnline()) {
            player.sendRawMessage(prefix, color, message);
        }
    }

    public void sendMessage(String prefix, Color color, String key, Object... args) {
        for (OnlinePlayer player : getAllOnline()) {
            player.sendMessage(prefix, color, key, args);
        }
    }

    public void sendMessage(String prefix, Color color, String namespace, String key) {
        for (OnlinePlayer player : getAllOnline()) {
            player.sendMessage(prefix, color, namespace, key);
        }
    }

    public void sendMessage(String prefix, Color color, String namespace, String key, Object... args) {
        for (OnlinePlayer player : getAllOnline()) {
            player.sendMessage(prefix, color, namespace, key, args);
        }
    }

    public static boolean exists(OMDiscordBot bot, String name) {
        if (name.equalsIgnoreCase("everyone") || name.equalsIgnoreCase("here") || bot.getGuild().getRolesByName(name, true).size() != 0)
            return true;

        /* As backup, but shouldn't happen */
        return DiscordSquad.findBy(DiscordSquad.class, column.NAME.is(name)) != null;
    }

    /* Use selected DiscordSquadMember, and if not exists default to own squad */
    public static DiscordSquad getSelected(UUID player) {
        DiscordSquadMember selected = getSelectedMember(player);

        if (selected != null)
            return DiscordSquad.findBy(DiscordSquad.class, DiscordSquad.column.ID.is(selected.getDiscordSquadId()));

        return DiscordSquad.findBy(DiscordSquad.class, DiscordSquad.column.UUID.is(player));
    }

    public static DiscordSquadMember getSelectedMember(UUID player) {
        return DiscordSquadMember.findBy(DiscordSquadMember.class, DiscordSquadMember.column.UUID.is(player), DiscordSquadMember.column.SELECTED.is(true));
    }

    public static List<DiscordSquad> getSquadsAsMember(UUID player) {
        List<DiscordSquad> squads = new ArrayList<>();

        for (DiscordSquadMember member : DiscordSquadMember.getAll(DiscordSquadMember.class, DiscordSquadMember.column.UUID.is(player))) {
            squads.add(DiscordSquad.findBy(DiscordSquad.class, column.ID.is(member.getDiscordSquadId())));
        }

        return squads;
    }

    @Override
    protected void load() {
        this.id = getLong(column.ID);
        this.uuid = getUUID(column.UUID);
        this.textChannelId = getLong(column.TEXT_CHANNEL_ID);
        this.voiceChannelId = getLong(column.VOICE_CHANNEL_ID);
        this.categoryId = getLong(column.CATEGORY_ID);
        this.roleId = getLong(column.ROLE_ID);
        this.name = getString(column.NAME);
        this.color = getEnum(column.COLOR, Color.class);
        this.createdAt = getDate(column.CREATED_AT, DateUtils.DATE_TIME_FORMAT);
    }

    @Override
    public void insert() {
        setupReloadAfterInsert(
            localIdentifiers(column.UUID)
        );

        super.insert();
    }

    @Override
    public void delete() {
        super.delete();

        for (DiscordSquadMember member : DiscordSquadMember.getAll(DiscordSquadMember.class, DiscordSquadMember.column.DISCORD_SQUAD_ID.is(this.id))) {
            member.delete();
        }

        for (DiscordSquadInvite invite : DiscordSquadInvite.getAll(DiscordSquadInvite.class, DiscordSquadInvite.column.DISCORD_SQUAD_ID.is(this.id))) {
            invite.delete();
        }
    }

    @Override
    protected MySQLTable getTable() {
        return TABLE;
    }

    @Override
    protected ModelSelector[] getUniqueIdentifier() {
        return localIdentifiers(column.ID);
    }

    @Override
    protected column getSortedIdentifier() {
        return column.CREATED_AT;
    }

    @Override
    protected String stringifyValue(column column) {
        switch (column) {

            case ID:
                return stringify(this.id);
            case UUID:
                return stringify(this.uuid);
            case TEXT_CHANNEL_ID:
                return stringify(this.textChannelId);
            case VOICE_CHANNEL_ID:
                return stringify(this.voiceChannelId);
            case CATEGORY_ID:
                return stringify(this.categoryId);
            case ROLE_ID:
                return stringify(this.roleId);
            case NAME:
                return stringify(this.name);
            case COLOR:
                return stringify(this.color);
            case CREATED_AT:
                return stringify(this.createdAt, DateUtils.DATE_TIME_FORMAT);
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

        ID(new MySQLColumnKey("id", Column.Type.BIGINT, ColumnKey.Key.PRIMARY).autoIncrement()),
        UUID(new MySQLColumn("uuid", Column.Type.VARCHAR, 36).indexed()),

        TEXT_CHANNEL_ID(new MySQLColumn("text_channel_id", Column.Type.BIGINT).indexed()),
        VOICE_CHANNEL_ID(new MySQLColumn("voice_channel_id", Column.Type.BIGINT)),
        CATEGORY_ID(new MySQLColumn("category_id", Column.Type.BIGINT)),
        ROLE_ID(new MySQLColumn("role_id", Column.Type.BIGINT)),

        NAME(new MySQLColumn("name", Column.Type.VARCHAR)),
        COLOR(new MySQLColumn("color", Column.Type.VARCHAR)),
        CREATED_AT(new MySQLColumn("created_at", Column.Type.DATETIME));

        @Getter private final MySQLColumnInstance column;
    }

    public class Setup<P extends PlayerInstance & Languageable> {

        private final OMDiscordBot bot;
        private final P player;
        private final Guild guild;
        private final Guild controller;

        private Role role;
        private Category category;
        private TextChannel textChannel;
        private VoiceChannel voiceChannel;

        public Setup(OMDiscordBot bot, P player) {
            this.bot = bot;
            this.player = player;
            this.guild = bot.getGuild();
            this.controller = bot.getController();
        }

        public void start() {
            DiscordUser discordUser = DiscordUser.findBy(DiscordUser.class, DiscordUser.column.UUID.is(player.getUUID()));
            if (discordUser == null || discordUser.getDiscordUser(bot) == null) {
                player.sendMessage("Discord", Color.ERROR, "global", "player.discord_squad.setup.no_linked_account");
                return;
            }

            player.sendMessage("Discord", Color.INFO, "global", "player.discord_squad.setup.start");

            if (!DiscordSquad.this.isInserted())
                DiscordSquad.this.insert();

            setupRole();
        }

        private void setupRole() {
            if (DiscordSquad.this.roleId != null) {
                this.role = DiscordSquad.this.getRole(bot);
                setupCategory();
                return;
            }

            createRole().queue(role -> {
                this.role = role;

                player.sendMessage("Discord", Color.INFO, "global", "player.discord_squad.setup.created_role");

                DiscordSquad.this.setRoleId(role.getIdLong());
                DiscordSquad.this.update(column.ROLE_ID);

                setupCategory();
            }, throwable -> onError(throwable, "Role"));
        }

        private void setupCategory() {
            if (DiscordSquad.this.categoryId != null) {
                this.category = DiscordSquad.this.getCategory(bot);
                setupTextChannel();
                return;
            }

            createCategory(role).queue(category -> {
                this.category = (Category) category;

                player.sendMessage("Discord", Color.INFO, "global", "player.discord_squad.setup.created_category");

                DiscordSquad.this.setCategoryId(this.category.getIdLong());
                DiscordSquad.this.update(column.CATEGORY_ID);

                setupTextChannel();
            }, (Throwable throwable) -> onError(throwable, "Category"));
        }

        private void setupTextChannel() {
            if (DiscordSquad.this.textChannelId != null) {
                this.textChannel = DiscordSquad.this.getTextChannel(bot);
                setupVoiceChannel();
                return;
            }

            createTextChannel(category).queue(textChannel -> {
                this.textChannel = (TextChannel) textChannel;

                player.sendMessage("Discord", Color.INFO, "global", "player.discord_squad.setup.created_text_channel");

                DiscordSquad.this.setTextChannelId(this.textChannel.getIdLong());
                DiscordSquad.this.update(column.TEXT_CHANNEL_ID);

                setupVoiceChannel();
            }, (Throwable throwable) -> onError(throwable, "Text Channel"));
        }

        private void setupVoiceChannel() {
            if (DiscordSquad.this.voiceChannelId != null) {
                this.voiceChannel = DiscordSquad.this.getVoiceChannel(bot);

                finalizeSetup();
                return;
            }

            createVoiceChannel(category).queue(voiceChannel -> {
                this.voiceChannel = (VoiceChannel) voiceChannel;

                player.sendMessage("Discord", Color.INFO, "global", "player.discord_squad.setup.created_voice_channel");

                DiscordSquad.this.setVoiceChannelId(this.voiceChannel.getIdLong());
                DiscordSquad.this.update(column.VOICE_CHANNEL_ID);

                finalizeSetup();
            }, (Throwable throwable) -> onError(throwable, "Voice Channel"));
        }

        private void finalizeSetup() {
            DiscordUser discordUser = DiscordUser.findBy(DiscordUser.class, DiscordUser.column.UUID.is(player.getUUID()));
            discordUser.updateDiscordRanks(bot);

            bot.withPlayerEmote(player.getUUID(), player.getRawName(), false, emote -> {
                getLogChannel(bot).sendMessage("Successfully created **Discord Squad** for " + bot.getPlayerDisplay(player, emote, player.getRawName()) + " » " + role.getAsMention() + " » " + textChannel.getAsMention() + ".").queue();
            });

            File file = Image.ORBITMINES_LOGO.getFile("orbitmines_logo");
            if (file != null)
                textChannel.sendFiles(net.dv8tion.jda.api.utils.FileUpload.fromData(file)).queue();

            User user = discordUser.getDiscordUser(bot);

            textChannel.sendMessage("Welcome to " + role.getAsMention() + " " + user.getAsMention() + "!").queue();
            textChannel.sendMessage(" • Use **/discordsquad** in game to manage your squad").queue();
            textChannel.sendMessage(" • In order to type in your squad type **!<message>** in game.").queue();
            textChannel.sendMessage(" • Use **!list** in Discord to view all online players in game.").queue();

            player.sendMessage("Discord", Color.INFO, "global", "player.discord_squad.setup.ready", "§9§lDiscord Squad§7");
        }

        private void onError(Throwable throwable, String type) {
            throwable.printStackTrace();

            player.sendMessage("Discord", Color.ERROR, "global", "player.discord_squad.setup.error");
            DiscordSquad.this.onError(bot, type, "creating");
        }

        private RoleAction createRole() {
            return controller.createRole().
                setName(DiscordSquad.this.name).
                setColor(DiscordSquad.this.color.getAwtColor()).
                setMentionable(true);
        }

        @SuppressWarnings("unchecked")
        private ChannelAction<Category> createCategory(Role role) {
            Collection<Permission> everyone = Collections.singletonList(Permission.VIEW_CHANNEL);
            Collection<Permission> read = Collections.singletonList(Permission.VIEW_CHANNEL);

            return controller.createCategory(generateCategoryName())
                .addPermissionOverride(role, read, new ArrayList<>())
                .addPermissionOverride(bot.getRole(CustomRole.OWNER), read, new ArrayList<>())
                .addPermissionOverride(bot.getRole(CustomRole.ADMIN), read, new ArrayList<>())
                .addPermissionOverride(controller.getPublicRole(), new ArrayList<>(), everyone);
        }

        @SuppressWarnings("unchecked")
        private ChannelAction<TextChannel> createTextChannel(Category category) {
            return controller.createTextChannel(DiscordSquad.this.name.toLowerCase()).
                setParent(category);
        }

        @SuppressWarnings("unchecked")
        private ChannelAction<VoiceChannel> createVoiceChannel(Category category) {
            return controller.createVoiceChannel(DiscordSquad.this.name).
                setParent(category);
        }
    }

    public class Destroy<P extends PlayerInstance & Languageable> {

        private final OMDiscordBot bot;
        private final P player;

        private Role role;
        private Category category;
        private TextChannel textChannel;
        private VoiceChannel voiceChannel;

        public Destroy(OMDiscordBot bot, P player) {
            this.bot = bot;
            this.player = player;
            
            this.role = getRole(bot);
            this.category = getCategory(bot);
            this.textChannel = getTextChannel(bot);
            this.voiceChannel = getVoiceChannel(bot);
        }

        public void start() {
            player.sendMessage("Discord", Color.INFO, "global", "player.discord_squad.deletion.start");

            destroyVoiceChannel();
        }

        private void destroyVoiceChannel() {
            if (this.voiceChannel == null) {
                archiveTextChannel();
                return;
            }

            voiceChannel.delete().queue(voiceChannel -> {
                player.sendMessage("Discord", Color.INFO, "global", "player.discord_squad.deletion.deleted_voice_channel");

                DiscordSquad.this.setVoiceChannelId(null);
                DiscordSquad.this.update(column.VOICE_CHANNEL_ID);

                archiveTextChannel();
            }, throwable -> onError(throwable, "Voice Channel"));
        }

        private void archiveTextChannel() {
            if (this.textChannel == null) {
                destroyCategory();
                return;
            }

            textChannel.getManager().
                setName("archive_" + DiscordSquad.this.uuid.toString() + "_" + DateUtils.now().getTime()).
                setParent(bot.getCategory("ARCHIVE")).
            queue(textChannel -> {
                player.sendMessage("Discord", Color.INFO, "global", "player.discord_squad.deletion.deleted_text_channel");

                DiscordSquad.this.setTextChannelId(null);
                DiscordSquad.this.update(column.TEXT_CHANNEL_ID);

                destroyCategory();
            }, throwable -> onError(throwable, "Text Channel"));
        }

        private void destroyCategory() {
            if (this.category == null) {
                destroyRole();
                return;
            }

            category.delete().queue(category -> {
                player.sendMessage("Discord", Color.INFO, "global", "player.discord_squad.deletion.deleted_category");

                DiscordSquad.this.setCategoryId(null);
                DiscordSquad.this.update(column.CATEGORY_ID);

                destroyRole();
            }, throwable -> onError(throwable, "Category"));
        }

        private void destroyRole() {
            if (this.role == null) {
                finalizeDestroy();
                return;
            }

            role.delete().queue(category -> {
                player.sendMessage("Discord", Color.INFO, "global", "player.discord_squad.deletion.deleted_role");

                DiscordSquad.this.setRoleId(null);
                DiscordSquad.this.update(column.ROLE_ID);

                finalizeDestroy();
            }, throwable -> onError(throwable, "Role"));
        }

        private void finalizeDestroy() {
            ArrayList<DiscordSquadMember> members = DiscordSquadMember.getAll(DiscordSquadMember.class, DiscordSquadMember.column.DISCORD_SQUAD_ID.is(DiscordSquad.this.id));

            /* Destroy */
            if (!DiscordSquad.this.isDestroyed())
                DiscordSquad.this.delete();

            /* Update Ranks */
            DiscordUser discordUser = DiscordUser.findBy(DiscordUser.class, DiscordUser.column.UUID.is(player.getUUID()));
            discordUser.updateDiscordRanks(bot);

            for (DiscordSquadMember member : members) {
                discordUser = DiscordUser.findBy(DiscordUser.class, DiscordUser.column.UUID.is(member.getUuid()));
                discordUser.updateDiscordRanks(bot);
            }

            /* Confirmation */
            bot.withPlayerEmote(player.getUUID(), player.getRawName(), false, emote -> {
                getLogChannel(bot).sendMessage("Successfully deleted **Discord Squad** for " + bot.getPlayerDisplay(player, emote, player.getRawName()) + " » " + role.getAsMention() + " » " + textChannel.getAsMention() + ".").queue();
            });

            player.sendMessage("Discord", Color.INFO, "global", "player.discord_squad.deletion.ready", "§9§lDiscord Squad§7");
        }

        private void onError(Throwable throwable, String type) {
            throwable.printStackTrace();

            player.sendMessage("Discord", Color.ERROR, "global", "player.discord_squad.deletion.error");
            DiscordSquad.this.onError(bot, type, "deleting");
        }
    }
}
