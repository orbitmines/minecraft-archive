package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.datapoints.lobby;

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.KitPvPKit;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.PotionBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilderInstance;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.PotionItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPointLoader;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.maps.datapoints.DataPointSign;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */
@Deprecated
public class KitPvPDataPointLobbyKitInfo extends DataPointSign {

    @Getter private Map<Location, KitInfo> kitInfo;

    public KitPvPDataPointLobbyKitInfo() {
        super("KIT_INFO", Type.GOLD_PLATE, Material.MAGENTA_WOOL);

        kitInfo = new HashMap<>();
    }

    @Override
    public boolean buildAt(DataPointLoader loader, Location location, String[] data) {
        KitInfo kitInfo;
        if (data.length >= 1) {
            try {
                kitInfo = KitInfo.valueOf(data[0]);
            } catch(IllegalArgumentException ex) {
                failureMessage = "Invalid KitInfo.";
                return false;
            }
        } else {
            failureMessage = "KitInfo not given.";
            return false;
        }

        this.kitInfo.put(location.add(0.5, 0.5, 0.5), kitInfo);
        return true;
    }

    @Override
    public boolean setup() {
        return true;
    }

    public enum KitInfo {

        CLASS("Class", Color.AQUA, 1, 0, new ItemBuilder(Material.BOOK)) {
            @Override
            public String getDescription(KitPvPPlayer player, KitPvPKit.Level kit) {
                return kit.getHandler().getKitClass().getName();
            }
        },
        MAX_HEALTH("Health", Color.RED, 2, 0, new ItemBuilder(Material.BEETROOT)) {
            @Override
            public String getDescription(KitPvPPlayer player, KitPvPKit.Level kit) {
                return String.format("%.1f", kit.getMaxHealth());
            }
        },
        HEALTH_REGEN("Health Regen", Color.FUCHSIA, 1, 8, new PotionItemBuilder(PotionItemBuilder.Type.NORMAL, new PotionBuilder(PotionEffectType.REGENERATION, 1)).addFlag(ItemFlag.HIDE_POTION_EFFECTS)) {
            @Override
            public String getDescription(KitPvPPlayer player, KitPvPKit.Level kit) {
                return kit.getHealthRegen().getName() + " (" + String.format("%.1f", kit.getHealthRegen().getMultiplier() * 100) + "%)";
            }
        },
        KNOCKBACK_RES("Knockback Evading", Color.ORANGE, 2, 8, new ItemBuilder(Material.SHIELD)) {
            @Override
            public String getDescription(KitPvPPlayer player, KitPvPKit.Level kit) {
                return String.format("%.1f", kit.getKnockbackResistance() * 100) + "%";
            }
        };

        @Getter protected final String name;
        @Getter protected final Color color;
        @Getter private final int row;
        @Getter private final int slot;
        private final ItemBuilderInstance icon;

        KitInfo(String name, Color color, int row, int slot, ItemBuilderInstance icon) {
            this.name = name;
            this.color = color;
            this.row = row;
            this.slot = slot;
            this.icon = icon;
        }

        public String getDisplayName() {
            return color.getCc() + "§l" + name;
        }

        public ItemBuilderInstance getIcon() {
            return icon.clone();
        }

        public String getDescription(KitPvPPlayer player, KitPvPKit.Level kit) {
            throw new IllegalStateException();
        }
    }
}
