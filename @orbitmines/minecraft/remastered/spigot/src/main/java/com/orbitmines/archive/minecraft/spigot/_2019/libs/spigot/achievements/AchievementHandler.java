package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.achievements;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft._2019.libs.database.models.loot.LootItem;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.PlayerAchievement;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.loot.LootItemType;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.Sound;

@Deprecated
public abstract class AchievementHandler<S extends OMServer<S, P>, P extends OMPlayer<S, P>> {

    @Getter protected Achievement achievement;

    public AchievementHandler(Achievement achievement) {
        this.achievement = achievement;
    }

    public abstract boolean hasCompleted(P player);

    public String getName(P player) {
        return achievement.getName(player);
    }

    public String getDescription(P player) {
        return StringUtils.join(achievement.getDescription(player), " ");
    }

    public ItemBuilder getItemBuilder(P player) {
        PlayerAchievement playerAchievement = getPlayerAchievement(player);

        ItemBuilder item = new ItemBuilder(playerAchievement.isCompleted() ? Material.EXPERIENCE_BOTTLE : Material.GLASS_BOTTLE, 1, (playerAchievement.isCompleted() ? "§a§l" : "§c§l") + achievement.getName(player));
        if (playerAchievement.isCompleted())
            item.addLore("§a§l§o" + player.translate("spigot", "word.completed").toUpperCase());

        item.addLore("");

        for (String line : achievement.getDescription(player)) {
            item.addLore("§7§o" + line);
        }

        item.addLore("");
        item.addLore("§7" + player.translate("spigot", "word.rewards"));

        for (LootItem lootItem : achievement.getRewards()) {
            item.addLore("§7- " + LootItemType.from(lootItem.getType()).getDisplayName(lootItem.getCount()));
        }

        return item;
    }

    public void complete(P player, boolean notify) {
        PlayerAchievement playerAchievement = getPlayerAchievement(player);

        if (playerAchievement.isCompleted())
            return;

        LootItem[] rewards = achievement.getRewards();

        if (notify) {
            player.sendMessage("§5§m---------------------------------------------");
            player.sendMessage("  §d§lACHIEVEMENT " + player.translate("spigot", "word.completed"));
            player.sendMessage("");
            player.sendMessage("  §e§l" + getName(player));
            player.sendMessage("    §7" + getDescription(player));
            player.sendMessage("");
            player.sendMessage("  §7" + player.translate("spigot", "word.rewards") + ": " + lootToString());
            player.sendMessage("§5§m---------------------------------------------");

            player.playSound(Sound.ENTITY_ARROW_HIT_PLAYER);
        }

        playerAchievement.complete();

        /* Give rewards */
        for (LootItem lootItem : rewards) {
            LootItem item = lootItem.copyFor(player.getUUID());
            item.getDescription().add("§d§l§oAchievement (" + getName(player) + ")");

            item.insert();
            player.getLootItems(false).add(item);
        }
    }

    private String lootToString() {
        StringBuilder stringBuilder = new StringBuilder();
        LootItem[] rewards = achievement.getRewards();

        for (int i = 0; i < rewards.length; i++) {
            LootItem loot = rewards[i];

            if (i != 0)
                stringBuilder.append("§7, ");

            stringBuilder.append(LootItemType.from(loot.getType()).getDisplayName(loot.getCount()));
        }

        return stringBuilder.toString();
    }

    protected PlayerAchievement getPlayerAchievement(P player) {
        for (PlayerAchievement achievement : player.getAchievements(false)) {
            if (achievement.getAchievement().equals(this.getAchievement()))
                return achievement;
        }

        return null;
    }
}
