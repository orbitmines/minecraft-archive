package com.orbitmines.archive.minecraft._2019.libs.database.models.loot;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.database.mysql.OMMySQLModel;
import com.orbitmines.archive.minecraft._2019.libs.discord.CustomChannel;
import com.orbitmines.archive.minecraft._2019.libs.loot.Rarity;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Column;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.ColumnKey;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumn;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumnInstance;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumnKey;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLTable;
import com.orbitmines.archive.minecraft._2019.utils.database.model.ModelSelector;
import com.orbitmines.archive.minecraft._2019.utils.database.model.mysql.MySQLModelColumn;
import com.orbitmines.archive.minecraft._2019.utils.serializers.StringListSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

@AllArgsConstructor
public class LootItem extends OMMySQLModel<LootItem, LootItem.column> {

    public static MySQLTable TABLE = new MySQLTable("loot_items", MySQLModelColumn.toColumns(column.values()));

    @Getter private Long id;
    @Getter private UUID uuid;
    @Getter private Type type;
    @Getter private Rarity rarity;
    @Getter private int count;
    @Getter private List<String> description;
    @Getter private Date createdAt;

    public LootItem() {
    }

    public LootItem(Type type, Rarity rarity, int count, String... description) {
        this(null, type, rarity, count, description);
    }

    public LootItem(UUID uuid, Type type, Rarity rarity, int count, String... description) {
        this(uuid, type, rarity, count, new ArrayList<>(Arrays.asList(description)));
    }

    public LootItem(UUID uuid, Type type, Rarity rarity, int count, List<String> description) {
        this.uuid = uuid;
        this.type = type;
        this.rarity = rarity;
        this.count = count;
        this.description = description;
        this.createdAt = DateUtils.now();
    }

    public LootItem copyFor(UUID uuid) {
        return new LootItem(uuid, this.type, this.rarity, this.count, new ArrayList<>(this.description));
    }

    public boolean isSimilarTo(LootItem item) {
        return
            this.type.canBeSimilar &&
            this.uuid.equals(item.uuid) &&
            this.type == item.type &&
            this.rarity == item.rarity &&
            this.description.equals(item.description);
    }

    @Override
    protected void load() {
        this.id = getLong(column.ID);
        this.uuid = getUUID(column.UUID);
        this.type = getEnum(column.TYPE, Type.class);
        this.rarity = getEnum(column.RARITY, Rarity.class);
        this.count = getInteger(column.COUNT);
        this.description = new StringListSerializer().deserialize(getJson(column.DESCRIPTION));
        this.createdAt = getDate(column.CREATED_AT, DateUtils.DATE_TIME_FORMAT);

        setupLog(LootItem.class, Server.BUNGEECORD, CustomChannel.LOOT_LOG);
    }

    @Override
    public void insert() {
        if (this.uuid == null)
            throw new IllegalStateException("Cannot insert LootItem without UUID");

        setupReloadAfterInsert(
            localIdentifiers(column.UUID, column.TYPE, column.RARITY)
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
        return column.CREATED_AT;
    }

    @Override
    protected String stringifyValue(column column) {
        switch (column) {

            case ID:
                return stringify(this.id);
            case UUID:
                return stringify(this.uuid);
            case TYPE:
                return stringify(this.type);
            case RARITY:
                return stringify(this.rarity);
            case COUNT:
                return stringify(this.count);
            case DESCRIPTION:
                return stringify(new StringListSerializer().serialize(this.description));
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

        TYPE(new MySQLColumn("type", Column.Type.VARCHAR).notNull()),
        RARITY(new MySQLColumn("rarity", Column.Type.VARCHAR).notNull()),
        COUNT(new MySQLColumn("count", Column.Type.INT)),
        DESCRIPTION(new MySQLColumn("description", Column.Type.TEXT)),
        CREATED_AT(new MySQLColumn("created_at", Column.Type.DATETIME).indexed());

        @Getter private final MySQLColumnInstance column;
    }

    @AllArgsConstructor
    public enum Type {

        DONATION(false),
        BUYCRAFT_VOUCHER(false),
        PRISMS(true),
        SOLARS(true),
        SURVIVAL_SPAWNER_MINER(false);

        @Getter private boolean canBeSimilar;

    }
}
