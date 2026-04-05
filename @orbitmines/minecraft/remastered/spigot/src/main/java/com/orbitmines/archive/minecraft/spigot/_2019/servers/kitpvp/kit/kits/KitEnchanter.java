package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.kits;

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
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilderInstance;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.LeatherArmorBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.kits.Kit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class KitEnchanter extends KitPvPKit {

    public static final long ID = 9L;

    private static final org.bukkit.Color DEFAULT_COLOR = org.bukkit.Color.PURPLE;

    public KitEnchanter(KitPvP kitPvP) {
        super(
                kitPvP,

                ID, "Enchanter",

                Color.PURPLE, new ItemBuilder(Material.ENCHANTED_BOOK),

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

    /* Pick a random item (weapon, active item, etc.) from another kit's level 1 inventory */
    private static ItemBuilderInstance randomItem(KitPvPPlayer player) {
        List<KitPvPKit> otherKits = KitPvPKit.getKits().stream()
                .filter(k -> k.getId() != ID)
                .collect(Collectors.toList());

        /* Try up to 10 times to find a non-null item */
        for (int attempt = 0; attempt < 10; attempt++) {
            KitPvPKit randomKit = otherKits.get(ThreadLocalRandom.current().nextInt(otherKits.size()));
            Kit<KitPvPPlayer> kit = randomKit.getLevel(1).getKit();

            /* Collect all non-null content slots (excluding slot 8 which is player tracker) */
            List<Integer> filledSlots = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                if (kit.get(i) != null)
                    filledSlots.add(i);
            }

            if (!filledSlots.isEmpty()) {
                int slot = filledSlots.get(ThreadLocalRandom.current().nextInt(filledSlots.size()));
                return kit.get(slot).toBuilder(player);
            }
        }

        /* Fallback: return a basic sword */
        return null;
    }

    private class Level1 extends KitPvPKit.Level {

        @Override
        public int getPrice() {
            return 12500;
        }

        @Override
        protected Kit<KitPvPPlayer> registerKit() {
            Kit<KitPvPPlayer> kit = new Kit<>();

            kit.set(0, player -> new KitItemBuilder(this, Material.ENCHANTED_BOOK).addPassive(Passive.ENCHANTING_TABLE, 1).addPassive(Passive.ATTACK_DAMAGE, 5).addEnchantment(Enchantment.SHARPNESS, 1));
            kit.set(1, player -> randomItem(player));

            kit.setHelmet(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.HELMET, DEFAULT_COLOR).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setChestplate(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.CHESTPLATE, DEFAULT_COLOR).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setLeggings(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.LEGGINGS, DEFAULT_COLOR).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setBoots(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.BOOTS, DEFAULT_COLOR).addEnchantment(Enchantment.PROTECTION, 1));

            return kit;
        }

        @Override
        public double getMaxHealth() {
            return 42;
        }

        @Override
        public double getKnockbackResistance() {
            return 0;
        }

        @Override
        public HealthRegen getHealthRegen() {
            return HealthRegen.NORMAL;
        }
    }

    private class Level2 extends KitPvPKit.Level {

        @Override
        public int getPrice() {
            return 30000;
        }

        @Override
        protected Kit<KitPvPPlayer> registerKit() {
            Kit<KitPvPPlayer> kit = new Kit<>();

            kit.set(0, player -> new KitItemBuilder(this, Material.ENCHANTED_BOOK).addPassive(Passive.ENCHANTING_TABLE, 2).addPassive(Passive.ATTACK_DAMAGE, 5).addEnchantment(Enchantment.SHARPNESS, 2));
            kit.set(1, player -> randomItem(player));
            kit.set(2, player -> randomItem(player));

            kit.setHelmet(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.HELMET, DEFAULT_COLOR).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setChestplate(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.CHESTPLATE, DEFAULT_COLOR).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setLeggings(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.LEGGINGS, DEFAULT_COLOR).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setBoots(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.BOOTS, DEFAULT_COLOR).addEnchantment(Enchantment.PROTECTION, 1));

            return kit;
        }

        @Override
        public double getMaxHealth() {
            return 42;
        }

        @Override
        public double getKnockbackResistance() {
            return 0;
        }

        @Override
        public HealthRegen getHealthRegen() {
            return HealthRegen.NORMAL;
        }
    }

    private class Level3 extends KitPvPKit.Level {

        @Override
        public int getPrice() {
            return 70000;
        }

        @Override
        protected Kit<KitPvPPlayer> registerKit() {
            Kit<KitPvPPlayer> kit = new Kit<>();

            kit.set(0, player -> new KitItemBuilder(this, Material.ENCHANTED_BOOK).addPassive(Passive.ENCHANTING_TABLE, 3).addPassive(Passive.ATTACK_DAMAGE, 6).addEnchantment(Enchantment.SHARPNESS, 2));
            kit.set(1, player -> randomItem(player));
            kit.set(2, player -> randomItem(player));
            kit.set(3, player -> randomItem(player));

            kit.setHelmet(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.HELMET, DEFAULT_COLOR).addEnchantment(Enchantment.PROTECTION, 2));
            kit.setChestplate(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.CHESTPLATE, DEFAULT_COLOR).addEnchantment(Enchantment.PROTECTION, 2));
            kit.setLeggings(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.LEGGINGS, DEFAULT_COLOR).addEnchantment(Enchantment.PROTECTION, 1));
            kit.setBoots(player -> new KitLeatherArmorBuilder(this, LeatherArmorBuilder.Type.BOOTS, DEFAULT_COLOR).addEnchantment(Enchantment.PROTECTION, 1));

            return kit;
        }

        @Override
        public double getMaxHealth() {
            return 42;
        }

        @Override
        public double getKnockbackResistance() {
            return 0;
        }

        @Override
        public HealthRegen getHealthRegen() {
            return HealthRegen.NORMAL;
        }
    }
}
