package com.orbitmines.minecraft.spigot.servers.fog.events;

import com.orbitmines.minecraft.spigot.servers.fog.FoG;
import com.orbitmines.minecraft.spigot.servers.fog.FoGPlayer;
import com.orbitmines.minecraft.spigot.servers.fog.beehive.BeehiveHandler;
import com.orbitmines.minecraft.spigot.servers.fog.run.Run;
import com.orbitmines.minecraft.spigot.servers.fog.stats.Stats;
import com.orbitmines.minecraft.spigot.servers.fog.stats.ToolStatsStamp;
import com.orbitmines.minecraft.spigot.servers.fog.stats.ToolType;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Bumps the player's per-tool block-break counter on every break, Prison-style.
 * Also protects FoG-placed bee-nest structures (unbreakable: nest, campfire
 * underneath, and surrounding log they were attached to).
 */
public class BlockBreakListener implements Listener {

    private final FoG server;

    public BlockBreakListener(FoG server) {
        this.server = server;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        FoGPlayer player = server.getPlayer(event.getPlayer());
        if (player == null) return;
        Run run = player.getActiveRun();
        if (run == null) return;

        Block block = event.getBlock();
        if (block.getType() == Material.BEE_NEST || block.getType() == Material.BEEHIVE || block.getType() == Material.CAMPFIRE) {
            if (BeehiveHandler.isProtectedHive(run, block.getLocation())
                    || BeehiveHandler.isProtectedHive(run, block.getLocation().clone().add(0, 1, 0))) {
                event.setCancelled(true);
                return;
            }
        }

        Stats stats = player.getStats();
        if (stats == null) return; // run joined mid-tick before stats finished loading
        ToolType tool = ToolType.fromItem(event.getPlayer().getInventory().getItemInMainHand().getType());
        stats.incrementBlocksBroken(tool);

        /* Stamp the updated count onto the tool's metadata + lore so the player sees it. */
        ItemStack stack = event.getPlayer().getInventory().getItemInMainHand();
        long total = stats.getBlocksBroken(tool);
        ItemStack updated = ToolStatsStamp.stamp(server, player, stack, tool, total);
        if (updated != null) event.getPlayer().getInventory().setItemInMainHand(updated);
    }
}
