package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.guis;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.utils.ServerUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilderInstance;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.GUI;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public class ServerSelectorGUI<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends GUI<P> {

    private static final List<Integer> unknownGalaxies = Arrays.asList(
        (3 * 9) + 1,
        (3 * 9) + 5,
        (3 * 9) + 7,
        (4 * 9) + 2,
        (4 * 9) + 6
    );

    public ServerSelectorGUI(P viewer) {
        super(54, "§0§lServer Selector", viewer);

        for (int i = 0; i < this.getInventory().getSize(); i++) {
            if (i == (1 * 9) + 4)
                setItem(i, Server.HUB);
            else if (i == (4 * 9) + 4)
                setItem(i, Server.SURVIVAL);
            else if (i == (3 * 9) + 3)
                setItem(i, Server.KITPVP);
            else if (unknownGalaxies.contains(i))
                set(i, new Item<P, MutableItemBuilder>(() -> new ItemBuilder(Material.RED_STAINED_GLASS_PANE, 1, "§c" + viewer.translate("spigot", "player.unknown_galaxies"))));
            else
                set(i, new Item<P, MutableItemBuilder>(() -> new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, "§f")));
        }
    }

    private void setItem(int i, Server server) {
        set(i, new Item<P, MutableItemBuilder>(() -> getItem(server)).click((event) -> {
            viewer.connect(server, true);
        }));
    }


    private ItemBuilderInstance getItem(Server server) {
        ItemBuilderInstance item = ServerUtils.getItemBuilder(server);
        item.setDisplayName("§8§lOrbit§7§lMines " + server.getDisplayName());

        item.addLore("§7§o" + viewer.translate("spigot", getServerNamespace(server) + ".underline"));
        item.addLore("");

        Server.Status status = server.getStatus();
        item.addLore(status != Server.Status.ONLINE ? status.getColor().getCc() + "§l" + status.getName() : server.getColor().getCc() + "§l" + server.getPlayerCount() + " §7§l/ " + server.getMaxPlayers());
        item.addLore("");

        for (String description : viewer.getLanguage().getStringArray("spigot", getServerNamespace(server) + ".description")) {
            item.addLore("§7" + description);
        }

        if (server != Server.HUB) {
            item.addLore("");

            for (String feature : viewer.getLanguage().getStringArray("spigot", getServerNamespace(server) + ".features")) {
                item.addLore("§7- " + server.getColor().getCc() + feature);
            }
        }

        item.addLore("");

        if (server == viewer.server().getType()) {
            item.glow();
            item.addLore("§c" + viewer.translate("spigot", "player.server_selector.already_connected"));
        } else {
            item.addLore("§a" + viewer.translate("spigot", "player.server_selector.click_to_connect"));
        }

        return item;
    }
    
    private String getServerNamespace(Server server) {
        return "player.server_selector." + server.toString().toLowerCase();
    }
}
