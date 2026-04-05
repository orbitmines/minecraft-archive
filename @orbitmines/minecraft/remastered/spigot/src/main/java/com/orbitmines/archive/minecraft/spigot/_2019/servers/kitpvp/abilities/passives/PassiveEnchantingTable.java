package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.passives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Active;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events.KitEvent;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;
import com.orbitmines.archive.minecraft._2019.utils.RandomUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ItemUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.npcs.FloatingItem;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.itemstack.ItemStackNms;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class PassiveEnchantingTable implements Passive.Handler<PlayerDeathEvent> {

    /* Passives that can be stolen onto the weapon */
    private static final Passive[] STEALABLE_PASSIVES = {
            Passive.KNOCKUP,
            Passive.LIFESTEAL,
            Passive.WITHER_HIT,
            Passive.BLEED,
            Passive.SUCKER_PUNCH,
            Passive.BACKSTAB,
            Passive.WRECKER_OF_WORLDS
    };

    /* Actives that can be stolen onto the weapon */
    private static final Active[] STEALABLE_ACTIVES = {
            Active.FIRE_SPELL,
            Active.BLAZE_INFERNO,
            Active.TNT_LAUNCHER,
            Active.MAGIC_SPELL,
            Active.POTION_LAUNCHER,
            Active.LIGHTNING_STRIKE,
            Active.TORNADO
    };

    private static final int MAX_PASSIVE_LEVEL = 3;

    @Override
    public void trigger(KitEvent<PlayerDeathEvent> passiveEvent, PlayerDeathEvent event, int level) {
        Player killer = passiveEvent.getPlayer().bukkit();
        Player killed = passiveEvent.getTarget() != null ? passiveEvent.getTarget().bukkit() : null;

        if (killed == null)
            return;

        if (Math.random() >= getChance(level))
            return;

        ItemStackNms nms = passiveEvent.server().getNms().customItem();

        /* Decide what kind of reward: vanilla enchantment, custom passive, or active */
        int roll = ThreadLocalRandom.current().nextInt(3);

        if (roll == 0) {
            /* Try to add a custom passive to the weapon */
            if (tryAddPassive(killer, killed, nms, passiveEvent))
                return;
        } else if (roll == 1) {
            /* Try to add an active to the weapon */
            if (tryAddActive(killer, killed, nms, passiveEvent))
                return;
        }

        /* Fall back to vanilla enchantment */
        if (tryAddEnchantment(killer, killed, nms, passiveEvent))
            return;

        /* If vanilla enchantment also maxed, try passive then active */
        if (tryAddPassive(killer, killed, nms, passiveEvent))
            return;
        tryAddActive(killer, killed, nms, passiveEvent);
    }

    private boolean tryAddEnchantment(Player killer, Player killed, ItemStackNms nms, KitEvent<PlayerDeathEvent> passiveEvent) {
        Slot slot = Slot.random(killer);

        if (slot == null)
            return false;

        ItemStack itemStack = slot.getFor(killer);
        Enchantment enchantment = slot.randomEnchantment(killer);

        if (enchantment == null)
            return false;

        int newLevel = itemStack.getEnchantmentLevel(enchantment) + 1;

        /* Since 1.13 sharpness enchantment no longer applies on items it cannot enchant, so we do it ourselves. */
        if (enchantment == Enchantment.SHARPNESS && !enchantment.canEnchantItem(itemStack))
            itemStack = Passive.ATTACK_DAMAGE.apply(nms, itemStack, Passive.ATTACK_DAMAGE.getLevel(nms, itemStack) + 1);

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

        showHologram(killer, killed, itemStack, passiveEvent,
                "§e§o+ " + ChatColor.stripColor(ItemUtils.getName(enchantment, newLevel)));

        return true;
    }

    private boolean tryAddPassive(Player killer, Player killed, ItemStackNms nms, KitEvent<PlayerDeathEvent> passiveEvent) {
        ItemStack weapon = killer.getInventory().getItemInMainHand();
        if (weapon == null || weapon.getType() == Material.AIR)
            return false;

        /* Find passives that can still be leveled up */
        Map<Passive, Integer> currentPassives = Passive.from(nms, weapon, Passive.Interaction.HIT_OTHER);
        if (currentPassives == null)
            currentPassives = new HashMap<>();

        List<Passive> candidates = new ArrayList<>();
        Map<Passive, Integer> finalCurrentPassives = currentPassives;
        for (Passive passive : STEALABLE_PASSIVES) {
            int currentLevel = finalCurrentPassives.getOrDefault(passive, 0);
            if (currentLevel < MAX_PASSIVE_LEVEL)
                candidates.add(passive);
        }

        if (candidates.isEmpty())
            return false;

        Passive chosen = RandomUtils.randomFrom(candidates);
        int currentLevel = currentPassives.getOrDefault(chosen, 0);
        int newLevel = currentLevel + 1;

        /* Apply passive metadata */
        weapon = chosen.apply(nms, weapon, newLevel);

        /* Update lore: add new passive line or update existing */
        ItemMeta meta = weapon.getItemMeta();
        List<String> lore = meta.getLore() != null ? meta.getLore() : new ArrayList<>();

        String oldDisplayName = currentLevel > 0 ? ChatColor.stripColor(chosen.getDisplayName(currentLevel)) : null;
        boolean replaced = false;

        if (oldDisplayName != null) {
            for (int i = 0; i < lore.size(); i++) {
                if (ChatColor.stripColor(lore.get(i)).equals(oldDisplayName)) {
                    /* Replace the old line and remove its description lines */
                    lore.set(i, chosen.getDisplayName(newLevel));
                    /* Remove old description lines (lines starting with spaces after this line) */
                    int j = i + 1;
                    while (j < lore.size() && ChatColor.stripColor(lore.get(j)).startsWith("  ")) {
                        lore.remove(j);
                    }
                    /* Insert new description */
                    String[] desc = chosen.getDescription(newLevel);
                    for (int d = 0; d < desc.length; d++) {
                        lore.add(i + 1 + d, desc[d]);
                    }
                    replaced = true;
                    break;
                }
            }
        }

        if (!replaced) {
            lore.add(chosen.getDisplayName(newLevel));
            lore.addAll(Arrays.asList(chosen.getDescription(newLevel)));
        }

        meta.setLore(lore);
        weapon.setItemMeta(meta);

        /* Clear glow if present */
        meta = weapon.getItemMeta();
        if (meta.hasItemFlag(ItemFlag.HIDE_ENCHANTS)) {
            meta.removeEnchant(Enchantment.UNBREAKING);
            meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
            weapon.setItemMeta(meta);
        }
        if (!weapon.getEnchantments().containsKey(Enchantment.UNBREAKING)) {
            weapon.addUnsafeEnchantment(Enchantment.UNBREAKING, 1);
            meta = weapon.getItemMeta();
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            weapon.setItemMeta(meta);
        }

        killer.getInventory().setItemInMainHand(weapon);

        showHologram(killer, killed, weapon, passiveEvent,
                "§e§o+ " + ChatColor.stripColor(chosen.getDisplayName(newLevel)));

        return true;
    }

    private boolean tryAddActive(Player killer, Player killed, ItemStackNms nms, KitEvent<PlayerDeathEvent> passiveEvent) {
        ItemStack weapon = killer.getInventory().getItemInMainHand();
        if (weapon == null || weapon.getType() == Material.AIR)
            return false;

        Active chosen = STEALABLE_ACTIVES[ThreadLocalRandom.current().nextInt(STEALABLE_ACTIVES.length)];

        /* Check current active on weapon — only 1 allowed, new overrides old */
        Map<Active, Integer> currentActives = Active.from(nms, weapon);

        ItemMeta meta = weapon.getItemMeta();
        List<String> lore = meta.getLore() != null ? meta.getLore() : new ArrayList<>();

        if (currentActives != null && !currentActives.isEmpty()) {
            for (Map.Entry<Active, Integer> entry : currentActives.entrySet()) {
                Active oldActive = entry.getKey();
                int oldLevel = entry.getValue();

                /* If same active, level it up instead (cap at 3) */
                if (oldActive == chosen) {
                    if (oldLevel >= 3)
                        return false;

                    int newLevel = oldLevel + 1;
                    weapon = chosen.apply(nms, weapon, newLevel);

                    /* Update lore */
                    String oldDisplay = ChatColor.stripColor(oldActive.getDisplayName(oldLevel));
                    for (int i = 0; i < lore.size(); i++) {
                        if (ChatColor.stripColor(lore.get(i)).equals(oldDisplay)) {
                            lore.set(i, chosen.getDisplayName(newLevel));
                            int j = i + 1;
                            while (j < lore.size() && ChatColor.stripColor(lore.get(j)).startsWith("  ")) {
                                lore.remove(j);
                            }
                            String[] desc = chosen.getDescription(newLevel);
                            for (int d = 0; d < desc.length; d++) {
                                lore.add(i + 1 + d, desc[d]);
                            }
                            break;
                        }
                    }

                    meta.setLore(lore);
                    weapon.setItemMeta(meta);
                    killer.getInventory().setItemInMainHand(weapon);

                    showHologram(killer, killed, weapon, passiveEvent,
                            "§e§o+ " + ChatColor.stripColor(chosen.getDisplayName(newLevel)));
                    return true;
                }

                /* Different active — remove the old one */
                weapon = nms.removeMetaData(weapon, "active", oldActive.toString());

                /* Remove old active lore lines */
                String oldDisplay = ChatColor.stripColor(oldActive.getDisplayName(oldLevel));
                for (int i = 0; i < lore.size(); i++) {
                    if (ChatColor.stripColor(lore.get(i)).equals(oldDisplay)) {
                        lore.remove(i);
                        while (i < lore.size() && ChatColor.stripColor(lore.get(i)).startsWith("  ")) {
                            lore.remove(i);
                        }
                        break;
                    }
                }
            }
        }

        /* Add new active at level 1 */
        weapon = chosen.apply(nms, weapon, 1);

        lore.add(chosen.getDisplayName(1));
        lore.addAll(Arrays.asList(chosen.getDescription(1)));

        meta = weapon.getItemMeta();
        meta.setLore(lore);
        weapon.setItemMeta(meta);

        killer.getInventory().setItemInMainHand(weapon);

        showHologram(killer, killed, weapon, passiveEvent,
                "§e§o+ " + ChatColor.stripColor(chosen.getDisplayName(1)));

        return true;
    }

    private void showHologram(Player killer, Player killed, ItemStack itemStack, KitEvent<PlayerDeathEvent> passiveEvent, String bonusLine) {
        ItemStack fItemstack = itemStack;
        FloatingItem hologram = new FloatingItem(() -> new ItemStack(fItemstack), killed.getLocation());
        hologram.addLine(() -> Passive.ENCHANTING_TABLE.getColor().getCc() + "§l" + Passive.ENCHANTING_TABLE.getName(), false);
        hologram.addLine(() -> bonusLine, false);
        hologram.create(killer);

        new BukkitRunnable() {
            @Override
            public void run() {
                hologram.destroy();
            }
        }.runTaskLater(passiveEvent.server().getPlugin(), 60L);
    }

    public double getChance(int level) {
        return 1.0D;
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
