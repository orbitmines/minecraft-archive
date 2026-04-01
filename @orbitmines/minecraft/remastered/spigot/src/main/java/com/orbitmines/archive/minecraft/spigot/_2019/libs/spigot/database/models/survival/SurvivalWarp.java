package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.database.mysql.OMMySQLModel;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import com.orbitmines.archive.minecraft._2019.utils.EnumUtils;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Column;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.ColumnKey;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumn;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumnInstance;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumnKey;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLTable;
import com.orbitmines.archive.minecraft._2019.utils.database.model.ModelSelector;
import com.orbitmines.archive.minecraft._2019.utils.database.model.mysql.MySQLModelColumn;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.serializers.LocationSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;

import java.util.Date;
import java.util.UUID;

public class SurvivalWarp extends OMMySQLModel<SurvivalWarp, SurvivalWarp.column> {

    public static MySQLTable TABLE = new MySQLTable("survival_warps", MySQLModelColumn.toColumns(column.values()));

    @Getter private Long id;
    @Getter private UUID owner;
    @Getter @Setter private String name;
    @Getter @Setter private Location location;
    @Getter @Setter private boolean enabled;
    @Getter private Slot slot;
    @Getter @Setter private Icon icon;
    @Getter private int timesUsed;
    @Getter private Date createdOn;

    public SurvivalWarp() {
    }

    public SurvivalWarp(UUID owner, String name, Slot slot) {
        this.owner = owner;
        this.name = name;
        this.enabled = false;
        this.slot = slot;
        this.icon = EnumUtils.random(Icon.class);
        this.createdOn = DateUtils.now();
    }

    public void addTimeUsed() {
        this.timesUsed++;
    }

    @Override
    public void insert() {
        setupReloadAfterInsert(
            localIdentifiers(column.OWNER, column.NAME)
        );

        super.insert();
    }

    @Override
    protected void load() {
        this.id = getLong(column.ID);
        this.owner = getUUID(column.OWNER);
        this.name = getString(column.NAME);
        this.location = new LocationSerializer().deserialize(getJson(column.LOCATION));
        this.enabled = getBoolean(column.ENABLED);
        this.slot = getEnum(column.SLOT, Slot.class);
        this.icon = getEnum(column.ICON, Icon.class);
        this.timesUsed = getInteger(column.TIMES_USED);
        this.createdOn = getDate(column.CREATED_ON, DateUtils.DATE_TIME_FORMAT);
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
        return column.CREATED_ON;
    }

    @Override
    protected String stringifyValue(column column) {
        switch (column) {

            case ID:
                return stringify(this.id);
            case OWNER:
                return stringify(this.owner);
            case NAME:
                return stringify(this.name);
            case LOCATION:
                return stringify(new LocationSerializer().serialize(this.location));
            case ENABLED:
                return stringify(this.enabled);
            case SLOT:
                return stringify(this.slot);
            case ICON:
                return stringify(this.icon);
            case TIMES_USED:
                return stringify(this.timesUsed);
            case CREATED_ON:
                return stringify(this.createdOn, DateUtils.DATE_TIME_FORMAT);
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
        OWNER(new MySQLColumn("owner", Column.Type.VARCHAR, 36).indexed()),
        NAME(new MySQLColumn("name", Column.Type.VARCHAR).indexed()),
        LOCATION(new MySQLColumn("location", Column.Type.TEXT)),
        ENABLED(new MySQLColumn("enabled", Column.Type.TINYINT, 1)),
        SLOT(new MySQLColumn("slot", Column.Type.VARCHAR)),
        ICON(new MySQLColumn("icon", Column.Type.VARCHAR)),
        TIMES_USED(new MySQLColumn("times_used", Column.Type.INT)),
        CREATED_ON(new MySQLColumn("created_on", Column.Type.DATETIME));

        @Getter private final MySQLColumnInstance column;
    }

    @AllArgsConstructor
    public enum Slot {

        VIP_SLOT(Color.LIME),
        SHOP_SLOT(Color.TEAL),
        PRISMS_SLOT(Color.BLUE);

        @Getter private final Color color;

        public String getDisplayName(OMPlayer player) {
            return color.getCc() + "§l" + getName(player);
        }

        public String getName(OMPlayer player) {
            return player.translate("survival", "player.warp.slot." + toString().toLowerCase() + ".name");
        }

        public String getDescription(OMPlayer player) {
            return player.translate("survival", "player.warp.slot." + toString().toLowerCase() + ".description");
        }
    }

    public enum Icon {

        POWERED_RAIL(new ItemBuilder(Material.POWERED_RAIL)),
        HORN_CORAL(new ItemBuilder(Material.HORN_CORAL)),
        IRON_CHESTPLATE(new ItemBuilder(Material.IRON_CHESTPLATE).addFlag(ItemFlag.HIDE_ATTRIBUTES)),
        STICKY_PISTON(new ItemBuilder(Material.STICKY_PISTON)),
        GLISTERING_MELON_SLICE(new ItemBuilder(Material.GLISTERING_MELON_SLICE)),
        BLUE_ICE(new ItemBuilder(Material.STONE)),
        FIREWORK_ROCKET(new ItemBuilder(Material.FIREWORK_ROCKET)),
        BLACK_GLAZED_TERRACOTTA(new ItemBuilder(Material.BLACK_GLAZED_TERRACOTTA)),
        GUN_POWDER(new ItemBuilder(Material.GUNPOWDER)),
        LECTERN(new ItemBuilder(Material.LECTERN)),
        LIGHT_BLUE_CONCRETE_POWDER(new ItemBuilder(Material.LIGHT_BLUE_CONCRETE_POWDER)),
        ORANGE_TERRACOTTA(new ItemBuilder(Material.ORANGE_TERRACOTTA)),
        BOOKSHELF(new ItemBuilder(Material.BOOKSHELF)),
        ELYTRA(new ItemBuilder(Material.ELYTRA)),
        TOTEM_OF_UNDYING(new ItemBuilder(Material.TOTEM_OF_UNDYING)),
        PUFFERFISH_BUCKET(new ItemBuilder(Material.PUFFERFISH_BUCKET)),
        GRASS_BLOCK(new ItemBuilder(Material.GRASS_BLOCK)),
        HAY_BALE(new ItemBuilder(Material.HAY_BLOCK)),
        GRAY_GLAZED_TERRACOTTA(new ItemBuilder(Material.GRAY_GLAZED_TERRACOTTA)),
        LEATHER(new ItemBuilder(Material.LEATHER)),
        PUMPKIN_PIE(new ItemBuilder(Material.PUMPKIN_PIE)),
        RED_WOOL(new ItemBuilder(Material.RED_WOOL)),
        POPPY(new ItemBuilder(Material.POPPY)),
        TRIDENT(new ItemBuilder(Material.TRIDENT).addFlag(ItemFlag.HIDE_ATTRIBUTES)),
        FIRE_CORAL(new ItemBuilder(Material.FIRE_CORAL)),
        DARK_PRISMARINE(new ItemBuilder(Material.DARK_PRISMARINE)),
        CAMPFIRE(new ItemBuilder(Material.CAMPFIRE)),
        SPRUCE_SIGN(new ItemBuilder(Material.SPRUCE_SIGN)),
        PURPUR_BLOCK(new ItemBuilder(Material.PURPUR_BLOCK)),
        MELON(new ItemBuilder(Material.MELON)),
        PAINTING(new ItemBuilder(Material.PAINTING)),
        COOKIE(new ItemBuilder(Material.COOKIE)),
        BELL(new ItemBuilder(Material.BELL)),
        TURTLE_EGG(new ItemBuilder(Material.TURTLE_EGG)),
        OBSIDIAN(new ItemBuilder(Material.OBSIDIAN)),
        TUBE_CORAL_BLOCK(new ItemBuilder(Material.TUBE_CORAL_BLOCK)),
        LANTERN(new ItemBuilder(Material.LANTERN)),
        BEACON(new ItemBuilder(Material.BEACON)),
        LIME_STAINED_GLASS(new ItemBuilder(Material.LIME_STAINED_GLASS)),
        BLAZE_POWDER(new ItemBuilder(Material.BLAZE_POWDER)),
        ROSE_BUSH(new ItemBuilder(Material.ROSE_BUSH)),
        MAGMA_BLOCK(new ItemBuilder(Material.MAGMA_BLOCK)),
        CACTUS(new ItemBuilder(Material.CACTUS)),
        TROPICAL_FISH(new ItemBuilder(Material.TROPICAL_FISH));

        private final ItemBuilder itemBuilder;

        Icon(ItemBuilder itemBuilder) {
            this.itemBuilder = itemBuilder;
        }

        public ItemBuilder getItemBuilder() {
            return itemBuilder.clone();
        }
    }
}
