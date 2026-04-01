package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.item_builders;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Active;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.KitPvPKit;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.PotionBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.PotionItemBuilder;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class KitPotionItemBuilder extends PotionItemBuilder implements KitItemBuilderInstance {

    @Getter private KitItemInstance<KitPotionItemBuilder> kitItem;

    public KitPotionItemBuilder(KitPvPKit.Level kit, Type type) {
        super(type);
        setup(kit);
    }

    public KitPotionItemBuilder(KitPvPKit.Level kit, Type type, PotionBuilder potionBuilder) {
        super(type, potionBuilder);
        setup(kit);
    }

    public KitPotionItemBuilder(KitPvPKit.Level kit, Type type, PotionBuilder potionBuilder, int amount) {
        super(type, potionBuilder, amount);
        setup(kit);
    }

    public KitPotionItemBuilder(KitPvPKit.Level kit, Type type, PotionBuilder potionBuilder, int amount, String displayName) {
        super(type, potionBuilder, amount, displayName);
        setup(kit);
    }

    public KitPotionItemBuilder(KitPvPKit.Level kit, Type type, PotionBuilder potionBuilder, int amount, String displayName, String... lore) {
        super(type, potionBuilder, amount, displayName, lore);
        setup(kit);
    }

    public KitPotionItemBuilder(KitPvPKit.Level kit, Type type, PotionBuilder potionBuilder, int amount, String displayName, List<String> lore) {
        super(type, potionBuilder, amount, displayName, lore);
        setup(kit);
    }

    public KitPotionItemBuilder(KitPvPKit.Level kit, PotionItemBuilder itemBuilder) {
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

    public KitPotionItemBuilder addPassive(Passive passive, Integer level) {
        return kitItem.addPassive(passive, level);
    }

    public KitPotionItemBuilder addActive(Active active, Integer level) {
        return kitItem.addActive(active, level);
    }

    public KitPotionItemBuilder applyNewPassive(Set<Passive> newPassives) {
        return kitItem.applyNewPassive(newPassives);
    }

    public KitPotionItemBuilder applyRemovedPassive(Set<Passive> removedPassives) {
        return kitItem.applyRemovedPassive(removedPassives);
    }

    public KitPotionItemBuilder applyNewActives(Set<Active> newActives) {
        return kitItem.applyNewActives(newActives);
    }

    public KitPotionItemBuilder applyRemovedActive(Set<Active> removedActives) {
        return kitItem.applyRemovedActive(removedActives);
    }
}
