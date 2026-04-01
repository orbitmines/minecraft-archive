package com.orbitmines.archive.minecraft._2019.libs.database.models.discord.squad;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.database.mysql.OMMySQLModel;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Column;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumn;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumnInstance;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLTable;
import com.orbitmines.archive.minecraft._2019.utils.database.model.ModelSelector;
import com.orbitmines.archive.minecraft._2019.utils.database.model.mysql.MySQLModelColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class DiscordSquadMember extends OMMySQLModel<DiscordSquadMember, DiscordSquadMember.column> {

    public static MySQLTable TABLE = new MySQLTable("discord_squad_members", MySQLModelColumn.toColumns(column.values()));

    @Getter private UUID uuid;
    @Getter private long discordSquadId;
    @Getter @Setter private boolean selected;

    public DiscordSquadMember() {
    }

    public DiscordSquadMember(UUID uuid, long discordSquadId, boolean selected) {
        this.uuid = uuid;
        this.discordSquadId = discordSquadId;
        this.selected = selected;
    }

    @Override
    protected void load() {
        this.uuid = getUUID(column.UUID);
        this.discordSquadId = getLong(column.DISCORD_SQUAD_ID);
        this.selected = getBoolean(column.SELECTED);
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
        throw new UnsupportedOperationException();
    }

    @Override
    protected String stringifyValue(column column) {
        switch (column) {

            case UUID:
                return stringify(this.uuid);
            case DISCORD_SQUAD_ID:
                return stringify(this.discordSquadId);
            case SELECTED:
                return stringify(this.selected);
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
        SELECTED(new MySQLColumn("selected", Column.Type.TINYINT, 1).indexed());

        @Getter private final MySQLColumnInstance column;
    }
}
