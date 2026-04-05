package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.kitpvp;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.database.mysql.OMMySQLModel;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Column;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.ColumnKey;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumn;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumnInstance;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLColumnKey;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.froms.MySQLTable;
import com.orbitmines.archive.minecraft._2019.utils.database.model.ModelSelector;
import com.orbitmines.archive.minecraft._2019.utils.database.model.mysql.MySQLModelColumn;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.Title;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class KitPvPPlayerModel extends OMMySQLModel<KitPvPPlayerModel, KitPvPPlayerModel.column> {

    public static final int START_COINS = 10000;

    public static MySQLTable TABLE = new MySQLTable("kitpvp_players", MySQLModelColumn.toColumns(column.values()));

    @Getter private UUID uuid;
    @Getter @Setter private int coins;
    @Getter @Setter private long experience;
    @Getter @Setter private long lastSelectedId;
    @Getter @Setter private int lastSelectedLevel;

    public KitPvPPlayerModel() {
    }

    public KitPvPPlayerModel(UUID uuid) {
        this.uuid = uuid;
        this.coins = START_COINS;
        this.experience = 0;
        this.lastSelectedId = 0;
        this.lastSelectedLevel = 1;
    }

    public LevelData getLevelData() {
        LevelData data = new LevelData(this);
        data.update(null, false);
        return data;
    }

    public void addExperience(long amount) {
        this.experience += amount;
    }

    public void addCoins(int amount) {
        this.coins += amount;
    }

    public void removeCoins(int amount) {
        this.coins -= amount;
    }

    public List<KitPvPPlayerKitModel> getKits() {
        return KitPvPPlayerKitModel.getAll(KitPvPPlayerKitModel.class, KitPvPPlayerKitModel.column.UUID.is(getUuid()));
    }

    public KitPvPPlayerKitModel getKit(long id) {
        KitPvPPlayerKitModel kit = KitPvPPlayerKitModel.findBy(KitPvPPlayerKitModel.class, KitPvPPlayerKitModel.column.UUID.is(getUuid()), KitPvPPlayerKitModel.column.KIT_ID.is(id));

        if (kit != null)
            return kit;

        return new KitPvPPlayerKitModel(uuid, id, 0);
    }

    public int getKills(List<KitPvPPlayerKitModel> kits) {
        int kills = 0;

        for (KitPvPPlayerKitModel kit : kits) {
            kills += kit.getKills();
        }

        return kills;
    }

    public int getDeaths(List<KitPvPPlayerKitModel> kits) {
        int deaths = 0;

        for (KitPvPPlayerKitModel kit : kits) {
            deaths += kit.getDeaths();
        }

        return deaths;
    }

    public long getDamageDealt(List<KitPvPPlayerKitModel> kits) {
        long damageDealt = 0;

        for (KitPvPPlayerKitModel kit : kits) {
            damageDealt += kit.getDamageDealt();
        }

        return damageDealt;
    }

    public int getBestStreak(List<KitPvPPlayerKitModel> kits) {
        int bestStreak = 0;

        for (KitPvPPlayerKitModel kit : kits) {
            if (bestStreak == 0 || kit.getBestStreak() > bestStreak)
                bestStreak = kit.getBestStreak();
        }

        return bestStreak;
    }

    @Override
    protected void load() {
        this.uuid = getUUID(column.UUID);
        this.coins = getInteger(column.COINS);
        this.experience = getLong(column.EXPERIENCE);
        this.lastSelectedId = getLong(column.LAST_SELECTED_ID);
        this.lastSelectedLevel = getInteger(column.LAST_SELECTED_LEVEL);
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
            case COINS:
                return stringify(this.coins);
            case EXPERIENCE:
                return stringify(this.experience);
            case LAST_SELECTED_ID:
                return stringify(this.lastSelectedId);
            case LAST_SELECTED_LEVEL:
                return stringify(this.lastSelectedLevel);
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
        COINS(new MySQLColumn("coins", Column.Type.INT)),
        EXPERIENCE(new MySQLColumn("experience", Column.Type.BIGINT)),
        LAST_SELECTED_ID(new MySQLColumn("last_selected_id", Column.Type.BIGINT)),
        LAST_SELECTED_LEVEL(new MySQLColumn("last_selected_level", Column.Type.INT));

        @Getter private final MySQLColumnInstance column;
    }

    public static KitPvPPlayerModel findOrInitializeBy(UUID uuid) {
        KitPvPPlayerModel model = findBy(KitPvPPlayerModel.class, KitPvPPlayerModel.column.UUID.is(uuid));

        if (model != null)
            return model;

        return new KitPvPPlayerModel(uuid);
    }

    public static class LevelData {

        public final static int maxLevel = 100;

        private final KitPvPPlayerModel playerModel;
        @Getter private int level;

        @Getter private long currentLevelXp;
        @Getter private long nextLevelXp;

        public LevelData(KitPvPPlayerModel playerModel) {
            this.playerModel = playerModel;

            this.level = 0;
            this.currentLevelXp = 0;
            this.nextLevelXp = 0;
        }

        public KitPvPPlayerModel getPlayerModel() {
            return playerModel;
        }

        public Color getColor() {
            return getColor(level);
        }

        public void updateExperienceBar(Player player) {
            player.setLevel(level);
            player.setExp(((float) currentLevelXp) / ((float) nextLevelXp));
        }

        public void update(OMPlayer player, boolean notify) {
            long experience = playerModel.getExperience();

            int level = 0;

            for (int i = 0; i < maxLevel + 1 /* max levels */; i++) {
                long requiredExperience = getRequired(i);

                if (experience >= requiredExperience) {
                    experience -= requiredExperience;

                    level++;
                } else {
                    /* Gained enough experience to level up */
                    if (notify && this.level != level) {
                        player.playSound(Sound.ENTITY_PLAYER_LEVELUP);

                        int fLevel = level;
                        Title<OMPlayer> title = new Title<>(p -> "", p -> "§e§lLevel up! §7You are now " + getColor(fLevel).getCc() + "§lLevel " + fLevel + "§7!", 30, 60, 30);
                        title.send(player);
                    }

                    this.level = level;
                    this.nextLevelXp = requiredExperience;
                    this.currentLevelXp = experience;

                    return;
                }
            }

            /* Highest level reached */
            this.level = maxLevel;
        }

        private long getRequired(int level) {
            return (level + 1) * (500 + 10 * level);
        }

        public int getExperience(int level) {
            int experience = 0;
            for (int i = 0; i < level; i++) {
                experience += getRequired(i);
            }

            return experience;
        }

        public Color getColor(int level) {
            if (level < 10)
                return Color.SILVER;
            else if (level < 20)
                return Color.FUCHSIA;
            else if (level < 30)
                return Color.PURPLE;
            else if (level < 40)
                return Color.AQUA;
            else if (level < 50)
                return Color.BLUE;
            else if (level < 60)
                return Color.TEAL;
            else if (level < 70)
                return Color.LIME;
            else if (level < 80)
                return Color.GREEN;
            else if (level < 90)
                return Color.YELLOW;
            else if (level < 100)
                return Color.ORANGE;
            else
                return Color.RED;
        }
    }
}
