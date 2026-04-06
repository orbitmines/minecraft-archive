package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.actives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Active;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft._2019.utils.cooldown.Cooldown;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.entity.EntityNms;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BundleMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.*;

public class ActiveSpearToss implements Active.Handler {

    private static final Cooldown NO_COOLDOWN = new Cooldown(0);

    public static final String BUNDLE_NAME = "§4§lBloodthirst";
    private static final Map<UUID, Integer> skullCounts = new HashMap<>();
    private static final Map<UUID, List<String>> skullNames = new HashMap<>();
    private static final Map<UUID, List<String>> skullRawNames = new HashMap<>();
    private static boolean listenerRegistered = false;
    /* Tracks players currently dealing thrown spear damage — prevents melee listener from halving it */
    private static final Set<UUID> dealingThrownDamage = new HashSet<>();

    @Override
    public void trigger(PlayerInteractEvent event, KitPvPPlayer omp, int level) {
        Player player = omp.bukkit();
        KitPvP kitPvP = (KitPvP) KitPvP.getInstance();

        if (!listenerRegistered) {
            kitPvP.getPlugin().getServer().getPluginManager().registerEvents(new BundleProtectListener(), kitPvP.getPlugin());
            kitPvP.getPlugin().getServer().getPluginManager().registerEvents(new MeleeDamageListener(kitPvP), kitPvP.getPlugin());
            listenerRegistered = true;
        }

        /* Find the spear item in the player's hand */
        int heldSlot = player.getInventory().getHeldItemSlot();
        ItemStack spearItem = player.getInventory().getItem(heldSlot);
        if (spearItem == null || spearItem.getType() == Material.AIR)
            return;

        /* Calculate thrown damage: read the player's actual attack damage attribute + sharpness bonus
           This matches exactly what a normal melee hit would deal before armor */
        double baseAttack = kitPvP.getNms().entity().getAttribute(player, EntityNms.Attribute.ATTACK_DAMAGE);
        int sharpnessLevel = spearItem.getEnchantmentLevel(Enchantment.SHARPNESS);
        double thrownDamage = baseAttack + (sharpnessLevel > 0 ? 0.5 * sharpnessLevel + 0.5 : 0);

        /* Remove the spear from inventory */
        player.getInventory().setItem(heldSlot, null);

        /* Switch to other spear after a short delay */
        int thrownFromSlot = heldSlot;
        new BukkitRunnable() {
            @Override
            public void run() {
                switchToOtherSpear(player, kitPvP, thrownFromSlot);
            }
        }.runTaskLater(kitPvP.getPlugin(), 5);

        /* Launch the spear */
        Location eyeLoc = player.getEyeLocation();
        Vector direction = eyeLoc.getDirection().multiply(1.8);

        /* Spawn armor stand as the spear projectile */
        ArmorStand spear = player.getWorld().spawn(eyeLoc.clone().add(direction.clone().normalize()), ArmorStand.class);
        spear.setVisible(false);
        spear.setGravity(false);
        spear.setInvulnerable(true);
        spear.setMarker(true);
        spear.setSmall(true);
        spear.getEquipment().setItemInMainHand(spearItem.clone());

        /* Orient the spear along the throw direction — set once */
        initOrientation(spear, direction);

        omp.playSound(Sound.ITEM_TRIDENT_THROW);

        List<UUID> hitPlayers = new ArrayList<>();

        new BukkitRunnable() {
            Vector velocity = direction.clone();
            int tick = 0;
            boolean stuck = false;
            int stuckTicks = 0;
            boolean returning = false;
            int returnTick = 0;

            @Override
            public void run() {
                if (!player.isOnline() || spear.isDead()) {
                    cleanup();
                    return;
                }

                /* If player died, just remove the spear */
                KitPvPPlayer omp2 = kitPvP.getPlayer(player);
                if (omp2.getSelectedKit() == null || omp2.isSpectator()) {
                    cleanup();
                    return;
                }

                /* Phase 1: Flying forward */
                if (!stuck && !returning) {
                    /* Apply gravity for arc */
                    velocity.setY(velocity.getY() - 0.05);

                    Location current = spear.getLocation();
                    Location next = current.clone().add(velocity);

                    /* Check for block collision */
                    Block block = next.getBlock();
                    if (block.getType().isSolid()) {
                        stuck = true;
                        Location stuckLoc = current.clone().subtract(0, 0.5, 0);
                        moveOnly(spear, stuckLoc);
                        player.getWorld().playSound(current, Sound.ITEM_TRIDENT_HIT_GROUND, 1.0f, 1.0f);
                        tick++;
                        return;
                    }

                    /* Move only — orientation was set at spawn */
                    moveOnly(spear, next);

                    /* Black smoke particle trail behind the spear */
                    player.getWorld().spawnParticle(Particle.SMOKE,
                            next.clone().subtract(velocity.clone().normalize().multiply(0.5)),
                            3, 0.05, 0.05, 0.05, 0.01);

                    /* Check for player hits — pierces through all */
                    for (Player target : player.getWorld().getPlayers()) {
                        if (target.equals(player))
                            continue;
                        if (hitPlayers.contains(target.getUniqueId()))
                            continue;

                        KitPvPPlayer targetOmp = kitPvP.getPlayer(target);
                        if (targetOmp.getSelectedKit() == null || targetOmp.isSpectator())
                            continue;

                        if (target.getLocation().add(0, 1, 0).distance(next) < 1.5) {
                            hitPlayers.add(target.getUniqueId());

                            /* Deal full spear damage — mark as thrown so melee listener doesn't halve it */
                            dealingThrownDamage.add(player.getUniqueId());
                            target.setNoDamageTicks(0);
                            target.damage(thrownDamage, player);
                            dealingThrownDamage.remove(player.getUniqueId());

                            /* Red smoke effect */
                            target.getWorld().spawnParticle(Particle.CRIMSON_SPORE,
                                    target.getLocation().add(0, 1, 0), 30, 0.3, 0.5, 0.3, 0.1);
                            target.getWorld().playSound(target.getLocation(), Sound.ENTITY_PLAYER_HURT, 1.0f, 0.8f);
                        }
                    }

                    /* Timeout after 80 ticks */
                    if (tick >= 80) {
                        returning = true;
                    }

                    tick++;
                    return;
                }

                /* Phase 2: Stuck in block */
                if (stuck && !returning) {
                    stuckTicks++;
                    if (stuckTicks >= 20) {
                        returning = true;
                        stuck = false;
                    }
                    return;
                }

                /* Phase 3: Returning to player */
                if (returning) {
                    Location spearLoc = spear.getLocation();
                    Location playerLoc = player.getLocation().add(0, 1, 0);
                    double distance = spearLoc.distance(playerLoc);

                    if (distance < 2.0) {
                        /* Arrived back */
                        cleanup();
                        returnSpearToInventory(player, spearItem);
                        player.getWorld().playSound(playerLoc, Sound.ITEM_TRIDENT_RETURN, 1.0f, 1.0f);
                        return;
                    }

                    /* Fast direct return with slight upward arc at start */
                    Vector toPlayer = playerLoc.toVector().subtract(spearLoc.toVector()).normalize();
                    double speed = Math.min(3.0, 1.5 + returnTick * 0.15);
                    Vector returnVel = toPlayer.multiply(speed);

                    /* Slight upward arc only at the very beginning */
                    if (returnTick < 5 && distance > 5) {
                        returnVel.setY(returnVel.getY() + 0.2);
                    }

                    Location returnNext = spearLoc.clone().add(returnVel);

                    /* Return: re-orient toward player then move */
                    initOrientation(spear, toPlayer);
                    moveOnly(spear, returnNext);

                    returnTick++;

                    /* Safety timeout */
                    if (returnTick >= 100) {
                        cleanup();
                        returnSpearToInventory(player, spearItem);
                    }
                }
            }

            private void cleanup() {
                if (!spear.isDead()) spear.remove();
                cancel();
            }
        }.runTaskTimer(kitPvP.getPlugin(), 0, 1);
    }

    private void initOrientation(ArmorStand stand, Vector direction) {
        double dx = direction.getX();
        double dy = direction.getY();
        double dz = direction.getZ();
        double horizontal = Math.sqrt(dx * dx + dz * dz);

        Location loc = stand.getLocation();
        /* Yaw matches movement direction so the client doesn't auto-rotate the body */
        float yaw = (float) Math.toDegrees(Math.atan2(-dx, dz));
        loc.setYaw(yaw);
        loc.setPitch(0);
        stand.teleport(loc);

        /* Arm pose: pitch tilts forward, roll (Z) flips 180° to orient the item correctly */
        float pitch = (float) Math.toDegrees(Math.atan2(-dy, horizontal));
        stand.setRightArmPose(new EulerAngle(Math.toRadians(-45 + pitch), 0, Math.PI));
    }

    private void moveOnly(ArmorStand stand, Location target) {
        /* Use NMS setPos to move without teleport packet */
        net.minecraft.world.entity.Entity nms = ((CraftEntity) stand).getHandle();
        nms.setPos(target.getX(), target.getY(), target.getZ());
    }

    private void switchToOtherSpear(Player player, KitPvP kitPvP, int currentSlot) {
        for (int i = 0; i < 9; i++) {
            if (i == currentSlot) continue;
            ItemStack item = player.getInventory().getItem(i);
            if (item == null) continue;

            Map<Active, Integer> actives = Active.from(kitPvP.getNms().customItem(), item);
            if (actives != null && actives.containsKey(Active.SPEAR_TOSS)) {
                player.getInventory().setHeldItemSlot(i);
                return;
            }
        }
    }

    private void returnSpearToInventory(Player player, ItemStack spearItem) {
        if (!player.isOnline()) return;

        /* Return spear directly to inventory */
        for (int i = 0; i < 9; i++) {
            ItemStack item = player.getInventory().getItem(i);
            if (item == null || item.getType() == Material.AIR) {
                player.getInventory().setItem(i, spearItem);

                /* If the player's current held slot is empty, switch to the returned item */
                ItemStack held = player.getInventory().getItem(player.getInventory().getHeldItemSlot());
                if (held == null || held.getType() == Material.AIR) {
                    player.getInventory().setHeldItemSlot(i);
                }
                return;
            }
        }
        /* Fallback: drop at feet */
        player.getWorld().dropItemNaturally(player.getLocation(), spearItem);
    }

    private static int findBundleSlot(Player player) {
        for (int i = 0; i < 36; i++) {
            ItemStack item = player.getInventory().getItem(i);
            if (item != null && (item.getType() == Material.BLACK_BUNDLE || item.getType() == Material.RED_BUNDLE)) {
                if (item.hasItemMeta() && item.getItemMeta().hasDisplayName() &&
                        item.getItemMeta().getDisplayName().startsWith(BUNDLE_NAME)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static int getSkulls(Player player) {
        return skullCounts.getOrDefault(player.getUniqueId(), 0);
    }

    public static void setSkulls(Player player, int count, String rawName, String displayName) {
        skullCounts.put(player.getUniqueId(), count);
        skullRawNames.computeIfAbsent(player.getUniqueId(), k -> new ArrayList<>()).add(rawName);
        skullNames.computeIfAbsent(player.getUniqueId(), k -> new ArrayList<>()).add(displayName);
        updateBundle(player);
    }

    public static void updateBundle(Player player) {
        int skulls = getSkulls(player);
        List<String> displayNames = skullNames.getOrDefault(player.getUniqueId(), new ArrayList<>());
        List<String> rawNames = skullRawNames.getOrDefault(player.getUniqueId(), new ArrayList<>());

        /* Find existing bundle */
        int bundleSlot = findBundleSlot(player);
        if (bundleSlot == -1) return;

        /* Rebuild the bundle with skulls inside */
        Material bundleMaterial = skulls > 0 ? Material.RED_BUNDLE : Material.BLACK_BUNDLE;
        ItemStack bundle = new ItemStack(bundleMaterial, skulls > 0 ? skulls : 1);
        BundleMeta meta = (BundleMeta) bundle.getItemMeta();
        meta.setDisplayName(BUNDLE_NAME + " §7(" + skulls + ")");

        List<String> lore = new ArrayList<>();
        lore.add("§7Collected Skulls:");
        for (String displayName : displayNames) {
            lore.add("  §c\u2620 " + displayName);
        }
        if (skulls == 0) {
            lore.add("  §8None");
        }
        meta.setLore(lore);

        /* Add player head for each kill — unique display name prevents stacking */
        for (int i = 0; i < displayNames.size(); i++) {
            String rawName = rawNames.get(i);
            String displayName = displayNames.get(i);
            ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
            skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(rawName));
            skullMeta.setDisplayName("§c\u2620 " + displayName + " §8#" + (i + 1));
            skull.setItemMeta(skullMeta);
            meta.addItem(skull);
        }

        bundle.setItemMeta(meta);

        /* Re-apply SOUL_COLLECTOR passive metadata so the bundle continues to trigger on kills */
        KitPvP kitPvP = (KitPvP) KitPvP.getInstance();
        bundle = Passive.SOUL_COLLECTOR.apply(kitPvP.getNms().customItem(), bundle, 1);

        player.getInventory().setItem(bundleSlot, bundle);
    }

    public static void clearSkulls(UUID uuid) {
        skullCounts.remove(uuid);
        skullNames.remove(uuid);
        skullRawNames.remove(uuid);
    }

    @Override
    public Cooldown getCooldown(int level) {
        return NO_COOLDOWN;
    }

    /* Halve melee damage when hitting with a spear that has the SPEAR_TOSS active */
    public static class MeleeDamageListener implements Listener {

        private final KitPvP kitPvP;

        public MeleeDamageListener(KitPvP kitPvP) {
            this.kitPvP = kitPvP;
        }

        @EventHandler(priority = EventPriority.HIGH)
        public void onMeleeDamage(EntityDamageByEntityEvent event) {
            if (!(event.getDamager() instanceof Player))
                return;
            if (event.isCancelled())
                return;

            Player damager = (Player) event.getDamager();

            /* Don't halve thrown spear damage */
            if (dealingThrownDamage.contains(damager.getUniqueId()))
                return;

            ItemStack held = damager.getInventory().getItemInMainHand();
            if (held == null || held.getType() == Material.AIR)
                return;

            Map<Active, Integer> actives = Active.from(kitPvP.getNms().customItem(), held);
            if (actives != null && actives.containsKey(Active.SPEAR_TOSS)) {
                event.setDamage(event.getDamage() * 0.5);
            }
        }
    }

    /* Prevent players from interacting with the Bloodthirst bundle */
    public static class BundleProtectListener implements Listener {

        @EventHandler
        public void onBundleRightClick(PlayerInteractEvent event) {
            if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)
                return;
            if (isBundleItem(event.getItem()))
                event.setCancelled(true);
        }

        @EventHandler
        public void onBundleInteract(InventoryClickEvent event) {
            ItemStack current = event.getCurrentItem();
            ItemStack cursor = event.getCursor();

            if (isBundleItem(current) || isBundleItem(cursor)) {
                event.setCancelled(true);
            }
        }

        private boolean isBundleItem(ItemStack item) {
            if (item == null) return false;
            if (item.getType() != Material.BLACK_BUNDLE && item.getType() != Material.RED_BUNDLE) return false;
            return item.hasItemMeta() && item.getItemMeta().hasDisplayName() &&
                    item.getItemMeta().getDisplayName().startsWith(BUNDLE_NAME);
        }
    }
}
