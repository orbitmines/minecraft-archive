package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.item_handlers;

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.PlayerUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilderInstance;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

/*
* OrbitMines - @author Fadi Shawki - 2017
*/
public abstract class ItemHoverBlockTarget<S extends SpigotServer<P>, P extends SpigotPlayer<S>> extends ItemHover<P> {

    @Getter private final List<Material> materials;

    public ItemHoverBlockTarget(S server, ItemBuilderInstance itemBuilder, boolean offHandAllowed, Material... materials) {
        super(server, itemBuilder, offHandAllowed);

        this.materials = Arrays.asList(materials);
    }

    public abstract void onTargetEnter(P player);

    @Override
    protected void onEnter(P player, ItemStack item, int slot) {
        if (isTargeted(player))
            onTargetEnter(player);
    }

    @Override
    protected void tick(P player) {
        super.tick(player);

        if (player.getCurrentHover() != this)
            return;

        boolean targeted = isTargeted(player);

        if (targeted && !entered.contains(player)) {
            onTargetEnter(player);
            entered.add(player);
        } else if (!targeted && entered.contains(player)) {
            onLeave(player);
            entered.remove(player);
        }
    }

    private boolean isTargeted(P player) {
        Block block = PlayerUtils.getTargetBlock(player.bukkit(), 5);

        return block != null && this.materials.contains(block.getType());
    }
}
