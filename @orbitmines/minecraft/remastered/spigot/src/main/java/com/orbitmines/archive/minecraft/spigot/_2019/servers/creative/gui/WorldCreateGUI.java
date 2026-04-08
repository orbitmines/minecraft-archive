package com.orbitmines.archive.minecraft.spigot._2019.servers.creative.gui;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.database.models.PlayerModel;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.creative.CreativePlayerModel;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.Creative;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.CreativePlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.CreativeWorld;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.GUI;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.generators.DefaultWorldType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;

public class WorldCreateGUI extends GUI<CreativePlayer> {

    public static final int FREE_WORLDS = 3;
    public static final int WORLD_COST = 2500;

    private final Creative creative;

    public WorldCreateGUI(Creative creative, CreativePlayer viewer) {
        super(27, "§0§l" + viewer.translate("creative", "player.world.create.title"), viewer);
        this.creative = creative;

        int totalSlots = FREE_WORLDS + viewer.getModel().getExtraWorldSlots();
        boolean isFree = viewer.getWorlds().size() < totalSlots;

        /* Cost display */
        set(0, 4, new Item<CreativePlayer, MutableItemBuilder>(() -> {
            if (isFree) {
                int remaining = totalSlots - viewer.getWorlds().size();
                return new ItemBuilder(Material.PRISMARINE_SHARD, 1,
                    "§d§lFREE",
                    "§7" + viewer.translate("creative", "player.world.create.free_slots", String.valueOf(remaining)));
            } else {
                return new ItemBuilder(Material.PRISMARINE_SHARD, 1,
                    "§7" + viewer.translate("spigot", "player.prism_shop.price") + ": §9§l" + NumberUtils.locale(WORLD_COST) + " Prisms",
                    "",
                    "§7" + viewer.translate("creative", "player.world.create.balance", "§9§l" + NumberUtils.locale(viewer.getPrisms())));
            }
        }));

        /* Void */
        set(1, 1, worldTypeItem(DefaultWorldType.VOID, Material.GLASS, "§f§lVoid", isFree));

        /* Flat */
        set(1, 3, worldTypeItem(DefaultWorldType.FLAT, Material.SMOOTH_STONE, "§7§lFlat", isFree));

        /* Normal */
        set(1, 5, worldTypeItem(DefaultWorldType.NORMAL, Material.GRASS_BLOCK, "§2§lNormal", isFree));

        /* Nether */
        set(1, 6, worldTypeItem(DefaultWorldType.NETHER, Material.NETHERRACK, "§c§lNether", isFree));

        /* End */
        set(1, 7, worldTypeItem(DefaultWorldType.END, Material.END_STONE, "§e§lEnd", isFree));
    }

    private Item<CreativePlayer, MutableItemBuilder> worldTypeItem(DefaultWorldType type, Material icon, String name, boolean isFree) {
        return new Item<>(() -> {
            ItemBuilder item = new ItemBuilder(icon, 1, name);

            item.addLore("");
            if (isFree) {
                item.addLore("§d§lFREE");
            } else {
                item.addLore("§7" + viewer.translate("spigot", "player.prism_shop.price") + ": §9§l" + NumberUtils.locale(WORLD_COST) + " Prisms");
            }

            return item;
        }, event -> {
            if (!isFree) {
                if (viewer.getPrisms() < WORLD_COST) {
                    viewer.playSound(Sound.ENTITY_ENDERMAN_SCREAM);
                    viewer.sendMessage("Shop", Color.RED, "spigot", "player.prism_shop.insufficient_funds", "§9§lPrisms§7");
                    return;
                }

                viewer.removePrisms(WORLD_COST);
                viewer.update(PlayerModel.column.PRISMS);

                viewer.getModel().addExtraWorldSlots(1);
                if (!viewer.getModel().isInserted())
                    viewer.getModel().insert();
                else
                    viewer.getModel().update(CreativePlayerModel.column.EXTRA_WORLD_SLOTS);

                viewer.sendMessage("Shop", Color.SUCCESS, "spigot", "player.prism_shop.received", "§d§l1 extra world slot§7");
            }

            close();

            int id = viewer.getNextWorldId();
            String worldFileName = viewer.getUUID().toString().toLowerCase() + "_" + id;
            String worldName = String.valueOf(id);

            viewer.sendMessage("Creative", Color.FUCHSIA, "creative", "player.world.creating", "§d" + worldName + "§7");

            CreativeWorld world = new CreativeWorld(creative, viewer.getUUID(), worldName, worldFileName, type);
            world.setOwnerName(viewer.getName(Name.RAW_COLORED));
            world.insert();

            viewer.getWorlds().add(world);
            creative.getAllWorlds().add(world);

            viewer.playSound(Sound.ENTITY_PLAYER_LEVELUP);

            /* Place bedrock below spawn in void worlds on creation */
            if (type == DefaultWorldType.VOID) {
                creative.runSync(() -> {
                    World bukkit = world.loadOrCreateWorld();
                    if (bukkit != null) {
                        Location spawn = bukkit.getSpawnLocation();
                        if (spawn.getBlock().getRelative(0, -1, 0).getType() == Material.AIR) {
                            spawn.getBlock().getRelative(0, -1, 0).setType(Material.BEDROCK);
                        }
                    }
                });
            }

            creative.loadAndTeleport(viewer, world);
        });
    }
}
