package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.runnables;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events.KitEvent;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.Nms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.entity.EntityNms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.Interval;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.PassiveRunnable;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.TimeUnit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class KitPassiveRunnable extends PassiveRunnable<KitPvP> {

    private final KitPvP server;
    
    public KitPassiveRunnable(KitPvP plugin) {
        super(plugin, Interval.of(TimeUnit.SECOND, 1));
        
        this.server = plugin;
    }

    @Override
    public void onRun() {
        Nms nms = server.getNms();

        for (KitPvPPlayer player : server.getPlayers()) {

            if (player.getSelectedKit() == null || player.isSpectator())
                return;

            for (ItemStack item : player.getInventory().getContents()) {
                if (item == null || item.getType() == Material.AIR)
                    continue;

                Map<Passive, Integer> passives = Passive.from(nms.customItem(), item);

                /* No Passives on this item */
                if (passives == null)
                    continue;

                for (Passive passive : passives.keySet()) {
                    if (passive == Passive.ARROW_REGEN || passive == Passive.PLAYER_TRACKING) {
                        server.runSync(() -> passive.getHandler().trigger(new KitEvent<>(player, null), null, passives.get(passive)));
                    } else if (passive.getHandler() instanceof Passive.LowHealthHandler) {
                        KitEvent event = new KitEvent<>(player, null);
                        int level = passives.get(passive);

                        Passive.LowHealthHandler lowHealthHandler = (Passive.LowHealthHandler) passive.getHandler();

                        server.runSync(() -> {
                            if (player.getHealth() / nms.entity().getAttribute(player.bukkit(), EntityNms.Attribute.MAX_HEALTH) > lowHealthHandler.getPercentage(level))
                                lowHealthHandler.triggerOff(event, level);
                            else
                                lowHealthHandler.trigger(new KitEvent<>(player, null), null, level);
                        });
                    }
                }
            } 
        }
    }
}
