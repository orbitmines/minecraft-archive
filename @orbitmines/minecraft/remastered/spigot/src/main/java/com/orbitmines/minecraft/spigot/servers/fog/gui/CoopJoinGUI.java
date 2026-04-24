package com.orbitmines.minecraft.spigot.servers.fog.gui;

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.utils.Message;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.text.TextBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.GUI;
import com.orbitmines.minecraft.spigot.servers.fog.FoG;
import com.orbitmines.minecraft.spigot.servers.fog.FoGPlayer;
import com.orbitmines.minecraft.spigot.servers.fog.run.Run;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * List of online players currently in a run. Clicking sends a coop-join request
 * to the target (like /tphere). The target accepts via a clickable chat message
 * or {@code /coopaccept <requester>}.
 *
 * <p>If the viewer closes this GUI without clicking any player, the RunSelector
 * is re-opened — cancelling coop puts them back at the top-level choice.</p>
 */
public class CoopJoinGUI extends GUI<FoGPlayer> {

    /** UUID → currently-open GUI; used by {@link com.orbitmines.minecraft.spigot.servers.fog.events.CoopCloseListener}. */
    private static final Map<UUID, CoopJoinGUI> OPEN = new HashMap<>();

    private final FoG fog;
    private boolean requestSent;

    public static void open(FoG server, FoGPlayer viewer) {
        CoopJoinGUI gui = new CoopJoinGUI(server, viewer);
        OPEN.put(viewer.getUUID(), gui);
        gui.open();
    }

    /**
     * Called from {@link com.orbitmines.minecraft.spigot.servers.fog.events.CoopCloseListener}
     * when the viewer closes any inventory. If the closed one is the registered
     * CoopJoinGUI and no request was sent, re-open RunSelector on the next tick.
     */
    public static void onInventoryClose(FoG server, FoGPlayer viewer, Inventory closed) {
        CoopJoinGUI gui = OPEN.get(viewer.getUUID());
        if (gui == null) return;
        if (gui.getInventory() != closed) return;
        OPEN.remove(viewer.getUUID());
        if (!gui.requestSent) {
            server.runSync(() -> RunSelector.open(server, viewer));
        }
    }

    private CoopJoinGUI(FoG server, FoGPlayer viewer) {
        super(54, "§0§l" + viewer.translate("fog", "gui.coop_join.title"), viewer);
        this.fog = server;

        int slot = 0;
        for (FoGPlayer other : server.getPlayers()) {
            if (other == viewer) continue;
            Run run = other.getActiveRun();
            if (run == null) continue;
            final Run finalRun = run;
            final String otherName = other.getName();
            final FoGPlayer finalOther = other;
            set(slot++, new Item<FoGPlayer, MutableItemBuilder>(() ->
                new ItemBuilder(Material.PLAYER_HEAD, 1, "§e§l" + otherName)
                    .addLore(viewer.translate("fog", "gui.coop_join.run", finalRun.getId()))
                    .addLore(viewer.translate("fog", "gui.coop_join.difficulty", finalRun.getDifficulty().getColoredName(viewer)))
                    .addLore(" ")
                    .addLore("§a§l" + viewer.translate("fog", "gui.coop_join.click_to_request")),
                event -> {
                    this.requestSent = true;
                    OPEN.remove(viewer.getUUID());
                    viewer.closeInventory();
                    sendRequest(server, viewer, finalOther, finalRun);
                }
            ));
            if (slot >= 45) break;
        }

        for (int s = slot; s < getInventory().getSize(); s++) {
            set(s, new Item<FoGPlayer, MutableItemBuilder>(() ->
                new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, "§f")));
        }
    }

    /** Send a coop request to {@code target}. Target sees a click-to-accept chat message. */
    private static void sendRequest(FoG server, FoGPlayer requester, FoGPlayer target, Run run) {
        target.getCoopRequestsFrom().put(requester.getRawName(), run.getId());

        requester.sendMessage("Coop", Color.LIME, "fog", "coop.request.sent", target.getName(Name.RAW_COLORED) + "§7");

        target.sendRawMessage("");
        target.sendMessage("Coop", Color.BLUE, "fog", "coop.request.received", requester.getName(Name.RAW_COLORED) + "§7");

        TextBuilder<FoGPlayer> builder = new TextBuilder<>();
        builder.add(Color.SILVER, p -> Message.format("Coop", Color.LIME,
                        "  " + target.translate("fog", "coop.request.click_to_accept",
                                "§a" + target.translate("fog", "coop.request.accept"))))
               .click(ClickEvent.Action.RUN_COMMAND, p -> "/coopaccept " + requester.getRawName())
               .hover(HoverEvent.Action.SHOW_TEXT, p -> "§7" + target.translate("fog", "coop.request.hover",
                       requester.getName(Name.RAW_COLORED) + "§7"));
        builder.send(target);
    }
}
