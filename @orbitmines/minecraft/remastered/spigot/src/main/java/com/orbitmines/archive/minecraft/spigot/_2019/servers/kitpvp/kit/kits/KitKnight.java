package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.kits;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.item_builders.KitItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.item_builders.KitPotionItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.HealthRegen;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.KitClass;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.KitPvPKit;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.PotionBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.PotionItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.kits.Kit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.potion.PotionEffectType;

public class KitKnight extends KitPvPKit {

    public static final long ID = 0L;

    public KitKnight(KitPvP kitPvP) {
        super(
                kitPvP,

                ID, "Knight",

                Color.GREEN,
                new ItemBuilder(Material.IRON_SWORD).addFlag(ItemFlag.HIDE_ATTRIBUTES),

                KitClass.MELEE
        );
    }

    @Override
    protected Level[] registerLevels() {
        return new Level[]{
                new Level1(),
                new Level2(),
                new Level3()
        };
    }

    protected class Level1 extends Level {

        @Override
        public int getPrice() {
            return 0;
        }

        @Override
        protected Kit<KitPvPPlayer> registerKit() {
            Kit<KitPvPPlayer> kit = new Kit<>();

            kit.set(0, player -> new KitItemBuilder(this, Material.IRON_SWORD));
            kit.set(1, player -> new KitPotionItemBuilder(this, PotionItemBuilder.Type.SPLASH, new PotionBuilder(PotionEffectType.INSTANT_HEALTH, 0)));

            kit.setHelmet(player -> new KitItemBuilder(this, Material.CHAINMAIL_HELMET));
            kit.setChestplate(player -> new KitItemBuilder(this, Material.CHAINMAIL_CHESTPLATE));
            kit.setLeggings(player -> new KitItemBuilder(this, Material.CHAINMAIL_LEGGINGS));
            kit.setBoots(player -> new KitItemBuilder(this, Material.CHAINMAIL_BOOTS));

            return kit;
        }

        @Override
        public double getMaxHealth() {
            return 20D;
        }

        @Override
        public double getKnockbackResistance() {
            return 0.0D;
        }

        @Override
        public HealthRegen getHealthRegen() {
            return HealthRegen.HIGHEST;
        }
    }

    protected class Level2 extends Level {

        @Override
        public int getPrice() {
            return 20000;
        }

        @Override
        protected Kit<KitPvPPlayer> registerKit() {
            Kit<KitPvPPlayer> kit = new Kit<>();

            kit.set(0, player -> new KitItemBuilder(this, Material.IRON_SWORD));
            kit.set(1, player -> new KitPotionItemBuilder(this, PotionItemBuilder.Type.SPLASH, new PotionBuilder(PotionEffectType.INSTANT_HEALTH, 1)));

            kit.setHelmet(player -> new KitItemBuilder(this, Material.CHAINMAIL_HELMET).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setChestplate(player -> new KitItemBuilder(this, Material.CHAINMAIL_CHESTPLATE));
            kit.setLeggings(player -> new KitItemBuilder(this, Material.CHAINMAIL_LEGGINGS).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setBoots(player -> new KitItemBuilder(this, Material.CHAINMAIL_BOOTS));

            return kit;
        }

        @Override
        public double getMaxHealth() {
            return 20D;
        }

        @Override
        public double getKnockbackResistance() {
            return 0.0D;
        }

        @Override
        public HealthRegen getHealthRegen() {
            return HealthRegen.HIGHEST;
        }
    }

    protected class Level3 extends Level {

        @Override
        public int getPrice() {
            return 50000;
        }

        @Override
        protected Kit<KitPvPPlayer> registerKit() {
            Kit<KitPvPPlayer> kit = new Kit<>();

            kit.set(0, player -> new KitItemBuilder(this, Material.IRON_SWORD).addEnchantment(Enchantment.SHARPNESS, 1));
            kit.set(1, player -> new KitPotionItemBuilder(this, PotionItemBuilder.Type.SPLASH, new PotionBuilder(PotionEffectType.INSTANT_HEALTH, 1), 2));

            kit.setHelmet(player -> new KitItemBuilder(this, Material.CHAINMAIL_HELMET).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setChestplate(player -> new KitItemBuilder(this, Material.CHAINMAIL_CHESTPLATE).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setLeggings(player -> new KitItemBuilder(this, Material.CHAINMAIL_LEGGINGS).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setBoots(player -> new KitItemBuilder(this, Material.CHAINMAIL_BOOTS).addEnchantment(Enchantment.PROTECTION, 1));

            return kit;
        }

        @Override
        public double getMaxHealth() {
            return 20D;
        }

        @Override
        public double getKnockbackResistance() {
            return 0.0D;
        }

        @Override
        public HealthRegen getHealthRegen() {
            return HealthRegen.HIGHEST;
        }
    }
}
