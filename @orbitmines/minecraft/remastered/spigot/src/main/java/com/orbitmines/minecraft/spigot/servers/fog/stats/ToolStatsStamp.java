package com.orbitmines.minecraft.spigot.servers.fog.stats;

import com.orbitmines.archive.minecraft._2019.libs.player.Languageable;
import com.orbitmines.minecraft.spigot.servers.fog.FoG;
import com.orbitmines.minecraft.spigot.servers.fog.FoGPlayer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Persist the per-tool "blocks broken" counter on the tool itself:
 *   - NMS metadata under {@code fog:blocks_broken} (survives restarts);
 *   - a lore line "Blocks broken: N" (translated), so the player sees it.
 */
public class ToolStatsStamp {

    public static final String NS = "fog";
    public static final String KEY = "blocks_broken";

    private static final String LORE_MARKER = "fog-stats:blocks_broken";

    /** Returns a new ItemStack (may be the same instance) with updated meta + lore. */
    public static ItemStack stamp(FoG server, Languageable viewer, ItemStack stack, ToolType tool, long total) {
        if (stack == null || stack.getType() == Material.AIR) return stack;
        if (tool == ToolType.HAND) return stack; // skip bare-hand — nothing to stamp on

        ItemStack withNbt = server.getNms().customItem().setMetaData(stack, NS, KEY, total);
        ItemMeta meta = withNbt.getItemMeta();
        if (meta == null) return withNbt;

        List<String> lore = meta.hasLore() ? new ArrayList<>(meta.getLore()) : new ArrayList<>();
        /* Strip any prior fog-stats lines so we don't accumulate. */
        lore.removeIf(line -> line != null && line.contains(LORE_MARKER));

        /* Visible summary + an invisible marker tag so we can find-and-replace next time. */
        String label = viewer.translate("fog", "stats.blocks_broken", total);
        lore.add("§7" + label + "§0§o " + LORE_MARKER);
        meta.setLore(lore);
        withNbt.setItemMeta(meta);
        return withNbt;
    }

    public static long read(FoG server, ItemStack stack) {
        if (stack == null || stack.getType() == Material.AIR) return 0;
        Long v = server.getNms().customItem().getMetaDataLong(stack, NS, KEY);
        return v == null ? 0L : v;
    }
}
