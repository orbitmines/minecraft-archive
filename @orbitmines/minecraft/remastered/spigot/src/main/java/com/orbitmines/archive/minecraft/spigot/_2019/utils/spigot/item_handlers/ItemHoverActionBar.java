package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.item_handlers;

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilderInstance;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.ActionBar;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/*
* OrbitMines - @author Fadi Shawki - 2017
*/
public abstract class ItemHoverActionBar<P extends SpigotPlayer> extends ItemHover<P> {

    private Map<P, ActionBar> actionBars;
    private Message<P> message;

    public ItemHoverActionBar(SpigotServer<P> server, ItemBuilderInstance itemBuilder, boolean offHandAllowed, Message<P> message) {
        super(server, itemBuilder, offHandAllowed);

        this.actionBars = new HashMap<>();
        this.message = message;
    }

    @Override
    public void onEnter(P player, ItemStack item, int slot) {
        ActionBar existing = actionBars.remove(player);
        if (existing != null)
            existing.forceStop();

        ActionBar actionBar = new ActionBar(player.bukkit(), () -> message.getMessage(player, player.getInventory().getItem(slot)), Long.MAX_VALUE);
        actionBars.put(player, actionBar);
        actionBar.send();
    }

    @Override
    public void onLeave(P omp) {
        if (actionBars.containsKey(omp))
            actionBars.get(omp).forceStop();
    }

    @FunctionalInterface
    public interface Message<P extends SpigotPlayer> {

        String getMessage(P player, ItemStack item);

    }
}
