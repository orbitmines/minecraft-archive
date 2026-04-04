package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.kits._2015;

/*
 * OrbitMines - @author Fadi Shawki - 2015
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.item_builders.KitItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.HealthRegen;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.KitClass;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.KitPvPKit;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.kits.Kit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public class KitVillager extends KitPvPKit {

    public static final long ID = 27L;

    public KitVillager(KitPvP kitPvP) {
        super(
                kitPvP,

                ID, "Villager",

                Color.GREEN,
                new ItemBuilder(Material.EMERALD),

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

            kit.set(0, player -> new KitItemBuilder(this, Material.EMERALD).addPassive(Passive.ATTACK_DAMAGE, 9));

            kit.setHelmet(player -> new KitItemBuilder(this, Material.CHAINMAIL_HELMET));
            kit.setChestplate(player -> new KitItemBuilder(this, Material.CHAINMAIL_CHESTPLATE));
            kit.setLeggings(player -> new KitItemBuilder(this, Material.CHAINMAIL_LEGGINGS));
            kit.setBoots(player -> new KitItemBuilder(this, Material.CHAINMAIL_BOOTS));

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

            kit.set(0, player -> new KitItemBuilder(this, Material.EMERALD).addPassive(Passive.ATTACK_DAMAGE, 9).addEnchantment(Enchantment.FIRE_ASPECT, 1));

            kit.setHelmet(player -> new KitItemBuilder(this, Material.CHAINMAIL_HELMET).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setChestplate(player -> new KitItemBuilder(this, Material.CHAINMAIL_CHESTPLATE));
            kit.setLeggings(player -> new KitItemBuilder(this, Material.CHAINMAIL_LEGGINGS).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setBoots(player -> new KitItemBuilder(this, Material.CHAINMAIL_BOOTS));

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

            kit.set(0, player -> new KitItemBuilder(this, Material.EMERALD).addPassive(Passive.ATTACK_DAMAGE, 9).addEnchantment(Enchantment.FIRE_ASPECT, 1));
            kit.set(1, player -> new KitItemBuilder(this, Material.BREAD, 3, "\u00a7a\u00a7lVillager Trade").addPassive(Passive.TRADE, 1));

            kit.setHelmet(player -> new KitItemBuilder(this, Material.CHAINMAIL_HELMET).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setChestplate(player -> new KitItemBuilder(this, Material.CHAINMAIL_CHESTPLATE).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setLeggings(player -> new KitItemBuilder(this, Material.CHAINMAIL_LEGGINGS).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setBoots(player -> new KitItemBuilder(this, Material.CHAINMAIL_BOOTS).addEnchantment(Enchantment.PROTECTION, 1));

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
