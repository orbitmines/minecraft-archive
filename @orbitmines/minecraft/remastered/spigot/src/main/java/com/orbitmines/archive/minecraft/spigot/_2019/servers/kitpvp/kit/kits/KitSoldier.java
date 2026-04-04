package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.kits;

/*
 * OrbitMines - @author Fadi Shawki - 2018
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
import org.bukkit.inventory.ItemFlag;

public class KitSoldier extends KitPvPKit {

    public static final long ID = 2L;
    public static final String JARNBJORN = "§b§lJarnbjorn";

    public KitSoldier(KitPvP kitPvP) {
        super(
                kitPvP,

                ID, "Soldier",

                Color.GREEN,
                new ItemBuilder(Material.IRON_LEGGINGS).addFlag(ItemFlag.HIDE_ATTRIBUTES),

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
            return 0;
        }

        @Override
        protected Kit<KitPvPPlayer> registerKit() {
            Kit<KitPvPPlayer> kit = new Kit<>();

            kit.set(0, player -> new KitItemBuilder(this, Material.STONE_SWORD));
            kit.set(1, player -> new KitItemBuilder(this, Material.BOW));
            kit.set(2, player -> new KitItemBuilder(this, Material.ARROW, 10));

            kit.setHelmet(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.HELMET));
            kit.setChestplate(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.CHESTPLATE));
            kit.setLeggings(player -> new KitItemBuilder(this, Material.IRON_LEGGINGS).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setBoots(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.BOOTS));

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
            return HealthRegen.NORMAL;
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

            kit.set(0, player -> new KitItemBuilder(this, Material.IRON_AXE, 1, JARNBJORN).addPassive(Passive.ATTACK_DAMAGE, 5).addPassive(Passive.WRECKER_OF_WORLDS, 1));
            kit.set(1, player -> new KitItemBuilder(this, Material.BOW));
            kit.set(2, player -> new KitItemBuilder(this, Material.ARROW, 10));

            kit.setHelmet(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.HELMET).addEnchantment(Enchantment.PROJECTILE_PROTECTION, 1));
            kit.setChestplate(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.CHESTPLATE));
            kit.setLeggings(player -> new KitItemBuilder(this, Material.IRON_LEGGINGS).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setBoots(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.BOOTS));

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
            return HealthRegen.NORMAL;
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

            kit.set(0, player -> new KitItemBuilder(this, Material.IRON_AXE, 1, JARNBJORN).addPassive(Passive.ATTACK_DAMAGE, 5).addPassive(Passive.WRECKER_OF_WORLDS, 2));
            kit.set(1, player -> new KitItemBuilder(this, Material.BOW));
            kit.set(2, player -> new KitItemBuilder(this, Material.ARROW, 10));

            kit.setHelmet(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.HELMET).addEnchantment(Enchantment.PROJECTILE_PROTECTION, 1));
            kit.setChestplate(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.CHESTPLATE));
            kit.setLeggings(player -> new KitItemBuilder(this, Material.IRON_LEGGINGS).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setBoots(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.BOOTS));

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
            return HealthRegen.NORMAL;
        }
    }
}
