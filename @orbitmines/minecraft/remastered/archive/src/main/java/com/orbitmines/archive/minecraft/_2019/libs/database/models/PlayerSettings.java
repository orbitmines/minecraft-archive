package com.orbitmines.archive.minecraft._2019.libs.database.models;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.database.mysql.OMMySQLModel;
import com.orbitmines.archive.minecraft._2019.libs.player.Languageable;
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

public class PlayerSettings extends OMMySQLModel<PlayerSettings, PlayerSettings.column> {

    public static MySQLTable TABLE = new MySQLTable("player_settings", MySQLModelColumn.toColumns(column.values()));

    @Getter private UUID uuid;
    @Getter private Type type;
    @Getter @Setter private Level level;

    public PlayerSettings() {
    }

    public PlayerSettings(UUID uuid, Type type) {
        this(uuid, type, type.getDefaultLevel());
    }

    public PlayerSettings(UUID uuid, Type type, Level level) {
        this.uuid = uuid;
        this.type = type;
        this.level = level;
    }

    @Override
    protected void load() {
        this.uuid = getUUID(column.UUID);
        this.type = getEnum(column.TYPE, Type.class);
        this.level = getEnum(column.LEVEL, Level.class);
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
            case LEVEL:
                return stringify(this.level);
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
        LEVEL(new MySQLColumn("level", Column.Type.VARCHAR));

        @Getter private final MySQLColumnInstance column;
    }

    public enum Type {

        PRIVATE_MESSAGES(Level.values()),
        PLAYER_VISIBILITY(Level.values()),
        SCOREBOARD_VISIBILITY(Level.ENABLED, Level.DISABLED),
        GADGETS(Level.values()),
        STATS(Level.values());

        @Getter Level[] levels;

        Type(Level... levels) {
            this.levels = levels;
        }

        public Level getDefaultLevel() {
            return levels[0];
        }

        public String getTitle(Languageable player) {
            return player.translate("global", "player.settings." + toString().toLowerCase() + ".title");
        }
    }

    @AllArgsConstructor
    public enum Level {

        ENABLED(Color.LIME),
        ONLY_FRIENDS(Color.AQUA),
        ONLY_FAVORITE_FRIENDS(Color.ORANGE),
        DISABLED(Color.RED);

        @Getter private Color color;

        public String getTranslation(Languageable player) {
            return player.translate("global", "player.settings_level." + toString().toLowerCase());
        }
    }
}
