package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.kits._2015;

/*
 * OrbitMines - @author Fadi Shawki - 2015
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Active;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.item_builders.KitItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.item_builders.KitPotionItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.HealthRegen;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.KitClass;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.KitPvPKit;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.PotionBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.PotionItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.kits.Kit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.potion.PotionEffectType;

public class KitWizard extends KitPvPKit {

    public static final long ID = 28L;

    public KitWizard(KitPvP kitPvP) {
        super(
                kitPvP,

                ID, "Wizard",

                Color.YELLOW,
                new ItemBuilder(Material.GOLDEN_HELMET).addFlag(ItemFlag.HIDE_ATTRIBUTES),

                KitClass.SPELLCASTER
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

            kit.set(0, player -> new KitItemBuilder(this, Material.STONE_SWORD).addActive(Active.HEAL, 1));
            kit.set(1, player -> new KitPotionItemBuilder(this, PotionItemBuilder.Type.SPLASH, new PotionBuilder(PotionEffectType.REGENERATION, 0)));
            kit.set(2, player -> new KitPotionItemBuilder(this, PotionItemBuilder.Type.SPLASH, new PotionBuilder(PotionEffectType.SPEED, 0)));
            kit.set(3, player -> new KitPotionItemBuilder(this, PotionItemBuilder.Type.SPLASH, new PotionBuilder(PotionEffectType.WEAKNESS, 0)));
            kit.set(4, player -> new KitPotionItemBuilder(this, PotionItemBuilder.Type.SPLASH, new PotionBuilder(PotionEffectType.INSTANT_HEALTH, 0), 2));

            kit.setHelmet(player -> new KitItemBuilder(this, Material.GOLDEN_HELMET));
            kit.setChestplate(player -> new KitItemBuilder(this, Material.GOLDEN_CHESTPLATE));
            kit.setLeggings(player -> new KitItemBuilder(this, Material.GOLDEN_LEGGINGS));
            kit.setBoots(player -> new KitItemBuilder(this, Material.GOLDEN_BOOTS));

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
            return 30000;
        }

        @Override
        protected Kit<KitPvPPlayer> registerKit() {
            Kit<KitPvPPlayer> kit = new Kit<>();

            kit.set(0, player -> new KitItemBuilder(this, Material.STONE_SWORD).addActive(Active.HEAL, 2));
            kit.set(1, player -> new KitPotionItemBuilder(this, PotionItemBuilder.Type.SPLASH, new PotionBuilder(PotionEffectType.REGENERATION, 0)));
            kit.set(2, player -> new KitPotionItemBuilder(this, PotionItemBuilder.Type.SPLASH, new PotionBuilder(PotionEffectType.SPEED, 0)));
            kit.set(3, player -> new KitPotionItemBuilder(this, PotionItemBuilder.Type.SPLASH, new PotionBuilder(PotionEffectType.WEAKNESS, 0)));
            kit.set(4, player -> new KitPotionItemBuilder(this, PotionItemBuilder.Type.SPLASH, new PotionBuilder(PotionEffectType.INSTANT_HEALTH, 0), 2));

            kit.setHelmet(player -> new KitItemBuilder(this, Material.GOLDEN_HELMET).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setChestplate(player -> new KitItemBuilder(this, Material.GOLDEN_CHESTPLATE));
            kit.setLeggings(player -> new KitItemBuilder(this, Material.GOLDEN_LEGGINGS));
            kit.setBoots(player -> new KitItemBuilder(this, Material.GOLDEN_BOOTS).addEnchantment(Enchantment.PROTECTION, 1));

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
            return 70000;
        }

        @Override
        protected Kit<KitPvPPlayer> registerKit() {
            Kit<KitPvPPlayer> kit = new Kit<>();

            kit.set(0, player -> new KitItemBuilder(this, Material.STONE_SWORD).addActive(Active.HEAL, 3));
            kit.set(1, player -> new KitPotionItemBuilder(this, PotionItemBuilder.Type.SPLASH, new PotionBuilder(PotionEffectType.REGENERATION, 1)));
            kit.set(2, player -> new KitPotionItemBuilder(this, PotionItemBuilder.Type.SPLASH, new PotionBuilder(PotionEffectType.SPEED, 0)));
            kit.set(3, player -> new KitPotionItemBuilder(this, PotionItemBuilder.Type.SPLASH, new PotionBuilder(PotionEffectType.WEAKNESS, 0)));
            kit.set(4, player -> new KitPotionItemBuilder(this, PotionItemBuilder.Type.SPLASH, new PotionBuilder(PotionEffectType.INSTANT_HEALTH, 0), 3));

            kit.setHelmet(player -> new KitItemBuilder(this, Material.GOLDEN_HELMET).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setChestplate(player -> new KitItemBuilder(this, Material.GOLDEN_CHESTPLATE).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setLeggings(player -> new KitItemBuilder(this, Material.GOLDEN_LEGGINGS).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setBoots(player -> new KitItemBuilder(this, Material.GOLDEN_BOOTS).addEnchantment(Enchantment.PROTECTION, 1));

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
