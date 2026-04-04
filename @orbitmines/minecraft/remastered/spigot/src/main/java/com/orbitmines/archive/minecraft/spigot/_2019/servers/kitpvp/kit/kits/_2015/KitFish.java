package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.kits._2015;

/*
 * OrbitMines - @author Fadi Shawki - 2015
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Active;
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
import org.bukkit.potion.PotionEffectType;

public class KitFish extends KitPvPKit {

    public static final long ID = 14L;

    public KitFish(KitPvP kitPvP) {
        super(
                kitPvP,

                ID, "Fish",

                Color.AQUA,
                new ItemBuilder(Material.COD),

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
            return 15000;
        }

        @Override
        protected Kit<KitPvPPlayer> registerKit() {
            Kit<KitPvPPlayer> kit = new Kit<>();

            kit.set(0, player -> new KitItemBuilder(this, Material.COD).addPassive(Passive.ATTACK_DAMAGE, 8).addActive(Active.FISH_ATTACK, 1));
            kit.set(1, player -> new KitItemBuilder(this, Material.SALMON, 1, "\u00a7b\u00a7lKnockback Fish").addPassive(Passive.ATTACK_DAMAGE, 2).addEnchantment(Enchantment.KNOCKBACK, 3));
            kit.set(2, player -> new KitItemBuilder(this, Material.COOKED_COD).addPassive(Passive.ATTACK_DAMAGE, 2).addEnchantment(Enchantment.FIRE_ASPECT, 2));

            kit.setHelmet(player -> new KitItemBuilder(this, Material.CHAINMAIL_HELMET));
            kit.setChestplate(player -> new KitItemBuilder(this, Material.CHAINMAIL_CHESTPLATE));
            kit.setLeggings(player -> new KitItemBuilder(this, Material.CHAINMAIL_LEGGINGS));
            kit.setBoots(player -> new KitItemBuilder(this, Material.CHAINMAIL_BOOTS));

            kit.addPotionBuilder(new PotionBuilder(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 2, true, false, true));

            return kit;
        }

        @Override
        public double getMaxHealth() {
            return 34D;
        }

        @Override
        public double getKnockbackResistance() {
            return 0D;
        }

        @Override
        public HealthRegen getHealthRegen() {
            return HealthRegen.NORMAL;
        }
    }

    protected class Level2 extends Level {

        @Override
        public int getPrice() {
            return 35000;
        }

        @Override
        protected Kit<KitPvPPlayer> registerKit() {
            Kit<KitPvPPlayer> kit = new Kit<>();

            kit.set(0, player -> new KitItemBuilder(this, Material.COD).addPassive(Passive.ATTACK_DAMAGE, 8).addActive(Active.FISH_ATTACK, 2));
            kit.set(1, player -> new KitItemBuilder(this, Material.SALMON, 1, "\u00a7b\u00a7lKnockback Fish").addPassive(Passive.ATTACK_DAMAGE, 2).addEnchantment(Enchantment.KNOCKBACK, 3));
            kit.set(2, player -> new KitItemBuilder(this, Material.COOKED_COD).addPassive(Passive.ATTACK_DAMAGE, 2).addEnchantment(Enchantment.FIRE_ASPECT, 2));

            kit.setHelmet(player -> new KitItemBuilder(this, Material.CHAINMAIL_HELMET).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setChestplate(player -> new KitItemBuilder(this, Material.CHAINMAIL_CHESTPLATE));
            kit.setLeggings(player -> new KitItemBuilder(this, Material.CHAINMAIL_LEGGINGS));
            kit.setBoots(player -> new KitItemBuilder(this, Material.CHAINMAIL_BOOTS).addEnchantment(Enchantment.PROTECTION, 1));

            kit.addPotionBuilder(new PotionBuilder(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 2, true, false, true));

            return kit;
        }

        @Override
        public double getMaxHealth() {
            return 34D;
        }

        @Override
        public double getKnockbackResistance() {
            return 0D;
        }

        @Override
        public HealthRegen getHealthRegen() {
            return HealthRegen.NORMAL;
        }
    }

    protected class Level3 extends Level {

        @Override
        public int getPrice() {
            return 80000;
        }

        @Override
        protected Kit<KitPvPPlayer> registerKit() {
            Kit<KitPvPPlayer> kit = new Kit<>();

            kit.set(0, player -> new KitItemBuilder(this, Material.COD).addPassive(Passive.ATTACK_DAMAGE, 9).addActive(Active.FISH_ATTACK, 3));
            kit.set(1, player -> new KitItemBuilder(this, Material.SALMON, 1, "\u00a7b\u00a7lKnockback Fish").addPassive(Passive.ATTACK_DAMAGE, 2).addEnchantment(Enchantment.KNOCKBACK, 3));
            kit.set(2, player -> new KitItemBuilder(this, Material.COOKED_COD).addPassive(Passive.ATTACK_DAMAGE, 2).addEnchantment(Enchantment.FIRE_ASPECT, 2));

            kit.setHelmet(player -> new KitItemBuilder(this, Material.CHAINMAIL_HELMET).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setChestplate(player -> new KitItemBuilder(this, Material.CHAINMAIL_CHESTPLATE).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setLeggings(player -> new KitItemBuilder(this, Material.CHAINMAIL_LEGGINGS).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setBoots(player -> new KitItemBuilder(this, Material.CHAINMAIL_BOOTS).addEnchantment(Enchantment.PROTECTION, 1));

            kit.addPotionBuilder(new PotionBuilder(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 2, true, false, true));

            return kit;
        }

        @Override
        public double getMaxHealth() {
            return 34D;
        }

        @Override
        public double getKnockbackResistance() {
            return 0D;
        }

        @Override
        public HealthRegen getHealthRegen() {
            return HealthRegen.NORMAL;
        }
    }
}
