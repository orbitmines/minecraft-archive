package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.guis.stats;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.database.models.TimePlayed;
import com.orbitmines.archive.minecraft._2019.libs.database.models.discord.DiscordUser;
import com.orbitmines.archive.minecraft._2019.libs.database.models.vote.MonthlyVotes;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.PlayerAchievement;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.kitpvp.KitPvPPlayerKitModel;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.kitpvp.KitPvPPlayerModel;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalClaim;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalHome;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalPlayerModel;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalWarp;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.utils.ServerUtils;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;
import com.orbitmines.archive.minecraft._2019.utils.TimeUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.PlayerSkullBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.GUI;
import net.dv8tion.jda.api.entities.User;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;

import java.util.List;

public class StatsGUI<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends GUI<P> {

    private S server;
    private P key;

    public StatsGUI(S server, P viewer, P key) {
        super(54, "§0§lStats (" + key.getName(Name.RAW) + ")", viewer);

        this.server = server;
        this.key = key;

        set(1, 3, new Item<P, MutableItemBuilder>(() -> new ItemBuilder(Material.SUNFLOWER, 1, "§e§l" + NumberUtils.locale(key.getSolars()) + " Solar" + (key.getSolars() == 1 ? "" : "s"))));
        set(1, 4, getItem(server, viewer, key, true).click(event -> new AchievementsGUI<>(server, viewer, key).open()));
        set(1, 5, new Item<P, MutableItemBuilder>(() -> new ItemBuilder(Material.PRISMARINE_SHARD, 1, "§9§l" + NumberUtils.locale(key.getPrisms()) + " Prism" + (key.getPrisms() == 1 ? "" : "s"))));

        set(4, 4, Server.SURVIVAL);
        set(3, 3, Server.KITPVP);

        {
            ItemBuilder item = new ItemBuilder(Material.RED_STAINED_GLASS_PANE, 1, "§c" + viewer.translate("spigot", "player.unknown_galaxies"));

            set(3, 1, new Item<P, MutableItemBuilder>(() -> item));
            set(3, 5, new Item<P, MutableItemBuilder>(() -> item));
            set(3, 7, new Item<P, MutableItemBuilder>(() -> item));
            set(4, 2, new Item<P, MutableItemBuilder>(() -> item));
            set(4, 6, new Item<P, MutableItemBuilder>(() -> item));
        }
    }

    private void set(int row, int slot, Server server) {
        set(row, slot, getItem(viewer, key, server, true).click(event -> new ServerStatsGUI<>(this.server, this.viewer, this.key, server).open()));
    }

    public static <S extends OMServer<S, P>, P extends OMPlayer<S, P>> Item<P, MutableItemBuilder> getItem(S server, P viewer, P key, boolean generalStats) {
        return new Item<>(() -> {
            PlayerSkullBuilder item = new PlayerSkullBuilder(key.getUUID(), 1, key.getName(Name.RAW_COLORED));
            item.addLore("§7Rank: " + key.getRank().getDisplayName() + (!key.getStaffRank().isNone() && !key.getVipRank().isNone() ? " §7/ " + key.getVipRank().getDisplayName() : ""));

            /* Discord */
            DiscordUser discordUser = key.getDiscordUser();
            User user = discordUser != null && server.getDiscordBot() != null ? discordUser.getDiscordUser(server.getDiscordBot()) : null;
            item.addLore("§7Discord: " + (user != null ? "§9§l" + user.getName() : StaffRank.NONE.getDisplayName()));

            /* Time Played */
            List<TimePlayed> timePlayed = key.getTimePlayed(false);
            long totalTimePlayed = 0;
            for (TimePlayed played : timePlayed) {
                totalTimePlayed += played.getSeconds();
            }
            totalTimePlayed = totalTimePlayed * 1000;

            item.addLore("§7" + viewer.translate("spigot", "player.stats.time_played", "§a§l" + TimeUtils.humanFriendlyTimeUnit(viewer.getLanguage(), totalTimePlayed, TimeUtils.Unit.HOURS)));

            /* First Login */
            item.addLore("§7" + viewer.translate("spigot", "player.stats.member_since", "§a§l" + DateUtils.format(key.getFirstLoginAt(), DateUtils.DATE_FORMAT)));

            /* Votes */
            item.addLore("");

            List<MonthlyVotes> monthlyVotes = key.getMonthlyVotes(false);
            int totalVotes = 0;
            for (MonthlyVotes votes : monthlyVotes) {
                totalVotes += votes.getVotes();
            }

            MonthlyVotes thisMonth = key.getVotesThisMonth(false);

            item.addLore("§7" + viewer.translate("spigot", "player.stats.votes.this_month", DateUtils.humanFriendlyMonth(), "§9§l" + thisMonth.getVotes()) + "§7");
            item.addLore("§7" + viewer.translate("spigot", "player.stats.votes.total", "§9§l" + NumberUtils.locale(totalVotes)) + "§7");

            /* Achievements */
            int completedAchievementCount = 0;
            int totalAchievementCount = 0;
            for (PlayerAchievement achievement : key.getAchievements(false)) {
                if (achievement.isCompleted())
                    completedAchievementCount++;

                totalAchievementCount++;
            }

            item.addLore("");
            item.addLore("§7Achievements: §d§l" + completedAchievementCount + "§7§l / " + totalAchievementCount);
            item.addLore("");
            item.addLore("§7Cosmetics");
            item.addLore("§7  §a§l" + viewer.translate("spigot", "word.coming_soon"));

            if (generalStats) {
                item.addLore("");
                item.addLore("§a" + viewer.translate("spigot", "player.stats.achievements.hover"));
            }

            return item;
        });
    }

    public static <S extends OMServer<S, P>, P extends OMPlayer<S, P>> Item<P, MutableItemBuilder> getItem(P viewer, P key, Server serverType, boolean generalStats) {
        return new Item<>(() -> {
            ItemBuilder item = ServerUtils.getItemBuilder(serverType);
            item.setDisplayName("§8§lOrbit§7§lMines " + serverType.getDisplayName());

            /* Time Played */
            TimePlayed timePlayed = key.getTimePlayed(serverType, false);

            item.addLore("§7" + viewer.translate("spigot", "player.stats.time_played", "§a§l" + TimeUtils.humanFriendlyTimeUnit(viewer.getLanguage(), timePlayed.getSeconds() * 1000L, TimeUtils.Unit.HOURS)));
            item.addLore("");

            switch (serverType) {

                case KITPVP: {
                    item.setMaterial(Material.IRON_SWORD);
                    item.addFlag(ItemFlag.HIDE_ATTRIBUTES);

                    KitPvPPlayerModel model = KitPvPPlayerModel.findOrInitializeBy(key.getUUID());
                    List<KitPvPPlayerKitModel> kits = model.getKits();
                    KitPvPPlayerModel.LevelData levelData = model.getLevelData();

                    item.addLore("§7Coins: §6§l" + NumberUtils.locale(model.getCoins()));
                    item.addLore("§7Level: " + levelData.getColor().getCc() + "§l" + levelData.getLevel());
                    item.addLore((levelData.getLevel() == KitPvPPlayerModel.LevelData.maxLevel ? levelData.getColor() + "Max Level" : "§7Next level: §e§l" + NumberUtils.locale(levelData.getCurrentLevelXp()) + " XP §7§l/ " + NumberUtils.locale(levelData.getNextLevelXp())));
                    item.addLore("");
                    item.addLore("§7Kills: §c§l" + NumberUtils.locale(model.getKills(kits)));
                    item.addLore("§7Deaths: §4§l" + NumberUtils.locale(model.getDeaths(kits)));
                    item.addLore("§7Best streak: §5§l" + NumberUtils.locale(model.getBestStreak(kits)));
                    item.addLore("§7Damage dealt: §c§l" + NumberUtils.locale(model.getDamageDealt(kits)));
                    item.addLore("");
                    break;
                }
                case SURVIVAL: {
                    SurvivalPlayerModel model = SurvivalPlayerModel.findOrInitializeBy(key.getUUID());
                    int claims = SurvivalClaim.getAll(SurvivalClaim.class, SurvivalClaim.column.OWNER.is(key.getUUID())).size();
                    int homes = SurvivalHome.getAll(SurvivalHome.class, SurvivalHome.column.UUID.is(key.getUUID())).size();
                    int warps = SurvivalWarp.getAll(SurvivalWarp.class, SurvivalWarp.column.OWNER.is(key.getUUID())).size();

                    item.addLore("§7Credits: §2§l" + NumberUtils.locale(model.getCredits()));
                    item.addLore("§7Claimblocks: §9§l" + NumberUtils.locale(model.getClaimBlocks()));
                    item.addLore("");
                    item.addLore("§7Claims: §a§l" + NumberUtils.locale(claims));
                    item.addLore("");
                    item.addLore("§7Homes: §6§l" + NumberUtils.locale(homes));
                    item.addLore("§7Warps: §3§l" + NumberUtils.locale(warps));
                    item.addLore("");
                    item.addLore("§7Back Charges: §6§l" + NumberUtils.locale(model.getBackCharges()));
                    item.addLore("");
                    break;
                }
                default:
                    throw new IllegalStateException();
            }

            int completedAchievementCount = 0;
            int totalAchievementCount = 0;
            for (PlayerAchievement achievement : key.getAchievements(serverType, false)) {
                if (achievement.isCompleted())
                    completedAchievementCount++;

                totalAchievementCount++;
            }

            item.addLore("§7Achievements: §d§l" + completedAchievementCount + "§7§l / " + totalAchievementCount);

            if (generalStats) {
                item.addLore("");
                item.addLore("§a" + viewer.translate("spigot", "player.stats.achievements.hover"));
            }

            return item;
        });
    }
}
