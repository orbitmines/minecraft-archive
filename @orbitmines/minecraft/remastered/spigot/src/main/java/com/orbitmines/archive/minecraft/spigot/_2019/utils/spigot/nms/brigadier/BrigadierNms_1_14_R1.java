package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.brigadier;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import net.minecraft.server.v1_14_R1.CommandListenerWrapper;
import org.bukkit.command.CommandSender;

public class BrigadierNms_1_14_R1 implements BrigadierNms {

    @Override
    public CommandSender getSender(Object wrapper) {
        return ((CommandListenerWrapper) wrapper).getBukkitSender();
    }
}
