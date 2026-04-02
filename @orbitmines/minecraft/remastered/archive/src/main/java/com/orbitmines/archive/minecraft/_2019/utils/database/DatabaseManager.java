package com.orbitmines.archive.minecraft._2019.utils.database;

import com.orbitmines.archive.minecraft._2019.libs.Environment;
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
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Table;
import com.orbitmines.archive.minecraft._2019.utils.exceptions.SyncDatabaseAccessException;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.HashMap;

/*
    Created By Robin Egberts On 1/30/2019
    Copyrighted By OrbitMines ©2019
*/

//TODO: ADD CHECK() IN THE CONSTRAINT OR PERHAPS A SEPARATE FUNCTION

//TODO: PERHAPS ADD SUPPORT FOR DROPPING AND BACKING UP DATABASES!

public class DatabaseManager {

    public static int DATABASE_ORBIT_MINES = 143;

    @Getter private static DatabaseManager instance = new DatabaseManager();

    private HashMap<Integer, Database> databases = new HashMap<>();
    @Setter private SQLiteDatabase defaultDatabase;

    @Getter @Setter private AsyncChecker asyncChecker;
    @Getter @Setter private AsyncQuerier asyncQuerier;

    public void register(Database database, int id) {
        databases.put(id, database);
    }

    public Collection<Database> getDatabases() {
        return databases.values();
    }

    /* Only initialization of database is allowed in sync, the rest has to be done async */
    public SQLiteDatabase getDefault() {
        if (asyncChecker != null && !asyncChecker.isAsync())
            throw new SyncDatabaseAccessException();

        return defaultDatabase;
    }

    public SQLiteDatabase initializeDefaultDatabase() {
        if (asyncChecker != null && !asyncChecker.isAsync())
            throw new SyncDatabaseAccessException();

        String root = Environment.get("OM_ROOT", ".");
        String dbPath = Environment.get("OM_DB_PATH", root + "/.orbitmines/database/current");

        SQLiteDatabase database = new SQLiteDatabase(dbPath);

        register(database, DatabaseManager.DATABASE_ORBIT_MINES);
        defaultDatabase = database;

        return database;
    }

    public void setupDefaultDatabase(Table... tables) {
        if (asyncChecker != null && !asyncChecker.isAsync())
            throw new SyncDatabaseAccessException();

        defaultDatabase.registerTables(
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
                StateEntry.TABLE
        );
        defaultDatabase.registerTables(tables);

        defaultDatabase.setupTables();
    }
}
