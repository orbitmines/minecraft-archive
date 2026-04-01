package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.npcs.events;

import com.orbitmines.archive.minecraft._2019.utils.cooldown.Cooldown;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.PlayerUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.npcs.*;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

/*
* OrbitMines - @author Fadi Shawki - 29-7-2017
*/
public class NpcEvents<S extends SpigotServer<P>, P extends SpigotPlayer<S>> implements Listener {

    private static final Cooldown INTERACT_COOLDOWN = new Cooldown(1000);

    private final S server;

    public NpcEvents(S server) {
        this.server = server;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteractEntityEvent(PlayerInteractEntityEvent event) {
        Npc<?, P> npc = Npc.getNpc(event.getRightClicked());
        if (npc == null)
            return;

        event.setCancelled(true);

        P player = server.getPlayer(event.getPlayer());
        if (npc.isClickable() && !player.onCooldown(INTERACT_COOLDOWN)) {
            npc.getInteractAction().onInteract(event, player);

            player.resetCooldown(INTERACT_COOLDOWN);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamageEvent(EntityDamageEvent event) {
        Npc<?, P> npc = Npc.getNpc(event.getEntity());

        if (npc != null)
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteractAtEntityEvent(PlayerInteractAtEntityEvent event) {
        if (!(event.getRightClicked() instanceof ArmorStand))
            return;

        ArmorStand armorStand = (ArmorStand) event.getRightClicked();

        Player player = event.getPlayer();

        Npc<?, P> npc = Npc.getNpc(armorStand);

        if (npc != null) {
            event.setCancelled(true);

            if (npc.isClickable())
                npc.getInteractAction().onInteract(event, server.getPlayer(player));

            PlayerUtils.updateInventory(player);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onWorldUnload(WorldUnloadEvent event) {
        String worldName = event.getWorld().getName();

        List<Npc> npcs = Npc.getNpcsIn(event.getWorld());

        if (npcs == null)
            return;

        for (Npc npc : new ArrayList<>(npcs)) {
            if (npc.getSpawnLocation().getWorld().getName().equals(worldName))
                npc.destroy();
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onItemDespawnEvent(ItemDespawnEvent event) {
        FloatingItem npc = FloatingItem.getFloatingItem(event.getEntity());

        if (npc != null)
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onWorldSwitch(PlayerChangedWorldEvent event) {
        SpigotPlayer player = server.getPlayer(event.getPlayer());

        new BukkitRunnable() {
            @Override
            public void run() {
                List<Npc> list = Npc.getNpcsIn(player.getWorld());

                /* Toggle Personalised MobNpcs */
                for (List<MobNpc> npcs : MobNpc.getMobNpcs().values()) {
                    for (MobNpc npc : npcs) {
                        if (npc instanceof PersonalisedMobNpc)
                            ((PersonalisedMobNpc) npc).toggle(player);
                    }
                }
                for (List<FloatingItem> floatingItems : FloatingItem.getFloatingItems().values()) {
                    for (FloatingItem floatingItem : floatingItems) {
                        if (floatingItem instanceof PersonalisedFloatingItem)
                            ((PersonalisedFloatingItem) floatingItem).toggle(player);
                    }
                }

                if (list == null)
                    return;

                /* Hide other npcs that shouldn't be visible */
                for (Npc npc : list) {
                    if (npc.getWatchers() != null && !npc.getWatchers().contains(player.bukkit()))
                        npc.hideFor(player.bukkit());
                }
            }
        }.runTaskLater(server, 10);
    }

    /* Fix npcs catching on fire */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onCombust(EntityCombustEvent event) {
        MobNpc npc = MobNpc.getMobNpc(event.getEntity());

        if (npc != null)
            event.setCancelled(true);
    }
}
