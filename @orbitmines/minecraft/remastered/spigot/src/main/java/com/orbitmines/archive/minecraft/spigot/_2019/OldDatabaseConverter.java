package com.orbitmines.archive.minecraft.spigot._2019;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.google.gson.JsonObject;
import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.database.models.*;
import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.DiscordUser;
import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.squad.DiscordSquad;
import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.squad.DiscordSquadInvite;
import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.squad.DiscordSquadMember;
import com.orbitmines.archive.minecraft._2019.libs.database.models.friend.Friend;
import com.orbitmines.archive.minecraft._2019.libs.database.models.friend.FriendInvite;
import com.orbitmines.archive.minecraft._2019.libs.database.models.loot.LootItem;
import com.orbitmines.archive.minecraft._2019.libs.database.models.loot.PeriodLootItem;
import com.orbitmines.archive.minecraft._2019.libs.database.models.punishment.Pardon;
import com.orbitmines.archive.minecraft._2019.libs.database.models.punishment.Punishment;
import com.orbitmines.archive.minecraft._2019.libs.database.models.punishment.Report;
import com.orbitmines.archive.minecraft._2019.libs.database.models.vote.LastVote;
import com.orbitmines.archive.minecraft._2019.libs.database.models.vote.MonthlyVotes;
import com.orbitmines.archive.minecraft._2019.libs.database.models.vote.PendingVote;
import com.orbitmines.archive.minecraft._2019.libs.loot.Rarity;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.achievements.servers.HubAchievement;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.OMMap;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.PlayerAchievement;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.*;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import com.orbitmines.archive.minecraft._2019.utils.UUIDUtils;
import com.orbitmines.archive.minecraft._2019.utils.database.DatabaseManager;
import com.orbitmines.archive.minecraft._2019.utils.database.MySQLDatabase;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.From;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Selectable;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.mysql.MySQLQueryBuilder;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumn;
import com.orbitmines.archive.minecraft._2019.utils.language.Language;

import java.time.Month;
import java.util.*;

public class OldDatabaseConverter {

    public static void main(String... args) {
        OldDatabaseConverter converter = new OldDatabaseConverter();

        converter.transferAchievements();
        converter.transferDiscordUsers();
        converter.transferDiscordUsers();
        converter.transferDiscordSquads();
        converter.transferDonations();
        converter.transferFriends();
        converter.transferIPEntries();
        converter.transferLootItems();
        converter.transferPlayers();
        converter.transferPlayTime();
        converter.transferBans();
        converter.transferVotes();
        converter.transferSurvivalModels();
    }

    private MySQLDatabase old_database;
    private MySQLDatabase new_database;

    private void transferSurvivalModels() {
        T t = new T("SurvivalPlayers");
        C uuid = new C("UUID");
        C claimBlocks = new C("ClaimBlocks");
        C backCharges = new C("BackCharges");
        C extraHomes = new C("ExtraHomes");
        C warpSlotShop = new C("WarpSlotShop");
        C warpSlotPrisms = new C("WarpSlotPrisms");

        for (LinkedHashMap<Selectable, Object> entry : getOldEntries(t, uuid, claimBlocks, backCharges, extraHomes, warpSlotShop, warpSlotPrisms)) {
            UUID primary = UUID.fromString((String) entry.get(uuid));

            SurvivalPlayerModel newEntry = new SurvivalPlayerModel(
                primary,
                175,
                (Integer) entry.get(claimBlocks),
                null,
                (Integer) entry.get(backCharges),
                null,
                false,
                null,
                (Integer) entry.get(extraHomes),
                (Boolean) entry.get(warpSlotShop),
                (Boolean) entry.get(warpSlotPrisms)
            );
            newEntry.insert();
        }
    }

    private void transferVotes() {
        T t = new T("Votes");
        C uuid = new C("UUID");
        C july2018 = new C("July2018");
        C august2018 = new C("August2018");
        C september2018 = new C("September2018");
        C october2018 = new C("October2018");
        C november2018 = new C("November2018");
        C december2018 = new C("December2018");
        C january2019 = new C("January2019");
        C february2019 = new C("February2019");
        C march2019 = new C("March2019");
        C april2019 = new C("April2019");
        C cachedVotes = new C("CachedVotes");

        for (LinkedHashMap<Selectable, Object> entry : getOldEntries(t, uuid, july2018, august2018, september2018, october2018, november2018, december2018, january2019, february2019, march2019, april2019, cachedVotes)) {
            UUID primary = UUID.fromString((String) entry.get(uuid));

            transferRelevantMonthlyVotes(primary, Month.JULY, 2018,  entry.get(july2018));
            transferRelevantMonthlyVotes(primary, Month.AUGUST, 2018,  entry.get(august2018));
            transferRelevantMonthlyVotes(primary, Month.SEPTEMBER, 2018,  entry.get(september2018));
            transferRelevantMonthlyVotes(primary, Month.OCTOBER, 2018,  entry.get(october2018));
            transferRelevantMonthlyVotes(primary, Month.NOVEMBER, 2018,  entry.get(november2018));
            transferRelevantMonthlyVotes(primary, Month.DECEMBER, 2018,  entry.get(december2018));
            transferRelevantMonthlyVotes(primary, Month.JANUARY, 2019,  entry.get(january2019));
            transferRelevantMonthlyVotes(primary, Month.FEBRUARY, 2019,  entry.get(february2019));
            transferRelevantMonthlyVotes(primary, Month.MARCH, 2019,  entry.get(march2019));
            transferRelevantMonthlyVotes(primary, Month.APRIL, 2019,  entry.get(april2019));

            for (int i = 0; i < (Integer) entry.get(cachedVotes); i++) {
                new PendingVote(primary).insert();
            }
        }
    }
    
    private void transferRelevantMonthlyVotes(UUID uuid, Month month, int year, Object object) {
        if (object == null)
            return;

        int votes = (Integer) object;

        if (votes == 0)
            return;

        MonthlyVotes newEntry = new MonthlyVotes(
            uuid,
            month,
            year,
            votes
        );
        newEntry.insert();
    }

    private void transferBans() {
        new Punishment(
            null,
            UUID.fromString("ec777c46-b563-4e1a-ad52-ff0b1c41d08c"),
            Punishment.Type.BAN,
            UUID.fromString("33ee168b-5c2c-42c5-b3b2-d841ceb76b70"),
            DateUtils.parse("2018-08-23 15:52:31", DateUtils.DATE_TIME_FORMAT),
            DateUtils.parse("2068-08-23 15:52:31", DateUtils.DATE_TIME_FORMAT),
            Punishment.Duration.PERMANENT,
            null,
            "Advertising through DM.",
            false
        ).insert();
        new Punishment(
            null,
            UUID.fromString("488f5006-8908-4e65-82bb-4b2da847d705"),
            Punishment.Type.BAN,
            UUID.fromString("33ee168b-5c2c-42c5-b3b2-d841ceb76b70"),
            DateUtils.parse("2018-08-25 14:59:56", DateUtils.DATE_TIME_FORMAT),
            DateUtils.parse("2068-08-25 14:59:56", DateUtils.DATE_TIME_FORMAT),
            Punishment.Duration.PERMANENT,
            null,
            "Inappropriate behaviore&advertising",
            false
        ).insert();
        new Punishment(
            null,
            UUID.fromString("f9977973-e981-4d03-90b6-126480de297d"),
            Punishment.Type.BAN,
            UUID.fromString("6625bd11-1a1f-4bd0-b46f-a49fb9c71e7d"),
            DateUtils.parse("2018-09-05 16:34:53", DateUtils.DATE_TIME_FORMAT),
            DateUtils.parse("2068-09-05 16:34:53", DateUtils.DATE_TIME_FORMAT),
            Punishment.Duration.PERMANENT,
            null,
            "Advertising",
            false
        ).insert();
    }

    private void transferPlayTime() {
        T t = new T("PlayTime");
        C uuid = new C("UUID");
        C kitpvp = new C("KITPVP");
        C survival = new C("SURVIVAL");
        C hub = new C("HUB");

        for (LinkedHashMap<Selectable, Object> entry : getOldEntries(t, uuid, kitpvp, survival, hub)) {
            UUID primary = UUID.fromString((String) entry.get(uuid));

            insertPlayTime(primary, Server.KITPVP, (Long) entry.get(kitpvp));
            insertPlayTime(primary, Server.SURVIVAL, (Long) entry.get(survival));
            insertPlayTime(primary, Server.HUB, (Long) entry.get(hub));
        }
    }

    private void insertPlayTime(UUID uuid, Server server, Long seconds) {
        if (seconds == null || seconds == 0)
            return;

        TimePlayed newEntry = new TimePlayed(
            uuid,
            server,
            seconds
        );
        newEntry.insert();
    }

    private void transferPlayers() {
        T t = new T("Players");
        C uuid = new C("UUID");
        C name = new C("Name");
        C nick = new C("Nick");
        C staffRank = new C("StaffRank");
        C vipRank = new C("VipRank");
        C firstLogin = new C("FirstLogin");
        C language = new C("Language");
        C solars = new C("Solars");
        C prisms = new C("Prisms");

        C scoreboard = new C("Scoreboard");
        C privateMessages = new C("PrivateMessages");
        C playerVisibility = new C("PlayerVisibility");
        C stats = new C("Stats");

        for (LinkedHashMap<Selectable, Object> entry : getOldEntries(t, uuid, name, nick, staffRank, vipRank, firstLogin, language, scoreboard, privateMessages, playerVisibility, stats, solars, prisms)) {
            String nickName = (String) entry.get(nick);
            if (nickName.isEmpty())
                nickName = null;

            PlayerModel newEntry = new PlayerModel(
                UUID.fromString((String) entry.get(uuid)),
                (String) entry.get(name),
                nickName,
                StaffRank.valueOf((String) entry.get(staffRank)),
                VipRank.valueOf((String) entry.get(vipRank)),
                Language.valueOf((String) entry.get(language)),
                (Integer) entry.get(solars),
                (Integer) entry.get(prisms),
                (Date) entry.get(firstLogin)
            );
            newEntry.insert();

            if ((Integer) entry.get(scoreboard) == 0)
                transferSettingIfNecessary(newEntry, PlayerSettings.Type.SCOREBOARD_VISIBILITY, PlayerSettings.Level.DISABLED.toString());

            transferSettingIfNecessary(newEntry, PlayerSettings.Type.PRIVATE_MESSAGES, (String) entry.get(privateMessages));
            transferSettingIfNecessary(newEntry, PlayerSettings.Type.PLAYER_VISIBILITY, (String) entry.get(playerVisibility));
            transferSettingIfNecessary(newEntry, PlayerSettings.Type.STATS, (String) entry.get(stats));
        }
    }

    private void transferSettingIfNecessary(PlayerModel model, PlayerSettings.Type type, String value) {
        PlayerSettings.Level level = PlayerSettings.Level.valueOf(value);

        if (level == PlayerSettings.Level.ENABLED)
            return;

        if (type == PlayerSettings.Type.STATS && level == PlayerSettings.Level.ONLY_FRIENDS)
            return;

        PlayerSettings newEntry = new PlayerSettings(
            model.getUUID(),
            type,
            level
        );

        newEntry.insert();
    }

    private void transferLootItems() {
        T t = new T("Loot");
        C uuid = new C("UUID");
        C loot = new C("Loot");
        C rarity = new C("Rarity");
        C count = new C("Count");
        C description = new C("Description");

        for (LinkedHashMap<Selectable, Object> entry : getOldEntries(t, uuid, loot, rarity, count, description)) {
            if (((String) entry.get(loot)).equals("SURVIVAL_CREDITS"))
                continue;

            LootItem newEntry = new LootItem(
                null,
                UUID.fromString((String) entry.get(uuid)),
                LootItem.Type.valueOf((String) entry.get(loot)),
                Rarity.valueOf((String) entry.get(rarity)),
                (Integer) entry.get(count),
                Collections.singletonList(((String) entry.get(description)).replaceAll("&", "§")),
                DateUtils.now()
            );
            newEntry.insert();
        }
    }

    private void transferIPEntries() {
        T t = new T("IPs");
        C uuid = new C("UUID");
        C lastLogin = new C("LastLogin");
        C history = new C("History");

        for (LinkedHashMap<Selectable, Object> entry : getOldEntries(t, uuid, lastLogin, history)) {
            UUID primary = UUID.fromString((String) entry.get(uuid));

            for (String ipEntry : ((String) entry.get(history)).split("\\|")) {
                String ip;
                Date date;

                if (ipEntry.contains("~")) {
                    ip = ipEntry.split("~")[0];

                    if (!ipEntry.split("~")[1].equals("UNKNOWN"))
                        date = DateUtils.parse(ipEntry.split("~")[1], DateUtils.DATE_TIME_FORMAT);
                    else
                        date = (Date) entry.get(lastLogin);
                } else {
                    ip = ipEntry;
                    date = (Date) entry.get(lastLogin);
                }

                IPEntry newEntry = new IPEntry(
                    null,
                    primary,
                    ip,
                    date,
                    IPEntry.ProtocolVersion.MINECRAFT_1_13_2,
                    date,
                    new JsonObject()
                );
                newEntry.insert();
            }
        }
    }

    private void transferFriends() {
        T t = new T("Friends");
        C uuid = new C("UUID");
        C f = new C("Friends");
        C ff = new C("Favorite");

        for (LinkedHashMap<Selectable, Object> entry : getOldEntries(t, uuid, f, ff)) {
            UUID primary = UUID.fromString((String) entry.get(uuid));

            List<UUID> friends = UUIDUtils.parseUUIDList(Arrays.asList(((String) entry.get(f)).split("\\|")));
            List<UUID> favorites = UUIDUtils.parseUUIDList(Arrays.asList(((String) entry.get(ff)).split("\\|")));

            for (UUID friendUuid : friends) {
                Friend newEntry = new Friend(primary, friendUuid, favorites.contains(friendUuid), DateUtils.now());
                newEntry.insert();
            }
        }
    }

    private void transferDonations() {
        T t = new T("Donations");
        C uuid = new C("UUID");
        C packageID = new C("PackageID");
        C amount = new C("AmountSpent");
        C currency = new C("Currency");
        C date = new C("Date");

        for (LinkedHashMap<Selectable, Object> entry : getOldEntries(t, uuid, packageID, amount, currency, date)) {
            Donation newEntry = new Donation(
                null,
                UUID.fromString((String) entry.get(uuid)),
                (Integer) entry.get(packageID),
                (Double) entry.get(amount),
                (String) entry.get(currency),
                (Date) entry.get(date)
            );
            newEntry.insert();
        }
    }

    private void transferDiscordSquads() {
        T t = new T("DiscordGroup");
        C uuid = new C("UUID");
        C catergoryId = new C("CategoryId");
        C textChannelId = new C("TextChannelId");
        C voiceChannelId = new C("VoiceChannelId");
        C roleId = new C("RoleId");
        C name = new C("Name");
        C color = new C("Color");
        C members = new C("Members");

        for (LinkedHashMap<Selectable, Object> entry : getOldEntries(t, uuid, catergoryId, textChannelId, voiceChannelId, roleId, name, color, members)) {
            DiscordSquad squad = new DiscordSquad(
                null,
                UUID.fromString((String) entry.get(uuid)),
                (Long) entry.get(textChannelId),
                (Long) entry.get(voiceChannelId),
                (Long) entry.get(catergoryId),
                (Long) entry.get(roleId),
                (String) entry.get(name),
                Color.valueOf((String) entry.get(color)),
                DateUtils.now()
            );
            squad.insert();

            for (String memberUuid : ((String) entry.get(members)).split("\\|")) {
                if (memberUuid.isEmpty())
                    continue;

                DiscordSquadMember member = new DiscordSquadMember(UUID.fromString(memberUuid), squad.getId(), false);
                member.insert();
            }
        }
    }

    private void transferDiscordUsers() {
        T t = new T("Discord");
        C uuid = new C("UUID");
        C id = new C("Id");

        for (LinkedHashMap<Selectable, Object> entry : getOldEntries(t, uuid, id)) {
            DiscordUser newEntry = new DiscordUser(UUID.fromString((String) entry.get(uuid)), (Long) entry.get(id));
            newEntry.insert();
        }
    }

    private void transferAchievements() {
        T t = new T("Achievements");
        C uuid = new C("UUID");
        C server = new C("Server");
        C achievement = new C("Achievement");
        C completed = new C("Completed");
        C progress = new C("progress");

        for (LinkedHashMap<Selectable, Object> entry : getOldEntries(t, uuid, server, achievement, completed, progress)) {
            if (!entry.get(server).equals("HUB"))
                continue;

            PlayerAchievement newEntry = new PlayerAchievement(
                UUID.fromString((String) entry.get(uuid)),
                Server.HUB,
                HubAchievement.valueOf((String) entry.get(achievement)),
                (Integer) entry.get(completed) == 1,
                (long) (Integer) entry.get(progress)
            );
            newEntry.insert();
        }
    }

    private ArrayList<LinkedHashMap<Selectable, Object>> getOldEntries(T t, C... c) {
        return old_database.getEntries(new MySQLQueryBuilder(t), c);
    }

    private ArrayList<LinkedHashMap<Selectable, Object>> getEntries(MySQLDatabase database, MySQLQueryBuilder builder, Selectable... selectable) {
        return database.getEntries(builder, selectable);
    }

    public OldDatabaseConverter() {
        old_database = new MySQLDatabase(
            "localhost",
            3308,
            "old_om",
            "orbitmines",
            "password",
            "?useUnicode=true&amp;useJDBCCompliantTimezoneShift=true&amp;serverTimezone=UTC"
        );
        new_database = new MySQLDatabase(
            "localhost",
            3308,
            "orbitmines_production",
            "orbitmines",
            "password",
            "?useUnicode=true&amp;useJDBCCompliantTimezoneShift=true&amp;serverTimezone=UTC"
        );
        // DatabaseManager.getInstance().setDefaultDatabase(new_database);
        registerTables();
    }

    private void registerTables() {
        new_database.registerTables(
            PlayerModel.TABLE,

            DiscordSquad.TABLE,
            DiscordSquadInvite.TABLE,
            DiscordSquadMember.TABLE,
            DiscordUser.TABLE,

            Friend.TABLE,
            FriendInvite.TABLE,

            LootItem.TABLE,
            PeriodLootItem.TABLE,

            Pardon.TABLE,
            Punishment.TABLE,
            Report.TABLE,

            LastVote.TABLE,
            MonthlyVotes.TABLE,
            PendingVote.TABLE,

            Donation.TABLE,
            IPEntry.TABLE,
            PlayerAuth.TABLE,
            PlayerSettings.TABLE,
            TimePlayed.TABLE,

            OMMap.TABLE,
            PlayerAchievement.TABLE,

            SurvivalPlayerModel.TABLE,
            SurvivalClaim.TABLE,
            SurvivalClaimMember.TABLE,
            SurvivalChestShop.TABLE,
            SurvivalHome.TABLE,
            SurvivalWarp.TABLE,
            SurvivalFavoriteWarp.TABLE
        );
        new_database.setupTables();
    }

    public class T implements From {

        private final String name;

        public T(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        public boolean containsColumn(Selectable c) {
            return true;
        }
    }

    public class C extends MySQLColumn {

        public C(String name) {
            super(name, Type.BIGINT);
        }
    }
}
