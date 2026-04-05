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
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.item_builders.KitLeatherArmorBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.HealthRegen;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.KitClass;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.KitPvPKit;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.PotionBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.LeatherArmorBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.kits.Kit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.potion.PotionEffectType;

public class KitSnowGolem extends KitPvPKit {

    public static final long ID = 22L;

    private static final org.bukkit.Color LEATHER_COLOR = org.bukkit.Color.fromRGB(200, 200, 200);

    public KitSnowGolem(KitPvP kitPvP) {
        super(
                kitPvP,

                ID, "Snow Golem",

                Color.WHITE,
                new ItemBuilder(Material.SNOWBALL),

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

            kit.set(0, player -> new KitItemBuilder(this, Material.SNOWBALL).addPassive(Passive.ATTACK_DAMAGE, 6).addPassive(Passive.IRON_GOLEM_SUMMON, 1).addActive(Active.SNOWBALL_THROW, 1));

            kit.setHelmet(player -> new KitItemBuilder(this, Material.CARVED_PUMPKIN));
            kit.setChestplate(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.CHESTPLATE, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 2));
            kit.setLeggings(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.LEGGINGS, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setBoots(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.BOOTS, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 1));

            kit.addPotionBuilder(new PotionBuilder(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, true, false, true));

            return kit;
        }

        @Override
        public double getMaxHealth() {
            return 32D;
        }

        @Override
        public double getKnockbackResistance() {
            return 0.0D;
        }

        @Override
        public HealthRegen getHealthRegen() {
            return HealthRegen.LOW;
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

            kit.set(0, player -> new KitItemBuilder(this, Material.SNOWBALL).addPassive(Passive.ATTACK_DAMAGE, 7).addPassive(Passive.IRON_GOLEM_SUMMON, 1).addActive(Active.SNOWBALL_THROW, 2));
            kit.set(1, player -> new KitItemBuilder(this, Material.IRON_INGOT, 1, "§f§lIron Surge").addActive(Active.GOLEM_SPEED, 1));

            kit.setHelmet(player -> new KitItemBuilder(this, Material.CARVED_PUMPKIN));
            kit.setChestplate(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.CHESTPLATE, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 2));
            kit.setLeggings(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.LEGGINGS, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 2));
            kit.setBoots(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.BOOTS, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 1));

            kit.addPotionBuilder(new PotionBuilder(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, true, false, true));

            return kit;
        }

        @Override
        public double getMaxHealth() {
            return 32D;
        }

        @Override
        public double getKnockbackResistance() {
            return 0.0D;
        }

        @Override
        public HealthRegen getHealthRegen() {
            return HealthRegen.LOW;
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

            kit.set(0, player -> new KitItemBuilder(this, Material.SNOWBALL).addPassive(Passive.ATTACK_DAMAGE, 8).addPassive(Passive.IRON_GOLEM_SUMMON, 1).addActive(Active.SNOWBALL_THROW, 3));
            kit.set(1, player -> new KitItemBuilder(this, Material.IRON_INGOT, 1, "§f§lIron Surge").addActive(Active.GOLEM_SPEED, 1));

            kit.setHelmet(player -> new KitItemBuilder(this, Material.CARVED_PUMPKIN));
            kit.setChestplate(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.CHESTPLATE, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 2));
            kit.setLeggings(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.LEGGINGS, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 2));
            kit.setBoots(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.BOOTS, LEATHER_COLOR).addEnchantment(Enchantment.PROTECTION, 2));

            kit.addPotionBuilder(new PotionBuilder(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, true, false, true));

            return kit;
        }

        @Override
        public double getMaxHealth() {
            return 32D;
        }

        @Override
        public double getKnockbackResistance() {
            return 0.0D;
        }

        @Override
        public HealthRegen getHealthRegen() {
            return HealthRegen.LOW;
        }
    }
}
