package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.actives.*;
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
    },

    /* 2015 Kits */
    FIRE_SPELL("Fire Spell", Color.ORANGE, new ActiveFireSpell()) {
        @Override
        public String[] getDescription(int level) {
            return new String[]{
                    "  §7§oShoot §6§ofire projectiles",
                    "  §7§oin a spread pattern on a",
                    "  §b§o" + ((int) (getHandler().getCooldown(level).getCooldown() / 1000)) + " second cooldown§7."
            };
        }
    },
    POTION_LAUNCHER("Potion Launcher", Color.PURPLE, new ActivePotionLauncher()) {
        @Override
        public String[] getDescription(int level) {
            return new String[]{
                    "  §7§oLaunch a §5§osplash potion",
                    "  §7§oof §c§oInstant Damage §7§oon a",
                    "  §b§o" + ((int) (getHandler().getCooldown(level).getCooldown() / 1000)) + " second cooldown§7."
            };
        }
    },
    MAGIC_SPELL("Magic Spell", Color.PURPLE, new ActiveMagicSpell()) {
        @Override
        public String[] getDescription(int level) {
            return new String[]{
                    "  §7§oApply §8§oWither §7§oto all",
                    "  §7§onearby players on a",
                    "  §b§o" + ((int) (getHandler().getCooldown(level).getCooldown() / 1000)) + " second cooldown§7."
            };
        }
    },
    FISH_ATTACK("Fish Attack", Color.AQUA, new ActiveFishAttack()) {
        @Override
        public String[] getDescription(int level) {
            return new String[]{
                    "  §7§oPoison §a§onearby players",
                    "  §7§oin a splash on a",
                    "  §b§o" + ((int) (getHandler().getCooldown(level).getCooldown() / 1000)) + " second cooldown§7."
            };
        }
    },
    SHIELD("Shield", Color.SILVER, new ActiveShield()) {
        @Override
        public String[] getDescription(int level) {
            ActiveShield active = (ActiveShield) getHandler();
            PotionBuilder builder = active.getBuilder(level);

            return new String[]{
                    "  §7§oReceive §e§o" + ItemUtils.getName(builder.getType()) + " " + NumberUtils.toRoman(builder.getAmplifier() + 1),
                    "  §7§ofor §9§o" + (builder.getDuration() / 20) + " seconds§7§o on a",
                    "  §b§o" + ((int) (active.getCooldown(level).getCooldown() / 1000)) + " second cooldown§7."
            };
        }
    },
    HEALING_KIT("Healing Kit", Color.GREEN, new ActiveHealingKit()) {
        @Override
        public String[] getDescription(int level) {
            return new String[]{
                    "  §7§oFully heal yourself.",
                    "  §7§o§cConsumes the item§7§o."
            };
        }
    },
    TNT_LAUNCHER("TNT Launcher", Color.RED, new ActiveTNTLauncher()) {
        @Override
        public String[] getDescription(int level) {
            return new String[]{
                    "  §7§oLaunch §c§oTNT §7§oforward on a",
                    "  §b§o" + ((int) (getHandler().getCooldown(level).getCooldown() / 1000)) + " second cooldown§7."
            };
        }
    },
    BARRIER("Barrier", Color.GREEN, new ActiveBarrier()) {
        @Override
        public String[] getDescription(int level) {
            ActiveBarrier active = (ActiveBarrier) getHandler();
            PotionBuilder builder = active.getBuilder(level);

            return new String[]{
                    "  §7§oSpawn a protective barrier",
                    "  §7§oand gain §e§o" + ItemUtils.getName(builder.getType()) + " " + NumberUtils.toRoman(builder.getAmplifier() + 1),
                    "  §7§oon a §b§o" + ((int) (active.getCooldown(level).getCooldown() / 1000)) + " second cooldown§7."
            };
        }
    },
    BLAZE_INFERNO("Blaze Inferno", Color.ORANGE, new ActiveBlazeInferno()) {
        @Override
        public String[] getDescription(int level) {
            return new String[]{
                    "  §7§oBecome §6§oinvulnerable §7§oand fly up,",
                    "  §7§ospreading a §c§oring of fire §7§oon a",
                    "  §b§o" + ((int) (getHandler().getCooldown(level).getCooldown() / 1000)) + " second cooldown§7."
            };
        }
    },
    REAPER_TELEPORT("Shadow Step", Color.GRAY, new ActiveReaperTeleport()) {
        @Override
        public String[] getDescription(int level) {
            return new String[]{
                    "  §7§oTeleport to a location",
                    "  §7§oup to §8§o15 blocks §7§oaway on a",
                    "  §b§o" + ((int) (getHandler().getCooldown(level).getCooldown() / 1000)) + " second cooldown§7."
            };
        }
    },
    SNOWBALL_THROW("Frost Shot", Color.WHITE, new ActiveSnowballThrow()) {
        @Override
        public String[] getDescription(int level) {
            return new String[]{
                    "  §7§oThrow a §f§osnowball §7§othat",
                    "  §7§odirects your §f§oIron Golem",
                    "  §7§oto attack the target."
            };
        }
    },
    GOLEM_SPEED("Iron Surge", Color.WHITE, new ActiveGolemSpeed()) {
        @Override
        public String[] getDescription(int level) {
            return new String[]{
                    "  §7§oGive your §f§oIron Golem",
                    "  §e§oSpeed II §7§ofor §9§o4 seconds§7§o on a",
                    "  §b§o" + ((int) (getHandler().getCooldown(level).getCooldown() / 1000)) + " second cooldown§7."
            };
        }
    },
    WITHER_STAFF("Wither Staff", Color.GRAY, new ActiveWitherStaff()) {
        @Override
        public String[] getDescription(int level) {
            return new String[]{
                    "  §7§oShoot §8§oWither Skulls§7§o.",
                    "  §7§oConsumes §d§o1 Soul §7§oper use on a",
                    "  §b§o" + ((int) (getHandler().getCooldown(level).getCooldown() / 1000)) + " second cooldown§7."
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
