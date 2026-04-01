package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.kit;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.guis.ServerSelectorGUI;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.guis.stats.StatsGUI;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.CoinBooster;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.gui.CoinBoosterGUI;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.gui.KitPvPPrismSolarShopGUI;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.gui.KitSelectorGUI;
import com.orbitmines.archive.minecraft._2019.utils.TimeUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.PotionBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.kits.interactive.InteractiveKit;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class LobbyKit extends InteractiveKit<KitPvP, KitPvPPlayer> {

    public LobbyKit(KitPvP server) {
        addPotionBuilder(new PotionBuilder(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, true, false));

        setHelmet(player -> new ItemBuilder(Material.WHITE_STAINED_GLASS, 1, "§7Helmet"));

        set(1,
            player -> new ItemBuilder(Material.EMERALD, 1, "§1 "),

            new InteractiveKit.Interaction<KitPvP, KitPvPPlayer>(server) {
                @Override
                public void onInteract(KitPvPPlayer player, PlayerInteractEvent event, ItemStack itemStack) {
                    new StatsGUI<>(server, player, player).open();
                }
            }.onActionBarHover(
                (player, item) -> "§a§l" + player.translate("spigot", "word.stats") + "§r §8- §e§l" + player.translate("spigot", "player.mouse.right_click")
            )
        );

        set(3,
            player -> new ItemBuilder(Material.PRISMARINE_SHARD, 1, "§2 "),

            new InteractiveKit.Interaction<KitPvP, KitPvPPlayer>(server) {
                @Override
                public void onInteract(KitPvPPlayer player, PlayerInteractEvent event, ItemStack itemStack) {
                    new KitPvPPrismSolarShopGUI(player).open();

                    player.updateInventory();
                }
            }.onActionBarHover(
                (player, item) -> "§9§lPrism Shop§r §8- §e§l" + player.translate("spigot", "player.mouse.right_click")
            )
        );

        set(4,
            player -> new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1, "§3 ").addFlag(ItemFlag.HIDE_ATTRIBUTES),

            new InteractiveKit.Interaction<KitPvP, KitPvPPlayer>(server) {
                @Override
                public void onInteract(KitPvPPlayer player, PlayerInteractEvent event, ItemStack itemStack) {
                    new KitSelectorGUI(player).open();

                    player.updateInventory();
                }
            }.onActionBarHover(
                (player, item) -> "§b§lKit Selector§r §8- §e§l" + player.translate("spigot", "player.mouse.right_click")
            )
        );

        set(5,
            player -> new ItemBuilder(Material.GOLD_NUGGET, 1, "§4 "),

            new InteractiveKit.Interaction<KitPvP, KitPvPPlayer>(server) {
                @Override
                public void onInteract(KitPvPPlayer player, PlayerInteractEvent event, ItemStack itemStack) {
                    if (CoinBooster.ACTIVE == null)
                        new CoinBoosterGUI(player).open();
                }
            }.onActionBarHover(
                (player, item) -> "§6§lCoin Booster§r §8- §e§l" +
                    (CoinBooster.ACTIVE == null ?
                        player.translate("spigot", "player.mouse.right_click") :
                        CoinBooster.ACTIVE.getType().getMultiplier() + "x§r §8- §b§l" + TimeUtils.humanFriendlyTimer(player.getLanguage(), CoinBooster.ACTIVE.getTimer().getRemainingTicks() * 50)
                    )
            )
        );
        
        set(7,
            player -> new ItemBuilder(Material.ENDER_PEARL, 1, "§5 "),

            new InteractiveKit.Interaction<KitPvP, KitPvPPlayer>(server) {
                @Override
                public void onInteract(KitPvPPlayer player, PlayerInteractEvent event, ItemStack itemStack) {
                    new ServerSelectorGUI<>(player).open();
                }
            }.onActionBarHover(
                (player, item) -> "§3§lServer Selector§r §8- §e§l" + player.translate("spigot", "player.mouse.right_click")
            )
        );
    }
}
