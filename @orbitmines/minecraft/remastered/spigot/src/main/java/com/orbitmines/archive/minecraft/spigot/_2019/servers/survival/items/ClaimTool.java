package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.items;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.claim.Visualization;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.database.models.Claim;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.gui.claim.ClaimGUI;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.scoreboard.ClaimScoreboard;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.scoreboard.SurvivalScoreboard;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.BlockUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.LocationUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.PlayerUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.ActionBar;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.item_handlers.ItemHoverActionBar;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.item_handlers.ItemInteraction;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Set;
import java.util.UUID;

public class ClaimTool extends ItemInteraction<SurvivalPlayer> {

    public static ItemBuilder ITEM = new ItemBuilder(Material.STONE_HOE, 1, "§a§lClaiming Tool").addFlag(ItemFlag.HIDE_ATTRIBUTES).addFlag(ItemFlag.HIDE_UNBREAKABLE).unbreakable(true);

    private final Survival survival;

    public ClaimTool(Survival survival) {
        super(ITEM);

        this.survival = survival;
    }

    @Override
    public void onInteract(SurvivalPlayer player, PlayerInteractEvent event, ItemStack itemStack) {
        event.setCancelled(true);
    }

    @Override
    public void onLeftClick(SurvivalPlayer player, PlayerInteractEvent event, ItemStack itemStack) {
        if (!survival.canClaimIn(player.getWorld()))
            return;

        if (player.isSneaking()) {
            /* Show all claims nearby */
            Set<Claim> claims = survival.getClaimHandler().getNearbyClaims(player.getLocation());

            Visualization.show(player, claims, player.getEyeLocation().getBlockY(), Visualization.Type.CLAIM, player.getLocation());

            new ActionBar(player.bukkit(), () -> "§a§l" + player.translate("survival", "player.claim.nearby_claims", claims.size() + " " + (claims.size() == 1 ? "Claim" : "Claims")), 60).send();
        } else {
            /* Show current claim information */
            Block block = event.getClickedBlock();
            if (event.getAction() == Action.LEFT_CLICK_AIR)
                block = PlayerUtils.getTargetBlock(player.bukkit(), 100);

            if (block == null)
                return;

            if (block.getType() == Material.AIR) {
                new ActionBar(player.bukkit(), () -> "§c§l" + player.translate("survival", "player.claim.too_far_away"), 60).send();

                Visualization.revert(player);
                return;
            }

            Claim claim = survival.getClaimHandler().getClaimAt(block.getLocation(), false, player.getLastClaim());

            if (claim == null) {
                new ActionBar(player.bukkit(), () -> "§c§l" + player.translate("survival", "player.claim.block_not_claimed"), 60).send();

                Visualization.revert(player);
            } else {
                player.setLastClaim(claim);

                if (claim.getOwner() != null && (claim.getOwner().equals(player.getUUID()) || survival.canEditOtherClaims(player))) {
                    new ClaimGUI(player, claim, survival).open();
                } else {
                    String name = claim.getOwnerName();
                    new ActionBar(player.bukkit(), () -> "§a§l" + player.translate("survival", "player.claim.claimed_by", name + "§a§l"), 60).send();

                    Visualization.show(player, claim, player.getEyeLocation().getBlockY(), Visualization.Type.CLAIM, player.getLocation());
                }
            }
        }
    }

    @Override
    public void onRightClick(SurvivalPlayer player, PlayerInteractEvent event, ItemStack itemStack) {
        if (!survival.canClaimIn(player.getWorld()))
            return;

        Block block = event.getClickedBlock();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK)
            block = PlayerUtils.getTargetBlock(player.bukkit(), 100);

        if (block == null)
            return;

        if (block.getType() == Material.AIR) {
            new ActionBar(player.bukkit(), () -> "§c§l" + player.translate("survival", "player.claim.too_far_away"), 60).send();
            return;
        }

        if (survival.canEditOtherClaims(player))
            return;

        if (player.getResizingClaim() != null && player.getResizingClaim().isInserted() && !player.getResizingClaim().isDestroyed()) {
            /* Already resizing exisiting claim */

            if (BlockUtils.equals(block, player.getLastClaimToolLocation().getBlock()))
                return;

            int newX1, newX2, newZ1, newZ2, newY1, newY2;

            if (player.getLastClaimToolLocation().getBlockX() == player.getResizingClaim().getCorner1().getBlockX())
                newX1 = block.getX();
            else
                newX1 = player.getResizingClaim().getCorner1().getBlockX();

            if (player.getLastClaimToolLocation().getBlockX() == player.getResizingClaim().getCorner2().getBlockX())
                newX2 = block.getX();
            else
                newX2 = player.getResizingClaim().getCorner2().getBlockX();

            if (player.getLastClaimToolLocation().getBlockZ() == player.getResizingClaim().getCorner1().getBlockZ())
                newZ1 = block.getZ();
            else
                newZ1 = player.getResizingClaim().getCorner1().getBlockZ();

            if (player.getLastClaimToolLocation().getBlockZ() == player.getResizingClaim().getCorner2().getBlockZ())
                newZ2 = block.getZ();
            else
                newZ2 = player.getResizingClaim().getCorner2().getBlockZ();

            newY1 = player.getResizingClaim().getCorner1().getBlockY();
            newY2 = block.getY();

            survival.getClaimHandler().resizeClaimWithChecks(player, newX1, newX2, newY1, newY2, newZ1, newZ2);
            return;
        }

        Claim claim = survival.getClaimHandler().getClaimAt(block.getLocation(), true, player.getLastClaim());

        if (claim != null) {
            /* Not creating a new claim */

            if (claim.canModify(player)) {
                if ((block.getX() == claim.getCorner1().getBlockX() || block.getX() == claim.getCorner2().getBlockX()) && (block.getZ() == claim.getCorner1().getBlockZ() || block.getZ() == claim.getCorner2().getBlockZ())) {
                    /* Click on a corner -> resize */
                    player.setResizingClaim(claim);
                    player.setLastClaimToolLocation(block.getLocation());
                    player.playSound(Sound.UI_BUTTON_CLICK);
                } else {
                    /* Can't create claim here */
                    new ActionBar(player.bukkit(), () -> "§c§l" + player.translate("survival", "player.claim.overlaps_own"), 60).send();
                    Visualization.show(player, claim, block.getY(), Visualization.Type.INVALID, player.getLocation());
                }
            } else {
                /* In someone else's claim */
                String name = claim.getOwnerName();
                new ActionBar(player.bukkit(), () -> "§c§l" + player.translate("survival", "player.claim.overlaps_other", name + "§c§l"), 60).send();
                Visualization.show(player, claim, block.getY(), Visualization.Type.INVALID, player.getLocation());
            }

            return;
        }

        /* Not in an existing claim */
        if (player.getLastClaimToolLocation() == null) {
            /* First point not selected */

            /* Start claiming */
            player.setLastClaimToolLocation(block.getLocation());

            Claim newClaim = new Claim(player.getUUID(), block.getLocation(), block.getLocation());
            Visualization.show(player, newClaim, block.getY(), Visualization.Type.DISPLAY, player.getLocation());
        } else {
            /* Finishing claim */

            /* New event if switched worlds */
            if (!player.getWorld().getName().equals(player.getLastClaimToolLocation().getWorld().getName())) {
                player.setLastClaimToolLocation(null);
                onRightClick(player, event, itemStack);
                return;
            }

            int newWidth = Math.abs(player.getLastClaimToolLocation().getBlockX() - block.getX()) + 1;
            int newHeight = Math.abs(player.getLastClaimToolLocation().getBlockZ() - block.getZ()) + 1;

            UUID owner = player.getUUID();
            int remaining;

            if (newWidth < Claim.MIN_WIDTH || newHeight < Claim.MIN_WIDTH) {
                /* If event fired twice */
                if (newWidth != 1 && newHeight != 1)
                    new ActionBar(player.bukkit(), () -> "§c§l" + player.translate("survival", "player.claim.min_width"), 60).send();

                return;
            }

            int newArea = newWidth * newHeight;
            if (newArea < Claim.MIN_AREA) {
                if (newArea != 1)
                    new ActionBar(player.bukkit(), () -> "§c§l" + player.translate("survival", "player.claim.min_width"), 60).send();

                return;
            }

            remaining = player.getRemainingClaimBlocks() - newArea;
            if (remaining < 0) {
                new ActionBar(player.bukkit(), () -> "§c§l" + player.translate("survival", "player.claim.need_more_claimsblocks", "§6§l" + Math.abs(remaining) + " Claimblocks§c§l"), 60).send();
                return;
            }

            Claim.CreateResult result = survival.getClaimHandler().createClaim(player.getWorld(), player, owner, player.getLastClaimToolLocation().getBlockX(), block.getX(), player.getLastClaimToolLocation().getBlockY(), block.getY(), player.getLastClaimToolLocation().getBlockZ(), block.getZ(), null);

            if (!result.isSucceeded()) {
                new ActionBar(player.bukkit(), () -> "§c§l" + player.translate("survival", "player.claim.overlaps"), 60).send();

                if (result.getClaim() != null)
                    Visualization.show(player, result.getClaim(), block.getY(), Visualization.Type.INVALID, player.getLocation());

                return;
            } else {
                new ActionBar(player.bukkit(), () -> "§a§l" + player.translate("survival", "player.claim.created_claim", "§6§l" + remaining + "§a§l"), 100).send();
                Visualization.show(player, result.getClaim(), block.getY(), Visualization.Type.CLAIM, player.getLocation());

                player.setLastClaimToolLocation(null);
                player.playSound(Sound.ENTITY_ARROW_HIT_PLAYER);
            }
        }
    }
    
    public static class Hover extends ItemHoverActionBar<SurvivalPlayer> {

        private final Survival server;

        public Hover(Survival server) {
            super(server, ITEM, true, (player, item) -> {
                if (!server.canClaimIn(player.getWorld()))
                    return "§c§l" + player.translate("survival", "player.claim.cant_claim_here");

                if (server.canEditOtherClaims(player))
                    return "§a§lClaiming Tool §7| §4§lOp Mode";

                Claim claim = server.getClaimHandler().getClaimAt(player.getLocation(), true, player.getLastClaim());

                if (claim != null) {
                    player.setLastClaim(claim);

                    if (claim.isOwner(player.getUUID()) || server.canEditOtherClaims(player))
                        return "§6§l" + player.translate("spigot", "player.mouse.left_click").toUpperCase() + " §7| §a§l" + player.translate("survival", "player.claim.within_claim", claim.getName());
                }

                if (player.getLastClaimToolLocation() == null)
                    return "§6§l" + player.translate("spigot", "player.mouse.right_click").toUpperCase() + " §7| §a§l" + player.translate("survival", "player.claim.idle");

                if (player.getResizingClaim() != null)
                    return "§a§l" + player.translate("survival", "player.claim.resizing") + "        §c§lPos 1: §6§l" + LocationUtils.humanFriendlyString(player.getLastClaimToolLocation()) + "        §c§lPos 2: §6§l" + player.translate("player.claim.position.none");

                return "§a§l" + player.translate("survival", "player.claim.claiming") + "        §c§lPos 1: §6§l" + LocationUtils.humanFriendlyString(player.getLastClaimToolLocation()) + "        §c§lPos 2: §6§l" + player.translate("player.claim.position.none");
            });

            this.server = server;
        }

        @Override
        public void onEnter(SurvivalPlayer player, ItemStack item, int slot) {
            super.onEnter(player, item, slot);

            player.playSound(Sound.UI_BUTTON_CLICK);

            server.runSync(() -> {
                player.resetScoreboard();
                player.setScoreboard(new ClaimScoreboard(server, player));
            });
        }

        @Override
        public void onLeave(SurvivalPlayer player) {
            super.onLeave(player);

            player.setLastClaimToolLocation(null);
            player.setResizingClaim(null);

            server.runSync(() -> {
                player.resetScoreboard();
                player.setScoreboard(new SurvivalScoreboard(server, player));
            });

            Visualization.revert(player);
        }
    }
}
