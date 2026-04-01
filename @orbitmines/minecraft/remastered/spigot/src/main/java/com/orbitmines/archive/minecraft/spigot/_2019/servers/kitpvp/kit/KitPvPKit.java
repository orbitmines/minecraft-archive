package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.item_builders.KitItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilderInstance;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.kits.Kit;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public abstract class KitPvPKit {

    @Getter private static List<KitPvPKit> kits = new ArrayList<>();

    @Getter protected final KitPvP server;
    @Getter protected final long id;
    @Getter protected final String name;
    @Getter protected final Color color;
    protected final ItemBuilderInstance icon;
    @Getter protected final KitClass kitClass;
    @Getter protected final Level[] levels;

    public KitPvPKit(KitPvP server, long id, String name, Color color, ItemBuilderInstance icon, KitClass kitClass) {
        this.server = server;
        this.id = id;
        this.name = name;
        this.color = color;
        this.icon = icon;
        this.kitClass = kitClass;
        this.levels = registerLevels();

        kits.add(this);
    }

    protected abstract Level[] registerLevels();

    public String getDisplayName() {
        return color.getCc() + "§l" + name;
    }

    public ItemBuilderInstance getIcon() {
        return icon.clone();
    }

    public Level getLevel(int level) {
        try {
            return levels[level - 1];
        } catch(IndexOutOfBoundsException ex) {
            return null;
        }
    }

    public void give(KitPvPPlayer omp, int level) {
        Level lvl = getLevel(level);

        if (lvl != null)
            lvl.give(omp);
    }

    public static KitPvPKit getKit(long kitId) {
        for (KitPvPKit kit : kits) {
            if (kit.getId() == kitId)
                return kit;
        }
        return null;
    }

    public abstract class Level implements KitAttributes {

        @Getter protected final Kit<KitPvPPlayer> kit;

        public Level() {
            this.kit = registerKit();
            this.kit.set(8, player -> new KitItemBuilder(this, KitPvP.PLAYER_TRACKER));
        }

        public abstract int getPrice();

        protected abstract Kit<KitPvPPlayer> registerKit();

        public KitPvPKit getHandler() {
            return KitPvPKit.this;
        }

        public int getLevel() {
            for (int l = 0; l < levels.length; l++) {
                if (levels[l] == this)
                    return l + 1;
            }
            throw new ArrayIndexOutOfBoundsException();
        }

        public void give(KitPvPPlayer omp) {
            kit.copyToInventory(omp);
        }
    }
}
