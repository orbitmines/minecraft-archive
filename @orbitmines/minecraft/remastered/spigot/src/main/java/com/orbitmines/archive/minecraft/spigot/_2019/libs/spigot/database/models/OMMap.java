package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.google.gson.JsonArray;
import com.orbitmines.archive.minecraft._2019.libs.Server;
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
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.generators.DefaultWorldType;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPointHandler;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPointLoader;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.World;

import java.util.Date;

public class OMMap extends OMMySQLModel<OMMap, OMMap.column> {

    public static MySQLTable TABLE = new MySQLTable("maps", MySQLModelColumn.toColumns(OMMap.column.values()));

    @Getter private Long id;
    @Getter @Setter private Server server;
    @Getter @Setter private String name;
    @Getter @Setter private String worldFileName;
    @Getter private DefaultWorldType worldGenerator;
    @Getter private Type worldType;
    @Getter @Setter private Boolean enabled;
    @Getter private JsonArray authors;
    @Getter private Date createdAt;

    @Getter World world;
    @Getter DataPointHandler dataPointHandler;

    public OMMap() {
    }

    public OMMap(Server server, String name, String worldFileName, DefaultWorldType worldGenerator, Type worldType, boolean enabled, JsonArray authors, Date createdAt) {
        this.server = server;
        this.name = name;

        this.worldFileName = worldFileName;
        if (this.worldFileName != null) /* Since 1.14.1 world name has to be lowercase */
            this.worldFileName = this.worldFileName.toLowerCase();

        this.worldGenerator = worldGenerator;
        this.worldType = worldType;
        this.enabled = enabled;
        this.authors = authors;
        this.createdAt = createdAt;
    }

    public void setup(World world) {
        setup(world, null);
    }

    public void setup(World world, DataPointHandler dataPointHandler) {
        this.world = world;
        this.dataPointHandler = dataPointHandler;

        if (dataPointHandler == null)
            return;

        setupDataPoints();
    }

    public void setupDataPoints() {
        /* Clear previous setups */
        dataPointHandler.clearDataPoints();

        DataPointLoader loader = new DataPointLoader(world, dataPointHandler.getAsMap());
        loader.load();

        dataPointHandler.setup();
    }

    @Override
    protected void load() {
        this.id = getLong(column.ID);
        this.server = getEnum(column.SERVER, Server.class);
        this.name = getString(column.NAME);

        this.worldFileName = getString(column.WORLD_FILE_NAME);
        if (this.worldFileName != null) /* Since 1.14.1 world name has to be lowercase */
            this.worldFileName = this.worldFileName.toLowerCase();

        this.worldGenerator = getEnum(column.WORLD_GENERATOR, DefaultWorldType.class); //TODO ALLOW OTHER WORLD GENERATORS THAN DEFAULT
        this.worldType = getEnum(column.WORLD_TYPE, Type.class);
        this.enabled = getBoolean(column.ENABLED);
        this.authors = getJson(column.AUTHORS).getAsJsonArray();
        this.createdAt = getDate(column.CREATED_AT, DateUtils.DATE_TIME_FORMAT);
    }

    @Override
    public void insert() {
        setupReloadAfterInsert(
            localIdentifiers(column.SERVER)
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
            case SERVER:
                return stringify(this.server);
            case NAME:
                return this.name;
            case WORLD_FILE_NAME:
                return this.worldFileName;
            case WORLD_TYPE:
                return stringify(this.worldType);
            case WORLD_GENERATOR:
                return stringify(this.worldGenerator);
            case ENABLED:
                return stringify(this.enabled);
            case AUTHORS:
                return stringify(this.authors);
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
        SERVER(new MySQLColumn("server", Column.Type.VARCHAR).indexed()),

        NAME(new MySQLColumn("name", Column.Type.VARCHAR).notNull()),
        WORLD_FILE_NAME(new MySQLColumn("world_file_name", Column.Type.VARCHAR).notNull()),
        WORLD_GENERATOR(new MySQLColumn("world_generator", Column.Type.VARCHAR).notNull()),
        WORLD_TYPE(new MySQLColumn("world_type", Column.Type.VARCHAR).notNull()),
        ENABLED(new MySQLColumn("enabled", Column.Type.TINYINT, 1)),
        AUTHORS(new MySQLColumn("authors", Column.Type.TEXT).notNull()),
        CREATED_AT(new MySQLColumn("created_at", Column.Type.DATETIME).indexed());

        @Getter
        private final MySQLColumnInstance column;
    }

    public enum Type {

        LOBBY,
        GAMEMAP;

    }

    public static OMMap getLobby(Server server) {
        return OMMap.findBy(OMMap.class,
            column.WORLD_TYPE.is(Type.LOBBY),
            column.SERVER.is(server),
            column.ENABLED.is(true)
        );
    }
}
