package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.brigadier;

import net.minecraft.commands.CommandSourceStack;
import org.bukkit.command.CommandSender;

public class BrigadierNms_26_1 implements BrigadierNms {

    @Override
    public CommandSender getSender(Object wrapper) {
        return ((CommandSourceStack) wrapper).getBukkitSender();
    }
}
