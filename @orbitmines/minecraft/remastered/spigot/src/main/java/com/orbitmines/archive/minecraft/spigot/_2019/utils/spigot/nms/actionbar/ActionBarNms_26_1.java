package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.actionbar;

import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Collection;

public class ActionBarNms_26_1 implements ActionBarNms {

    public void send(Collection<? extends Player> players, String actionBar) {
        if (actionBar == null) actionBar = "";
        Component component = Component.literal(actionBar);
        ClientboundSystemChatPacket packet = new ClientboundSystemChatPacket(component, true);

        for (Player player : players) {
            ((CraftPlayer) player).getHandle().connection.send(packet);
        }
    }
}
