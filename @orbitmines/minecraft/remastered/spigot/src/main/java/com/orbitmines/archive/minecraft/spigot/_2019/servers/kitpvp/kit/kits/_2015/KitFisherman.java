package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.kits._2015;

/*
 * OrbitMines - @author Fadi Shawki - 2015
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.item_builders.KitItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.item_builders.KitLeatherArmorBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.HealthRegen;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.KitClass;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.KitPvPKit;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.LeatherArmorBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.kits.Kit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public class KitFisherman extends KitPvPKit {

    public static final long ID = 15L;

    private static final org.bukkit.Color LEATHER_COLOR = org.bukkit.Color.fromRGB(120, 130, 10);

    public KitFisherman(KitPvP kitPvP) {
        super(
                kitPvP,

                ID, "Fisherman",

                Color.TEAL,
                new ItemBuilder(Material.FISHING_ROD),

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

            kit.set(0, player -> new KitItemBuilder(this, Material.FISHING_ROD).addPassive(Passive.ATTACK_DAMAGE, 6).addPassive(Passive.HOOKED, 1).addEnchantment(Enchantment.KNOCKBACK, 2));

            kit.setHelmet(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.HELMET, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 2));
            kit.setChestplate(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.CHESTPLATE, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setLeggings(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.LEGGINGS, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setBoots(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.BOOTS, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 2));

            return kit;
        }

        @Override
        public double getMaxHealth() {
            return 36D;
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

            kit.set(0, player -> new KitItemBuilder(this, Material.FISHING_ROD).addPassive(Passive.ATTACK_DAMAGE, 7).addPassive(Passive.HOOKED, 1).addEnchantment(Enchantment.KNOCKBACK, 3));
            kit.set(1, player -> new KitItemBuilder(this, Material.COD).addPassive(Passive.ATTACK_DAMAGE, 1).addEnchantment(Enchantment.KNOCKBACK, 4).addEnchantment(Enchantment.FIRE_ASPECT, 2));

            kit.setHelmet(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.HELMET, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 2));
            kit.setChestplate(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.CHESTPLATE, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 2));
            kit.setLeggings(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.LEGGINGS, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 2));
            kit.setBoots(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.BOOTS, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 2));

            return kit;
        }

        @Override
        public double getMaxHealth() {
            return 36D;
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

            kit.set(0, player -> new KitItemBuilder(this, Material.FISHING_ROD).addPassive(Passive.ATTACK_DAMAGE, 8).addPassive(Passive.HOOKED, 1).addEnchantment(Enchantment.KNOCKBACK, 3));
            kit.set(1, player -> new KitItemBuilder(this, Material.COD).addPassive(Passive.ATTACK_DAMAGE, 1).addEnchantment(Enchantment.KNOCKBACK, 5).addEnchantment(Enchantment.FIRE_ASPECT, 2));

            kit.setHelmet(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.HELMET, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 3));
            kit.setChestplate(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.CHESTPLATE, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 2));
            kit.setLeggings(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.LEGGINGS, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 2));
            kit.setBoots(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.BOOTS, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 2));

            return kit;
        }

        @Override
        public double getMaxHealth() {
            return 36D;
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
