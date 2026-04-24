package com.orbitmines.minecraft.spigot.servers.fog.gui;

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.GUI;
import com.orbitmines.minecraft.spigot.servers.fog.FoG;
import com.orbitmines.minecraft.spigot.servers.fog.FoGPlayer;
import com.orbitmines.minecraft.spigot.servers.fog.drone.Drone;
import com.orbitmines.minecraft.spigot.servers.fog.drone.DroneFactory;
import com.orbitmines.minecraft.spigot.servers.fog.drone.DroneModule;
import com.orbitmines.minecraft.spigot.servers.fog.drone.ModuleType;

public class DroneFactoryGUI extends GUI<FoGPlayer> {

    public DroneFactoryGUI(FoG server, FoGPlayer viewer) {
        super(54, "§0§l" + viewer.translate("fog", "gui.drone_factory.title"), viewer);

        int slot = 0;
        for (Drone drone : viewer.getDrones()) {
            final Drone finalDrone = drone;
            set(slot++, new Item<FoGPlayer, MutableItemBuilder>(() ->
                new ItemBuilder(org.bukkit.Material.ALLAY_SPAWN_EGG, 1,
                        "§b§l" + viewer.translate("fog", "gui.drone_factory.drone_title", finalDrone.getId()))
                    .addLore(viewer.translate("fog", "gui.drone_factory.hp", (int) finalDrone.getHealth(), (int) finalDrone.getMaxHealth()))
                    .addLore(viewer.translate("fog", "gui.drone_factory.modules", finalDrone.getModules().size()))
                    .addLore(" ")
                    .addLore("§e§l" + viewer.translate("fog", "gui.drone_factory.click_to_repair", DroneFactory.repairCost(finalDrone))),
                event -> {
                    DroneFactory.repair(finalDrone);
                    viewer.sendMessage("FoG", com.orbitmines.archive.minecraft._2019.libs.Color.LIME, "fog", "drone.repaired", finalDrone.getId());
                }
            ));
            if (slot >= 9) break;
        }

        slot = 18;
        for (ModuleType module : ModuleType.values()) {
            final ModuleType finalModule = module;
            set(slot++, new Item<FoGPlayer, MutableItemBuilder>(() ->
                new ItemBuilder(finalModule.getIcon(), 1,
                        finalModule.getColor().getCc() + "§l" + finalModule.getDisplayName(viewer))
                    .addLore(" ")
                    .addLore("§e§l" + viewer.translate("fog", "gui.drone_factory.click_to_attach")),
                event -> {
                    if (viewer.getDrones().isEmpty()) {
                        viewer.sendMessage("FoG", com.orbitmines.archive.minecraft._2019.libs.Color.RED, "fog", "drone.none");
                        return;
                    }
                    Drone first = viewer.getDrones().get(0);
                    if (!first.addModule(new DroneModule(finalModule))) {
                        viewer.sendMessage("FoG", com.orbitmines.archive.minecraft._2019.libs.Color.RED, "fog", "drone.modules_full");
                    } else {
                        viewer.sendMessage("FoG", com.orbitmines.archive.minecraft._2019.libs.Color.LIME, "fog", "drone.module_attached",
                                finalModule.getDisplayName(viewer), first.getId());
                    }
                }
            ));
        }
    }
}
