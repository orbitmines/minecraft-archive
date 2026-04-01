package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.item_handlers;

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilderInstance;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.Interval;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.PlayerRunnable;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.TimeUnit;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/*
* OrbitMines - @author Fadi Shawki - 2017
*/
public abstract class ItemHover<P extends SpigotPlayer> {

    @Getter private static List<ItemHover> itemHovers = new ArrayList<>();

    @Getter private final ItemBuilderInstance itemBuilder;
    @Getter private final boolean offHandAllowed;

    protected SpigotServer<P> server;
    protected PlayerRunnable runnable;

    protected Set<P> entered;

    public ItemHover(SpigotServer<P> server, ItemBuilderInstance itemBuilder, boolean offHandAllowed) {
        this.server = server;
        this.itemBuilder = itemBuilder;
        this.offHandAllowed = offHandAllowed;
        this.entered = new HashSet<>();

        itemHovers.add(this);

        runnable = new PlayerRunnable<SpigotServer<P>, P>(server, Interval.of(TimeUnit.TICK, 1)) {
            @Override
            public void run(P player) {
                tick(player);
            }

            @Override
            public Collection<P> getPlayers() {
                return server.getPlayers();
            }
        };
        runnable.async().start();
    }

    protected abstract void onEnter(P player, ItemStack item, int slot);

    protected abstract void onLeave(P player);

    protected void tick(P player) {
        ItemStack mainHand = player.getItemInMainHand();
        ItemStack offHand = player.getItemInOffHand();

        try {
            /* Player has hover, but not in main hand, and not in his off hand, leave that hover */
            if (player.getCurrentHover() != null && (!player.getCurrentHover().equals(mainHand) && (!offHandAllowed || !player.getCurrentHover().equals(offHand))))
                player.getCurrentHover().leave(player);
        } catch (NullPointerException ex) {
            //TODO Weird NullPointer, probably timing issue with other thread perhaps
        }

        if (player.getCurrentHover() != null)
            return;

        /* Player has currently no hover in main hand, and offhand equals this hover */
        if (equals(mainHand))
            enter(player, mainHand, player.getHeldItemSlot());
        else if (offHandAllowed && equals(offHand))
            enter(player, offHand, 41);
    }

    public void enter(P player, ItemStack item, int slot) {
        if (player.getCurrentHover() != null)
            player.getCurrentHover().leave(player);

        player.setCurrentHover(this);
        onEnter(player, item, slot);

        entered.add(player);
    }

    public void leave(P player) {
        if (!entered.contains(player))
            return;

        onLeave(player);

        player.setCurrentHover(null);
    }

    public void unregister() {
        itemHovers.remove(this);

        if (runnable != null)
            runnable.cancel();
    }

    public boolean equals(ItemStack itemStack) {
        return itemBuilder.equals(itemStack);
    }

    public boolean hasEntered(P player) {
        return entered.contains(player);
    }
}
