package com.orbitmines.archive.minecraft.spigot._2019.servers.hub.kit;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.discord.SpigotDiscordBot;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.guis.ServerSelectorGUI;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.guis.SettingsGUI;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.guis.discord_squad.DiscordSquadGUI;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.guis.friends.FriendGUI;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.guis.stats.StatsGUI;
import com.orbitmines.archive.minecraft.spigot._2019.servers.hub.Hub;
import com.orbitmines.archive.minecraft.spigot._2019.servers.hub.player.HubPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.PotionBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.PlayerSkullBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.kits.interactive.InteractiveKit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class HubKit extends InteractiveKit<Hub, HubPlayer> {

    public HubKit(Hub hub) {
        addPotionBuilder(new PotionBuilder(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, true, false));

        setHelmet(player -> new ItemBuilder(Material.WHITE_STAINED_GLASS, 1, "§7Helmet"));

        set(1,
            player -> new ItemBuilder(Material.EMERALD, 1, "§2 "),

            new InteractiveKit.Interaction<Hub, HubPlayer>(hub) {
                @Override
                public void onInteract(HubPlayer player, PlayerInteractEvent event, ItemStack itemStack) {
                    new StatsGUI<>(hub, player, player).open();
                }
            }.onActionBarHover(
                (player, item) -> "§a§l" + player.translate("spigot", "word.stats") + "§r §8- §e§l" + player.translate("spigot", "player.mouse.right_click")
            )
        );

        set(2,
            player -> new PlayerSkullBuilder(player.getUUID(), 1, "§3 "),

            new InteractiveKit.Interaction<Hub, HubPlayer>(hub) {
                @Override
                public void onInteract(HubPlayer player, PlayerInteractEvent event, ItemStack itemStack) {
                    new FriendGUI<>(player, player).open();
                }
            }.onActionBarHover(
                (player, item) -> "§b§l" + player.translate("spigot", "word.friend.plural") + "§r §8- §e§l" + player.translate("spigot", "player.mouse.right_click")
            )
        );

        set(4,
            player -> new ItemBuilder(Material.ENDER_PEARL, 1, "§4 "),

            new InteractiveKit.Interaction<Hub, HubPlayer>(hub) {
                @Override
                public void onInteract(HubPlayer player, PlayerInteractEvent event, ItemStack itemStack) {
                    new ServerSelectorGUI<>(player).open();
                }
            }.onActionBarHover(
                (player, item) -> "§3§lServer Selector§r §8- §e§l" + player.translate("spigot", "player.mouse.right_click")
            )
        );

        set(6,
            player -> new ItemBuilder(Material.REDSTONE_TORCH, 1, "§5 "),

            new InteractiveKit.Interaction<Hub, HubPlayer>(hub) {
                @Override
                public void onInteract(HubPlayer player, PlayerInteractEvent event, ItemStack itemStack) {
                     player.playEffect(player.getLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);

                     new SettingsGUI<>(player, player).open();
                }
            }.onActionBarHover(
                (player, item) -> "§c§l" + player.translate("spigot", "word.settings") + "§r §8- §e§l" + player.translate("spigot", "player.mouse.right_click")
            )
        );

        if (hub.getDiscordBot() != null) {
            set(7,
                player -> new PlayerSkullBuilder("Discord Skull", SpigotDiscordBot.SKULL_TEXTURE, 1, "§6 "),

                new Interaction<Hub, HubPlayer>(hub) {
                    @Override
                    public void onInteract(HubPlayer player, PlayerInteractEvent event, ItemStack itemStack) {
                        hub.runAsync(() -> new DiscordSquadGUI<>(player, player).open());
                    }
                }.onActionBarHover(
                    (player, item) -> "§9§lDiscord Squad§r §8- §e§l" + player.translate("spigot", "player.mouse.right_click")
                )
            );
        }
    }
}
