package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.item_builders;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Active;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.KitPvPKit;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ItemUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilderInstance;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.itemstack.ItemStackNms;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class KitItemInstance<B extends ItemBuilderInstance> {

    private final KitPvPKit.Level kit;
    private final B builder;

    @Getter private final Map<Passive, Integer> passives;
    @Getter private final Map<Active, Integer> actives;

    private Set<Passive> newPassives;
    private Set<Passive> removedPassives;
    private Set<Active> newActives;
    private Set<Active> removedActives;

    public KitItemInstance(KitPvPKit.Level kit, B builder) {
        this.kit = kit;
        this.builder = builder;
        this.passives = new HashMap<>();
        this.actives = new HashMap<>();

        builder.unbreakable(true);
    }

    public B addPassive(Passive passive, Integer level) {
        this.passives.put(passive, level);

        builder.glow();

        return builder;
    }

    public B addActive(Active active, Integer level) {
        this.actives.put(active, level);

        builder.glow();
        builder.setDisplayName(active.getColor().getCc() + "§l" + active.getName());

        return builder;
    }

    public B applyNewPassive(Set<Passive> newPassives) {
        this.newPassives = newPassives;
        return builder;
    }

    public B applyRemovedPassive(Set<Passive> removedPassives) {
        this.removedPassives = removedPassives;
        return builder;
    }

    public B applyNewActives(Set<Active> newActives) {
        this.newActives = newActives;
        return builder;
    }

    public B applyRemovedActive(Set<Active> removedActives) {
        this.removedActives = removedActives;
        return builder;
    }

    @Deprecated //TODO: Update this
    public ItemStack apply(ItemStack item) {
        if (item.getItemMeta().getDisplayName().isEmpty()) {
            String displayName = kit.getHandler().getDisplayName() + "'s " + ItemUtils.getName(builder.getMaterial());

            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(displayName);
            meta.setLocalizedName(displayName);
            item.setItemMeta(meta);
        }

        ItemStackNms nms = kit.getHandler().getServer().getNms().customItem();

        /* Apply passives */
        {
            List<Passive> ordered = new ArrayList<>(this.passives.keySet());
            ordered.sort(Comparator.comparing(Enum::ordinal));
            for (Passive passive : ordered) {
                int level = this.passives.get(passive);

                item = passive.apply(nms, item, level);

                if (passive == Passive.ATTACK_DAMAGE)
                    /* We handle this in apply */
                    continue;

                ItemMeta meta = item.getItemMeta();
                List<String> lore = meta.getLore() != null ? meta.getLore() : new ArrayList<>();

                if (passive.isBreakLine())
                    lore.add("");

                if (newPassives != null && newPassives.contains(passive)) {
                    lore.add("§a§l+NEW! " + passive.getDisplayName(level));
                } else if (removedPassives != null && removedPassives.contains(passive)) {
                    lore.add("§c§m" + Color.stripColor(passive.getDisplayName(level)));

                    meta.setLore(lore);
                    item.setItemMeta(meta);
                    continue;
                } else {
                    lore.add(passive.getDisplayName(level));
                }

                lore.addAll(Arrays.asList(passive.getDescription(level)));
                if (passive.isStackable())
                    lore.add("  §2§o[stackable]");

                meta.setLore(lore);
                item.setItemMeta(meta);
            }
        }

        /* Apply actives */
        {
            List<Active> ordered = new ArrayList<>(this.actives.keySet());
            ordered.sort(Comparator.comparing(Enum::ordinal));
            for (Active active : ordered) {
                int level = this.actives.get(active);

                item = active.apply(nms, item, level);

                ItemMeta meta = item.getItemMeta();
                List<String> lore = meta.getLore() != null ? meta.getLore() : new ArrayList<>();

                lore.add(active.getDisplayName(level));
                lore.addAll(Arrays.asList(active.getDescription(level)));

                meta.setLore(lore);
                item.setItemMeta(meta);
            }
        }

        return item;
    }
}
