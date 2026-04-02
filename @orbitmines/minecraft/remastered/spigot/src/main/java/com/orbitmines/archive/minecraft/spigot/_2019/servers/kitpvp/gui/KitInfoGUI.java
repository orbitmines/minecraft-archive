package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.gui;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.kitpvp.KitPvPPlayerKitModel;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.kitpvp.KitPvPPlayerModel;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.utils.SkullTexture;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.datapoints.lobby.KitPvPDataPointLobbyKitInfo;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.item_builders.KitItemBuilderInstance;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.item_builders.KitItemInstance;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit.KitPvPKit;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ItemUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.PotionBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilderInstance;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.PlayerSkullBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.PotionItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutablePlayerItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.GUI;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.GUICenterHelper;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.kits.Kit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class KitInfoGUI extends GUI<KitPvPPlayer> implements GUICenterHelper<KitPvPPlayer, KitPvPKit.Level> {

    private final KitPvPKit.Level kit;

    public KitInfoGUI(KitPvPPlayer viewer, KitPvPKit.Level kit) {
        super(54, "§0§l" + kit.getHandler().getName() + " (Level " + kit.getLevel() + ")", viewer);

        this.kit = kit;

        /* Contents */
        setItem(0, 4, kit.getKit().getHelmet(), Type.HELMET);
        setItem(1, 4, kit.getKit().getChestplate(), Type.CHESTPLATE);
        setItem(2, 4, kit.getKit().getLeggings(), Type.LEGGINGS);
        setItem(3, 4, kit.getKit().getBoots(), Type.BOOTS);

        int count = kit.getKit().contentSize();

        {
            int row = 0;
            int slot = 2;
            for (int i = 0; i < count; i++) {
                setItem(row, slot, kit.getKit().firstOfIndex(i), Type.CONTENT);

                if (slot == 2) {
                    slot = 6;
                } else {
                    slot = 2;
                    row++;
                }
            }
        }

        /* Info */
        for (KitPvPDataPointLobbyKitInfo.KitInfo info : KitPvPDataPointLobbyKitInfo.KitInfo.values()) {
            set(info.getRow(), info.getSlot(), new Item<KitPvPPlayer, MutableItemBuilder>(() -> {
                ItemBuilderInstance icon = info.getIcon().setDisplayName(info.getDisplayName()).addLore("§7§o" + info.getDescription(viewer, kit));
                if (info == KitPvPDataPointLobbyKitInfo.KitInfo.MAX_HEALTH)
                    icon.setAmount((int) kit.getMaxHealth());
                return icon;
            }));
        }

        center(5, () -> Arrays.asList(kit.getHandler().getLevels()));

        set(5, 0, new Item<KitPvPPlayer, MutableItemBuilder>(() -> {
            return new PlayerSkullBuilder("Red Arrow Left", SkullTexture.RED_ARROW_LEFT, 1, "§c« Back to Kit Selector");
        }, event -> {
            new KitSelectorGUI(viewer).open();
        }));

        set(5, 8, new Item<KitPvPPlayer, MutableItemBuilder>(() -> {
            KitPvPPlayerKitModel model = viewer.getKit(kit.getHandler(), false);

            int level = kit.getLevel();
            int unlockedLevel = model.getUnlockedLevel();

            if (unlockedLevel >= level)
                return new ItemBuilder(Material.LIME_TERRACOTTA, level, "§a§lUNLOCKED");
            else if (unlockedLevel == level - 1)
                return new ItemBuilder(Material.RED_TERRACOTTA, level, "§4§lLOCKED", "§7Price: §6§l" + NumberUtils.locale(kit.getPrice()), "", "§aClick here to buy.");
            else
                return new ItemBuilder(Material.GRAY_TERRACOTTA, level, "§8§lUNAVAILABLE", "§8Price: §8§l" + NumberUtils.locale(kit.getPrice()));
        }, event -> {
            KitPvPPlayerKitModel model = viewer.getKit(kit.getHandler(), false);

            int level = kit.getLevel();
            int unlockedLevel = model.getUnlockedLevel();

            if (unlockedLevel >= level)
                return;
            else if (unlockedLevel != level - 1)
                return;

            if (viewer.getCoins() >= kit.getPrice()) {
                viewer.removeCoins(kit.getPrice());
                viewer.update(KitPvPPlayerModel.column.COINS);

                model.setUnlockedLevel(level);
                model.insertOrUpdate(KitPvPPlayerKitModel.column.UNLOCKED_LEVEL);

                viewer.playSound(Sound.ENTITY_PLAYER_LEVELUP);

                update();
            } else {
                viewer.playSound(Sound.ENTITY_ENDERMAN_SCREAM);
                viewer.sendRawMessage("Shop", Color.RED, "You don't have enough §6§lCoins§7.");
            }
        }));

        {
            List<PotionBuilder> potionEffects = kit.getKit().getPotionBuilders();
            int size = potionEffects.size();

            /* In order to center  */
            int slot = 4 - (size % 2 == 0 ? size / 2 : (size - 1) / 2);

            for (int i = 0; i < size; i++) {
                /* Skip middle slot whenever a the amount of types is even to center types correctly */
                if (slot == 4 && size % 2 == 0) {
                    clear(3, slot);
                    slot++;
                }

                //TODO NEW WHEN CHANGES IS LEVELS

                PotionBuilder effect = potionEffects.get(i);

                set(4, slot, new Item<KitPvPPlayer, MutableItemBuilder>(() -> {
                    return new PotionItemBuilder(
                        PotionItemBuilder.Type.NORMAL,
                        effect,
                        effect.getAmplifier() + 1,
                        (ItemUtils.POSITIVE_EFFECTS.contains(effect.getType()) ? "§a§l" : "§c§l") + ItemUtils.getName(effect.getType()) + " " + NumberUtils.toRoman(effect.getAmplifier() + 1)
                    );
                }));

                slot++;
            }
        }
    }

    @Override
    public GUI<KitPvPPlayer> getInstance() {
        return this;
    }

    @Override
    public Item<KitPvPPlayer, MutableItemBuilder> getItem(KitPvPKit.Level object) {
        return new Item<KitPvPPlayer, MutableItemBuilder>(() -> {
            int level = object.getLevel();
            KitPvPPlayerKitModel model = viewer.getKit(object.getHandler(), false);

            ItemBuilder item;

            if (model.getUnlockedLevel() >= level) {
                /* Unlocked */
                item = new ItemBuilder(Material.LIME_DYE, level);

                item.setDisplayName("§a§lLvl " + level + " §a§lUNLOCKED");
            } else {
                item = new ItemBuilder(Material.GRAY_DYE, level);

                /* Locked */
                item.setDisplayName("§a§lLvl " + level + " §4§lLOCKED");
            }

            item.addLore("");

            if (level == this.kit.getLevel()) {
                item.glow();
                item.addLore("§cAlready selected.");
            } else{
                item.addLore("§aClick here to select.");
            }

            return item;
        }, event -> {
            new KitInfoGUI(viewer, object).open();
        });
    }

    private void setItem(int row, int slot, MutablePlayerItemBuilder<?, KitPvPPlayer> itemBuilder, Type type) {
        if (itemBuilder == null)
            return;

        if (kit.getLevel() == 1) {
            set(row, slot, new Item<KitPvPPlayer, MutableItemBuilder>(() -> itemBuilder.toBuilder(viewer)));
            return;
        }

        set(row, slot, new Item<KitPvPPlayer, MutableItemBuilder>(() -> {
            ItemBuilderInstance<?, ?> item = itemBuilder.toBuilder(viewer);

            /*
                Show added to new level
             */
            KitPvPKit.Level prevLevel = kit.getHandler().getLevel(kit.getLevel() - 1);
            Kit<KitPvPPlayer> kit = prevLevel.getKit();

            MutablePlayerItemBuilder<?, KitPvPPlayer> prevItem;
            switch (type) {

                case HELMET:
                    prevItem = kit.getHelmet();
                    break;
                case CHESTPLATE:
                    prevItem = kit.getChestplate();
                    break;
                case LEGGINGS:
                    prevItem = kit.getLeggings();
                    break;
                case BOOTS:
                    prevItem = kit.getBoots();
                    break;
                case CONTENT:
                    prevItem = kit.get(this.kit.getKit().indexOf(itemBuilder));
                    break;
                default:
                    throw new NullPointerException();
            }
            ItemStack exampleItem = item.build();

            if (prevItem == null) {
                /* Cviewerletely new item */
                item.setDisplayName("§a§l+NEW! " + exampleItem.getItemMeta().getDisplayName());
            } else {
                /* Clone it */
                ItemStack prevItemStack = prevItem.toBuilder(viewer).build();

                if (item.getMaterial() != prevItemStack.getType()) {
                    item.setDisplayName("§a§l+NEW! " + exampleItem.getItemMeta().getDisplayName());
                    item.addLore("§c§l§m" + Color.stripColor(prevItemStack.getItemMeta().getDisplayName()));
                } else if (item.getAmount() != prevItemStack.getAmount()) {
                    int diff = item.getAmount() - prevItemStack.getAmount();

                    if (diff > 0)
                        item.addLore("§a§l+" + diff + "x " + exampleItem.getItemMeta().getDisplayName());
                    else
                        item.addLore("§c§l" + diff + "x " + exampleItem.getItemMeta().getDisplayName());
                }

                KitItemInstance kitItem = as(itemBuilder);
                KitItemInstance kitItemPrev = as(prevItem);

                /* Passives */
                Map<Passive, Integer> passives = new HashMap<>(kitItem.getPassives());
                Map<Passive, Integer> passivesPrev = new HashMap<>(kitItemPrev.getPassives());

                Set<Passive> newPassives = new HashSet<>();
                Set<Passive> removedPassives = new HashSet<>();

                for (Passive passive : passives.keySet()) {
                    if (!passivesPrev.containsKey(passive)) {
                        newPassives.add(passive);
                    } else if (!passivesPrev.get(passive).equals(passives.get(passive))) {
                        newPassives.add(passive);
                        item.addLore("§c§m" + Color.stripColor(passive.getDisplayName(passivesPrev.get(passive))));
                    }
                }
                for (Passive passive : passivesPrev.keySet()) {
                    if (!passives.containsKey(passive))
                        removedPassives.add(passive);
                }

                kitItem.applyNewPassive(newPassives);
                kitItem.applyRemovedPassive(removedPassives);

                /* Enchantments */
                for (Enchantment enchantment : item.getEnchantments().keySet()) {
                    item.addFlag(ItemFlag.HIDE_ENCHANTS);

                    if (enchantment.getName().equalsIgnoreCase(ItemBuilder.GLOW_ENCHANTMENT.getName()))
                        continue;

                    String prefix = "";
                    if (!prevItemStack.getEnchantments().containsKey(enchantment)) {
                        prefix = "§a§l+NEW! ";
                    } else if (!prevItemStack.getEnchantments().get(enchantment).equals(item.getEnchantments().get(enchantment))) {
                        prefix = "§a§l+NEW! ";
                        item.addLore("§c§m" + Color.stripColor(ItemUtils.getName(enchantment, prevItemStack.getEnchantments().get(enchantment))));
                    }

                    item.addLore(prefix + "§7" + ItemUtils.getName(enchantment, item.getEnchantments().get(enchantment)));
                }
            }

            return item;
        }));
    }

    private KitItemInstance as(MutablePlayerItemBuilder<?, KitPvPPlayer> item) {
        ItemBuilderInstance builderInstance = item.toBuilder(viewer);

        return ((KitItemBuilderInstance) builderInstance).getKitItem();
    }

    private enum Type {

        HELMET,
        CHESTPLATE,
        LEGGINGS,
        BOOTS,
        CONTENT;

    }
}
