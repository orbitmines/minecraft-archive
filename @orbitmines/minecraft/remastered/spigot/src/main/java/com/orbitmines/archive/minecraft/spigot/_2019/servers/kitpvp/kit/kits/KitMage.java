package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.kits;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.item_builders.KitItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.item_builders.KitPotionItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.HealthRegen;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.KitClass;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.KitPvPKit;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.PotionBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.PotionItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.kits.Kit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.potion.PotionEffectType;

public class KitMage extends KitPvPKit {

    public static final long ID = 3L;

    public KitMage(KitPvP kitPvP) {
        super(
                kitPvP,

                ID, "Mage",

                Color.YELLOW,
                new PotionItemBuilder(PotionItemBuilder.Type.SPLASH, new PotionBuilder(PotionEffectType.INSTANT_DAMAGE, 0)).addFlag(ItemFlag.HIDE_ADDITIONAL_TOOLTIP),

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
            return 10000;
        }

        @Override
        protected Kit<KitPvPPlayer> registerKit() {
            Kit<KitPvPPlayer> kit = new Kit<>();

            kit.set(0, player -> new KitItemBuilder(this, Material.STONE_SWORD).addPassive(Passive.POTION_BREWER, 1));
            kit.set(1, player -> new KitPotionItemBuilder(this, PotionItemBuilder.Type.SPLASH, new PotionBuilder(PotionEffectType.FIRE_RESISTANCE, 30 * 20, 0)));
            kit.set(2, player -> new KitPotionItemBuilder(this, PotionItemBuilder.Type.SPLASH, new PotionBuilder(PotionEffectType.WEAKNESS, 15 * 20, 0)));
            kit.set(3, player -> new KitPotionItemBuilder(this, PotionItemBuilder.Type.SPLASH, new PotionBuilder(PotionEffectType.INSTANT_DAMAGE, 0)));
            kit.set(4, player -> new KitPotionItemBuilder(this, PotionItemBuilder.Type.SPLASH, new PotionBuilder(PotionEffectType.SPEED, 60 * 20, 0)));
            kit.set(5, player -> new KitPotionItemBuilder(this, PotionItemBuilder.Type.SPLASH, new PotionBuilder(PotionEffectType.REGENERATION, 20 * 20, 0)));

            kit.setHelmet(player -> new KitItemBuilder(this, Material.GOLDEN_HELMET));
            kit.setChestplate(player -> new KitItemBuilder(this, Material.GOLDEN_CHESTPLATE));
            kit.setLeggings(player -> new KitItemBuilder(this, Material.GOLDEN_LEGGINGS));
            kit.setBoots(player -> new KitItemBuilder(this, Material.GOLDEN_BOOTS));

            return kit;
        }

        @Override
        public double getMaxHealth() {
            return 22D;
        }

        @Override
        public double getKnockbackResistance() {
            return 0.0D;
        }

        @Override
        public HealthRegen getHealthRegen() {
            return HealthRegen.HIGH;
        }
    }

    protected class Level2 extends Level {

        @Override
        public int getPrice() {
            return 25000;
        }

        @Override
        protected Kit<KitPvPPlayer> registerKit() {
            Kit<KitPvPPlayer> kit = new Kit<>();

            kit.set(0, player -> new KitItemBuilder(this, Material.STONE_SWORD).addPassive(Passive.POTION_BREWER, 1));
            kit.set(1, player -> new KitPotionItemBuilder(this, PotionItemBuilder.Type.SPLASH, new PotionBuilder(PotionEffectType.FIRE_RESISTANCE, 35 * 20, 0)));
            kit.set(2, player -> new KitPotionItemBuilder(this, PotionItemBuilder.Type.SPLASH, new PotionBuilder(PotionEffectType.WEAKNESS, 17 * 20, 0)));
            kit.set(3, player -> new KitPotionItemBuilder(this, PotionItemBuilder.Type.SPLASH, new PotionBuilder(PotionEffectType.INSTANT_DAMAGE, 1)));
            kit.set(4, player -> new KitPotionItemBuilder(this, PotionItemBuilder.Type.SPLASH, new PotionBuilder(PotionEffectType.SPEED, 60 * 20, 0)));
            kit.set(5, player -> new KitPotionItemBuilder(this, PotionItemBuilder.Type.SPLASH, new PotionBuilder(PotionEffectType.REGENERATION, 30 * 20, 0)));
            kit.set(6, player -> new KitPotionItemBuilder(this, PotionItemBuilder.Type.SPLASH, new PotionBuilder(PotionEffectType.SPEED, 10 * 20, 1)));

            kit.setHelmet(player -> new KitItemBuilder(this, Material.GOLDEN_HELMET));
            kit.setChestplate(player -> new KitItemBuilder(this, Material.GOLDEN_CHESTPLATE).addPassive(Passive.LIGHTNING_PROTECTION, 1));
            kit.setLeggings(player -> new KitItemBuilder(this, Material.GOLDEN_LEGGINGS));
            kit.setBoots(player -> new KitItemBuilder(this, Material.GOLDEN_BOOTS).addPassive(Passive.LIGHTNING_PROTECTION, 1));

            return kit;
        }

        @Override
        public double getMaxHealth() {
            return 22D;
        }

        @Override
        public double getKnockbackResistance() {
            return 0.0D;
        }

        @Override
        public HealthRegen getHealthRegen() {
            return HealthRegen.HIGH;
        }
    }

    protected class Level3 extends Level {

        @Override
        public int getPrice() {
            return 60000;
        }

        @Override
        protected Kit<KitPvPPlayer> registerKit() {
            Kit<KitPvPPlayer> kit = new Kit<>();

            kit.set(0, player -> new KitItemBuilder(this, Material.STONE_SWORD).addPassive(Passive.POTION_BREWER, 1));
            kit.set(1, player -> new KitPotionItemBuilder(this, PotionItemBuilder.Type.SPLASH, new PotionBuilder(PotionEffectType.FIRE_RESISTANCE, 40 * 20, 0)));
            kit.set(2, player -> new KitPotionItemBuilder(this, PotionItemBuilder.Type.SPLASH, new PotionBuilder(PotionEffectType.WEAKNESS, 17 * 20, 0), 2));
            kit.set(3, player -> new KitPotionItemBuilder(this, PotionItemBuilder.Type.SPLASH, new PotionBuilder(PotionEffectType.INSTANT_DAMAGE, 1), 2));
            kit.set(4, player -> new KitPotionItemBuilder(this, PotionItemBuilder.Type.SPLASH, new PotionBuilder(PotionEffectType.SPEED, 60 * 20, 0)));
            kit.set(5, player -> new KitPotionItemBuilder(this, PotionItemBuilder.Type.SPLASH, new PotionBuilder(PotionEffectType.REGENERATION, 32 * 20, 0)));
            kit.set(6, player -> new KitPotionItemBuilder(this, PotionItemBuilder.Type.SPLASH, new PotionBuilder(PotionEffectType.SPEED, 15 * 20, 1)));

            kit.setHelmet(player -> new KitItemBuilder(this, Material.GOLDEN_HELMET).addPassive(Passive.LIGHTNING_PROTECTION, 1));
            kit.setChestplate(player -> new KitItemBuilder(this, Material.GOLDEN_CHESTPLATE).addPassive(Passive.LIGHTNING_PROTECTION, 1));
            kit.setLeggings(player -> new KitItemBuilder(this, Material.GOLDEN_LEGGINGS).addPassive(Passive.LIGHTNING_PROTECTION, 1));
            kit.setBoots(player -> new KitItemBuilder(this, Material.GOLDEN_BOOTS).addPassive(Passive.LIGHTNING_PROTECTION, 1));

            return kit;
        }

        @Override
        public double getMaxHealth() {
            return 22D;
        }

        @Override
        public double getKnockbackResistance() {
            return 0.0D;
        }

        @Override
        public HealthRegen getHealthRegen() {
            return HealthRegen.HIGH;
        }
    }
}
