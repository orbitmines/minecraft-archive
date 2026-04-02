package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Environment;
import com.orbitmines.archive.minecraft._2019.utils.URLUtils;

import java.util.Collections;

public class TabListHandler<S extends OMServer<S, P>, P extends OMPlayer<S, P>> {

    protected final S server;
    protected final P player;

    public TabListHandler(S server, P player) {
        this.server = server;
        this.player = player;
    }

    public String getHeader() {
        return "\n§8§lOrbit§7§lMines\n" +
                server.getType().getDisplayName() +
                "\n";
    }

    public String getFooter() {
        String websiteLink = URLUtils.humanReadableLink(Environment.get("OM_WEBSITE_LINK", "https://www.orbitmines.com"));
        String shopLink = URLUtils.humanReadableLink(Environment.get("OM_SHOP_LINK", "https://shop.orbitmines.com"));
        String discordLink = URLUtils.humanReadableLink(Environment.get("OM_DISCORD_INVITE_LINK", "https://www.discord.orbitmines.com/minecraft"));

        return "\n" +
                "    §7Website: §6§l" + websiteLink + "§r    \n" +
                "    §7" + player.translate("spigot", "word.shop") + ": §3§l" + shopLink + "§r    \n" +
                "    §7Discord: §9§l" + discordLink + "    \n" +
                "\n" +
                "    §7Voting: §9§l/vote§r    \n" +
                "§7";
    }

    public void send() {
        server.getNms().tabList().send(Collections.singletonList(player.bukkit()), getHeader(), getFooter());
    }
}
