package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.database.mysql.OMMySQLModel;
import com.orbitmines.archive.minecraft._2019.libs.player.Languageable;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
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
import java.util.List;
import java.util.UUID;

public class SurvivalClaim extends OMMySQLModel<SurvivalClaim, SurvivalClaim.column> {

    public static MySQLTable TABLE = new MySQLTable("survival_claims", MySQLModelColumn.toColumns(column.values()));

    @Getter private Long id;
    @Getter private UUID owner;
    @Getter @Setter private String name;
    @Getter @Setter private Location corner1;
    @Getter @Setter private Location corner2;
    @Getter private Date createdOn;

    private List<SurvivalClaimMember> members;

    public SurvivalClaim() {
    }

    public SurvivalClaim(UUID owner, Location corner1, Location corner2) {
        this.owner = owner;
        this.corner1 = corner1;
        this.corner2 = corner2;
        this.createdOn = DateUtils.now();
    }

    public void reloadMembers() {
        this.members = SurvivalClaimMember.getAll(SurvivalClaimMember.class, SurvivalClaimMember.column.CLAIM_ID.is(getId()));
    }

    public List<SurvivalClaimMember> getMembers(boolean reload) {
        if (reload || this.members == null)
            reloadMembers();

        return members;
    }

    public SurvivalClaimMember getMember(UUID uuid, boolean reload) {
        for (SurvivalClaimMember member : getMembers(reload)) {
            if (member.getUuid().equals(uuid))
                return member;
        }
        return null;
    }

    @Override
    public void insert() {
        setupReloadAfterInsert(
            localIdentifier(column.OWNER)
        );

        super.insert();
    }

    @Override
    protected void load() {
        this.id = getLong(column.ID);
        this.owner = getUUID(column.OWNER);
        this.name = getString(column.NAME);
        this.corner1 = new LocationSerializer().deserialize(getJson(column.CORNER_1));
        this.corner2 = new LocationSerializer().deserialize(getJson(column.CORNER_2));
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
            case CORNER_1:
                return stringify(new LocationSerializer().serialize(this.corner1));
            case CORNER_2:
                return stringify(new LocationSerializer().serialize(this.corner2));
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
        NAME(new MySQLColumn("name", Column.Type.VARCHAR)),
        CORNER_1(new MySQLColumn("corner1", Column.Type.TEXT)),
        CORNER_2(new MySQLColumn("corner2", Column.Type.TEXT)),
        CREATED_ON(new MySQLColumn("created_on", Column.Type.DATETIME).indexed());

        @Getter private final MySQLColumnInstance column;
    }

    public enum Permission {

        ACCESS(new ItemBuilder(Material.DARK_OAK_DOOR)), /* Use Doors, Buttons, Fly etc. */
        MANAGE(new ItemBuilder(Material.HOPPER)), /* Access Chests & Plant Farms */
        BUILD(new ItemBuilder(Material.IRON_AXE).addFlag(ItemFlag.HIDE_ATTRIBUTES)); /* Build & Break Blocks */

        private final ItemBuilder icon;

        Permission(ItemBuilder icon) {
            this.icon = icon;
        }

        public ItemBuilder getIcon() {
            return icon.clone();
        }

        public String getName(Languageable languageable) {
            return languageable.translate("survival", "player.claim.permission." + toString().toLowerCase() + ".name");
        }

        public String[] getDescription(Languageable languageable) {
            return languageable.getLanguage().getStringArray("survival", "player.claim.permission." + toString().toLowerCase() + ".features");
        }

        public boolean hasPerms(Permission permission) {
            return this.ordinal() >= permission.ordinal();
        }
    }
}
