package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.itemstack;

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.WrittenBookBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

/**
 * Created by Fadi on 30-4-2016.
 */
public interface ItemStackNms {

    ItemStack setCustomSkullTexture(ItemStack item, String name, String value);

    void openBook(Player player, WrittenBookBuilder builder);

    default MetaData getMetaData(ItemStack itemStack) {
        return new MetaData(itemStack);
    }

    ItemStack setMetaData(ItemStack item, String namespace, String key, String value);
    ItemStack setMetaData(ItemStack item, String namespace, String key, int value);
    ItemStack setMetaData(ItemStack item, String namespace, String key, long value);
    ItemStack setMetaData(ItemStack item, String namespace, String key, double value);
    ItemStack setMetaData(ItemStack item, String namespace, String key, short value);
    ItemStack setMetaData(ItemStack item, String namespace, String key, float value);
    ItemStack setMetaData(ItemStack item, String namespace, String key, boolean value);

    ItemStack removeMetaData(ItemStack item, String namespace, String key);

    String getMetaDataString(ItemStack item, String namespace, String key);
    Integer getMetaDataInt(ItemStack item, String namespace, String key);
    Long getMetaDataLong(ItemStack item, String namespace, String key);
    Double getMetaDataDouble(ItemStack item, String namespace, String key);
    Short getMetaDataShort(ItemStack item, String namespace, String key);
    Float getMetaDataFloat(ItemStack item, String namespace, String key);
    Boolean getMetaDataBoolean(ItemStack item, String namespace, String key);

    Map<String, String> getMetaDataKeys(ItemStack item, String tagName);

    ItemStack setAttackDamage(ItemStack item, int damage);

}
