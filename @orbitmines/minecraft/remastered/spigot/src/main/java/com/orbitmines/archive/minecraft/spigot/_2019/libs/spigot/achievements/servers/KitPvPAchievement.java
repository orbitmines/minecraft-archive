package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.achievements.servers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.database.models.loot.LootItem;
import com.orbitmines.archive.minecraft._2019.libs.loot.Rarity;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.achievements.Achievement;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.achievements.AchievementHandler;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.achievements.StoredProgressAchievement;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.kitpvp.KitPvPPlayerKitModel;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.kitpvp.KitPvPPlayerModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Deprecated
public enum KitPvPAchievement implements Achievement {

    THOR(
        new LootItem(LootItem.Type.PRISMS, Rarity.RARE, 2500),
        new LootItem(LootItem.Type.SOLARS, Rarity.RARE, 50)
    ),
    KINGSLAYER(
        new LootItem(LootItem.Type.PRISMS, Rarity.RARE, 4500),
        new LootItem(LootItem.Type.SOLARS, Rarity.RARE, 75)
    ),
    MERLIN(
        new LootItem(LootItem.Type.PRISMS, Rarity.RARE, 6500),
        new LootItem(LootItem.Type.SOLARS, Rarity.RARE, 100)
    ),
    HUNTER(
        new LootItem(LootItem.Type.PRISMS, Rarity.RARE, 8500),
        new LootItem(LootItem.Type.SOLARS, Rarity.RARE, 100)
    ),
    DRUNKEN_FOOL(
        new LootItem(LootItem.Type.PRISMS, Rarity.RARE, 11000),
        new LootItem(LootItem.Type.SOLARS, Rarity.RARE, 125)
    ),
    MANSLAUGHTER(
        new LootItem(LootItem.Type.PRISMS, Rarity.RARE, 14000),
        new LootItem(LootItem.Type.SOLARS, Rarity.RARE, 175)
    ),
    KEEPING_SCORE(
        new LootItem(LootItem.Type.PRISMS, Rarity.RARE, 20000),
        new LootItem(LootItem.Type.SOLARS, Rarity.RARE, 250)
    );

    static {
        THOR.handler = new StoredProgressAchievement(THOR, 100);
        MERLIN.handler = new StoredProgressAchievement(MERLIN, 15) {
            @Override
            public long getCurrent(OMPlayer player) {
                KitPvPPlayerModel kitpvpModel = KitPvPPlayerModel.findOrInitializeBy(player.getUUID());
                List<KitPvPPlayerKitModel> spellCasters = Arrays.asList(
                    kitpvpModel.getKit(3L),
                    kitpvpModel.getKit(9L)
                );

                return kitpvpModel.getBestStreak(spellCasters);
            }
        };
        KINGSLAYER.handler = new StoredProgressAchievement(KINGSLAYER, 150);
        HUNTER.handler = new StoredProgressAchievement(HUNTER, 100);
        DRUNKEN_FOOL.handler = new StoredProgressAchievement(DRUNKEN_FOOL, 5000) {
            @Override
            public long getCurrent(OMPlayer player) {
                KitPvPPlayerModel kitpvpModel = KitPvPPlayerModel.findOrInitializeBy(player.getUUID());

                return (int) Math.floor(kitpvpModel.getKit(6L).getDamageDealt());
            }
        };
        MANSLAUGHTER.handler = new StoredProgressAchievement(MANSLAUGHTER, 1000) {
            @Override
            public long getCurrent(OMPlayer player) {
                KitPvPPlayerModel kitpvpModel = KitPvPPlayerModel.findOrInitializeBy(player.getUUID());

                return kitpvpModel.getKills(kitpvpModel.getKits());
            }
        };
        KEEPING_SCORE.handler = new StoredProgressAchievement(KEEPING_SCORE, 200000) {
            @Override
            public long getCurrent(OMPlayer player) {
                KitPvPPlayerModel kitpvpModel = KitPvPPlayerModel.findOrInitializeBy(player.getUUID());

                return (int) Math.floor(kitpvpModel.getDamageDealt(kitpvpModel.getKits()));
            }
        };
    }

    @Getter private LootItem[] rewards;
    @Getter @Setter private AchievementHandler handler;

    KitPvPAchievement(LootItem... rewards) {
        this.rewards = rewards;
    }

    @Override
    public int getId() {
        return ordinal();
    }

    @Override
    public Server getServer() {
        return Server.KITPVP;
    }
}
