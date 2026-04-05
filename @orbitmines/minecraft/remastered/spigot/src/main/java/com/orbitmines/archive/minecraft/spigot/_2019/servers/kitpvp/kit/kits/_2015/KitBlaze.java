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
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.item_builders.KitLeatherArmorBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.HealthRegen;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.KitClass;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.KitPvPKit;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.PotionBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.LeatherArmorBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.kits.Kit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.potion.PotionEffectType;

public class KitBlaze extends KitPvPKit {

    public static final long ID = 12L;

    private static final org.bukkit.Color LEATHER_COLOR = org.bukkit.Color.fromRGB(180, 130, 10);

    public KitBlaze(KitPvP kitPvP) {
        super(
                kitPvP,

                ID, "Blaze",

                Color.ORANGE,
                new ItemBuilder(Material.BLAZE_POWDER),

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

            kit.set(0, player -> new KitItemBuilder(this, Material.BLAZE_POWDER).addPassive(Passive.ATTACK_DAMAGE, 4).addEnchantment(Enchantment.FIRE_ASPECT, 2));
            kit.set(1, player -> new KitItemBuilder(this, Material.BLAZE_ROD, 1, "§6§lFire Spell").addActive(Active.FIRE_SPELL, 1));
            kit.set(2, player -> new KitItemBuilder(this, Material.FIRE_CHARGE, 1, "§6§lBlaze Inferno").addActive(Active.BLAZE_INFERNO, 1));

            kit.setHelmet(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.HELMET, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 1).addEnchantment(Enchantment.FIRE_PROTECTION, 3));
            kit.setChestplate(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.CHESTPLATE, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 1).addEnchantment(Enchantment.FIRE_PROTECTION, 3));
            kit.setLeggings(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.LEGGINGS, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 1).addEnchantment(Enchantment.FIRE_PROTECTION, 3));
            kit.setBoots(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.BOOTS, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 1).addEnchantment(Enchantment.FIRE_PROTECTION, 3));

            kit.addPotionBuilder(new PotionBuilder(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0, true, false, true));

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

            kit.set(0, player -> new KitItemBuilder(this, Material.BLAZE_POWDER).addPassive(Passive.ATTACK_DAMAGE, 5).addEnchantment(Enchantment.FIRE_ASPECT, 3));
            kit.set(1, player -> new KitItemBuilder(this, Material.BLAZE_ROD, 1, "§6§lFire Spell").addActive(Active.FIRE_SPELL, 2));
            kit.set(2, player -> new KitItemBuilder(this, Material.FIRE_CHARGE, 1, "§6§lBlaze Inferno").addActive(Active.BLAZE_INFERNO, 1));

            kit.setHelmet(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.HELMET, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 1).addEnchantment(Enchantment.FIRE_PROTECTION, 3));
            kit.setChestplate(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.CHESTPLATE, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 2).addEnchantment(Enchantment.FIRE_PROTECTION, 3));
            kit.setLeggings(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.LEGGINGS, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 1).addEnchantment(Enchantment.FIRE_PROTECTION, 3));
            kit.setBoots(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.BOOTS, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 1).addEnchantment(Enchantment.FIRE_PROTECTION, 3));

            kit.addPotionBuilder(new PotionBuilder(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0, true, false, true));

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

            kit.set(0, player -> new KitItemBuilder(this, Material.BLAZE_POWDER).addPassive(Passive.ATTACK_DAMAGE, 6).addEnchantment(Enchantment.FIRE_ASPECT, 4));
            kit.set(1, player -> new KitItemBuilder(this, Material.BLAZE_ROD, 1, "§6§lFire Spell").addActive(Active.FIRE_SPELL, 3));
            kit.set(2, player -> new KitItemBuilder(this, Material.FIRE_CHARGE, 1, "§6§lBlaze Inferno").addActive(Active.BLAZE_INFERNO, 1));

            kit.setHelmet(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.HELMET, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 2).addEnchantment(Enchantment.FIRE_PROTECTION, 3));
            kit.setChestplate(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.CHESTPLATE, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 2).addEnchantment(Enchantment.FIRE_PROTECTION, 3));
            kit.setLeggings(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.LEGGINGS, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 2).addEnchantment(Enchantment.FIRE_PROTECTION, 3));
            kit.setBoots(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.BOOTS, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 2).addEnchantment(Enchantment.FIRE_PROTECTION, 3));

            kit.addPotionBuilder(new PotionBuilder(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0, true, false, true));

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
