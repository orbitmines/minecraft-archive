package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.actives.ActiveHealing;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.actives.ActiveSugarRush;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;
import com.orbitmines.archive.minecraft._2019.utils.cooldown.Cooldown;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ItemUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.PotionBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.itemstack.ItemStackNms;
import lombok.Getter;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public enum Active {

    SUGAR_RUSH("Sugar Rush", Color.WHITE, new ActiveSugarRush()) {
        @Override
        public String[] getDescription(int level) {
            ActiveSugarRush active = (ActiveSugarRush) getHandler();
            PotionBuilder builder = active.getBuilder(level);

            return new String[] {
                    "  §7§oReceive §e§o" + ItemUtils.getName(builder.getType()) + " " + NumberUtils.toRoman(builder.getAmplifier() + 1),
                    "  §7§ofor §9§o" + (builder.getDuration() / 20) + " seconds§7§o on a",
                    "  §b§o" + ((int) (active.getCooldown(level).getCooldown() / 1000)) + " second cooldown§7."
            };
        }
    },
    HEAL("Healing", Color.FUCHSIA, new ActiveHealing()) {
        @Override
        public String[] getDescription(int level) {
            ActiveHealing active = (ActiveHealing) getHandler();
            PotionBuilder builder = active.getBuilder(level);

            return new String[]{
                    "  §7§oReceive §e§o" + ItemUtils.getName(builder.getType()) + " " + NumberUtils.toRoman(builder.getAmplifier() + 1),
                    "  §7§ofor §9§o" + (builder.getDuration() / 20) + " seconds§7§o on a",
                    "  §b§o" + ((int) (active.getCooldown(level).getCooldown() / 1000)) + " second cooldown§7."
            };
        }
    };

    @Getter private final String name;
    @Getter private final Color color;
    @Getter private final Handler handler;

    Active(String name, Color color, Handler handler) {
        this.name = name;
        this.color = color;
        this.handler = handler;
    }

    public String getDisplayName(int level) {
        return color.getCc() + name + " " + NumberUtils.toRoman(level) + " §d[ACTIVE]";
    }

    public String[] getDescription(int level) {
        return new String[] {};
    }

    public static Map<Active, Integer> from(ItemStackNms nms, ItemStack itemStack) {
        Map<String, String> metaData = nms.getMetaData(itemStack).getKeys("active");

        if (metaData == null)
            return null;

        Map<Active, Integer> actives = new HashMap<>();

        for (String string : metaData.keySet()) {
            Active passive = Active.valueOf(string);

            actives.put(passive, Integer.parseInt(metaData.get(string)));
        }

        if (actives.size() == 0)
            return null;

        return actives;
    }

    public ItemStack apply(ItemStackNms nms, ItemStack itemStack, int level) {
        return nms.setMetaData(itemStack, "active", toString(), level + "");
    }

    public int getLevel(ItemStackNms nms, ItemStack itemStack) {
        Map<Active, Integer> all = from(nms, itemStack);
        if (all == null)
            return 0;

        return all.getOrDefault(this, 0);
    }

    public boolean canUse(ItemStackNms nms, ItemStack itemStack, int level) {
        Cooldown cooldown = handler.getCooldown(level);

        /* Check if cooldown is actually on cooldown; can use if not on cooldown */
        return !cooldown.onCooldown(getLastUse(nms, itemStack, level));
    }

    public long getLastUse(ItemStackNms nms, ItemStack itemStack, int level) {
        return nms.getMetaData(itemStack).getOrDefault("activeCooldown", toString() + level, -1L);
    }

    public ItemStack resetCooldown(ItemStackNms nms, ItemStack itemStack, int level) {
        return nms.getMetaData(itemStack).set("activeCooldown", toString() + level, System.currentTimeMillis());
    }

    public interface Handler {

        void trigger(PlayerInteractEvent event, KitPvPPlayer omp, int level);

        Cooldown getCooldown(int level);

    }
}
