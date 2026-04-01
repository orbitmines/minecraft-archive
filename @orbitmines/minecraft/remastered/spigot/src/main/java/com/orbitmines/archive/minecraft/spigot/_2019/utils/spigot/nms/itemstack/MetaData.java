package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.itemstack;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.UUID;

public class MetaData {

    private static final ItemStackNms nms;

    //TODO ADD OBSERVER TO CERTAIN NAMESPACE / KEY

    static {
        nms = SpigotServer.getInstance().getNms().customItem();
    }

    @Getter private ItemStack itemStack;

    public MetaData(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack set(String namespace, String key, UUID value) {
        itemStack = nms.setMetaData(itemStack, namespace, key, value.toString());
        return itemStack;
    }

    public ItemStack set(String namespace, String key, String value) {
        itemStack = nms.setMetaData(itemStack, namespace, key, value);
        return itemStack;
    }

    public ItemStack set(String namespace, String key, int value) {
        itemStack = nms.setMetaData(itemStack, namespace, key, value);
        return itemStack;
    }

    public ItemStack set(String namespace, String key, long value) {
        itemStack = nms.setMetaData(itemStack, namespace, key, value);
        return itemStack;
    }

    public ItemStack set(String namespace, String key, double value) {
        itemStack = nms.setMetaData(itemStack, namespace, key, value);
        return itemStack;
    }

    public ItemStack set(String namespace, String key, short value) {
        itemStack = nms.setMetaData(itemStack, namespace, key, value);
        return itemStack;
    }

    public ItemStack set(String namespace, String key, float value) {
        itemStack = nms.setMetaData(itemStack, namespace, key, value);
        return itemStack;
    }

    public ItemStack set(String namespace, String key, boolean value) {
        itemStack = nms.setMetaData(itemStack, namespace, key, value);
        return itemStack;
    }

    public UUID getAsUUID(String namespace, String key) {
        String uuid = getAsString(namespace, key);
        return uuid != null ? UUID.fromString(uuid) : null;
    }

    public String getAsString(String namespace, String key) {
        return nms.getMetaDataString(itemStack, namespace, key);
    }

    public Integer getAsInt(String namespace, String key) {
        return nms.getMetaDataInt(itemStack, namespace, key);
    }

    public Long getAsLong(String namespace, String key) {
        return nms.getMetaDataLong(itemStack, namespace, key);
    }

    public Double getAsDouble(String namespace, String key) {
        return nms.getMetaDataDouble(itemStack, namespace, key);
    }

    public Short getAsShort(String namespace, String key) {
        return nms.getMetaDataShort(itemStack, namespace, key);
    }

    public Float getAsFloat(String namespace, String key) {
        return nms.getMetaDataFloat(itemStack, namespace, key);
    }

    public Boolean getAsBoolean(String namespace, String key) {
        return nms.getMetaDataBoolean(itemStack, namespace, key);
    }

    public UUID getOrDefault(String namespace, String key, UUID defaultValue) {
        String value = getOrDefault(namespace, key, defaultValue.toString());

        return value != null ? UUID.fromString(value) : defaultValue;
    }

    public String getOrDefault(String namespace, String key, String defaultValue) {
        String value = nms.getMetaDataString(itemStack, namespace, key);

        return value != null ? value : defaultValue;
    }

    public boolean getOrDefault(String namespace, String key, boolean defaultValue) {
        Boolean value = nms.getMetaDataBoolean(itemStack, namespace, key);

        return value != null ? value : defaultValue;
    }

    public int getOrDefault(String namespace, String key, int defaultValue) {
        Integer value = nms.getMetaDataInt(itemStack, namespace, key);

        return value != null ? value : defaultValue;
    }

    public long getOrDefault(String namespace, String key, long defaultValue) {
        Long value = nms.getMetaDataLong(itemStack, namespace, key);

        return value != null ? value : defaultValue;
    }

    public double getOrDefault(String namespace, String key, double defaultValue) {
        Double value = nms.getMetaDataDouble(itemStack, namespace, key);

        return value != null ? value : defaultValue;
    }

    public short getOrDefault(String namespace, String key, short defaultValue) {
        Short value = nms.getMetaDataShort(itemStack, namespace, key);

        return value != null ? value : defaultValue;
    }

    public float getOrDefault(String namespace, String key, float defaultValue) {
        Float value = nms.getMetaDataFloat(itemStack, namespace, key);

        return value != null ? value : defaultValue;
    }

    public Map<String, String> getKeys(String namespace) {
        return nms.getMetaDataKeys(itemStack, namespace);
    }
}
