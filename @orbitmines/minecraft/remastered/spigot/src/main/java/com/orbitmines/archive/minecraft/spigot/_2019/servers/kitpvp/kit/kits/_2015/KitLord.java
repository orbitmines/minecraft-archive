package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.kits._2015;

/*
 * OrbitMines - @author Fadi Shawki - 2015
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.item_builders.KitItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.HealthRegen;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.KitClass;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.KitPvPKit;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.PotionBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.kits.Kit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.potion.PotionEffectType;

public class KitLord extends KitPvPKit {

    public static final long ID = 19L;

    public KitLord(KitPvP kitPvP) {
        super(
                kitPvP,

                ID, "Lord",

                Color.YELLOW,
                new ItemBuilder(Material.GOLDEN_SWORD).addFlag(ItemFlag.HIDE_ATTRIBUTES),

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
            return 12500;
        }

        @Override
        protected Kit<KitPvPPlayer> registerKit() {
            Kit<KitPvPPlayer> kit = new Kit<>();

            kit.set(0, player -> new KitItemBuilder(this, Material.GOLDEN_SWORD).addEnchantment(Enchantment.UNBREAKING, 3));
            kit.set(1, player -> new KitItemBuilder(this, Material.GOLDEN_APPLE, 5));

            kit.setHelmet(player -> new KitItemBuilder(this, Material.GOLDEN_HELMET).addEnchantment(Enchantment.UNBREAKING, 3));
            kit.setChestplate(player -> new KitItemBuilder(this, Material.CHAINMAIL_CHESTPLATE));
            kit.setLeggings(player -> new KitItemBuilder(this, Material.CHAINMAIL_LEGGINGS));
            kit.setBoots(player -> new KitItemBuilder(this, Material.CHAINMAIL_BOOTS));

            kit.addPotionBuilder(new PotionBuilder(PotionEffectType.STRENGTH, Integer.MAX_VALUE, 0, true, false, true));

            return kit;
        }

        @Override
        public double getMaxHealth() {
            return 36D;
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
            return 30000;
        }

        @Override
        protected Kit<KitPvPPlayer> registerKit() {
            Kit<KitPvPPlayer> kit = new Kit<>();

            kit.set(0, player -> new KitItemBuilder(this, Material.GOLDEN_SWORD).addEnchantment(Enchantment.SHARPNESS, 1).addEnchantment(Enchantment.UNBREAKING, 3));
            kit.set(1, player -> new KitItemBuilder(this, Material.GOLDEN_APPLE, 7));

            kit.setHelmet(player -> new KitItemBuilder(this, Material.GOLDEN_HELMET).addEnchantment(Enchantment.PROTECTION, 1).addEnchantment(Enchantment.UNBREAKING, 3));
            kit.setChestplate(player -> new KitItemBuilder(this, Material.CHAINMAIL_CHESTPLATE).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setLeggings(player -> new KitItemBuilder(this, Material.CHAINMAIL_LEGGINGS));
            kit.setBoots(player -> new KitItemBuilder(this, Material.CHAINMAIL_BOOTS));

            kit.addPotionBuilder(new PotionBuilder(PotionEffectType.STRENGTH, Integer.MAX_VALUE, 0, true, false, true));

            return kit;
        }

        @Override
        public double getMaxHealth() {
            return 36D;
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
            return 70000;
        }

        @Override
        protected Kit<KitPvPPlayer> registerKit() {
            Kit<KitPvPPlayer> kit = new Kit<>();

            kit.set(0, player -> new KitItemBuilder(this, Material.GOLDEN_SWORD).addEnchantment(Enchantment.SHARPNESS, 2).addEnchantment(Enchantment.UNBREAKING, 3));
            kit.set(1, player -> new KitItemBuilder(this, Material.GOLDEN_APPLE, 10));

            kit.setHelmet(player -> new KitItemBuilder(this, Material.GOLDEN_HELMET).addEnchantment(Enchantment.PROTECTION, 2).addEnchantment(Enchantment.UNBREAKING, 3));
            kit.setChestplate(player -> new KitItemBuilder(this, Material.CHAINMAIL_CHESTPLATE).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setLeggings(player -> new KitItemBuilder(this, Material.CHAINMAIL_LEGGINGS).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setBoots(player -> new KitItemBuilder(this, Material.CHAINMAIL_BOOTS).addEnchantment(Enchantment.PROTECTION, 1));

            kit.addPotionBuilder(new PotionBuilder(PotionEffectType.STRENGTH, Integer.MAX_VALUE, 0, true, false, true));

            return kit;
        }

        @Override
        public double getMaxHealth() {
            return 36D;
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
