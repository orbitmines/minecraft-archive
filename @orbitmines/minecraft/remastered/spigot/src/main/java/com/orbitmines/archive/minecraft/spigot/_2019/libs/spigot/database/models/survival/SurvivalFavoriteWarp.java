package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival;

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

public class SurvivalFavoriteWarp extends OMMySQLModel<SurvivalFavoriteWarp, SurvivalFavoriteWarp.column> {

    public static MySQLTable TABLE = new MySQLTable("survival_favorite_warps", MySQLModelColumn.toColumns(column.values()));

    @Getter private Long warpId;
    @Getter private UUID uuid;
    @Getter @Setter private boolean favorite;

    public SurvivalFavoriteWarp() {
    }

    public SurvivalFavoriteWarp(UUID uuid, SurvivalWarp warp, boolean favorite) {
        this.uuid = uuid;
        this.warpId = warp.getId();
        this.favorite = favorite;
    }

    @Override
    protected void load() {
        this.uuid = getUUID(column.UUID);
        this.warpId = getLong(column.WARP_ID);
        this.favorite = getBoolean(column.FAVORITE);
    }

    @Override
    protected MySQLTable getTable() {
        return TABLE;
    }

    @Override
    protected ModelSelector[] getUniqueIdentifier() {
        return localIdentifiers(column.UUID, column.WARP_ID);
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
            case WARP_ID:
                return stringify(this.warpId);
            case FAVORITE:
                return stringify(this.favorite);
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

        UUID(new MySQLColumn("owner", Column.Type.VARCHAR, 36).indexed()),
        WARP_ID(new MySQLColumn("warp_id", Column.Type.BIGINT).indexed()),
        FAVORITE(new MySQLColumn("favorite", Column.Type.TINYINT, 1));

        @Getter private final MySQLColumnInstance column;
    }
}
