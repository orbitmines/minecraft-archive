package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.events;

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.player.OfflinePlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.database.models.Claim;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.items.ClaimTool;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.region.Region;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ItemUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.PlayerUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.ActionBar;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.itemstack.ItemStackNms;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.*;
import org.bukkit.entity.minecart.ExplosiveMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*
* OrbitMines - @author Fadi Shawki - 2017
*/
@Deprecated
public class ClaimEvents implements Listener {

    private Survival survival;

    public ClaimEvents(Survival survival) {
        this.survival = survival;
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        for (ItemStack item : event.getInventory().getContents()) {
            if (ClaimTool.ITEM.equals(item)) {
                event.setCancelled(true);
                
                SurvivalPlayer player = survival.getPlayer((Player) event.getWhoClicked());
                player.sendMessage("Claim", Color.RED, "survival", "player.uncraftable", ClaimTool.ITEM.getDisplayName() + "§7");
                return;
            } else if (Survival.SPAWNER_MINER.equals(item)) {
                event.setCancelled(true);
                
                SurvivalPlayer player = survival.getPlayer((Player) event.getWhoClicked());
                player.sendMessage("Spawner Miner", Color.RED, "survival", "player.uncraftable", Survival.SPAWNER_MINER.getDisplayName() + "§7");
                return;
            } else if (Survival.PET_TICKET.equals(item)) {
                event.setCancelled(true);

                SurvivalPlayer player = survival.getPlayer((Player) event.getWhoClicked());
                player.sendMessage("Pet Ticket", Color.RED, "survival", "player.uncraftable", Survival.PET_TICKET.getDisplayName() + "§7");
                return;
            }
        }
    }

    @EventHandler
    public void onAnvil(PrepareAnvilEvent event) {
        for (ItemStack item : event.getInventory().getContents()) {
            if (!ClaimTool.ITEM.equals(item) && !Survival.SPAWNER_MINER.equals(item) && !Survival.PET_TICKET.equals(item))
                continue;

            event.setResult(null);
            return;
        }
    }

    /*


        Player


     */

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        if (event.getCause() != PlayerTeleportEvent.TeleportCause.CHORUS_FRUIT && event.getCause() != PlayerTeleportEvent.TeleportCause.ENDER_PEARL)
            return;

        SurvivalPlayer player = survival.getPlayer(event.getPlayer());

        Claim claim = survival.getClaimHandler().getClaimAt(event.getTo(), false, player.getLastClaim());

        if (claim != null && !claim.canAccess(player, true)) {
            String name = claim.getOwnerName();
            event.setCancelled(true);

            switch (event.getCause()) {
                case ENDER_PEARL:
                    player.getInventory().addItem(new ItemStack(Material.ENDER_PEARL));
                    break;
                case CHORUS_FRUIT:
                    player.getInventory().addItem(new ItemStack(Material.CHORUS_FRUIT));
                    break;
            }
        }
    }

    @EventHandler
    public void onPortal(PlayerPortalEvent event) {
        if (event.getTo() == null || event.getTo().getWorld() == null)
            return;

        if (event.getCause() != PlayerTeleportEvent.TeleportCause.NETHER_PORTAL)
            return;

        if (!survival.canClaimIn(event.getTo().getWorld()))
            return;

        Location to = event.getTo();

//        if (event.useTravelAgent()) {
//            if (!event.getPortalTravelAgent().getCanCreatePortal())
//                return;
//
//            TravelAgent agent = event.getPortalTravelAgent();
//            agent.setCanCreatePortal(false);
//            to = agent.findOrCreate(to);
//            agent.setCanCreatePortal(true);
//        }

        /* Not a new portal */
        if (to.getBlock().getType() == Material.NETHER_PORTAL)
            return;

        SurvivalPlayer player = survival.getPlayer(event.getPlayer());

        if (Region.isInRegion(to)) {
            event.setCancelled(true);
            new ActionBar(player.bukkit(), () -> "§c§l" + player.translate("survival", "player.region.cant_use_portal"), 60).send();
            return;
        }

        Claim claim = survival.getClaimHandler().getClaimAt(to, false, null);

        if (claim != null && !claim.canBuild(player, Material.NETHER_PORTAL)) {
            event.setCancelled(true);
            new ActionBar(player.bukkit(), () -> "§c§l" + player.translate("survival", "player.claim.cant_use_portal"), 60).send();
       }
    }

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked() instanceof ArmorStand)
            onInteract((PlayerInteractEntityEvent) event);
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        boolean petTicket = Survival.PET_TICKET.equals(item);

        if (petTicket) {
            event.setCancelled(true);
            PlayerUtils.updateInventory(event.getPlayer());
        }

        if (!survival.canClaimIn(event.getRightClicked().getWorld()))
            return;

        SurvivalPlayer player = survival.getPlayer(event.getPlayer());
        Entity entity = event.getRightClicked();

        if (entity instanceof Tameable) {
            Tameable tameable = (Tameable) entity;

            if (tameable.isTamed() && tameable.getOwner() != null) {
                UUID owner = tameable.getOwner().getUniqueId();

                boolean isOwner = owner.equals(player.getUUID()) || player.isOpMode();

                if (petTicket) {
                    ItemStackNms nms = survival.getNms().customItem();
                    String storedUuid = nms.getMetaData(item).getAsString("orbitmines_survival", "stored_uuid");

                    if (storedUuid == null) {
                        if (isOwner) {
                            for (ItemStack content : player.getInventory().getContents()) {
                                String contentStoredUuid = nms.getMetaData(content).getAsString("orbitmines_survival", "stored_uuid");

                                if (tameable.getUniqueId().toString().equals(contentStoredUuid)) {
                                    new ActionBar(player, () -> "§c§l" + player.translate("survival", "player.pet_ticket.already_has_ticket", Survival.PET_TICKET.getDisplayName() + "§c§l"), 100).send();
                                    return;
                                }
                            }

                            survival.runAsync(() -> {
                                ItemStack newItem = Survival.PET_TICKET.build();
                                ItemMeta meta = newItem.getItemMeta();

                                List<String> lore = new ArrayList<>();
                                OfflinePlayer ownerPlayer = OfflinePlayer.get(owner);
                                String name = ownerPlayer.getName(Name.RAW_COLORED);
                                lore.add(" §7Owner: " + name);
                                lore.add(" §7Mob: §e" + com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.entities.Mob.from(tameable.getType()).getName());
                                lore.add(" §7Name: §f" + (tameable.getCustomName() != null ? tameable.getCustomName() : "§lUnknown"));
                                meta.setLore(lore);

                                newItem.setItemMeta(meta);

                                newItem = nms.getMetaData(newItem).set("orbitmines_survival", "owner_uuid", owner.toString());
                                newItem = nms.getMetaData(newItem).set("orbitmines_survival", "stored_uuid", tameable.getUniqueId().toString());

                                ItemStack fNewItem = newItem;
                                survival.runSync(() -> player.getInventory().setItemInMainHand(fNewItem));

                                player.sendMessage("Pet Ticket", Color.LIME, "survival", "player.pet_ticket.linked", Survival.PET_TICKET.getDisplayName() + "§7");
                            });
                        } else {
                            survival.runAsync(() -> {
                                OfflinePlayer ownerPlayer = OfflinePlayer.get(owner);
                                String name = ownerPlayer.getName(Name.RAW_COLORED);

                                new ActionBar(player, () -> player.translate("survival", "player.belongs_to", name + "§c§l"), 60).send();
                            });
                        }
                    } else {
                        if (tameable.getUniqueId().toString().equals(storedUuid)) {
                            if (isOwner) {
                                new ActionBar(player, () -> "§c§l" + player.translate("survival", "player.pet_ticket.already_yours"), 60).send();
                            } else {
                                if (survival.getNms().customItem().getMetaData(item).getAsString("orbitmines_survival", "owner_uuid").equals(owner.toString())) {
                                    tameable.setOwner(player.bukkit());
                                    player.getInventory().setItemInMainHand(null);
                                    player.sendMessage("Pet Ticket", Color.LIME, "survival", "player.pet_ticket.new_owner");
                                } else {
                                    player.getInventory().setItemInMainHand(null);
                                    PlayerUtils.updateInventory(player.bukkit());

                                    player.sendMessage("Pet Ticket", Color.LIME, "survival", "player.pet_ticket.expired", Survival.PET_TICKET.getDisplayName() + "§7");
                                }
                            }
                        } else {
                            if (isOwner)
                                new ActionBar(player, () -> "§c§l" + player.translate("survival", "player.pet_ticket.linked_to_other", Survival.PET_TICKET.getDisplayName() + "§c§l"), 100).send();
                            else
                                new ActionBar(player, () -> "§c§l" + player.translate("survival", "player.pet_ticket.not_linked_to", Survival.PET_TICKET.getDisplayName() + "§c§l"), 100).send();
                        }
                    }

                    return;
                }

                if (!isOwner) {
                    survival.runAsync(() -> {
                        OfflinePlayer ownerPlayer = OfflinePlayer.get(owner);
                        String name = ownerPlayer.getName(Name.RAW_COLORED);

                        new ActionBar(player, () -> "§c§l" + player.translate("survival", "player.belongs_to", name + "§c§l"), 60).send();
                    });

                    event.setCancelled(true);
                    return;
                }
            }
        }

        if (entity instanceof ArmorStand || entity instanceof Hanging) {
            if (!survival.getClaimHandler().canBuild(player, entity.getLocation(), Material.ITEM_FRAME)) {
                event.setCancelled(true);
                return;
            }
        }

        if (player.isOpMode())
            return;

        if (entity instanceof Vehicle && entity instanceof InventoryHolder) {
            Claim claim = survival.getClaimHandler().getClaimAt(entity.getLocation(), false, null);

            if (claim != null && !claim.canInteract(player)) {
                event.setCancelled(true);
                return;
            }
        }

        if (entity instanceof Animals || entity.getType() == EntityType.VILLAGER) {
            Claim claim = survival.getClaimHandler().getClaimAt(entity.getLocation(), false, null);

            if (claim != null && !claim.canInteract(player)) {
                event.setCancelled(true);
                return;
            }
        }

        if (entity instanceof Creature && player.getItemInMainHand() != null && player.getItemInMainHand().getType() == Material.LEAD) {
            Claim claim = survival.getClaimHandler().getClaimAt(entity.getLocation(), false, player.getLastClaim());

            if (claim != null && !claim.canInteract(player)) {
                event.setCancelled(true);
                return;
            }
        }

        if (petTicket) {
            new ActionBar(player, () -> "§c§l" + player.translate("survival", "player.pet_ticket.only_tamable"), 100).send();
            PlayerUtils.updateInventory(player.bukkit());
            return;
        }
    }

    @EventHandler
    public void onFish(PlayerFishEvent event) {
        if (event.getCaught() == null)
            return;

        Entity entity = event.getCaught();

        if (!survival.canClaimIn(entity.getWorld()))
            return;

        if (entity instanceof ArmorStand || entity instanceof Animals) {
            SurvivalPlayer player = survival.getPlayer(event.getPlayer());

            Claim claim = survival.getClaimHandler().getClaimAt(entity.getLocation(), false, player.getLastClaim());

            if (claim != null && !claim.canInteract(player)) {
                event.setCancelled(true);
                return;
            }
        }
    }

//    @EventHandler
//    public void onPickup(EntityPickupItemEvent event) {
//        if (!(event.getEntity() instanceof Player))
//            return;
//
//        SurvivalPlayer player = survival.getPlayer((Player) event.getEntity());
//
//        Item item = event.getItem();
//        List<MetadataValue> data = item.getMetadata("SURVIVAL_PROTECTED");
//
//        if (data == null || data.size() == 0)
//            return;
//
//        UUID owner = (UUID) data.get(0).value();
//
//        if (player.getUUID().equals(owner))
//            return;
//
//        SurvivalPlayer ownerPlayer = survival.getPlayer(owner);
//
//        /* Not online, so it can be picked up */
//        if (ownerPlayer == null)
//            return;
//
//        if (ownerPlayer.hasEnabled(Survival.Settings.DROPS_UNLOCKED))
//            return;
//
//        event.setCancelled(true);
//        player.sendMessage(player.lang("Instellingen", "Settings"), Color.RED, "§7Je kan " + ownerPlayer.getName() + "§7 items niet oppakken, dit moet aangezet worden in zijn/haar instellingen.", "§7You can't pickup " + ownerPlayer.getName() + "§7's items, they have to enabled it in their settings.");
//    }

    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        if (!survival.canClaimIn(event.getBlockClicked().getWorld()))
            return;

        SurvivalPlayer player = survival.getPlayer(event.getPlayer());
        Block block = event.getBlockClicked().getRelative(event.getBlockFace());

        if (Region.isInRegion(player, block.getLocation())) {
            event.setCancelled(true);
            return;
        }

        int distance = 3;

        Claim claim = survival.getClaimHandler().getClaimAt(block.getLocation(), false, player.getLastClaim());

        if (claim != null) {
            if (!claim.canBuild(player, Material.WATER)) {
                event.setCancelled(true);
                return;
            }
        }

        if (event.getBucket() != Material.LAVA_BUCKET || player.isOpMode())
            return;

        for (Player p : block.getWorld().getPlayers()) {
            if (!p.equals(player.bukkit()) && p.getGameMode() == GameMode.SURVIVAL && block.getY() >= p.getLocation().getBlockY() - 1 && p.getLocation().distanceSquared(block.getLocation()) < distance * distance) {
                new ActionBar(player, () -> "§c§l" + player.translate("player.lava_bucket_near_other"), 100).send();
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent event) {
        if (!survival.canClaimIn(event.getBlockClicked().getWorld()))
            return;

        SurvivalPlayer player = survival.getPlayer(event.getPlayer());
        Block block = event.getBlockClicked();

        /* Milking Cow */
        if (block.getType() == Material.AIR || block.getType().isSolid())
            return;

        if (Region.isInRegion(player, block.getLocation()) || !survival.getClaimHandler().canBuild(player, block.getLocation(), Material.AIR))
            event.setCancelled(true);
    }
    
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        
        if (action == Action.LEFT_CLICK_AIR || action == Action.PHYSICAL)
            return;

        if (!survival.canClaimIn(event.getPlayer().getWorld()))
            return;

        Block block = event.getClickedBlock();
        if (block == null)
            return;

        SurvivalPlayer player = survival.getPlayer(event.getPlayer());
        Material blockType = block.getType();

        /* Put out Fires */
        if (action == Action.LEFT_CLICK_BLOCK) {
            if (block.getY() < block.getWorld().getMaxHeight() - 1 || event.getBlockFace() != BlockFace.UP) {

                Block adjacent = block.getRelative(event.getBlockFace());
                byte lightLevel = adjacent.getLightFromBlocks();

                if (lightLevel == 15 && adjacent.getType() == Material.FIRE) {
                    if (Region.isInRegion(player, block.getLocation())) {
                        event.setCancelled(true);
                        player.sendBlockChange(adjacent.getLocation(), adjacent.getBlockData());
                        return;
                    }

                    Claim claim = survival.getClaimHandler().getClaimAt(block.getLocation(), false, player.getLastClaim());
                    player.setLastClaim(claim);

                    if (claim != null && !claim.canBuild(player, Material.AIR)) {
                        event.setCancelled(true);
                        player.sendBlockChange(adjacent.getLocation(), adjacent.getBlockData());
                        return;
                    }
                }
            }
        }

        if (action == Action.RIGHT_CLICK_BLOCK && block.getState() instanceof InventoryHolder)
            blockType = Material.CAULDRON;

        switch (blockType) {
            /* Interact */
            case CAULDRON:
            case JUKEBOX:
            case ANVIL:
            case CHIPPED_ANVIL:
            case DAMAGED_ANVIL:
            case CAKE: {
                Claim claim = survival.getClaimHandler().getClaimAt(block.getLocation(), false, player.getLastClaim());
                player.setLastClaim(claim);

                if (claim != null && !claim.canInteract(player))
                    event.setCancelled(true);

                return;
            }
            /* Access */
            case STONE_BUTTON:

            case OAK_BUTTON:
            case ACACIA_BUTTON:
            case BIRCH_BUTTON:
            case JUNGLE_BUTTON:
            case SPRUCE_BUTTON:
            case DARK_OAK_BUTTON:

            case LEVER:

            case OAK_DOOR:
            case ACACIA_DOOR:
            case BIRCH_DOOR:
            case JUNGLE_DOOR:
            case SPRUCE_DOOR:
            case DARK_OAK_DOOR:

            case WHITE_BED:
            case ORANGE_BED:
            case MAGENTA_BED:
            case LIGHT_BLUE_BED:
            case YELLOW_BED:
            case LIME_BED:
            case PINK_BED:
            case GRAY_BED:
            case LIGHT_GRAY_BED:
            case CYAN_BED:
            case PURPLE_BED:
            case BLUE_BED:
            case BROWN_BED:
            case GREEN_BED:
            case RED_BED:
            case BLACK_BED:

            case OAK_TRAPDOOR:
            case ACACIA_TRAPDOOR:
            case BIRCH_TRAPDOOR:
            case JUNGLE_TRAPDOOR:
            case SPRUCE_TRAPDOOR:
            case DARK_OAK_TRAPDOOR:

            case OAK_FENCE_GATE:
            case ACACIA_FENCE_GATE:
            case BIRCH_FENCE_GATE:
            case JUNGLE_FENCE_GATE:
            case SPRUCE_FENCE_GATE:
            case DARK_OAK_FENCE_GATE: {

                Claim claim = survival.getClaimHandler().getClaimAt(block.getLocation(), false, player.getLastClaim());
                player.setLastClaim(claim);

                if (claim != null && !claim.canAccess(player, true))
                    event.setCancelled(true);

                return;
            }
            /* Build */
            case NOTE_BLOCK:
            case REPEATER:
            case DRAGON_EGG:
            case DAYLIGHT_DETECTOR:
            case COMPARATOR:
            case FLOWER_POT: {

                Claim claim = survival.getClaimHandler().getClaimAt(block.getLocation(), false, player.getLastClaim());
                player.setLastClaim(claim);

                if (claim != null && !claim.canBuild(player, blockType))
                    event.setCancelled(true);

                return;
            }
        }

        /* Right Click with item */
        if (action != Action.RIGHT_CLICK_BLOCK && action != Action.RIGHT_CLICK_AIR)
            return;

        ItemStack item = event.getHand() == EquipmentSlot.HAND ? player.getItemInMainHand() : player.getItemInOffHand();

        switch (item.getType()) {
            /* Build */
            case INK_SAC:
            case BLACK_DYE:
            case BLUE_DYE:
            case BROWN_DYE:
            case RED_DYE:
            case GREEN_DYE:
            case COCOA_BEANS:
            case LAPIS_LAZULI:
            case PURPLE_DYE:
            case CYAN_DYE:
            case LIGHT_GRAY_DYE:
            case GRAY_DYE:
            case PINK_DYE:
            case LIME_DYE:
            case DANDELION:
            case YELLOW_DYE:
            case LIGHT_BLUE_DYE:
            case MAGENTA_DYE:
            case ORANGE_DYE:
            case BONE_MEAL:
            case WHITE_DYE:

            case ARMOR_STAND:

            case END_CRYSTAL:

            case OAK_BOAT:
            case ACACIA_BOAT:
            case BIRCH_BOAT:
            case DARK_OAK_BOAT:
            case JUNGLE_BOAT:
            case SPRUCE_BOAT:

            case MINECART:
            case CHEST_MINECART:
            case COMMAND_BLOCK_MINECART:
            case FURNACE_MINECART:
            case TNT_MINECART:
            case HOPPER_MINECART: {
                Claim claim = survival.getClaimHandler().getClaimAt(block.getLocation(), false, player.getLastClaim());
                player.setLastClaim(claim);

                if (claim != null && !claim.canBuild(player, item.getType()))
                    event.setCancelled(true);

                return;
            }
        }

        if (ItemUtils.EGGS.contains(item.getType())) {
            Claim claim = survival.getClaimHandler().getClaimAt(block.getLocation(), false, player.getLastClaim());
            player.setLastClaim(claim);

            if (claim != null && !claim.canBuild(player, item.getType()))
                event.setCancelled(true);
        } else if (item.getType() == Material.FLINT_AND_STEEL) {
            if (player.isOpMode())
                return;

            for (Player p : PlayerUtils.getNearbyPlayers(event.getClickedBlock().getRelative(BlockFace.UP).getLocation().add(0.5, 0, 0.5), 1.5)) {
                if (p == event.getPlayer())
                    continue;

                new ActionBar(player, () -> "§c§l" + player.translate("survival", "player.flint_and_steal_near_other"), 100).send();
                event.setCancelled(true);
                return;
            }
        }
    }

    /*


        Blocks


     */

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();

        if (!survival.canClaimIn(p.getWorld()))
            return;

        Block block = event.getBlock();
        SurvivalPlayer player = survival.getPlayer(p);

        if (Region.isInRegion(player, block.getLocation()) || !survival.getClaimHandler().canBuild(player, block.getLocation(), block.getType()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player p = event.getPlayer();

        if (!survival.canClaimIn(p.getWorld()))
            return;

        Block block = event.getBlock();
        SurvivalPlayer player = survival.getPlayer(p);

        if (Region.isInRegion(player, block.getLocation()) || !survival.getClaimHandler().canBuild(player, block.getLocation(), event.getBlockReplacedState().getType())) {
            event.setCancelled(true);
            player.updateInventory();
        }
    }

    @EventHandler
    public void onMultiPlace(BlockMultiPlaceEvent event) {
        Player p = event.getPlayer();

        if (!survival.canClaimIn(p.getWorld()))
            return;

        SurvivalPlayer player = survival.getPlayer(p);

        for (BlockState block : event.getReplacedBlockStates()) {
            if (Region.isInRegion(player, block.getLocation()) || !survival.getClaimHandler().canBuild(player, block.getLocation(), event.getBlockReplacedState().getType())) {
                event.setCancelled(true);
                player.updateInventory();

                return;
            }
        }
    }

    @EventHandler
    public void onPisonExtend(BlockPistonExtendEvent event) {
        if (event.getDirection() == BlockFace.DOWN)
            return;

        if (!survival.canClaimIn(event.getBlock().getWorld()))
            return;

        Block piston = event.getBlock();
        List<Block> blocks = event.getBlocks();

        if (blocks.size() == 0) {
            Block invaded = piston.getRelative(event.getDirection());

            if (invaded.getType() == Material.AIR)
                return;

            if (survival.getClaimHandler().getClaimAt(piston.getLocation(), false, null) == null && (Region.isInRegion(invaded.getLocation()) || survival.getClaimHandler().getClaimAt(invaded.getLocation(), false, null) != null))
                event.setCancelled(true);

            return;
        }

        String pistonOwner = "";
        Claim claim = survival.getClaimHandler().getClaimAt(piston.getLocation(), false, null);

        if (claim != null)
            pistonOwner = claim.getOwnerName();

        /* Check what blocks are being pushed */
        Claim cached = claim;
        for (Block block : blocks) {
            /* If any blocks that are being pushed are in a region, or in someone else's claim */

            if (Region.isInRegion(block.getLocation())) {
                event.setCancelled(true);
                destroyPiston(piston, piston.getType());
                return;
            }

            claim = survival.getClaimHandler().getClaimAt(block.getLocation(), false, cached);

            if (claim == null)
                continue;

            cached = claim;

            if (claim.getOwnerName().equals(pistonOwner))
                continue;

            event.setCancelled(true);
            destroyPiston(piston, piston.getType());
            return;
        }

        /* If any blocks are pushed into a claim */
        for (Block block : blocks) {
            claim = survival.getClaimHandler().getClaimAt(block.getLocation(), false, cached);

            String ownerName = "";
            if (claim != null) {
                cached = claim;
                ownerName = claim.getOwnerName();
            }

            Claim next = survival.getClaimHandler().getClaimAt(block.getRelative(event.getDirection()).getLocation(), false, cached);

            String nextOwnerName = "";
            if (next != null)
                nextOwnerName = next.getOwnerName();

            if (!nextOwnerName.equals(ownerName) && !nextOwnerName.isEmpty()) {
                event.setCancelled(true);
                destroyPiston(piston, piston.getType());
            }
        }
    }

    @EventHandler
    public void onPistonRetract(BlockPistonRetractEvent event) {
        if (event.getDirection() == BlockFace.UP)
            return;

        if (!survival.canClaimIn(event.getBlock().getWorld()))
            return;

        Block piston = event.getBlock();

        String pistomOwner = "";
        Claim claim = survival.getClaimHandler().getClaimAt(piston.getLocation(), false, null);

        if (claim != null)
            pistomOwner = claim.getOwnerName();

        for (Block block : event.getBlocks()) {
            if (Region.isInRegion(block.getLocation())) {
                event.setCancelled(true);
                destroyPiston(piston, Material.STICKY_PISTON);
                return;
            }

            Claim next = survival.getClaimHandler().getClaimAt(block.getLocation(), false, claim);

            String nextOwnerName = "";
            if (next != null)
                nextOwnerName = next.getOwnerName();

            if (!nextOwnerName.equals(pistomOwner)) {
                event.setCancelled(true);
                destroyPiston(piston, Material.PISTON);
                return;
            }
        }
    }

    private void destroyPiston(Block piston, Material material) {
        piston.getWorld().createExplosion(piston.getLocation(), 0);
        piston.getWorld().dropItem(piston.getLocation(), new ItemStack(material));
        piston.setType(Material.AIR);
    }

    @EventHandler
    public void onSpread(BlockSpreadEvent event) {
        if (event.getSource().getType() != Material.FIRE)
            return;

        if (!survival.canClaimIn(event.getBlock().getWorld()))
            return;

        if (Region.isInRegion(event.getBlock().getLocation()) || survival.getClaimHandler().getClaimAt(event.getBlock().getLocation(), false, null) != null)
            event.setCancelled(true);
    }

    @EventHandler
    public void onBurn(BlockBurnEvent event) {
        if (!survival.canClaimIn(event.getBlock().getWorld()))
            return;

        if (Region.isInRegion(event.getBlock().getLocation()) || survival.getClaimHandler().getClaimAt(event.getBlock().getLocation(), false, null) != null)
            event.setCancelled(true);
    }

    private Claim lastSpread = null;

    @EventHandler
    public void onFromTo(BlockFromToEvent event) {
        if (event.getFace() == BlockFace.DOWN)
            return;

        if (!survival.canClaimIn(event.getBlock().getWorld()))
            return;

        Block to = event.getToBlock();

        if (Region.isInRegion(to.getLocation())) {
            event.setCancelled(true);
            return;
        }

        Claim claim = survival.getClaimHandler().getClaimAt(to.getLocation(), false, lastSpread);

        if (claim == null)
            return;

        this.lastSpread = claim;

        if (claim.inClaim(event.getBlock().getLocation(), false))
            return;

        if (!claim.inClaim(event.getBlock().getLocation(), false))
            event.setCancelled(true);
    }

    @EventHandler
    public void onDispense(BlockDispenseEvent event) {
        if (!survival.canClaimIn(event.getBlock().getWorld()))
            return;

        Block from = event.getBlock();
        org.bukkit.block.data.type.Dispenser dispenser = (org.bukkit.block.data.type.Dispenser) from.getBlockData();

        Block to = from.getRelative(dispenser.getFacing());

        if (Region.isInRegion(to.getLocation())) {
            event.setCancelled(true);
            return;
        }

        Claim fromClaim = survival.getClaimHandler().getClaimAt(from.getLocation(), false, null);
        Claim toClaim = survival.getClaimHandler().getClaimAt(to.getLocation(), false, fromClaim);

        if (fromClaim == null && toClaim == null)
            return;

        if (fromClaim == toClaim)
            return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onTreeGrow(StructureGrowEvent event) {
        if (!survival.canClaimIn(event.getWorld()))
            return;

        Location root = event.getLocation();
        Claim claim = survival.getClaimHandler().getClaimAt(root, false, null);

        String ownerName = null;

        if (claim != null) {
            ownerName = claim.getOwnerName();
        }

        for (BlockState block : new ArrayList<>(event.getBlocks())) {
            if (Region.isInRegion(block.getLocation())) {
                event.getBlocks().remove(block);
                continue;
            }

            Claim blockClaim = survival.getClaimHandler().getClaimAt(block.getLocation(), false, claim);

            if (blockClaim != null && (ownerName == null || !ownerName.equals(blockClaim.getOwnerName())))
                event.getBlocks().remove(block);
        }
    }

//    @EventHandler
//    public void onPickupItem(InventoryPickupItemEvent event) {
//        /* Prevent hoppers from picking up items that are protected */
//        InventoryHolder holder = event.getInventory().getHolder();
//
//        if (holder instanceof HopperMinecart || holder instanceof Hopper) {
//            Item item = event.getItem();
//            List<MetadataValue> data = item.getMetadata("SURVIVAL_PROTECTED");
//
//            if (data != null && data.size() > 0)
//                event.setCancelled(true);
//        }
//    }

//    @EventHandler
//    public void onItemSpawn(ItemSpawnEvent event) {
//        List<ItemProtection> items = ItemProtection.getItems();
//
//        Item item = event.getEntity();
//        long now = System.currentTimeMillis();
//
//        for (ItemProtection protection : new ArrayList<>(items)) {
//            if (protection.getExpirationTimestamp() < now) {
//                items.remove(protection);
//                continue;
//            }
//
//            if (protection.getItemStack().getAmount() != item.getItemStack().getAmount() || protection.getItemStack().getType() != item.getItemStack().getType())
//                continue;
//
//            Location spawn = event.getLocation();
//            Location expected = protection.getLocation();
//
//            if (!spawn.getWorld().equals(expected.getWorld()) ||
//                    spawn.getX() < expected.getX() - 5 ||
//                    spawn.getX() > expected.getX() + 5 ||
//                    spawn.getZ() < expected.getZ() - 5 ||
//                    spawn.getZ() > expected.getZ() + 5 ||
//                    spawn.getY() < expected.getY() - 15 ||
//                    spawn.getY() > expected.getY() + 3)
//                continue;
//
//            item.setMetadata("SURVIVAL_PROTECTED", new FixedMetadataValue(survival, protection.getOfflineOwner()));
//
//            items.remove(protection);
//            break;
//        }
//    }

    @EventHandler
    public void onHangingBreak(HangingBreakEvent e) {
        if (!survival.canClaimIn(e.getEntity().getWorld()))
            return;

        if (e.getCause() == HangingBreakEvent.RemoveCause.EXPLOSION) {
            e.setCancelled(true);
            return;
        }

        if (!(e instanceof HangingBreakByEntityEvent)) {
            e.setCancelled(true);
            return;
        }

        HangingBreakByEntityEvent event = (HangingBreakByEntityEvent) e;

        if (!(event.getRemover() instanceof Player)) {
            event.setCancelled(true);
            return;
        }

        SurvivalPlayer player = survival.getPlayer((Player) event.getRemover());

        if (Region.isInRegion(player, event.getEntity().getLocation()) || !survival.getClaimHandler().canBuild(player, event.getEntity().getLocation(), Material.AIR))
            event.setCancelled(true);
    }

    @EventHandler
    public void onHangingPlace(HangingPlaceEvent event) {
        if (!survival.canClaimIn(event.getEntity().getWorld()))
            return;

        SurvivalPlayer player = survival.getPlayer(event.getPlayer());

        if (Region.isInRegion(player, event.getEntity().getLocation()) || !survival.getClaimHandler().canBuild(player, event.getEntity().getLocation(), Material.PAINTING))
            event.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        handleEntityDamageEvent(event);
    }

    @EventHandler
    public void onCombust(EntityCombustByEntityEvent event) {
        EntityDamageByEntityEvent e = new EntityDamageByEntityEvent(event.getCombuster(), event.getEntity(), EntityDamageEvent.DamageCause.FIRE_TICK, event.getDuration());
        handleEntityDamageEvent(e);
        event.setCancelled(e.isCancelled());
    }

    private void handleEntityDamageEvent(EntityDamageEvent e) {
        if (isMonster(e.getEntity()))
            return;

//        if (e.getEntityType() == EntityType.DROPPED_ITEM && e.getEntity().hasMetadata("SURVIVAL_PROTECTED")) {
//            e.setCancelled(true);
//            return;
//        }

        if (e.getCause() != null && e.getEntity() instanceof Tameable) {
            Tameable tameable = (Tameable) e.getEntity();

            if (tameable.isTamed()) {

                switch (e.getCause()) {
                    case ENTITY_EXPLOSION:
                    case FALLING_BLOCK:
                    case FIRE:
                    case FIRE_TICK:
                    case LAVA:
                    case SUFFOCATION:
                        e.setCancelled(true);
                        break;
                }
            }
        }

        if (!survival.canClaimIn(e.getEntity().getWorld()))
            return;

        if (!(e instanceof EntityDamageByEntityEvent))
            return;

        EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) e;

        Player attacker = null;
        Projectile arrow = null;
        Entity damager = event.getDamager();

        if (damager != null) {
            if (damager instanceof Player) {
                attacker = (Player) damager;
            } else if (damager instanceof Projectile) {
                arrow = (Projectile) damager;

                if (arrow.getShooter() instanceof Player)
                    attacker = (Player) arrow.getShooter();
            }
        }

        switch (event.getEntityType()) {
            case ITEM_FRAME:
            case ARMOR_STAND:
            case VILLAGER:
            case ENDER_CRYSTAL: {

                SurvivalPlayer player = null;
                if (attacker != null)
                    player = survival.getPlayer(attacker);

                Claim claim = survival.getClaimHandler().getClaimAt(event.getEntity().getLocation(), false, player == null ? null : player.getLastClaim());

                if (claim != null) {
                    if (player == null) {
                        if (event.getEntity() instanceof Villager && damager instanceof Zombie)
                            return;

                        event.setCancelled(true);
                        return;
                    }

                    if (Region.isInRegion(player, event.getEntity().getLocation()) || !claim.canBuild(player, Material.AIR))
                        event.setCancelled(true);
                }

                break;
            }
        }

        if (event.getEntity() instanceof Creature || event.getEntity() instanceof WaterMob) {
            if (event.getEntity() instanceof Tameable) {
                Tameable tameable = (Tameable) event.getEntity();

                if (attacker != null && tameable.isTamed() && tameable.getOwner() != null) {
                    SurvivalPlayer player = survival.getPlayer(attacker);
                    UUID owner = tameable.getOwner().getUniqueId();

                    if (player.getUUID().equals(owner))
                        return;

                    if (player.isOpMode())
                        return;

                    survival.runAsync(() -> {
                        OfflinePlayer ownerPlayer = OfflinePlayer.get(owner);
                        String name = ownerPlayer.getName(Name.RAW_COLORED);

                        new ActionBar(player, () -> "§c§l" + player.translate("survival", "player.belongs_to", name + "§c§l"), 60).send();
                    });

                    event.setCancelled(true);
                    return;
                }
            }

            if (attacker == null
                    && damager != null
                    && damager.getType() != EntityType.CREEPER
                    && damager.getType() != EntityType.WITHER
                    && damager.getType() != EntityType.ENDER_CRYSTAL
                    && damager.getType() != EntityType.AREA_EFFECT_CLOUD
                    && damager.getType() != EntityType.WITCH
                    && !(damager instanceof Projectile)
                    && !(damager instanceof Explosive)
                    && !(damager instanceof ExplosiveMinecart))
                return;

            SurvivalPlayer player = attacker == null ? null : survival.getPlayer(attacker);

            Claim claim = survival.getClaimHandler().getClaimAt(event.getEntity().getLocation(), false, player == null ? null : player.getLastClaim());

            if (claim != null) {
                if (player == null) {
                    if (event.getEntityType() == EntityType.VILLAGER && damager != null && (damager.getType() == EntityType.ZOMBIE || damager.getType() == EntityType.VINDICATOR || damager.getType() == EntityType.EVOKER || damager.getType() == EntityType.EVOKER_FANGS || damager.getType() == EntityType.VEX))
                        return;

                    event.setCancelled(true);

                    if (damager instanceof Projectile)
                        damager.remove();

                    return;
                }

                if (!claim.canInteract(player)) {
                    event.setCancelled(true);

                    if (arrow != null)
                        arrow.remove();

                    survival.runAsync(() -> {
                        OfflinePlayer ownerPlayer = OfflinePlayer.get(claim.getOwner());
                        String name = ownerPlayer.getName(Name.RAW_COLORED);

                        new ActionBar(player, () -> "§c§l" + player.translate("survival", "player.belongs_to", name + "§c§l"), 60).send();
                    });
                }

                player.setLastClaim(claim);
            }
        }
    }

    private boolean isMonster(Entity entity) {
        if (entity instanceof Monster) return true;

        EntityType type = entity.getType();
        if (type == EntityType.GHAST || type == EntityType.MAGMA_CUBE || type == EntityType.SHULKER || type == EntityType.POLAR_BEAR)
            return true;

        if (type == EntityType.RABBIT) {
            Rabbit rabbit = (Rabbit) entity;

            if (rabbit.getRabbitType() == Rabbit.Type.THE_KILLER_BUNNY)
                return true;
        }

        return false;
    }

    @EventHandler
    public void onVehicleDamage(VehicleDamageEvent event) {
        if (event.getVehicle() == null)
            return;

        if (!survival.canClaimIn(event.getVehicle().getWorld()))
            return;

        Player attacker = null;
        Entity damager = event.getAttacker();
        EntityType damagerType = null;

        if (damager != null) {
            damagerType = damager.getType();

            if (damager.getType() == EntityType.PLAYER) {
                attacker = (Player) damager;
            } else if (damager instanceof Projectile) {
                Projectile arrow = (Projectile) damager;

                if (arrow.getShooter() instanceof Player)
                    attacker = (Player) arrow.getShooter();
            }
        }

        if (attacker == null && damagerType != EntityType.CREEPER && damagerType != EntityType.WITHER && damagerType != EntityType.PRIMED_TNT)
            return;

        SurvivalPlayer player = attacker == null ? null : survival.getPlayer(attacker);

        Claim claim = survival.getClaimHandler().getClaimAt(event.getVehicle().getLocation(), false, player == null ? null : player.getLastClaim());

        if (claim != null) {
            if (player == null) {
                event.setCancelled(true);
                return;
            }

            if (!claim.canInteract(player)) {
                event.setCancelled(true);

                survival.runAsync(() -> {
                    OfflinePlayer ownerPlayer = OfflinePlayer.get(claim.getOwner());
                    String name = ownerPlayer.getName(Name.RAW_COLORED);

                    new ActionBar(player, () -> "§c§l" + player.translate("survival", "player.belongs_to", name + "§c§l"), 60).send();
                });
            }

            player.setLastClaim(claim);
        }
    }

    @EventHandler
    public void onPotionSplash(PotionSplashEvent event) {
        ThrownPotion potion = event.getPotion();

        ProjectileSource projectileSource = potion.getShooter();
        if (projectileSource == null)
            return;

        SurvivalPlayer thrower = !(projectileSource instanceof Player) ? null : survival.getPlayer((Player) projectileSource);

        for (PotionEffect effect : potion.getEffects()) {
            PotionEffectType effectType = effect.getType();

            /* Prevent griefers from stealing animals & villagers / killing them */
            if (effectType == PotionEffectType.JUMP || effectType == PotionEffectType.POISON) {
                Claim cached = null;

                for (LivingEntity effected : event.getAffectedEntities()) {
                    if (effected.getType() == EntityType.VILLAGER || effect instanceof Animals) {
                        Claim claim = survival.getClaimHandler().getClaimAt(effected.getLocation(), false, cached);

                        if (claim != null) {
                            cached = claim;

                            if (thrower == null || !claim.canInteract(thrower)) {
                                event.setIntensity(effected, 0);

                                if (thrower != null) {
                                    survival.runAsync(() -> {
                                        OfflinePlayer ownerPlayer = OfflinePlayer.get(claim.getOwner());
                                        String name = ownerPlayer.getName(Name.RAW_COLORED);

                                        new ActionBar(thrower, () -> "§c§l" + thrower.translate("survival", "player.belongs_to", name + "§c§l"), 60).send();
                                    });
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /*


        Entities


     */

    @EventHandler
    public void onEntityBlockForm(EntityBlockFormEvent event) {
        if (!survival.canClaimIn(event.getBlock().getWorld()))
            return;

        if (!(event.getEntity() instanceof Player))
            return;

        SurvivalPlayer player = survival.getPlayer((Player) event.getEntity());

        if (Region.isInRegion(player, event.getBlock().getLocation()) || !survival.getClaimHandler().canBuild(player, event.getBlock().getLocation(), event.getNewState().getType()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        if (!survival.canClaimIn(event.getBlock().getWorld()))
            return;

        Block block = event.getBlock();

        if (event.getEntity() instanceof Wither) {
            /* Wither breaking blocks */

            if (Region.isInRegion(block.getLocation()) || survival.getClaimHandler().getClaimAt(block.getLocation(), false, null) != null)
                event.setCancelled(true);

        } else if (event.getTo() == Material.DIRT && block.getType() == Material.FARMLAND) {

            /* Trampled crops */
            if (!(event.getEntity() instanceof Player)) {
                if (Region.isInRegion(block.getLocation()) || survival.getClaimHandler().getClaimAt(block.getLocation(), false, null) != null)
                    event.setCancelled(true);
            } else {
                SurvivalPlayer player = survival.getPlayer((Player) event.getEntity());

                if (Region.isInRegion(player, block.getLocation()) || !survival.getClaimHandler().canBuild(player, block.getLocation(), block.getType()))
                    event.setCancelled(true);
            }
        } else if (event.getEntity() instanceof FallingBlock) {
            /* Sand Cannon Fix */

            FallingBlock entity = (FallingBlock) event.getEntity();

            if (event.getTo() == Material.AIR) {
                entity.setMetadata("SURVIVAL_FALLINGBLOCK", new FixedMetadataValue(survival, block.getLocation()));
            } else {
                List<MetadataValue> values = entity.getMetadata("SURVIVAL_FALLINGBLOCK");

                if (values.size() < 1)
                    return;

                Location start = (Location) values.get(0).value();
                Location next = block.getLocation();

                if (start.getBlockX() != next.getBlockX() || start.getBlockZ() != next.getBlockZ()) {
                    Claim claim = survival.getClaimHandler().getClaimAt(next, false, null);

                    if (Region.isInRegion(next) || !claim.inClaim(start, false)) {
                        event.setCancelled(true);

                        ItemStack itemStack = new ItemStack(entity.getBlockData().getMaterial(), 1);
                        Item item = block.getWorld().dropItem(entity.getLocation(), itemStack);
                        item.setVelocity(new Vector());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPortalEnter(EntityPortalEnterEvent event) {
        if (!(event.getEntity() instanceof FallingBlock))
            return;

        event.getEntity().removeMetadata("SURVIVAL_FALLINGBLOCK", survival);
    }

    @EventHandler
    public void onPortalExit(EntityPortalExitEvent event) {
        if (!(event.getEntity() instanceof TNTPrimed) || event.getTo().getWorld().getEnvironment() != World.Environment.THE_END)
            return;

        event.getEntity().remove();
    }

    @EventHandler
    public void onInteract(EntityInteractEvent event) {
        if (event.getBlock().getType() != Material.FARMLAND)
            return;

        if (!survival.canClaimIn(event.getBlock().getWorld()))
            return;

        for (Entity passenger : event.getEntity().getPassengers()) {
            if (!(passenger instanceof Player))
                continue;

            SurvivalPlayer player = survival.getPlayer((Player) passenger);

            if (Region.isInRegion(player, event.getBlock().getLocation()) || !survival.getClaimHandler().canBuild(player, event.getBlock().getLocation(), event.getBlock().getRelative(BlockFace.UP).getType())) {
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        handleExplosion(/*event.getEntity(), */event.getLocation(), event.blockList());
    }

    @EventHandler
    public void onExplode(BlockExplodeEvent event) {
        handleExplosion(/*null, */event.getBlock().getLocation(), event.blockList());
    }

//    @EventHandler
//    public void onIgnite(BlockIgniteEvent event) {
//        if (!(event.getIgnitingEntity() instanceof TNTPrimed))
//            return;
//
//        if (event.getPlayer() != null) {
//            SurvivalPlayer player = SurvivalPlayer.getPlayer(event.getPlayer());
//            tnt.put((TNTPrimed) event.getIgnitingEntity(), player);
//        } else if (toIgnite.containsKey(event.getIgnitingBlock())) {
//            tnt.put((TNTPrimed) event.getIgnitingEntity(), toIgnite.get(event.getIgnitingBlock()));
//            toIgnite.remove(event.getIgnitingBlock());
//        }
//    }

//    private Map<TNTPrimed, SurvivalPlayer> tnt = new HashMap<>();
//    private Map<Block, SurvivalPlayer> toIgnite = new HashMap<>();

    private void handleExplosion(/*Entity en, */Location location, List<Block> blocks) {
        if (!survival.canClaimIn(location.getWorld()))
            return;

        Claim cached = null;

        loop:
        for (Block block : new ArrayList<>(blocks)) {
            if (block.getType() == Material.AIR)
                continue;

            if (Region.isInRegion(block.getLocation())) {
                blocks.remove(block);
                continue;
            }

            Claim claim = survival.getClaimHandler().getClaimAt(block.getLocation(), true, cached);

            if (claim != null) {
                cached = claim;

//                if (en != null) {
//                    if (en instanceof Creeper) {
//                        for (Entity entity : en.getNearbyEntities(10, 10, 10)) {
//                            if (!(entity instanceof Player))
//                                continue;
//
//                            Player player = (Player) entity;
//
//                            if (claim.getPermission(player.getUniqueId()) != Claim.Permission.BUILD) {
//                                blocks.remove(block);
//                                continue loop;
//                            }
//                        }
//
//                        continue;
//                    } else if (en instanceof TNTPrimed && this.tnt.containsKey(en)) {
//                        if (claim.canBuild(this.tnt.get(en), Material.TNT)) {
//                            blocks.remove(block);
//                        } else {
//                            if (block.getType() == Material.TNT)
//                                this.toIgnite.put(block, this.tnt.get(en));
//
//                            continue;
//                        }
//                    }
//                }

                blocks.remove(block);
            }
        }

//        if (en instanceof TNTPrimed)
//            tnt.remove(en);
    }
}
