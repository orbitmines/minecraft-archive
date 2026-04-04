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
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.kits.Kit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public class KitTNT extends KitPvPKit {

    public static final long ID = 24L;

    public KitTNT(KitPvP kitPvP) {
        super(
                kitPvP,

                ID, "TNT",

                Color.RED,
                new ItemBuilder(Material.TNT),

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

            kit.set(0, player -> new KitItemBuilder(this, Material.TNT).addPassive(Passive.ATTACK_DAMAGE, 5).addEnchantment(Enchantment.KNOCKBACK, 3));
            kit.set(1, player -> new KitItemBuilder(this, Material.REDSTONE_TORCH, 1, "§c§lTNT Launcher").addActive(Active.TNT_LAUNCHER, 1));

            kit.setChestplate(player -> new KitItemBuilder(this, Material.GOLDEN_CHESTPLATE).addEnchantment(Enchantment.BLAST_PROTECTION, 1));
            kit.setLeggings(player -> new KitItemBuilder(this, Material.GOLDEN_LEGGINGS).addEnchantment(Enchantment.BLAST_PROTECTION, 1));
            kit.setBoots(player -> new KitItemBuilder(this, Material.GOLDEN_BOOTS).addEnchantment(Enchantment.BLAST_PROTECTION, 1));

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
            return 30000;
        }

        @Override
        protected Kit<KitPvPPlayer> registerKit() {
            Kit<KitPvPPlayer> kit = new Kit<>();

            kit.set(0, player -> new KitItemBuilder(this, Material.TNT).addPassive(Passive.ATTACK_DAMAGE, 5).addEnchantment(Enchantment.KNOCKBACK, 4));
            kit.set(1, player -> new KitItemBuilder(this, Material.BOW).addPassive(Passive.ARROW_REGEN, 25).addPassive(Passive.EXPLODE, 1));
            kit.set(2, player -> new KitItemBuilder(this, Material.REDSTONE_TORCH, 1, "§c§lTNT Launcher").addActive(Active.TNT_LAUNCHER, 2));
            kit.set(3, player -> new KitItemBuilder(this, Material.ARROW, 2));

            kit.setChestplate(player -> new KitItemBuilder(this, Material.GOLDEN_CHESTPLATE).addEnchantment(Enchantment.BLAST_PROTECTION, 2).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setLeggings(player -> new KitItemBuilder(this, Material.GOLDEN_LEGGINGS).addEnchantment(Enchantment.BLAST_PROTECTION, 2));
            kit.setBoots(player -> new KitItemBuilder(this, Material.GOLDEN_BOOTS).addEnchantment(Enchantment.BLAST_PROTECTION, 2));

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
            return 70000;
        }

        @Override
        protected Kit<KitPvPPlayer> registerKit() {
            Kit<KitPvPPlayer> kit = new Kit<>();

            kit.set(0, player -> new KitItemBuilder(this, Material.TNT).addPassive(Passive.ATTACK_DAMAGE, 5).addEnchantment(Enchantment.KNOCKBACK, 4));
            kit.set(1, player -> new KitItemBuilder(this, Material.BOW).addPassive(Passive.ARROW_REGEN, 20).addPassive(Passive.EXPLODE, 1));
            kit.set(2, player -> new KitItemBuilder(this, Material.REDSTONE_TORCH, 1, "§c§lTNT Launcher").addActive(Active.TNT_LAUNCHER, 3));
            kit.set(3, player -> new KitItemBuilder(this, Material.ARROW, 4));

            kit.setChestplate(player -> new KitItemBuilder(this, Material.GOLDEN_CHESTPLATE).addEnchantment(Enchantment.BLAST_PROTECTION, 3).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setLeggings(player -> new KitItemBuilder(this, Material.GOLDEN_LEGGINGS).addEnchantment(Enchantment.BLAST_PROTECTION, 3).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setBoots(player -> new KitItemBuilder(this, Material.GOLDEN_BOOTS).addEnchantment(Enchantment.BLAST_PROTECTION, 3).addEnchantment(Enchantment.PROTECTION, 1));

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
