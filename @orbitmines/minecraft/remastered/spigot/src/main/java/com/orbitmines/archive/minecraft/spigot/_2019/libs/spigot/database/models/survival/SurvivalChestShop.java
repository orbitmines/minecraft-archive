package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

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
import com.orbitmines.archive.minecraft._2019.utils.language.Language;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.serializers.LocationSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.Date;
import java.util.UUID;

public class SurvivalChestShop extends OMMySQLModel<SurvivalChestShop, SurvivalChestShop.column> {

    public static MySQLTable TABLE = new MySQLTable("survival_chest_shops", MySQLModelColumn.toColumns(column.values()));

    @Getter protected Long id;
    @Getter protected UUID uuid;
    @Getter protected Location location;
    @Getter @Setter protected Material material;
    @Getter @Setter protected PurchaseType purchaseType;
    @Getter @Setter protected int amount;
    @Getter @Setter protected int price;
    @Getter protected int timesUsed;
    @Getter protected Date createdAt;

    public SurvivalChestShop() {
    }

    public SurvivalChestShop(UUID uuid, Location location, Material material, PurchaseType purchaseType, int amount, int price) {
        this.uuid = uuid;
        this.location = location;
        this.material = material;
        this.purchaseType = purchaseType;
        this.amount = amount;
        this.price = price;
        this.createdAt = DateUtils.now();
    }

    public void addTimeUsed() {
        this.timesUsed++;
    }

    @Override
    protected void load() {
        this.id = getLong(column.ID);
        this.uuid = getUUID(column.UUID);
        this.location = new LocationSerializer().deserialize(getJson(column.LOCATION));
        this.material = getEnum(column.MATERIAL, Material.class);
        this.purchaseType = getEnum(column.PURCHASE_TYPE, PurchaseType.class);
        this.amount = getInteger(column.AMOUNT);
        this.price = getInteger(column.PRICE);
        this.timesUsed = getInteger(column.TIMES_USED);
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
            case LOCATION:
                return stringify(new LocationSerializer().serialize(this.location));
            case MATERIAL:
                return stringify(this.material);
            case PURCHASE_TYPE:
                return stringify(this.purchaseType);
            case AMOUNT:
                return stringify(this.amount);
            case PRICE:
                return stringify(this.price);
            case TIMES_USED:
                return stringify(this.timesUsed);
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
        LOCATION(new MySQLColumn("location", Column.Type.VARCHAR).notNull()),
        MATERIAL(new MySQLColumn("material", Column.Type.VARCHAR)),
        PURCHASE_TYPE(new MySQLColumn("purchase_type", Column.Type.VARCHAR)),
        AMOUNT(new MySQLColumn("amount", Column.Type.INT)),
        PRICE(new MySQLColumn("price", Column.Type.INT)),
        TIMES_USED(new MySQLColumn("times_used", Column.Type.INT)),
        CREATED_AT(new MySQLColumn("created_at", Column.Type.DATETIME));

        @Getter private final MySQLColumnInstance column;
    }

    public enum PurchaseType {

        BUY,
        SELL;

        public String getName(Language language) {
            return language.getString("survival", "player.chest_shop.purchase_type." + toString().toLowerCase() + ".name");
        }
    }
}
