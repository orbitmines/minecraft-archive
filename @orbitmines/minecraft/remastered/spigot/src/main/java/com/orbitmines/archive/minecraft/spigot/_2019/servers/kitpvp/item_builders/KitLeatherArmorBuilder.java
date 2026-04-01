package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.item_builders;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Active;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.KitPvPKit;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.LeatherArmorBuilder;
import lombok.Getter;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class KitLeatherArmorBuilder extends LeatherArmorBuilder implements KitItemBuilderInstance {

    @Getter private KitItemInstance<KitLeatherArmorBuilder> kitItem;

    public KitLeatherArmorBuilder(KitPvPKit.Level kit, Type type) {
        super(type);
        setup(kit);
    }

    public KitLeatherArmorBuilder(KitPvPKit.Level kit, Type type, Color color) {
        super(type, color);
        setup(kit);
    }

    public KitLeatherArmorBuilder(KitPvPKit.Level kit, Type type, Color color, int amount) {
        super(type, color, amount);
        setup(kit);
    }

    public KitLeatherArmorBuilder(KitPvPKit.Level kit, Type type, Color color, int amount, String displayName) {
        super(type, color, amount, displayName);
        setup(kit);
    }

    public KitLeatherArmorBuilder(KitPvPKit.Level kit, Type type, Color color, int amount, String displayName, String... lore) {
        super(type, color, amount, displayName, lore);
        setup(kit);
    }

    public KitLeatherArmorBuilder(KitPvPKit.Level kit, Type type, Color color, int amount, String displayName, List<String> lore) {
        super(type, color, amount, displayName, lore);
        setup(kit);
    }

    public KitLeatherArmorBuilder(KitPvPKit.Level kit, LeatherArmorBuilder itemBuilder) {
        super(itemBuilder);
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

    public KitLeatherArmorBuilder addPassive(Passive passive, Integer level) {
        return kitItem.addPassive(passive, level);
    }

    public KitLeatherArmorBuilder addActive(Active active, Integer level) {
        return kitItem.addActive(active, level);
    }

    public KitLeatherArmorBuilder applyNewPassive(Set<Passive> newPassives) {
        return kitItem.applyNewPassive(newPassives);
    }

    public KitLeatherArmorBuilder applyRemovedPassive(Set<Passive> removedPassives) {
        return kitItem.applyRemovedPassive(removedPassives);
    }

    public KitLeatherArmorBuilder applyNewActives(Set<Active> newActives) {
        return kitItem.applyNewActives(newActives);
    }

    public KitLeatherArmorBuilder applyRemovedActive(Set<Active> removedActives) {
        return kitItem.applyRemovedActive(removedActives);
    }
}
