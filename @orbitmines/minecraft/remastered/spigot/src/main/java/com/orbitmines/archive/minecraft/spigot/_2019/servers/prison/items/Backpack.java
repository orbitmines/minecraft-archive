package com.orbitmines.archive.minecraft.spigot._2019.servers.prison.items;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ItemUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.itemstack.ItemStackNms;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.itemstack.MetaData;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.serializers.InventorySerializer;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public interface Backpack {

    // TODO: 1.14 Has something build in that might transfer NBT tags to the blocks (when block is placed) Or do we have to add this ourselfs

    Map<UUID, Inventory> cachedBackpacks = new HashMap<>();
    ItemStackNms nms = SpigotServer.getInstance().getNms().customItem();

    ItemStack getItem();

    void setItem(ItemStack itemStack);

    MetaData getMetaData();

    default boolean isBackpack() {
        return ItemUtils.isShulkerBox(getItem());
    }

    default Inventory getAsBackpack() {
        MetaData metaData = getMetaData();
        UUID uuid = metaData.getOrDefault("backpack", "id", UUID.randomUUID());

        if (cachedBackpacks.containsKey(uuid))
            return cachedBackpacks.get(uuid);

        String jsonInventory = metaData.getOrDefault("backpack", "inventory", (String) null);

        // No backpack present, initialize one
        if (jsonInventory == null) {
            ItemStack itemStack = getItem();
            itemStack = initializeBackpack(Type.of(itemStack.getType()), uuid);

            setItem(itemStack);

            return cachedBackpacks.get(uuid);
        }

        // Backpack present but not in cache, get it from item and add to cache
        Inventory inventory = new InventorySerializer().deserialize(new JsonParser().parse(jsonInventory));

        cachedBackpacks.put(uuid, inventory);

        return inventory;
    }

    default void updateBackpack(Inventory inventory) {
        setItem(updateBackpack(getItem(), inventory));
    }

    default int getBackpackSize(){
        //TODO: CHANGE TO NMS Size()
        return getAsBackpack().getSize();
    }

    static ItemStack updateBackpack(ItemStack itemStack, Inventory inventory) {
        MetaData metaData = nms.getMetaData(itemStack);

        itemStack = metaData.set("backpack", "inventory", new GsonBuilder().create().toJson(new InventorySerializer().serialize(inventory)));

        // TODO: Update lore with contents

        return itemStack;
    }

    static ItemStack initializeBackpack(Type type, UUID uuid) {
        ItemStack itemStack = new ItemStack(type.shulkerBox, 1);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(type.getDisplayName() + "§r §8(" + type.size + ")");
        itemStack.setItemMeta(meta);

        // Setup actual backpack
        MetaData metaData = nms.getMetaData(itemStack);
        itemStack = metaData.set("backpack", "id", uuid);

        Inventory inventory;
        if (type.inventoryType == InventoryType.CHEST)
            inventory = Bukkit.createInventory(null, type.size);
        else
            inventory = Bukkit.createInventory(null, type.inventoryType);

        itemStack = updateBackpack(itemStack, inventory);

        // Cache backpack
        cachedBackpacks.put(uuid, inventory);

        return itemStack;
    }

    enum Type {

        SIZE_5(5, InventoryType.HOPPER, Material.LIGHT_GRAY_SHULKER_BOX, Color.SILVER, "Small Backpack"),
        SIZE_9(9, Material.LIME_SHULKER_BOX, Color.LIME, "Common Backpack"),
        SIZE_18(18, Material.CYAN_SHULKER_BOX, Color.TEAL, "Uncommon Backpack"),
        SIZE_27(27, Material.BLUE_SHULKER_BOX, Color.BLUE, "Rare Backpack"),
        SIZE_36(36, Material.YELLOW_SHULKER_BOX, Color.YELLOW, "Epic Backpack"),
        SIZE_45(45, Material.ORANGE_SHULKER_BOX, Color.ORANGE, "Legendary Backpack"),
        SIZE_54(54, Material.RED_SHULKER_BOX, Color.RED, "Ultra Backpack");

        @Getter private final int size;
        @Getter private final InventoryType inventoryType;
        @Getter private final Material shulkerBox;
        @Getter private final Color color;
        @Getter private final String name;

        Type(int size, Material shulkerBox, Color color, String name) {
            this(size, InventoryType.CHEST, shulkerBox, color, name);
        }

        Type(int size, InventoryType inventoryType, Material shulkerBox, Color color, String name) {
            this.size = size;
            this.inventoryType = inventoryType;
            this.shulkerBox = shulkerBox;
            this.color = color;
            this.name = name;
        }

        public String getDisplayName() {
            return this.color.getCc() + "§l" + this.name;
        }

        public static Type of(Material shulkerBox) {
            for (Type type : values()) {
                if (type.shulkerBox == shulkerBox)
                    return type;
            }

            return Type.SIZE_5;
        }
    }
}
