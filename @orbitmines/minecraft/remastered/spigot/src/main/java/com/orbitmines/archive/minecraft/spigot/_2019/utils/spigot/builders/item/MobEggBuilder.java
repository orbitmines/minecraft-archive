package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.entities.Mob;
import lombok.Getter;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class MobEggBuilder extends ItemBuilderInstance<MobEggBuilder, ItemMeta> {

    @Getter protected Mob mob;

    public MobEggBuilder(Mob mob) {
        super(mob.getSpawnEggMaterial());
    }

    public MobEggBuilder(Mob mob, int amount) {
        super(mob.getSpawnEggMaterial(), amount);

        this.mob = mob;
    }

    public MobEggBuilder(Mob mob, int amount, String displayName) {
        super(mob.getSpawnEggMaterial(), amount, displayName);

        this.mob = mob;
    }

    public MobEggBuilder(Mob mob, int amount, String displayName, String... lore) {
        super(mob.getSpawnEggMaterial(), amount, displayName, lore);

        this.mob = mob;
    }

    public MobEggBuilder(Mob mob, int amount, String displayName, List<String> lore) {
        super(mob.getSpawnEggMaterial(), amount, displayName, lore);

        this.mob = mob;
    }

    public MobEggBuilder(MobEggBuilder itemBuilder) {
        super(itemBuilder);

        this.mob = itemBuilder.mob;
    }

    public MobEggBuilder setMob(Mob mob) { this.mob = mob; return this; }
    
    @Override
    public MobEggBuilder clone() {
        return new MobEggBuilder(this);
    }

    @Override
    protected MobEggBuilder getInstance() {
        return this;
    }

}
