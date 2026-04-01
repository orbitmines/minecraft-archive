package com.orbitmines.archive.minecraft._2019.libs.database.models;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.DiscordUser;
import com.orbitmines.archive.minecraft._2019.libs.database.mysql.OMMySQLModel;
import com.orbitmines.archive.minecraft._2019.libs.player.PlayerInstance;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Column;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.ColumnKey;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumn;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumnInstance;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumnKey;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLTable;
import com.orbitmines.archive.minecraft._2019.utils.database.model.ModelSelector;
import com.orbitmines.archive.minecraft._2019.utils.database.model.mysql.MySQLModelColumn;
import com.orbitmines.archive.minecraft._2019.utils.language.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
public class PlayerModel extends OMMySQLModel<PlayerModel, PlayerModel.column> implements PlayerInstance {

    public static MySQLTable TABLE = new MySQLTable("players", MySQLModelColumn.toColumns(column.values()));

    @Getter private UUID uuid;
    @Getter @Setter private String name;
    @Getter @Setter private String nickName;
    @Getter @Setter private StaffRank staffRank;
    @Getter @Setter private VipRank vipRank;
    @Getter @Setter private Language language;
    @Getter private int solars;
    @Getter private int prisms;
    @Getter private Date firstLoginAt;

    public PlayerModel() {
    }

    public PlayerModel(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.nickName = null;
        this.staffRank = StaffRank.NONE;
        this.vipRank = VipRank.NONE;
        this.language = Language.ENGLISH;
        this.solars = 0;
        this.prisms = 0;
        this.firstLoginAt = DateUtils.now();
    }

    public void addSolars(int amount) {
        this.solars += amount;
    }

    public void removeSolars(int amount) {
        this.solars -= amount;
    }

    public void addPrisms(int amount) {
        this.prisms += amount;
    }

    public void removePrisms(int amount) {
        this.prisms -= amount;
    }

    @Override
    public UUID getUUID() {
        return this.uuid;
    }

    @Override
    public String getRawName() {
        return this.name;
    }

    @Override
    public DiscordUser getDiscordUser() {
        return DiscordUser.findBy(DiscordUser.class, DiscordUser.column.UUID.is(getUUID()));
    }

    @Override
    protected void load() {
        this.uuid = getUUID(column.UUID);
        this.name = getString(column.NAME);
        this.nickName = getString(column.NICK_NAME);
        this.staffRank = getEnum(column.STAFF_RANK, StaffRank.class);
        this.vipRank = getEnum(column.VIP_RANK, VipRank.class);
        this.language = getEnum(column.LANGUAGE, Language.class);
        this.solars = getInteger(column.SOLARS);
        this.prisms = getInteger(column.PRISMS);
        this.firstLoginAt = getDate(column.FIRST_LOGIN_AT, DateUtils.DATE_TIME_FORMAT);
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
        return column.FIRST_LOGIN_AT;
    }

    @Override
    protected String stringifyValue(column column) {
        switch (column) {

            case UUID:
                return stringify(this.uuid);
            case NAME:
                return stringify(this.name);
            case NICK_NAME:
                return stringify(this.nickName);
            case FIRST_LOGIN_AT:
                return stringify(this.firstLoginAt, DateUtils.DATE_TIME_FORMAT);
            case STAFF_RANK:
                return stringify(this.staffRank);
            case VIP_RANK:
                return stringify(this.vipRank);
            case LANGUAGE:
                return stringify(this.language);
            case SOLARS:
                return stringify(this.solars);
            case PRISMS:
                return stringify(this.prisms);
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
        NAME(new MySQLColumn("name", Column.Type.VARCHAR).indexed()),
        NICK_NAME(new MySQLColumn("nick_name", Column.Type.VARCHAR)),
        FIRST_LOGIN_AT(new MySQLColumn("first_login_at", Column.Type.DATETIME).notNull().indexed()),
        STAFF_RANK(new MySQLColumn("staff_rank", Column.Type.VARCHAR).indexed()),
        VIP_RANK(new MySQLColumn("vip_rank", Column.Type.VARCHAR).indexed()),
        LANGUAGE(new MySQLColumn("language", Column.Type.VARCHAR)),
        SOLARS(new MySQLColumn("solars", Column.Type.INT)),
        PRISMS(new MySQLColumn("prisms", Column.Type.INT));

        @Getter private final MySQLColumnInstance column;
    }
}
