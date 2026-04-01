package com.orbitmines.archive.minecraft._2019.libs.database.models.discord.squad;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.database.mysql.OMMySQLModel;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Column;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumn;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumnInstance;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLTable;
import com.orbitmines.archive.minecraft._2019.utils.database.model.ModelSelector;
import com.orbitmines.archive.minecraft._2019.utils.database.model.mysql.MySQLModelColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

public class DiscordSquadInvite extends OMMySQLModel<DiscordSquadInvite, DiscordSquadInvite.column> {

    public static MySQLTable TABLE = new MySQLTable("discord_squad_invites", MySQLModelColumn.toColumns(column.values()));

    @Getter private UUID uuid;
    @Getter private long discordSquadId;
    @Getter private Date invitedAt;

    public DiscordSquadInvite() {
    }

    public DiscordSquadInvite(UUID uuid, long discordSquadId, Date invitedAt) {
        this.uuid = uuid;
        this.discordSquadId = discordSquadId;
        this.invitedAt = invitedAt;
    }

    @Override
    protected void load() {
        this.uuid = getUUID(column.UUID);
        this.discordSquadId = getLong(column.DISCORD_SQUAD_ID);
        this.invitedAt = getDate(column.INVITED_AT, DateUtils.DATE_TIME_FORMAT);
    }

    @Override
    protected MySQLTable getTable() {
        return TABLE;
    }

    @Override
    protected ModelSelector[] getUniqueIdentifier() {
        return localIdentifiers(column.UUID, column.DISCORD_SQUAD_ID);
    }

    @Override
    protected column getSortedIdentifier() {
        return column.INVITED_AT;
    }

    @Override
    protected String stringifyValue(column column) {
        switch (column) {

            case UUID:
                return stringify(this.uuid);
            case DISCORD_SQUAD_ID:
                return stringify(this.discordSquadId);
            case INVITED_AT:
                return stringify(this.invitedAt, DateUtils.DATE_TIME_FORMAT);
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

        UUID(new MySQLColumn("uuid", Column.Type.VARCHAR, 36).indexed()),
        DISCORD_SQUAD_ID(new MySQLColumn("discord_squad_id", Column.Type.BIGINT).indexed()),
        INVITED_AT(new MySQLColumn("invited_at", Column.Type.DATETIME).notNull().indexed());

        @Getter private final MySQLColumnInstance column;
    }
}
