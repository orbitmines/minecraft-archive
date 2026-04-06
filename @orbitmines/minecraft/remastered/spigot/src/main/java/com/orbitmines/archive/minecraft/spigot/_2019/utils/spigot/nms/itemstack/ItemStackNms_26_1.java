package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.itemstack;

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.WrittenBookBuilder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.component.CustomData;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class ItemStackNms_26_1 implements ItemStackNms {

    @Override
    public ItemStack setCustomSkullTexture(ItemStack item, String name, String value) {
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);

        CompoundTag skullOwner = new CompoundTag();
        skullOwner.putString("Id", UUID.randomUUID().toString());
        skullOwner.putString("Name", "{\"text\":\"" + name + "\"}");

        CompoundTag properties = new CompoundTag();
        CompoundTag texture = new CompoundTag();
        texture.putString("Value", value);

        net.minecraft.nbt.ListTag list = new net.minecraft.nbt.ListTag();
        list.add(texture);

        properties.put("textures", list);
        skullOwner.put("Properties", properties);

        CompoundTag customData = nmsStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        customData.put("SkullOwner", skullOwner);
        nmsStack.set(DataComponents.CUSTOM_DATA, CustomData.of(customData));

        return CraftItemStack.asCraftMirror(nmsStack);
    }

    @Override
    public void openBook(Player player, WrittenBookBuilder builder) {
        ItemStack before = player.getInventory().getItemInMainHand();
        ItemStack book = builder.build();

        player.getInventory().setItemInMainHand(book);

        try {
            ((CraftPlayer) player).getHandle().openItemGui(CraftItemStack.asNMSCopy(book), InteractionHand.MAIN_HAND);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        player.getInventory().setItemInMainHand(before);
    }

    private CompoundTag getOrCreateCustomData(net.minecraft.world.item.ItemStack nmsStack) {
        return nmsStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
    }

    private net.minecraft.world.item.ItemStack setCustomData(net.minecraft.world.item.ItemStack nmsStack, CompoundTag data) {
        nmsStack.set(DataComponents.CUSTOM_DATA, CustomData.of(data));
        return nmsStack;
    }

    @Override
    public ItemStack setMetaData(ItemStack item, String tagName, String key, String value) {
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        CompoundTag customData = getOrCreateCustomData(nmsStack);
        CompoundTag namespaceTag = customData.getCompoundOrEmpty(tagName);

        namespaceTag.putString(key, value);
        customData.put(tagName, namespaceTag);
        setCustomData(nmsStack, customData);

        return CraftItemStack.asCraftMirror(nmsStack);
    }

    @Override
    public ItemStack setMetaData(ItemStack item, String tagName, String key, int value) {
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        CompoundTag customData = getOrCreateCustomData(nmsStack);
        CompoundTag namespaceTag = customData.getCompoundOrEmpty(tagName);

        namespaceTag.putInt(key, value);
        customData.put(tagName, namespaceTag);
        setCustomData(nmsStack, customData);

        return CraftItemStack.asCraftMirror(nmsStack);
    }

    @Override
    public ItemStack setMetaData(ItemStack item, String tagName, String key, long value) {
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        CompoundTag customData = getOrCreateCustomData(nmsStack);
        CompoundTag namespaceTag = customData.getCompoundOrEmpty(tagName);

        namespaceTag.putLong(key, value);
        customData.put(tagName, namespaceTag);
        setCustomData(nmsStack, customData);

        return CraftItemStack.asCraftMirror(nmsStack);
    }

    @Override
    public ItemStack setMetaData(ItemStack item, String tagName, String key, double value) {
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        CompoundTag customData = getOrCreateCustomData(nmsStack);
        CompoundTag namespaceTag = customData.getCompoundOrEmpty(tagName);

        namespaceTag.putDouble(key, value);
        customData.put(tagName, namespaceTag);
        setCustomData(nmsStack, customData);

        return CraftItemStack.asCraftMirror(nmsStack);
    }

    @Override
    public ItemStack setMetaData(ItemStack item, String tagName, String key, short value) {
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        CompoundTag customData = getOrCreateCustomData(nmsStack);
        CompoundTag namespaceTag = customData.getCompoundOrEmpty(tagName);

        namespaceTag.putShort(key, value);
        customData.put(tagName, namespaceTag);
        setCustomData(nmsStack, customData);

        return CraftItemStack.asCraftMirror(nmsStack);
    }

    @Override
    public ItemStack setMetaData(ItemStack item, String tagName, String key, float value) {
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        CompoundTag customData = getOrCreateCustomData(nmsStack);
        CompoundTag namespaceTag = customData.getCompoundOrEmpty(tagName);

        namespaceTag.putFloat(key, value);
        customData.put(tagName, namespaceTag);
        setCustomData(nmsStack, customData);

        return CraftItemStack.asCraftMirror(nmsStack);
    }

    @Override
    public ItemStack setMetaData(ItemStack item, String tagName, String key, boolean value) {
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        CompoundTag customData = getOrCreateCustomData(nmsStack);
        CompoundTag namespaceTag = customData.getCompoundOrEmpty(tagName);

        namespaceTag.putBoolean(key, value);
        customData.put(tagName, namespaceTag);
        setCustomData(nmsStack, customData);

        return CraftItemStack.asCraftMirror(nmsStack);
    }

    private CompoundTag getMetaDataTag(ItemStack item, String namespace) {
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        CompoundTag customData = getOrCreateCustomData(nmsStack);

        if (!customData.contains(namespace))
            return null;

        return customData.getCompoundOrEmpty(namespace);
    }

    @Override
    public String getMetaDataString(ItemStack item, String namespace, String key) {
        CompoundTag namespaceTag = getMetaDataTag(item, namespace);
        return namespaceTag != null && namespaceTag.contains(key) ? namespaceTag.getStringOr(key, null) : null;
    }

    @Override
    public Integer getMetaDataInt(ItemStack item, String namespace, String key) {
        CompoundTag namespaceTag = getMetaDataTag(item, namespace);
        return namespaceTag != null && namespaceTag.contains(key) ? namespaceTag.getIntOr(key, 0) : null;
    }

    @Override
    public Long getMetaDataLong(ItemStack item, String namespace, String key) {
        CompoundTag namespaceTag = getMetaDataTag(item, namespace);
        return namespaceTag != null && namespaceTag.contains(key) ? namespaceTag.getLongOr(key, 0L) : null;
    }

    @Override
    public Double getMetaDataDouble(ItemStack item, String namespace, String key) {
        CompoundTag namespaceTag = getMetaDataTag(item, namespace);
        return namespaceTag != null && namespaceTag.contains(key) ? namespaceTag.getDoubleOr(key, 0.0) : null;
    }

    @Override
    public Short getMetaDataShort(ItemStack item, String namespace, String key) {
        CompoundTag namespaceTag = getMetaDataTag(item, namespace);
        return namespaceTag != null && namespaceTag.contains(key) ? namespaceTag.getShortOr(key, (short) 0) : null;
    }

    @Override
    public Float getMetaDataFloat(ItemStack item, String namespace, String key) {
        CompoundTag namespaceTag = getMetaDataTag(item, namespace);
        return namespaceTag != null && namespaceTag.contains(key) ? namespaceTag.getFloatOr(key, 0.0F) : null;
    }

    @Override
    public Boolean getMetaDataBoolean(ItemStack item, String namespace, String key) {
        CompoundTag namespaceTag = getMetaDataTag(item, namespace);
        return namespaceTag != null && namespaceTag.contains(key) ? namespaceTag.getBoolean(key).orElse(null) : null;
    }

    @Override
    public ItemStack removeMetaData(ItemStack item, String namespace, String key) {
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        CompoundTag customData = getOrCreateCustomData(nmsStack);
        CompoundTag namespaceTag = customData.getCompoundOrEmpty(namespace);

        namespaceTag.remove(key);
        customData.put(namespace, namespaceTag);
        setCustomData(nmsStack, customData);

        return CraftItemStack.asCraftMirror(nmsStack);
    }

    @Override
    public HashMap<String, String> getMetaDataKeys(ItemStack item, String tagName) {
        CompoundTag namespaceTag = getMetaDataTag(item, tagName);

        if (namespaceTag == null)
            return null;

        HashMap<String, String> metaData = new HashMap<>();
        for (String key : namespaceTag.keySet()) {
            metaData.put(key, namespaceTag.getStringOr(key, ""));
        }

        return metaData;
    }

    @Override
    public ItemStack setAttackDamage(ItemStack item, int damage) {
        org.bukkit.inventory.meta.ItemMeta meta = item.getItemMeta();
        org.bukkit.NamespacedKey key = org.bukkit.NamespacedKey.minecraft("generic.attack_damage");

        /* Remove existing modifier with the same key before adding */
        if (meta.getAttributeModifiers() != null) {
            Collection<org.bukkit.attribute.AttributeModifier> existing = meta.getAttributeModifiers(org.bukkit.attribute.Attribute.ATTACK_DAMAGE);
            if (existing != null)
                for (org.bukkit.attribute.AttributeModifier mod : existing)
                    if (mod.getKey().equals(key))
                        meta.removeAttributeModifier(org.bukkit.attribute.Attribute.ATTACK_DAMAGE, mod);
        }

        meta.addAttributeModifier(
                org.bukkit.attribute.Attribute.ATTACK_DAMAGE,
                new org.bukkit.attribute.AttributeModifier(
                        key,
                        damage,
                        org.bukkit.attribute.AttributeModifier.Operation.ADD_NUMBER,
                        org.bukkit.inventory.EquipmentSlotGroup.MAINHAND
                )
        );
        item.setItemMeta(meta);
        return item;
    }
}
