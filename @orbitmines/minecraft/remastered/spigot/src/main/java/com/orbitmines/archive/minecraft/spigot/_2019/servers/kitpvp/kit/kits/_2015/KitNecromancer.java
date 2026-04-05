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
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.LeatherArmorBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.kits.Kit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

public class KitNecromancer extends KitPvPKit {

    public static final long ID = 21L;

    private static final org.bukkit.Color LEATHER_COLOR = org.bukkit.Color.BLACK;

    public KitNecromancer(KitPvP kitPvP) {
        super(
                kitPvP,

                ID, "Necromancer",

                Color.PURPLE,
                new ItemBuilder(Material.GOLDEN_HOE).addFlag(ItemFlag.HIDE_ATTRIBUTES),

                KitClass.RANGED
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

            kit.set(0, player -> new KitItemBuilder(this, Material.GOLDEN_HOE).addPassive(Passive.ATTACK_DAMAGE, 8));
            kit.set(1, player -> new KitItemBuilder(this, Material.BOW).addPassive(Passive.UNDEATH, 1).addEnchantment(Enchantment.POWER, 1));
            kit.set(2, player -> new KitItemBuilder(this, Material.ARROW, 5));

            kit.setHelmet(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.HELMET, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setChestplate(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.CHESTPLATE, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setLeggings(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.LEGGINGS, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setBoots(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.BOOTS, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 1));

            return kit;
        }

        @Override
        public double getMaxHealth() {
            return 34D;
        }

        @Override
        public double getKnockbackResistance() {
            return 0.0D;
        }

        @Override
        public HealthRegen getHealthRegen() {
            return HealthRegen.NORMAL;
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

            kit.set(0, player -> new KitItemBuilder(this, Material.GOLDEN_HOE).addPassive(Passive.ATTACK_DAMAGE, 8).addPassive(Passive.SOUL_REAP, 1));
            kit.set(1, player -> new KitItemBuilder(this, Material.BOW).addPassive(Passive.ARROW_REGEN, 27).addPassive(Passive.UNDEATH, 1).addEnchantment(Enchantment.POWER, 1));
            kit.set(2, player -> new KitItemBuilder(this, Material.STICK, 1, "§8§lWither Staff").addActive(Active.WITHER_STAFF, 1));
            kit.set(3, player -> new KitItemBuilder(this, Material.ARROW, 10));

            kit.setHelmet(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.HELMET, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 2));
            kit.setChestplate(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.CHESTPLATE, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 2));
            kit.setLeggings(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.LEGGINGS, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 2));
            kit.setBoots(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.BOOTS, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 2));

            return kit;
        }

        @Override
        public double getMaxHealth() {
            return 34D;
        }

        @Override
        public double getKnockbackResistance() {
            return 0.0D;
        }

        @Override
        public HealthRegen getHealthRegen() {
            return HealthRegen.NORMAL;
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

            kit.set(0, player -> new KitItemBuilder(this, Material.GOLDEN_HOE).addPassive(Passive.ATTACK_DAMAGE, 8).addPassive(Passive.BLEED, 1).addPassive(Passive.SOUL_REAP, 1));
            kit.set(1, player -> new KitItemBuilder(this, Material.BOW).addPassive(Passive.ARROW_REGEN, 25).addPassive(Passive.UNDEATH, 2).addEnchantment(Enchantment.POWER, 1));
            kit.set(2, player -> new KitItemBuilder(this, Material.STICK, 1, "§8§lWither Staff").addActive(Active.WITHER_STAFF, 1));
            kit.set(3, player -> new KitItemBuilder(this, Material.ARROW, 10));

            kit.setHelmet(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.HELMET, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 2));
            kit.setChestplate(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.CHESTPLATE, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 2));
            kit.setLeggings(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.LEGGINGS, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 2));
            kit.setBoots(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.BOOTS, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 2));

            return kit;
        }

        @Override
        public double getMaxHealth() {
            return 34D;
        }

        @Override
        public double getKnockbackResistance() {
            return 0.0D;
        }

        @Override
        public HealthRegen getHealthRegen() {
            return HealthRegen.NORMAL;
        }
    }
}
