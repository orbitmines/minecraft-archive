package com.orbitmines.archive.minecraft._2019.libs.database.models;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.google.gson.JsonObject;
import com.orbitmines.archive.minecraft._2019.libs.database.mysql.OMMySQLModel;
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

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
public class IPEntry extends OMMySQLModel<IPEntry, IPEntry.column> {

    public static MySQLTable TABLE = new MySQLTable("ip_entries", MySQLModelColumn.toColumns(column.values()));

    @Getter private Long id;
    @Getter private UUID uuid;
    @Getter private String ip;
    @Getter private Date loginAt;
    @Getter private int protocolVersion;
    @Getter @Setter private Date logoutAt;
    @Getter private JsonObject data;

    public IPEntry() {
    }

    public IPEntry(UUID uuid, String ip, int protocolVersion) {
        this.uuid = uuid;
        this.ip = ip;
        this.protocolVersion = protocolVersion;
        this.loginAt = DateUtils.now();
        this.data = new JsonObject();
    }

    @Override
    protected void load() {
        this.id = getLong(column.ID);
        this.uuid = getUUID(column.UUID);
        this.ip = getString(column.IP);
        this.protocolVersion = getInteger(column.PROTOCOL_VERSION);
        this.loginAt = getDate(column.LOGIN_AT, DateUtils.DATE_TIME_FORMAT);
        this.logoutAt = getDate(column.LOGOUT_AT, DateUtils.DATE_TIME_FORMAT);
        this.data = getJson(column.DATA).getAsJsonObject();
    }

    @Override
    public void insert() {
        setupReloadAfterInsert(
            localIdentifiers(column.UUID)
        );

        super.insert();
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
        return column.LOGIN_AT;
    }

    @Override
    protected String stringifyValue(column column) {
        switch (column) {

            case ID:
                return stringify(this.id);
            case UUID:
                return stringify(this.uuid);
            case IP:
                return stringify(this.ip);
            case PROTOCOL_VERSION:
                return stringify(this.protocolVersion);
            case LOGIN_AT:
                return stringify(this.loginAt, DateUtils.DATE_TIME_FORMAT);
            case LOGOUT_AT:
                return stringify(this.logoutAt, DateUtils.DATE_TIME_FORMAT);
            case DATA:
                return stringify(this.data);
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

        IP(new MySQLColumn("ip", Column.Type.VARCHAR).indexed()),
        PROTOCOL_VERSION(new MySQLColumn("protocol_version", Column.Type.INT).indexed()),
        LOGIN_AT(new MySQLColumn("login_at", Column.Type.DATETIME).indexed()),
        LOGOUT_AT(new MySQLColumn("logout_at", Column.Type.DATETIME).indexed()),
        DATA(new MySQLColumn("data", Column.Type.MEDIUMTEXT));

        @Getter private final MySQLColumnInstance column;
    }

    public static class ProtocolVersion {

        public static final int MINECRAFT_1_8 = 47;
        public static final int MINECRAFT_1_9 = 107;
        public static final int MINECRAFT_1_9_1 = 108;
        public static final int MINECRAFT_1_9_2 = 109;
        public static final int MINECRAFT_1_9_4 = 110;
        public static final int MINECRAFT_1_10 = 210;
        public static final int MINECRAFT_1_11 = 315;
        public static final int MINECRAFT_1_11_1 = 316;
        public static final int MINECRAFT_1_12 = 335;
        public static final int MINECRAFT_1_12_1 = 338;
        public static final int MINECRAFT_1_12_2 = 340;
        public static final int MINECRAFT_1_13 = 393;
        public static final int MINECRAFT_1_13_1 = 401;
        public static final int MINECRAFT_1_13_2 = 404;
        public static final int MINECRAFT_1_14 = 477;
        public static final int MINECRAFT_1_14_1 = 480;

        public static final int FIRST_SUPPORTED_VERSION = MINECRAFT_1_14_1;

        public static String humanReadableVersion(int version) {
            if (version >= MINECRAFT_1_14_1)
                return "1.14.1";
            else if (version >= MINECRAFT_1_14)
                return "1.14";
            else if (version >= MINECRAFT_1_13_2)
                return "1.13.2";
            else if (version >= MINECRAFT_1_13_1)
                return "1.13.1";
            else if (version >= MINECRAFT_1_13)
                return "1.13";
            else if (version >= MINECRAFT_1_12_2)
                return "1.12.2";
            else if (version >= MINECRAFT_1_12_1)
                return "1.12.1";
            else if (version >= MINECRAFT_1_12)
                return "1.12";
            else if (version >= MINECRAFT_1_11_1)
                return "1.11.1";
            else if (version >= MINECRAFT_1_11)
                return "1.11";
            else if (version >= MINECRAFT_1_10)
                return "1.10";
            else if (version >= MINECRAFT_1_9_4)
                return "1.9.4";
            else if (version >= MINECRAFT_1_9_2)
                return "1.9.2";
            else if (version >= MINECRAFT_1_9_1)
                return "1.9.1";
            else if (version >= MINECRAFT_1_9)
                return "1.9";
            else if (version >= MINECRAFT_1_8)
                return "1.8";
            else
                return null;
        }
    }
}
