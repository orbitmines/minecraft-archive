package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.item_builders;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Active;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.KitPvPKit;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.PlayerSkullBuilder;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class KitPlayerSkullBuilder extends PlayerSkullBuilder implements KitItemBuilderInstance {

    @Getter private KitItemInstance<KitPlayerSkullBuilder> kitItem;

    public KitPlayerSkullBuilder(KitPvPKit.Level kit, UUID uuid) {
        super(uuid);
        setup(kit);
    }

    public KitPlayerSkullBuilder(KitPvPKit.Level kit, UUID uuid, int amount) {
        super(uuid, amount);
        setup(kit);
    }

    public KitPlayerSkullBuilder(KitPvPKit.Level kit, UUID uuid, int amount, String displayName) {
        super(uuid, amount, displayName);
        setup(kit);
    }

    public KitPlayerSkullBuilder(KitPvPKit.Level kit, UUID uuid, int amount, String displayName, String... lore) {
        super(uuid, amount, displayName, lore);
        setup(kit);
    }

    public KitPlayerSkullBuilder(KitPvPKit.Level kit, UUID uuid, int amount, String displayName, List<String> lore) {
        super(uuid, amount, displayName, lore);
        setup(kit);
    }

    public KitPlayerSkullBuilder(KitPvPKit.Level kit, String textureName, String texture) {
        super(textureName, texture);
        setup(kit);
    }

    public KitPlayerSkullBuilder(KitPvPKit.Level kit, String textureName, String texture, int amount) {
        super(textureName, texture, amount);
        setup(kit);
    }

    public KitPlayerSkullBuilder(KitPvPKit.Level kit, String textureName, String texture, int amount, String displayName) {
        super(textureName, texture, amount, displayName);
        setup(kit);
    }

    public KitPlayerSkullBuilder(KitPvPKit.Level kit, String textureName, String texture, int amount, String displayName, String... lore) {
        super(textureName, texture, amount, displayName, lore);
        setup(kit);
    }

    public KitPlayerSkullBuilder(KitPvPKit.Level kit, String textureName, String texture, int amount, String displayName, List<String> lore) {
        super(textureName, texture, amount, displayName, lore);
        setup(kit);
    }

    public KitPlayerSkullBuilder(KitPvPKit.Level kit, PlayerSkullBuilder itemBuilder) {
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

    public KitPlayerSkullBuilder addPassive(Passive passive, Integer level) {
        return kitItem.addPassive(passive, level);
    }

    public KitPlayerSkullBuilder addActive(Active active, Integer level) {
        return kitItem.addActive(active, level);
    }

    public KitPlayerSkullBuilder applyNewPassive(Set<Passive> newPassives) {
        return kitItem.applyNewPassive(newPassives);
    }

    public KitPlayerSkullBuilder applyRemovedPassive(Set<Passive> removedPassives) {
        return kitItem.applyRemovedPassive(removedPassives);
    }

    public KitPlayerSkullBuilder applyNewActives(Set<Active> newActives) {
        return kitItem.applyNewActives(newActives);
    }

    public KitPlayerSkullBuilder applyRemovedActive(Set<Active> removedActives) {
        return kitItem.applyRemovedActive(removedActives);
    }
}
