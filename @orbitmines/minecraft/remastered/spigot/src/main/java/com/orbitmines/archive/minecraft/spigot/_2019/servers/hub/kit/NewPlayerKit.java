package com.orbitmines.archive.minecraft.spigot._2019.servers.hub.kit;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.guis.ServerSelectorGUI;
import com.orbitmines.archive.minecraft.spigot._2019.servers.hub.Hub;
import com.orbitmines.archive.minecraft.spigot._2019.servers.hub.player.HubPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.PotionBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.kits.interactive.InteractiveKit;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class NewPlayerKit extends InteractiveKit<Hub, HubPlayer> {

    public NewPlayerKit(Hub hub) {
        addPotionBuilder(new PotionBuilder(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, true, false));

        setHelmet(player -> new ItemBuilder(Material.WHITE_STAINED_GLASS, 1, "§7Helmet"));

        set(4,
            player -> new ItemBuilder(Material.ENDER_PEARL, 1, "§4 "),

            new Interaction<Hub, HubPlayer>(hub) {
                @Override
                public void onInteract(HubPlayer player, PlayerInteractEvent event, ItemStack itemStack) {
                    new ServerSelectorGUI<>(player).open();
                }
            }.onActionBarHover(
                (player, item) -> "§3§lServer Selector§r §8- §e§l" + player.translate("spigot", "player.mouse.right_click")
            )
        );
    }
}
