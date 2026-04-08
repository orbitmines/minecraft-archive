package com.orbitmines.archive.minecraft.spigot._2019.servers.creative.kit;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.Creative;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.CreativePlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.gui.WorldCreateGUI;
import com.orbitmines.archive.minecraft.spigot._2019.servers.creative.gui.WorldListGUI;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.kits.interactive.InteractiveKit;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CreativeLobbyKit extends InteractiveKit<Creative, CreativePlayer> {

    /* Inventory slots for the top-right items (always given) */
    public static final int SLOT_WORLD_MANAGER = 17;
    public static final int SLOT_CREATE_WORLD = 16;

    public CreativeLobbyKit(Creative server) {

        /* Top-right inventory: World Manager (slot 17) */
        set(SLOT_WORLD_MANAGER,
            player -> new ItemBuilder(Material.NETHER_STAR, 1,
                "§d§l" + player.translate("creative", "player.item.world_manager"),
                "§7" + player.translate("creative", "player.item.world_manager.description"))
        );

        /* Top-right inventory: Create New World (slot 16) */
        set(SLOT_CREATE_WORLD,
            player -> new ItemBuilder(Material.GRASS_BLOCK, 1,
                "§a§l" + player.translate("creative", "player.item.create_world"),
                "§7" + player.translate("creative", "player.item.create_world.description"))
        );

        /* Hotbar slot 3: World Manager */
        set(3,
            player -> new ItemBuilder(Material.NETHER_STAR, 1, "§5 "),

            new InteractiveKit.Interaction<Creative, CreativePlayer>(server) {
                @Override
                public void onInteract(CreativePlayer player, PlayerInteractEvent event, ItemStack itemStack) {
                    new WorldListGUI(server, player).open();
                }
            }.onActionBarHover(
                (player, item) -> "§d§l" + player.translate("creative", "player.item.world_manager") + "§r §8- §e§l" + player.translate("spigot", "player.mouse.right_click")
            )
        );

        /* Hotbar slot 4 (selected on first join): Create New World */
        set(4,
            player -> new ItemBuilder(Material.GRASS_BLOCK, 1, "§2 "),

            new InteractiveKit.Interaction<Creative, CreativePlayer>(server) {
                @Override
                public void onInteract(CreativePlayer player, PlayerInteractEvent event, ItemStack itemStack) {
                    new WorldCreateGUI(server, player).open();
                }
            }.onActionBarHover(
                (player, item) -> "§a§l" + player.translate("creative", "player.item.create_world") + "§r §8- §e§l" + player.translate("spigot", "player.mouse.right_click")
            )
        );

        /* Hotbar slot 5: WorldEdit Wand */
        set(5,
            player -> new ItemBuilder(Material.WOODEN_AXE, 1, "§6 "),

            new InteractiveKit.Interaction<Creative, CreativePlayer>(server) {
                @Override
                public void onInteract(CreativePlayer player, PlayerInteractEvent event, ItemStack itemStack) {
                    /* WorldEdit wand — interaction handled by FAWE */
                }
            }.onActionBarHover(
                (player, item) -> "§e§l" + player.translate("creative", "player.item.worldedit_wand") + "§r §8- §e§l" + player.translate("spigot", "player.mouse.right_click")
            )
        );
    }
}
