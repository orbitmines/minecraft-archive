package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.kits.interactive;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilderInstance;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutablePlayerItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.item_handlers.ItemHoverActionBar;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.item_handlers.ItemInteraction;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.kits.Kit;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class InteractiveKit<S extends SpigotServer<P>, P extends SpigotPlayer> extends Kit<P> {
    /*
        Note: These are session based, and won't work after server restart.
     */

    private final Map<MutablePlayerItemBuilder<? extends ItemBuilderInstance, P>, Interaction<S, P>> interactions;

    public InteractiveKit() {
        this.interactions = new HashMap<>();
    }

    public Kit set(int index, MutablePlayerItemBuilder<? extends ItemBuilderInstance, P> itemBuilder, Interaction<S, P> interaction) {
        this.interactions.put(itemBuilder, interaction);
        return super.set(index, itemBuilder);
    }

    public Kit setHelmet(MutablePlayerItemBuilder<? extends ItemBuilderInstance, P> helmet, Interaction<S, P> interaction) {
        this.interactions.put(helmet, interaction);
        return super.setHelmet(helmet);
    }

    public Kit setChestplate(MutablePlayerItemBuilder<? extends ItemBuilderInstance, P> chestplate, Interaction<S, P> interaction) {
        this.interactions.put(chestplate, interaction);
        return super.setChestplate(chestplate);
    }

    public Kit setLeggings(MutablePlayerItemBuilder<? extends ItemBuilderInstance, P> leggings, Interaction<S, P> interaction) {
        this.interactions.put(leggings, interaction);
        return super.setLeggings(leggings);
    }

    public Kit setBoots(MutablePlayerItemBuilder<? extends ItemBuilderInstance, P> boots, Interaction<S, P> interaction) {
        this.interactions.put(boots, interaction);
        return super.setBoots(boots);
    }

    public Kit setItemInOffHand(MutablePlayerItemBuilder<? extends ItemBuilderInstance, P> itemInOffHand, Interaction<S, P> interaction) {
        this.interactions.put(itemInOffHand, interaction);
        return super.setItemInOffHand(itemInOffHand);
    }

    @Override
    public ItemStack build(MutablePlayerItemBuilder<? extends ItemBuilderInstance, P> builder, P player) {
        ItemStack itemStack = super.build(builder, player);

        if (!interactions.containsKey(builder))
            return itemStack;

        return interactions.get(builder).applyId(itemStack);
    }

    public static abstract class Interaction<S extends SpigotServer<P>, P extends SpigotPlayer> extends ItemInteraction<P> {

        private static long NEXT_ID = 0;

        private S server;
        private final long id;

        public Interaction(S server) {
            super(null);

            this.server = server;

            id = NEXT_ID++;
        }

        public Interaction<S, P> onActionBarHover(ItemHoverActionBar.Message<P> message) {
            return onActionBarHover(message, false);
        }

        public Interaction<S, P> onActionBarHover(ItemHoverActionBar.Message<P> message, boolean offHandAllowed) {
            new ItemHoverActionBar<P>(server, null, offHandAllowed, message) {
                @Override
                public boolean equals(ItemStack itemStack) {
                    return Interaction.this.equals(itemStack);
                }

                @Override
                public ItemBuilderInstance getItemBuilder() {
                    throw new UnsupportedOperationException();
                }
            };

            return this;
        }

        @Override
        public ItemBuilderInstance getItemBuilder() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(ItemStack itemStack) {
            if (itemStack == null)
                return false;

            return server.getNms().customItem().getMetaData(itemStack).getOrDefault("interactive_kit", "item_id", -1L) == this.id;
        }

        public ItemStack applyId(ItemStack itemStack) {
            return server.getNms().customItem().setMetaData(itemStack, "interactive_kit", "item_id", id);
        }
    }
}
