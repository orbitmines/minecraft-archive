package com.orbitmines.archive.minecraft._2019.libs.database.models.loot;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Server;
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
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class PeriodLootItem extends OMMySQLModel<PeriodLootItem, PeriodLootItem.column> {

    public static MySQLTable TABLE = new MySQLTable("period_loot_items", MySQLModelColumn.toColumns(column.values()));

    @Getter private UUID uuid;
    @Getter private Type type;
    @Getter @Setter private Date lastUsageAt;

    public PeriodLootItem() {
    }

    public PeriodLootItem(UUID uuid, Type type, Date lastUsageAt) {
        this.uuid = uuid;
        this.type = type;
        this.lastUsageAt = lastUsageAt;
    }

    public boolean canCollect() {
        if (lastUsageAt == null)
            return true;

        return System.currentTimeMillis() - lastUsageAt.getTime() >= type.getInterval();
    }

    public long getMillisLeft() {
        if (lastUsageAt == null)
            return 0L;

        return type.getInterval() - (System.currentTimeMillis() - lastUsageAt.getTime());
    }

    public void collect() {
        this.lastUsageAt = DateUtils.now();
    }

    @Override
    protected void load() {
        this.uuid = getUUID(column.UUID);
        this.type = getEnum(column.TYPE, Type.class);
        this.lastUsageAt = getDate(column.LAST_USAGE_AT, DateUtils.DATE_TIME_FORMAT);
    }

    @Override
    protected MySQLTable getTable() {
        return TABLE;
    }

    @Override
    protected ModelSelector[] getUniqueIdentifier() {
        return localIdentifiers(column.UUID, column.TYPE);
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
            case TYPE:
                return stringify(this.type);
            case LAST_USAGE_AT:
                return stringify(this.lastUsageAt, DateUtils.DATE_TIME_FORMAT);
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
        TYPE(new MySQLColumn("type", Column.Type.VARCHAR).notNull().indexed()),
        LAST_USAGE_AT(new MySQLColumn("last_usage_at", Column.Type.DATETIME));

        @Getter private final MySQLColumnInstance column;
    }

    @AllArgsConstructor
    public enum Type {

        DAILY(TimeUnit.DAYS.toMillis(1)),
        MONTHLY(TimeUnit.DAYS.toMillis(30)),
        MONTHLY_VIP(TimeUnit.DAYS.toMillis(30)),
        SURVIVAL_BACK_CHARGES(TimeUnit.DAYS.toMillis(30), Server.SURVIVAL),
        SURVIVAL_SPAWNER_ITEM(TimeUnit.DAYS.toMillis(30), Server.SURVIVAL);

        @Getter private final long interval;
        @Getter private final Server server;

        Type(long interval) {
            this(interval, null);
        }

        public static List<Type> from(Server server){
            List<Type> list = new ArrayList<>();

            for (Type loot : values()) {
                if (loot.server == server)
                    list.add(loot);
            }

            return list;
        }
    }
}
