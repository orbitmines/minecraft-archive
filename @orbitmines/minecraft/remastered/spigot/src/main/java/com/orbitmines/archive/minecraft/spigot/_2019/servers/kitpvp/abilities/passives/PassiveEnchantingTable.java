package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.passives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events.KitEvent;
import com.orbitmines.archive.minecraft._2019.utils.RandomUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ItemUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.npcs.FloatingItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public class PassiveEnchantingTable implements Passive.Handler<PlayerDeathEvent> {

    @Override
    public void trigger(KitEvent<PlayerDeathEvent> passiveEvent, PlayerDeathEvent event, int level) {
        Player entity = event.getEntity();
        Player killer = entity.getKiller();

        /* There's a chance of the lightning hitting, otherwise move on */
        if (Math.random() >= getChance(level))
            return;

        Slot slot = Slot.random(killer);

        if (slot == null)
            /* All possible enchantments gained. */
            return;

        ItemStack itemStack = slot.getFor(killer);
        Enchantment enchantment = slot.randomEnchantment(killer);

        int newLevel = itemStack.getEnchantmentLevel(enchantment) + 1;

        /* Since 1.13 sharpness enchantment no longer applies on items it cannot enchant, so we do it ourselves. */
        if (enchantment == Enchantment.SHARPNESS && !enchantment.canEnchantItem(itemStack))
            itemStack = Passive.ATTACK_DAMAGE.apply(passiveEvent.server().getNms().customItem(), itemStack, Passive.ATTACK_DAMAGE.getLevel(passiveEvent.server().getNms().customItem(), itemStack) + 1);

        /* Clear `glow` effect from item */
        ItemMeta meta = itemStack.getItemMeta();
        if (meta.hasItemFlag(ItemFlag.HIDE_ENCHANTS)) {
            meta.removeEnchant(Enchantment.UNBREAKING);
            meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemStack.setItemMeta(meta);
        }

        /* Add Enchantment */
        itemStack.addUnsafeEnchantment(enchantment, newLevel);

        slot.setFor(killer, itemStack);

        /* Build Item Hologram */
        ItemStack fItemstack = itemStack;
        FloatingItem hologram = new FloatingItem(() -> new ItemStack(fItemstack), entity.getLocation());
        hologram.addLine(() -> Passive.ENCHANTING_TABLE.getColor().getCc() + "§l" + Passive.ENCHANTING_TABLE.getName(), false);
        hologram.addLine(() -> "§e§o+ " + ChatColor.stripColor(ItemUtils.getName(enchantment, newLevel)), false);
        hologram.create(killer);

        new BukkitRunnable() {
            @Override
            public void run() {
                hologram.destroy();
            }
        }.runTaskLater(passiveEvent.server().getPlugin(), 60L);
    }

    public double getChance(int level) {
        switch (level) {
            case 1:
                return 0.5D;
            case 2:
                return 0.6D;
            case 3:
                return 0.7D;
            default:
                throw new ArrayIndexOutOfBoundsException();
        }
    }

    private enum Slot {

        WEAPON(new Enchantment[] {
                Enchantment.SHARPNESS,
                Enchantment.KNOCKBACK,
                Enchantment.FIRE_ASPECT,
                Enchantment.SWEEPING_EDGE
        }) {
            @Override
            public ItemStack getFor(Player player) {
                return player.getInventory().getItemInMainHand();
            }

            @Override
            public void setFor(Player player, ItemStack itemStack) {
                player.getInventory().setItemInMainHand(itemStack);
            }
        },
        BOW(new Enchantment[] {
                Enchantment.POWER,
                Enchantment.PUNCH,
                Enchantment.FLAME,
                Enchantment.INFINITY
        }) {
            @Override
            public ItemStack getFor(Player player) {
                int slot = getSlot(player);
                if (slot == -1)
                    return null;

                return player.getInventory().getItem(slot);
            }

            @Override
            public void setFor(Player player, ItemStack itemStack) {
                player.getInventory().setItem(getSlot(player), itemStack);
            }

            private int getSlot(Player player) {
                return player.getInventory().first(Material.BOW);
            }
        },

        HELMET(new Enchantment[] {
                Enchantment.PROTECTION,
                Enchantment.FIRE_PROTECTION,
                Enchantment.BLAST_PROTECTION,
                Enchantment.PROJECTILE_PROTECTION,

                Enchantment.RESPIRATION
        }) {
            @Override
            public ItemStack getFor(Player player) {
                return player.getInventory().getHelmet();
            }

            @Override
            public void setFor(Player player, ItemStack itemStack) {
                player.getInventory().setHelmet(itemStack);
            }
        },
        CHESTPLATE(new Enchantment[] {
                Enchantment.PROTECTION,
                Enchantment.FIRE_PROTECTION,
                Enchantment.BLAST_PROTECTION,
                Enchantment.PROJECTILE_PROTECTION
        }) {
            @Override
            public ItemStack getFor(Player player) {
                return player.getInventory().getChestplate();
            }

            @Override
            public void setFor(Player player, ItemStack itemStack) {
                player.getInventory().setChestplate(itemStack);
            }
        },
        LEGGINGS(new Enchantment[] {
                Enchantment.PROTECTION,
                Enchantment.FIRE_PROTECTION,
                Enchantment.BLAST_PROTECTION,
                Enchantment.PROJECTILE_PROTECTION
        }) {
            @Override
            public ItemStack getFor(Player player) {
                return player.getInventory().getLeggings();
            }

            @Override
            public void setFor(Player player, ItemStack itemStack) {
                player.getInventory().setLeggings(itemStack);
            }
        },
        BOOTS(new Enchantment[] {
                Enchantment.PROTECTION,
                Enchantment.FIRE_PROTECTION,
                Enchantment.BLAST_PROTECTION,
                Enchantment.PROJECTILE_PROTECTION,

                Enchantment.FEATHER_FALLING,
                Enchantment.DEPTH_STRIDER,
                Enchantment.FROST_WALKER
        }) {
            @Override
            public ItemStack getFor(Player player) {
                return player.getInventory().getBoots();
            }

            @Override
            public void setFor(Player player, ItemStack itemStack) {
                player.getInventory().setBoots(itemStack);
            }
        };

        private final Enchantment[] enchantments;

        Slot(Enchantment[] enchantments) {
            this.enchantments = enchantments;
        }

        public Enchantment randomEnchantment(Player player) {
            ItemStack itemStack = this.getFor(player);

            if (itemStack == null)
                return null;

            Set<Enchantment> enchantments = new HashSet<>();
            for (Enchantment enchantment : this.enchantments) {
                if (itemStack.getEnchantmentLevel(enchantment) < getMaxLevel(enchantment))
                    enchantments.add(enchantment);
            }

            return RandomUtils.randomFrom(enchantments);
        }

        public ItemStack getFor(Player player) {
            throw new IllegalStateException();
        }

        public void setFor(Player player, ItemStack itemStack) {
            throw new IllegalStateException();
        }

        private int getMaxLevel(Enchantment enchantment) {
            if (enchantment == Enchantment.POWER ||
                enchantment == Enchantment.SHARPNESS ||
                enchantment == Enchantment.PROTECTION
            )
                return 8;
            else if (enchantment == Enchantment.KNOCKBACK ||
                     enchantment == Enchantment.SWEEPING_EDGE ||
                     enchantment == Enchantment.PUNCH ||
                     enchantment == Enchantment.FIRE_PROTECTION ||
                     enchantment == Enchantment.BLAST_PROTECTION ||
                     enchantment == Enchantment.PROJECTILE_PROTECTION ||
                     enchantment == Enchantment.FEATHER_FALLING
            )
                return 5;
            else if (enchantment == Enchantment.FIRE_ASPECT)
                return 3;
            else
                return enchantment.getMaxLevel();
        }

        public static Slot random(Player player) {
            Set<Slot> slots = new HashSet<>();
            for (Slot slot : values()) {
                if (slot.randomEnchantment(player) != null)
                    slots.add(slot);
            }

            return RandomUtils.randomFrom(slots);
        }
    }
}
