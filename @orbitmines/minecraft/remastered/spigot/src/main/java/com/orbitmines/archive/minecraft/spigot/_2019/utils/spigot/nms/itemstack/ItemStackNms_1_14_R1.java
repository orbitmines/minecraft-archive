package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.itemstack;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.WrittenBookBuilder;
import net.minecraft.server.v1_14_R1.*;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class ItemStackNms_1_14_R1 implements ItemStackNms {

    @Override
    public ItemStack setCustomSkullTexture(ItemStack item, String name, String value) {
        net.minecraft.server.v1_14_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);

        NBTTagCompound tag = nmsStack.hasTag() ? nmsStack.getTag() : new NBTTagCompound();

        NBTTagCompound skullOwner = new NBTTagCompound();
        skullOwner.setString("Id", UUID.randomUUID().toString());
        skullOwner.setString("Name", "{\"text\":\"" + name + "\"}");

        NBTTagCompound properties = new NBTTagCompound();
        NBTTagCompound texture = new NBTTagCompound();
        texture.setString("Value", value);

        NBTTagList list = new NBTTagList();
        list.add(texture);

        properties.set("textures", list);

        skullOwner.set("Properties", properties);
        tag.set("SkullOwner", skullOwner);

        nmsStack.setTag(tag);

        return CraftItemStack.asCraftMirror(nmsStack);
    }

    @Override
    public void openBook(Player player, WrittenBookBuilder builder) {
        ItemStack before = player.getInventory().getItemInMainHand();
        ItemStack book = builder.build();

        player.getInventory().setItemInMainHand(book);

        try {
            ((CraftPlayer) player).getHandle().a(CraftItemStack.asNMSCopy(book), EnumHand.MAIN_HAND);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        player.getInventory().setItemInMainHand(before);
    }

    @Override
    public ItemStack setMetaData(ItemStack item, String tagName, String key, String value) {
        net.minecraft.server.v1_14_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = nmsStack.hasTag() ? nmsStack.getTag() : new NBTTagCompound();
        NBTTagCompound namespaceTag = tag.hasKey(tagName) ? tag.getCompound(tagName) : new NBTTagCompound();

        namespaceTag.setString(key, value);
        tag.set(tagName, namespaceTag);
        nmsStack.setTag(tag);

        return CraftItemStack.asCraftMirror(nmsStack);
    }

    @Override
    public ItemStack setMetaData(ItemStack item, String tagName, String key, int value) {
        net.minecraft.server.v1_14_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = nmsStack.hasTag() ? nmsStack.getTag() : new NBTTagCompound();
        NBTTagCompound namespaceTag = tag.hasKey(tagName) ? tag.getCompound(tagName) : new NBTTagCompound();

        namespaceTag.setInt(key, value);
        tag.set(tagName, namespaceTag);
        nmsStack.setTag(tag);

        return CraftItemStack.asCraftMirror(nmsStack);
    }

    @Override
    public ItemStack setMetaData(ItemStack item, String tagName, String key, long value) {
        net.minecraft.server.v1_14_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = nmsStack.hasTag() ? nmsStack.getTag() : new NBTTagCompound();
        NBTTagCompound namespaceTag = tag.hasKey(tagName) ? tag.getCompound(tagName) : new NBTTagCompound();

        namespaceTag.setLong(key, value);
        tag.set(tagName, namespaceTag);
        nmsStack.setTag(tag);

        return CraftItemStack.asCraftMirror(nmsStack);
    }

    @Override
    public ItemStack setMetaData(ItemStack item, String tagName, String key, double value) {
        net.minecraft.server.v1_14_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = nmsStack.hasTag() ? nmsStack.getTag() : new NBTTagCompound();
        NBTTagCompound namespaceTag = tag.hasKey(tagName) ? tag.getCompound(tagName) : new NBTTagCompound();

        namespaceTag.setDouble(key, value);
        tag.set(tagName, namespaceTag);
        nmsStack.setTag(tag);

        return CraftItemStack.asCraftMirror(nmsStack);
    }

    @Override
    public ItemStack setMetaData(ItemStack item, String tagName, String key, short value) {
        net.minecraft.server.v1_14_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = nmsStack.hasTag() ? nmsStack.getTag() : new NBTTagCompound();
        NBTTagCompound namespaceTag = tag.hasKey(tagName) ? tag.getCompound(tagName) : new NBTTagCompound();

        namespaceTag.setShort(key, value);
        tag.set(tagName, namespaceTag);
        nmsStack.setTag(tag);

        return CraftItemStack.asCraftMirror(nmsStack);
    }

    @Override
    public ItemStack setMetaData(ItemStack item, String tagName, String key, float value) {
        net.minecraft.server.v1_14_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = nmsStack.hasTag() ? nmsStack.getTag() : new NBTTagCompound();
        NBTTagCompound namespaceTag = tag.hasKey(tagName) ? tag.getCompound(tagName) : new NBTTagCompound();

        namespaceTag.setFloat(key, value);
        tag.set(tagName, namespaceTag);
        nmsStack.setTag(tag);

        return CraftItemStack.asCraftMirror(nmsStack);
    }

    @Override
    public ItemStack setMetaData(ItemStack item, String tagName, String key, boolean value) {
        net.minecraft.server.v1_14_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = nmsStack.hasTag() ? nmsStack.getTag() : new NBTTagCompound();
        NBTTagCompound namespaceTag = tag.hasKey(tagName) ? tag.getCompound(tagName) : new NBTTagCompound();

        namespaceTag.setBoolean(key, value);
        tag.set(tagName, namespaceTag);
        nmsStack.setTag(tag);

        return CraftItemStack.asCraftMirror(nmsStack);
    }

    private NBTTagCompound getMetaDataTag(ItemStack item, String namespace) {
        net.minecraft.server.v1_14_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);

        NBTTagCompound tag = nmsStack.hasTag() ? nmsStack.getTag() : new NBTTagCompound();

        if (!tag.hasKey(namespace))
            return null;

        return tag.getCompound(namespace);
    }

    @Override
    public String getMetaDataString(ItemStack item, String namespace, String key) {
        NBTTagCompound namespaceTag = getMetaDataTag(item, namespace);

        return namespaceTag != null && namespaceTag.hasKey(key) ? namespaceTag.getString(key) : null;
    }

    @Override
    public Integer getMetaDataInt(ItemStack item, String namespace, String key) {
        NBTTagCompound namespaceTag = getMetaDataTag(item, namespace);

        return namespaceTag != null && namespaceTag.hasKey(key) ? namespaceTag.getInt(key) : null;
    }

    @Override
    public Long getMetaDataLong(ItemStack item, String namespace, String key) {
        NBTTagCompound namespaceTag = getMetaDataTag(item, namespace);

        return namespaceTag != null && namespaceTag.hasKey(key) ? namespaceTag.getLong(key) : null;
    }

    @Override
    public Double getMetaDataDouble(ItemStack item, String namespace, String key) {
        NBTTagCompound namespaceTag = getMetaDataTag(item, namespace);

        return namespaceTag != null && namespaceTag.hasKey(key) ? namespaceTag.getDouble(key) : null;
    }

    @Override
    public Short getMetaDataShort(ItemStack item, String namespace, String key) {
        NBTTagCompound namespaceTag = getMetaDataTag(item, namespace);

        return namespaceTag != null && namespaceTag.hasKey(key) ? namespaceTag.getShort(key) : null;
    }

    @Override
    public Float getMetaDataFloat(ItemStack item, String namespace, String key) {
        NBTTagCompound namespaceTag = getMetaDataTag(item, namespace);

        return namespaceTag != null && namespaceTag.hasKey(key) ? namespaceTag.getFloat(key) : null;
    }

    @Override
    public Boolean getMetaDataBoolean(ItemStack item, String namespace, String key) {
        NBTTagCompound namespaceTag = getMetaDataTag(item, namespace);

        return namespaceTag != null && namespaceTag.hasKey(key) ? namespaceTag.getBoolean(key) : null;
    }

    @Override
    public HashMap<String, String> getMetaDataKeys(ItemStack item, String tagName) {
        NBTTagCompound namespaceTag = getMetaDataTag(item, tagName);

        if (namespaceTag == null)
            return null;

        HashMap<String, String> metaData = new HashMap<>();

        for (String key : namespaceTag.getKeys()) {
            metaData.put(key, namespaceTag.getString(key));
        }

        return metaData;
    }

    @Override
    public ItemStack setAttackDamage(ItemStack item, int damage) {
        net.minecraft.server.v1_14_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);

        NBTTagCompound tag = nmsStack.hasTag() ? nmsStack.getTag() : new NBTTagCompound();

        NBTTagList attributes = new NBTTagList();
        NBTTagCompound damageAttribute = new NBTTagCompound();
        damageAttribute.set("AttributeName", new NBTTagString("generic.attackDamage"));
        damageAttribute.set("Name", new NBTTagString("generic.attackDamage"));
        damageAttribute.set("Amount", new NBTTagInt(damage));
        damageAttribute.set("Operation", new NBTTagInt(0));//0 -> value, 1 -> * 100%
        damageAttribute.set("UUIDLeast", new NBTTagInt(894654));
        damageAttribute.set("UUIDMost", new NBTTagInt(2872));
        damageAttribute.set("Slot", new NBTTagString("mainhand"));
        attributes.add(damageAttribute);

        tag.set("AttributeModifiers", attributes);

        nmsStack.setTag(tag);

        return CraftItemStack.asCraftMirror(nmsStack);
    }
}
