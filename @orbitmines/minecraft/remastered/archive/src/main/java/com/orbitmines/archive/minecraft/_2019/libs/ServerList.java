package com.orbitmines.archive.minecraft._2019.libs;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public enum ServerList {

    MINECRAFT_SERVERS_ORG("MinecraftServers.org", "MinecraftServers.org", "https://minecraftservers.org/vote/686052"),
    //SERVER_PACT("ServerPact.com", "ServerPact.com", "https://serverpact.nl/vote-8503"),
    MINECRAFT_SERVER_LIST_COM("Minecraft-Server-List.com", "MCSL", "https://minecraft-server-list.com/server/519217/vote/"),
    MINECRAFT_SERVERLIST_COM("Minecraft-Serverlist.com", "minecraft-serverlist-com", "https://minecraft-serverlist.com/server/4827/vote"),
    MINECRAFT_MP_COM("Minecraft-MP.com", "Minecraft-MP.com", "https://minecraft-mp.com/server/356588/vote/"),
    TOPMINECRAFTSERVERS_ORG("TopMinecraftServers.org", "TopMinecraftServers", "https://topminecraftservers.org/vote/43164"),
    MINECRAFT_SERVERS_LIST_ORG("Minecraft-Servers-List.org", "http://www.minecraft-servers-list.org", ""),
    MINECRAFT_SERVER_NET("Minecraft-Server.net", "Minecraft-Server.net", "");
    //TOP_G_ORG("TopG.org", "TopG.org", "https://topg.org/Minecraft/in-495521");

    public static final List<ServerList> ACTIVE = Arrays.asList(
        MINECRAFT_SERVERS_ORG,
        MINECRAFT_SERVER_LIST_COM,
        MINECRAFT_SERVERLIST_COM,
        MINECRAFT_MP_COM,
        TOPMINECRAFTSERVERS_ORG
    );

    @Getter private final String displayName;
    @Getter private final String domainName;
    @Getter private final String url;

    ServerList(String displayName, String domainName, String url) {
        this.displayName = displayName;
        this.domainName = domainName;
        this.url = url;
    }

    public boolean isActive() {
        return ACTIVE.contains(this);
    }

    public static ServerList fromDomain(String domainName) {
        for (ServerList serverList : ServerList.values()) {
            if (serverList.getDomainName().equalsIgnoreCase(domainName))
                return serverList;
        }

        return null;
    }
}
