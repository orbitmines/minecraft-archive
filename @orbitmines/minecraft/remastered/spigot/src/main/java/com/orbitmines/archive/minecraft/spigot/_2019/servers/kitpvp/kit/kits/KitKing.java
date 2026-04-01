package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.kits;

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Active;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.item_builders.KitItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.HealthRegen;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.KitClass;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.KitPvPKit;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.kits.Kit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

public class KitKing extends KitPvPKit {

    public static final long ID = 5L;

    public KitKing(KitPvP kitPvP) {
        super(
                kitPvP,

                ID, "King",

                Color.YELLOW,
                new ItemBuilder(Material.DIAMOND_HELMET).addFlag(ItemFlag.HIDE_ATTRIBUTES),

                KitClass.MELEE);
    }

    @Override
    protected Level[] registerLevels() {
        return new Level[]{
                new Level1(),
                new Level2(),
                new Level3()
        };
    }

    private class Level1 extends Level {


        @Override
        public int getPrice() {
            return 10000;
        }

        @Override
        protected Kit<KitPvPPlayer> registerKit() {
            Kit<KitPvPPlayer> kit = new Kit<>();

            kit.setHelmet(player -> new KitItemBuilder(this, Material.DIAMOND_HELMET));
            kit.setChestplate(player -> new KitItemBuilder(this, Material.GOLDEN_CHESTPLATE));
            kit.setLeggings(player -> new KitItemBuilder(this, Material.GOLDEN_LEGGINGS));
            kit.setBoots(player -> new KitItemBuilder(this, Material.GOLDEN_BOOTS));

            kit.set(0, player -> new KitItemBuilder(this, Material.STONE_SWORD));

            return kit;
        }

        @Override
        public double getMaxHealth() {
            return 20D;
        }

        @Override
        public double getKnockbackResistance() {
            return 0D;
        }

        @Override
        public HealthRegen getHealthRegen() {
            return HealthRegen.MEDIUM;
        }
    }

    private class Level2 extends Level {

        @Override
        public int getPrice() {
            return 25000;
        }

        @Override
        protected Kit<KitPvPPlayer> registerKit() {
            Kit<KitPvPPlayer> kit = new Kit<>();

            kit.setHelmet(player -> new KitItemBuilder(this, Material.DIAMOND_HELMET));
            kit.setChestplate(player -> new KitItemBuilder(this, Material.GOLDEN_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1));
            kit.setLeggings(player -> new KitItemBuilder(this, Material.GOLDEN_LEGGINGS));
            kit.setBoots(player -> new KitItemBuilder(this, Material.GOLDEN_BOOTS));

            kit.set(0, player -> new KitItemBuilder(this, Material.STONE_SWORD).addActive(Active.HEAL, 1));

            return kit;
        }

        @Override
        public double getMaxHealth() {
            return 20D;
        }

        @Override
        public double getKnockbackResistance() {
            return 0D;
        }

        @Override
        public HealthRegen getHealthRegen() {
            return HealthRegen.MEDIUM;
        }
    }
    private class Level3 extends Level {

        @Override
        public int getPrice() {
            return 60000;
        }

        @Override
        protected Kit<KitPvPPlayer> registerKit() {
            Kit<KitPvPPlayer> kit = new Kit<>();

            kit.setHelmet(player -> new KitItemBuilder(this, Material.DIAMOND_HELMET));
            kit.setChestplate(player -> new KitItemBuilder(this, Material.GOLDEN_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2));
            kit.setLeggings(player -> new KitItemBuilder(this, Material.GOLDEN_LEGGINGS));
            kit.setBoots(player -> new KitItemBuilder(this, Material.GOLDEN_BOOTS));

            kit.set(0, player -> new KitItemBuilder(this, Material.STONE_SWORD).addActive(Active.HEAL, 2));

            return kit;
        }

        @Override
        public double getMaxHealth() {
            return 20D;
        }

        @Override
        public double getKnockbackResistance() {
            return 0D;
        }

        @Override
        public HealthRegen getHealthRegen() {
            return HealthRegen.MEDIUM;
        }
    }
}
