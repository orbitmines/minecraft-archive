package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.kits;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Active;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.item_builders.KitItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.item_builders.KitLeatherArmorBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.item_builders.KitPotionItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.HealthRegen;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.KitClass;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.KitPvPKit;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.PotionBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.LeatherArmorBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.PotionItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.kits.Kit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.potion.PotionEffectType;

public class KitBunny extends KitPvPKit {

    public static final long ID = 8L;

    private static final org.bukkit.Color LEATHER_COLOR = org.bukkit.Color.WHITE;

    public KitBunny(KitPvP kitPvP) {
        super(
                kitPvP,

                ID, "Bunny",

                Color.YELLOW,
                new ItemBuilder(Material.LEATHER_BOOTS).addFlag(ItemFlag.HIDE_ATTRIBUTES),

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

            kit.set(0, player -> new KitItemBuilder(this, Material.STONE_SWORD));
            kit.set(1, player -> new KitPotionItemBuilder(this, PotionItemBuilder.Type.SPLASH, new PotionBuilder(PotionEffectType.POISON, 45 * 20, 0)));

            kit.setHelmet(player -> new KitItemBuilder(this, Material.IRON_HELMET));
            kit.setChestplate(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.CHESTPLATE, LEATHER_COLOR));
            kit.setLeggings(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.LEGGINGS, LEATHER_COLOR));
            kit.setBoots(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.BOOTS, LEATHER_COLOR));

            kit.addPotionBuilder(new PotionBuilder(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, true, false, true));
            kit.addPotionBuilder(new PotionBuilder(PotionEffectType.JUMP_BOOST, Integer.MAX_VALUE, 3, true, false, true));

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

            kit.set(0, player -> new KitItemBuilder(this, Material.STONE_SWORD));
            kit.set(1, player -> new KitPotionItemBuilder(this, PotionItemBuilder.Type.SPLASH, new PotionBuilder(PotionEffectType.POISON, 45 * 20, 0)));
            kit.set(2, player -> new KitItemBuilder(this, Material.SUGAR).addActive(Active.SUGAR_RUSH, 1));

            kit.setHelmet(player -> new KitItemBuilder(this, Material.IRON_HELMET).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setChestplate(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.CHESTPLATE, LEATHER_COLOR));
            kit.setLeggings(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.LEGGINGS, LEATHER_COLOR));
            kit.setBoots(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.BOOTS, LEATHER_COLOR));

            kit.addPotionBuilder(new PotionBuilder(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, true, false, true));
            kit.addPotionBuilder(new PotionBuilder(PotionEffectType.JUMP_BOOST, Integer.MAX_VALUE, 3, true, false, true));

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

            kit.set(0, player -> new KitItemBuilder(this, Material.STONE_SWORD));
            kit.set(1, player -> new KitPotionItemBuilder(this, PotionItemBuilder.Type.SPLASH, new PotionBuilder(PotionEffectType.POISON, 21 * 20, 1)));
            kit.set(2, player -> new KitItemBuilder(this, Material.SUGAR).addActive(Active.SUGAR_RUSH, 1));
            kit.set(3, player -> new KitItemBuilder(this, Material.CARROT, 1, "§6§lKnockback Carrot").addEnchantment(Enchantment.KNOCKBACK, 3).addEnchantment(Enchantment.SHARPNESS, 2));

            kit.setHelmet(player -> new KitItemBuilder(this, Material.IRON_HELMET).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setChestplate(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.CHESTPLATE, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setLeggings(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.LEGGINGS, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setBoots(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.BOOTS, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 1));

            kit.addPotionBuilder(new PotionBuilder(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, true, false, true));
            kit.addPotionBuilder(new PotionBuilder(PotionEffectType.JUMP_BOOST, Integer.MAX_VALUE, 3, true, false, true));

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
