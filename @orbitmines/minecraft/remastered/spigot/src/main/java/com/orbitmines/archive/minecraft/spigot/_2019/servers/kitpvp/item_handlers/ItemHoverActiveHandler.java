package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.item_handlers;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Active;
import com.orbitmines.archive.minecraft._2019.utils.TimeUtils;
import com.orbitmines.archive.minecraft._2019.utils.cooldown.Cooldown;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.item_handlers.ItemHoverActionBar;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.itemstack.ItemStackNms;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Map;

public class ItemHoverActiveHandler extends ItemHoverActionBar<KitPvPPlayer> {

    private static final int BAR_COUNT = 40;

    public ItemHoverActiveHandler(KitPvP kitPvP) {
        super(kitPvP, null, false, (player, item) -> {
            ItemStackNms nms = kitPvP.getNms().customItem();

            Map<Active, Integer> actives = Active.from(nms, item);

            /* No Actives on this item */
            if (actives == null)
                return null;

            Active active = new ArrayList<>(actives.keySet()).get(0);
            int level = actives.get(active);
            Cooldown cooldown = active.getHandler().getCooldown(level);
            long lastUse = active.getLastUse(nms, item, level);
            String name = active.getColor().getCc() + "§l" + active.getName();

            if (!cooldown.onCooldown(lastUse))
                return name + " §7| §e§l" + player.translate("spigot", "player.mouse.right_click");

            long timeLeft = cooldown.getCooldown() - (System.currentTimeMillis() - lastUse);
            int red = (int) (((float) (timeLeft) / (float) cooldown.getCooldown()) * BAR_COUNT);

            StringBuilder actionBar = new StringBuilder(name + " §7| §e" + TimeUtils.humanFriendlyTimer(player.getLanguage(), timeLeft, true) + " §7| ");

            for (int i = 0; i < BAR_COUNT - red; i++) {
                if (i == 0)
                    actionBar.append("§a");

                actionBar.append("|");
            }
            for (int i = 0; i < red; i++) {
                if (i == 0)
                    actionBar.append("§c");

                actionBar.append("|");
            }

            return actionBar.toString();
        });
    }

    @Override
    public boolean equals(ItemStack itemStack) {
        return Active.from(server.getNms().customItem(), itemStack) != null;
    }
}
