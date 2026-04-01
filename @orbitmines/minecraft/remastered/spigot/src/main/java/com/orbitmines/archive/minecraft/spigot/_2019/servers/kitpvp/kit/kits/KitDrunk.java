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
import org.bukkit.potion.PotionEffectType;

public class KitDrunk extends KitPvPKit {

    public static final long ID = 6L;

    public KitDrunk(KitPvP kitPvP) {
        super(
                kitPvP,

                ID, "Drunk",

                Color.YELLOW,
                new ItemBuilder(Material.GLASS_BOTTLE),

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

            kit.set(0, player -> new KitItemBuilder(this, Material.GLASS_BOTTLE).addPassive(Passive.BLEED, 1).addPassive(Passive.ATTACK_DAMAGE, 5));
            kit.set(1, player -> new KitItemBuilder(this, Material.BOW).addPassive(Passive.ARROW_REGEN, 15));
            kit.set(2, player -> new KitItemBuilder(this, Material.ARROW, 10));

            kit.setHelmet(player -> new KitItemBuilder(this, Material.CHAINMAIL_HELMET).addPassive(Passive.LAST_BREATH, 1));
            kit.setChestplate(player -> new KitItemBuilder(this, Material.CHAINMAIL_CHESTPLATE));
            kit.setLeggings(player -> new KitItemBuilder(this, Material.CHAINMAIL_LEGGINGS));
            kit.setBoots(player -> new KitItemBuilder(this, Material.CHAINMAIL_BOOTS));

            kit.addPotionBuilder(new PotionBuilder(PotionEffectType.CONFUSION, Integer.MAX_VALUE, 1, true, false, true));

            return kit;
        }

        @Override
        public double getMaxHealth() {
            return 21D;
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

            kit.set(0, player -> new KitItemBuilder(this, Material.GLASS_BOTTLE).addPassive(Passive.BLEED, 2).addPassive(Passive.ATTACK_DAMAGE, 6));
            kit.set(1, player -> new KitItemBuilder(this, Material.BOW).addPassive(Passive.ARROW_REGEN, 13));
            kit.set(2, player -> new KitItemBuilder(this, Material.ARROW, 11));

            kit.setHelmet(player -> new KitItemBuilder(this, Material.CHAINMAIL_HELMET).addPassive(Passive.LAST_BREATH, 1));
            kit.setChestplate(player -> new KitItemBuilder(this, Material.CHAINMAIL_CHESTPLATE));
            kit.setLeggings(player -> new KitItemBuilder(this, Material.CHAINMAIL_LEGGINGS));
            kit.setBoots(player -> new KitItemBuilder(this, Material.CHAINMAIL_BOOTS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1));

            kit.addPotionBuilder(new PotionBuilder(PotionEffectType.CONFUSION, Integer.MAX_VALUE, 1, true, false, true));

            return kit;
        }

        @Override
        public double getMaxHealth() {
            return 21D;
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

            kit.set(0, player -> new KitItemBuilder(this, Material.GLASS_BOTTLE).addPassive(Passive.BLEED, 3).addPassive(Passive.ATTACK_DAMAGE, 6));
            kit.set(1, player -> new KitItemBuilder(this, Material.BOW).addPassive(Passive.ARROW_REGEN, 10));
            kit.set(2, player -> new KitItemBuilder(this, Material.ARROW, 12));

            kit.setHelmet(player -> new KitItemBuilder(this, Material.DIAMOND_HELMET).addPassive(Passive.LAST_BREATH, 1));
            kit.setChestplate(player -> new KitItemBuilder(this, Material.CHAINMAIL_CHESTPLATE));
            kit.setLeggings(player -> new KitItemBuilder(this, Material.CHAINMAIL_LEGGINGS));
            kit.setBoots(player -> new KitItemBuilder(this, Material.DIAMOND_BOOTS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1));

            kit.addPotionBuilder(new PotionBuilder(PotionEffectType.CONFUSION, Integer.MAX_VALUE, 1, true, false, true));

            return kit;
        }

        @Override
        public double getMaxHealth() {
            return 21D;
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
