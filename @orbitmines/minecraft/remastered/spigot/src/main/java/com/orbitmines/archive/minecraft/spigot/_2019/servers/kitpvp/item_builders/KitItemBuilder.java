package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.item_builders;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Active;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.KitPvPKit;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class KitItemBuilder extends ItemBuilder implements KitItemBuilderInstance {

    @Getter private KitItemInstance<KitItemBuilder> kitItem;

    public KitItemBuilder(KitPvPKit.Level kit, Material material) {
        super(material);
        setup(kit);
    }

    public KitItemBuilder(KitPvPKit.Level kit, Material material, int amount) {
        super(material, amount);
        setup(kit);
    }

    public KitItemBuilder(KitPvPKit.Level kit, Material material, int amount, String displayName) {
        super(material, amount, displayName);
        setup(kit);
    }

    public KitItemBuilder(KitPvPKit.Level kit, Material material, int amount, String displayName, String... lore) {
        super(material, amount, displayName, lore);
        setup(kit);
    }

    public KitItemBuilder(KitPvPKit.Level kit, ItemBuilder itemBuilder) {
        super(itemBuilder);
        setup(kit);
    }

    public KitItemBuilder(KitPvPKit.Level kit, Material material, int amount, String displayName, List<String> lore) {
        super(material, amount, displayName, lore);
        setup(kit);
    }

    private void setup(KitPvPKit.Level kit) {
        kitItem = new KitItemInstance<>(kit, this);
    }

    @Override
    public ItemStack build() {
        return kitItem.apply(super.build());
    }

    /*

        KitItem delegates

     */

    public Map<Passive, Integer> getPassives() {
        return kitItem.getPassives();
    }

    public Map<Active, Integer> getActives() {
        return kitItem.getActives();
    }

    public KitItemBuilder addPassive(Passive passive, Integer level) {
        return kitItem.addPassive(passive, level);
    }

    public KitItemBuilder addActive(Active active, Integer level) {
        return kitItem.addActive(active, level);
    }

    public KitItemBuilder applyNewPassive(Set<Passive> newPassives) {
        return kitItem.applyNewPassive(newPassives);
    }

    public KitItemBuilder applyRemovedPassive(Set<Passive> removedPassives) {
        return kitItem.applyRemovedPassive(removedPassives);
    }

    public KitItemBuilder applyNewActives(Set<Active> newActives) {
        return kitItem.applyNewActives(newActives);
    }

    public KitItemBuilder applyRemovedActive(Set<Active> removedActives) {
        return kitItem.applyRemovedActive(removedActives);
    }
}
