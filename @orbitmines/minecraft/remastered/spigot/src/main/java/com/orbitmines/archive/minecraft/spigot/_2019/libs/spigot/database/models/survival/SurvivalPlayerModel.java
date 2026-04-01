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
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.serializers.LocationSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
public class SurvivalPlayerModel extends OMMySQLModel<SurvivalPlayerModel, SurvivalPlayerModel.column> {

    private static final int START_CREDITS = 175;
    private static final int START_CLAIM_BLOCKS = 250;

    public static MySQLTable TABLE = new MySQLTable("survival_players", MySQLModelColumn.toColumns(column.values()));

    @Getter private UUID uuid;
    @Getter private int credits;
    @Getter private int claimBlocks;
    @Getter @Setter private Location backLocation;
    @Getter private int backCharges;
    @Getter @Setter private Location logoutLocation;
    @Getter @Setter private boolean logoutFly;
    @Getter @Setter private Date lastBedEnter;
    @Getter private int extraHomes;
    @Getter @Setter private boolean warpSlotShop;
    @Getter @Setter private boolean warpSlotPrisms;

    public SurvivalPlayerModel() {
    }

    public SurvivalPlayerModel(UUID uuid) {
        this.uuid = uuid;
        this.credits = START_CREDITS;
        this.claimBlocks = START_CLAIM_BLOCKS;
        this.backLocation = null;
        this.backCharges = 0;
        this.logoutLocation = null;
        this.logoutFly = false;
        this.extraHomes = 0;
        this.warpSlotShop = false;
        this.warpSlotPrisms = false;
    }

    public void addCredits(int amount) {
        this.credits += amount;
    }

    public void removeCredits(int amount) {
        this.credits -= amount;
    }

    public void addClaimBlocks(int amount) {
        this.claimBlocks += amount;
    }

    public void addBackCharges(int amount) {
        this.backCharges += amount;
    }

    public void removeBackCharges(int amount) {
        this.backCharges -= amount;
    }

    public void addExtraHomes(int amount) {
        this.extraHomes += amount;
    }

    @Override
    protected void load() {
        this.uuid = getUUID(column.UUID);
        this.credits = getInteger(column.CREDITS);
        this.claimBlocks = getInteger(column.CLAIM_BLOCKS);
        this.backLocation = new LocationSerializer().deserialize(getJson(column.BACK_LOCATION));
        this.backCharges = getInteger(column.BACK_CHARGES);
        this.logoutLocation = new LocationSerializer().deserialize(getJson(column.LOGOUT_LOCATION));
        this.logoutFly = getBoolean(column.LOGOUT_FLY);
        this.lastBedEnter = getDate(column.LAST_BED_ENTER, DateUtils.DATE_TIME_FORMAT);
        this.extraHomes = getInteger(column.EXTRA_HOMES);
        this.warpSlotShop = getBoolean(column.WARP_SLOT_SHOP);
        this.warpSlotPrisms = getBoolean(column.WARP_SLOT_PRISMS);
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
            case CREDITS:
                return stringify(this.credits);
            case CLAIM_BLOCKS:
                return stringify(this.claimBlocks);
            case BACK_LOCATION:
                return stringify(new LocationSerializer().serialize(this.backLocation));
            case BACK_CHARGES:
                return stringify(this.backCharges);
            case LOGOUT_LOCATION:
                return stringify(new LocationSerializer().serialize(this.logoutLocation));
            case LOGOUT_FLY:
                return stringify(this.logoutFly);
            case LAST_BED_ENTER:
                return stringify(this.lastBedEnter, DateUtils.DATE_TIME_FORMAT);
            case EXTRA_HOMES:
                return stringify(this.extraHomes);
            case WARP_SLOT_SHOP:
                return stringify(this.warpSlotShop);
            case WARP_SLOT_PRISMS:
                return stringify(this.warpSlotPrisms);
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
        CREDITS(new MySQLColumn("credits", Column.Type.INT)),
        CLAIM_BLOCKS(new MySQLColumn("claim_blocks", Column.Type.INT)),
        BACK_LOCATION(new MySQLColumn("back_location", Column.Type.TEXT)),
        BACK_CHARGES(new MySQLColumn("back_charges", Column.Type.INT)),
        LOGOUT_LOCATION(new MySQLColumn("logout_location", Column.Type.TEXT)),
        LOGOUT_FLY(new MySQLColumn("logout_fly", Column.Type.TINYINT, 1)),
        LAST_BED_ENTER(new MySQLColumn("last_bed_enter", Column.Type.DATETIME)),
        EXTRA_HOMES(new MySQLColumn("extra_homes", Column.Type.INT)),
        WARP_SLOT_SHOP(new MySQLColumn("warp_slot_shop", Column.Type.TINYINT, 1)),
        WARP_SLOT_PRISMS(new MySQLColumn("warp_slot_prisms", Column.Type.TINYINT, 1));

        @Getter private final MySQLColumnInstance column;
    }

    public static SurvivalPlayerModel findOrInitializeBy(UUID uuid) {
        SurvivalPlayerModel model = findBy(SurvivalPlayerModel.class, SurvivalPlayerModel.column.UUID.is(uuid));

        if (model != null)
            return model;

        return new SurvivalPlayerModel(uuid);
    }
}
