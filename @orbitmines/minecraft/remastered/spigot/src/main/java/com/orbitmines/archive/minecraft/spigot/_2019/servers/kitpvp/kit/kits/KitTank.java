package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.kits;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
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

public class KitTank extends KitPvPKit {

    public static final long ID = 4L;

    public KitTank(KitPvP kitPvP) {
        super(
                kitPvP,

                ID, "Tank",

                Color.YELLOW,
                new ItemBuilder(Material.IRON_CHESTPLATE).addFlag(ItemFlag.HIDE_ATTRIBUTES),

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
            return 10000;
        }

        @Override
        protected Kit<KitPvPPlayer> registerKit() {
            Kit<KitPvPPlayer> kit = new Kit<>();

            kit.set(0, player -> new KitItemBuilder(this, Material.WOODEN_SWORD).addPassive(Passive.SUCKER_PUNCH, 1).addEnchantment(Enchantment.KNOCKBACK, 1));

            kit.setHelmet(player -> new KitItemBuilder(this, Material.IRON_HELMET));
            kit.setChestplate(player -> new KitItemBuilder(this, Material.IRON_CHESTPLATE));
            kit.setLeggings(player -> new KitItemBuilder(this, Material.IRON_LEGGINGS));
            kit.setBoots(player -> new KitItemBuilder(this, Material.DIAMOND_BOOTS));

            kit.addPotionBuilder(new PotionBuilder(PotionEffectType.SLOW, Integer.MAX_VALUE, 1, true, false, true));

            return kit;
        }

        @Override
        public double getMaxHealth() {
            return 25D;
        }

        @Override
        public double getKnockbackResistance() {
            return 0.25D;
        }

        @Override
        public HealthRegen getHealthRegen() {
            return HealthRegen.EXTREMELY_LOW;
        }
    }

    protected class Level2 extends Level {

        @Override
        public int getPrice() {
            return 25000;
        }

        @Override
        protected Kit<KitPvPPlayer> registerKit() {
            Kit<KitPvPPlayer> kit = new Kit<>();

            kit.set(0, player -> new KitItemBuilder(this, Material.WOODEN_SWORD).addPassive(Passive.SUCKER_PUNCH, 2).addEnchantment(Enchantment.KNOCKBACK, 1));

            kit.setHelmet(player -> new KitItemBuilder(this, Material.IRON_HELMET));
            kit.setChestplate(player -> new KitItemBuilder(this, Material.IRON_CHESTPLATE));
            kit.setLeggings(player -> new KitItemBuilder(this, Material.IRON_LEGGINGS));
            kit.setBoots(player -> new KitItemBuilder(this, Material.DIAMOND_BOOTS).addEnchantment(Enchantment.PROTECTION, 2));

            kit.addPotionBuilder(new PotionBuilder(PotionEffectType.SLOW, Integer.MAX_VALUE, 1, true, false, true));

            return kit;
        }

        @Override
        public double getMaxHealth() {
            return 25D;
        }

        @Override
        public double getKnockbackResistance() {
            return 0.25D;
        }

        @Override
        public HealthRegen getHealthRegen() {
            return HealthRegen.EXTREMELY_LOW;
        }
    }

    protected class Level3 extends Level {

        @Override
        public int getPrice() {
            return 60000;
        }

        @Override
        protected Kit<KitPvPPlayer> registerKit() {
            Kit<KitPvPPlayer> kit = new Kit<>();

            kit.set(0, player -> new KitItemBuilder(this, Material.STONE_SWORD).addPassive(Passive.SUCKER_PUNCH, 3).addEnchantment(Enchantment.KNOCKBACK, 2));

            kit.setHelmet(player -> new KitItemBuilder(this, Material.IRON_HELMET));
            kit.setChestplate(player -> new KitItemBuilder(this, Material.IRON_CHESTPLATE));
            kit.setLeggings(player -> new KitItemBuilder(this, Material.IRON_LEGGINGS).addEnchantment(Enchantment.PROTECTION, 1).addEnchantment(Enchantment.PROJECTILE_PROTECTION, 2));
            kit.setBoots(player -> new KitItemBuilder(this, Material.DIAMOND_BOOTS).addEnchantment(Enchantment.PROTECTION, 2));

            kit.addPotionBuilder(new PotionBuilder(PotionEffectType.SLOW, Integer.MAX_VALUE, 1, true, false, true));

            return kit;
        }

        @Override
        public double getMaxHealth() {
            return 25D;
        }

        @Override
        public double getKnockbackResistance() {
            return 0.25D;
        }

        @Override
        public HealthRegen getHealthRegen() {
            return HealthRegen.EXTREMELY_LOW;
        }
    }
}
