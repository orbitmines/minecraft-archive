package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.tablist;

import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundTabListPacket;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Collection;

public class TabListNms_26_1 implements TabListNms {

    public void send(Collection<? extends Player> players, String header, String footer) {
        Component headerComponent = Component.literal(header);
        Component footerComponent = Component.literal(footer);
        ClientboundTabListPacket packet = new ClientboundTabListPacket(headerComponent, footerComponent);

        for (Player player : players) {
            ((CraftPlayer) player).getHandle().connection.send(packet);
        }
    }
}
