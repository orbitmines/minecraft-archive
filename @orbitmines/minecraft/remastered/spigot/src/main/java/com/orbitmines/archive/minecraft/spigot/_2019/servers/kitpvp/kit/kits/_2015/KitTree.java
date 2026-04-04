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
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.HealthRegen;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.KitClass;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.KitPvPKit;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.kits.Kit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public class KitTree extends KitPvPKit {

    public static final long ID = 25L;

    public KitTree(KitPvP kitPvP) {
        super(
                kitPvP,

                ID, "Tree",

                Color.GREEN,
                new ItemBuilder(Material.OAK_LEAVES),

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

            kit.set(0, player -> new KitItemBuilder(this, Material.OAK_LEAVES).addPassive(Passive.ATTACK_DAMAGE, 4));

            kit.setChestplate(player -> new KitItemBuilder(this, Material.CHAINMAIL_CHESTPLATE));
            kit.setLeggings(player -> new KitItemBuilder(this, Material.CHAINMAIL_LEGGINGS));
            kit.setBoots(player -> new KitItemBuilder(this, Material.CHAINMAIL_BOOTS));

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
            return 35000;
        }

        @Override
        protected Kit<KitPvPPlayer> registerKit() {
            Kit<KitPvPPlayer> kit = new Kit<>();

            kit.set(0, player -> new KitItemBuilder(this, Material.OAK_LEAVES).addPassive(Passive.ATTACK_DAMAGE, 5));
            kit.set(1, player -> new KitItemBuilder(this, Material.OAK_LOG, 1, "§2§lBarrier").addActive(Active.BARRIER, 1));

            kit.setChestplate(player -> new KitItemBuilder(this, Material.CHAINMAIL_CHESTPLATE));
            kit.setLeggings(player -> new KitItemBuilder(this, Material.CHAINMAIL_LEGGINGS));
            kit.setBoots(player -> new KitItemBuilder(this, Material.CHAINMAIL_BOOTS).addEnchantment(Enchantment.PROTECTION, 1));

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
            return 80000;
        }

        @Override
        protected Kit<KitPvPPlayer> registerKit() {
            Kit<KitPvPPlayer> kit = new Kit<>();

            kit.set(0, player -> new KitItemBuilder(this, Material.OAK_LEAVES).addPassive(Passive.ATTACK_DAMAGE, 5));
            kit.set(1, player -> new KitItemBuilder(this, Material.OAK_LOG, 1, "§2§lBarrier").addActive(Active.BARRIER, 2));

            kit.setChestplate(player -> new KitItemBuilder(this, Material.CHAINMAIL_CHESTPLATE).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setLeggings(player -> new KitItemBuilder(this, Material.CHAINMAIL_LEGGINGS).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setBoots(player -> new KitItemBuilder(this, Material.CHAINMAIL_BOOTS).addEnchantment(Enchantment.PROTECTION, 1));

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
