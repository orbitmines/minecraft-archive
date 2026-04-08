package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.creative;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.orbitmines.archive.minecraft._2019.libs.database.mysql.OMMySQLModel;
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

import java.util.UUID;

public class CreativePlayerModel extends OMMySQLModel<CreativePlayerModel, CreativePlayerModel.column> {

    public static MySQLTable TABLE = new MySQLTable("creative_players", MySQLModelColumn.toColumns(column.values()));

    @Getter private UUID uuid;
    @Getter @Setter private Location logoutLocation;
    @Getter private String logoutWorldName;
    private JsonElement logoutLocationJson;
    @Getter private int extraWorldSlots;

    public CreativePlayerModel() {
    }

    public CreativePlayerModel(UUID uuid) {
        this.uuid = uuid;
        this.logoutLocation = null;
        this.extraWorldSlots = 0;
    }

    public void addExtraWorldSlots(int amount) {
        this.extraWorldSlots += amount;
    }

    @Override
    protected void load() {
        this.uuid = getUUID(column.UUID);

        this.logoutLocationJson = getJson(column.LOGOUT_LOCATION);
        this.logoutLocation = new LocationSerializer().deserialize(logoutLocationJson);
        this.extraWorldSlots = getInteger(column.EXTRA_WORLD_SLOTS);

        /* Extract world name from raw JSON — world may not be loaded yet */
        if (logoutLocationJson != null && logoutLocationJson.isJsonObject()) {
            JsonObject obj = logoutLocationJson.getAsJsonObject();
            if (obj.has("world"))
                this.logoutWorldName = obj.get("world").getAsString();
        }
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
            case LOGOUT_LOCATION:
                return stringify(new LocationSerializer().serialize(this.logoutLocation));
            case EXTRA_WORLD_SLOTS:
                return stringify(this.extraWorldSlots);
            default:
                throw new UnsupportedOperationException();
        }
    }

    /**
     * Re-deserialize the logout location after the world has been loaded.
     */
    public Location resolveLogoutLocation() {
        if (logoutLocation != null && logoutLocation.getWorld() != null)
            return logoutLocation;

        this.logoutLocation = new LocationSerializer().deserialize(logoutLocationJson);
        return logoutLocation;
    }

    @Override
    protected column[] getColumns() {
        return column.values();
    }

    @AllArgsConstructor
    public enum column implements MySQLModelColumn {

        UUID(new MySQLColumnKey("uuid", Column.Type.VARCHAR, ColumnKey.Key.PRIMARY, 36)),
        LOGOUT_LOCATION(new MySQLColumn("logout_location", Column.Type.TEXT)),
        EXTRA_WORLD_SLOTS(new MySQLColumn("extra_world_slots", Column.Type.INT));

        @Getter private final MySQLColumnInstance column;
    }

    public static CreativePlayerModel findOrInitializeBy(UUID uuid) {
        CreativePlayerModel model = findBy(CreativePlayerModel.class, CreativePlayerModel.column.UUID.is(uuid));

        if (model != null)
            return model;

        return new CreativePlayerModel(uuid);
    }
}
