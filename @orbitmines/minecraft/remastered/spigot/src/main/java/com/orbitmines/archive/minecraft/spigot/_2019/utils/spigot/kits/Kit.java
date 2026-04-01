package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.kits;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.PlayerUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.PotionBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilderInstance;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutablePlayerItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotPlayer;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;
import java.util.stream.Collectors;

public class Kit<P extends SpigotPlayer> {

    @Getter private MutablePlayerItemBuilder<? extends ItemBuilderInstance, P> helmet;
    @Getter private MutablePlayerItemBuilder<? extends ItemBuilderInstance, P> chestplate;
    @Getter private MutablePlayerItemBuilder<? extends ItemBuilderInstance, P> leggings;
    @Getter private MutablePlayerItemBuilder<? extends ItemBuilderInstance, P> boots;
    @Getter private MutablePlayerItemBuilder<? extends ItemBuilderInstance, P>[] contents;
    @Getter private MutablePlayerItemBuilder<? extends ItemBuilderInstance, P> itemInOffHand;

    @Getter private List<PotionBuilder> potionBuilders;

    public Kit() {
        this.contents = new MutablePlayerItemBuilder[36];
        this.potionBuilders = new ArrayList<>();
    }

    public Kit set(int index, MutablePlayerItemBuilder<? extends ItemBuilderInstance, P> itemBuilder) { this.contents[index] = itemBuilder; return this; }
    public Kit setHelmet(MutablePlayerItemBuilder<? extends ItemBuilderInstance, P> helmet) { this.helmet = helmet; return this; }
    public Kit setChestplate(MutablePlayerItemBuilder<? extends ItemBuilderInstance, P> chestplate) { this.chestplate = chestplate; return this; }
    public Kit setLeggings(MutablePlayerItemBuilder<? extends ItemBuilderInstance, P> leggings) { this.leggings = leggings; return this; }
    public Kit setBoots(MutablePlayerItemBuilder<? extends ItemBuilderInstance, P> boots) { this.boots = boots; return this; }
    public Kit setContents(MutablePlayerItemBuilder<? extends ItemBuilderInstance, P>[] contents) { this.contents = contents; return this; }
    public Kit setItemInOffHand(MutablePlayerItemBuilder<? extends ItemBuilderInstance, P> itemInOffHand) { this.itemInOffHand = itemInOffHand; return this; }

    public Kit setPotionBuilders(List<PotionBuilder> potionBuilders) { this.potionBuilders = potionBuilders; return this; }
    public Kit addPotionBuilder(PotionBuilder potionBuilder) { this.potionBuilders.add(potionBuilder); return this; }

    protected ItemStack build(MutablePlayerItemBuilder<? extends ItemBuilderInstance, P> builder, P player) {
        return builder.toBuilder(player).build();
    }

    /* Copy kit to player's inventory, clearing all other items that might be in it */
    public void copyToInventory(P player) {
        for (int i = 0; i < this.contents.length; i++) {
            setItem(player, i);
        }

        setHelmet(player);
        setChestplate(player);
        setLeggings(player);
        setBoots(player);

        setItemInOffHand(player);
        
        for (PotionBuilder potionBuilder : this.potionBuilders) {
            player.addPotionEffect(potionBuilder.build());
        }

        PlayerUtils.updateInventory(player.bukkit());
    }

    /* Add items to player's inventory, when switchArmor = true it will equip the armor of the kit and add equipped armor to the player's inventory */
    public void addToInventory(P player) {
        addToInventory(player, true);
    }
    public void addToInventory(P player, boolean switchArmor) {
        addToInventory(player, switchArmor, true);
    }
    public void addToInventory(P player, boolean switchArmor, boolean switchOffHand) {
        PlayerInventory inventory = player.getInventory();

        for (MutablePlayerItemBuilder<? extends ItemBuilderInstance, P> builder : this.contents) {
            if (builder == null)
                continue;

            inventory.addItem(build(builder, player));
        }

        addHelmet(player, switchArmor);
        addChestplate(player, switchArmor);
        addLeggings(player, switchArmor);
        addBoots(player, switchArmor);

        addItemInOffHand(player, switchOffHand);

        for (PotionBuilder potionBuilder : this.potionBuilders) {
            player.addPotionEffect(potionBuilder.build());
        }

        PlayerUtils.updateInventory(player.bukkit());
    }

    public MutablePlayerItemBuilder<? extends ItemBuilderInstance, P> get(int index) {
        return this.contents[index];
    }

    public MutablePlayerItemBuilder<? extends ItemBuilderInstance, P> first() {
        for (MutablePlayerItemBuilder<? extends ItemBuilderInstance, P> builder : this.contents) {
            if (builder != null)
                return builder;
        }
        return null;
    }

    public MutablePlayerItemBuilder<? extends ItemBuilderInstance, P> firstOfIndex(int index) {
        int i = 0;

        for (MutablePlayerItemBuilder<? extends ItemBuilderInstance, P> builder : this.contents) {
            if (builder == null)
                continue;

            if (i == index)
                return builder;

            i++;
        }

        return null;
    }

    public int indexOf(MutablePlayerItemBuilder<? extends ItemBuilderInstance, P> builder) {
        for (int i = 0; i < this.contents.length; i++) {
            if (builder.equals(this.contents[i]))
                return i;
        }
        return -1;
    }

    public int contentSize() {
        int amount = 0;

        for (MutablePlayerItemBuilder<? extends ItemBuilderInstance, P> builder : this.contents) {
            if (builder == null)
                continue;

            amount++;
        }

        return amount;
    }

    public List<MutablePlayerItemBuilder<? extends ItemBuilderInstance, P>> getAll() {
        List<MutablePlayerItemBuilder<? extends ItemBuilderInstance, P>> list = new ArrayList<>();
        list.add(helmet);
        list.add(chestplate);
        list.add(leggings);
        list.add(boots);
        list.addAll(Arrays.asList(this.contents));

        return list.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    public List<MutablePlayerItemBuilder<? extends ItemBuilderInstance, P>> with(P player, Material... materials) {
        Set<Material> set = new HashSet<>(Arrays.asList(materials));

        return getAll().stream().filter((item) -> {
            ItemBuilderInstance builder = item.toBuilder(player);

            if (builder == null)
                return false;

            return set.contains(builder.getMaterial());
        }).collect(Collectors.toList());
    }

    private void addHelmet(P player, boolean switchArmor) {
        PlayerInventory inventory = player.getInventory();

        if (helmet == null)
            return;

        ItemStack item = build(this.helmet, player);

        if (inventory.getHelmet() == null) {
            inventory.setHelmet(item);
            return;
        }

        if (!switchArmor) {
            inventory.addItem(item);
            return;
        }

        inventory.addItem(inventory.getHelmet());
        inventory.setHelmet(item);
    }

    private void addChestplate(P player, boolean switchArmor) {
        PlayerInventory inventory = player.getInventory();

        if (chestplate == null)
            return;

        ItemStack item = build(this.chestplate, player);

        if (inventory.getChestplate() == null) {
            inventory.setChestplate(item);
            return;
        }

        if (!switchArmor) {
            inventory.addItem(item);
            return;
        }

        inventory.addItem(inventory.getChestplate());
        inventory.setChestplate(item);
    }

    private void addLeggings(P player, boolean switchArmor) {
        PlayerInventory inventory = player.getInventory();

        if (leggings == null)
            return;

        ItemStack item = build(this.leggings, player);

        if (inventory.getLeggings() == null) {
            inventory.setLeggings(item);
            return;
        }

        if (!switchArmor) {
            inventory.addItem(item);
            return;
        }

        inventory.addItem(inventory.getLeggings());
        inventory.setLeggings(item);
    }

    private void addBoots(P player, boolean switchArmor) {
        PlayerInventory inventory = player.getInventory();

        if (boots == null)
            return;

        ItemStack item = build(this.boots, player);

        if (inventory.getBoots() == null) {
            inventory.setBoots(item);
            return;
        }

        if (!switchArmor) {
            inventory.addItem(item);
            return;
        }

        inventory.addItem(inventory.getBoots());
        inventory.setBoots(item);
    }
    
    private void addItemInOffHand(P player, boolean switchOffHand) {
        PlayerInventory inventory = player.getInventory();

        if (itemInOffHand == null)
            return;

        ItemStack item = build(this.itemInOffHand, player);

        if (inventory.getItemInOffHand() == null) {
            inventory.setItemInOffHand(item);
            return;
        }

        if (!switchOffHand) {
            inventory.addItem(item);
            return;
        }

        inventory.addItem(inventory.getItemInOffHand());
        inventory.setItemInOffHand(item);
    }

    private void setItem(P player, int index) {
        PlayerInventory inventory = player.getInventory();
        MutablePlayerItemBuilder<? extends ItemBuilderInstance, P> builder = this.contents[index];

        if (builder == null)
            inventory.setItem(index, null);
        else
            inventory.setItem(index, build(builder, player));
    }
    
    private void setHelmet(P player) {
        PlayerInventory inventory = player.getInventory();

        if (this.helmet == null)
            inventory.setHelmet(null);
        else
            inventory.setHelmet(build(this.helmet, player));
    }
    
    private void setChestplate(P player) {
        PlayerInventory inventory = player.getInventory();

        if (this.chestplate == null)
            inventory.setChestplate(null);
        else
            inventory.setChestplate(build(this.chestplate, player));
    }
    
    private void setLeggings(P player) {
        PlayerInventory inventory = player.getInventory();

        if (this.leggings == null)
            inventory.setLeggings(null);
        else
            inventory.setLeggings(build(this.leggings, player));
    }
    
    private void setBoots(P player) {
        PlayerInventory inventory = player.getInventory();

        if (this.boots == null)
            inventory.setBoots(null);
        else
            inventory.setBoots(build(this.boots, player));
    }
    
    private void setItemInOffHand(P player) {
        PlayerInventory inventory = player.getInventory();

        if (this.itemInOffHand == null)
            inventory.setItemInOffHand(null);
        else
            inventory.setItemInOffHand(build(this.itemInOffHand, player));
    }
}
