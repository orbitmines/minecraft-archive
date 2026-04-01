package com.orbitmines.archive.minecraft._2019.libs;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public enum ServerList {

    MINECRAFT_SERVERS_ORG("MinecraftServers.org", "MinecraftServers.org", "https://minecraftservers.org/vote/508746"),
    //SERVER_PACT("ServerPact.com", "ServerPact.com", "https://serverpact.nl/vote-8503"),
    MINECRAFT_SERVERS_LIST_ORG("Minecraft-Servers-List.org", "http://www.minecraft-servers-list.org", "http://www.minecraft-servers-list.org/index.php?a=in&u=OrbitMines"),
    MINECRAFT_SERVER_NET("Minecraft-Server.net", "Minecraft-Server.net", "https://minecraft-server.net/vote/orbitmines/"),
    MINECRAFT_MP_COM("Minecraft-MP.com", "Minecraft-MP.com", "https://minecraft-mp.com/server/201145/vote/"),
    TOPMINECRAFTSERVERS_ORG("TopMinecraftServers.org", "TopMinecraftServers", "https://topminecraftservers.org/vote/4030");
    //TOP_G_ORG("TopG.org", "TopG.org", "https://topg.org/Minecraft/in-495521");

    public static final List<ServerList> ACTIVE = Arrays.asList(
        MINECRAFT_SERVERS_ORG,
        MINECRAFT_SERVERS_LIST_ORG,
        MINECRAFT_SERVER_NET,
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
