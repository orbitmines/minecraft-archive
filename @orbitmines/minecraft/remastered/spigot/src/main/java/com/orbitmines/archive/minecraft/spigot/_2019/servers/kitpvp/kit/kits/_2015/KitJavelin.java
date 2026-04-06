package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.kits._2015;

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
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.actives.ActiveSpearToss;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.LeatherArmorBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.kits.Kit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public class KitJavelin extends KitPvPKit {

    public static final long ID = 29L;

    private static final org.bukkit.Color LEATHER_COLOR = org.bukkit.Color.fromRGB(60, 40, 40);
    private static final org.bukkit.Color CHESTPLATE_COLOR = org.bukkit.Color.fromRGB(128, 0, 0);

    public KitJavelin(KitPvP kitPvP) {
        super(
                kitPvP,

                ID, "Javelin",

                Color.MAROON,
                new ItemBuilder(Material.NETHERITE_SPEAR),

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
            return 20000;
        }

        @Override
        protected Kit<KitPvPPlayer> registerKit() {
            Kit<KitPvPPlayer> kit = new Kit<>();

            kit.set(0, player -> new KitItemBuilder(this, Material.NETHERITE_SPEAR, 1, "§4§lJavelin")
                    .addPassive(Passive.ATTACK_DAMAGE, 6)
                    .addActive(Active.SPEAR_TOSS, 1)
                    .addEnchantment(Enchantment.LOYALTY, 3));
            kit.set(1, player -> new KitItemBuilder(this, Material.NETHERITE_SPEAR, 1, "§4§lJavelin")
                    .addPassive(Passive.ATTACK_DAMAGE, 6)
                    .addActive(Active.SPEAR_TOSS, 1)
                    .addEnchantment(Enchantment.LOYALTY, 3));
            kit.set(2, player -> new KitItemBuilder(this, Material.BLACK_BUNDLE, 1, ActiveSpearToss.BUNDLE_NAME + " §7(0)")
                    .addPassive(Passive.SOUL_COLLECTOR, 1));

            kit.setHelmet(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.HELMET, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setChestplate(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.CHESTPLATE, CHESTPLATE_COLOR).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setLeggings(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.LEGGINGS, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setBoots(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.BOOTS, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 1));

            return kit;
        }

        @Override
        public double getMaxHealth() {
            return 40D;
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
            return 45000;
        }

        @Override
        protected Kit<KitPvPPlayer> registerKit() {
            Kit<KitPvPPlayer> kit = new Kit<>();

            kit.set(0, player -> new KitItemBuilder(this, Material.NETHERITE_SPEAR, 1, "§4§lJavelin")
                    .addPassive(Passive.ATTACK_DAMAGE, 6)
                    .addActive(Active.SPEAR_TOSS, 1)
                    .addEnchantment(Enchantment.SHARPNESS, 1)
                    .addEnchantment(Enchantment.LOYALTY, 3));
            kit.set(1, player -> new KitItemBuilder(this, Material.NETHERITE_SPEAR, 1, "§4§lJavelin")
                    .addPassive(Passive.ATTACK_DAMAGE, 6)
                    .addActive(Active.SPEAR_TOSS, 1)
                    .addEnchantment(Enchantment.SHARPNESS, 1)
                    .addEnchantment(Enchantment.LOYALTY, 3));
            kit.set(2, player -> new KitItemBuilder(this, Material.BLACK_BUNDLE, 1, ActiveSpearToss.BUNDLE_NAME + " §7(0)")
                    .addPassive(Passive.SOUL_COLLECTOR, 1));

            kit.setHelmet(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.HELMET, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 2));
            kit.setChestplate(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.CHESTPLATE, CHESTPLATE_COLOR).addEnchantment(Enchantment.PROTECTION, 2));
            kit.setLeggings(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.LEGGINGS, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setBoots(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.BOOTS, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 1));

            return kit;
        }

        @Override
        public double getMaxHealth() {
            return 40D;
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
            return 90000;
        }

        @Override
        protected Kit<KitPvPPlayer> registerKit() {
            Kit<KitPvPPlayer> kit = new Kit<>();

            kit.set(0, player -> new KitItemBuilder(this, Material.NETHERITE_SPEAR, 1, "§4§lJavelin")
                    .addPassive(Passive.ATTACK_DAMAGE, 7)
                    .addActive(Active.SPEAR_TOSS, 1)
                    .addEnchantment(Enchantment.SHARPNESS, 2)
                    .addEnchantment(Enchantment.LOYALTY, 3));
            kit.set(1, player -> new KitItemBuilder(this, Material.NETHERITE_SPEAR, 1, "§4§lJavelin")
                    .addPassive(Passive.ATTACK_DAMAGE, 7)
                    .addActive(Active.SPEAR_TOSS, 1)
                    .addEnchantment(Enchantment.SHARPNESS, 2)
                    .addEnchantment(Enchantment.LOYALTY, 3));
            kit.set(2, player -> new KitItemBuilder(this, Material.BLACK_BUNDLE, 1, ActiveSpearToss.BUNDLE_NAME + " §7(0)")
                    .addPassive(Passive.SOUL_COLLECTOR, 1));

            kit.setHelmet(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.HELMET, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 2));
            kit.setChestplate(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.CHESTPLATE, CHESTPLATE_COLOR).addEnchantment(Enchantment.PROTECTION, 2));
            kit.setLeggings(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.LEGGINGS, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 2));
            kit.setBoots(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.BOOTS, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 2));

            return kit;
        }

        @Override
        public double getMaxHealth() {
            return 40D;
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
