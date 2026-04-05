package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.kits._2015;

/*
 * OrbitMines - @author Fadi Shawki - 2015
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Active;
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

public class KitWizard extends KitPvPKit {

    public static final long ID = 28L;

    public KitWizard(KitPvP kitPvP) {
        super(
                kitPvP,

                ID, "Wizard",

                Color.WHITE,
                new ItemBuilder(Material.WIND_CHARGE).addFlag(ItemFlag.HIDE_ATTRIBUTES),

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

            kit.set(0, player -> new KitItemBuilder(this, Material.STONE_SWORD).addEnchantment(Enchantment.SHARPNESS, 1));
            kit.set(1, player -> new KitItemBuilder(this, Material.WIND_CHARGE).addActive(Active.TORNADO, 1).setDisplayName("§f§lCyclone"));
            kit.set(2, player -> new KitItemBuilder(this, Material.LIGHTNING_ROD).addActive(Active.LIGHTNING_STRIKE, 1).setDisplayName("§e§lArcane Bolt"));

            kit.setHelmet(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.HELMET, org.bukkit.Color.WHITE));
            kit.setChestplate(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.CHESTPLATE, org.bukkit.Color.BLACK));
            kit.setLeggings(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.LEGGINGS, org.bukkit.Color.WHITE));
            kit.setBoots(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.BOOTS, org.bukkit.Color.WHITE));

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

            kit.set(0, player -> new KitItemBuilder(this, Material.STONE_SWORD).addEnchantment(Enchantment.SHARPNESS, 2));
            kit.set(1, player -> new KitItemBuilder(this, Material.WIND_CHARGE).addActive(Active.TORNADO, 2).setDisplayName("§f§lCyclone"));
            kit.set(2, player -> new KitItemBuilder(this, Material.LIGHTNING_ROD).addActive(Active.LIGHTNING_STRIKE, 2).setDisplayName("§e§lArcane Bolt"));

            kit.setHelmet(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.HELMET, org.bukkit.Color.WHITE).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setChestplate(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.CHESTPLATE, org.bukkit.Color.BLACK));
            kit.setLeggings(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.LEGGINGS, org.bukkit.Color.WHITE));
            kit.setBoots(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.BOOTS, org.bukkit.Color.WHITE).addEnchantment(Enchantment.PROTECTION, 1));

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

            kit.set(0, player -> new KitItemBuilder(this, Material.STONE_SWORD).addEnchantment(Enchantment.SHARPNESS, 2));
            kit.set(1, player -> new KitItemBuilder(this, Material.WIND_CHARGE).addActive(Active.TORNADO, 3).setDisplayName("§f§lCyclone"));
            kit.set(2, player -> new KitItemBuilder(this, Material.LIGHTNING_ROD).addActive(Active.LIGHTNING_STRIKE, 3).setDisplayName("§e§lArcane Bolt"));
            kit.set(3, player -> new KitItemBuilder(this, Material.GLASS_BOTTLE).addActive(Active.INVISIBILITY, 1).setDisplayName("§7§lVanishing Act"));

            kit.setHelmet(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.HELMET, org.bukkit.Color.WHITE).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setChestplate(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.CHESTPLATE, org.bukkit.Color.BLACK).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setLeggings(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.LEGGINGS, org.bukkit.Color.WHITE).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setBoots(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.BOOTS, org.bukkit.Color.WHITE).addEnchantment(Enchantment.PROTECTION, 1));

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
