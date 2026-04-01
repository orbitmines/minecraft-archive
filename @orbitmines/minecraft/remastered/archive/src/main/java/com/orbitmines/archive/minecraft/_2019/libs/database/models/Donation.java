package com.orbitmines.archive.minecraft._2019.libs.database.models;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Image;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.database.mysql.OMMySQLModel;
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
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
public class Donation extends OMMySQLModel<Donation, Donation.column> {

    public static MySQLTable TABLE = new MySQLTable("donations", MySQLModelColumn.toColumns(column.values()));

    @Getter private Long id;
    @Getter private UUID uuid;
    @Getter private int typeId;
    @Getter private double amount;
    @Getter private String currency;
    @Getter private Date donatedAt;

    public Donation() {
    }

    public Donation(UUID uuid, Type type, double amount, String currency, Date donatedAt) {
        this.uuid = uuid;
        this.typeId = type.getId();
        this.amount = amount;
        this.currency = currency;
        this.donatedAt = donatedAt;
    }

    public Type getType() {
        return Type.getById(this.typeId);
    }

    @Override
    protected void load() {
        this.id = getLong(column.ID);
        this.uuid = getUUID(column.UUID);
        this.typeId = getInteger(column.TYPE_ID);
        this.amount = getDouble(column.AMOUNT);
        this.currency = getString(column.CURRENCY);
        this.donatedAt = getDate(column.DONATED_AT, DateUtils.DATE_TIME_FORMAT);
    }

    @Override
    public void insert() {
        setupReloadAfterInsert(
            localIdentifiers(column.UUID, column.TYPE_ID, column.AMOUNT)
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
        return column.DONATED_AT;
    }

    @Override
    protected String stringifyValue(column column) {
        switch (column) {

            case ID:
                return stringify(this.id);
            case UUID:
                return stringify(this.uuid);
            case TYPE_ID:
                return stringify(this.typeId);
            case AMOUNT:
                return stringify(this.amount);
            case CURRENCY:
                return stringify(this.currency);
            case DONATED_AT:
                return stringify(this.donatedAt, DateUtils.DATE_TIME_FORMAT);
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

        TYPE_ID(new MySQLColumn("type_id", Column.Type.INT).notNull()),
        AMOUNT(new MySQLColumn("amount", Column.Type.DOUBLE, 16, 2).notNull()),
        CURRENCY(new MySQLColumn("currency", Column.Type.VARCHAR).notNull()),
        DONATED_AT(new MySQLColumn("donated_at", Column.Type.DATETIME).notNull().indexed());

        @Getter private final MySQLColumnInstance column;
    }

    public enum Type {

        /* Prices are in EUR */

        UNKNOWN(0, 0.00, Image.ORBITMINES_ICON, "§8§lUNKNOWN", ""),
        DONATION(1, 0.00, Image.ORBITMINES_ICON, "§3§lDonation", ""),

        VIP_IRON(2, 5.00, Image.VIP_IRON, VipRank.IRON.getDisplayName(), ""),
        VIP_GOLD(3, 10.00, Image.VIP_GOLD, VipRank.GOLD.getDisplayName(), ""),
        VIP_DIAMOND(4, 25.00, Image.VIP_DIAMOND,  VipRank.DIAMOND.getDisplayName(), ""),
        VIP_EMERALD(5, 50.00, Image.VIP_EMERALD, VipRank.EMERALD.getDisplayName(), ""),

        VIP_UPGRADE_IRON_GOLD(6, 5.00, Image.VIP_UPGRADE_IRON_GOLD, VipRank.IRON.getDisplayName() + "§r §7» " + VipRank.GOLD.getDisplayName(), ""),
        VIP_UPGRADE_IRON_DIAMOND(7, 20.00, Image.VIP_UPGRADE_IRON_DIAMOND, VipRank.IRON.getDisplayName() + "§r §7» " + VipRank.DIAMOND.getDisplayName(), ""),
        VIP_UPGRADE_IRON_EMERALD(8, 45.00, Image.VIP_UPGRADE_IRON_EMERALD, VipRank.IRON.getDisplayName() + "§r §7» " + VipRank.EMERALD.getDisplayName(), ""),
        VIP_UPGRADE_GOLD_DIAMOND(9, 15.00, Image.VIP_UPGRADE_GOLD_DIAMOND, VipRank.GOLD.getDisplayName() + "§r §7» " + VipRank.DIAMOND.getDisplayName(), ""),
        VIP_UPGRADE_GOLD_EMERALD(10, 40.00, Image.VIP_UPGRADE_GOLD_EMERALD, VipRank.GOLD.getDisplayName() + "§r §7» " + VipRank.EMERALD.getDisplayName(), ""),
        VIP_UPGRADE_DIAMOND_EMERALD(11, 25.00, Image.VIP_UPGRADE_DIAMOND_EMERALD, VipRank.DIAMOND.getDisplayName() + "§r §7» " + VipRank.EMERALD.getDisplayName(), ""),

        SOLARS_1500(12, 5.00, Image.ORBITMINES_ICON, "§e§l1,500 Solars", ""),
        SOLARS_4000(13, 10.00, Image.ORBITMINES_ICON, "§e§l4,000 Solars", ""),
        SOLARS_12500(14, 25.00, Image.ORBITMINES_ICON, "§e§l12,500 Solars", ""),

        SURVIVAL_CLAIMBLOCKS_10000(15, 5.00, Server.SURVIVAL, Image.SURVIVAL_ICON, "§9§l10,000 Claimblocks", ""),
        SURVIVAL_CLAIMBLOCKS_25000(16, 10.00, Server.SURVIVAL, Image.SURVIVAL_ICON, "§9§l25,000 Claimblocks", ""),
        SURVIVAL_CLAIMBLOCKS_75000(17, 25.00, Server.SURVIVAL, Image.SURVIVAL_ICON, "§9§l75,000 Claimblocks", ""),
        @Deprecated SURVIVAL_CLAIMBLOCKS_50000(18, 17.50, Server.SURVIVAL, Image.SURVIVAL_ICON, "§9§l50,000 Claimblocks", ""),
        SURVIVAL_HOMES_25(19, 2.50, Server.SURVIVAL, Image.SURVIVAL_ICON, "§6§l25 Homes", ""),
        SURVIVAL_HOMES_100(20, 7.50, Server.SURVIVAL, Image.SURVIVAL_ICON, "§6§l100 Homes", ""),
        SURVIVAL_WARP_1(21, 7.50, Server.SURVIVAL, Image.SURVIVAL_ICON, "§3§l1 Warp", "");

        @Getter private final int id;
        @Getter private final double price;
        @Getter private final Server server;
        @Getter private final Image image;
        @Getter private final String title;
        @Getter private final String[] description;

        Type(int id, double price, Image image, String title, String... description) {
            this(id, price, null, image, title, description);
        }

        Type(int id, double price, Server server, Image image, String title, String... description) {
            this.id = id;
            this.price = price;
            this.server = server;
            this.image = image;
            this.title = title;
            this.description = description;
        }

        public VipRank getUnlockedRank() {
            switch (this) {

                case VIP_IRON:
                    return VipRank.IRON;
                case VIP_GOLD:
                case VIP_UPGRADE_IRON_GOLD:
                    return VipRank.GOLD;
                case VIP_DIAMOND:
                case VIP_UPGRADE_IRON_DIAMOND:
                case VIP_UPGRADE_GOLD_DIAMOND:
                    return VipRank.DIAMOND;
                case VIP_EMERALD:
                case VIP_UPGRADE_IRON_EMERALD:
                case VIP_UPGRADE_GOLD_EMERALD:
                case VIP_UPGRADE_DIAMOND_EMERALD:
                    return VipRank.EMERALD;
            }
            return null;
        }

        public VipRank getPreviousRank() {
            switch (this) {

                case VIP_UPGRADE_IRON_GOLD:
                case VIP_UPGRADE_IRON_DIAMOND:
                case VIP_UPGRADE_IRON_EMERALD:
                    return VipRank.IRON;
                case VIP_UPGRADE_GOLD_DIAMOND:
                case VIP_UPGRADE_GOLD_EMERALD:
                    return VipRank.GOLD;
                case VIP_UPGRADE_DIAMOND_EMERALD:
                    return VipRank.DIAMOND;
            }
            return null;
        }

        public static Type getById(int id) {
            for (Type type : values()) {
                if (type.id == id)
                    return type;
            }
            return Type.UNKNOWN;
        }
    }
}
