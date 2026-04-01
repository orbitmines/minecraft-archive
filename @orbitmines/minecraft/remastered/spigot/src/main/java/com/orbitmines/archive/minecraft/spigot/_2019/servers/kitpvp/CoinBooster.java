package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.player.OfflinePlayer;
import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;
import com.orbitmines.archive.minecraft._2019.utils.TimeUtils;
import com.orbitmines.archive.minecraft._2019.utils.language.Language;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.Interval;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.SpigotTimer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.TimeUnit;
import lombok.Getter;
import org.bukkit.Material;

import java.util.UUID;

public class CoinBooster {

    public static CoinBooster ACTIVE;

    private final KitPvP server;

    @Getter private final Type type;
    @Getter private final OfflinePlayer player;

    @Getter private SpigotTimer timer;

    public CoinBooster(KitPvP server, Type type, UUID uuid) {
        this.server = server;

        this.type = type;
        this.player = OfflinePlayer.get(uuid);
    }

    public void start() {
        String name = player.getName(Name.RAW_COLORED);
        String boosterName = type.getColor().getCc() + "§l" + type.multiplier + "x Coin Booster";

        server.broadcastRaw("", Color.ORANGE, name + " §7has activated a " + boosterName + " §7(§6" + TimeUtils.humanFriendlyTimer(Language.ENGLISH, type.duration.toMillis()) + "§7).");

        timer = new SpigotTimer(server, type.duration, Interval.of(TimeUnit.MINUTE, 5)) {
            @Override
            public void onInterval() {
                server.broadcastRaw("", Color.ORANGE, name + "'s " + boosterName + " §7expires in §6" + TimeUtils.humanFriendlyTimer(Language.ENGLISH, getRemainingTicks() * 50) + "§7.");
            }

            @Override
            public void onFinish() {
                server.broadcastRaw("", Color.ORANGE, name + "'s " + boosterName + " §7has expired.");
                ACTIVE = null;
            }
        };
        timer.async().start();

        ACTIVE = this;
    }

    public enum Type {

        DEFAULT(1.25, VipRank.NONE, 250, "Coin Booster", VipRank.NONE.getPrefixColor(), new ItemBuilder(Material.GOLD_NUGGET), Interval.of(TimeUnit.SECOND, 30 * 60)),
        IRON(1.5, VipRank.IRON, 250, VipRank.IRON.getName() + " Coin Booster", VipRank.IRON.getPrefixColor(), new ItemBuilder(Material.IRON_INGOT), Interval.of(TimeUnit.SECOND, 30 * 60)),
        GOLD(1.75, VipRank.GOLD, 250, VipRank.GOLD.getName() + " Coin Booster", VipRank.GOLD.getPrefixColor(), new ItemBuilder(Material.GOLD_INGOT), Interval.of(TimeUnit.SECOND, 30 * 60)),
        DIAMOND(2.0, VipRank.DIAMOND, 250, VipRank.DIAMOND.getName() + " Coin Booster", VipRank.DIAMOND.getPrefixColor(), new ItemBuilder(Material.DIAMOND), Interval.of(TimeUnit.SECOND, 30 * 60)),
        EMERALD(2.5, VipRank.EMERALD, 250, VipRank.EMERALD.getName() + " Coin Booster", VipRank.EMERALD.getPrefixColor(), new ItemBuilder(Material.EMERALD), Interval.of(TimeUnit.SECOND, 30 * 60));

        @Getter private final double multiplier;
        @Getter private final VipRank vipRank;
        @Getter private final int price;
        @Getter private final String name;
        @Getter private final Color color;
        private final ItemBuilder icon;
        @Getter private final Interval duration;

        Type(double multiplier, VipRank vipRank, int price, String name, Color color, ItemBuilder icon, Interval duration) {
            this.multiplier = multiplier;
            this.vipRank = vipRank;
            this.price = price;
            this.name = name;
            this.color = color;
            this.icon = icon;
            this.duration = duration;
        }

        public String getDisplayName() {
            return color.getCc() + "§l" + name;
        }

        public ItemBuilder getIcon() {
            return icon.clone();
        }
    }
}
